package com.example.lasic.ublog;

import android.animation.Animator;
import android.animation.AnimatorListenerAdapter;
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

import butterknife.BindView;
import butterknife.ButterKnife;

/**
 * A login screen that offers login via email/password.
 */
public class LoginActivity extends AppCompatActivity {

    private enum State{
        IDLE,
        LOGIN,
        REGISTER,
        WAITING
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
            case WAITING:
                loginSelector.setVisibility(View.GONE);
                loginForm.setVisibility(View.GONE);
                loaderView.setVisibility(View.VISIBLE);
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
                setState(State.IDLE);
                break;
            default:
                super.onBackPressed();
        }
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
        String username = etUsername.getText().toString();
        String email = etEmail.getText().toString();
        String password = etPassword.getText().toString();

        boolean cancel = false;
        View focusView = null;

        // Check for a valid password, if the user entered one.
        if (!TextUtils.isEmpty(password) && !isPasswordValid(password)) {
            etPassword.setError(getString(R.string.error_invalid_password));
            focusView = etPassword;
            cancel = true;
        }

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
        int shortAnimTime = getResources().getInteger(android.R.integer.config_shortAnimTime);

        loginForm.setVisibility(show ? View.GONE : View.VISIBLE);
        loginForm.animate().setDuration(shortAnimTime).alpha(
                show ? 0 : 1).setListener(new AnimatorListenerAdapter() {
            @Override
            public void onAnimationEnd(Animator animation) {
                loginForm.setVisibility(show ? View.GONE : View.VISIBLE);
            }
        });

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

