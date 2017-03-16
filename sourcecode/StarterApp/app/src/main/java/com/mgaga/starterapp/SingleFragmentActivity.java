package com.mgaga.starterapp;


import android.content.Context;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentTransaction;
import android.support.v7.app.AppCompatActivity;

import com.mgaga.starterapp.base.ApplicationExtension;

public abstract class SingleFragmentActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(null);

        initializeInjector();

        setContentView(R.layout.activity_single_fragment);

        String tag = getTag();
        Fragment fragment = getFragment(this);

        FragmentTransaction transaction = getSupportFragmentManager().beginTransaction();
        if (tag != null) {
            transaction.replace(R.id.activity_single_fragment_container, fragment, tag);
        } else {
            transaction.replace(R.id.activity_single_fragment_container, fragment);
        }
        transaction.commit();
    }

    protected ApplicationComponent getApplicationComponent() {
        return ((ApplicationExtension) getApplication()).getApplicationComponent();
    }

    protected abstract void initializeInjector();

    protected String getTag() {
        return null;
    }

    protected abstract Fragment getFragment(Context context);

    @Override
    protected void onStop() {
        super.onStop();
    }

    @Override
    public void onSaveInstanceState(Bundle outState) {
        super.onSaveInstanceState(outState);

    }

    @Override
    protected void onRestoreInstanceState(Bundle savedInstanceState) {
        super.onRestoreInstanceState(savedInstanceState);

    }

    @Override
    protected void onStart() {
        super.onStart();
    }

    private void gotoLogin() {
//        activeTimeStamp = System.currentTimeMillis();
//        Intent intent = new Intent(this, SplashActivity.class);
//        intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP | Intent.FLAG_ACTIVITY_NEW_TASK);
//        startActivity(intent);
//        finish();
    }


}