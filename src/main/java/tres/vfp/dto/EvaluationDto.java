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
	private String weight;
	private String description;
	private Date EvaluationDate;
	private Date createdDate;

	private boolean editable;

	private boolean failedBtn;
	private Task task;
	private Users user;
	private Users supervisor;
	private int TotalEvalActivity;
	private int TotalEvalMarks;
	private int TotalExpMarks;
	private int TotalActFailed;
	private int TotalActCompleted;
	private int EvaluationOverAllMarks;
	
	public String getWeight() {
		return weight;
	}

	public void setWeight(String weight) {
		this.weight = weight;
	}

	public String getDescription() {
		return description;
	}

	public void setDescription(String description) {
		this.description = description;
	}

	public Date getEvaluationDate() {
		return EvaluationDate;
	}

	public void setEvaluationDate(Date evaluationDate) {
		EvaluationDate = evaluationDate;
	}

	public int getEvaluationOverAllMarks() {
		return EvaluationOverAllMarks;
	}

	public void setEvaluationOverAllMarks(int evaluationOverAllMarks) {
		EvaluationOverAllMarks = evaluationOverAllMarks;
	}

	public Users getSupervisor() {
		return supervisor;
	}

	public void setSupervisor(Users supervisor) {
		this.supervisor = supervisor;
	}

	public Task getTask() {
		return task;
	}

	public void setTask(Task task) {
		this.task = task;
	}

	public int getTotalEvalActivity() {
		return TotalEvalActivity;
	}

	public void setTotalEvalActivity(int totalEvalActivity) {
		TotalEvalActivity = totalEvalActivity;
	}

	public int getTotalEvalMarks() {
		return TotalEvalMarks;
	}

	public void setTotalEvalMarks(int totalEvalMarks) {
		TotalEvalMarks = totalEvalMarks;
	}

	public int getTotalExpMarks() {
		return TotalExpMarks;
	}

	public void setTotalExpMarks(int totalExpMarks) {
		TotalExpMarks = totalExpMarks;
	}

	public int getTotalActFailed() {
		return TotalActFailed;
	}

	public void setTotalActFailed(int totalActFailed) {
		TotalActFailed = totalActFailed;
	}

	public int getTotalActCompleted() {
		return TotalActCompleted;
	}

	public void setTotalActCompleted(int totalActCompleted) {
		TotalActCompleted = totalActCompleted;
	}

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
