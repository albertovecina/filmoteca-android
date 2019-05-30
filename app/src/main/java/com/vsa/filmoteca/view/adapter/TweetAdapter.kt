package com.vsa.filmoteca.view.adapter

import android.content.Context
import android.view.View
import android.view.ViewGroup
import android.widget.BaseAdapter

import com.twitter.sdk.android.core.models.Tweet
import com.twitter.sdk.android.tweetui.TweetView
import com.vsa.filmoteca.R

import butterknife.BindView
import butterknife.ButterKnife

/**
 * Created by Alberto Vecina Sánchez on 29/7/18.
 */
class TweetAdapter(private val mContext: Context, val tweets: List<Tweet>) : BaseAdapter() {

    override fun getCount(): Int {
        return tweets.size
    }

    override fun getItem(i: Int): Tweet {
        return tweets[i]
    }

    override fun getItemId(i: Int): Long {
        return tweets[i].id
    }

    override fun getView(i: Int, view: View, viewGroup: ViewGroup): View {
        return TweetView(mContext, tweets[i])
    }

    inner class ViewHolder internal constructor(view: View) {

        @BindView(R.id.tweetview)
        public lateinit var tweetView: TweetView

        init {
            ButterKnife.bind(this, view)
        }

        internal fun setTweet(tweet: Tweet) {
            tweetView.tweet = tweet
        }

    }

}
