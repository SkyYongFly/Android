package com.example.utils;

import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.InputStream;

public class StreamUtil {

	/**
	 * 将输入流读取字符串输出
	 * @param in
	 * @return
	 * @throws IOException 
	 */
	public  static String readFromStream(InputStream in) throws IOException{
		ByteArrayOutputStream out = new ByteArrayOutputStream();
		byte[]  bt = new byte[1024];
		int length=0;
		while((length=in.read(bt))!=-1){
			out.write(bt,0,length);
		}
		in.close();
		String result =  out.toString();
		out.close();
		return result;
		
	}
}
