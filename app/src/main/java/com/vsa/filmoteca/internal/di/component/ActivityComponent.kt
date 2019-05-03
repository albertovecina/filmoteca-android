package com.vsa.filmoteca.internal.di.component

import com.vsa.filmoteca.internal.di.PerActivity
import com.vsa.filmoteca.internal.di.module.ActivityModule
import com.vsa.filmoteca.view.activity.CommentsActivity
import dagger.Subcomponent

/**
 * Created by Alberto Vecina Sánchez on 2019-05-02.
 */
@PerActivity
@Subcomponent(modules = [ActivityModule::class])
interface ActivityComponent {

    fun inject(commentsActivity: CommentsActivity)

}