package com.vsa.filmoteca.presentation.service;

import android.app.Service;
import android.content.Intent;
import android.os.IBinder;
import android.util.Log;

import com.twitter.sdk.android.Twitter;
import com.twitter.sdk.android.core.Callback;
import com.twitter.sdk.android.core.Result;
import com.twitter.sdk.android.core.TwitterApiClient;
import com.twitter.sdk.android.core.TwitterCore;
import com.twitter.sdk.android.core.TwitterException;
import com.twitter.sdk.android.core.TwitterSession;
import com.twitter.sdk.android.core.models.Search;
import com.twitter.sdk.android.core.models.Tweet;
import com.vsa.filmoteca.data.model.event.BUS;
import com.vsa.filmoteca.data.model.event.comments.EventOnTweetsLoaded;
import com.vsa.filmoteca.presentation.utils.Constants;

import java.util.List;

/**
 * Created by seldon on 31/03/15.
 */
public class ReloadTweetsService extends Service implements Runnable {

    public static final String TAG = ReloadTweetsService.class.getSimpleName();

    public static final String EXTRA_HASHTAG = "extraHashTag";

    private String mHashTag;
    private TwitterSession mUserSession;
    private Thread mThreadTask = new Thread(this);
    private Callback<Search> mCallbackTweetSearch = new Callback<Search>() {
        @Override
        public void success(Result<Search> result) {
            final List<Tweet> tweets = result.data.tweets;
            BUS.getInstance().post(new EventOnTweetsLoaded(tweets));
            Log.d(TAG, "REFRESH TWEETS!");
        }

        @Override
        public void failure(TwitterException exception) {
        }
    };


    @Override
    public int onStartCommand(Intent intent, int flags, int startId) {
        mHashTag = intent.getStringExtra(EXTRA_HASHTAG);
        mUserSession = Twitter.getSessionManager().getActiveSession();
        if (mUserSession == null || mUserSession.getAuthToken().isExpired()) {
            stopSelf();
        } else {
            if (!mThreadTask.isAlive())
                mThreadTask.start();
        }
        return START_NOT_STICKY;
    }

    @Override
    public IBinder onBind(Intent intent) {
        return null;
    }


    @Override
    public void run() {
        while (!mThreadTask.interrupted()) {
            try {
                Thread.sleep(5500);
            } catch (InterruptedException e) {
                e.printStackTrace();
                mThreadTask.interrupt();
            }
            if (!mUserSession.getAuthToken().isExpired()) {
                TwitterApiClient twitterApiClient = TwitterCore.getInstance().getApiClient(mUserSession);
                twitterApiClient.getSearchService().tweets(
                        Constants.HASHTAG_FILMOTECA + " AND " + mHashTag,
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
            } else {
                mThreadTask.interrupt();
            }
        }
    }


    @Override
    public void onDestroy() {
        super.onDestroy();
        mThreadTask.interrupt();
    }
}
