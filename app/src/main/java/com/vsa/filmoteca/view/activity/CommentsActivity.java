package com.vsa.filmoteca.view.activity;

import android.app.ProgressDialog;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.text.Editable;
import android.text.InputFilter;
import android.text.TextWatcher;
import android.view.MenuItem;
import android.view.View;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import com.squareup.picasso.Picasso;
import com.twitter.sdk.android.core.TwitterSession;
import com.twitter.sdk.android.core.models.Tweet;
import com.twitter.sdk.android.tweetui.TweetView;
import com.twitter.sdk.android.tweetui.TweetViewAdapter;
import com.vsa.filmoteca.R;
import com.vsa.filmoteca.internal.di.component.ApplicationComponent;
import com.vsa.filmoteca.internal.di.component.DaggerCommentsComponent;
import com.vsa.filmoteca.internal.di.module.CommentsModule;
import com.vsa.filmoteca.presentation.comments.CommentsPresenter;
import com.vsa.filmoteca.view.CommentsView;
import com.vsa.filmoteca.view.component.TwitterRxLoginButton;

import java.util.ArrayList;
import java.util.List;

import javax.inject.Inject;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;
import rx.Observable;

public class CommentsActivity extends BaseActivity implements CommentsView, TextWatcher {

    public static final String EXTRA_TITLE = "extra_title";

    @BindView(R.id.imageview_profile_image)
    ImageView mImageViewProfileImage;
    @BindView(R.id.textview_user_name)
    TextView mTextViewUserName;
    @BindView(R.id.textview_user_description)
    TextView mTextViewUserDescription;
    @BindView(R.id.textview_char_counter)
    TextView mTextViewCharCounter;
    @BindView(R.id.listview_tweets)
    ListView mListViewTweets;
    @BindView(R.id.edittext_twitter_message)
    EditText mEditTextTwitterMessage;
    @BindView(R.id.login_button)
    TwitterRxLoginButton mLoginButton;
    @BindView(R.id.layout_tweet_editor)
    LinearLayout mLayoutTweetEditor;

    @Inject
    CommentsPresenter mPresenter;
    private ProgressDialog mProgressDialog;
    private TweetViewAdapter<TweetView> mTweetViewAdapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_comments);
        ButterKnife.bind(this);
        initViews();
        mPresenter.onCreate(getIntent().getStringExtra(EXTRA_TITLE));
    }

    @Override
    protected void initializeInjector(ApplicationComponent applicationComponent) {
        DaggerCommentsComponent.builder()
                .applicationComponent(applicationComponent)
                .commentsModule(new CommentsModule())
                .build()
                .inject(this);
    }

    @Override
    protected void onResume() {
        super.onResume();
        mPresenter.onResume();
    }

    @Override
    protected void onPause() {
        super.onPause();
        mPresenter.onPause();
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        mPresenter.onDestroy();
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            case android.R.id.home:
                mPresenter.onHomeAsUpClick();
                return true;
            default:
                return false;
        }
    }

    private void initViews() {
        mPresenter.setView(this);
        mProgressDialog = ProgressDialog.show(this, "",
                getString(R.string.loading), true, false);
        mTweetViewAdapter = new TweetViewAdapter(this);
        mListViewTweets.setAdapter(mTweetViewAdapter);
        mEditTextTwitterMessage.addTextChangedListener(this);
    }

    @OnClick(R.id.button_publish_tweet)
    public void onPublishButtonClick() {
        mPresenter.onPublishButtonClick();
        mEditTextTwitterMessage.setText("");
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        mLoginButton.onActivityResult(requestCode, resultCode,
                data);
    }

    @Override
    public void showLoginButton() {
        mLoginButton.setVisibility(View.VISIBLE);
        mLayoutTweetEditor.setVisibility(View.GONE);
    }

    @Override
    public void showTweetEditor() {
        mLoginButton.setVisibility(View.GONE);
        mLayoutTweetEditor.setVisibility(View.VISIBLE);
    }

    @Override
    public void showProgressDialog() {
        mProgressDialog.show();
    }

    @Override
    public void hideProgressDialog() {
        mProgressDialog.dismiss();
    }

    @Override
    public void setMaxTweetLength(int length) {
        InputFilter[] filterArray = {new InputFilter.LengthFilter(length)};
        mEditTextTwitterMessage.setFilters(filterArray);
    }

    @Override
    public String getMessage() {
        return mEditTextTwitterMessage.getText().toString();
    }

    @Override
    public void showCharactersLeft(String charactersLeft) {
        mTextViewCharCounter.setText(charactersLeft);
    }

    @Override
    public void showTweets(List<Tweet> tweetList) {
        mTweetViewAdapter.setTweets(tweetList);
    }

    @Override
    public void showProfileImage(String url) {
        Picasso.with(this).load(url).into(mImageViewProfileImage);
    }

    @Override
    public void showUserName(String userName) {
        mTextViewUserName.setText(userName);
    }

    @Override
    public void showUserDescription(String userDescription) {
        mTextViewUserDescription.setText(userDescription);
    }

    @Override
    public void addTweet(Tweet tweet) {
        List<Tweet> tweetList = mTweetViewAdapter.getTweets();
        List<Tweet> auxTweetList = new ArrayList<>(tweetList);
        tweetList.clear();
        tweetList.add(0, tweet);
        tweetList.addAll(auxTweetList);
        mTweetViewAdapter.notifyDataSetChanged();
    }

    public void showErrorEmptyMessage() {
        mEditTextTwitterMessage.setError(getString(R.string.error_empty_message));
    }

    public void showErrorCantLoadTweets() {
        Toast.makeText(this, getString(R.string.error_cant_load_tweets), Toast.LENGTH_LONG).show();
    }

    public void showErrorCantLogin() {
        Toast.makeText(this, getString(R.string.error_cant_login), Toast.LENGTH_LONG).show();
    }

    public void showErrorCantPostTweet() {
        Toast.makeText(this, getString(R.string.error_cant_post_tweet), Toast.LENGTH_LONG).show();
    }

    @Override
    public Context getContext() {
        return this;
    }

    @Override
    public Observable<TwitterSession> twitterSession() {
        return mLoginButton.twitterSession();
    }

    @Override
    public void beforeTextChanged(CharSequence s, int start, int count, int after) {

    }

    @Override
    public void onTextChanged(CharSequence s, int start, int before, int count) {
        mPresenter.onTweetTextChanged(s);
    }

    @Override
    public void afterTextChanged(Editable s) {

    }

    @OnClick(R.id.imagebutton_close_session)
    public void onCloseSessionButtonPressed() {
        mPresenter.closeSession();
    }

}
