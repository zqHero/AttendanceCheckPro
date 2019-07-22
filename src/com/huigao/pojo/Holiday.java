package com.huigao.pojo;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;

/**
 * 请假
 * @author Administrator
 *
 */
@Entity
@Table(name="t_holiday")
public class Holiday {
	
	@Id
	@GeneratedValue(strategy=GenerationType.AUTO)
	private Integer id; //编号
	
	@Column
	private String holidayName; //名称
	
	public Integer getId() {
		return id;
	}

	public void setId(Integer id) {
		this.id = id;
	}

	public String getHolidayName() {
		return holidayName;
	}

	public void setHolidayName(String holidayName) {
		this.holidayName = holidayName;
	}

}
