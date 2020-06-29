package com.codepath.apps.restclienttemplate.models;

import androidx.room.ColumnInfo;

import org.json.JSONException;
import org.json.JSONObject;

public class User {

    @ColumnInfo
    String name;

    // normally this field would be annotated @PrimaryKey because this is an embedded object
    // it is not needed
    @ColumnInfo
    Long twitter_id;

    public static User parseJSON(JSONObject tweetJson) {

        User user = new User();
        try {
            user.twitter_id = tweetJson.getLong("id");
            user.name = tweetJson.getString("name");

        } catch (JSONException e) {
            e.printStackTrace();
        }
        return user;
    }
}
