package com.example.net;

import java.io.InputStream;

import org.apache.commons.lang3.StringUtils;
import org.apache.http.HttpHost;
import org.apache.http.HttpResponse;
import org.apache.http.client.HttpClient;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.conn.params.ConnRouteParams;
import org.apache.http.entity.StringEntity;
import org.apache.http.impl.client.DefaultHttpClient;

import com.example.daomain.Config;

/**
 * 传输XML文件
 * 
 * @author yzas
 *
 */
@SuppressWarnings("deprecation")
public class HttpClientUtil {

	private HttpClient client;

	public HttpClientUtil() {
		client = new DefaultHttpClient();
		// 判断是否需要携带APN参数
		if (StringUtils.isNotBlank(Config.PROXY)) {
			HttpHost host = new HttpHost(Config.PROXY, Config.PORT);
			client.getParams().setParameter(ConnRouteParams.DEFAULT_PROXY, host);
		}
	}

	/**
	 * 发送xml文件到服务器端
	 * 
	 * @return
	 */
	public InputStream sendXml(String uri, String xmlString) {
		try {
			StringEntity entity = new StringEntity(xmlString, Config.CHARSET);
			HttpPost post = new HttpPost(uri);
			post.setEntity(entity);

			HttpResponse response = client.execute(post);
			if (response.getStatusLine().getStatusCode() == 200) {
				return response.getEntity().getContent();
			}

		} catch (Exception e) {
			e.printStackTrace();
		}
		return null;
	}
}
