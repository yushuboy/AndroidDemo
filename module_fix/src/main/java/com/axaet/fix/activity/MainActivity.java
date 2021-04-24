package com.axaet.fix.activity;

import android.Manifest;
import android.os.Build;
import android.os.Bundle;
import android.widget.TextView;

import com.axaet.fix.R;
import com.tencent.mmkv.MMKV;

import androidx.appcompat.app.AppCompatActivity;

/**
 * @author android
 */
public class MainActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        String[] permission = new String[]{Manifest.permission.READ_PHONE_STATE, Manifest.permission.WRITE_EXTERNAL_STORAGE};
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
            requestPermissions(permission, 0x01);
        }
        TextView mTvShow = findViewById(R.id.mTvShow);
        MMKV.defaultMMKV().getInt("key",0);
//        mTvShow.setText("bug出现");
    }
}
