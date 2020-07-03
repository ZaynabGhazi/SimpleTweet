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

    @Query("SELECT Tweet.body AS tweet_body, Tweet.createdAt AS tweet_createdAt, Tweet.id AS tweet_id,Tweet.body_image_url AS tweet_body_image_url,Tweet.isFavorite AS tweet_isFavorite,Tweet.favorite_count AS tweet_favorite_count,Tweet.retweet_count AS tweet_retweet_count,Tweet.reply_count AS tweet_reply_count,Tweet.retweeted AS tweet_retweeted,User.*" + " FROM Tweet INNER JOIN User ON Tweet.userid = User.id ORDER BY createdAt DESC LIMIT 10")
    List<TweetWithUser> getRecentTweets();

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    void insertModel(Tweet... tweets);

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    void insertModel(User... users);
}
