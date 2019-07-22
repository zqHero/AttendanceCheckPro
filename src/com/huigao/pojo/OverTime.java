package com.huigao.pojo;

import java.util.Date;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.ManyToOne;
import javax.persistence.Table;

import com.huigao.util.DateUtil;


@Entity
@Table(name="t_overtime")
public class OverTime {
	
	@Id
	@GeneratedValue(strategy=GenerationType.AUTO)
	private Integer id;
	
	@Column
	private Date startTime;
	
	@Column
	private Date endTime;
	
	@ManyToOne
	private Users user;
	
	@Column
	private String reason;
	
	
	public String toJsonString(){
		return "{id:" + id + ",userId:" + user.getId()  
			+ ",userName:'" + user.getUserName() + "',startTime:'" 
			+ DateUtil.getFormatDateString(startTime) + "',endTime:'" 
			+ DateUtil.getFormatDateString(endTime) + "',reason:'" + reason + "'}";
	}

	public Integer getId() {
		return id;
	}

	public void setId(Integer id) {
		this.id = id;
	}
	
	public Date getStartTime() {
		return startTime;
	}
	
	public void setStartTime(Date startTime) {
		this.startTime = startTime;
	}
	
	public Date getEndTime() {
		return endTime;
	}

	public void setEndTime(Date endTime) {
		this.endTime = endTime;
	}

	public Users getUser() {
		return user;
	}

	public void setUser(Users user) {
		this.user = user;
	}

	public String getReason() {
		return reason;
	}

	public void setReason(String reason) {
		this.reason = reason;
	}
	
}
