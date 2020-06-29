package com.codepath.apps.restclienttemplate;

import android.content.Intent;
import android.os.AsyncTask;
import android.os.Bundle;
import android.util.Log;
import android.view.Menu;
import android.view.View;

import com.codepath.apps.restclienttemplate.models.TwitterDAO;
import com.codepath.oauth.OAuthLoginActionBarActivity;

public class LoginActivity extends OAuthLoginActionBarActivity<TwitterClient> {

	TwitterDAO twitterDAO;
	
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_login);
		//DB management
		twitterDAO = ((TwitterApp) getApplicationContext()).getMyDatabase().twitterDao();
		AsyncTask.execute(new Runnable() {
			@Override
			public void run() {
				twitterDAO.insertTweet();
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
