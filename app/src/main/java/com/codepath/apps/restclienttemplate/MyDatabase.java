package com.codepath.apps.restclienttemplate;

import androidx.room.Database;
import androidx.room.RoomDatabase;


import com.codepath.apps.restclienttemplate.models.Tweet;
import com.codepath.apps.restclienttemplate.models.TweetDAO;
import com.codepath.apps.restclienttemplate.models.User;

//increased version number for every schema update
@Database(entities = {Tweet.class, User.class}, version = 13)
public abstract class MyDatabase extends RoomDatabase {
    public abstract TweetDAO tweetDao();

    public static final String NAME = "MyDataBase";
}
