package com.mgaga.starterapp.components.login;

import org.greenrobot.eventbus.EventBus;
import com.mgaga.starterapp.helpers.KeyVal;

/**
 * Created by tshepomgaga on 2017/03/14.
 */

public class LoginBus extends EventBus {

    void login(String username, String password) {
        this.post(new LoginEvent(LoginEventType.LOGIN, new KeyVal<>(username, password)));
    }

    static class LoginEvent {
        private final LoginEventType type;
        private final Object value;

        KeyVal<String, String> getLogin() {
            return (KeyVal) this.value;
        }

        LoginEvent(LoginEventType type, Object value) {
            this.type = type;
            this.value = value;
        }

        public LoginEventType getType() {
            return this.type;
        }
    }

    enum LoginEventType {
        LOGIN
    }
}
