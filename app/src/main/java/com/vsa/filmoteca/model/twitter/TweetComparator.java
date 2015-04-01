package com.vsa.filmoteca.model.twitter;

import com.twitter.sdk.android.core.models.Tweet;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Comparator;
import java.util.Date;
import java.util.Locale;

/**
 * Created by seldon on 1/04/15.
 */
public class TweetComparator implements Comparator<Tweet> {

    private static final String TWEET_DATE_PATTERN = "EEE MMM dd HH:mm:ss ZZZZZ yyyy";
    private static final SimpleDateFormat mTweetDateFormat = new SimpleDateFormat(TWEET_DATE_PATTERN, Locale.ENGLISH);

    @Override
    public int compare(Tweet lhs, Tweet rhs) {
        try {
            Date lhsDate = mTweetDateFormat.parse(lhs.createdAt);
            Date rhsDate = mTweetDateFormat.parse(rhs.createdAt);
            return lhsDate.compareTo(rhsDate) * -1;
        } catch (ParseException e) {
            e.printStackTrace();
        }

        return 0;
    }
}
