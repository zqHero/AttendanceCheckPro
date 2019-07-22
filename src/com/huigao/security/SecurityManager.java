package com.huigao.security;

import java.util.Map;

/**
 * 安全管理器
 * @author cgx<br>参考:downpour(je)
 */
public interface SecurityManager {
	
	/**
	 * 加载权限关系映射
	 * @return 映射
	 */
	public Map<String, String> loadUrlAuthorities();
	
}
