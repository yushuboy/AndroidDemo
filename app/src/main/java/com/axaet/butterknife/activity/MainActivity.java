package com.axaet.butterknife.activity;

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
import com.axaet.butterknife.BuildConfig;
import com.axaet.butterknife.R;
import com.axaet.butterknife.application.MyApplication;
import com.axaet.module.base.common.ARouterURL;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import butterknife.ButterKnife;
import butterknife.OnClick;

/**
 * @author android
 */
@Route(path = ARouterURL.MAIN_ACTIVITY)
public class MainActivity extends AppCompatActivity {

    /**
     * 不同变体不同数值
     */
    public static final boolean THREE_PARTY_LOGIN = BuildConfig.THREE_PARTY_LOGIN;

    public static final int REQUEST_CODE = 0x01;
    private static final String TAG = "MainActivity";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        ButterKnife.bind(this);
        Application application = MyApplication.getInstance().getApplication();
        Log.i(TAG, THREE_PARTY_LOGIN + "   app name:  " + application.getClass().getName());
    }


    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (requestCode == REQUEST_CODE && resultCode == RESULT_OK && data != null) {
            String result = data.getStringExtra("result");
            Toast.makeText(this, result, Toast.LENGTH_SHORT).show();
        }
    }

    @OnClick({R.id.btnShow, R.id.button})
    public void onViewClicked(View view) {
        switch (view.getId()) {
            case R.id.btnShow:
                try {
                    Postcard postcard = ARouter.getInstance().build(ARouterURL.LOGIN_ACTIVITY);
                    LogisticsCenter.completion(postcard);
                    Class<?> destination = postcard.getDestination();
                    Log.i(TAG, destination.getName());
                    startActivityForResult(new Intent(this, destination), REQUEST_CODE);
                } catch (Exception e) {
                    Toast.makeText(this, "没有依赖该module", Toast.LENGTH_SHORT).show();
                }
                break;
            case R.id.button:
                ARouter.getInstance().build(ARouterURL.NEWS_MAIN)
                        .withString("msg", "从主module传递参数给其他module")
                        .withBoolean("boy", true)
                        .navigation();
                break;
            default:
        }
    }
}
