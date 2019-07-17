package com.huigao.dao.impl;

import java.sql.SQLException;
import java.util.List;

import org.apache.commons.codec.digest.DigestUtils;
import org.hibernate.HibernateException;
import org.hibernate.Session;
import org.springframework.orm.hibernate3.HibernateCallback;
import org.springframework.orm.hibernate3.support.HibernateDaoSupport;
import org.springframework.security.providers.encoding.Md5PasswordEncoder;

import com.huigao.dao.UsersDao;
import com.huigao.pojo.Users;

public class UsersDaoImpl extends HibernateDaoSupport implements UsersDao {
	
	public void save(Users users) {
		if(users == null) return; 
	    String encodedPassword = new Md5PasswordEncoder().encodePassword(users.getPassword(), users.getUserName());
	    users.setPassword(encodedPassword);
	    getHibernateTemplate().save(users);
	}
	
	public boolean checkUsername(String username) {
		return ((Long)getHibernateTemplate().findByNamedParam(" select count(*) from Users where userName=:username ","username",username).get(0))>0;
	} 
	
	@SuppressWarnings("unchecked")
	public Users login(String username, String password) {
		//---TODO
		return null;
	}
	
	@SuppressWarnings("unchecked")
	public List<Users> listByDepartment(final int departmentID,final int start, final int limit) {
		return getHibernateTemplate().executeFind(new HibernateCallback() {
			public Object doInHibernate(Session session) throws HibernateException, SQLException {
				String hql = "from Users ";
				if(departmentID != 0) hql = hql + " where department.id= " + departmentID;
				return session.createQuery(hql).setFirstResult(start).setMaxResults(limit).list();
			}
		});
	}
	
	@SuppressWarnings("unchecked")
	public List<Users> listByDepartmentAndRealName(final int departmentId,final String  realName){
		if(departmentId == 0) {
			String hql = " from Users ";
			if(realName != null&&!realName.startsWith(" ")) hql = hql + " where userName like '" + realName + "%' ";
			return getHibernateTemplate().find(hql);
		} else {
			String hql = " from Users where department.id=:departmentId ";
			if(realName != null) hql = hql + " and userName like '" + realName + "%' ";
			return getHibernateTemplate().findByNamedParam("", "departmentId", departmentId);
		}
	}
	
	@SuppressWarnings("unchecked")
	public List<Users> list() {
		return getHibernateTemplate().find(" from Users ");
	}
	
	public Integer getCountByDepartment(Integer departmentID) {
		if(departmentID == 0) {
			String hql = " select count(*) from Users ";
			return ((Long)getHibernateTemplate().find(hql).get(0)).intValue();  
		} else {
			String hql = " select count(*) from Users where department.id=:departmentId ";
			return ((Long)getHibernateTemplate().findByNamedParam(hql,new String[]{"departmentId"},new Object[]{departmentID}).get(0)).intValue();  
		}
	}

	public Users getById(Integer userId) {
		return (Users) getHibernateTemplate().get(Users.class, userId); 
	}
	
	@SuppressWarnings("unchecked")
	public Users getByUsername(String username) {
		List<Users> list = getHibernateTemplate().findByNamedParam("from Users where userName=:userName", "userName", username); 
		if(list == null || list.isEmpty()) return null;
		return list.get(0);
	}
	
	public void delete(Users users) {
		getHibernateTemplate().delete(users);
	}
	
	public void delete(final Integer userId) {
		getHibernateTemplate().execute(new HibernateCallback(){
			public Object doInHibernate(Session session) throws HibernateException, SQLException {
				return session.createQuery(" delete Users where id=:userId ").setInteger("userId", userId).executeUpdate();
			}
		});
	}
	
	public void update(Users users) {
		if(users == null) return; 
		if(users.getPassword().length() != 32)  users.setPassword(DigestUtils.md5Hex(users.getPassword())); //MD5加密 
		getHibernateTemplate().update(users);
	}
	
	public void merge(Users user){
		getHibernateTemplate().merge(user);
	}

}
