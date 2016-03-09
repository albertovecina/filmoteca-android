package com.vsa.filmoteca.data.repository.ws;

import com.twitter.sdk.android.core.Session;
import com.twitter.sdk.android.core.TwitterApiClient;
import com.twitter.sdk.android.core.models.Search;
import com.twitter.sdk.android.core.models.Tweet;
import com.twitter.sdk.android.core.models.User;
import com.twitter.sdk.android.core.services.params.Geocode;

import retrofit.http.EncodedQuery;
import retrofit.http.Field;
import retrofit.http.FormUrlEncoded;
import retrofit.http.GET;
import retrofit.http.POST;
import retrofit.http.Query;
import rx.Observable;

/**
 * Created by albertovecinasanchez on 14/3/16.
 */
public class CommentsTwitterClient extends TwitterApiClient {


    public CommentsTwitterClient(Session session) {
        super(session);
    }

    /**
     * Provide CustomService with defined endpoints
     */
    public CommentsTwitterInterface getTwitterInterface() {
        return getService(CommentsTwitterInterface.class);
    }

    public interface CommentsTwitterInterface {

        @GET("/1.1/search/tweets.json")
        Observable<Search> tweets(@Query("q") String var1, @EncodedQuery("geocode") Geocode var2, @Query("lang") String var3, @Query("locale") String var4, @Query("result_type") String var5, @Query("count") Integer var6, @Query("until") String var7, @Query("since_id") Long var8, @Query("max_id") Long var9, @Query("include_entities") Boolean var10);

        @FormUrlEncoded
        @POST("/1.1/statuses/update.json")
        Observable<Tweet> update(@Field("status") String var1, @Field("in_reply_to_status_id") Long var2, @Field("possibly_sensitive") Boolean var3, @Field("lat") Double var4, @Field("long") Double var5, @Field("place_id") String var6, @Field("display_cooridnates") Boolean var7, @Field("trim_user") Boolean var8);

        @GET("/1.1/account/verify_credentials.json")
        Observable<User> verifyCredentials(@Query("include_entities") Boolean var1, @Query("skip_status") Boolean var2);

    }


}
