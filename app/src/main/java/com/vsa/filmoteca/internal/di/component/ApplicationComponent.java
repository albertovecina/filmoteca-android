package com.vsa.filmoteca.internal.di.component;

import android.app.Application;

import com.vsa.filmoteca.data.repository.TwitterDataRepository;
import com.vsa.filmoteca.data.repository.MoviesPersistanceRepository;
import com.vsa.filmoteca.internal.di.module.ActivityModule;
import com.vsa.filmoteca.internal.di.module.ApplicationModule;
import com.vsa.filmoteca.view.activity.BaseActivity;

import javax.inject.Singleton;

import dagger.Component;

/**
 * Created by albertovecinasanchez on 18/7/16.
 */

@Singleton
@Component(modules = ApplicationModule.class)
public interface ApplicationComponent {

    void injects(BaseActivity baseActivity);

    ActivityComponent plusActivityComponent(ActivityModule activityModule);

    Application getApplication();

    MoviesPersistanceRepository getMoviesPersistanceRepository();

    TwitterDataRepository getTwitterDataRepository();

}
