package com.mgaga.starterapp;

import android.app.Application;
import android.content.SharedPreferences;

import com.mgaga.starterapp.auth.AuthenticationProvider;
import com.mgaga.starterapp.base.ApplicationExtension;
import com.mgaga.starterapp.components.AppBus;

import javax.inject.Singleton;

import dagger.Component;

/**
 * Created by tshepomgaga on 2017/03/15.
 */

@Singleton
@Component(modules = ApplicationModule.class)
public interface ApplicationComponent {
    void inject(ApplicationExtension applicationExtension);

    AppBus appBus();

    Application application();

    SharedPreferences getSharedPreferences();

    AuthenticationProvider authenticationProvider();
}
