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
@Table(name = "t_tasklog")

public class TaskLog {
    
	@Id
	@GeneratedValue(strategy=GenerationType.AUTO)
	private Integer id;
	
	@ManyToOne
	private Task task;
	
	@Column
	private Date date;//操作时间
	
	@ManyToOne
	private Users operator;//操作人
	
	@Column
	private String action;//操作动作
	
	@ManyToOne
	private Users executor;//接收者
	
	@Column
	private String result;//操作结果

	public Integer getId() {
		return id;
	}

	public void setId(Integer id) {
		this.id = id;
	}

	public Task getTask() {
		return task;
	}

	public void setTask(Task task) {
		this.task = task;
	}

	public Date getDate() {
		return date;
	}

	public void setDate(Date date) {
		this.date = date;
	}

	public Users getOperator() {
		return operator;
	}

	public void setOperator(Users operator) {
		this.operator = operator;
	}

	public String getAction() {
		return action;
	}

	public void setAction(String action) {
		this.action = action;
	}

	public Users getExecutor() {
		return executor;
	}

	public void setExecutor(Users executor) {
		this.executor = executor;
	}

	public String getResult() {
		return result;
	}

	public void setResult(String result) {
		this.result = result;
	}
	
	public String toJsonString() {
		StringBuffer buf = new StringBuffer("");
		buf.append("id:").append(id).append(",");
		
		if(task!=null) buf.append("taskId:").append(task.getId()).append(",")
		                  .append("taskName:'").append(task.getTaskname()).append("',");
		else buf.append("taskId:'',taskName:'',");
		
		if(date!=null) buf.append("date:'").append(DateUtil.getFormatDateString(date)).append("',");
		else buf.append("date:'',");
		
		if(operator!=null) buf.append("operatorId:'").append(operator.getId()).append("',")
		                      .append("operatorName:'").append(operator.getRealName()).append("',");
		else buf.append("operatorId:'',operatorName:'',");
		
		if(action!=null) buf.append("action:'").append(action).append("',");
		else buf.append("action:'',");
		
		if(executor!=null) buf.append("executorId:'").append(executor.getId()).append("',")
		                      .append("executorName:'").append(executor.getRealName()).append("',");
		else buf.append("executorId:'',executorName:'',");
		
		if(result!=null) buf.append("result:'").append(result).append("'");
		else buf.append("result:''");

		return "{" + buf.toString() + "}";
	}
}
