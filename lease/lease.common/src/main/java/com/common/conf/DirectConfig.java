package com.common.conf;

import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.util.Properties;
import java.util.Set;

import com.base.log.Logging;

/**
 * 读取HTTP访问配置权限
 * 
 * @author 唐宗鸿
 *
 */
public class DirectConfig {

	private static Logging log = Logging.getLogging("utillog");

	private static Properties properties = new Properties();

	private DirectConfig() {

	}

	static {
		InputStream in = DirectConfig.class
				.getResourceAsStream("/direct.properties");
		try {
			properties.load(new InputStreamReader(in, "UTF-8"));
		} catch (IOException e) {
			log.error(e.getMessage() + ":" + e.getStackTrace());
		}
	}

	/**
	 * 读取配置文件中的值
	 * 
	 * @param key
	 * @return
	 * @author 唐宗鸿
	 */
	public static String get(String key) {
		return properties.getProperty(key);
	}

	/**
	 * 获取所有键值
	 * 
	 * @return
	 * @author 唐宗鸿
	 */
	public static Set<Object> getAllKeys() {
		return properties.keySet();
	}
}
