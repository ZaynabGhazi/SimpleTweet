package com.codepath.apps.restclienttemplate.models;

import androidx.room.Dao;
import androidx.room.Insert;
import androidx.room.OnConflictStrategy;
import androidx.room.Query;
import androidx.room.Update;

import java.util.List;

@Dao
public interface TweetDAO {
    // Record finders
    @Query("SELECT * FROM Tweet WHERE id = :tweetId")
    Tweet byTweetId(Long tweetId);

    @Query("SELECT Tweet.body AS tweet_body, Tweet.createdAt AS tweet_createdAt, Tweet.id AS tweet_id, User.*" + " FROM Tweet INNER JOIN User ON Tweet.userid = User.id ORDER BY createdAt DESC LIMIT 5")
    List<TweetWithUser> getRecentTweets();

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    void insertModel(Tweet... tweets);

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    void insertModel(User... users);
}
