package com.vsa.filmoteca.internal.di.component;

import com.vsa.filmoteca.internal.di.module.MainModule;
import com.vsa.filmoteca.view.activity.CommentsActivity;
import com.vsa.filmoteca.view.activity.DetailActivity;
import com.vsa.filmoteca.view.activity.MainActivity;
import com.vsa.filmoteca.view.widget.EventsWidget;

import javax.inject.Singleton;

import dagger.Component;

/**
 * Created by albertovecinasanchez on 8/3/16.
 */
@Singleton
@Component(modules = MainModule.class)
public interface MainComponent {

    void inject(MainActivity mainActivity);

    void inject(DetailActivity detailActivity);

    void inject(CommentsActivity commentsActivity);

    void inject(EventsWidget eventsWidget);

}
