package com.vsa.filmoteca.model.event.comments;

import com.twitter.sdk.android.core.models.Tweet;

import java.util.List;

/**
 * Created by albertovecinasanchez on 12/12/15.
 */
public class EventOnTweetsLoaded {

    private List<Tweet> tweets;

    public EventOnTweetsLoaded(List<Tweet> tweets) {
        this.tweets = tweets;
    }

    public List<Tweet> getTweets() {
        return tweets;
    }

}
