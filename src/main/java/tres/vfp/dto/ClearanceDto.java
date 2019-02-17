package tres.vfp.dto;

import java.io.Serializable;
import java.util.Date;

public class ClearanceDto implements Serializable {	
	private static final long serialVersionUID = 1L;
	
	private String strategicplan;
	private String taskName;
	private int numberOfActivities;
	private int planned;
	private int approved;
	private int reported;
	private int completed;
	private int failed;
	private int done;
	private int notStarted;
	private int rejected;
	private String activityname;
	private double rate;
	private boolean editable;
	private String dueDate;
	private Date startDate;
	private Date dateDue;
	
	public Date getStartDate() {
		return startDate;
	}
	public void setStartDate(Date startDate) {
		this.startDate = startDate;
	}
	public Date getDateDue() {
		return dateDue;
	}
	public void setDateDue(Date dateDue) {
		this.dateDue = dateDue;
	}
	public String getDueDate() {
		return dueDate;
	}
	public void setDueDate(String dueDate) {
		this.dueDate = dueDate;
	}
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

	public int getPlanned() {
		return planned;
	}
	public void setPlanned(int planned) {
		this.planned = planned;
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
	public int getApproved() {
		return approved;
	}
	public void setApproved(int approved) {
		this.approved = approved;
	}
	public int getFailed() {
		return failed;
	}
	public void setFailed(int failed) {
		this.failed = failed;
	}
	public int getDone() {
		return done;
	}
	public void setDone(int done) {
		this.done = done;
	}
	public int getRejected() {
		return rejected;
	}
	public void setRejected(int rejected) {
		this.rejected = rejected;
	}
	public String getActivityname() {
		return activityname;
	}
	public void setActivityname(String activityname) {
		this.activityname = activityname;
	}
	public int getReported() {
		return reported;
	}
	public void setReported(int reported) {
		this.reported = reported;
	}
	public int getNotStarted() {
		return notStarted;
	}
	public void setNotStarted(int notStarted) {
		this.notStarted = notStarted;
	}
	
}
