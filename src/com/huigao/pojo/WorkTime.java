package com.huigao.pojo;

import java.sql.Time;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;

import org.hibernate.annotations.Cache;
import org.hibernate.annotations.CacheConcurrencyStrategy;

/**
 * 工作时间
 * @author Administrator
 *
 */

@Entity
@Table(name="t_worktime")
@Cache(usage = CacheConcurrencyStrategy.READ_WRITE)
public class WorkTime {
	
	@Id
	@GeneratedValue(strategy=GenerationType.AUTO)
	private Integer id;
	
	@Column
	private Time time;
	
	@Column
	private String description;
	
	public Integer getId() {
		return id;
	}
	
	public void setId(Integer id) {
		this.id = id;
	}
	
	public Time getTime() {
		return time;
	}
	
	public void setTime(Time time) {
		this.time = time;
	}
	
	public String getDescription() {
		return description;
	}
	
	public void setDescription(String description) {
		this.description = description;
	}
	
}
