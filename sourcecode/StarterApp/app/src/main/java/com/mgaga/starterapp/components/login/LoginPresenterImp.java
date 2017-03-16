package com.mgaga.starterapp.components.login;

import android.app.Activity;
import android.content.Intent;
import android.content.SharedPreferences;

import com.hannesdorfmann.mosby.mvp.MvpNullObjectBasePresenter;
import com.mgaga.starterapp.MainActivity;
import com.mgaga.starterapp.auth.AuthenticationProvider;
import com.mgaga.starterapp.auth.enums.LoginResponse;
import com.mgaga.starterapp.base.Const;
import com.mgaga.starterapp.components.login.interfaces.LoginPresenter;
import com.mgaga.starterapp.components.login.interfaces.LoginUI;

import org.greenrobot.eventbus.Subscribe;

import javax.inject.Inject;

import bolts.Continuation;
import bolts.Task;

/**
 * Created by tshepomgaga on 2017/03/15.
 */

public class LoginPresenterImp extends MvpNullObjectBasePresenter<LoginUI> implements LoginPresenter {

    @Inject
    LoginBus bus;
    @Inject
    Activity activity;
    @Inject
    AuthenticationProvider authProvider;
    @Inject
    SharedPreferences prefs;

    private LoginUI ui;

    @Inject
    LoginPresenterImp() {
    }

    @Override
    public void attachView(LoginUI view) {
        super.attachView(view);
        ui = getView();
        bus.register(this);

        initUI(prefs.getString(Const.PREFS_LAST_USED_USERNAME, ""));
    }

    @Override
    public void detachView(boolean retainInstance) {
        super.detachView(retainInstance);
        bus.unregister(this);
    }

    private void initUI(String savedUsername) {
        ui.setUsername(savedUsername);
        if (savedUsername != null && savedUsername.length() > 0) {
            ui.focusPassword();
        }
    }

    private void login(String username, String password) {
        ui.showProgress(true);
        prefs.edit().putString(Const.PREFS_LAST_USED_USERNAME, username).apply();

        if (password.length() == 0) {
            ui.showProgress(false);
            ui.focusPassword();
            return;
        }

        authProvider.login(username, password).continueWith(new Continuation<LoginResponse, Void>() {
            @Override
            public Void then(Task<LoginResponse> task) throws Exception {
                if (task.isFaulted()) {
                    ui.networkError();

                    return null;
                } else if (task.getResult() == LoginResponse.SERVER_MAINTENANCE) {
//                    Intent intent = new Intent(activity, ServerMaintenanceActivity.class);
//                    activity.startActivity(intent);
//                    activity.finish();
                    return null;
                } else if (task.getResult() != LoginResponse.SUCCESS) {
                    ui.showProgress(false);

                    return null;
                }
                ui.showProgress(false);
                gotoActivity();
                return null;
            }
        });
    }

    private void gotoActivity() {
        Intent intent = new Intent(activity, MainActivity.class);
        activity.startActivity(intent);
        activity.finish();
    }

    @Subscribe
    public void loginEvent(LoginBus.LoginEvent event) {
        switch (event.getType()) {

            case LOGIN:
                prefs.edit().putString(Const.PREFS_LAST_USED_USERNAME, event.getLogin().getKey()).apply();
                login(event.getLogin().getKey(), event.getLogin().getValue());
                break;

        }
    }
}
