package com.mgaga.starterapp.auth;

import com.mgaga.starterapp.helpers.KeyVal;

import java.net.HttpCookie;
import java.util.List;

/**
 * Created by tshepomgaga on 2017/03/15.
 */

public interface BaseAuthenticationProvider {
    List<KeyVal> getHeaders();

    List<HttpCookie> getCookies();

    void authenticationFailed();
}
