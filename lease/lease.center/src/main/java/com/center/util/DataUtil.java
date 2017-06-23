package com.center.util;

import java.util.Iterator;
import java.util.List;

import com.base.utils.ParaMap;
import com.common.util.ApiUtil;

public class DataUtil {

	/**
	 * 将srcMap中的keys值填充到targetMap
	 * 
	 * @param targetMap
	 * @param srcMap
	 * @keys
	 * @return
	 */
	public static void format(ParaMap targetMap, ParaMap srcMap, String[] keys) {
		for (String key : keys) {
			Object value = srcMap.get(key);
			if (value == null) {
				targetMap.put(key, "");
			} else {
				targetMap.put(key, value);
			}
		}
	}

	/**
	 * 将srcList集合中含有的keys值填充到targetList集合中
	 * 
	 * @param targetList
	 * @param srcList
	 * @param keys
	 * @return
	 */
	public static void formatList(List<ParaMap> targetList, List<ParaMap> srcList, String[] keys) {
		ParaMap srcMap = null;
		if (srcList != null && !srcList.isEmpty()) {
			srcMap = ApiUtil.formatMap(srcList, keys[0]);
		}
		Iterator<ParaMap> it = targetList.iterator();
		while (it.hasNext()) {
			ParaMap targetMap = it.next();
			ParaMap tempMap = null;
			if (srcMap != null) {
				tempMap = ((ParaMap) srcMap.get(targetMap.getString(keys[0])));
			}
			for (int i = 1; i < keys.length; i++) {
				if (tempMap == null) {
					targetMap.put(keys[i], "");
				} else {
					targetMap.put(keys[i], tempMap.get(keys[i]));
				}
			}
		}
	}
}
