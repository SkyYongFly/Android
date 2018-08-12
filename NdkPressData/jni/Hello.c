#include<stdio.h>
#include <android/log.h>
#include <string.h>
#include "com_example_test_DataProvider.h"

//定义打印Log的方法
#define LOG_TAG "clog"
#define LOGD(...) __android_log_print(ANDROID_LOG_DEBUG, LOG_TAG, __VA_ARGS__)
#define LOGI(...) __android_log_print(ANDROID_LOG_INFO, LOG_TAG, __VA_ARGS__)

/**
 * 把Java中字符串转化成C语言中的字符数组
 */
char*   Jstring2CStr(JNIEnv*   env,   jstring   jstr)
{
	 char*   rtn   =   NULL;
	 jclass   clsstring   =   (*env)->FindClass(env,"java/lang/String");
	 jstring   strencode   =   (*env)->NewStringUTF(env,"GB2312");
	 jmethodID   mid   =   (*env)->GetMethodID(env,clsstring,   "getBytes",   "(Ljava/lang/String;)[B");
	 jbyteArray   barr=   (jbyteArray)(*env)->CallObjectMethod(env,jstr,mid,strencode); // String .getByte("GB2312");
	 jsize   alen   =   (*env)->GetArrayLength(env,barr);
	 jbyte*   ba   =   (*env)->GetByteArrayElements(env,barr,JNI_FALSE);
	 if(alen   >   0)
	 {
	  rtn   =   (char*)malloc(alen+1);         //"\0"
	  memcpy(rtn,ba,alen);
	  rtn[alen]=0;
	 }
	 (*env)->ReleaseByteArrayElements(env,barr,ba,0);  //
	 return rtn;
}


/*
 * 计算两个数的和
 * Class:     com_example_test_DataProvider
 * Method:    sum
 * Signature: (II)I
 */
JNIEXPORT jint JNICALL Java_com_example_test_DataProvider_sum
  (JNIEnv * env, jobject  jobj, jint x, jint y){
	LOGD("x=%d",x);
	LOGI("y=%d",y);
	return x+y;

}

/*
 * 拼接字符串
 * Class:     com_example_test_DataProvider
 * Method:    sayHelloInC
 * Signature: (Ljava/lang/String;)Ljava/lang/String;
 */
JNIEXPORT jstring JNICALL Java_com_example_test_DataProvider_sayHelloInC
  (JNIEnv * env, jobject  jobj, jstring  str){

	char* c = "Hello I am c";
	//Java中的字符串C不能直接使用，需要将其转换成C语言中的字符数组
	char* str2 = Jstring2CStr(env,str);
	//拼接
	strcat(str2,c);
	//返回字符数组，但是Java中是字符串，所以需要C的函数来完成这一功能
	return (*env)->NewStringUTF(env,str2);




}

/*
 * Class:     com_example_test_DataProvider
 * Method:    intMethod
 * Signature: ([I)[I
 */
JNIEXPORT jintArray JNICALL Java_com_example_test_DataProvider_intMethod
  (JNIEnv * env, jobject jobj, jintArray  jarray){
	// jArray  遍历数组   jint*       (*GetIntArrayElements)(JNIEnv*, jintArray, jboolean*);
	// 数组的长度    jsize       (*GetArrayLength)(JNIEnv*, jarray);
	// 对数组中每个元素 +5

	//获取数组长度
	int length = (*env)->GetArrayLength(env,jarray);
	//得到Java中的数组的指针变量
	int* array = (*env)->GetIntArrayElements(env,jarray,0);
	//遍历数组,通过指针操作
	int i=0;
	for(;i<length;i++){
		LOGD("%d\n",*(array+i));
		*(array+i)+=5;
		LOGD("%d\n",*(array+i));
	}

	return jarray;



}
