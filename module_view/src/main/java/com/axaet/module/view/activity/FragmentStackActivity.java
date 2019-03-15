package com.axaet.module.view.activity;

import android.os.Bundle;
import android.util.Log;

import com.axaet.module.view.R;
import com.axaet.module.view.base.BaseFragment;
import com.axaet.module.view.fragment.FirstFragment;
import com.axaet.module.view.fragment.SecondFragment;
import com.axaet.module.view.utils.function.Functions;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentTransaction;

/**
 * date: 2019/3/14
 *
 * @author yuShu
 */
public class FragmentStackActivity extends AppCompatActivity implements BaseFragment.OnAddFunctionCallbacks {


    private static final String TAG = "yushu";


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.view_fragment_stack_activity);
        FragmentManager manager = getSupportFragmentManager();
        FragmentTransaction transaction = manager.beginTransaction();
        if (savedInstanceState == null) {
            FirstFragment firstFragment = new FirstFragment();
            transaction.setCustomAnimations(android.R.anim.fade_in, android.R.anim.fade_out);
            //R.id.content为占位布局
            transaction.add(R.id.content, firstFragment, FirstFragment.class.getSimpleName());
            //将当前的事务添加到了回退栈
            transaction.addToBackStack(FirstFragment.class.getSimpleName());
            transaction.commit();
        } else {
            int num = getSupportFragmentManager().getBackStackEntryCount();
            Log.i(TAG, "Fragment BackStackEntryCount: " + num);
        }
    }


    @Override
    public void onBackPressed() {
        super.onBackPressed();
        if (getSupportFragmentManager().getBackStackEntryCount() == 0) {
            super.onBackPressed();
        }
    }


    @Override
    public void setFunctionsForFragment(String tag) {
        FragmentManager manager = getSupportFragmentManager();
        BaseFragment fragment = (BaseFragment) manager.findFragmentByTag(tag);
        if (fragment != null) {
            if (SecondFragment.class.getSimpleName().equals(tag)) {
                Functions functions = new Functions().addFunction(
                        //没有参数，没有返回值
                        new Functions.FunctionNoParamAndResult(Functions.FUNCTION_NO_PARAM_NO_RESULT) {
                            @Override
                            public void function() {
                                Log.i(TAG, "function: 没有参数，没有返回值");
                            }
                        })
                        .addFunction(
                                //有参数，没有返回值
                                new Functions.FunctionWithParam<String>(Functions.FUNCTION_HAS_PARAM_NO_RESULT) {
                                    @Override
                                    public void function(String s) {
                                        //把数据传递给FirsFragment
                                        FirstFragment firstFragment = (FirstFragment) manager.findFragmentByTag(FirstFragment.class.getSimpleName());
                                        if (firstFragment != null) {
                                            firstFragment.showText(s);
                                        }
                                        Log.i(TAG, "function: 有参数，没有返回值   参数:" + s);
                                    }
                                }
                        )
                        .addFunction(
                                //没有参数，有返回值
                                new Functions.FunctionWithResult<String>(Functions.FUNCTION_NO_PARAM_WITH_RESULT) {
                                    @Override
                                    public String function() {
                                        Log.i(TAG, "function: 没有参数，有返回值");
                                        return "這是Activity返回值";
                                    }
                                }
                        )
                        .addFunction(
                                //有参数，有返回值
                                new Functions.FunctionWithParamAndResult<String, String[]>(Functions.FUNCTION_HAS_PARAM_WITH_RESULT) {
                                    @Override
                                    public String function(String[] data) {
                                        Log.i(TAG, "function: 有参数，有返回值   参数" + data[0]);
                                        return "這是Activity返回值";
                                    }
                                }
                        );
                fragment.setFunctions(functions);
            }
        }
    }


    @Override
    protected void onSaveInstanceState(@NonNull Bundle outState) {
        super.onSaveInstanceState(outState);
        Log.i(TAG, "FragmentStackActivity onSaveInstanceState: ");
    }

    @Override
    protected void onRestoreInstanceState(Bundle savedInstanceState) {
        super.onRestoreInstanceState(savedInstanceState);
        Log.i(TAG, "FragmentStackActivity onRestoreInstanceState: ");
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        Log.i(TAG, "FragmentStackActivity onDestroy");
    }
}
