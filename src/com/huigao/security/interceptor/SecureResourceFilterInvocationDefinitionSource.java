/**
 * 
 */
package com.huigao.security.interceptor;

import java.util.Collection;
import java.util.Iterator;
import java.util.Map;

import javax.servlet.ServletContext;

import org.springframework.beans.factory.InitializingBean;
import org.springframework.security.ConfigAttributeDefinition;
import org.springframework.security.ConfigAttributeEditor;
import org.springframework.security.intercept.web.FilterInvocation;
import org.springframework.security.intercept.web.FilterInvocationDefinitionSource;
import org.springframework.security.util.AntUrlPathMatcher;
import org.springframework.security.util.RegexUrlPathMatcher;
import org.springframework.security.util.UrlMatcher;

/**
 * spring security 权限控制拦截器
 * @author cgx<br>参考:downpour(je)
 */
public class SecureResourceFilterInvocationDefinitionSource implements FilterInvocationDefinitionSource, InitializingBean {
    
    private UrlMatcher urlMatcher;

    private boolean useAntPath = true;
    
    private boolean lowercaseComparisons = true;
    
    /**
     * @param useAntPath the useAntPath to set
     */
    public void setUseAntPath(boolean useAntPath) {
        this.useAntPath = useAntPath;
    }
    
    /**
     * @param lowercaseComparisons
     */
    public void setLowercaseComparisons(boolean lowercaseComparisons) {
        this.lowercaseComparisons = lowercaseComparisons;
    }
    
    public void afterPropertiesSet() throws Exception {
        
        this.urlMatcher = new RegexUrlPathMatcher();
        
        if (useAntPath) {  // change the implementation if required
            this.urlMatcher = new AntUrlPathMatcher();
        }
        
        if ("true".equals(lowercaseComparisons)) {
            if (!this.useAntPath) {
                ((RegexUrlPathMatcher) this.urlMatcher).setRequiresLowerCaseUrl(true);
            }
        } else if ("false".equals(lowercaseComparisons)) {
            if (this.useAntPath) {
                ((AntUrlPathMatcher) this.urlMatcher).setRequiresLowerCaseUrl(false);
            }
        }
        
    }
    
    public ConfigAttributeDefinition getAttributes(Object filter) throws IllegalArgumentException {
        FilterInvocation filterInvocation = (FilterInvocation) filter;
        String requestURI = filterInvocation.getRequestUrl();
        Map<String, String> urlAuthorities = this.getUrlAuthorities(filterInvocation);
        String grantedAuthorities = null;
        for(Iterator<Map.Entry<String, String>> iter = urlAuthorities.entrySet().iterator(); iter.hasNext();) {
            Map.Entry<String, String> entry = iter.next();
            String url = entry.getKey();
            if(urlMatcher.pathMatchesUrl(url, requestURI)) {
                grantedAuthorities = entry.getValue();
                break;
            }
        }
        
        if(grantedAuthorities != null) {
            ConfigAttributeEditor configAttrEditor = new ConfigAttributeEditor();
            configAttrEditor.setAsText(grantedAuthorities);
            return (ConfigAttributeDefinition) configAttrEditor.getValue();
        }
        return null;
    }

    @SuppressWarnings("unchecked")
	public Collection getConfigAttributeDefinitions() {
        return null;
    }

    @SuppressWarnings("unchecked")
	public boolean supports(Class clazz) {
        return true;
    }
    
    /**
     * 
     * @param filterInvocation
     * @return
     */
    @SuppressWarnings("unchecked")
	private Map<String, String> getUrlAuthorities(FilterInvocation filterInvocation) {
        ServletContext servletContext = filterInvocation.getHttpRequest().getSession().getServletContext();
        return (Map<String, String>)servletContext.getAttribute("urlAuthorities");
    }

}
