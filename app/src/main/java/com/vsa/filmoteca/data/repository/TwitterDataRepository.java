package com.vsa.filmoteca.data.repository;

import com.twitter.sdk.android.core.Session;
import com.twitter.sdk.android.core.TwitterCore;
import com.twitter.sdk.android.core.TwitterSession;
import com.twitter.sdk.android.core.models.Search;
import com.twitter.sdk.android.core.models.Tweet;
import com.twitter.sdk.android.core.models.User;
import com.vsa.filmoteca.data.repository.ws.CommentsTwitterClient;
import com.vsa.filmoteca.presentation.utils.Constants;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import rx.Observable;
import rx.android.schedulers.AndroidSchedulers;
import rx.schedulers.Schedulers;

/**
 * Created by albertovecinasanchez on 14/3/16.
 */
public class TwitterDataRepository {

    public Session getActiveSession() {
        Session session = TwitterCore.getInstance().getSessionManager().getActiveSession();
        return session;
    }

    public void closeActiveSession() {
        TwitterCore.getInstance().getSessionManager().clearActiveSession();
    }

    public Observable<Search> tweets(TwitterSession session, String hashTag) {
        CommentsTwitterClient twitterApiClient = new CommentsTwitterClient(session);
        Call<Search> call = twitterApiClient.getTwitterInterface().tweets(
                Constants.HASHTAG_FILMOTECA + " AND " + hashTag,
                null,
                null,
                null,
                null,
                50,
                null,
                null,
                null,
                true);
        return Observable.create((Observable.OnSubscribe<Search>) subscriber -> {
            subscriber.onStart();
            call.enqueue(new Callback<Search>() {
                @Override
                public void onResponse(Call<Search> call1, Response<Search> response) {
                    subscriber.onNext(response.body());
                    subscriber.onCompleted();
                }

                @Override
                public void onFailure(Call<Search> call1, Throwable t) {
                    subscriber.onError(t);
                }
            });
        }).subscribeOn(Schedulers.newThread())
                .observeOn(AndroidSchedulers.mainThread());
    }

    public Observable<Tweet> update(Session session, String hashTag, String message) {
        CommentsTwitterClient twitterApiClient = new CommentsTwitterClient();
        Call<Tweet> call = twitterApiClient.getTwitterInterface().update(
                Constants.HASHTAG_FILMOTECA + " " + hashTag + " " + message,
                null,
                null,
                null,
                null,
                null,
                null,
                null);
        return Observable.create((Observable.OnSubscribe<Tweet>) subscriber -> {
            subscriber.onStart();
            call.enqueue(new Callback<Tweet>() {
                @Override
                public void onResponse(Call<Tweet> call1, Response<Tweet> response) {
                    subscriber.onNext(response.body());
                    subscriber.onCompleted();
                }

                @Override
                public void onFailure(Call<Tweet> call1, Throwable t) {
                    subscriber.onError(t);
                }
            });
        }).subscribeOn(Schedulers.newThread())
                .observeOn(AndroidSchedulers.mainThread());
    }

    public Observable<User> verifyCredentials(Session session) {
        CommentsTwitterClient twitterApiClient = new CommentsTwitterClient();
        Call<User> call = twitterApiClient.getTwitterInterface().verifyCredentials(true, true);
        return Observable.create((Observable.OnSubscribe<User>) subscriber -> {
            subscriber.onStart();
            call.enqueue(new Callback<User>() {
                @Override
                public void onResponse(Call<User> call1, Response<User> response) {
                    subscriber.onNext(response.body());
                    subscriber.onCompleted();
                }

                @Override
                public void onFailure(Call<User> call1, Throwable t) {
                    subscriber.onError(t);
                }
            });
        }).subscribeOn(Schedulers.newThread())
                .observeOn(AndroidSchedulers.mainThread());
    }

}
