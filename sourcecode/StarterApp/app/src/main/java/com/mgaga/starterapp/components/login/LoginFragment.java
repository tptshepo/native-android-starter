package com.mgaga.starterapp.components.login;

import android.app.Dialog;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.view.KeyEvent;
import android.view.LayoutInflater;
import android.view.MotionEvent;
import android.view.View;
import android.view.ViewGroup;
import android.view.ViewTreeObserver;
import android.view.inputmethod.EditorInfo;
import android.widget.Button;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.ScrollView;
import android.widget.TextView;
import android.widget.Toast;

import com.afollestad.materialdialogs.MaterialDialog;
import com.hannesdorfmann.mosby.mvp.MvpFragment;
import com.mgaga.starterapp.R;
import com.mgaga.starterapp.components.login.interfaces.LoginPresenter;
import com.mgaga.starterapp.components.login.interfaces.LoginUI;
import com.mgaga.starterapp.helpers.CheckNetwork;

import javax.inject.Inject;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;
import butterknife.Unbinder;


public class LoginFragment extends MvpFragment<LoginUI, LoginPresenter> implements LoginUI {

    @Inject
    LoginBus bus;
    @Inject
    LoginPresenter presenter;

    private Dialog dialog;

    @BindView(R.id.txtUsername)
    EditText username;
    @BindView(R.id.txtPassword)
    EditText password;
    @BindView(R.id.btnLogin)
    Button logIn;

    @BindView(R.id.login_form)
    ScrollView mainScroll;


//    @BindView(R.id.error_login_text_textview)
//    TextView errorLoginText;

    private boolean errorOn = false;

    private Unbinder unbinder;

    @Inject
    public LoginFragment() {
        super();
    }

    @Override
    @NonNull
    public LoginPresenter createPresenter() {
        return presenter;
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {

        super.onCreateView(inflater, container, savedInstanceState);
        View rootView = inflater.inflate(R.layout.fragment_login, container, false);

        unbinder = ButterKnife.bind(this, rootView);

        password.setOnEditorActionListener(new TextView.OnEditorActionListener() {
            @Override
            public boolean onEditorAction(TextView textView, int id, KeyEvent keyEvent) {
                if (id == R.id.login || id == EditorInfo.IME_NULL) {
                    login();
                    return true;
                }
                return false;
            }
        });

        mainScroll.getViewTreeObserver().addOnGlobalLayoutListener(new ViewTreeObserver.OnGlobalLayoutListener() {
            @Override
            public void onGlobalLayout() {
                mainScroll.smoothScrollTo(0, mainScroll.getHeight());
            }
        });

        username.setOnTouchListener(new View.OnTouchListener() {
            @Override
            public boolean onTouch(View v, MotionEvent event) {
                if (MotionEvent.ACTION_UP == event.getAction())
                    if (errorOn) {
                        //changeColor(R.color.brand_primary);
                    }
                return false;
            }
        });

        password.setOnTouchListener(new View.OnTouchListener() {
            @Override
            public boolean onTouch(View v, MotionEvent event) {
                if (MotionEvent.ACTION_UP == event.getAction())
                    if (errorOn) {
                        //changeColor(R.color.brand_primary);
                    }
                return false;
            }
        });

        return rootView;
    }


    @Override
    public void networkError() {
        showProgress(false);
        Toast.makeText(getContext(), R.string.login_error_network_error, Toast.LENGTH_LONG).show();
    }

    @OnClick(R.id.btnLogin)
    public void onLoginIn(View sender) {
        if (CheckNetwork.isInternetAvailable(getActivity())) {//returns true if internet available
            login();
        } else {
            Toast.makeText(getActivity(), R.string.global_error_no_internet, Toast.LENGTH_LONG).show();
        }
    }

    private void login() {
        // Reset errors.
        username.setError(null);
        password.setError(null);
        bus.login(username.getText().toString(), password.getText().toString());
        password.setText("");
    }


    @Override
    public void showProgress(final boolean show) {
        if (show) {
            dialog = new MaterialDialog.Builder(getContext())
                    .title(getResources().getString(R.string.login_loading))
                    .content(getResources().getString(R.string.login_loading_text))
                    .progress(true, 0)
                    .show();
        } else {
            dialog.dismiss();
        }
    }

    @Override
    public void setUsername(String emailText) {
        username.setText(emailText);
    }

    @Override
    public void focusPassword() {
        password.post(new Runnable() {
            @Override
            public void run() {
                password.requestFocus();
            }
        });
    }

    @Override
    public void onResume() {
        super.onResume();
    }

    @Override
    public void onPause() {
        super.onPause();
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
        unbinder.unbind();
        bus.unregister(this);
    }




}
