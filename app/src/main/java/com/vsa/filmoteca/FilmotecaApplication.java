package com.vsa.filmoteca;

import android.app.Application;

import com.twitter.sdk.android.Twitter;
import com.twitter.sdk.android.core.AppSession;
import com.twitter.sdk.android.core.TwitterAuthConfig;
import com.vsa.filmoteca.data.repository.TwitterRepository;
import com.vsa.filmoteca.internal.di.component.ApplicationComponent;
import com.vsa.filmoteca.internal.di.component.DaggerApplicationComponent;
import com.vsa.filmoteca.internal.di.module.ApplicationModule;
import com.vsa.filmoteca.presentation.utils.ConnectivityUtils;

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

    private ApplicationComponent mApplicationComponent;

    @Override
    public void onCreate() {
        super.onCreate();
        sApplication = this;
        initializeApplicationComponent();
        if (ConnectivityUtils.isInternetAvailable(this)) {
            initTwitter();
            startGuestSession();
        }
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

    private void initializeApplicationComponent() {
        mApplicationComponent = DaggerApplicationComponent.builder()
                .applicationModule(new ApplicationModule(this))
                .build();
    }

    private void startGuestSession() {
        mRepository.guestLogin().subscribe();
    }

    public ApplicationComponent getApplicationComponent() {
        return mApplicationComponent;
    }

    public static FilmotecaApplication getInstance() {
        return sApplication;
    }

}
