package com.example.campus;

import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;

import com.example.campus.bean.User;
import com.example.campus.utils.LogUtils;
import com.example.campus.view.DialogPrompt;

import java.util.Date;

import cn.bmob.v3.datatype.BmobDate;
import cn.bmob.v3.exception.BmobException;
import cn.bmob.v3.listener.SaveListener;

public class RegisterActivity extends BaseActivity {
    private static final String TAG = "RegisterActivity";

    TextView tvToolbarText;
    EditText etLoginUsername;
    EditText etLoginPassword;
    EditText etLoginComfirmPassword;
    Button btnRegister;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_register);
        initUI();
        tvToolbarText.setText(getString(R.string.register));
        btnRegister.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                register();
            }
        });
    }

    private void initUI() {
        tvToolbarText = findViewById(R.id.tv_toolbarText);
        etLoginUsername = (EditText) findViewById(R.id.et_login_username);
        etLoginPassword = (EditText) findViewById(R.id.et_login_password);
        etLoginComfirmPassword = (EditText) findViewById(R.id.et_login_comfirmPassword);
        btnRegister = (Button) findViewById(R.id.btn_register);
    }

    private void register() {
        String name = etLoginUsername.getText().toString();
        String pwd = etLoginPassword.getText().toString();
        String pwdConfirm = etLoginComfirmPassword.getText().toString();
        if (name.isEmpty()) {
            DialogPrompt dialogPrompt = new DialogPrompt(RegisterActivity.this, R.string.user_name_is_not_empty);
            dialogPrompt.show();
            return;
        }
        if (pwd.isEmpty()) {
            DialogPrompt dialogPrompt = new DialogPrompt(RegisterActivity.this, R.string.please_input_password);
            dialogPrompt.show();
            return;
        }
        if (!pwd.equals(pwdConfirm)) {
            DialogPrompt dialogPrompt = new DialogPrompt(RegisterActivity.this, R.string.password_new_different);
            dialogPrompt.show();
            return;
        }
        User user = new User();
        user.setUsername(name);
        user.setPassword(pwd);
//        user.setLevel(1);
        user.setSex("保密");
        user.setSignature(getString(R.string.signature));
//        user.setAbo("2B");
        user.setBirthday(new BmobDate(new Date()));
//        user.setPronoun("一级萌新");
//        user.setConstellation("天秤座");
//        user.setLevelScore(1);
        //提交注册
        user.signUp(new SaveListener<User>() {
            @Override
            public void done(User user, BmobException e) {
                if (e == null) {
                    DialogPrompt dialogPrompt = new DialogPrompt(RegisterActivity.this, R.string.register_ok, 3);
                    dialogPrompt.showAndFinish(RegisterActivity.this);
                } else {
                    LogUtils.i(TAG, new Throwable(), e.getErrorCode() + ":" + e.getMessage());
                    if (e.getErrorCode() == 202) {
                        DialogPrompt dialogPrompt = new DialogPrompt(RegisterActivity.this, R.string.register_error_user_already_taken);
                        dialogPrompt.show();
                    } else {
                        DialogPrompt dialogPrompt = new DialogPrompt(RegisterActivity.this, getString(R.string.register_error) + "，错误码：" + e.getErrorCode() + "," + e.getMessage());
                        dialogPrompt.show();
                    }
                }
            }
        });
    }
}
