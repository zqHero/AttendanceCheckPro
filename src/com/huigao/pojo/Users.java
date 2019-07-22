package com.huigao.pojo;

import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Set;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.JoinTable;
import javax.persistence.ManyToMany;
import javax.persistence.ManyToOne;
import javax.persistence.Table;
import javax.persistence.Transient;

import org.apache.commons.codec.digest.DigestUtils;
import org.hibernate.annotations.Cache;
import org.hibernate.annotations.CacheConcurrencyStrategy;
import org.hibernate.annotations.Proxy;
import org.springframework.security.GrantedAuthority;
import org.springframework.security.GrantedAuthorityImpl;
import org.springframework.security.userdetails.UserDetails;

import com.huigao.util.DateUtil;
import com.huigao.util.StringUtils;


@Entity
@Table(name="t_user")
@Proxy(lazy = false)
@Cache(usage = CacheConcurrencyStrategy.READ_WRITE)
public class Users implements UserDetails  {
	
	private static final long serialVersionUID = -5043781305150263616L;

	@Id
	@GeneratedValue(strategy=GenerationType.AUTO)
	private Integer id; //编号
	
	@Column(unique=true)
	private String userName;  //登陆用户名
	
	@Column
	private String password; //登陆密码
	
	@Column
	private String realName; //真实姓名
	
	@Column
	private String gender; //性别
	
	@Column
	private Date birthday; //出生日期
	
	@Column
	private String school; //学校
	
	@Column
	private String specialty; //专业
	
	@ManyToOne
	private Department department; //部门
	
	@ManyToOne
	private Users superiors; //上级
	
	@Column
	private boolean disabled; //是否禁用
	
	@ManyToMany(targetEntity = Role.class, fetch = FetchType.EAGER)  
	@JoinTable(name = "t_user_role", joinColumns = @JoinColumn(name = "user_id"), inverseJoinColumns = @JoinColumn(name = "role_id"))  
	@Cache(usage = CacheConcurrencyStrategy.READ_WRITE)
	private Set<Role> roles;  
	
	@Transient
	private Map<String, List<Resource>> roleResources;
	
	
	/* ---以下为实现UserDetails接口--- */
	public GrantedAuthority[] getAuthorities() {  
        List<GrantedAuthority> grantedAuthorities = new ArrayList<GrantedAuthority>(roles.size());  
        for(Role role : roles) {  
            grantedAuthorities.add(new GrantedAuthorityImpl(role.getName()));  
        }  
        return grantedAuthorities.toArray(new GrantedAuthority[roles.size()]);  
    } 
	
	public boolean isAccountNonExpired() {  
        return true;  
    } 
	
	public boolean isAccountNonLocked() {  
        return true;  
    }  
    
    public boolean isCredentialsNonExpired() {  
        return true;  
    }  
    
    public String getUsername() {  
        return getUserName();  
    }
    
    public boolean isEnabled() {
		return !isDisabled();
	}
    
    /* ---以上为实现UserDetails接口--- */
    
    
    /* ---以下为一些公共方法--- */
    public String getAuthoritiesString() {  
        List<String> authorities = new ArrayList<String>();  
        for(GrantedAuthority authority : this.getAuthorities()) {  
            authorities.add(authority.getAuthority());  
        }  
        return StringUtils.join(authorities, ",");  
    } 
    
    public Map<String, List<Resource>> getRoleResources() {  
        if(this.roleResources == null) { 
            this.roleResources = new HashMap<String, List<Resource>>();  
            for(Role role : this.roles) {  
                String roleName = role.getName();  
                Set<Resource> resources = role.getResources();  
                for(Resource resource : resources) {  
                    String key = roleName + "_" + resource.getType();  
                        if(!this.roleResources.containsKey(key)) {  
                            this.roleResources.put(key, new ArrayList<Resource>());  
                    }  
                    this.roleResources.get(key).add(resource);                    
                }  
            }  
        }  
        return this.roleResources;  
    }  
    /* ---以上为一些公共方法--- */
    
    
    public String toJsonString() {
		StringBuffer buf = new StringBuffer("");
		buf.append("id:").append(id).append(",") 
		   .append("userName:'").append(userName).append("',")
		   .append("password:'").append(password).append("',")
		   .append("realName:'").append(realName).append("',")
		   .append("gender:'").append(gender).append("',")
		   .append("birthday:'").append(DateUtil.getDateString(birthday)).append("',")
		   .append("school:'").append(school).append("',")
		   .append("specialty:'").append(specialty).append("',")
		   .append("departmentId:'").append(department.getId()).append("',")
		   .append("departmentName:'").append(department.getDepartmentName()).append("',")
		   .append("superiorsId:'").append(superiors==null?"--":superiors.getId()).append("',")
		   .append("superiorsName:'").append(superiors==null?"--":superiors.getUserName()).append("'");
		return "{" + buf.toString() + "}";
	}
	
	
	public Department getDepartment() {
		return department;
	}
	
	public void setDepartment(Department department) {
		this.department = department;
	}
	
	public Integer getId() {
		return id;
	}
	
	public void setId(Integer id) {
		this.id = id;
	}

	public String getUserName() {
		return userName;
	}

	public void setUserName(String userName) {
		this.userName = userName;
	}

	public String getPassword() {
		return password;
	}

	public void setPassword(String password) {
		this.password = password;
	}

	public Users getSuperiors() {
		return superiors;
	}

	public void setSuperiors(Users superiors) {
		this.superiors = superiors;
	}
	
	public String getRealName() {
		return realName;
	}

	public void setRealName(String realName) {
		this.realName = realName;
	}

	public String getGender() {
		return gender;
	}

	public void setGender(String gender) {
		this.gender = gender;
	}

	public Date getBirthday() {
		return birthday;
	}

	public void setBirthday(Date birthday) {
		this.birthday = birthday;
	}

	public String getSchool() {
		return school;
	}

	public void setSchool(String school) {
		this.school = school;
	}

	public String getSpecialty() {
		return specialty;
	}

	public void setSpecialty(String specialty) {
		this.specialty = specialty;
	}
	
	public boolean isDisabled() {
		return disabled;
	}

	public void setDisabled(boolean disabled) {
		this.disabled = disabled;
	}

	public Set<Role> getRoles() {
		return roles;
	}

	public void setRoles(Set<Role> roles) {
		this.roles = roles;
	}

	public void setRoleResources(Map<String, List<Resource>> roleResources) {
		this.roleResources = roleResources;
	}
	
	
	public static void main(String[] args) {
		String a = DigestUtils.md5Hex("c");
		System.out.println(a); 
	}

}
