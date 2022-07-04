#include <string>
#include <jni.h>

int getKeySize() {
    return 128;
}
// Do not forget to dynamically load the C++ library into your application.
//
// For instance,
//
// In MainActivity.java:
//    static {
//       System.loadLibrary("mvicompose");
//    }
//
// Or, in MainActivity.kt:
//    companion object {
//      init {
//         System.loadLibrary("mvicompose")
//      }
//    }
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