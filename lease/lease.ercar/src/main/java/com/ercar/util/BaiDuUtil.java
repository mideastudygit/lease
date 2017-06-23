package com.ercar.util;

import com.base.utils.ParaMap;
import com.common.util.HttpUtil;


/**
 * 百度工具类
 * 
 */
public class BaiDuUtil {
	
	public static final String BAIDU_MAP_AK = "BaGqhrzBmFlpVNi5PdoOZyMaic8XBwKx";
	
	public static final String BAIDU_URL = "http://api.map.baidu.com/geocoder/v2/";
	
	public static final String OUTPUT = "json";
	
	/**
	 * 根据经纬度查询地址（百度地图）
	 * 
	 * @param lat 纬度
	 * @param lng 经度
	 * @return
	 * @throws Exception 
	 */
    public static String getLocationInfo(String lat, String lng) throws Exception {
    	ParaMap inMap = new ParaMap();
    	inMap.put("location", lat+","+lng);
    	inMap.put("output", OUTPUT);
    	inMap.put("ak", BAIDU_MAP_AK);
    	String data = HttpUtil.getData(BAIDU_URL, inMap);
        return data;  
    }
	
    /**
	 * 根据地址获取经纬度
	 * 
	 * @param address 
	 * @return 
	 * 			lng-经度，lat-纬度，status-状态(0：有返回经纬度数据，1：无匹配的经纬度)，msg-返回描述信息 
     * @throws Exception 
	 */
    public static String getLngAndLat(String address) throws Exception{
        ParaMap inMap = new ParaMap();
    	inMap.put("address", address);
    	inMap.put("output", OUTPUT);
    	inMap.put("ak", BAIDU_MAP_AK);
    	String data = HttpUtil.getData(BAIDU_URL, inMap);
        return data;
    }

    public static void main(String[] args) throws Exception {
    	
    	String address = "宝山";
    	String outMap = getLngAndLat(address);
    	System.out.println(outMap);
    	String lat = "22.5748728670";
    	String lng = "113.8774799369";
    	String json = BaiDuUtil.getLocationInfo(lat, lng);
    	System.out.println(json);
    }  
}
