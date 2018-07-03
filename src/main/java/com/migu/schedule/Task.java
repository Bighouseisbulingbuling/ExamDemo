package com.migu.schedule;

public class Task {
	
	private Integer taskId;
	private Integer consumption;
	
	public Task(){}
	public Task(int taskId, int consumption) {
		this.taskId = taskId;
		this.consumption = consumption;
	}
	
	public Integer getTaskId() {
		return taskId;
	}
	public void setTaskId(Integer taskId) {
		this.taskId = taskId;
	}
	public Integer getConsumption() {
		return consumption;
	}
	public void setConsumption(Integer consumption) {
		this.consumption = consumption;
	}
	@Override
	public String toString() {
		return "Task [taskId=" + taskId + ", consumption=" + consumption + "]";
	}
	
}
