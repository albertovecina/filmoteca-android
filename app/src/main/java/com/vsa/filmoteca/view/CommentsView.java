package com.vsa.filmoteca.view;

import android.content.Context;

import com.twitter.sdk.android.core.TwitterSession;
import com.twitter.sdk.android.core.models.Tweet;

import java.util.List;

import rx.Observable;

/**
 * Created by seldon on 31/03/15.
 */
public interface CommentsView {

    void showLoginButton();

    void showTweetEditor();

    void showProgressDialog();

    void hideProgressDialog();

    void setMaxTweetLength(int length);

    String getMessage();

    void showCharactersLeft(String charactersLeft);

    void showTweets(List<Tweet> tweetList);

    void showProfileImage(String url);

    void showUserName(String userName);

    void showUserDescription(String userDescription);

    void showErrorEmptyMessage();

    void showErrorCantLoadTweets();

    void showErrorCantLogin();

    void showErrorGuestSession();

    void showErrorCantPostTweet();

    void addTweet(Tweet tweet);

    void onBackPressed();

    Context getContext();

    Observable<TwitterSession> twitterSession();

}
