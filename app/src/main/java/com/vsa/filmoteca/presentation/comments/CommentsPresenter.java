package com.vsa.filmoteca.presentation.comments;

import com.twitter.sdk.android.core.AppSession;
import com.twitter.sdk.android.core.Session;
import com.twitter.sdk.android.core.TwitterSession;
import com.twitter.sdk.android.core.models.Search;
import com.twitter.sdk.android.core.models.Tweet;
import com.twitter.sdk.android.core.models.User;
import com.vsa.filmoteca.data.model.twitter.FakeTweetsManager;
import com.vsa.filmoteca.data.model.twitter.TweetComparator;
import com.vsa.filmoteca.data.usecase.CommentsUseCase;
import com.vsa.filmoteca.presentation.Presenter;
import com.vsa.filmoteca.presentation.utils.Constants;
import com.vsa.filmoteca.presentation.utils.StringUtils;
import com.vsa.filmoteca.view.CommentsView;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.concurrent.TimeUnit;

import rx.Observable;
import rx.Observer;
import rx.Subscriber;

/**
 * Created by seldon on 31/03/15.
 */
public class CommentsPresenter implements Presenter<CommentsView> {

    private CommentsView mView;
    private String mMovieHashTag;

    private CommentsUseCase mCommentsUseCase;

    private TweetComparator mTweetComparator = new TweetComparator();

    private Subscriber<Search> mSearchSubscriber = new Subscriber<Search>() {
        @Override
        public void onCompleted() {

        }

        @Override
        public void onError(Throwable e) {
            mView.showErrorCantLoadTweets();
            mView.hideProgressDialog();
        }

        @Override
        public void onNext(Search search) {
            if (search != null)
                showTweets(search.tweets);
            mView.hideProgressDialog();
        }
    };

    private Observer<Tweet> mTweetObserver = new Observer<Tweet>() {
        @Override
        public void onCompleted() {

        }

        @Override
        public void onError(Throwable e) {
            mView.showErrorCantPostTweet();
            mView.hideProgressDialog();
        }

        @Override
        public void onNext(Tweet tweet) {
            FakeTweetsManager.addFakeTweet(mMovieHashTag, tweet);
            mView.addTweet(tweet);
        }
    };

    private Observer<TwitterSession> mTwitterSessionObserver = new Observer<TwitterSession>() {
        @Override
        public void onCompleted() {

        }

        @Override
        public void onError(Throwable e) {
            mView.showErrorCantLogin();
            mView.hideProgressDialog();
        }

        @Override
        public void onNext(TwitterSession twitterSession) {
            requestUserInfo(twitterSession);
            mView.showTweetEditor();
        }
    };

    public CommentsPresenter(CommentsUseCase commentsUseCase) {
        mCommentsUseCase = commentsUseCase;
    }

    public void onCreate(String movieTitle) {
        mMovieHashTag = createMovieHashTag(movieTitle);
        mView.twitterSession().subscribe(mTwitterSessionObserver);
    }

    public void onResume() {
        Session session = mCommentsUseCase.getActiveSession();
        if (session == null) {
            startGuestSessionAndRefreshTweets();
            mView.showLoginButton();
        } else if (session instanceof TwitterSession) {
            TwitterSession twitterSession = (TwitterSession) session;
            requestUserInfo(twitterSession);
            mView.showTweetEditor();
            mView.setMaxTweetLength(getTweetLength());
            mView.showCharactersLeft(Integer.toString(getTweetLength()));
            startRefreshingTweets();
        } else {
            mView.showLoginButton();
            startRefreshingTweets();
        }
    }

    public void onPause() {
        stopRefreshingTweets();
    }

    public void onDestroy() {
    }

    public void onHomeAsUpClick() {
        mView.onBackPressed();
    }

    public void onPublishButtonClick() {
        String message = mView.getMessage();
        if (message == null || message.isEmpty())
            mView.showErrorEmptyMessage();
        else
            mCommentsUseCase.update(mCommentsUseCase.getActiveSession(), mMovieHashTag, message).subscribe(mTweetObserver);
    }

    public void onTweetTextChanged(CharSequence message) {
        mView.showCharactersLeft(Integer.toString(getTweetLength() - message.length()));
    }

    public void closeSession() {
        mCommentsUseCase.closeActiveSession();
        mView.showLoginButton();
    }

    private void requestUserInfo(TwitterSession session) {
        mCommentsUseCase.verifyCredentials(session).subscribe(user -> {
            showUserInfo(user);
        });
    }

    private void showUserInfo(User user) {
        mView.showProfileImage(user.profileImageUrl);
        mView.showUserDescription(user.name);
        mView.showUserName("@" + user.screenName);
    }

    synchronized private void showTweets(List<Tweet> tweets) {
        List<Tweet> realTweets = new ArrayList<>(tweets);
        List<Tweet> fakeTweetsList = FakeTweetsManager.getFakeTweets(mMovieHashTag);

        for (Tweet tweet : realTweets)
            if (fakeTweetsList.contains(tweet))
                fakeTweetsList.remove(tweet);

        realTweets.addAll(fakeTweetsList);
        Collections.sort(realTweets, mTweetComparator);
        mView.showTweets(realTweets);
    }

    private String createMovieHashTag(String movieTitle) {
        String movieHashTag = movieTitle.toLowerCase();
        movieHashTag = movieHashTag.replaceAll("\\p{Punct}+", "");
        return "#" + StringUtils.capitalizeFirstCharacter(movieHashTag).replace(" ", "");
    }

    private void startGuestSessionAndRefreshTweets() {
        mCommentsUseCase.guestLogin().subscribe(new Observer<AppSession>() {
            @Override
            public void onCompleted() {

            }

            @Override
            public void onError(Throwable e) {
                mView.showErrorGuestSession();
            }

            @Override
            public void onNext(AppSession appSession) {
                startRefreshingTweets();
            }
        });
    }

    private void startRefreshingTweets() {
        Observable.interval(0, 5500, TimeUnit.MILLISECONDS)
                .flatMap(n -> {
                    Session session = mCommentsUseCase.getActiveSession();
                    if (session != null)
                        return mCommentsUseCase.tweets(session, mMovieHashTag);
                    else
                        return Observable.just(null);
                }).subscribe(mSearchSubscriber);
    }

    private void stopRefreshingTweets() {
        if (!mSearchSubscriber.isUnsubscribed())
            mSearchSubscriber.unsubscribe();
    }

    private int getTweetLength() {
        int tweetLength = 140
                - Constants.HASHTAG_FILMOTECA.length()
                - mMovieHashTag.length()
                - 2;
        return tweetLength;
    }

    @Override
    public void setView(CommentsView view) {
        mView = view;
    }
}
