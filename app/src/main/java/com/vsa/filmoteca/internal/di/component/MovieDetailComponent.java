package com.vsa.filmoteca.internal.di.component;

import com.vsa.filmoteca.internal.di.PerActivity;
import com.vsa.filmoteca.internal.di.module.MovieDetailModule;
import com.vsa.filmoteca.view.activity.DetailActivity;

import dagger.Component;

/**
 * Created by albertovecinasanchez on 18/7/16.
 */

@PerActivity
@Component(dependencies = ApplicationComponent.class, modules = MovieDetailModule.class)
public interface MovieDetailComponent {

    void inject(DetailActivity detailActivity);

}
