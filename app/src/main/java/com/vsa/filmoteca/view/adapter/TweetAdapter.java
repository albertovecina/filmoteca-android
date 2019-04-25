package com.vsa.filmoteca.view.adapter;

import android.content.Context;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;

import com.twitter.sdk.android.core.models.Tweet;
import com.twitter.sdk.android.tweetui.TweetView;
import com.vsa.filmoteca.R;

import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;

/**
 * Created by Alberto Vecina Sánchez on 29/7/18.
 */
public class TweetAdapter extends BaseAdapter {

    private Context mContext;
    private List<Tweet> mTweets;

    public TweetAdapter(Context context, List<Tweet> tweets) {
        mContext = context;
        this.mTweets = tweets;
    }

    @Override
    public int getCount() {
        return mTweets.size();
    }

    @Override
    public Tweet getItem(int i) {
        return mTweets.get(i);
    }

    @Override
    public long getItemId(int i) {
        return mTweets.get(i).id;
    }

    @Override
    public View getView(int i, View view, ViewGroup viewGroup) {
        return new TweetView(mContext, mTweets.get(i));
    }

    public List<Tweet> getTweets() {
        return mTweets;
    }

    public class ViewHolder {

        @BindView(R.id.tweetview)
        TweetView mTweetView;

        ViewHolder(View view) {
            ButterKnife.bind(this, view);
        }

        void setTweet(Tweet tweet) {
            mTweetView.setTweet(tweet);
        }

    }

}
