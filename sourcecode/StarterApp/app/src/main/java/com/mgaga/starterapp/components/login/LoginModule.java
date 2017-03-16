package com.mgaga.starterapp.components.login;

import com.mgaga.starterapp.base.annotations.PerActivity;
import com.mgaga.starterapp.components.login.interfaces.LoginPresenter;
import com.mgaga.starterapp.components.login.interfaces.LoginUI;

import dagger.Module;
import dagger.Provides;

/**
 * Created by tshepomgaga on 2017/03/15.
 */

@Module
public class LoginModule {

    @PerActivity
    @Provides
    public LoginUI provideLoginUI(LoginFragment ui) {
        return ui;
    }

    @PerActivity
    @Provides
    public LoginPresenter provideLoginPresenter(LoginPresenterImp presenter) {
        return presenter;
    }

    @PerActivity
    @Provides
    public LoginBus provideLoginBus() {
        return new LoginBus();
    }
}
