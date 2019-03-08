package com.axaet.login.activity;

import android.app.Application;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Toast;

import com.alibaba.android.arouter.core.LogisticsCenter;
import com.alibaba.android.arouter.facade.Postcard;
import com.alibaba.android.arouter.facade.annotation.Route;
import com.alibaba.android.arouter.launcher.ARouter;
import com.axaet.login.R;
import com.axaet.login.R2;
import com.axaet.login.application.LoginApplicationImpl;
import com.axaet.login.bean.Person;
import com.axaet.module.base.common.ARouterURL;

import androidx.appcompat.app.AppCompatActivity;
import butterknife.ButterKnife;
import butterknife.OnClick;

/**
 * @author android
 */
@Route(path = ARouterURL.LOGIN_ACTIVITY)
public class LoginActivity extends AppCompatActivity {


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.login_activity_login);
        ButterKnife.bind(this);
        Application application = LoginApplicationImpl.getInstance().getApplication();
        Log.i("yushu", "onCreate: "+application.getClass().getName());
    }


    @OnClick({R2.id.login_button3, R2.id.login_button4})
    public void onViewClicked(View view) {
        final int id = view.getId();
        if (id == R.id.login_button3) {
            //跳转并携带参数
            ARouter.getInstance().build(ARouterURL.LOGIN_MAIN)
                    .withParcelable("person", new Person(18, "yushu"))
                    .withString("tag", "888")
                    .navigation();
        } else if (id == R.id.login_button4) {
            try {
                Postcard postcard = ARouter.getInstance().build(ARouterURL.MAIN_ACTIVITY);
                LogisticsCenter.completion(postcard);
                Class<?> destination = postcard.getDestination();
                Intent intent = new Intent(this, destination);
                intent.putExtra("result", "这是组件返回的结果");
                setResult(RESULT_OK, intent);
                finish();
            } catch (Exception e) {
                Toast.makeText(this, "没有对应的Activity返回", Toast.LENGTH_SHORT).show();
                e.printStackTrace();
            }
        }
    }
}
