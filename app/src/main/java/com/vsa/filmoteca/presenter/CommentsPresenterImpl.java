package com.vsa.filmoteca.presenter;

import android.content.Intent;
import android.util.Log;
import android.view.MenuItem;
import android.widget.Toast;

import com.twitter.sdk.android.Twitter;
import com.twitter.sdk.android.core.AppSession;
import com.twitter.sdk.android.core.Callback;
import com.twitter.sdk.android.core.Result;
import com.twitter.sdk.android.core.Session;
import com.twitter.sdk.android.core.TwitterApiClient;
import com.twitter.sdk.android.core.TwitterCore;
import com.twitter.sdk.android.core.TwitterException;
import com.twitter.sdk.android.core.TwitterSession;
import com.twitter.sdk.android.core.identity.TwitterLoginButton;
import com.twitter.sdk.android.core.models.Search;
import com.twitter.sdk.android.core.models.Tweet;
import com.twitter.sdk.android.core.models.User;
import com.vsa.filmoteca.FilmotecaApplication;
import com.vsa.filmoteca.model.sharedpreferences.SharedPreferencesManager;
import com.vsa.filmoteca.model.twitter.FakeTweetsManager;
import com.vsa.filmoteca.model.twitter.TweetComparator;
import com.vsa.filmoteca.presenter.service.ReloadTweetsService;
import com.vsa.filmoteca.presenter.utils.Constants;
import com.vsa.filmoteca.presenter.utils.StringUtils;
import com.vsa.filmoteca.view.CommentsView;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

import de.greenrobot.event.EventBus;
import retrofit.client.Header;

/**
 * Created by seldon on 31/03/15.
 */
public class CommentsPresenterImpl implements CommentsPresenter {

    private static final String TAG = CommentsPresenterImpl.class.getSimpleName();

    private CommentsView mView;
    private AppSession mGuestSession;
    private TwitterSession mUserSession;
    private String mMovieHashTag;

    private TweetComparator mTweetComparator = new TweetComparator();

    //FIRST SEARCH CALLBACK
    private Callback<Search> mCallbackTweetSearch = new Callback<Search>() {
        @Override
        public void success(Result<Search> result) {
            showTweets(result.data.tweets);
            mView.hideProgressDialog();
            startRefreshingTweets();
            for(Header header:result.response.getHeaders()) {
                Log.d(TAG, header.getName() + ": " + header.getValue());
            }
        }

        @Override
        public void failure(TwitterException exception) {
            mView.showErrorCantLoadTweets();
            mView.hideProgressDialog();
        }
    };

    //NEW TWEET CALLBACK
    private Callback<Tweet> mCallbackNewTweet = new Callback<Tweet>() {
        @Override
        public void success(Result<Tweet> tweetResult) {
            FakeTweetsManager.addFakeTweet(mMovieHashTag, tweetResult.data);
            mView.addTweet(tweetResult.data);
        }

        @Override
        public void failure(TwitterException e) {
            mView.showErrorCantPostTweet();
            mView.hideProgressDialog();
        }
    };

    //USER LOGIN CALLBACK
    private Callback<TwitterSession> mCallbackUserLogin = new Callback<TwitterSession>() {
        @Override
        public void success(Result<TwitterSession> twitterSessionResult) {
            mUserSession = twitterSessionResult.data;
            mView.showTweetEditor();
        }

        @Override
        public void failure(TwitterException e) {
            mView.showErrorCantLogin();
            mView.hideProgressDialog();
        }
    };

    //GUEST LOGIN CALLBACK
    private Callback<AppSession> mCallbackGuestLogin = new Callback<AppSession>() {
        @Override
        public void success(Result<AppSession> appSessionResult) {
            ((FilmotecaApplication) mView.getContext().getApplicationContext()).setGuestSession(appSessionResult.data);
            requestTweets(appSessionResult.data, mMovieHashTag);
        }

        @Override
        public void failure(TwitterException e) {
            mView.hideProgressDialog();
        }
    };

    //GET USER INFO CALLBACK
    private Callback<User> mCallbackUserInfo = new Callback<User>() {
        @Override
        public void success(Result<User> userResult) {
            SharedPreferencesManager.setTwitterProfileImageUrl(mView.getContext(), userResult.data.profileImageUrl);
            SharedPreferencesManager.setTwitterUserDescription(mView.getContext(), userResult.data.name);
            mView.showProfileImage(userResult.data.profileImageUrl);
            mView.showUserDescription(userResult.data.name);
            mView.showUserName("@" + userResult.data.screenName);
        }

        @Override
        public void failure(TwitterException e) {
        }
    };

    public CommentsPresenterImpl(CommentsView view) {
        mView = view;
    }

    @Override
    public void onResume(String movieTitle) {
        EventBus.getDefault().register(this);
        if(!movieTitle.isEmpty())
            mMovieHashTag = createMovieHashTag(movieTitle);
        restoreSession();
        loadTweets(mGuestSession, mMovieHashTag);
        if(mUserSession != null && !mUserSession.getAuthToken().isExpired()) {
            loadUserInfo(mUserSession);
            mView.showTweetEditor();
            mView.setMaxTweetLength(getTweetLength());
            mView.showCharactersLeft(Integer.toString(getTweetLength()));
        } else {
            mView.showLoginButton();
        }
    }

    @Override
    public void onPause() {
        EventBus.getDefault().unregister(this);
        stopRefreshingTweets();
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch(item.getItemId()) {
            case android.R.id.home:
                mView.onBackPressed();
                return true;
            default:
                return false;
        }
    }

    @Override
    public void setLoginCallBack(TwitterLoginButton twitterLoginButton) {
        twitterLoginButton.setCallback(mCallbackUserLogin);
    }

    @Override
    public void onPublishTweet(String message) {
        if(message == null || message.isEmpty()) {
            mView.showErrorEmptyMessage();
        } else {
            TwitterApiClient apiClient = TwitterCore.getInstance().getApiClient(mUserSession);
            apiClient.getStatusesService().update(
                    Constants.HASHTAG_FILMOTECA + " " + mMovieHashTag + " " + message,
                    null,
                    null,
                    null,
                    null,
                    null,
                    null,
                    null,
                    mCallbackNewTweet);
        }
    }

    @Override
    public void onTweetTextChanged(CharSequence message) {
        mView.showCharactersLeft(Integer.toString(getTweetLength() - message.length()));
    }

    @Override
    public void closeSession() {
        if(mUserSession != null) {
            stopRefreshingTweets();
            Twitter.getSessionManager().clearActiveSession();
            SharedPreferencesManager.removeTwitterAccountInfo(mView.getContext());
            mView.showLoginButton();
        }
    }

    public void loadTweets(Session session, String hashTag) {
        mView.showProgressDialog();
        if(session != null)
            requestTweets(session, hashTag);
        else if (session instanceof AppSession)
            requestGuestSessionAndGetTweets();

    }

    private void requestTweets(Session session, String hashTag) {
        TwitterApiClient twitterApiClient = TwitterCore.getInstance().getApiClient(session);
        twitterApiClient.getSearchService().tweets(
                Constants.HASHTAG_FILMOTECA + " AND " + hashTag,
                null,
                null,
                null,
                null,
                50,
                null,
                null,
                null,
                true,
                mCallbackTweetSearch);
    }

    private void loadUserInfo(TwitterSession session) {
        TwitterApiClient twitterApiClient = TwitterCore.getInstance().getApiClient(session);
        String profileImageUrl = SharedPreferencesManager.getTwitterProfileImageUrl(mView.getContext());
        String userDescription = SharedPreferencesManager.getTwitterUserDescription(mView.getContext());
        if(profileImageUrl.isEmpty() || userDescription.isEmpty()) {
            twitterApiClient.getAccountService().verifyCredentials(true, true, mCallbackUserInfo);
        } else {
            mView.showProfileImage(profileImageUrl);
            mView.showUserDescription(userDescription);
            mView.showUserName("@" + session.getUserName());
        }
    }

    private void requestGuestSessionAndGetTweets() {
        TwitterCore.getInstance().logInGuest(mCallbackGuestLogin);
    }

    synchronized private void showTweets(List<Tweet> tweets) {
        List<Tweet> realTweets = new ArrayList<>(tweets);
        List<Tweet> fakeTweetsList = FakeTweetsManager.getFakeTweets(mMovieHashTag);
        for(Tweet tweet:realTweets) {
            if(fakeTweetsList.contains(tweet)) {
                fakeTweetsList.remove(tweet);
                Toast.makeText(mView.getContext(), "REEMPLAZO EL TWEET FAKE POR EL TWEET REAL", Toast.LENGTH_SHORT).show();
            }
        }
        realTweets.addAll(fakeTweetsList);
        Collections.sort(realTweets, mTweetComparator);
        mView.showTweets(realTweets);
    }

    private void restoreSession() {
        mGuestSession = ((FilmotecaApplication) mView.getContext().getApplicationContext())
                .getGuestSession();
        mUserSession = Twitter.getSessionManager().getActiveSession();
    }

    //Listen events for EventBus
    public void onEvent(List<Tweet> tweetList){
        showTweets(tweetList);
    }

    private String createMovieHashTag(String movieTitle) {
        String movieHashTag = movieTitle.toLowerCase();
        movieHashTag = movieHashTag.replaceAll("\\p{Punct}+", "");
        return "#" + StringUtils.firstLetterUpperCase(movieHashTag).replace(" ", "");
    }

    private void startRefreshingTweets() {
        Intent intent = new Intent(mView.getContext(), ReloadTweetsService.class);
        intent.putExtra(ReloadTweetsService.EXTRA_HASHTAG, mMovieHashTag);
        mView.getContext().startService(intent);
    }

    private void stopRefreshingTweets() {
        mView.getContext().stopService(new Intent(mView.getContext(), ReloadTweetsService.class));
    }

    private int getTweetLength() {
        int tweetLength = 140
                - Constants.HASHTAG_FILMOTECA .length()
                - mMovieHashTag.length()
                - 2;
        return tweetLength;
    }

}
