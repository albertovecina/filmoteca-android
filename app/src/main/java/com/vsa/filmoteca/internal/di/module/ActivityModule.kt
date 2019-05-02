package com.vsa.filmoteca.internal.di.module

import android.content.Context
import com.vsa.filmoteca.data.repository.TwitterDataRepository
import com.vsa.filmoteca.data.usecase.CommentsUseCase
import com.vsa.filmoteca.internal.di.PerActivity
import com.vsa.filmoteca.presentation.comments.CommentsPresenter
import dagger.Module
import dagger.Provides

/**
 * Created by Alberto Vecina Sánchez on 2019-05-02.
 */
@Module
class ActivityModule(private val context: Context) {

    @Provides
    @PerActivity
    fun provideCommentsUseCase(twitterDataRepository: TwitterDataRepository): CommentsUseCase {
        return CommentsUseCase(twitterDataRepository)
    }

    @Provides
    @PerActivity
    fun provideCommentsPresenter(commentsUseCase: CommentsUseCase): CommentsPresenter {
        return CommentsPresenter(commentsUseCase)
    }

}