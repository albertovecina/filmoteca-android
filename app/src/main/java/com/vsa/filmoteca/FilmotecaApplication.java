package com.vsa.filmoteca;

import android.app.Application;
import android.content.pm.ApplicationInfo;
import android.content.pm.PackageManager;
import android.os.Bundle;
import android.util.Log;

import com.crashlytics.android.Crashlytics;
import com.twitter.sdk.android.Twitter;
import com.twitter.sdk.android.core.AppSession;
import com.twitter.sdk.android.core.TwitterAuthConfig;
import com.vsa.filmoteca.data.repository.TwitterDataRepository;
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

    private TwitterDataRepository mRepository = new TwitterDataRepository();

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
        try {
            ApplicationInfo applicationInfo = getPackageManager().getApplicationInfo(getPackageName(), PackageManager.GET_META_DATA);
            Bundle bundle = applicationInfo.metaData;
            String apiKey = bundle.getString("io.twitter.ApiKey");
            String apiSecret = bundle.getString("io.twitter.ApiSecret");
            TwitterAuthConfig authConfig =
                    new TwitterAuthConfig(apiKey,
                            apiSecret);

            Fabric.with(this, new Twitter(authConfig), new Crashlytics());
        } catch (PackageManager.NameNotFoundException e) {
            Log.e(TAG, "Failed to load meta-data, NameNotFound: " + e.getMessage());
        } catch (NullPointerException e) {
            Log.e(TAG, "Failed to load meta-data, NullPointer: " + e.getMessage());
        }

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
