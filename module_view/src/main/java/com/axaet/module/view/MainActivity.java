package com.axaet.module.view;

import android.content.Intent;
import android.content.res.Configuration;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.view.ViewConfiguration;

import com.alibaba.android.arouter.facade.annotation.Route;
import com.axaet.module.base.common.ARouterURL;
import com.axaet.module.view.activity.ComplexRecyclerViewActivity;
import com.axaet.module.view.activity.FragmentStackActivity;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import butterknife.ButterKnife;
import butterknife.OnClick;

/**
 * date: 2019/3/14
 *
 * @author yuShu
 */
@Route(path = ARouterURL.VIEW_MAIN_ACTIVITY)
public class MainActivity extends AppCompatActivity {

    private static final String TAG = "MainActivity";


    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        Log.i(TAG, "onCreate: ");
        setContentView(R.layout.view_activity_main);
        ButterKnife.bind(this);
        int touchSlop = ViewConfiguration.get(this).getScaledTouchSlop();
        Log.i(TAG, "touchSlop: " + touchSlop);
    }

    @OnClick({R2.id.btnRecycleView, R2.id.btnFragment, R2.id.btnAboutView})
    public void onViewClicked(View view) {
        int viewId = view.getId();
        if (viewId == R.id.btnRecycleView) {
            startActivity(new Intent(this, ComplexRecyclerViewActivity.class));
        } else if (viewId == R.id.btnFragment) {
            startActivity(new Intent(this, FragmentStackActivity.class));
        } else if (viewId == R.id.btnAboutView) {
            int left = view.getLeft();
            int right = view.getRight();
            Log.i(TAG, "相对父View的位置:  left："+left+"  right:"+right);
        }
    }

    @Override
    public void onConfigurationChanged(@NonNull Configuration newConfig) {
        super.onConfigurationChanged(newConfig);
        Log.i(TAG, "onConfigurationChanged: " + newConfig.orientation);
        Log.i(TAG, "onConfigurationChanged: " + newConfig.locale.getLanguage());
    }

    @Override
    protected void onResume() {
        super.onResume();
        Log.i(TAG, "onResume: ");
    }

    @Override
    protected void onPause() {
        super.onPause();
        Log.i(TAG, "onPause: ");
    }


    @Override
    protected void onRestoreInstanceState(Bundle savedInstanceState) {
        super.onRestoreInstanceState(savedInstanceState);
        Log.i(TAG, "onRestoreInstanceState: ");
    }

    @Override
    protected void onStop() {
        super.onStop();
        Log.i(TAG, "onStop: ");
    }

    @Override
    protected void onRestart() {
        super.onRestart();
        Log.i(TAG, "onRestart: ");
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        Log.i(TAG, "onDestroy: ");
    }

}
