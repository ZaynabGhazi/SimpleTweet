package com.codepath.apps.restclienttemplate;

import android.content.Context;

import com.codepath.asynchttpclient.RequestParams;
import com.codepath.asynchttpclient.callback.JsonHttpResponseHandler;
import com.codepath.oauth.OAuthBaseClient;
import com.github.scribejava.apis.FlickrApi;
import com.github.scribejava.apis.TwitterApi;
import com.github.scribejava.core.builder.api.BaseApi;

/*
 * This is the object responsible for communicating with the REST API.
 */

public class TwitterClient extends OAuthBaseClient {
    public static final BaseApi REST_API_INSTANCE = TwitterApi.instance();
    public static final String REST_URL = "https://api.twitter.com/1.1";
    public static final String REST_CONSUMER_KEY = BuildConfig.CONSUMER_KEY;
    public static final String REST_CONSUMER_SECRET = BuildConfig.CONSUMER_SECRET;

    // Landing page to indicate the OAuth flow worked in case Chrome for Android 25+ blocks navigation back to the app.
    public static final String FALLBACK_URL = "https://codepath.github.io/android-rest-client-template/success.html";

    // See https://developer.chrome.com/multidevice/android/intents
    public static final String REST_CALLBACK_URL_TEMPLATE = "intent://%s#Intent;action=android.intent.action.VIEW;scheme=%s;package=%s;S.browser_fallback_url=%s;end";

    public TwitterClient(Context context) {
        super(context, REST_API_INSTANCE,
                REST_URL,
                REST_CONSUMER_KEY,
                REST_CONSUMER_SECRET,
                null,  // OAuth2 scope, null for OAuth1
                String.format(REST_CALLBACK_URL_TEMPLATE, context.getString(R.string.intent_host),
                        context.getString(R.string.intent_scheme), context.getPackageName(), FALLBACK_URL));
    }

    //fetch homepage
    public void getHomeTimeline(JsonHttpResponseHandler handler) {
        String apiUrl = getApiUrl("statuses/home_timeline.json");
        RequestParams params = new RequestParams();
        params.put("count", 25);
        params.put("since_id", 1);
        client.get(apiUrl, params, handler);
    }

    //post tweet
    public void publishTweet(String body, JsonHttpResponseHandler handler) {
        String apiUrl = getApiUrl("statuses/update.json");
        RequestParams params = new RequestParams();
        params.put("status", body);
        //params type tbchecked
        client.post(apiUrl, params, "", handler);
    }

    public void getNextPageOftweets(JsonHttpResponseHandler handler, Long maxId) {
        String apiUrl = getApiUrl("statuses/home_timeline.json");
        RequestParams params = new RequestParams();
        params.put("count", 25);
        params.put("max_id", maxId);
        client.get(apiUrl, params, handler);
    }

    //like tweet
    public void likeTweet(JsonHttpResponseHandler handler, Long id) {
        String apiUrl = getApiUrl("favorites/create.json");
        RequestParams params = new RequestParams();
        params.put("id", id);
        client.post(apiUrl, params, "", handler);
    }

    //unlike tweet
    public void unlikeTweet(JsonHttpResponseHandler handler, Long id) {
        String apiUrl = getApiUrl("favorites/destroy.json");
        RequestParams params = new RequestParams();
        params.put("id", id);
        client.post(apiUrl, params, "", handler);
    }

    //retweet
    public void retweet(JsonHttpResponseHandler handler, Long id) {
        String apiUrl = getApiUrl("statuses/retweet.json");
        RequestParams params = new RequestParams();
        params.put("id", id);
        client.post(apiUrl, params, "", handler);
    }

    //see retweets of tweet
    public void see_retweet(JsonHttpResponseHandler handler, Long id) {
        String apiUrl = getApiUrl("statuses/retweets.json");
        RequestParams params = new RequestParams();
        params.put("id", id);
        params.put("count", 5);
        client.get(apiUrl, params, handler);
    }

}
