package com.mgaga.starterapp.components;

import android.app.Activity;

import com.mgaga.starterapp.ApplicationComponent;
import com.mgaga.starterapp.SplashActivity;
import com.mgaga.starterapp.base.annotations.PerActivity;

import dagger.Component;

/**
 * Created by tshepomgaga on 2017/03/15.
 */

@PerActivity
@Component(dependencies = ApplicationComponent.class, modules = {ActivityModule.class})
public interface ActivityComponent {
    void inject(SplashActivity activity);

    Activity activity();
}
