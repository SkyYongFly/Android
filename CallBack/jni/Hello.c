#include<stdio.h>
#include "com_example_callback_DataProvider.h"

/*
 * Class:     com_example_callback_DataProvider
 * Method:    method1
 * Signature: ()V
 */
JNIEXPORT void JNICALL Java_com_example_callback_DataProvider_method1
(JNIEnv * env, jobject jobj) {
	/*
	 *Java中的反射
	 Class<?> forName = Class.forName("com.example.ndkcallback.DataProvider");
	 Method declaredMethod = forName.getDeclaredMethod("helloFromJava", new Class[]{});
	 declaredMethod.invoke(forName.newInstance(), new Object[]{});
	 */

	//加载Java中类
	jclass clazz = (*env)->FindClass(env,"com/example/callback/DataProvider");
	//加载 方法  注意最后一个方法签名，V要大写
	jmethodID methodId = (*env)->GetMethodID(env,clazz,"helloFromJava","()V");
	//调用方法里面的具体实现
	(*env)->CallVoidMethod(env,jobj,methodId);

}

/*
 * Class:     com_example_callback_DataProvider
 * Method:    method2
 * Signature: ()V
 */
JNIEXPORT void JNICALL Java_com_example_callback_DataProvider_method2
(JNIEnv * env, jobject jobj){
	//加载Java中类
	jclass clazz = (*env)->FindClass(env,"com/example/callback/DataProvider");
	//加载 方法  注意最后一个方法签名
	jmethodID  methodId = (*env)->GetMethodID(env,clazz,"add","(II)I");//注意观察，int (int,int) 的签名 （II）I
	//调用方法里面的具体实现，这里传入了两个参数
	(*env)->CallIntMethod(env,jobj,methodId,3,5);
}

/*
 * Class:     com_example_callback_DataProvider
 * Method:    method3
 * Signature: ()V
 */
JNIEXPORT void JNICALL Java_com_example_callback_DataProvider_method3
(JNIEnv * env, jobject jobj){
		//加载Java中类
		jclass clazz = (*env)->FindClass(env,"com/example/callback/DataProvider");
		//加载 方法  注意最后一个方法签名
		jmethodID  methodId = (*env)->GetMethodID(env,clazz,"strDemo","(Ljava/lang/String;)V");//注意观察，int (int,int) 的签名 （II）I
		//生成一个java 类型的字符串
		jstring str = (*env)->NewStringUTF(env,"hello");
		//调用方法里面的具体实现，这里传入了字符串
		(*env)->CallVoidMethod(env,jobj,methodId,str);
}
