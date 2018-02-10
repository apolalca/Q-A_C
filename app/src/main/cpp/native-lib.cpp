#include <jni.h>
#include <string>

extern "C"
JNIEXPORT jstring JNICALL
Java_com_qanda_android_qa_ui_View_WelcomeActivity_stringFromJNI(JNIEnv *env, jobject instance) {
    std::string returnValue = "Hello from cpp";
    return env->NewStringUTF(returnValue.c_str());
}