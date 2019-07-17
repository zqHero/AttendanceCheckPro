package com.huigao.vo;

/**
 * 打卡统计VO
 * @author cgx
 * @version 1.0
 */
public class DakaVO {
	
	private Integer id; //员工的id
	
	private String realName; //员工姓名
	
	private Float workTime; //工作天数
	
	private Float overTime; // 加班天数
	
	private Float businessTime; //事假天数
	
	private Float sickTime; //病假天数
	
	private Float vacationTime; //休假天数
	
	private Integer lateCount; //迟到次数
	
	private Integer earlyCount;  //早退次数
	
	public DakaVO() {}

	public Integer getId() {
		return id;
	}

	public void setId(Integer id) {
		this.id = id;
	}

	public String getRealName() {
		return realName;
	}

	public void setRealName(String realName) {
		this.realName = realName;
	}

	public Float getWorkTime() {
		return workTime;
	}

	public void setWorkTime(Float workTime) {
		this.workTime = workTime;
	}

	public Float getOverTime() {
		return overTime;
	}

	public void setOverTime(Float overTime) {
		this.overTime = overTime;
	}

	public Float getBusinessTime() {
		return businessTime;
	}

	public void setBusinessTime(Float businessTime) {
		this.businessTime = businessTime;
	}

	public Float getSickTime() {
		return sickTime;
	}

	public void setSickTime(Float sickTime) {
		this.sickTime = sickTime;
	}

	public Float getVacationTime() {
		return vacationTime;
	}

	public void setVacationTime(Float vacationTime) {
		this.vacationTime = vacationTime;
	}

	public Integer getLateCount() {
		return lateCount;
	}

	public void setLateCount(Integer lateCount) {
		this.lateCount = lateCount;
	}

	public Integer getEarlyCount() {
		return earlyCount;
	}

	public void setEarlyCount(Integer earlyCount) {
		this.earlyCount = earlyCount;
	}
}
