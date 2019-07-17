/**
 * 
 */
package com.huigao.security.interceptor;

import java.util.Map;

import javax.servlet.ServletContext;
import javax.servlet.http.HttpServletRequest;

import org.springframework.web.context.support.WebApplicationContextUtils;
/**
 * 权限修改处理
 * @author cgx
 * @version 1.0
 */
public class SecurityChangeOperation {
    
	/**
	 * 权限修改操作
	 * @param request 请求对象
	 */
	public static void operate(HttpServletRequest request) {  
		ServletContext servletContext = request.getSession().getServletContext();  
		com.huigao.security.SecurityManager securityManager = SecurityChangeOperation.getSecurityManager(servletContext);  
		Map<String, String> urlAuthorities = securityManager.loadUrlAuthorities();  
		servletContext.removeAttribute("urlAuthorities"); 
		servletContext.setAttribute("urlAuthorities", urlAuthorities);
	}
	
	/**
	 * 获取安全管理器
	 * @param servletContext servlet上下文
	 * @return 安全管理器
	 */
    public static com.huigao.security.SecurityManager getSecurityManager(ServletContext servletContext) {  
       return (com.huigao.security.SecurityManager) WebApplicationContextUtils.getWebApplicationContext(servletContext).getBean("securityManager");   
    }
}
