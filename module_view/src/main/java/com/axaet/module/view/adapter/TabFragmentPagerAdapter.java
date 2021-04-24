package com.axaet.module.view.adapter;

import android.util.Log;

import java.util.List;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentPagerAdapter;

/**
 * fragment+viewpager
 * date: 2019/1/18
 *
 * @author yuShu
 */
public class TabFragmentPagerAdapter extends FragmentPagerAdapter {


    private final List<Fragment> fragments;
    private static final String TAG = "TabFragmentPagerAdapter";


    public TabFragmentPagerAdapter(FragmentManager fm, List<Fragment> fragments) {
        super(fm);
        this.fragments = fragments;
    }

    @NonNull
    @Override
    public Fragment getItem(int i) {
        Log.i(TAG, "getItem: " + i);
        return fragments.get(i);
    }

    @Override
    public int getCount() {
        return this.fragments == null ? 0 : this.fragments.size();
    }


}
