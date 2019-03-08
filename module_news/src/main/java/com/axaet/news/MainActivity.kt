package com.axaet.news

import android.annotation.SuppressLint
import android.os.Bundle
import android.util.Log
import androidx.appcompat.app.AppCompatActivity
import com.alibaba.android.arouter.facade.annotation.Autowired
import com.alibaba.android.arouter.facade.annotation.Route
import com.alibaba.android.arouter.launcher.ARouter
import com.axaet.module.base.common.ARouterURL
import kotlinx.android.synthetic.main.news_activity_main.*

@Route(path = ARouterURL.NEWS_MAIN)
class MainActivity : AppCompatActivity() {

    @JvmField
    @Autowired
    var boy: Boolean = false

    @JvmField
    @Autowired
    var msg: String? = null


    @SuppressLint("SetTextI18n")
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.news_activity_main)
        //获取其他组件传递过来的数组需要注入
        ARouter.getInstance().inject(this)
        Log.i(TAG, "boy=$boy")
        mTvShow.text = "$msg   boy=$boy"
    }

    companion object {
        const val TAG = "MainActivity"
    }
}
