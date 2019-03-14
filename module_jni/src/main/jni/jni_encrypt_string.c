//
// Created by android on 2019/3/11.
//
#include<jni.h>
#include<stdio.h>
#include <string.h>
#include "com_axaet_jni_activity_EncryptUtil.h"
#include <android/log.h>
#include <malloc.h>

#define TAG    "jni-test" // 这个是自定义的LOG的标识
#define LOGD(...)  __android_log_print(ANDROID_LOG_DEBUG,TAG,__VA_ARGS__) // 定义LOGD类型

/**
 * 将java字符串转换为c语言字符串（工具方法）
 * @param env
 * @param jstr
 * @return
 */
char *Jstring2CStr(JNIEnv *env, jstring jstr) {
    char *rtn = NULL;
    jclass clsString = (*env)->FindClass(env, "java/lang/String");
    jstring strEncode = (*env)->NewStringUTF(env, "utf-8");
    jmethodID mid = (*env)->GetMethodID(env, clsString, "getBytes", "(Ljava/lang/String;)[B");
    jbyteArray barr = (jbyteArray) (*env)->CallObjectMethod(env, jstr, mid,
                                                            strEncode); // String .getByte("GB2312");
    jsize aLen = (*env)->GetArrayLength(env, barr);
    jbyte *ba = (*env)->GetByteArrayElements(env, barr, JNI_FALSE);
    if (aLen > 0) {
        rtn = (char *) malloc((size_t) (aLen + 1));         //"\0"
        memcpy(rtn, ba, (size_t) aLen);
        rtn[aLen] = 0;
    }
    (*env)->ReleaseByteArrayElements(env, barr, ba, 0);
    return rtn;
}


/*
 * 加密
 * Class:     com_axaet_jni_activity_EncryptUtil
 * Method:    encrypt
 * Signature: (Ljava/lang/String;)Ljava/lang/String;
 */
JNIEXPORT jstring JNICALL
Java_com_axaet_jni_activity_EncryptUtil_encrypt
        (JNIEnv *env, jclass cls, jstring text) {
    //获取字符串长度
    jsize size = (*env)->GetStringLength(env, text);
    char *cstr = Jstring2CStr(env, text);
    for (int i = 0; i < size; i++) {
        //加密算法，将字符串每个字符加1
        *(cstr + i) += 1;
    }
    return (*env)->NewStringUTF(env, cstr);
}

/*
 * 解密
 * Class:     com_axaet_jni_activity_EncryptUtil
 * Method:    decrypt
 * Signature: (Ljava/lang/String;)Ljava/lang/String;
 */
JNIEXPORT jstring JNICALL Java_com_axaet_jni_activity_EncryptUtil_decrypt
        (JNIEnv *env, jclass cls, jstring text) {

    //获取字符串长度
    jsize size = (*env)->GetStringLength(env, text);

    char *cstr = Jstring2CStr(env, text);
    for (int i = 0; i < size; i++) {
        //解密算法，将字符串每个字符减1
        *(cstr + i) -= 1;
    }
    return (*env)->NewStringUTF(env, cstr);
}

/*
 * 直接操作指针
 * Class:     com_axaet_jni_activity_EncryptUtil
 * Method:    encodeArray
 * Signature: ([I)[I
 */
JNIEXPORT void JNICALL Java_com_axaet_jni_activity_EncryptUtil_encodeArray
        (JNIEnv *env, jclass cls, jintArray arr) {
    //拿到整型数组的长度以及第0个元素的地址
    //jsize       (*GetArrayLength)(JNIEnv*, jarray);
    int length = (*env)->GetArrayLength(env, arr);
    // jint*       (*GetIntArrayElements)(JNIEnv*, jintArray, jboolean*);
    int *arrp = (*env)->GetIntArrayElements(env, arr, 0);
    for (int i = 0; i < length; i++) {
        *(arrp + i) += 10; //将数组中的每个元素加10
    }
    //释放指针，防止内存泄漏
    (*env)->ReleaseIntArrayElements(env, arr, arrp, 0);
}



/*
 * 复制一份再操作数值
 * Class:     com_axaet_jni_activity_EncryptUtil
 * Method:    encodeReturnArray
 * Signature: ([I)[I
 */
JNIEXPORT jintArray JNICALL Java_com_axaet_jni_activity_EncryptUtil_encodeReturnArray
        (JNIEnv *env, jclass cls, jintArray arr) {
    //拿到整型数组的长度以及第0个元素的地址
    //jsize       (*GetArrayLength)(JNIEnv*, jarray);
    int length = (*env)->GetArrayLength(env, arr);
    //声明int数组
    int data[length];
    //拷贝数据到data数组里,保护原数据防止被破坏
    (*env)->GetIntArrayRegion(env, arr, 0, length, data);

    for (int i = 0; i < length; i++) {
        *(data + i) += 10; //将数组中的每个元素加10
        LOGD("data:  %1d", data[i]);
    }
    jintArray iarr = (*env)->NewIntArray(env, length);
    (*env)->SetIntArrayRegion(env, iarr, 0, length, data);
    return iarr;
}