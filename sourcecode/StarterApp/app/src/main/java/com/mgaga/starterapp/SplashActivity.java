package com.mgaga.starterapp;

import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;

import com.mgaga.starterapp.auth.AuthenticationProvider;

import javax.inject.Inject;

public class SplashActivity extends BaseActivity {

    @Inject
    AuthenticationProvider authProvider;

    @Inject
    SharedPreferences sharedPrefs;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        activityComponent.inject(this);

        nextActivity();
    }

    private void nextActivity() {
        Intent intent = new Intent(this, LoginActivity.class);
        startActivity(intent);
        finish();
    }
}
