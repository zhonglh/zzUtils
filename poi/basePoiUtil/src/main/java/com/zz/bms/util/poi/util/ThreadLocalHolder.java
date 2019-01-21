package com.zz.bms.util.poi.util;

import java.util.Map;

/**
 * @author Administrator
 */
public class ThreadLocalHolder {


	private static ThreadLocal<Map<String,Map<String,String>>> clzThreadLocal = new ThreadLocal<Map<String,Map<String,String>>>(){
		public Map<String,Map<String,String>> initialValue(){
			return null;
		}
	};
	private static ThreadLocal<String> accessLogIds = new ThreadLocal<String>(){
		public String initialValue(){
			return null;
		}
	};

	public static ThreadLocal<Map<String, Map<String, String>>> getClzThreadLocal() {
		return clzThreadLocal;
	}

	public static void setClzThreadLocal(ThreadLocal<Map<String, Map<String, String>>> clzThreadLocal) {
		ThreadLocalHolder.clzThreadLocal = clzThreadLocal;
	}


	public static void setDynamicMap(Map<String,Map<String,String>> map){
		clzThreadLocal.set(null);
		clzThreadLocal.set(map);
	}
	

	public static Map<String,Map<String,String>> getDynamicMap(){
		return clzThreadLocal.get();
	}



	public static String getAccessLogId() {
		return accessLogIds.get();
	}

	public static void setAccessLogId(String accessLogId) {
		accessLogIds.set(null);
		accessLogIds.set(accessLogId);
	}

}
