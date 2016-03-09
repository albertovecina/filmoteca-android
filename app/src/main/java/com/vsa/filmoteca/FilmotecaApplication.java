package com.vsa.filmoteca;

import android.app.Application;

import com.twitter.sdk.android.Twitter;
import com.twitter.sdk.android.core.AppSession;
import com.twitter.sdk.android.core.TwitterAuthConfig;
import com.vsa.filmoteca.data.repository.TwitterRepository;
import com.vsa.filmoteca.internal.di.component.DaggerMainComponent;
import com.vsa.filmoteca.internal.di.component.MainComponent;

import io.fabric.sdk.android.Fabric;
import rx.functions.Action1;


/**
 * Created by seldon on 31/03/15.
 */
public class FilmotecaApplication extends Application implements Action1<AppSession> {

    private static final String TAG = FilmotecaApplication.class.getSimpleName();

    private static FilmotecaApplication sApplication;

    private AppSession mGuestSession;

    private TwitterRepository mRepository = new TwitterRepository();

    private MainComponent mMainComponent;

    @Override
    public void onCreate() {
        super.onCreate();
        sApplication = this;
        mMainComponent = DaggerMainComponent.create();
        initTwitter();
        startGuestSession();
    }

    @Override
    public void call(AppSession appSession) {
        mGuestSession = appSession;
    }

    private void initTwitter() {
        TwitterAuthConfig authConfig =
                new TwitterAuthConfig(getString(R.string.twitter_api_key),
                        getString(R.string.twitter_api_secret));

        Fabric.with(this, new Twitter(authConfig));
    }

    private void startGuestSession() {
        mRepository.guestLogin().subscribe();
    }

    public MainComponent getMainComponent() {
        return mMainComponent;
    }

    public static FilmotecaApplication getInstance() {
        return sApplication;
    }

}
