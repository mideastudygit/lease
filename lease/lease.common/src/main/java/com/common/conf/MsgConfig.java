package com.common.conf;

import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.text.MessageFormat;
import java.util.Properties;

import com.base.log.Logging;

/**
 * 读取消息配置文件
 * 
 * @author 唐宗鸿
 *
 */
public class MsgConfig {

	private static Logging log = Logging.getLogging("utillog");

	private static Properties properties = new Properties();

	private MsgConfig() {

	}

	static {
		InputStream in = MsgConfig.class
				.getResourceAsStream("/message.properties");
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
	 * 读取配置文件中的值
	 * 
	 * @param key
	 * @param values
	 *            需要被替换的占位符{0}参数
	 * @return
	 * @author 唐宗鸿
	 */
	public static String get(String key, Object... values) {
		String msg = get(key);
		return msg == null ? null : MessageFormat.format(msg, values);
	}
}
