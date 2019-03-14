package com.axaet.login.activity;

import android.os.Bundle;
import android.view.View;
import android.widget.TextView;
import android.widget.Toast;

import com.alibaba.android.arouter.facade.annotation.Autowired;
import com.alibaba.android.arouter.facade.annotation.Route;
import com.alibaba.android.arouter.launcher.ARouter;
import com.axaet.login.R;
import com.axaet.login.R2;
import com.axaet.login.bean.Person;
import com.axaet.module.base.common.ARouterURL;

import androidx.appcompat.app.AppCompatActivity;
import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;


/**
 * 获取控件一直用R2，但是点击事件时，比较ID时要用R，而且不能用switch只能用if
 * 在支持路由的页面上添加注解(必选)
 * 这里的路径需要注意的是至少需要有两级，/xx/xx
 *
 * @author android
 */
@Route(path = ARouterURL.LOGIN_MAIN)
public class MainActivity extends AppCompatActivity {


    @Autowired
    public String tag;

    @Autowired
    public Person person;

    @BindView(R2.id.textView)
    TextView textView;
    @BindView(R2.id.textView2)
    TextView textView2;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.login_activity_main);
        ButterKnife.bind(this);
        //获取其他组件传递过来的数组需要注入
        ARouter.getInstance().inject(this);

        textView.setText(tag);
        textView2.setText(person.toString());
    }


    @OnClick({R2.id.login_button, R2.id.login_button2})
    public void onViewClicked(View view) {
        final int i = view.getId();
        if (i == R.id.login_button) {
            Toast.makeText(this, "點擊事件1", Toast.LENGTH_SHORT).show();
        } else if (i == R.id.login_button2) {
            ARouter.getInstance().build(ARouterURL.NEW_USER_LOGIN_ACTIVITY)
                    .navigation();
        }
    }
}
