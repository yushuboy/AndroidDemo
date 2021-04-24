//
// Created by android on 2019/3/11.
//
#include<jni.h>
#include<stdio.h>
#include <string.h>
#include "com_axaet_jni_activity_JniActivity.h"
#include <android/log.h>
#include <malloc.h>
#include<errno.h>


#define TAG    "jni-test" // 这个是自定义的LOG的标识
#define LOGD(...)  __android_log_print(ANDROID_LOG_DEBUG,TAG,__VA_ARGS__) // 定义LOGD类型

/**
 * 检测是否异常
 * @param env
 * @return
 */
int checkException(JNIEnv *env) {
    // 检查JNI调用是否有引发异常
    if ((*env)->ExceptionCheck(env)) {
        (*env)->ExceptionDescribe(env); // writes to logcat
        // 清除引发的异常，在Java层不会打印异常的堆栈信息
        (*env)->ExceptionClear(env);
        return 1;
    }
    return 0;
}

/**
 * 如果检测到异常，可以在JNI层将异常抛出到Java层进行处理
 * @param env
 * @param name 异常类名
 * @param msg  异常信息
 */
void JNU_ThrowByName(JNIEnv *env, const char *name, const char *msg) {
    // 查找异常类
    jclass cls = (*env)->FindClass(env, name);
    /* 如果这个异常类没有找到，VM会抛出一个NowClassDefFoundError异常 */
    if (cls != NULL) {
        (*env)->ThrowNew(env, cls, msg);  // 抛出指定名字的异常
    }
    /* 释放局部引用 */
    (*env)->DeleteLocalRef(env, cls);
}



/**
 *
 * 利用FindClass反射获取类对象,通过GetMethodID获取方法，
 * 其中方法签名可以用命令获取（build\intermediates\javac\debug\compileDebugJavaWithJavac\classes\com\axaet\jni\activity）
 * javap -s JniActivity.class
 * @param env
 * @param obj
 */
JNIEXPORT void JNICALL Java_com_axaet_jni_activity_JniActivity_showDialog
        (JNIEnv *env, jobject obj) {
    //打印log输出
    LOGD("我是C语言打印的debug日志");
    //通过反射来调用java的方法，需要知道方法签名，使用javap得到方法签名
    //在bin/classes目录下执行 javap -s 全类名
    //1、得到类的字节码对象,找到对应的类
    //jclass      (*FindClass)(JNIEnv*, const char*);
    jclass clazz = (*env)->FindClass(env, "com/axaet/jni/activity/JniActivity");

    // 每句jni执行之后都加入异常检查
    if (checkException(env)) {
        JNU_ThrowByName(env, "java/lang/Exception",
                        "exception from jni: found not JniActivity");
        return;
    }

    //jmethodID   (*GetMethodID)(JNIEnv*, jclass, const char*, const char*);
    jmethodID methodID = (*env)->GetMethodID(env, clazz, "show", "(Ljava/lang/String;)V");
    // 每句jni执行之后都加入异常检查
    if (checkException(env)) {
        JNU_ThrowByName(env, "java/lang/Exception",
                        "exception from jni: found not 'show' method");
        return;
    }

    //void        (*CallVoidMethod)(JNIEnv*, jobject, jmethodID, ...);
    LOGD("sock_fd is %d", errno);
    (*env)->CallVoidMethod(env, obj, methodID, (*env)->NewStringUTF(env, "这是C语言调用的弹框"));
}