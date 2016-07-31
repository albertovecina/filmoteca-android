package com.vsa.filmoteca.data.repository;

import com.twitter.sdk.android.core.AppSession;
import com.twitter.sdk.android.core.Callback;
import com.twitter.sdk.android.core.Result;
import com.twitter.sdk.android.core.Session;
import com.twitter.sdk.android.core.TwitterCore;
import com.twitter.sdk.android.core.TwitterException;
import com.twitter.sdk.android.core.models.Search;
import com.twitter.sdk.android.core.models.Tweet;
import com.twitter.sdk.android.core.models.User;
import com.vsa.filmoteca.data.repository.ws.CommentsTwitterClient;
import com.vsa.filmoteca.presentation.utils.Constants;

import rx.Observable;
import rx.Subscriber;
import rx.android.schedulers.AndroidSchedulers;
import rx.schedulers.Schedulers;

/**
 * Created by albertovecinasanchez on 14/3/16.
 */
public class TwitterDataRepository {

    public Session getActiveSession() {
        Session session = TwitterCore.getInstance().getSessionManager().getActiveSession();
        if (session == null) {
            session = TwitterCore.getInstance().getAppSessionManager().getActiveSession();
        }
        return session;
    }

    public void closeActiveSession() {
        TwitterCore.getInstance().logOut();
    }

    public Observable<AppSession> guestLogin() {
        AppSession guestSession = TwitterCore.getInstance().getAppSessionManager().getActiveSession();
        if (guestSession != null)
            return Observable.just(guestSession);
        return Observable.create(new Observable.OnSubscribe<AppSession>() {
            @Override
            public void call(Subscriber<? super AppSession> subscriber) {
                TwitterCore.getInstance().logInGuest(new Callback<AppSession>() {
                    @Override
                    public void success(Result<AppSession> result) {
                        subscriber.onNext(result.data);
                        subscriber.onCompleted();
                    }

                    @Override
                    public void failure(TwitterException e) {
                        subscriber.onError(e);
                        subscriber.onCompleted();
                    }
                });
            }
        }).subscribeOn(Schedulers.newThread())
                .observeOn(AndroidSchedulers.mainThread());
    }

    public Observable<Search> tweets(Session session, String hashTag) {
        CommentsTwitterClient twitterApiClient = new CommentsTwitterClient(session);
        return twitterApiClient.getTwitterInterface().tweets(
                Constants.HASHTAG_FILMOTECA + " AND " + hashTag,
                null,
                null,
                null,
                null,
                50,
                null,
                null,
                null,
                true)
                .subscribeOn(Schedulers.newThread())
                .observeOn(AndroidSchedulers.mainThread());
    }

    public Observable<Tweet> update(Session session, String hashTag, String message) {
        CommentsTwitterClient twitterApiClient = new CommentsTwitterClient(session);
        return twitterApiClient.getTwitterInterface().update(
                Constants.HASHTAG_FILMOTECA + " " + hashTag + " " + message,
                null,
                null,
                null,
                null,
                null,
                null,
                null)
                .subscribeOn(Schedulers.newThread())
                .observeOn(AndroidSchedulers.mainThread());
    }

    public Observable<User> verifyCredentials(Session session) {
        CommentsTwitterClient twitterApiClient = new CommentsTwitterClient(session);
        return twitterApiClient.getTwitterInterface().verifyCredentials(true, true)
                .subscribeOn(Schedulers.newThread())
                .observeOn(AndroidSchedulers.mainThread());
    }

}
