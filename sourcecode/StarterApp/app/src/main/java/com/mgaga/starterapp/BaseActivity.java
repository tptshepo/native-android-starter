package com.mgaga.starterapp;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;

import com.mgaga.starterapp.base.ApplicationExtension;
import com.mgaga.starterapp.components.ActivityComponent;
import com.mgaga.starterapp.components.ActivityModule;
import com.mgaga.starterapp.components.DaggerActivityComponent;

/**
 * Created by tshepomgaga on 2017/03/15.
 */

public abstract class BaseActivity extends AppCompatActivity {

    protected ActivityComponent activityComponent;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        this.initializeInjector();
    }

    protected void initializeInjector() {
        this.activityComponent = DaggerActivityComponent
                .builder()
                .applicationComponent(getApplicationComponent())
                .activityModule(new ActivityModule(this))
                .build();
    }

    protected ApplicationComponent getApplicationComponent() {
        return ((ApplicationExtension) getApplication()).getApplicationComponent();
    }
}
