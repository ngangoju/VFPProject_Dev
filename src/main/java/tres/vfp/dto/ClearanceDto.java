package tres.vfp.dto;

import java.io.Serializable;

public class ClearanceDto implements Serializable {	
	private static final long serialVersionUID = 1L;
	
	private String strategicplan;
	private String taskName;
	private int numberOfActivities;
	private int approved;
	private int rejected;
	private int completed;
	private double rate;
	private boolean editable;
	
	public String getStrategicplan() {
		return strategicplan;
	}
	public void setStrategicplan(String strategicplan) {
		this.strategicplan = strategicplan;
	}
	public String getTaskName() {
		return taskName;
	}
	public void setTaskName(String taskName) {
		this.taskName = taskName;
	}
	public int getNumberOfActivities() {
		return numberOfActivities;
	}
	public void setNumberOfActivities(int numberOfActivities) {
		this.numberOfActivities = numberOfActivities;
	}
	
	
	public int getApproved() {
		return approved;
	}
	public void setApproved(int approved) {
		this.approved = approved;
	}
	public int getRejected() {
		return rejected;
	}
	public void setRejected(int rejected) {
		this.rejected = rejected;
	}
	public int getCompleted() {
		return completed;
	}
	public void setCompleted(int completed) {
		this.completed = completed;
	}
	public double getRate() {
		return rate;
	}
	public void setRate(double rate) {
		this.rate = rate;
	}
	public boolean isEditable() {
		return editable;
	}
	public void setEditable(boolean editable) {
		this.editable = editable;
	}
	
}
