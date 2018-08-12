package com.example.secret.net;

import org.json.JSONException;
import org.json.JSONObject;

import com.example.secret.net.NetConnection.FaltureCallback;
import com.example.secret.net.NetConnection.SuccessCallBack;

import android.util.Log;

/**
 * µÇÂ½
 * @author yzas
 *
 */
public class Login {
 
	protected static final String TAG ="test";

	public Login(final String url,final HttpMethod method,final SuccessLoginCallback successLoginCallBack,final FailtureLoginCallback failtureLoginCallback,String ... params){
		NetConnection.connection(url, method, new SuccessCallBack() {
			
			@Override
			public void onSuccess(String result) {
				Log.d(TAG,result);
				try {
					JSONObject jObject  = new JSONObject(result);
					
					if(successLoginCallBack!=null){
						Log.d(TAG,jObject.getString("code"));
						successLoginCallBack.onSuccess(jObject.getString("code"));
					}else{
						failtureLoginCallback.onFail();
					}
					
					
					
				} catch (JSONException e) {
					e.printStackTrace();
					Log.d(TAG,"JSONException");
					failtureLoginCallback.onFail();
				}
				
				
				
				
				
			}
		}, new FaltureCallback() {
			@Override
			public void onFail() {
				Log.d(TAG,"fail netconnection");
				if(failtureLoginCallback!=null){
					failtureLoginCallback.onFail();
				}
			}
		}, params);
		
		
		
	}

	
	public static interface SuccessLoginCallback{
		void onSuccess(String token);
	}
	
	public static interface FailtureLoginCallback{
		void onFail();
	}
}
