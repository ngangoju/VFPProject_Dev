package tres.vfp.dto;

import java.io.Serializable;
import java.util.Date;

import tres.domain.Activity;
import tres.domain.Evaluation;
import tres.domain.Task;
import tres.domain.Users;

public class EvaluationDto implements Serializable {
	private static final long serialVersionUID = 1L;

	private int evaluationId;

	private String evaluationType;

	private String decision;

	private Activity activity;

	private int evaluationMarks;

	private String genericstatus;

	private Date createdDate;

	private boolean editable;

	private boolean failedBtn;

	private Users user;

	public int getEvaluationId() {
		return evaluationId;
	}

	public void setEvaluationId(int evaluationId) {
		this.evaluationId = evaluationId;
	}

	public String getEvaluationType() {
		return evaluationType;
	}

	public void setEvaluationType(String evaluationType) {
		this.evaluationType = evaluationType;
	}

	public Activity getActivity() {
		return activity;
	}

	public void setActivity(Activity activity) {
		this.activity = activity;
	}

	public boolean isFailedBtn() {
		return failedBtn;
	}

	public void setFailedBtn(boolean failedBtn) {
		this.failedBtn = failedBtn;
	}

	public String getGenericstatus() {
		return genericstatus;
	}

	public void setGenericstatus(String genericstatus) {
		this.genericstatus = genericstatus;
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

	public static long getSerialversionuid() {
		return serialVersionUID;
	}

	public String getDecision() {
		return decision;
	}

	public void setDecision(String decision) {
		this.decision = decision;
	}

	public Users getUser() {
		return user;
	}

	public void setUser(Users user) {
		this.user = user;
	}

	public int getEvaluationMarks() {
		return evaluationMarks;
	}

	public void setEvaluationMarks(int evaluationMarks) {
		this.evaluationMarks = evaluationMarks;
	}

}
