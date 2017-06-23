package com.common.util;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

/**
 * 字符串工具类
 * 
 * @author 唐宗鸿
 */
public final class StringUtil {

	private StringUtil() {
	}

	/**
	 * 判断字符串是否为空
	 * 
	 * @param value
	 * @return
	 * @author 唐宗鸿
	 */
	public static boolean isEmpty(String value) {
		return value == null || value.equals("") ? true : false;
	}

	/**
	 * 将字符串首字母转成大写
	 * 
	 * @param value
	 * @return
	 * @author 唐宗鸿
	 */
	public static String toUpperCaseFirst(String value) {
		if (StringUtil.isEmpty(value)) {
			return value;
		}
		StringBuffer sb = new StringBuffer(value);
		Character c = sb.charAt(0);

		return c.toString().toUpperCase() + sb.deleteCharAt(0);
	}

	/**
	 * 查询value在array数组的下标位置
	 * 
	 * @param array
	 * @param value
	 * @return
	 * @author 唐宗鸿
	 */
	public static int indexOf(String[] array, String value) {
		for (int i = 0; i < array.length; i++) {
			if (array[i].equals(value)) {
				return i;
			}
		}
		return -1;
	}
	
	/**
	 * 判断一个字符串是否为正数
	 *
	 * @author 吴财宾
	 * @param str
	 * @return
	 */
	public static boolean isNumber(String str) 
    { 
        Pattern pattern=Pattern.compile("^(([1-9]{1}\\d*)|([0]{1}))(\\.(\\d){0,2})?$");
        Matcher match=pattern.matcher(str); 
        if(match.matches()==false) 
        { 
           return false; 
        } else { 
           return true; 
        } 
    }
}
