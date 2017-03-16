package com.mgaga.starterapp.components.login.interfaces;

import com.hannesdorfmann.mosby.mvp.MvpView;

/**
 * Created by tshepomgaga on 2017/03/14.
 */

public interface LoginUI extends MvpView {
    void showProgress(boolean show);
    void setUsername(String username);
    void focusPassword();
    void networkError();
}
