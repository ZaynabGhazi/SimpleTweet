package com.codepath.apps.restclienttemplate.models;

import androidx.room.ColumnInfo;
import androidx.room.Entity;
import androidx.room.PrimaryKey;

import org.json.JSONException;
import org.json.JSONObject;
import org.parceler.Parcel;

import java.util.ArrayList;
import java.util.List;

@Parcel
@Entity
public class User {

    @ColumnInfo
    String name;

    @ColumnInfo
    String profileImageUrl;

    @ColumnInfo
    String screenName;

    @PrimaryKey
    @ColumnInfo
    long id;


    public User() {
    }

    public static User parseJSON(JSONObject tweetJson) {
        User user = new User();
        try {
            user.screenName = tweetJson.getString("screen_name");
            user.name = tweetJson.getString("name");
            user.profileImageUrl = tweetJson.getString("profile_image_url_https");
            user.id = tweetJson.getLong("id");

        } catch (JSONException e) {
            e.printStackTrace();
        }
        return user;
    }

    public static List<User> parseJSONTweetArray(List<Tweet> tweetsFromNet) {
        List<User> users = new ArrayList<>();
        for (int i = 0; i < tweetsFromNet.size(); i++) {
            users.add(tweetsFromNet.get(i).user);
        }
        return users;
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
