package com.huigao.pojo;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.ManyToOne;
import javax.persistence.Table;

/**
 * 月度统计
 * @author Administrator
 *
 */
@Entity
@Table(name="t_statistic")
public class Statistic {
	
	@Id
	@GeneratedValue(strategy=GenerationType.AUTO)
	private Integer id;
	
	@ManyToOne
	private Users users;
	
	@Column
	private Integer workTime; //工作时间
	
	@Column
	private Integer overTime; // 加班天数
	
	@Column
	private Integer businessTime; //事假天数
	
	@Column
	private Integer sickTime; //病假天数
	
	@Column
	private Integer vacationTime; //休假天数
	
	@Column
	private Integer countOfLate; //迟到次数
	
	@Column
	private Integer countOfEO; //早退次数
	
	@Column
	private Integer year; //年
	
	@Column
	private Integer month; //月

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

	public Integer getWorkTime() {
		return workTime;
	}

	public void setWorkTime(Integer workTime) {
		this.workTime = workTime;
	}

	public Integer getOverTime() {
		return overTime;
	}

	public void setOverTime(Integer overTime) {
		this.overTime = overTime;
	}

	public Integer getBusinessTime() {
		return businessTime;
	}

	public void setBusinessTime(Integer businessTime) {
		this.businessTime = businessTime;
	}

	public Integer getSickTime() {
		return sickTime;
	}

	public void setSickTime(Integer sickTime) {
		this.sickTime = sickTime;
	}

	public Integer getVacationTime() {
		return vacationTime;
	}

	public void setVacationTime(Integer vacationTime) {
		this.vacationTime = vacationTime;
	}

	public Integer getCountOfLate() {
		return countOfLate;
	}

	public void setCountOfLate(Integer countOfLate) {
		this.countOfLate = countOfLate;
	}

	public Integer getCountOfEO() {
		return countOfEO;
	}

	public void setCountOfEO(Integer countOfEO) {
		this.countOfEO = countOfEO;
	}

	public Integer getYear() {
		return year;
	}

	public void setYear(Integer year) {
		this.year = year;
	}

	public Integer getMonth() {
		return month;
	}

	public void setMonth(Integer month) {
		this.month = month;
	}

	public Integer getUserId() { //用户编号
		if(users==null) return null;
		return users.getId();
	}

	public String getRealName() { //真实姓名
		if(users==null) return null;
		return users.getRealName();
	}
	
	
	public String toJsonString() {
		StringBuffer buf = new StringBuffer("{");
		buf.append("id:").append(getId()).append(",")
		   .append("time:'").append(getYear()).append("年").append(getMonth()).append("月',") 
		   .append("userId:").append(getUsers().getId()).append(",")
		   .append("realName:'").append(getUsers().getRealName()).append("',")
		   .append("workTime:").append(getWorkTime()).append(",")
		   .append("overTime:").append(getOverTime()).append(",")
		   .append("businessTime:").append(getBusinessTime()).append(",")
		   .append("sickTime:").append(getSickTime()).append(",")
		   .append("vacationTime:").append(getVacationTime()).append(",")
		   .append("countOfLate:").append(getCountOfLate()).append(",")
		   .append("countOfEO:").append(getCountOfEO()).append(",") 
		   .append("year:").append(getYear()).append(",")
		   .append("month:").append(getMonth()).append("}"); 
		return buf.toString();
	}
	
}
