package com.vsa.filmoteca;

import android.app.Application;
import android.util.Log;

import com.twitter.sdk.android.Twitter;
import com.twitter.sdk.android.core.AppSession;
import com.twitter.sdk.android.core.Callback;
import com.twitter.sdk.android.core.Result;
import com.twitter.sdk.android.core.TwitterAuthConfig;
import com.twitter.sdk.android.core.TwitterCore;
import com.twitter.sdk.android.core.TwitterException;

import io.fabric.sdk.android.Fabric;


/**
 * Created by seldon on 31/03/15.
 */
public class FilmotecaApplication extends Application {

    private static final String TAG = FilmotecaApplication.class.getSimpleName();

    private AppSession mGuestSession;

    private Callback<AppSession> mGuestLoginCallback = new Callback<AppSession>() {
        @Override
        public void success(Result<AppSession> appSessionResult) {
            mGuestSession = appSessionResult.data;
        }

        @Override
        public void failure(TwitterException e) {
            Log.i(TAG, "Can't create twitter guest session");
        }
    };

    @Override
    public void onCreate() {
        super.onCreate();
        initTwitter();
        startGuestSession();
    }

    private void initTwitter() {
        TwitterAuthConfig authConfig =
                new TwitterAuthConfig(getString(R.string.twitter_api_key),
                        getString(R.string.twitter_api_secret));

        Fabric.with(this, new Twitter(authConfig));
    }

    private void startGuestSession() {
        TwitterCore.getInstance().logInGuest(mGuestLoginCallback);
    }

    public void setGuestSession(AppSession guestSession) {
        mGuestSession = guestSession;
    }

    public AppSession getGuestSession(){
        return mGuestSession;
    }
}
