package com.axaet.jni.activity

import android.content.Intent
import android.os.Bundle
import android.util.Log
import androidx.appcompat.app.AppCompatActivity
import com.alibaba.android.arouter.facade.annotation.Route
import com.axaet.jni.R
import com.axaet.module.base.common.ARouterURL
import kotlinx.android.synthetic.main.jni_activity_main.*


@Route(path = ARouterURL.JNI_MAIN_ACTIVITY)
class MainActivity : AppCompatActivity() {


    private val array = intArrayOf(1, 2, 3, 4, 5)

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.jni_activity_main)
        btnJni.setOnClickListener {
            startActivity(Intent(this, JniActivity::class.java))
        }
        EncryptUtil.encodeArray(array)
        for (i in array) {
            Log.i("yushu", "i: $i")
        }
    }
}
