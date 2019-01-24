package tres.vfp.dto;

import java.io.Serializable;
import java.util.Date;
import tres.domain.StrategicPlan;
import tres.domain.Task;

public class TaskDto implements Serializable {
	private static final long serialVersionUID = 1L;

	private int taskId;

	private String taskName;

	private String description;

	private Date startDate;

	private Date dueDate;

	private Date endDate;

	private StrategicPlan strategicPlan;

	private String status;
	
	private String weight;

	private Task task;

	private String genericstatus;

	private Date createdDate;

	private String createdBy;

	private boolean editable;

	public int getTaskId() {
		return taskId;
	}

	public void setTaskId(int taskId) {
		this.taskId = taskId;
	}

	public String getTaskName() {
		return taskName;
	}

	public void setTaskName(String taskName) {
		this.taskName = taskName;
	}

	public String getDescription() {
		return description;
	}

	public void setDescription(String description) {
		this.description = description;
	}

	public Date getStartDate() {
		return startDate;
	}

	public void setStartDate(Date startDate) {
		this.startDate = startDate;
	}

	public Date getDueDate() {
		return dueDate;
	}

	public void setDueDate(Date dueDate) {
		this.dueDate = dueDate;
	}

	public Date getEndDate() {
		return endDate;
	}

	public void setEndDate(Date endDate) {
		this.endDate = endDate;
	}

	public StrategicPlan getStrategicPlan() {
		return strategicPlan;
	}

	public void setStrategicPlan(StrategicPlan strategicPlan) {
		this.strategicPlan = strategicPlan;
	}

	public Task getTask() {
		return task;
	}

	public void setTask(Task task) {
		this.task = task;
	}

	public String getGenericstatus() {
		return genericstatus;
	}

	public void setGenericstatus(String genericstatus) {
		this.genericstatus = genericstatus;
	}

	public String getStatus() {
		return status;
	}

	public void setStatus(String status) {
		this.status = status;
	}

	public Date getCreatedDate() {
		return createdDate;
	}

	public void setCreatedDate(Date createdDate) {
		this.createdDate = createdDate;
	}

	public boolean isEditable() {
		return editable;
	}

	public void setEditable(boolean editable) {
		this.editable = editable;
	}

	public String getCreatedBy() {
		return createdBy;
	}

	public void setCreatedBy(String createdBy) {
		this.createdBy = createdBy;
	}

	public String getWeight() {
		return weight;
	}

	public void setWeight(String weight) {
		this.weight = weight;
	}

}
