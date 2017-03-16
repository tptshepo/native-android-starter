package com.mgaga.starterapp;

import android.content.Context;
import android.support.v4.app.Fragment;

import com.mgaga.starterapp.components.ActivityModule;
import com.mgaga.starterapp.components.login.interfaces.DaggerLoginComponent;
import com.mgaga.starterapp.components.login.interfaces.LoginComponent;
import com.mgaga.starterapp.components.login.interfaces.LoginPresenter;
import com.mgaga.starterapp.components.login.interfaces.LoginUI;

import javax.inject.Inject;

public class LoginActivity extends SingleFragmentActivity {

    @Inject
    LoginUI loginUI;
    @Inject
    LoginPresenter loginPresenter;

    @Override
    protected void initializeInjector() {
        LoginComponent component = DaggerLoginComponent
                .builder()
                .activityModule(new ActivityModule(this))
                .applicationComponent(getApplicationComponent())
                .build();

        component.inject(this);
    }

    @Override
    protected Fragment getFragment(Context context) {
        return (Fragment) this.loginUI;
    }


}

