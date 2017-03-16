package com.mgaga.starterapp.components.login.interfaces;

import com.mgaga.starterapp.ApplicationComponent;
import com.mgaga.starterapp.LoginActivity;
import com.mgaga.starterapp.base.annotations.PerActivity;
import com.mgaga.starterapp.components.ActivityModule;
import com.mgaga.starterapp.components.login.LoginModule;

import dagger.Component;

/**
 * Created by tshepomgaga on 2017/03/15.
 */

@PerActivity
@Component(
        dependencies = {ApplicationComponent.class},
        modules = {ActivityModule.class, LoginModule.class})
public interface LoginComponent {
    void inject(LoginActivity holder);

}
