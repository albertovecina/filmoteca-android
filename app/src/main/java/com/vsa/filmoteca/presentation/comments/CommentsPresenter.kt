package com.vsa.filmoteca.presentation.comments

import com.twitter.sdk.android.core.TwitterSession
import com.twitter.sdk.android.core.models.Search
import com.twitter.sdk.android.core.models.Tweet
import com.twitter.sdk.android.core.models.User
import com.vsa.filmoteca.data.model.twitter.FakeTweetsManager
import com.vsa.filmoteca.data.model.twitter.TweetComparator
import com.vsa.filmoteca.data.usecase.CommentsUseCase
import com.vsa.filmoteca.presentation.Presenter
import com.vsa.filmoteca.presentation.utils.Constants
import com.vsa.filmoteca.presentation.utils.StringUtils
import com.vsa.filmoteca.view.CommentsView

import java.util.ArrayList
import java.util.Collections
import java.util.concurrent.TimeUnit

import rx.Observable
import rx.Observer
import rx.Subscriber

/**
 * Created by seldon on 31/03/15.
 */
class CommentsPresenter(private val mCommentsUseCase: CommentsUseCase) : Presenter<CommentsView>() {

    private var mMovieHashTag: String? = null

    private val mTweetComparator = TweetComparator()

    private val mSearchSubscriber = object : Subscriber<Search>() {
        override fun onCompleted() {

        }

        override fun onError(e: Throwable) {
            view!!.showErrorCantLoadTweets()
        }

        override fun onNext(search: Search?) {
            if (search != null)
                showTweets(search.tweets)
        }
    }

    private val mTweetObserver = object : Observer<Tweet> {
        override fun onCompleted() {

        }

        override fun onError(e: Throwable) {
            view!!.showErrorCantPostTweet()
        }

        override fun onNext(tweet: Tweet) {
            FakeTweetsManager.addFakeTweet(mMovieHashTag, tweet)
            view!!.addTweet(tweet)
        }
    }

    private val mTwitterSessionObserver = object : Observer<TwitterSession> {
        override fun onCompleted() {

        }

        override fun onError(e: Throwable) {
            view!!.showErrorCantLogin()
        }

        override fun onNext(twitterSession: TwitterSession) {
            //requestUserInfo(twitterSession);
            view!!.showTweetEditor()
        }
    }

    private val tweetLength: Int
        get() = (140
                - Constants.HASHTAG_FILMOTECA.length
                - mMovieHashTag!!.length
                - 2)

    fun onCreate(movieTitle: String) {
        mMovieHashTag = createMovieHashTag(movieTitle)
        view!!.twitterSession().subscribe(mTwitterSessionObserver)
    }

    fun onResume() {
        /* Session session = mCommentsUseCase.getActiveSession();
        if (session == null) {
            startGuestSessionAndRefreshTweets();
            view.showLoginButton();
        } else if (session instanceof TwitterSession) {
            TwitterSession twitterSession = (TwitterSession) session;
            //requestUserInfo(twitterSession);
            view.showTweetEditor();
            view.setMaxTweetLength(getTweetLength());
            view.showCharactersLeft(Integer.toString(getTweetLength()));
            startRefreshingTweets();
        } else {
            view.showLoginButton();
            startRefreshingTweets();
        }*/
    }

    fun onPause() {
        stopRefreshingTweets()
    }

    fun onDestroy() {}

    fun onHomeAsUpClick() {
        view!!.onBackPressed()
    }

    fun onPublishButtonClick() {
        val message = view!!.message
        if (message == null || message.isEmpty())
            view.showErrorEmptyMessage()
        else
            mCommentsUseCase.update(mCommentsUseCase.activeSession, mMovieHashTag!!, message).subscribe(mTweetObserver)
    }

    fun onTweetTextChanged(message: CharSequence) {
        view!!.showCharactersLeft(Integer.toString(tweetLength - message.length))
    }

    fun closeSession() {
        mCommentsUseCase.closeActiveSession()
        view!!.showLoginButton()
    }

    private fun requestUserInfo(session: TwitterSession) {
        mCommentsUseCase.verifyCredentials(session).subscribe(object : Observer<User> {
            override fun onCompleted() {

            }

            override fun onError(e: Throwable) {
                view!!.showErrorVerifyCredentials()
                closeSession()
            }

            override fun onNext(user: User) {
                showUserInfo(user)
            }
        })
    }

    private fun showUserInfo(user: User) {
        view!!.showProfileImage(user.profileImageUrl)
        view.showUserDescription(user.name)
        view.showUserName("@" + user.screenName)
    }

    @Synchronized
    private fun showTweets(tweets: List<Tweet>) {
        val realTweets = ArrayList(tweets)
        val fakeTweetsList = FakeTweetsManager.getFakeTweets(mMovieHashTag)

        for (tweet in realTweets)
            if (fakeTweetsList.contains(tweet))
                fakeTweetsList.remove(tweet)

        realTweets.addAll(fakeTweetsList)
        Collections.sort(realTweets, mTweetComparator)
        view!!.showTweets(realTweets)
    }

    private fun createMovieHashTag(movieTitle: String): String {
        var movieHashTag = movieTitle.toLowerCase()
        movieHashTag = StringUtils.removePunctuationSymbols(movieHashTag)
        movieHashTag = StringUtils.capitalizeFirstCharacter(movieHashTag)
        movieHashTag = "#" + StringUtils.removeBlankSpaces(movieHashTag)
        return movieHashTag
    }

    private fun startGuestSessionAndRefreshTweets() {
        startRefreshingTweets()
    }

    private fun startRefreshingTweets() {
        Observable.interval(0, 5500, TimeUnit.MILLISECONDS)
                .flatMap { n ->
                    val session = mCommentsUseCase.activeSession
                    mCommentsUseCase.tweets(session as TwitterSession, mMovieHashTag!!)
                }.subscribe(mSearchSubscriber)
    }

    private fun stopRefreshingTweets() {
        if (!mSearchSubscriber.isUnsubscribed)
            mSearchSubscriber.unsubscribe()
    }

}
