package com.vsa.filmoteca.view.component;

import android.content.Context;
import android.util.AttributeSet;

import com.twitter.sdk.android.core.Callback;
import com.twitter.sdk.android.core.Result;
import com.twitter.sdk.android.core.TwitterException;
import com.twitter.sdk.android.core.TwitterSession;
import com.twitter.sdk.android.core.identity.TwitterLoginButton;

import rx.Observable;
import rx.Subscriber;
import rx.android.schedulers.AndroidSchedulers;
import rx.schedulers.Schedulers;

/**
 * Created by albertovecinasanchez on 14/3/16.
 */
public class TwitterRxLoginButton extends TwitterLoginButton {
    public TwitterRxLoginButton(Context context) {
        super(context);
    }

    public TwitterRxLoginButton(Context context, AttributeSet attrs) {
        super(context, attrs);
    }

    public TwitterRxLoginButton(Context context, AttributeSet attrs, int defStyle) {
        super(context, attrs, defStyle);
    }

    public Observable<TwitterSession> twitterSession() {
        return Observable.create(new Observable.OnSubscribe<TwitterSession>() {
            @Override
            public void call(Subscriber<? super TwitterSession> subscriber) {
                setCallback(new Callback<TwitterSession>() {
                    @Override
                    public void success(Result<TwitterSession> result) {
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
}
