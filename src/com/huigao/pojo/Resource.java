package com.huigao.pojo;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;
import java.util.Set;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.JoinTable;
import javax.persistence.ManyToMany;
import javax.persistence.Table;
import javax.persistence.Transient;

import org.apache.commons.lang.StringUtils;
import org.hibernate.annotations.Cache;
import org.hibernate.annotations.CacheConcurrencyStrategy;
import org.hibernate.annotations.Proxy;

/**
 * 资源
 * @author Administrator
 *
 */
@Entity
@Table(name="t_resource")
@Proxy(lazy = false)
@Cache(usage = CacheConcurrencyStrategy.READ_WRITE)
public class Resource implements java.io.Serializable {

	private static final long serialVersionUID = 4977825948519186538L;

	@Id
    @GeneratedValue
	private Integer id;
	
	@Column
	private String type = "URL"; //类型 ，可以是URL，MENU，METHOD等
	
	@Column
	private String value; //值
	
	@Column
	private String description; //描述
	
	@ManyToMany(targetEntity = Role.class, fetch = FetchType.EAGER)
	@JoinTable(name = "t_role_resource", joinColumns = @JoinColumn(name = "resource_id"), inverseJoinColumns = @JoinColumn(name = "role_id"))
	@Cache(usage = CacheConcurrencyStrategy.READ_WRITE)
	private Set<Role> roles; //有权访问该资源的角色
	
	
	public String toJsonString() {
		StringBuffer buf = new StringBuffer("[");
		Iterator<Role> it = getRoles().iterator();
		for (int i = 0; i < roles.size(); i++) {
			if(i != 0) buf.append(",");
			Role role = it.next();
			buf.append(role.toJsonString()); 
		}
		buf.append("]");
		String str = "{id:" + id + ",value:'" + value + "',description:'" + description + "',roles:" + buf.toString() + "}";
		return str;
	}
	
	/**
	 * The default constructor
	 */
	public Resource() {
	}
	
    /**
     * Get role authorities as string
     * 
     * @return
     */
    @Transient
	public String getRoleAuthorities() {
    	List<String> roleAuthorities = new ArrayList<String>();
    	for(Role role : roles) {
    		roleAuthorities.add(role.getName());
    	}
        return StringUtils.join(roleAuthorities.toArray(), ",");
    }
    

	/**
	 * @return the id
	 */
	public Integer getId() {
		return id;
	}

	/**
	 * @return the type
	 */
	public String getType() {
		return type;
	}

	/**
	 * @return the value
	 */
	public String getValue() {
		return value;
	}

	/**
	 * @return the roles
	 */
	public Set<Role> getRoles() {
		return roles;
	}

	/**
	 * @param id the id to set
	 */
	public void setId(Integer id) {
		this.id = id;
	}

	/**
	 * @param type the type to set
	 */
	public void setType(String type) {
		this.type = type;
	}

	/**
	 * @param value the value to set
	 */
	public void setValue(String value) {
		this.value = value;
	}

	/**
	 * @param roles the roles to set
	 */
	public void setRoles(Set<Role> roles) {
		this.roles = roles;
	}

	public String getDescription() {
		return description;
	}

	public void setDescription(String description) {
		this.description = description;
	}
}
