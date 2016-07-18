package com.vsa.filmoteca.internal.di.component;

import android.app.Application;

import com.vsa.filmoteca.data.repository.MoviesRepository;
import com.vsa.filmoteca.data.repository.TwitterRepository;
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

    Application getApplication();

    MoviesRepository getMoviesRepository();

    TwitterRepository getTwitterRepository();

}
