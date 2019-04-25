package com.vsa.filmoteca.data.repository.ws;

import com.twitter.sdk.android.core.TwitterApiClient;
import com.twitter.sdk.android.core.TwitterSession;
import com.twitter.sdk.android.core.internal.network.GuestAuthNetworkInterceptor;
import com.twitter.sdk.android.core.models.Search;
import com.twitter.sdk.android.core.models.Tweet;
import com.twitter.sdk.android.core.models.User;
import com.twitter.sdk.android.core.services.params.Geocode;

import okhttp3.OkHttpClient;
import okhttp3.logging.HttpLoggingInterceptor;
import retrofit2.Call;
import retrofit2.http.Field;
import retrofit2.http.FormUrlEncoded;
import retrofit2.http.GET;
import retrofit2.http.POST;
import retrofit2.http.Query;

/**
 * Created by albertovecinasanchez on 14/3/16.
 */
public class CommentsTwitterClient extends TwitterApiClient {

    private static OkHttpClient sTwitterOkHttpClient = getHttpClient();

    public CommentsTwitterClient() {
        super(sTwitterOkHttpClient);
    }

    public CommentsTwitterClient(TwitterSession session) {
        super(session);
    }

    public CommentsTwitterClient(TwitterSession session, OkHttpClient client) {
        super(session, client);
    }

    /**
     * Provide CustomService with defined endpoints
     */
    public CommentsTwitterInterface getTwitterInterface() {
        return getService(CommentsTwitterInterface.class);
    }

    public interface CommentsTwitterInterface {

        @GET("/1.1/search/tweets.json")
        Call<Search> tweets(@Query("q") String var1, @Query(value = "geocode", encoded = true) Geocode var2, @Query("lang") String var3, @Query("locale") String var4, @Query("result_type") String var5, @Query("count") Integer var6, @Query("until") String var7, @Query("since_id") Long var8, @Query("max_id") Long var9, @Query("include_entities") Boolean var10);

        @FormUrlEncoded
        @POST("/1.1/statuses/update.json")
        Call<Tweet> update(@Field("status") String var1, @Field("in_reply_to_status_id") Long var2, @Field("possibly_sensitive") Boolean var3, @Field("lat") Double var4, @Field("long") Double var5, @Field("place_id") String var6, @Field("display_cooridnates") Boolean var7, @Field("trim_user") Boolean var8);

        @GET("/1.1/account/verify_credentials.json")
        Call<User> verifyCredentials(@Query("include_entities") Boolean var1, @Query("skip_status") Boolean var2);

    }

    private static OkHttpClient getHttpClient() {
        HttpLoggingInterceptor interceptor = new HttpLoggingInterceptor();
        interceptor.setLevel(HttpLoggingInterceptor.Level.BODY);
        OkHttpClient client = new OkHttpClient.Builder().
                addInterceptor(interceptor).
                addNetworkInterceptor(new GuestAuthNetworkInterceptor())
                .build();
        return client;
    }


}
