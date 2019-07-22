/**
 * 
 */
package com.huigao.pojo;

import java.util.Set;

import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.ManyToMany;
import javax.persistence.Table;

import org.hibernate.annotations.Cache;
import org.hibernate.annotations.CacheConcurrencyStrategy;
import org.hibernate.annotations.Proxy;

/**
 * @author Administrator
 * 角色
 */
@Entity
@Table(name="t_role")
@Proxy(lazy = false)
@Cache(usage = CacheConcurrencyStrategy.READ_WRITE)
public class Role  implements java.io.Serializable{
	
	private static final long serialVersionUID = -3819571182288444704L;

	@Id
	@GeneratedValue
	private Integer id;
	
	private String name; //名称
	
	private String description; //描述
	
	@ManyToMany(mappedBy = "roles",targetEntity = Resource.class, fetch = FetchType.EAGER)
	@Cache(usage = CacheConcurrencyStrategy.READ_WRITE)
    private Set<Resource> resources;   //该角色有权访问的资源
	
	
	@ManyToMany(mappedBy = "roles", targetEntity = Users.class)
	private Set<Users> users; 
	
	public String toJsonString() {
		return "{id:" + id + ",name:'" + name + "',description:'" + description + "'}";
	}
	
	/**
	 * The default constructor
	 */
	public Role() {
	}

	/**
	 * @return the id
	 */
	public Integer getId() {
		return id;
	}

	/**
	 * @return the name
	 */
	public String getName() {
		return name;
	}
	
	/**
	 * @return the description
	 */
	public String getDescription() {
		return description;
	}

	/**
	 * @return the resources
	 */
	public Set<Resource> getResources() {
		return resources;
	}

	/**
	 * @param id the id to set
	 */
	public void setId(Integer id) {
		this.id = id;
	}

	/**
	 * @param name the name to set
	 */
	public void setName(String name) {
		this.name = name;
	}
	
	/**
	 * @param description the description to set
	 */
	public void setDescription(String description) {
		this.description = description;
	}

	/**
	 * @param resources the resources to set
	 */
	public void setResources(Set<Resource> resources) {
		this.resources = resources;
	}
	
	public Set<Users> getUsers() {
		return users;
	}

	public void setUsers(Set<Users> users) {
		this.users = users;
	}

}
