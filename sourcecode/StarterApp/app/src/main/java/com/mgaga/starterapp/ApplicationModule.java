package com.mgaga.starterapp;

import android.app.Application;
import android.content.Context;
import android.content.SharedPreferences;

import com.mgaga.starterapp.auth.AuthenticationProvider;
import com.mgaga.starterapp.base.ApplicationExtension;
import com.mgaga.starterapp.base.Const;
import com.mgaga.starterapp.components.AppBus;

import javax.inject.Singleton;

import dagger.Module;
import dagger.Provides;

/**
 * Created by tshepomgaga on 2017/03/15.
 */

@Module
public class ApplicationModule {

    private ApplicationExtension applicationExtension;

    public ApplicationModule(ApplicationExtension context) {
        this.applicationExtension = context;
    }

    @Provides
    protected Application provideApplication() {
        return applicationExtension;
    }


    @Singleton
    @Provides
    protected AuthenticationProvider provideAuthentication(SharedPreferences prefs) {
        return new AuthenticationProvider(applicationExtension.getApplicationContext(), getSharedPreferences());
    }

    @Singleton
    @Provides
    protected AppBus provideApplicationBus() {
        return new AppBus();
    }

    @Singleton
    @Provides
    protected SharedPreferences getSharedPreferences() {
        return applicationExtension.getSharedPreferences(Const.PREFS_COMMON, Context.MODE_PRIVATE);
    }
}
