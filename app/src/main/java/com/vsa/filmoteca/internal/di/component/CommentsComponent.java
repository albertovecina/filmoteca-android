package com.vsa.filmoteca.internal.di.component;

import com.vsa.filmoteca.internal.di.PerActivity;
import com.vsa.filmoteca.internal.di.module.CommentsModule;
import com.vsa.filmoteca.view.activity.CommentsActivity;

import dagger.Component;

/**
 * Created by albertovecinasanchez on 18/7/16.
 */

@PerActivity
@Component(dependencies = ApplicationComponent.class, modules = CommentsModule.class)
public interface CommentsComponent {

    void inject(CommentsActivity commentsActivity);

}
