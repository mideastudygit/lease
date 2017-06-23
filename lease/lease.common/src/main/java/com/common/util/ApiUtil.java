package com.common.util;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

import com.base.utils.ParaMap;

/**
 * 接口数据输出格式化
 *
 */
public class ApiUtil {

	private ApiUtil() {

	}

	/**
	 * 格式成Json键值对
	 * 
	 * @param inMap
	 * @return
	 */
	public static ParaMap format(ParaMap inMap) {
		List<ParaMap> list = parse(inMap);
		if (list.isEmpty()) {
			return new ParaMap();
		}
		return list.get(0);
	}

	/**
	 * 格式成Json键值对的集合
	 * 
	 * @param inMap
	 * @return
	 */
	public static List<ParaMap> formatList(ParaMap inMap) {
		List<ParaMap> list = parse(inMap);
		return list;
	}

	/**
	 * 去掉fs和rs，转换成json格式
	 * 
	 * @param inMap
	 * @return
	 */
	public static List<ParaMap> parse(ParaMap inMap) {
		List<ParaMap> keys = (List<ParaMap>) inMap.getFields();
		ArrayList<ParaMap> outList = new ArrayList<ParaMap>();
		for (int i = 0, recordCount = inMap.getRecordCount(); i < recordCount; i++) {
			ParaMap item = new ParaMap();
			for (int j = 0, count = keys.size(); j < count; j++) {
				ParaMap key = keys.get(j);
				item.put(key.getString("name"), inMap.getRecordValue(i, j));
			}
			outList.add(item);
		}
		return outList;
	}

	/**
	 * 将集合转化为以关键字为键的Map
	 * 
	 * @param inList
	 * @param key
	 *            关键字
	 * @return
	 */
	public static ParaMap formatMap(List<ParaMap> inList, String key) {
		ParaMap resultMap = new ParaMap();
		Iterator<ParaMap> it = inList.iterator();
		while (it.hasNext()) {
			ParaMap tempMap = it.next();
			resultMap.put(tempMap.getString(key), tempMap);
		}
		return resultMap;
	}

}
