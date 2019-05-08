package com.vsa.filmoteca.view.activity

import android.content.Context
import android.content.Intent
import android.os.Bundle
import android.text.Editable
import android.text.InputFilter
import android.text.TextWatcher
import android.view.MenuItem
import android.view.View
import android.widget.Toast
import butterknife.ButterKnife
import butterknife.OnClick
import com.squareup.picasso.Picasso
import com.twitter.sdk.android.core.TwitterSession
import com.twitter.sdk.android.core.models.Tweet
import com.vsa.filmoteca.R
import com.vsa.filmoteca.internal.di.component.ApplicationComponent
import com.vsa.filmoteca.internal.di.module.ActivityModule
import com.vsa.filmoteca.presentation.comments.CommentsPresenter
import com.vsa.filmoteca.view.CommentsView
import com.vsa.filmoteca.view.adapter.TweetAdapter
import com.vsa.filmoteca.view.dialog.ProgressDialogManager
import kotlinx.android.synthetic.main.activity_comments.*
import rx.Observable
import java.util.*
import javax.inject.Inject

class CommentsActivity : BaseActivity(), CommentsView, TextWatcher {

    @Inject
    lateinit var presenter: CommentsPresenter
    private var mTweetViewAdapter: TweetAdapter? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_comments)
        ButterKnife.bind(this)
        initViews()
        presenter.onCreate(intent.getStringExtra(EXTRA_TITLE))
    }

    override fun initializeInjector(applicationComponent: ApplicationComponent) {
        applicationComponent
                .plusActivityComponent(ActivityModule(this))
                .inject(this)
    }

    override fun onResume() {
        super.onResume()
        presenter.onResume()
    }

    override fun onPause() {
        super.onPause()
        presenter.onPause()
    }

    override fun onDestroy() {
        super.onDestroy()
        presenter.onDestroy()
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        return when (item.itemId) {
            android.R.id.home -> {
                presenter.onHomeAsUpClick()
                true
            }
            else -> false
        }
    }

    private fun initViews() {
        //presenter.view = this
        editTextMessage.addTextChangedListener(this)
    }

    @OnClick(R.id.button_publish_tweet)
    fun onPublishButtonClick() {
        presenter.onPublishButtonClick()
        editTextMessage.setText("")
    }

    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        buttonLogin.onActivityResult(requestCode, resultCode,
                data)
    }

    override fun showLoginButton() {
        buttonLogin.visibility = View.VISIBLE
        wrapperTwitterUserInfo.visibility = View.GONE
    }

    override fun showTweetEditor() {
        buttonLogin.visibility = View.GONE
        wrapperTwitterUserInfo.visibility = View.VISIBLE
    }

    override fun showProgressDialog() {
        ProgressDialogManager.showProgressDialog(this, R.string.loading)
    }

    override fun hideProgressDialog() {
        ProgressDialogManager.hideProgressDialog()
    }

    override fun setMaxTweetLength(length: Int) {
        val filterArray = arrayOf<InputFilter>(InputFilter.LengthFilter(length))
        editTextMessage.filters = filterArray
    }

    override fun getMessage(): String {
        return editTextMessage.text.toString()
    }

    override fun showCharactersLeft(charactersLeft: String) {
        textViewCharCounter.text = charactersLeft
    }

    override fun showTweets(tweetList: List<Tweet>) {
        mTweetViewAdapter = TweetAdapter(this, tweetList)
        listViewTweets.adapter = mTweetViewAdapter
    }

    override fun showProfileImage(url: String) {
        Picasso.get().load(url).into(imageViewProfile)
    }

    override fun showUserName(userName: String) {
        textViewUserName.text = userName
    }

    override fun showUserDescription(userDescription: String) {
        textViewUserDescription.text = userDescription
    }

    override fun addTweet(tweet: Tweet) {
        val tweetList = mTweetViewAdapter!!.tweets
        val auxTweetList = ArrayList(tweetList)
        tweetList.clear()
        tweetList.add(0, tweet)
        tweetList.addAll(auxTweetList)
        mTweetViewAdapter!!.notifyDataSetChanged()
    }

    override fun showErrorEmptyMessage() {
        editTextMessage.error = getString(R.string.error_empty_message)
    }

    override fun showErrorCantLoadTweets() {
        Toast.makeText(this, R.string.error_cant_load_tweets, Toast.LENGTH_LONG).show()
    }

    override fun showErrorCantLogin() {
        Toast.makeText(this, R.string.error_cant_login, Toast.LENGTH_LONG).show()
    }

    override fun showErrorGuestSession() {
        Toast.makeText(this, R.string.error_cant_get_twitter_guest_session, Toast.LENGTH_LONG).show()
    }

    override fun showErrorVerifyCredentials() {
        Toast.makeText(this, R.string.error_cant_verify_credentials, Toast.LENGTH_LONG).show()
    }

    override fun showErrorCantPostTweet() {
        Toast.makeText(this, R.string.error_cant_post_tweet, Toast.LENGTH_LONG).show()
    }

    override fun getContext(): Context {
        return this
    }

    override fun twitterSession(): Observable<TwitterSession> {
        return buttonLogin.twitterSession()
    }

    override fun beforeTextChanged(s: CharSequence, start: Int, count: Int, after: Int) {

    }

    override fun onTextChanged(s: CharSequence, start: Int, before: Int, count: Int) {
        presenter.onTweetTextChanged(s)
    }

    override fun afterTextChanged(s: Editable) {

    }

    @OnClick(R.id.imagebutton_close_session)
    fun onCloseSessionButtonPressed() {
        presenter.closeSession()
    }

    companion object {

        private const val EXTRA_TITLE = "extra_title"

        fun open(context: Context, title: String) {
            val intent = Intent(context, CommentsActivity::class.java)
            intent.putExtra(EXTRA_TITLE, title)
            context.startActivity(intent)
        }
    }

}
