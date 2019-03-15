package com.axaet.module.view;

import android.content.Intent;
import android.os.Bundle;
import android.widget.Button;

import com.alibaba.android.arouter.facade.annotation.Route;
import com.axaet.module.base.common.ARouterURL;
import com.axaet.module.view.activity.ComplexRecyclerViewActivity;
import com.axaet.module.view.activity.FragmentStackActivity;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import butterknife.BindView;
import butterknife.ButterKnife;

/**
 * date: 2019/3/14
 *
 * @author yuShu
 */
@Route(path = ARouterURL.VIEW_MAIN_ACTIVITY)
public class MainActivity extends AppCompatActivity {

    private static final String TAG = "MainActivity";

    @BindView(R2.id.btnRecycleView)
    Button btnRecycleView;
    @BindView(R2.id.btnFragment)
    Button btnFragment;


    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.view_activity_main);
        ButterKnife.bind(this);
        btnRecycleView.setOnClickListener(v -> startActivity(new Intent(this, ComplexRecyclerViewActivity.class)));
        btnFragment.setOnClickListener(v -> startActivity(new Intent(this, FragmentStackActivity.class)));
    }
}
