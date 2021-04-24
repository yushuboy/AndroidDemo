package com.axaet.jni.activity;

/**
 * date: 2019/3/11
 *
 * @author yuShu
 */
public class EncryptUtil {

    /**
     * 加密string
     *
     * @param msg data
     * @return
     */
    public static native String encrypt(String msg);

    /**
     * 解密string
     *
     * @param msg data
     * @return
     */
    public static native String decrypt(String msg);


    /**
     * GetIntArrayElements
     * 传递数组其实是传递一个堆内存的数组首地址的引用过去，所以实际操作的是同一块内存，
     * 当调用完方法，不需要返回值,实际上参数内容已经改变，
     * Android中很多操作硬件的方法都是这种C语言的传引用的思路，要非常熟练
     *
     * @param arr 数组
     */
    public static native void encodeArray(int[] arr);

    /**
     * GetIntArrayRegion
     * 把Java传过去的数据复制一份再操作
     *
     * @param arr 数组
     * @return
     */
    public static native int[] encodeReturnArray(int[] arr);


    // Used to load the 'native-lib' library on application startup.
    static {
        System.loadLibrary("native-lib");
    }
}
