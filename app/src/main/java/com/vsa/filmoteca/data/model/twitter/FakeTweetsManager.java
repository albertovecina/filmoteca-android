package com.vsa.filmoteca.data.model.twitter;

import com.twitter.sdk.android.core.models.Tweet;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * Created by seldon on 1/04/15.
 */
public class FakeTweetsManager {

    private static Map<String, List<Tweet>> mFakeTweets;

    private static void init() {
        if (mFakeTweets == null) {
            mFakeTweets = new HashMap<>();
        }
    }

    synchronized public static void addFakeTweet(String hashTag, Tweet tweet) {
        init();
        List<Tweet> fakeTweetList = mFakeTweets.get(hashTag);
        if(fakeTweetList == null) {
            fakeTweetList = new ArrayList<>();
            mFakeTweets.put(hashTag, fakeTweetList);
        }
        fakeTweetList.add(tweet);
    }

    synchronized public static boolean removeFakeTweet(String hashTag, Tweet tweet) {
        init();
        List<Tweet> fakeTweetList = mFakeTweets.get(hashTag);
        if(fakeTweetList != null)
            return fakeTweetList.remove(tweet);
        return false;
    }

    synchronized public static boolean contains(String hashTag, Tweet tweet) {
        init();
        List<Tweet> fakeTweetList = mFakeTweets.get(hashTag);
        if(fakeTweetList != null)
            return fakeTweetList.contains(tweet);
        return false;
    }

    public static List<Tweet> getFakeTweets(String hashTag) {
        init();
        List<Tweet> fakeTweetList = mFakeTweets.get(hashTag);
        if(fakeTweetList == null) {
            fakeTweetList = new ArrayList<>();
            mFakeTweets.put(hashTag, fakeTweetList);
        }
        return fakeTweetList;
    }

}
