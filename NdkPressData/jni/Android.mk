LOCAL_PATH:= $(call my-dir)

include $(CLEAR_VARS)
#��Ӧ����ɺ����������
LOCAL_MODULE    := hello
#��Ӧc������ļ�
LOCAL_SRC_FILES := Hello.c
#C��ӡLog��
LOCAL_LDLIBS += -llog


include $(BUILD_SHARED_LIBRARY)
