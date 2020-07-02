package com.codepath.apps.restclienttemplate;

import android.content.Context;
import android.graphics.drawable.Drawable;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.core.widget.ImageViewCompat;
import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;
import com.bumptech.glide.request.target.Target;
import com.codepath.apps.restclienttemplate.models.Tweet;
import com.codepath.asynchttpclient.callback.JsonHttpResponseHandler;

import org.w3c.dom.Text;

import java.util.List;

import okhttp3.Headers;

import static android.text.TextUtils.isEmpty;
import static java.security.AccessController.getContext;

public class TweetsAdapter extends RecyclerView.Adapter<TweetsAdapter.ViewHolder> {
    Context context;
    List<Tweet> tweets;

    public TweetsAdapter(Context context, List<Tweet> tweets) {
        this.context = context;
        this.tweets = tweets;
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(context).inflate(R.layout.item_tweet, parent, false);
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {
        Tweet tweet = tweets.get(position);
        holder.bind(tweet);
    }

    @Override
    public int getItemCount() {
        return tweets.size();
    }

    //--swipe to refresh methods:
    //clean recycler
    public void clear() {
        tweets.clear();
        notifyDataSetChanged();
    }

    // Add a list of tweets
    public void addAll(List<Tweet> list) {
        tweets.addAll(list);
        notifyDataSetChanged();
    }

    //define viewHolder
    public class ViewHolder extends RecyclerView.ViewHolder {
        ImageView ivProfileImage;
        TextView tvBody;
        TextView tvScreenName;
        TextView tvTimestamp;
        TextView tvUsername;
        ImageView ivMedia;
        TextView tvFav;
        TextView tvRetweet;
        ImageView ivFav;
        ImageView ivRetweet;
        TextView tvReply;
        //network:
        TwitterClient client;

        public ViewHolder(@NonNull View itemView) {
            super(itemView);
            ivProfileImage = itemView.findViewById(R.id.ivProfileImage);
            tvBody = itemView.findViewById(R.id.tvBody);
            tvScreenName = itemView.findViewById(R.id.tvScreenName);
            tvTimestamp = itemView.findViewById(R.id.tvTimeStamp);
            tvUsername = itemView.findViewById(R.id.tvUsername);
            ivMedia = itemView.findViewById(R.id.iv_media);
            tvFav = itemView.findViewById(R.id.tvFav);
            tvRetweet = itemView.findViewById(R.id.tvRetweet);
            ivFav = itemView.findViewById(R.id.ivFav);
            client = TwitterApp.getRestClient(context);
            ivRetweet = itemView.findViewById(R.id.ivRetweet);
            tvReply = itemView.findViewById(R.id.tvReply);
        }

        public void bind(final Tweet tweet) {
            tvBody.setText(tweet.getBody());
            tvScreenName.setText(tweet.getUser().getName());
            tvTimestamp.setText(tweet.getTimestamp());
            tvUsername.setText("@" + tweet.getUser().getScreenName());
            tvFav.setText(String.valueOf(tweet.getFavorite_count()));
            tvRetweet.setText(Integer.toString(tweet.getRetweet_count()));
            //tvReply.setText(Integer.toString(tweet.getReply_count()));
            Glide.with(context).load(tweet.getUser().getProfileImageUrl()).into(ivProfileImage);
            if (!isEmpty(tweet.getBody_image_url())) {
                Glide.with(context).load(tweet.getBody_image_url()).override(Target.SIZE_ORIGINAL, 200).into(ivMedia);
                Log.i("TWEET IMAGE", tweet.getBody_image_url());
            } else {
                ivMedia.setVisibility(View.GONE);
            }
            //additional features:
            implementLikeFeature(tweet, ivFav);
            implementRetweetFeature(tweet, ivRetweet);

        }

        //NOTE: can easily implement undo-retweet by analogy to unlike (time constraint)
        //Retweet
        private void implementRetweetFeature(final Tweet tweet, final ImageView ivRetweet) {
            ivRetweet.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    client.retweet(new JsonHttpResponseHandler() {
                        @Override
                        public void onSuccess(int statusCode, Headers headers, JSON json) {
                            Log.i("Tweet retweeted", "successfully.");
                        }

                        @Override
                        public void onFailure(int statusCode, Headers headers, String response, Throwable throwable) {
                            Log.e("Failed to like tweet", " error", throwable);
                        }
                    }, tweet.getId());
                    ivRetweet.setImageResource(R.drawable.ic_vector_retweet);
                    tvRetweet.setText(Integer.toString(Integer.parseInt(tvRetweet.getText().toString()) + 1));
                }
            });
        }

        private void implementLikeFeature(final Tweet tweet, final ImageView ivFav) {
            ivFav.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    if (!tweet.isFavorite()) {
                        //update through network
                        client.likeTweet(new JsonHttpResponseHandler() {
                            @Override
                            public void onSuccess(int statusCode, Headers headers, JSON json) {
                                Log.i("Tweet Liked", "successfully.");
                            }

                            @Override
                            public void onFailure(int statusCode, Headers headers, String response, Throwable throwable) {
                                Log.e("Failed to like tweet", " error", throwable);
                            }
                        }, tweet.getId());
                        ivFav.setImageResource(R.drawable.ic_vector_heart);
                        tvFav.setText(Integer.toString(Integer.parseInt(tvFav.getText().toString()) + 1));
                        tweet.setFavorite(true);
                    } else {
                        ivFav.setImageResource(R.drawable.ic_vector_heart_stroke);
                        tvFav.setText(Integer.toString(Integer.parseInt(tvFav.getText().toString()) - 1));
                        tweet.setFavorite(false);
                        //update through network
                        client.unlikeTweet(new JsonHttpResponseHandler() {
                            @Override
                            public void onSuccess(int statusCode, Headers headers, JSON json) {
                                Log.i("Tweet unliked", "successfully.");
                            }

                            @Override
                            public void onFailure(int statusCode, Headers headers, String response, Throwable throwable) {
                                Log.e("Failed to unlike tweet", " error", throwable);
                            }
                        }, tweet.getId());
                    }
                }
            });
        }

    }

    //solve rv shuffle issues:
    @Override
    public long getItemId(int position) {
        return position;
    }

    @Override
    public int getItemViewType(int position) {
        return position;
    }
}
