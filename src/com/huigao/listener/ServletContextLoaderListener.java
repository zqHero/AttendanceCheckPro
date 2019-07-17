package com.huigao.listener;

import java.util.Map;

import javax.servlet.ServletContext;
import javax.servlet.ServletContextEvent;
import javax.servlet.ServletContextListener;

import org.apache.log4j.Logger;
import org.springframework.web.context.support.WebApplicationContextUtils;

/**
 * 系统启动的时候将权限信息加载到内存中
 * @author cgx
 * @version 1.0
 */
public class ServletContextLoaderListener implements ServletContextListener {  
	
	private Logger logger = Logger.getLogger(this.getClass());
	
    public void contextInitialized(ServletContextEvent servletContextEvent) {  
        ServletContext servletContext = servletContextEvent.getServletContext();  
        com.huigao.security.SecurityManager securityManager = this.getSecurityManager(servletContext);  
        Map<String, String> urlAuthorities = securityManager.loadUrlAuthorities();  
        servletContext.setAttribute("urlAuthorities", urlAuthorities);
        logger.debug(urlAuthorities); 
    }  
      
    public void contextDestroyed(ServletContextEvent servletContextEvent) {  
        servletContextEvent.getServletContext().removeAttribute("urlAuthorities");  
    }
    
    protected com.huigao.security.SecurityManager getSecurityManager(ServletContext servletContext) {  
       return (com.huigao.security.SecurityManager) WebApplicationContextUtils.getWebApplicationContext(servletContext).getBean("securityManager");   
    }  
  
}  
