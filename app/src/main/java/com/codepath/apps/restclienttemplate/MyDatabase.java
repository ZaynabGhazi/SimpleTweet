package com.codepath.apps.restclienttemplate;

import androidx.room.Database;
import androidx.room.RoomDatabase;


import com.codepath.apps.restclienttemplate.models.Tweet;
import com.codepath.apps.restclienttemplate.models.TwitterDAO;

@Database(entities={Tweet.class}, version=3)
public abstract class MyDatabase extends RoomDatabase {
    public abstract TwitterDAO twitterDao();

    // Database name to be used
    public static final String NAME = "MyDataBase";
}
