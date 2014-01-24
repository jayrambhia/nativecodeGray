LOCAL_PATH := $(call my-dir)
 
include $(CLEAR_VARS)
 
include /home/jay/Android_OpenCV/OpenCV-2.4.8-android-sdk/sdk/native/jni/OpenCV.mk
 
LOCAL_MODULE    := nativegray
LOCAL_SRC_FILES := jni_part.cpp
LOCAL_LDLIBS +=  -llog -ldl
 
include $(BUILD_SHARED_LIBRARY)