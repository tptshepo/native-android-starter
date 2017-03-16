package com.mgaga.starterapp.auth;

import android.content.Context;
import android.content.SharedPreferences;

import com.mgaga.starterapp.auth.enums.LoginResponse;
import com.mgaga.starterapp.base.Const;
import com.mgaga.starterapp.base.ErrorHandler;
import com.mgaga.starterapp.helpers.KeyVal;

import org.json.JSONException;
import org.json.JSONObject;

import java.io.IOException;
import java.net.HttpCookie;
import java.util.ArrayList;
import java.util.List;

import bolts.Continuation;
import bolts.Task;
import bolts.TaskCompletionSource;
import okhttp3.Call;
import okhttp3.Callback;
import okhttp3.MediaType;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.RequestBody;
import okhttp3.Response;

/**
 * Created by tshepomgaga on 2017/03/15.
 */

public class AuthenticationProvider implements BaseAuthenticationProvider {

    private static final MediaType JSON = MediaType.parse("application/json; charset=utf-8");
    private final JSONObject unauthorizedObject;
    private final JSONObject serverDownObject;

    private OkHttpClient client = new OkHttpClient();
    private String authenticationEndpoint;
    private List<KeyVal> headers = new ArrayList<>();
    private AuthenticationError authenticationErrorHandler;
    private SharedPreferences sharedPref;



    public interface AuthenticationError {
        void authenticationError(Exception err);
    }

    public AuthenticationProvider(Context context, SharedPreferences preferences) {
        sharedPref = preferences;
        unauthorizedObject = new JSONObject();
        serverDownObject = new JSONObject();
    }

    public void setEndpoint(String endpoint) {
        String authEndpoint = "authentication";
        if (endpoint.endsWith("/")) {
            authenticationEndpoint = endpoint + authEndpoint;
        } else {
            authenticationEndpoint = endpoint + "/" + authEndpoint;
        }
    }

    public void setAuthenticationErrorHandler(AuthenticationError errorHandler) {
        authenticationErrorHandler = errorHandler;
    }

    @Override
    public List<HttpCookie> getCookies() {
        return null;
    }

    @Override
    public void authenticationFailed() {
        if (authenticationErrorHandler != null) {
            authenticationErrorHandler.authenticationError(new Exception("401"));
        }
    }

    @Override
    public List<KeyVal> getHeaders() {
        return new ArrayList<>();
    }


    private Task<JSONObject> callToAuthEndpoint(final JSONObject json, final String endpoint) {
        final TaskCompletionSource<JSONObject> task = new TaskCompletionSource<>();
        RequestBody body = RequestBody.create(JSON, json.toString());
        Request request = new Request.Builder().url(endpoint).post(body).build();

        client.newCall(request).enqueue(new Callback() {
            @Override
            public void onFailure(Call request, IOException e) {
                task.setError(e);
            }

            @Override
            public void onResponse(Call call, Response response) throws IOException {
                if (response.code() == Const.RESPONSE_CODE_UNAUTHORIZED || response.code() == Const.RESPONSE_CODE_BAD_REQUEST) {
                    task.setResult(unauthorizedObject);
                    return;
                } else if (response.code() == Const.RESPONSE_CODE_SERVER_MAINTENANCE) {
                    task.setResult(serverDownObject);
                    return;
                }

                JSONObject result;
                String body = response.body().string();
                try {
                    result = new JSONObject(body);
                } catch (JSONException e) {
                    task.setError(e);
                    ErrorHandler.reportAndHandle(e, "Exception in retrieving authentication response");
                    return;
                }
                try {
                    // get response data
                    result = result.getJSONObject("data");
                    String accessToken = result.getString("accessToken");
                    String refreshToken = result.getString("refreshToken");


                } catch (Exception e) {
                    ErrorHandler.reportAndHandle(e, "Exception in parsing authentication data for : " + endpoint);
                    ErrorHandler.reportAndHandle(json.toString());
                    ErrorHandler.reportAndHandle(body);
                    task.setError(e);
                    return;
                }
                task.setResult(result);
            }
        });

        return task.getTask();
    }

    public Task<LoginResponse> login(String username, String password) {
        final TaskCompletionSource<LoginResponse> task = new TaskCompletionSource<>();
        try {
            JSONObject json = new JSONObject();
            json.put("username", username);
            json.put("password", password);

            callToAuthEndpoint(json, authenticationEndpoint).continueWith(new Continuation<JSONObject, Void>() {
                @Override
                public Void then(final Task<JSONObject> resultTask) throws Exception {
                    if (resultTask.isFaulted()) {
                        task.setError(resultTask.getError());
                    } else if (resultTask.isCompleted()) {
                        if (resultTask.getResult() == unauthorizedObject) {
                            task.setResult(LoginResponse.UNAUTHORIZED);
                        } else if (resultTask.getResult() == serverDownObject) {
                            task.setResult(LoginResponse.SERVER_MAINTENANCE);
                        } else {
                            task.setResult(LoginResponse.SUCCESS);
                        }
                    } else {
                        task.setResult(LoginResponse.SERVER_ERROR);
                    }
                    return null;
                }
            }, Task.UI_THREAD_EXECUTOR);

        } catch (Exception e) {
            task.setError(e);
        }
        return task.getTask();
    }


}
