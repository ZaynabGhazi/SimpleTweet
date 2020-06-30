package com.codepath.apps.restclienttemplate;

import androidx.room.Database;
import androidx.room.RoomDatabase;


import com.codepath.apps.restclienttemplate.models.Tweet;
import com.codepath.apps.restclienttemplate.models.TwitterDAO;
//increased version number for every schema update
@Database(entities={Tweet.class}, version=3)
public abstract class MyDatabase extends RoomDatabase {
    public abstract TwitterDAO twitterDao();
    public static final String NAME = "MyDataBase";
}
