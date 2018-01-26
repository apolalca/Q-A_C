#include <jni.h>
#include <string>

extern "C"
JNIEXPORT jstring

JNICALL
Java_com_qanda_android_qa_MainActivity_stringFromJNI(
        JNIEnv *env,
        jobject /* this */) {
    std::string hello = "Hello from C++";
    return env->NewStringUTF(hello.c_str());
}

extern "C"
JNIEXPORT jstring JNICALL
Java_es_iesnervion_qa_ui_View_WelcomeActivity_stringFromJNI(JNIEnv *env) {
    std::string hello = "Hello from CPP";
    return env->NewStringUTF(hello.c_str());
}