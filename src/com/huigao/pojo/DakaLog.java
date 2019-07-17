package com.huigao.pojo;

import java.util.Date;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.ManyToOne;
import javax.persistence.Table;
import javax.persistence.Transient;

import com.huigao.util.DateUtil;

/**
 * 打卡登记
 * @author Administrator
 *
 */
@Entity
@Table(name="t_dakalog")
public class DakaLog {

	@Id
	@GeneratedValue(strategy=GenerationType.AUTO)
	private Integer id;
	
	@ManyToOne
	private Users users;
	
	@Column
	private Date date1; //上班1
	
	@ManyToOne
	private SystemTips tip1;
	
	@Column
	private Date date2; //下班1
	
	@ManyToOne
	private SystemTips tip2;
	
	@Column 
	private Date date3; //上班2
	
	@ManyToOne
	private SystemTips tip3;
	
	@Column 
	private Date date4; //下班2
	
	@ManyToOne
	private SystemTips tip4;
	
	@Transient
	private boolean flag = true; //标志 是否是数据库中的记录
	
	@Transient
	private Date dakaDate; //日期，，该属性仅在flag为false的时候有效

	public String toJsonString() {
		StringBuffer buf = new StringBuffer("{");
		buf.append("id:").append(id).append(",");
		if(users == null) buf.append("userid:'',username:'',");
		else buf.append("userid:").append(users.getId()).append(",")
		        .append("username:'").append(users.getUserName()).append("',");
		if(date1 != null) buf.append("dakaDate:'").append(DateUtil.getDateString(date1)).append("',"); 
		else if(date3 != null) buf.append("dakaDate:'").append(DateUtil.getDateString(date3)).append("',"); 
		else if(!flag) buf.append("dakaDate:'").append(DateUtil.getDateString(dakaDate)).append("',"); //没有打卡记录 
		else return "{}";
		
		if(date1 !=null) buf.append("date1:'").append(DateUtil.getTimeString(date1)).append("',");
		else buf.append("date1:'--',");
		if(tip1 != null) buf.append("tip1:'").append(tip1.getId()).append("',");
		else buf.append("tip1:'--',");
		
		if(date2 !=null) buf.append("date2:'").append(DateUtil.getTimeString(date2)).append("',");
		else buf.append("date2:'--',");
		if(tip2 != null) buf.append("tip2:'").append(tip2.getId()).append("',");
		else buf.append("tip2:'--',");
		
		if(date3 !=null) buf.append("date3:'").append(DateUtil.getTimeString(date3)).append("',");
		else buf.append("date3:'--',");
		if(tip3 != null) buf.append("tip3:'").append(tip3.getId()).append("',");
		else buf.append("tip3:'--',");
		
		if(date4 !=null) buf.append("date4:'").append(DateUtil.getTimeString(date4)).append("',");
		else buf.append("date4:'--',");
		if(tip4 != null) buf.append("tip4:'").append(tip4.getId()).append("' } ");
		else buf.append("tip4:'--' } ");
		
		return buf.toString();
	}

	public Date getDate3() {
		return date3;
	}

	public void setDate3(Date date3) {
		this.date3 = date3;
	}

	public SystemTips getTip3() {
		return tip3;
	}

	public void setTip3(SystemTips tip3) {
		this.tip3 = tip3;
	}

	public Date getDate4() {
		return date4;
	}

	public void setDate4(Date date4) {
		this.date4 = date4;
	}

	public SystemTips getTip4() {
		return tip4;
	}

	public void setTip4(SystemTips tip4) {
		this.tip4 = tip4;
	}

	public Date getDate1() {
		return date1;
	}

	public void setDate1(Date date1) {
		this.date1 = date1;
	}

	public SystemTips getTip1() {
		return tip1;
	}

	public void setTip1(SystemTips tip1) {
		this.tip1 = tip1;
	}

	public Date getDate2() {
		return date2;
	}

	public void setDate2(Date date2) {
		this.date2 = date2;
	}

	public SystemTips getTip2() {
		return tip2;
	}

	public void setTip2(SystemTips tip2) {
		this.tip2 = tip2;
	}

	public Integer getId() {
		return id;
	}

	public void setId(Integer id) {
		this.id = id;
	}


	public Users getUsers() {
		return users;
	}

	public void setUsers(Users users) {
		this.users = users;
	}

	public Date getDakaDate() {
		return dakaDate;
	}

	public void setDakaDate(Date dakaDate) {
		this.dakaDate = dakaDate;
	}
	
	public boolean isFlag() {
		return flag;
	}

	public void setFlag(boolean flag) {
		this.flag = flag;
	}
}
