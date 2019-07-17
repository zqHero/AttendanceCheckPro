package com.huigao.util;

import java.util.List;

/**
 * 字符串处理通用类
 * @author Administrator
 *
 */
public class StringUtils {
	
	public static String join(List<String> list, String str) {
		if(list == null || list.isEmpty()) return "";
		StringBuffer buf = new StringBuffer("");
		for (int i = 0; i < list.size(); i++) {
			if(i != 0) buf.append(str);
			buf.append(list.get(i)); 
		}
		return buf.toString();
	}
	
}
