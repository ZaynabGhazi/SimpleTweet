package com.codepath.apps.restclienttemplate.models;

import android.provider.ContactsContract;
import android.text.format.DateUtils;
import android.util.Log;

import androidx.room.ColumnInfo;
import androidx.room.Embedded;

import androidx.room.Entity;
import androidx.room.ForeignKey;
import androidx.room.Ignore;
import androidx.room.PrimaryKey;

import com.codepath.apps.restclienttemplate.TimeFormatter;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;
import org.parceler.Parcel;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Locale;

import static android.text.TextUtils.isEmpty;

@Parcel
@Entity(foreignKeys = @ForeignKey(entity = User.class, parentColumns = "id", childColumns = "userid"))
public class Tweet {
    // Define database columns and associated fields
    @PrimaryKey
    @ColumnInfo
    Long id;

    @ColumnInfo
    String createdAt;

    @ColumnInfo
    String body;

    @Ignore
    User user;

    @ColumnInfo
    Long userid;

    @ColumnInfo
    String body_image_url;

    int favorite_count;

    int retweet_count;

    boolean isFavorite;

    public Tweet() {
    }

    // Add a constructor that creates an object from the JSON response
    public static Tweet fromJson(JSONObject object) {
        Tweet tweet = new Tweet();
        try {
            tweet.id = object.getLong("id");
            tweet.user = User.parseJSON(object.getJSONObject("user"));
            tweet.createdAt = object.getString("created_at");
            tweet.body = object.getString("text");
            tweet.userid = tweet.user.id;
            tweet.favorite_count = object.getInt("favorite_count");
            tweet.retweet_count = object.getInt("retweet_count");
            tweet.isFavorite = false;

            //String url = object.getJSONObject("entities").getJSONArray("media").getJSONObject(0).getString("media_url_https");
            //Log.w("url fetched", url);
            String url = "";
            if (object.getJSONObject("entities").has("media")) {
                url += object.getJSONObject("entities").getJSONArray("media").getJSONObject(0).getString("media_url_https");
            }
            if (!isEmpty(url)) {
                tweet.body_image_url = url;
                Log.i("MEDIA", tweet.body_image_url);
            }
        } catch (JSONException e) {
            e.printStackTrace();
        }
        return tweet;
    }

    public boolean isFavorite() {
        return isFavorite;
    }

    public void setFavorite(boolean favorite) {
        isFavorite = favorite;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }


    public String getTimestamp() {
        return TimeFormatter.getTimeDifference(createdAt);
    }

    public void setTimestamp(String timestamp) {
        this.createdAt = timestamp;
    }

    public String getBody() {
        return body;
    }

    public String getBody_image_url() {
        return body_image_url;
    }

    public void setBody(String body) {
        this.body = body;
    }

    public User getUser() {
        return user;
    }

    public void setUser(User user) {
        this.user = user;
    }

    public String getCreatedAt() {
        return createdAt;
    }

    public Long getUserid() {
        return userid;
    }

    public int getFavorite_count() {
        return favorite_count;
    }

    public int getRetweet_count() {
        return retweet_count;
    }

    public static ArrayList<Tweet> fromJson(JSONArray jsonArray) {
        ArrayList<Tweet> tweets = new ArrayList<Tweet>(jsonArray.length());

        for (int i = 0; i < jsonArray.length(); i++) {
            JSONObject tweetJson = null;
            try {
                tweetJson = jsonArray.getJSONObject(i);
            } catch (Exception e) {
                e.printStackTrace();
                continue;
            }

            Tweet tweet = fromJson(tweetJson);
            tweets.add(tweet);
        }

        return tweets;
    }

}