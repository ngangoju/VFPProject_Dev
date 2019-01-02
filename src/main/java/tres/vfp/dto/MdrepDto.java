package tres.vfp.dto;

import java.io.Serializable;

public class MdrepDto implements Serializable {
	private static final long serialVersionUID = 1L;
	
	private String taskName;
	private String endDate;
	private String genericStatus;
	private String boarName;
	private int marks;
	
	public int getMarks() {
		return marks;
	}

	public void setMarks(int marks) {
		this.marks = marks;
	}

	public String getTaskName() {
		return taskName;
	}
	
	public void setTaskName(String taskName) {
		this.taskName = taskName;
	}
	public String getEndDate() {
		return endDate;
	}
	public void setEndDate(String endDate) {
		this.endDate = endDate;
	}
	public String getGenericStatus() {
		return genericStatus;
	}
	public void setGenericStatus(String genericStatus) {
		this.genericStatus = genericStatus;
	}
	public String getBoarName() {
		return boarName;
	}
	public void setBoarName(String boarName) {
		this.boarName = boarName;
	}
	

}
