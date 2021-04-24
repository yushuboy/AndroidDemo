package com.axaet.module.view.base;

import android.content.Context;

import com.axaet.module.view.utils.function.Functions;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;

/**
 * date: 2019/3/15
 *
 * @author yuShu
 */
public abstract class BaseFragment extends Fragment {

    /**
     * 函数的集合
     */
    protected Functions mFunctions;

    /**
     * activity调用此方法进行设置Functions
     *
     * @param functions functions
     */
    public void setFunctions(Functions functions) {
        this.mFunctions = functions;
    }


    @Override
    public void onAttach(@NonNull Context context) {
        super.onAttach(context);
        if (context instanceof OnAddFunctionCallbacks) {
            OnAddFunctionCallbacks functionMethod = (OnAddFunctionCallbacks) context;
            functionMethod.setFunctionsForFragment(getTag());
        }
    }


    public interface OnAddFunctionCallbacks {
        /**
         * 设置回调函数
         *
         * @param tag fragment tag
         */
        void setFunctionsForFragment(String tag);
    }

}
