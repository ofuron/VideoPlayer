#include <jni.h>
#include <string>

extern "C"
jstring
Java_io_ofu_android_videoplayer_VideoPlayerActivity_getVideoUrl(
        JNIEnv *env,
        jobject /* this */) {
    std::string url = "http://techslides.com/demos/sample-videos/small.mp4";
    return env->NewStringUTF(url.c_str());
}


