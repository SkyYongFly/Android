#include <stdio.h>
#include <jni.h>
#include "com_example_jni_MainActivity.h"

//头文件中生成的函数，就是java中的print函数
JNIEXPORT jstring JNICALL Java_com_example_jni_MainActivity_print
  (JNIEnv *env, jobject  obj, jstring str)
{
	//调用函数库函数，返回字符串
	return (*env)->NewStringUTF(env,"Hello I am C");
}
