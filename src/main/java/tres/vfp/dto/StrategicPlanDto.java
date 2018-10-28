package tres.vfp.dto;

import java.io.Serializable;
import java.util.Date;

import tres.domain.Users;

public class StrategicPlanDto implements Serializable {	
	private static final long serialVersionUID = 1L;
	
	private int strategicPlanId;

	private String details;

	private Date recordedDate;

	private Date startDate;

	private Date dueDate;

	private Date endDate;
	
	private Users users;

	private String genericStatus;
	
	private boolean editable;

	public int getStrategicPlanId() {
		return strategicPlanId;
	}

	public void setStrategicPlanId(int strategicPlanId) {
		this.strategicPlanId = strategicPlanId;
	}

	public String getDetails() {
		return details;
	}

	public void setDetails(String details) {
		this.details = details;
	}

	public Date getRecordedDate() {
		return recordedDate;
	}

	public void setRecordedDate(Date recordedDate) {
		this.recordedDate = recordedDate;
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

	public Users getUsers() {
		return users;
	}

	public void setUsers(Users users) {
		this.users = users;
	}

	public String getGenericStatus() {
		return genericStatus;
	}

	public void setGenericStatus(String genericStatus) {
		this.genericStatus = genericStatus;
	}

	public boolean isEditable() {
		return editable;
	}

	public void setEditable(boolean editable) {
		this.editable = editable;
	}

	
}
