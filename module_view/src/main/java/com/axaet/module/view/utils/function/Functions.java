package com.axaet.module.view.utils.function;

import android.os.Bundle;

import java.util.Map;

import androidx.collection.ArrayMap;

public class Functions {


    /**
     * 有参数没有返回值的函数
     */
    public static final String FUNCTION_HAS_PARAM_NO_RESULT = "FUNCTION_HAS_PARAM_NO_RESULT";

    /**
     * 有参数有返回值的函数
     */
    public static final String FUNCTION_HAS_PARAM_WITH_RESULT = "FUNCTION_HAS_PARAM_WITH_RESULT";

    /**
     * 没有参数有返回值的函数
     */
    public static final String FUNCTION_NO_PARAM_WITH_RESULT = "FUNCTION_NO_PARAM_WITH_RESULT";

    /**
     * 没有参数没有返回值的函数
     */
    public static final String FUNCTION_NO_PARAM_NO_RESULT = "FUNCTION_NO_PARAM_NO_RESULT";

    /**
     * 带有参数和返回值的方法
     *
     * @param <Result>
     * @param <Param>
     */
    public static abstract class FunctionWithParamAndResult<Result, Param> extends Function {
        public FunctionWithParamAndResult(String functionName) {
            super(functionName);
        }

        public abstract Result function(Param data);
    }

    /**
     * 没有参数和返回值的方法
     */
    public static abstract class Function {
        public String mFunctionName;

        public Function(String functionName) {
            this.mFunctionName = functionName;
        }
    }

    /**
     * 有返回值，没有参数的方法
     *
     * @param <Result>
     */
    public static abstract class FunctionWithResult<Result> extends Function {
        public FunctionWithResult(String functionName) {
            super(functionName);
        }

        public abstract Result function();
    }

    /**
     * 带有参数没有返回值的方法
     *
     * @param <Param>
     */
    public static abstract class FunctionWithParam<Param> extends Function {
        public FunctionWithParam(String functionName) {
            super(functionName);
        }

        public abstract void function(Param param);
    }

    /**
     * 没有参数和返回值的方法
     */
    public static abstract class FunctionNoParamAndResult extends Function {
        public FunctionNoParamAndResult(String functionName) {
            super(functionName);
        }

        public abstract void function();
    }

    private Map<String, FunctionWithParam> mFunctionWithParam;
    private Map<String, FunctionWithResult> mFunctionWithResult;
    private Map<String, FunctionNoParamAndResult> mFunctionNoParamAndResult;
    private Map<String, FunctionWithParamAndResult> mFunctionWithParamAndResult;


    /**
     * 添加带参数的函数
     *
     * @param function {@link FunctionWithParam}
     * @return
     */
    public Functions addFunction(FunctionWithParam function) {
        if (function == null) {
            return this;
        }
        if (mFunctionWithParam == null) {
            mFunctionWithParam = new ArrayMap<>(1);
        }
        mFunctionWithParam.put(function.mFunctionName, function);

        return this;
    }

    /**
     * 添加带返回值的函数
     *
     * @param function {@link FunctionWithResult}
     * @return
     */
    public Functions addFunction(FunctionNoParamAndResult function) {
        if (function == null) {
            return this;
        }
        if (mFunctionNoParamAndResult == null) {
            mFunctionNoParamAndResult = new ArrayMap<>(1);
        }
        mFunctionNoParamAndResult.put(function.mFunctionName, function);

        return this;
    }

    /**
     * 添加既有参数又有返回值的函数
     *
     * @param function {@link FunctionWithParamAndResult}
     * @return
     */
    public Functions addFunction(FunctionWithParamAndResult function) {
        if (function == null) {
            return this;
        }
        if (mFunctionWithParamAndResult == null) {
            mFunctionWithParamAndResult = new ArrayMap<>(1);
        }
        mFunctionWithParamAndResult.put(function.mFunctionName, function);

        return this;
    }

    /**
     * 添加带有返回值的函数
     *
     * @param function {@link FunctionWithResult}
     * @return
     */
    public Functions addFunction(FunctionWithResult function) {
        if (function == null) {
            return this;
        }
        if (mFunctionWithResult == null) {
            mFunctionWithResult = new ArrayMap<>(1);
        }
        mFunctionWithResult.put(function.mFunctionName, function);

        return this;
    }

    /**
     * 根据函数名，回调无参无返回值的函数
     *
     * @param funcName
     */
    public void invokeFunc(String funcName) throws FunctionException {
        FunctionNoParamAndResult f = null;
        if (mFunctionNoParamAndResult != null) {
            f = mFunctionNoParamAndResult.get(funcName);
            if (f != null) {
                f.function();
            }
        }
        if (f == null) {
            throw new FunctionException("没有此函数");
        }
    }

    /**
     * 根据函数名，回调无参有返回值的函数
     *
     * @param funcName
     */
    public <Result> Result invokeFuncWithResult(String funcName, Class<Result> c) throws FunctionException {
        FunctionWithResult f = null;
        if (mFunctionWithResult != null) {
            f = mFunctionWithResult.get(funcName);
            if (f != null) {
                if (c != null) {
                    return c.cast(f.function());
                } else {
                    return (Result) f.function();
                }
            }
        }
        throw new FunctionException("没有此函数");
    }

    /**
     * 调用具有参数的函数
     *
     * @param funcName
     * @param param
     * @param <Param>
     */
    public <Param> void invokeFunc(String funcName, Param param) throws FunctionException {
        FunctionWithParam f;
        if (mFunctionWithParam != null) {
            f = mFunctionWithParam.get(funcName);
            if (f != null) {
                f.function(param);
            }
        }
    }

    /**
     * 调用具有参数，同时具有返回值的函数
     *
     * @param funcName
     * @param param
     * @param <Result>
     * @param <Param>
     * @return
     */
    public <Result, Param> Result invokeFuncWithResult(String funcName, Param param, Class<Result> c) throws FunctionException {
        FunctionWithParamAndResult f;
        if (mFunctionWithParamAndResult != null) {
            f = mFunctionWithParamAndResult.get(funcName);
            if (f != null) {
                if (c != null) {
                    return c.cast(f.function(param));
                } else {
                    return (Result) f.function(param);
                }
            }
        }
        throw new FunctionException("没有此函数");
    }

    /**
     * 函数的参数，当函数的参数涉及到多个值时，可以用此类，
     * 此类使用规则：存参数与取参数的顺序必须一致，否则报错
     */
    public static class FunctionParams {

        private int mIndex = -1;
        private final Bundle mParams;
        private final Map mObjectParams;

        FunctionParams(Bundle mParams, Map mObjectParams) {
            this.mParams = mParams;
            this.mObjectParams = mObjectParams;
        }

        public <Param> Param getObject(Class<Param> p) {
            if (mObjectParams == null) {
                return null;
            }
            return p.cast(mObjectParams.get((mIndex++) + ""));
        }

        /**
         * 获取int值
         *
         * @return
         */
        public int getInt() {
            if (mParams != null) {
                return mParams.getInt((mIndex++) + "");
            }
            return 0;
        }

        /**
         * 获取int值
         *
         * @param defaultValue
         * @return
         */
        public int getInt(int defaultValue) {
            if (mParams != null) {
                return mParams.getInt((mIndex++) + "");
            }
            return defaultValue;
        }

        /**
         * 获取字符串
         *
         * @param defaultValue
         * @return
         */
        public String getString(String defaultValue) {
            if (mParams != null) {
                return mParams.getString((mIndex++) + "");
            }
            return defaultValue;
        }

        /**
         * 获取字符串
         *
         * @return
         */
        public String getString() {
            if (mParams != null) {
                return mParams.getString((mIndex++) + "");
            }
            return null;
        }


        /**
         * 获取Boolean值
         *
         * @return 默认返回false
         */
        public boolean getBoolean() {
            if (mParams != null) {
                return mParams.getBoolean((mIndex++) + "");
            }
            return false;
        }

        /**
         * 该类用来创建函数参数
         */
        public static class FunctionParamsBuilder {
            private Bundle mParams;
            private int mIndex = -1;
            private Map<String, Object> mObjectParams = new ArrayMap<>(1);

            public FunctionParamsBuilder() {
            }

            public FunctionParamsBuilder putInt(int value) {
                if (mParams == null) {
                    mParams = new Bundle(2);
                }
                mParams.putInt((mIndex++) + "", value);
                return this;
            }

            public FunctionParamsBuilder putString(String value) {
                if (mParams == null) {
                    mParams = new Bundle(2);
                }
                mParams.putString((mIndex++) + "", value);
                return this;
            }

            public FunctionParamsBuilder putBoolean(boolean value) {
                if (mParams == null) {
                    mParams = new Bundle(2);
                }
                mParams.putBoolean((mIndex++) + "", value);
                return this;
            }

            public FunctionParamsBuilder putObject(Object value) {
                if (mObjectParams == null) {
                    mObjectParams = new ArrayMap<>(1);
                }
                mObjectParams.put((mIndex++) + "", value);
                return this;
            }

            public FunctionParams create() {
                return new FunctionParams(mParams, mObjectParams);
            }
        }
    }

}
