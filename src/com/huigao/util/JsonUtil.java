package com.huigao.util;

import java.util.Collection;

import net.sf.json.JSONArray;
import net.sf.json.JSONObject;

/**
 * Json字符串处理通用类
 * @author cgx
 * @version 1.0
 */
public class JsonUtil {
	
	/**
	 * 将一个对象转换为json字符串
	 * @param o 对象
	 * @return 字符串
	 */
	public static String toJsonString(Object o) {
		if(o instanceof Collection) {
			StringBuffer buf = new StringBuffer("");
			Object[] objs = JSONArray.fromObject(o).toArray();
			for (int i = 0; i < objs.length; i ++) {
				if(i!=0) buf.append(",");
				buf.append(JSONObject.fromObject(objs[i]).toString());
			}
			return "{totalCount:" + objs.length + ",root:[" + buf.toString() + "]}";
		}
		return JSONObject.fromObject(o).toString();
	}
}
