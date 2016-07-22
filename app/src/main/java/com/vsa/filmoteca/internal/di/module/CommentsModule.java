package com.vsa.filmoteca.internal.di.module;

import com.vsa.filmoteca.data.repository.TwitterDataRepository;
import com.vsa.filmoteca.internal.di.PerActivity;
import com.vsa.filmoteca.presentation.comments.CommentsPresenter;
import com.vsa.filmoteca.data.usecase.CommentsUseCase;

import dagger.Module;
import dagger.Provides;

/**
 * Created by albertovecinasanchez on 18/7/16.
 */

@Module
public class CommentsModule {

    @Provides
    @PerActivity
    public CommentsUseCase provideCommentsUseCase(TwitterDataRepository twitterDataRepository) {
        return new CommentsUseCase(twitterDataRepository);
    }

    @Provides
    @PerActivity
    public CommentsPresenter provideCommentsPresenter(CommentsUseCase commentsUseCase) {
        return new CommentsPresenter(commentsUseCase);
    }

}
