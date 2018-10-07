package tres.vfp.dto;

import java.io.Serializable;
import java.util.Date;

import tres.domain.Evaluation;
import tres.domain.Task;

public class EvaluationDto implements Serializable {	
	private static final long serialVersionUID = 1L;
	
	private int evaluationId;
	
	private String evaluationType;

	private String description;

    private Evaluation evauation;

	private String status;

	private Task task;

	private String genericstatus;
	
	private Date createdDate;

	private boolean editable;

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

	public String getDescription() {
		return description;
	}

	public void setDescription(String description) {
		this.description = description;
	}

	public Evaluation getEvauation() {
		return evauation;
	}

	public void setEvauation(Evaluation evauation) {
		this.evauation = evauation;
	}

	public String getStatus() {
		return status;
	}

	public void setStatus(String status) {
		this.status = status;
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
	
}
