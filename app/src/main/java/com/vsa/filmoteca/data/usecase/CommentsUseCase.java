package com.vsa.filmoteca.data.usecase;

import com.twitter.sdk.android.core.Session;
import com.twitter.sdk.android.core.TwitterSession;
import com.twitter.sdk.android.core.models.Search;
import com.twitter.sdk.android.core.models.Tweet;
import com.twitter.sdk.android.core.models.User;
import com.vsa.filmoteca.data.repository.TwitterDataRepository;

import javax.inject.Inject;

import rx.Observable;

/**
 * Created by albertovecinasanchez on 14/3/16.
 */
public class CommentsUseCase {

    private TwitterDataRepository mRepository;

    @Inject
    public CommentsUseCase(TwitterDataRepository repository) {
        this.mRepository = repository;
    }

    public Session getActiveSession() {
        return mRepository.getActiveSession();
    }

    public void closeActiveSession() {
        mRepository.closeActiveSession();
    }

    public Observable<Search> tweets(TwitterSession session, String hashTag) {
        return mRepository.tweets(session, hashTag);
    }

    public Observable<Tweet> update(Session session, String hashTag, String message) {
        return mRepository.update(session, hashTag, message);
    }

    public Observable<User> verifyCredentials(Session session) {
        return mRepository.verifyCredentials(session);
    }

}
