package com.assignment.postbook.ui.loginmodule;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

import com.assignment.postbook.R;
import com.assignment.postbook.ui.userpost.UserPostActivity;

import static com.assignment.postbook.util.AppUtils.USER_ID;


public class LoginActivity extends AppCompatActivity implements LoginContract.View {

    private LoginPresenterImpl mLoginPresenter;
    private EditText mUserIdEditText;
    private Button mLogoutBtn;
    private Button mLoginBtn;
    private TextView mUserIdTextview;
    private static final String TAG = LoginActivity.class.getName();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);

        mUserIdEditText = (EditText) findViewById(R.id.user_id_edit_text);
        mUserIdTextview = (TextView) findViewById(R.id.userid_textview);
        mLogoutBtn = (Button) findViewById(R.id.logout_btn);
        mLoginBtn = (Button) findViewById(R.id.login_btn);
        mLoginBtn.setOnClickListener(new OnClickListener() {
            @Override
            public void onClick(View view) {
                mLoginPresenter.checkNetworkStatus();
            }
        });
        mLogoutBtn.setOnClickListener(new OnClickListener() {
            @Override
            public void onClick(View view) {
                mLoginPresenter.userLogout();
            }
        });

        mLoginPresenter = new LoginPresenterImpl(this, LoginActivity.this);

    }


    @Override
    public void navigationToHomePage() {
        Intent userPostIntent = new Intent(LoginActivity.this, UserPostActivity.class);
        userPostIntent.putExtra(USER_ID, mUserIdEditText.getText().toString());
        startActivity(userPostIntent);
    }

    @Override
    public void displayValidationErrorMsg(String msg) {
        Toast.makeText(LoginActivity.this, msg, Toast.LENGTH_SHORT).show();

    }

    @Override
    public void showErrorMessage(String errorMsg) {
        Toast.makeText(LoginActivity.this, errorMsg, Toast.LENGTH_SHORT).show();
    }

    @Override
    public void netWorkNotFound(String message) {
        Toast.makeText(LoginActivity.this, message, Toast.LENGTH_SHORT).show();
    }

    @Override
    public void proceedForLogin() {
        mLoginPresenter.checkUserIDValidation();
    }

    @Override
    public void setLogOutOption() {
        mUserIdTextview.setVisibility(View.INVISIBLE);
        mUserIdEditText.setVisibility(View.INVISIBLE);
        mLogoutBtn.setVisibility(View.VISIBLE);
        mLoginBtn.setVisibility(View.INVISIBLE);
    }

    @Override
    public void setLoginOption() {
        mUserIdTextview.setVisibility(View.VISIBLE);
        mUserIdEditText.setVisibility(View.VISIBLE);
        mLoginBtn.setVisibility(View.VISIBLE);
        mLogoutBtn.setVisibility(View.INVISIBLE);
    }

    @Override
    public String getUserId() {
        return mUserIdEditText.getText().toString();
    }

    @Override
    public void showUserIdError(int resId) {
        Toast.makeText(LoginActivity.this, getString(resId), Toast.LENGTH_SHORT).show();

    }

}
