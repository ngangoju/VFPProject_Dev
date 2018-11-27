package tres.vfp.dto;

import java.io.Serializable;

public class ClearanceDto implements Serializable {	
	private static final long serialVersionUID = 1L;
	
	
	private String Strategicplan;
	private String taskName;
	private int numberOfActivities;
	private int numberOfFinishedActivities;
	private double rate;
	private boolean editable;
	public String getStrategicplan() {
		return Strategicplan;
	}
	public void setStrategicplan(String strategicplan) {
		Strategicplan = strategicplan;
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
	public int getNumberOfFinishedActivities() {
		return numberOfFinishedActivities;
	}
	public void setNumberOfFinishedActivities(int numberOfFinishedActivities) {
		this.numberOfFinishedActivities = numberOfFinishedActivities;
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
