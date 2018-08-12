package com.example.secret.net;

import com.example.secret.daomain.Config;
import com.example.secret.net.NetConnection.FaltureCallback;
import com.example.secret.net.NetConnection.SuccessCallBack;

/**
 * �����������ȡ��Ϣ
 * @author yzas
 *
 */
public class MessageRequest {

	/**
	 * ������Ϣ
	 * @param cachePhone  ������ֻ���
	 * @param i    	      ����ڼ�ҳ������
	 * @return			������Ϣ�б�
	 */
	public static void getMessages(String cachePhone, int i,final  SuccessRequestMessage successRequestMessage,final FailtureRequestMessage failtureRequestMessage) {
		
		NetConnection.connection(Config.URI
				,HttpMethod.GET,new SuccessCallBack() {
					
					@Override
					public void onSuccess(String result) {
						if(successRequestMessage!=null){
							successRequestMessage.onSuccess(result);
						}
					}
				}, new FaltureCallback() {
					
					@Override
					public void onFail() {
						if(failtureRequestMessage!=null){
							failtureRequestMessage.onFail();
						}
					}
				},Config.CONNECT_ACTION,Config.ACTION_REQUEST_MESSAGE,
				  Config.Phone_NUM,cachePhone,
				  Config.REQUEST_MESSAGE_PAGE,i+"");
		
	}
	
	public static interface SuccessRequestMessage{
		void  onSuccess(String reString);
	}
	
	public static interface FailtureRequestMessage{
		void  onFail();
	}

}
