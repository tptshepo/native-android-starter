package com.mgaga.starterapp.components;

import android.app.Activity;
import android.content.Context;

import com.mgaga.starterapp.base.annotations.PerActivity;

import dagger.Module;
import dagger.Provides;

/**
 * Created by tshepomgaga on 2017/03/15.
 */

@Module
public class ActivityModule {
    private final Activity activity;

    public ActivityModule(Activity activity) {
        this.activity = activity;
    }

    @Provides
    @PerActivity
    public Activity provideActivity() {
        return this.activity;
    }

    @Provides
    @PerActivity
    Context provideContext() {
        return this.activity;
    }
}
