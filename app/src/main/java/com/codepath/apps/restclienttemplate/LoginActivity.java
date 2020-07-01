package com.codepath.apps.restclienttemplate;

import android.content.Intent;
import android.os.AsyncTask;
import android.os.Bundle;
import android.util.Log;
import android.view.Menu;
import android.view.View;

import com.codepath.apps.restclienttemplate.models.Tweet;
import com.codepath.apps.restclienttemplate.models.TweetDAO;
import com.codepath.oauth.OAuthLoginActionBarActivity;

public class LoginActivity extends OAuthLoginActionBarActivity<TwitterClient> {

	TweetDAO tweetDAO;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_login);
		final Tweet tweet = new Tweet();
		//DB management
		tweetDAO = ((TwitterApp) getApplicationContext()).getMyDatabase().tweetDao();
		AsyncTask.execute(new Runnable() {
			@Override
			public void run() {
				tweetDAO.insertModel(tweet);
			}
		});
	}

	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		getMenuInflater().inflate(R.menu.login, menu);
		return true;
	}

	@Override
	public void onLoginSuccess() {
		Log.i("LOGIN_SUCCESS","login success");
		Intent i = new Intent(this, TimelineActivity.class);
		startActivity(i);
	}

	@Override
	public void onLoginFailure(Exception e) {
		e.printStackTrace();
	}

	public void loginToRest(View view) {
		getClient().connect();
	}
}
