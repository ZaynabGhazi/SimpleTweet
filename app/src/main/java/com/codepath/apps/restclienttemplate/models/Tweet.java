package com.codepath.apps.restclienttemplate.models;

import androidx.room.ColumnInfo;
import androidx.room.Embedded;

import androidx.room.Entity;
import androidx.room.PrimaryKey;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;

@Entity
public class Tweet {
    // Define database columns and associated fields
    @PrimaryKey
    @ColumnInfo
    Long id;
    @ColumnInfo
    String createdAt;
    @ColumnInfo
    String body;

    // Use @Embedded to keep the column entries as part of the same table while still
    // keeping the logical separation between the two objects.
    @Embedded
    User user;
    public Tweet(){}
    public Tweet(Long id, String createdAt, String body, User user) {
        this.id = id;
        this.createdAt= createdAt;
        this.body = body;
        this.user = user;
    }

    // Add a constructor that creates an object from the JSON response
    public static Tweet fromJson(JSONObject object){
        Tweet tweet = new Tweet();
        try {
            tweet.user = User.parseJSON(object.getJSONObject("user"));
            tweet.createdAt = object.getString("created_at");
            tweet.body = object.getString("text");
        } catch (JSONException e) {
            e.printStackTrace();
        }
        return tweet;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }


    public String getTimestamp() {
        return createdAt;
    }

    public void setTimestamp(String timestamp) {
        this.createdAt = timestamp;
    }

    public String getBody() {
        return body;
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

    public static ArrayList<Tweet> fromJson(JSONArray jsonArray) {
        ArrayList<Tweet> tweets = new ArrayList<Tweet>(jsonArray.length());

        for (int i=0; i < jsonArray.length(); i++) {
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