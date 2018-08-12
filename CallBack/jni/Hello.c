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
	 *Java�еķ���
	 Class<?> forName = Class.forName("com.example.ndkcallback.DataProvider");
	 Method declaredMethod = forName.getDeclaredMethod("helloFromJava", new Class[]{});
	 declaredMethod.invoke(forName.newInstance(), new Object[]{});
	 */

	//����Java����
	jclass clazz = (*env)->FindClass(env,"com/example/callback/DataProvider");
	//���� ����  ע�����һ������ǩ����VҪ��д
	jmethodID methodId = (*env)->GetMethodID(env,clazz,"helloFromJava","()V");
	//���÷�������ľ���ʵ��
	(*env)->CallVoidMethod(env,jobj,methodId);

}

/*
 * Class:     com_example_callback_DataProvider
 * Method:    method2
 * Signature: ()V
 */
JNIEXPORT void JNICALL Java_com_example_callback_DataProvider_method2
(JNIEnv * env, jobject jobj){
	//����Java����
	jclass clazz = (*env)->FindClass(env,"com/example/callback/DataProvider");
	//���� ����  ע�����һ������ǩ��
	jmethodID  methodId = (*env)->GetMethodID(env,clazz,"add","(II)I");//ע��۲죬int (int,int) ��ǩ�� ��II��I
	//���÷�������ľ���ʵ�֣����ﴫ������������
	(*env)->CallIntMethod(env,jobj,methodId,3,5);
}

/*
 * Class:     com_example_callback_DataProvider
 * Method:    method3
 * Signature: ()V
 */
JNIEXPORT void JNICALL Java_com_example_callback_DataProvider_method3
(JNIEnv * env, jobject jobj){
		//����Java����
		jclass clazz = (*env)->FindClass(env,"com/example/callback/DataProvider");
		//���� ����  ע�����һ������ǩ��
		jmethodID  methodId = (*env)->GetMethodID(env,clazz,"strDemo","(Ljava/lang/String;)V");//ע��۲죬int (int,int) ��ǩ�� ��II��I
		//����һ��java ���͵��ַ���
		jstring str = (*env)->NewStringUTF(env,"hello");
		//���÷�������ľ���ʵ�֣����ﴫ�����ַ���
		(*env)->CallVoidMethod(env,jobj,methodId,str);
}
