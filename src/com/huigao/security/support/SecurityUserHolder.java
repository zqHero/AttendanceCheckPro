/**
 * 
 */
package com.huigao.security.support;

import org.springframework.security.context.SecurityContextHolder;

import com.huigao.pojo.Users;

/**
 * @author cgx<br>参考:downpour(je)
 */
public class SecurityUserHolder {

	/**
	 * 获取当前用户
	 * @return 用户
	 */
	public static Users getCurrentUser() {
		return (Users) SecurityContextHolder.getContext().getAuthentication().getPrincipal();
	}

}
