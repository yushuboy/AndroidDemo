package com.axaet.module.view.fragment;

import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.axaet.module.view.R;
import com.axaet.module.view.base.BaseFragment;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentTransaction;


/**
 * @author android
 */
public class FirstFragment extends BaseFragment {

    private static final String TAG = "yushu";
    private View mRootView;
    private TextView mTvShow;

    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        Log.i(TAG, "FirstFragment onCreateView    null = " + (mRootView == null));
        if (mRootView == null) {
            mRootView = inflater.inflate(R.layout.view_fragment_first, container, false);
        }
        mRootView.findViewById(R.id.btnPrevious).setOnClickListener(v -> {
            FragmentManager fm = getFragmentManager();
            if (fm != null) {
                FragmentTransaction tx = fm.beginTransaction();
                SecondFragment secondFragment = new SecondFragment();
                //转场动画
                tx.setTransition(FragmentTransaction.TRANSIT_FRAGMENT_FADE);
                tx.replace(R.id.content, secondFragment, SecondFragment.class.getSimpleName());
                //将当前的事务添加到了回退栈
                tx.addToBackStack(SecondFragment.class.getSimpleName());
                tx.commit();
            }
        });
        mTvShow = mRootView.findViewById(R.id.mTvShow);
        return mRootView;
    }


    public void showText(String msg) {
        if (mTvShow != null) {
            mTvShow.setText(msg);
        }
    }


    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        Log.i(TAG, "FirstFragment onViewCreated: ");
        if (savedInstanceState != null) {
            //重建时恢复数据
            String key = savedInstanceState.getString("key");
            mTvShow.setText(key);
            Log.i(TAG, "FirstFragment onViewCreated  key: " + key);
        }
    }

    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
        Log.i(TAG, "FirstFragment onActivityCreated: ");
        if (savedInstanceState != null) {
            int key = savedInstanceState.getInt("key");
            Log.i(TAG, "FirstFragment onActivityCreated  key: " + key);
        }
    }


    @Override
    public void onSaveInstanceState(@NonNull Bundle outState) {
        super.onSaveInstanceState(outState);
        Log.i(TAG, "FirstFragment onSaveInstanceState: ");
        if (mTvShow != null) {
            //异常销毁时保存数据
            String msg = mTvShow.getText().toString();
            outState.putString("key", msg);
        }
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        Log.i(TAG, "FirstFragment onDestroyView: ");
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
        Log.i(TAG, "FirstFragment onDestroy: ");
    }


}
