package com.codepath.apps.restclienttemplate.models;

import androidx.room.ColumnInfo;

import org.json.JSONException;
import org.json.JSONObject;
import org.parceler.Parcel;

@Parcel
public class User {

    @ColumnInfo
    String name;

    @ColumnInfo
    String profileImageUrl;

    // normally this field would be annotated @PrimaryKey because this is an embedded object
    // it is not needed
    @ColumnInfo
    String screenName;

    public User() {
    }

    public static User parseJSON(JSONObject tweetJson) {
        User user = new User();
        try {
            user.screenName = tweetJson.getString("screen_name");
            user.name = tweetJson.getString("name");
            user.profileImageUrl = tweetJson.getString("profile_image_url_https");

        } catch (JSONException e) {
            e.printStackTrace();
        }
        return user;
    }

    public String getName() {
        return name;
    }

    public String getProfileImageUrl() {
        return profileImageUrl;
    }

    public String getScreenName() {
        return screenName;
    }
}
