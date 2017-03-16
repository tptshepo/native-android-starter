package com.mgaga.starterapp;

/**
 * Created by tshepomgaga on 2017/03/15.
 */

import com.mgaga.starterapp.base.PersistenceMode;

import okhttp3.HttpUrl;

public class AppSettings {
    private final HttpUrl endpoint;

    private final long timeout;
    private final PersistenceMode persistenceMode;

    private AppSettings(AppSettings.Builder builder) {
        this.timeout = 60000L;
        this.endpoint = builder.endpoint;
        this.persistenceMode = builder.persistenceMode;
    }

    public HttpUrl getEndpoint() {
        return this.endpoint;
    }

    public long getTimeout() {
        this.getClass();
        return 60000L;
    }


    public static class Builder {
        private HttpUrl endpoint;
        private PersistenceMode persistenceMode;
        private long timeout;

        public Builder() {
        }

        public AppSettings.Builder endpoint(HttpUrl endpoint) {
            this.endpoint = endpoint;
            if(!endpoint.toString().endsWith("/")) {
                this.endpoint = HttpUrl.parse(endpoint.toString() + "/");
            }

            return this;
        }

        public AppSettings.Builder endpoint(String endpoint) {
            if(!endpoint.endsWith("/")) {
                endpoint = endpoint + "/";
            }

            this.endpoint = HttpUrl.parse(endpoint);
            return this;
        }

        public AppSettings.Builder persistenceMode(PersistenceMode persistenceMode) {
            this.persistenceMode = persistenceMode;
            return this;
        }

        public AppSettings.Builder timeout(long timeout) {
            this.timeout = timeout;
            return this;
        }


        public AppSettings build() {
            this.preBuild();
            return new AppSettings(this);
        }

        private void preBuild() {
        }
    }
}