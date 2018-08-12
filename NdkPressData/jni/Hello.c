#include<stdio.h>
#include <android/log.h>
#include <string.h>
#include "com_example_test_DataProvider.h"

//�����ӡLog�ķ���
#define LOG_TAG "clog"
#define LOGD(...) __android_log_print(ANDROID_LOG_DEBUG, LOG_TAG, __VA_ARGS__)
#define LOGI(...) __android_log_print(ANDROID_LOG_INFO, LOG_TAG, __VA_ARGS__)

/**
 * ��Java���ַ���ת����C�����е��ַ�����
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
 * �����������ĺ�
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
 * ƴ���ַ���
 * Class:     com_example_test_DataProvider
 * Method:    sayHelloInC
 * Signature: (Ljava/lang/String;)Ljava/lang/String;
 */
JNIEXPORT jstring JNICALL Java_com_example_test_DataProvider_sayHelloInC
  (JNIEnv * env, jobject  jobj, jstring  str){

	char* c = "Hello I am c";
	//Java�е��ַ���C����ֱ��ʹ�ã���Ҫ����ת����C�����е��ַ�����
	char* str2 = Jstring2CStr(env,str);
	//ƴ��
	strcat(str2,c);
	//�����ַ����飬����Java�����ַ�����������ҪC�ĺ����������һ����
	return (*env)->NewStringUTF(env,str2);




}

/*
 * Class:     com_example_test_DataProvider
 * Method:    intMethod
 * Signature: ([I)[I
 */
JNIEXPORT jintArray JNICALL Java_com_example_test_DataProvider_intMethod
  (JNIEnv * env, jobject jobj, jintArray  jarray){
	// jArray  ��������   jint*       (*GetIntArrayElements)(JNIEnv*, jintArray, jboolean*);
	// ����ĳ���    jsize       (*GetArrayLength)(JNIEnv*, jarray);
	// ��������ÿ��Ԫ�� +5

	//��ȡ���鳤��
	int length = (*env)->GetArrayLength(env,jarray);
	//�õ�Java�е������ָ�����
	int* array = (*env)->GetIntArrayElements(env,jarray,0);
	//��������,ͨ��ָ�����
	int i=0;
	for(;i<length;i++){
		LOGD("%d\n",*(array+i));
		*(array+i)+=5;
		LOGD("%d\n",*(array+i));
	}

	return jarray;



}
