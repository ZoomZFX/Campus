package com.example.campus;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.example.campus.utils.LogUtils;
import com.example.campus.utils.UiTools;
import com.example.campus.view.DialogPrompt;

import cn.bmob.v3.BmobUser;
import cn.bmob.v3.exception.BmobException;
import cn.bmob.v3.listener.SaveListener;

public class LoginActivity extends BaseActivity {
    private static final String TAG = "LoginActivity";

    private EditText etLoginUsername;
    private EditText etLoginPassword;
    private Button btnLongin;
    private TextView tvRegisterAccount;
    private TextView tvToolbarText;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);
        initUI();
        tvToolbarText.setText(getString(R.string.login));

        btnLongin.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                doLogin();
            }
        });
        tvRegisterAccount.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(LoginActivity.this, RegisterActivity.class);
                startActivity(intent);
            }
        });
    }

    private void doLogin() {
        String userName = etLoginUsername.getText().toString();
        String password = etLoginPassword.getText().toString();
        if (userName.isEmpty()) {
            DialogPrompt dialogPrompt = new DialogPrompt(LoginActivity.this, R.string.please_input_user_name);
            dialogPrompt.show();
            return;
        }
        if (password.isEmpty()) {
            DialogPrompt dialogPrompt = new DialogPrompt(LoginActivity.this, R.string.please_input_password);
            dialogPrompt.show();
            return;
        }
        UiTools.showSimpleLD(this, R.string.loading_login);
        //异步执行登录
        BmobUser bombUser = new BmobUser();
        bombUser.setUsername(userName);
        bombUser.setPassword(password);
        bombUser.login(new SaveListener<BmobUser>() {
            @Override
            public void done(BmobUser bmobUser, BmobException e) {
                UiTools.closeSimpleLD();
                if (e == null) {
                    //登录成功
                    //打开吧，新世界的大门
                    Toast.makeText(LoginActivity.this, R.string.login_successful, Toast.LENGTH_LONG).show();
                    LoginActivity.this.setResult(RESULT_OK);
                    LoginActivity.this.finish();
                } else {
                    LogUtils.e(TAG, new Throwable(), "登录失败" + e.getErrorCode() + ":" + e.getMessage());
                    //登录失败
                    if (e.getErrorCode() == 101) {
                        DialogPrompt dialogPrompt = new DialogPrompt(LoginActivity.this, R.string.login_error_name_or_password_incorrect);
                        dialogPrompt.show();
                    } else {
                        DialogPrompt dialogPrompt = new DialogPrompt(LoginActivity.this, LoginActivity.this.getString(R.string.login_error) + "：" + e.getErrorCode() + "，" + e.getMessage());
                        dialogPrompt.show();
                    }
                }
            }
        });
    }

    private void initUI() {
        tvToolbarText=(TextView)findViewById(R.id.tv_toolbarText);
        etLoginUsername=(EditText)findViewById(R.id.et_login_username);
        etLoginPassword=(EditText)findViewById(R.id.et_login_password);
        btnLongin=(Button)findViewById(R.id.btn_longin);
        tvRegisterAccount=(TextView)findViewById(R.id.tv_registerAccount);
    }
}
