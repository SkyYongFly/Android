#include <stdio.h>
#include <jni.h>
#include "com_example_jni_MainActivity.h"

//ͷ�ļ������ɵĺ���������java�е�print����
JNIEXPORT jstring JNICALL Java_com_example_jni_MainActivity_print
  (JNIEnv *env, jobject  obj, jstring str)
{
	//���ú����⺯���������ַ���
	return (*env)->NewStringUTF(env,"Hello I am C");
}
