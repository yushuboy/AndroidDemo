package com.axaet.jni.activity;

import android.annotation.SuppressLint;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.TextView;

import com.axaet.jni.R;

import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;

/**
 * @author android
 */
public class JniActivity extends AppCompatActivity {

    final int[] array = {1, 2, 3, 4, 5};

    @SuppressLint("SetTextI18n")
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.jni_activity_jni);
        TextView mTvEncrypt = findViewById(R.id.mTvEncrypt);
        TextView mTvDecrypt = findViewById(R.id.mTvDecrypt);
        TextView mTvOriginal = findViewById(R.id.mTvOriginal);
        String data = "123456789";
        mTvOriginal.setText("原始数据: " + data);
        String encrypt = EncryptUtil.encrypt(data);
        mTvEncrypt.setText("加密数据：" + encrypt);
        mTvDecrypt.setText("解密数据: " + EncryptUtil.decrypt(encrypt));

        int[] ints = EncryptUtil.encodeReturnArray(array);
        for (int i : ints) {
            Log.i("yushu", "i: " + i);
        }

        for (int i : array) {
            Log.i("yushu", "i: " + i);
        }

        findViewById(R.id.btnShowDialog).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                try {
                    showDialog();
                } catch (Exception e) {
                    e.printStackTrace();
                }
            }
        });
    }


    /**
     * C语言会调用该方法
     *
     * @param message c语言传递过来的数值
     */
    public void show(String message) {
        new AlertDialog.Builder(this)
                .setMessage(message)
                .show();
    }

    /**
     * jclass和jobject的区别
     * 静态方法传递到native层的是jclass（类对象），
     * 非静态方法传递到native的是jobject(实例对象)
     */
    private native void showDialog();
}
