package com.vsa.filmoteca.internal.di.component;

import com.vsa.filmoteca.internal.di.PerActivity;
import com.vsa.filmoteca.internal.di.module.MoviesListModule;
import com.vsa.filmoteca.view.activity.MoviesListActivity;

import dagger.Component;

/**
 * Created by albertovecinasanchez on 18/7/16.
 */

@PerActivity
@Component(dependencies = ApplicationComponent.class, modules = MoviesListModule.class)
public interface MoviesListComponent {

    void inject(MoviesListActivity moviesListActivity);

}
