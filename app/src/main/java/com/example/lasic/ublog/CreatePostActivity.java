package com.example.lasic.ublog;

import android.app.Activity;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.Toolbar;
import android.text.InputType;
import android.text.TextUtils;
import android.view.KeyEvent;
import android.view.View;
import android.view.inputmethod.EditorInfo;
import android.widget.EditText;
import android.widget.TextView;

import com.example.lasic.ublog.helpers.Utils;
import com.example.lasic.ublog.singletons.Session;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;

public class CreatePostActivity extends AppCompatActivity {

    @BindView(R.id.toolbar)
    Toolbar toolbar;

    @BindView(R.id.post_title)
    EditText etTitle;

    @BindView(R.id.post_content)
    EditText etContent;

    @BindView(R.id.send)
    View sendView;

    @BindView(R.id.loader)
    View loaderView;

    private boolean isSending;
    private String title;
    private String content;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_create_post);
        ButterKnife.bind(this);
        setSupportActionBar(toolbar);
        setTitle(R.string.create_post_activity);

        sendView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                attemptSend();
            }
        });

        etTitle.setImeOptions(EditorInfo.IME_ACTION_NEXT);
        etTitle.setRawInputType(InputType.TYPE_CLASS_TEXT);

        etContent.setImeOptions(EditorInfo.IME_ACTION_SEND);
        etContent.setRawInputType(InputType.TYPE_CLASS_TEXT);
        etContent.setOnEditorActionListener(new EditText.OnEditorActionListener() {
            @Override
            public boolean onEditorAction(TextView v, int actionId, KeyEvent event) {
                if (actionId == EditorInfo.IME_ACTION_SEND){
                    attemptSend();
                    return true;
                }
                return false;
            }
        });
    }

    void attemptSend(){
        etTitle.setError(null);
        etContent.setError(null);

        title = etTitle.getText().toString();
        content = etContent.getText().toString();

        boolean cancel = false;
        View focusView = null;

        // Check for a valid title
        if (TextUtils.isEmpty(content)) {
            etContent.setError(getString(R.string.error_field_required));
            focusView = etContent;
            cancel = true;
        }

        // Check for a valid title
        if (TextUtils.isEmpty(title)) {
            etTitle.setError(getString(R.string.error_field_required));
            focusView = etTitle;
            cancel = true;
        }

        if (cancel)
            focusView.requestFocus();
        else
            showLoader(true);
    }

    private void showLoader(boolean show){
        if (show){
            loaderView.setVisibility(View.VISIBLE);
            sendView.setVisibility(View.GONE);
            Utils.hideKeyboard(this);
            Session.getInstance(this).post(title, content, new SimpleCallback<String, String>() {
                @Override
                public void onSuccess(String data) {
                    setResult(Activity.RESULT_OK);
                    finish();
                }

                @Override
                public void onError(String error) {
                    showLoader(false);
                }
            });
        }
        else {
            loaderView.setVisibility(View.GONE);
            sendView.setVisibility(View.VISIBLE);
        }
    }
}
