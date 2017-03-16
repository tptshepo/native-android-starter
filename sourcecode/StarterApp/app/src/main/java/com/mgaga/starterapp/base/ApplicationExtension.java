package com.mgaga.starterapp.base;

import android.support.multidex.MultiDexApplication;

import com.mgaga.starterapp.ApplicationComponent;
import com.mgaga.starterapp.ApplicationModule;
import com.mgaga.starterapp.DaggerApplicationComponent;

/**
 * Created by tshepomgaga on 2017/03/15.
 */

public class ApplicationExtension extends MultiDexApplication {
    private ApplicationComponent applicationComponent;

//    @Named("network_log")
//    @Inject
//    protected WBXSettings settings;

    @Override
    public void onCreate() {
        super.onCreate();

        applicationComponent = DaggerApplicationComponent
                .builder()
                .applicationModule(new ApplicationModule(this))
                .build();

        applicationComponent.inject(this);

    }



    public ApplicationComponent getApplicationComponent() {
        return applicationComponent;
    }


}