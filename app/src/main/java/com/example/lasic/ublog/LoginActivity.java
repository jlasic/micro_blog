package com.example.lasic.ublog;

import android.animation.Animator;
import android.animation.AnimatorListenerAdapter;
import android.app.Activity;
import android.content.Intent;
import android.support.v7.app.AppCompatActivity;

import android.os.Bundle;
import android.text.TextUtils;
import android.view.KeyEvent;
import android.view.MotionEvent;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.inputmethod.EditorInfo;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;

import com.example.lasic.ublog.data.Post;
import com.example.lasic.ublog.helpers.Utils;
import com.example.lasic.ublog.singletons.Session;

import butterknife.BindView;
import butterknife.ButterKnife;

/**
 * A login screen that offers login via email/password.
 */
public class LoginActivity extends AppCompatActivity {

    private enum State{
        IDLE,
        LOGIN,
        REGISTER
    }

    @BindView(R.id.selector)
    View loginSelector;

    @BindView(R.id.login_btn)
    View btnLogin;

    @BindView(R.id.register_btn)
    View btnRegister;

    @BindView(R.id.email_login_form)
    View loginForm;

    @BindView(R.id.username)
    EditText etUsername;

    @BindView(R.id.email)
    EditText etEmail;

    @BindView(R.id.password)
    EditText etPassword;

    @BindView(R.id.loader)
    View loaderView;

    @BindView(R.id.confirm_btn)
    Button btnConfirm;

    private State mState;
    private String username;
    private String email;
    private String password;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);
        ButterKnife.bind(this);

        setState(State.IDLE);

        btnLogin.setOnClickListener(new OnClickListener() {
            @Override
            public void onClick(View v) {
                setState(State.LOGIN);
            }
        });

        btnRegister.setOnClickListener(new OnClickListener() {
            @Override
            public void onClick(View v) {
                setState(State.REGISTER);
            }
        });

        etPassword.setOnEditorActionListener(new TextView.OnEditorActionListener() {
            @Override
            public boolean onEditorAction(TextView textView, int id, KeyEvent keyEvent) {
                if (id == R.id.login || id == EditorInfo.IME_NULL) {
                    attemptLogin();
                    return true;
                }
                return false;
            }
        });

        btnConfirm.setOnClickListener(new OnClickListener() {
            @Override
            public void onClick(View view) {
                attemptLogin();
            }
        });

        loaderView.setOnTouchListener(new View.OnTouchListener() {
            @Override
            public boolean onTouch(View v, MotionEvent event) {
                return true;
            }
        });
    }

    private void setState(State state){
        mState = state;
        switch (state){
            case IDLE:
                loginSelector.setVisibility(View.VISIBLE);
                loginForm.setVisibility(View.GONE);
                loaderView.setVisibility(View.GONE);
                break;
            case LOGIN:
                btnConfirm.setText(getString(R.string.action_sign_in));
                ((View)etEmail.getParent()).setVisibility(View.GONE);
                ((View)etUsername.getParent()).setVisibility(View.VISIBLE);
                loginSelector.setVisibility(View.GONE);
                loginForm.setVisibility(View.VISIBLE);
                loaderView.setVisibility(View.GONE);
                etUsername.requestFocus();
                break;
            case REGISTER:
                btnConfirm.setText(getString(R.string.action_register));
                ((View)etEmail.getParent()).setVisibility(View.VISIBLE);
                ((View)etUsername.getParent()).setVisibility(View.VISIBLE);
                loginSelector.setVisibility(View.GONE);
                loginForm.setVisibility(View.VISIBLE);
                loaderView.setVisibility(View.GONE);
                etUsername.requestFocus();
                break;
        }
        etUsername.setError(null);
        etEmail.setError(null);
        etPassword.setError(null);
        etUsername.setText(null);
        etEmail.setText(null);
        etPassword.setText(null);
    }

    @Override
    public void onBackPressed() {
        switch (mState){
            case REGISTER:
            case LOGIN:
                if (!isLoading()) {
                    setState(State.IDLE);
                    return;
                }
        }

        super.onBackPressed();
    }

    private boolean isLoading(){
        return loaderView.getVisibility() == View.VISIBLE;
    }

    /**
     * Attempts to sign in or register the account specified by the login form.
     * If there are form errors (invalid email, missing fields, etc.), the
     * errors are presented and no actual login attempt is made.
     */
    private void attemptLogin() {

        // Reset errors.
        etUsername.setError(null);
        etEmail.setError(null);
        etPassword.setError(null);

        // Store values at the time of the login attempt.
        username = etUsername.getText().toString();
        email = etEmail.getText().toString();
        password = etPassword.getText().toString();

        boolean cancel = false;
        View focusView = null;

        // Check for a valid password
        if (TextUtils.isEmpty(password)) {
            etPassword.setError(getString(R.string.error_field_required));
            focusView = etPassword;
            cancel = true;
        } else if (!isPasswordValid(password)) {
            etPassword.setError(getString(R.string.error_invalid_password));
            focusView = etPassword;
            cancel = true;
        }

        if (mState == State.REGISTER) {
            // Check for a valid email address.
            if (TextUtils.isEmpty(email)) {
                etEmail.setError(getString(R.string.error_field_required));
                focusView = etEmail;
                cancel = true;
            } else if (!isEmailValid(email)) {
                etEmail.setError(getString(R.string.error_invalid_email));
                focusView = etEmail;
                cancel = true;
            }
        }

        // Check for a valid username
        if (TextUtils.isEmpty(username)) {
            etUsername.setError(getString(R.string.error_field_required));
            focusView = etUsername;
            cancel = true;
        } else if (!isUsernameValid(username)) {
            etUsername.setError(getString(R.string.error_invalid_username));
            focusView = etUsername;
            cancel = true;
        }

        if (cancel) {
            // There was an error; don't attempt login and focus the first
            // form field with an error.
            focusView.requestFocus();
        } else {
            // Show a progress spinner, and kick off a background task to
            // perform the user login attempt.
            showProgress(true);
        }
    }

    private boolean isUsernameValid(String username){
        return username.length() > 4;
    }

    private boolean isEmailValid(String email) {
        //TODO: Replace this with your own logic
        return email.contains("@");
    }

    private boolean isPasswordValid(String password) {
        //TODO: Replace this with your own logic
        return password.length() > 4;
    }
    /**
     * Shows the progress UI and hides the login form.
     */
    private void showProgress(final boolean show) {
        if (show) {
            Utils.hideKeyboard(this);
            switch (mState){
                case LOGIN:
                    Session.getInstance(this).login(username, new SimpleCallback<String, String>() {
                        @Override
                        public void onSuccess(String data) {
                            Intent resultIntent = new Intent();
                            resultIntent.putExtra("username", data);
                            setResult(Activity.RESULT_OK, resultIntent);
                            finish();
                        }

                        @Override
                        public void onError(String error) {

                        }
                    });
                    break;
            }
        }

        int shortAnimTime = getResources().getInteger(android.R.integer.config_shortAnimTime);
        loaderView.setVisibility(show ? View.VISIBLE : View.GONE);
        loaderView.animate().setDuration(shortAnimTime).alpha(
                show ? 1 : 0).setListener(new AnimatorListenerAdapter() {
            @Override
            public void onAnimationEnd(Animator animation) {
                loaderView.setVisibility(show ? View.VISIBLE : View.GONE);
            }
        });
    }

}

