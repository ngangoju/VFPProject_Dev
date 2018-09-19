package tres.vfp.dto;

import java.io.Serializable;
import java.util.Date;

import tres.domain.Users;

public class StrategicPlanDto implements Serializable {	
	private static final long serialVersionUID = 1L;
	
	private int strategicPlanId;

	private String details;

	private Date recordedDate;

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
