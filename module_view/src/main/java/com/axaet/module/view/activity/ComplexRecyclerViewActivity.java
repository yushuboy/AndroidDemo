package com.axaet.module.view.activity;

import android.os.Bundle;

import com.axaet.module.view.R;
import com.axaet.module.view.adapter.HomeMultiItemAdapter;
import com.axaet.module.view.beans.HomeMultiItem;

import java.util.ArrayList;
import java.util.List;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

/**
 * 复杂布局的recycleView
 *
 * @author android
 */
public class ComplexRecyclerViewActivity extends AppCompatActivity {

    private HomeMultiItemAdapter adapter;
    private final List<HomeMultiItem> mList = new ArrayList<>();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.view_activity_complex_recycler);
        initView();
        initData();
    }

    private void initData() {
        for (int i = 0; i < 2; i++) {
            //标题
            mList.add(new HomeMultiItem(HomeMultiItem.VIEW_TYPE_TITLE, "111", "111"));
            //viewPager
            mList.add(new HomeMultiItem(HomeMultiItem.VIEW_TYPE_VIEWPAGER, "222", "222"));
            //标题
            mList.add(new HomeMultiItem(HomeMultiItem.VIEW_TYPE_TITLE, "111", "111"));
            //单个item
            mList.add(new HomeMultiItem(HomeMultiItem.VIEW_TYPE_SINGLE_ITEM, "111", "111"));
            mList.add(new HomeMultiItem(HomeMultiItem.VIEW_TYPE_SINGLE_ITEM, "111", "111"));
            mList.add(new HomeMultiItem(HomeMultiItem.VIEW_TYPE_SINGLE_ITEM, "111", "111"));
            mList.add(new HomeMultiItem(HomeMultiItem.VIEW_TYPE_SINGLE_ITEM, "111", "111"));

            //标题
            mList.add(new HomeMultiItem(HomeMultiItem.VIEW_TYPE_TITLE, "111", "111"));
            //2个item
            mList.add(new HomeMultiItem(HomeMultiItem.VIEW_TYPE_TWO_ITEM, "222", "222"));
            mList.add(new HomeMultiItem(HomeMultiItem.VIEW_TYPE_TWO_ITEM, "222", "222"));
            mList.add(new HomeMultiItem(HomeMultiItem.VIEW_TYPE_TWO_ITEM, "222", "222"));
            //标题
            mList.add(new HomeMultiItem(HomeMultiItem.VIEW_TYPE_TITLE, "111", "111"));
            //多个item
            mList.add(new HomeMultiItem(HomeMultiItem.VIEW_TYPE_MULTIPLE_ITEM, "333", "333"));
            mList.add(new HomeMultiItem(HomeMultiItem.VIEW_TYPE_MULTIPLE_ITEM, "333", "333"));
            mList.add(new HomeMultiItem(HomeMultiItem.VIEW_TYPE_MULTIPLE_ITEM, "333", "333"));
            mList.add(new HomeMultiItem(HomeMultiItem.VIEW_TYPE_MULTIPLE_ITEM, "333", "333"));
            mList.add(new HomeMultiItem(HomeMultiItem.VIEW_TYPE_MULTIPLE_ITEM, "333", "333"));
            mList.add(new HomeMultiItem(HomeMultiItem.VIEW_TYPE_MULTIPLE_ITEM, "333", "333"));
            mList.add(new HomeMultiItem(HomeMultiItem.VIEW_TYPE_MULTIPLE_ITEM, "333", "333"));
            mList.add(new HomeMultiItem(HomeMultiItem.VIEW_TYPE_MULTIPLE_ITEM, "333", "333"));
        }
        adapter.setNewData(mList);
    }

    private void initView() {
        RecyclerView recyclerView = findViewById(R.id.mRecyclerView);
        adapter = new HomeMultiItemAdapter(new ArrayList<>(), getSupportFragmentManager());
        recyclerView.setAdapter(adapter);
        GridLayoutManager gridLayoutManager = new GridLayoutManager(this, 6);
        //需要先設置設配器才起作用
        gridLayoutManager.setSpanSizeLookup(new GridLayoutManager.SpanSizeLookup() {
            @Override
            public int getSpanSize(int position) {
                int viewType = adapter.getItemViewType(position);
                if (viewType == HomeMultiItem.VIEW_TYPE_SINGLE_ITEM) {
                    return 6;
                } else if (viewType == HomeMultiItem.VIEW_TYPE_VIEWPAGER) {
                    //这个宽度
                    return 6;
                } else if (viewType == HomeMultiItem.VIEW_TYPE_MULTIPLE_ITEM) {
                    //三分之一
                    return 2;
                } else if (viewType == HomeMultiItem.VIEW_TYPE_TWO_ITEM) {
                    //对半
                    return 3;
                }
                return 6;
            }
        });
        recyclerView.setLayoutManager(gridLayoutManager);
    }
}
