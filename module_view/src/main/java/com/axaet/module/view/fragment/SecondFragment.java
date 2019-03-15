package com.axaet.module.view.fragment;

import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;

import com.axaet.module.view.R;
import com.axaet.module.view.base.BaseFragment;
import com.axaet.module.view.utils.function.FunctionException;
import com.axaet.module.view.utils.function.Functions;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentTransaction;


/**
 * @author android
 */
public class SecondFragment extends BaseFragment {

    private View mRootView;
    private static final String TAG = "yushu";

    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        Log.i(TAG, "SecondFragment onCreateView    null = " + (mRootView == null));
        if (mRootView == null) {
            mRootView = inflater.inflate(R.layout.view_fragment_second, container, false);
        }
        return mRootView;
    }


    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        view.findViewById(R.id.btnPrevious).setOnClickListener(v -> {
            FragmentManager fm = getFragmentManager();
            if (fm != null) {
                ThreeFragment f2 = new ThreeFragment();
                FragmentTransaction tx = fm.beginTransaction();
                //转场
                tx.setCustomAnimations(android.R.anim.slide_in_left, android.R.anim.slide_out_right);
                tx.replace(R.id.content, f2, ThreeFragment.class.getSimpleName());
                //将当前的事务添加到了回退栈
                tx.addToBackStack(ThreeFragment.class.getSimpleName());
                tx.commit();
            }
        });

        view.findViewById(R.id.btnHasParamAndResult).setOnClickListener(v -> {
            String[] data = new String[1];
            data[0] = "来自SecondFragment的数据：1000";
            try {
                String result = mFunctions.invokeFuncWithResult(Functions.FUNCTION_HAS_PARAM_WITH_RESULT,
                        data, String.class);
                Toast.makeText(getActivity(), result, Toast.LENGTH_SHORT).show();
            } catch (FunctionException e) {
                e.printStackTrace();
            }
        });

        view.findViewById(R.id.btnHasParamNoResult).setOnClickListener(v -> {
            try {
                mFunctions.invokeFunc(Functions.FUNCTION_HAS_PARAM_NO_RESULT, "来自SecondFragment的数据：111");
            } catch (FunctionException e) {
                e.printStackTrace();
            }
        });

        view.findViewById(R.id.btnNoParamHasResult).setOnClickListener(v -> {
            try {
                String result = mFunctions.invokeFuncWithResult(Functions.FUNCTION_NO_PARAM_WITH_RESULT, String.class);
                Toast.makeText(getActivity(), result, Toast.LENGTH_SHORT).show();
            } catch (FunctionException e) {
                e.printStackTrace();
            }
        });
        view.findViewById(R.id.btnNoParamNoResult).setOnClickListener(v -> {
            try {
                mFunctions.invokeFunc(Functions.FUNCTION_NO_PARAM_NO_RESULT);
            } catch (FunctionException e) {
                e.printStackTrace();
            }
        });
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        Log.i(TAG, "SecondFragment onDestroyView: ");
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
        Log.i(TAG, "SecondFragment onDestroy: ");
    }
}
