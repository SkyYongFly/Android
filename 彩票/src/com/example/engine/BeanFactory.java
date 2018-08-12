package com.example.engine;

import java.io.IOException;
import java.util.Properties;

public class BeanFactory {
	
	private static Properties properties ;
	
	static {
		properties = new Properties();
		try {
			properties.load(BeanFactory.class.getClassLoader().getResourceAsStream("bean.properties"));
		} catch (IOException e) {
			e.printStackTrace();
		}
	}
	
	/**
	 * 获取类实例
	 * @param clazz
	 * @return
	 */
	@SuppressWarnings("unchecked")
	public static <T> T getClassImpl(Class<T> clazz){
		try{
		String name = clazz.getSimpleName();
		String clazzName = properties.getProperty(name);
		return (T) Class.forName(clazzName).newInstance();
		}catch(Exception e){
			e.printStackTrace();
		}
		return null;
	}
	

}
