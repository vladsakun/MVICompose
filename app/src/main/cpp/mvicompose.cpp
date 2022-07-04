#include <string>
#include <jni.h>

extern "C"
JNIEXPORT jstring JNICALL
Java_com_example_mvicompose_ui_feature_login_LoginViewModel_generateHelloString(JNIEnv *env,
                                                                                jobject thiz,
                                                                                jstring name) {
    jboolean isCopy;
    const char *convertedValue = (env)->GetStringUTFChars(name, &isCopy);
    std::string nameString = convertedValue;
    std::string helloString = "Hello, ";
    helloString.append(nameString);
    return (env)->NewStringUTF(helloString.c_str());
}
extern "C"
JNIEXPORT jint JNICALL
Java_com_example_mvicompose_cryptography_CryptographyManagerImpl_getKeySize(JNIEnv *env,
                                                                            jobject thiz) {
    return 128;
}