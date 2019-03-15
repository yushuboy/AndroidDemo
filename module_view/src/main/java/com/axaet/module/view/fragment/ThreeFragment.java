package com.axaet.module.view.fragment;


import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.axaet.module.view.MainActivity;
import com.axaet.module.view.R;
import com.axaet.module.view.base.BaseFragment;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;


/**
 * A simple {@link Fragment} subclass.
 * tag可以为null或者相对应的tag，flags只有0和1(POP_BACK_STACK_INCLUSIVE)两种情况
 * 如果tag为null，flags为0时，弹出回退栈中最上层的那个fragment。
 * 如果tag为null ，flags为1时，弹出回退栈中所有fragment。
 * 如果tag不为null，那就会找到这个tag所对应的fragment，flags为0时，弹出该
 * fragment以上的Fragment，如果是1，弹出该fragment（包括该fragment）以上的fragment。
 *
 * @author android
 */
public class ThreeFragment extends BaseFragment {

    private static final String TAG = "yushu";

    private View mRootView;

    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        Log.i(TAG, "ThreeFragment onCreateView    null = " + (mRootView == null));
        if (mRootView == null) {
            mRootView = inflater.inflate(R.layout.view_fragment_three, container, false);
        }
        return mRootView;
    }


    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        view.findViewById(R.id.btnPrevious).setOnClickListener(v -> {
            FragmentManager fm = getFragmentManager();
            if (fm != null) {
                // tag为null，flags为0时，弹出回退栈中最上层的那个fragment。
                fm.popBackStackImmediate(null, 0);
            }
        });
        view.findViewById(R.id.btnClearFragment).setOnClickListener(v -> {
            FragmentManager fm = getFragmentManager();
            if (fm != null) {
                //tag为null ，flags为1时，弹出回退栈中所有fragment
                fm.popBackStackImmediate(null, 1);
            }
        });
        view.findViewById(R.id.btnPopTag).setOnClickListener(v -> {
            FragmentManager fm = getFragmentManager();
            if (fm != null) {
                //tag不为null ，lags为0时，弹出该fragment以上的Fragment
                fm.popBackStackImmediate(FirstFragment.class.getSimpleName(), 0);
            }
        });
        view.findViewById(R.id.btnPopTag2).setOnClickListener(v -> {
            FragmentManager fm = getFragmentManager();
            if (fm != null) {
                //tag不为null ，lags为1时，弹出该fragment（包括该fragment）以上的fragment
                fm.popBackStackImmediate(SecondFragment.class.getSimpleName(), 1);
            }
        });
        view.findViewById(R.id.btnMainActivity).setOnClickListener(v -> {
            //所有Fragment会清空
            Intent intent = new Intent(getActivity(), MainActivity.class);
            intent.setFlags(Intent.FLAG_ACTIVITY_SINGLE_TOP | Intent.FLAG_ACTIVITY_CLEAR_TOP);
            startActivity(intent);
        });
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        Log.i(TAG, "ThreeFragment onDestroyView: ");
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
        Log.i(TAG, "ThreeFragment onDestroy: ");
    }
}
