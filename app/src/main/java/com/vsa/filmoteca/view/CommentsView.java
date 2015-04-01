package com.vsa.filmoteca.view;

import android.content.Context;

import com.twitter.sdk.android.core.models.Tweet;

import java.util.List;

/**
 * Created by seldon on 31/03/15.
 */
public interface CommentsView {

    public void showLoginButton();
    public void showTweetEditor();
    public void showProgressDialog();
    public void hideProgressDialog();
    public void setMaxTweetLength(int length);
    public void showCharactersLeft(String charactersLeft);
    public void showTweets(List<Tweet> tweetList);
    public void showProfileImage(String url);
    public void showUserName(String userName);
    public void showUserDescription(String userDescription);
    public void showErrorEmptyMessage();
    public void showErrorCantLoadTweets();
    public void showErrorCantLogin();
    public void showErrorCantPostTweet();
    public void addTweet(Tweet tweet);
    public Context getContext();
    public void onBackPressed();

}
