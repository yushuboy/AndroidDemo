package com.axaet.module.view.adapter;

import android.util.Log;
import android.widget.Toast;

import com.axaet.module.view.R;
import com.axaet.module.view.beans.HomeMultiItem;
import com.axaet.module.view.fragment.BlankFragment;
import com.axaet.module.view.fragment.ItemFragment;
import com.chad.library.adapter.base.BaseMultiItemQuickAdapter;
import com.chad.library.adapter.base.BaseViewHolder;

import java.util.ArrayList;
import java.util.List;

import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.viewpager.widget.ViewPager;

/**
 * recycleView复杂布局适配器
 * date: 2019/1/19
 *
 * @author yuShu
 */
public class HomeMultiItemAdapter extends BaseMultiItemQuickAdapter<HomeMultiItem, BaseViewHolder> {

    private final FragmentManager mFragmentManager;

    /**
     * Same as QuickAdapter#QuickAdapter(Context,int) but with
     * some initialization data.
     *
     * @param data A new list is created out of this one to avoid mutable list
     */
    public HomeMultiItemAdapter(List<HomeMultiItem> data, FragmentManager fragmentManager) {
        super(data);
        this.mFragmentManager = fragmentManager;

        addItemType(HomeMultiItem.VIEW_TYPE_SINGLE_ITEM, R.layout.view_item_single_item);

        addItemType(HomeMultiItem.VIEW_TYPE_VIEWPAGER, R.layout.view_item_view_pager);

        addItemType(HomeMultiItem.VIEW_TYPE_MULTIPLE_ITEM, R.layout.view_item_multiple_item);

        addItemType(HomeMultiItem.VIEW_TYPE_TITLE, R.layout.view_item_title);

        addItemType(HomeMultiItem.VIEW_TYPE_TWO_ITEM, R.layout.view_item_single_item);
    }

    @Override
    protected void convert(final BaseViewHolder helper, final HomeMultiItem item) {
        helper.itemView.setOnClickListener(v -> Toast.makeText(helper.itemView.getContext(),
                "数值：" + item.getTitle() + "    position: " + helper.getLayoutPosition(), Toast.LENGTH_SHORT).show());
        int itemViewType = helper.getItemViewType();
        //viewpager适配
        if (itemViewType == HomeMultiItem.VIEW_TYPE_VIEWPAGER) {
            Log.i(TAG, "convert: " + itemViewType);
            ViewPager viewPager = helper.getView(R.id.mViewPager);
            List<Fragment> list = new ArrayList<>();
            list.add(BlankFragment.newInstance("ViewPager1", "333"));
            list.add(BlankFragment.newInstance("ViewPager2", "444"));
            list.add(ItemFragment.newInstance(1));
            list.add(ItemFragment.newInstance(2));
            viewPager.removeAllViews();
            //ViewPager在同一个页面中不允许ID共享，也就是说ViewPager不允许存在相同的id，否则就会出现异常！
            viewPager.setId(helper.getAdapterPosition());
            viewPager.setAdapter(new TabFragmentPagerAdapter(mFragmentManager, list));
        }
    }
}
