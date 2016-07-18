package com.vsa.filmoteca.internal.di.component;

import com.vsa.filmoteca.internal.di.PerWidget;
import com.vsa.filmoteca.internal.di.module.MoviesWidgetModule;
import com.vsa.filmoteca.view.widget.EventsWidget;

import dagger.Component;

/**
 * Created by albertovecinasanchez on 18/7/16.
 */

@PerWidget
@Component(dependencies = ApplicationComponent.class, modules = MoviesWidgetModule.class)
public interface MoviesWidgetComponent {

    void inject(EventsWidget eventsWidget);

}
