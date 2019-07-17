/**
 * 
 */
package com.huigao.security.support;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.hibernate.SessionFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DataAccessException;
import org.springframework.orm.hibernate3.support.HibernateDaoSupport;
import org.springframework.security.userdetails.UserDetails;
import org.springframework.security.userdetails.UserDetailsService;
import org.springframework.security.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import com.huigao.pojo.Resource;
import com.huigao.pojo.Users;
import com.huigao.security.SecurityManager;

/**
 * 安全管理实现类
 * @author cgx<br>参考:downpour(je)
 */
@Service("securityManager")
public class SecurityManagerSupport extends HibernateDaoSupport implements UserDetailsService, SecurityManager {
    
	/**
	 * 在对象实例化的时候自动注入sessionFactory。
	 * @param sessionFactory 
	 */
	@Autowired
    public void init(SessionFactory sessionFactory) {
        super.setSessionFactory(sessionFactory);
    }
	
    /**
     * 通过用户名加载用户信息
     * @param userName 用户名
     */
    @SuppressWarnings("unchecked")
	public UserDetails loadUserByUsername(String userName) throws UsernameNotFoundException, DataAccessException {
        List<Users> users = getHibernateTemplate().find("FROM Users WHERE userName = ? AND disabled = false", userName);
        if(users.isEmpty()) {
            throw new UsernameNotFoundException("用户 " + userName + "没有权限");
        }
        return users.get(0);
    }
    
    /**
     * 获取权限关联映射
     * @return 映射
     */
    @SuppressWarnings("unchecked")
	public Map<String, String> loadUrlAuthorities() {
        Map<String, String> urlAuthorities = new HashMap<String, String>();
        List<Resource> urlResources = getHibernateTemplate().find("FROM Resource resource WHERE resource.type = ?", "URL");
        for(Resource resource : urlResources) {
            urlAuthorities.put(resource.getValue(), resource.getRoleAuthorities());
        }
        return urlAuthorities;
    }
}
