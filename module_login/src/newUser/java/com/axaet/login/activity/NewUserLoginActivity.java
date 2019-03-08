package com.axaet.login.activity;

import android.os.Bundle;

import com.alibaba.android.arouter.facade.annotation.Route;
import com.axaet.login.R;
import com.axaet.module.base.common.ARouterURL;

import androidx.appcompat.app.AppCompatActivity;

/**
 * @author android
 */
@Route(path = ARouterURL.NEW_USER_LOGIN_ACTIVITY)
public class NewUserLoginActivity extends AppCompatActivity {


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.login_activity_new_user_login);

    }
}
