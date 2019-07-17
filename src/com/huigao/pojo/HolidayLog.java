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

/**
 * 假期登记
 * @author Administrator
 *
 */
@Entity
@Table(name="t_holidaylog")
public class HolidayLog {
    
	@Id
	@GeneratedValue(strategy=GenerationType.AUTO)
	private Integer id; //编号
	
	@ManyToOne
	private Users users; //员工
	
	@ManyToOne
	private Holiday holiday; //假期类型
	
	@Column
	private Date startTime; //起始日期
	
	@Column
	private Date endTime; //终止日期
	
	@Column
	private String reason; //假期原因
	
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

	public Holiday getHoliday() {
		return holiday;
	}

	public void setHoliday(Holiday holiday) {
		this.holiday = holiday;
	}

	public String getReason() {
		return reason;
	}

	public void setReason(String reason) {
		this.reason = reason;
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
	
	public String toJsonString() {
		StringBuffer buf = new StringBuffer("{");
		buf.append("id:").append(id).append(",")
		   .append("userId:").append(users.getId()).append(",")
		   .append("realName:'").append(users.getRealName()).append("',")
		   .append("holidayId:").append(holiday.getId()).append(",")
		   .append("holidayName:'").append(holiday.getHolidayName()).append("',")
		   .append("startTime:'").append(DateUtil.getFormatDateTimeString(startTime)).append("',")
		   .append("endTime:'").append(DateUtil.getFormatDateTimeString(endTime)).append("',")
		   .append("reason:'").append(reason).append("' }"); 
		return buf.toString();
	}
}
