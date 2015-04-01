package com.vsa.filmoteca.presenter;

import android.view.MenuItem;

import com.twitter.sdk.android.core.identity.TwitterLoginButton;

/**
 * Created by seldon on 31/03/15.
 */
public interface CommentsPresenter {

    public void onResume(String movieTitle);
    public void onPause();
    public boolean onOptionsItemSelected(MenuItem item);
    public void setLoginCallBack(TwitterLoginButton twitterLoginButton);
    public void onPublishTweet(String message);
    public void onTweetTextChanged(CharSequence message);
    public void closeSession();

}
