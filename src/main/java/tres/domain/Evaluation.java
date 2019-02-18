package tres.domain;

import java.io.Serializable;
import java.util.Date;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;

import javax.persistence.Table;

@Entity
@Table(name = "Evaluation")

public class Evaluation extends CommonDomain implements Serializable {
	private static final long serialVersionUID = 1L;
	@Id
	@GeneratedValue
	@Column(name = "evaluationId")
	private int evaluationId;

	@Column(name = "EvaluationType")
	private String EvaluationType;

	@Column(name = "decision")
	private String decision;

	@Column(name = "EvaluationDate")
	private Date EvaluationDate; 

	@Column(name = "EvaluationMarks")
	private int EvaluationMarks;

	@ManyToOne
	@JoinColumn(name = "activity")
	private Activity activity; 

	@ManyToOne
	@JoinColumn(name = "supervisor")
	private Users supervisor; 

	
	public int getEvaluationMarks() {
		return EvaluationMarks;
	}

	public void setEvaluationMarks(int evaluationMarks) {
		EvaluationMarks = evaluationMarks;
	}

	 

	public int getEvaluationId() {
		return evaluationId;
	}

	public void setEvaluationId(int evaluationId) {
		this.evaluationId = evaluationId;
	}

	public String getEvaluationType() {
		return EvaluationType;
	}

	public void setEvaluationType(String evaluationType) {
		EvaluationType = evaluationType;
	}

	public Date getEvaluationDate() {
		return EvaluationDate;
	}

	public void setEvaluationDate(Date evaluationDate) {
		EvaluationDate = evaluationDate;
	}

	public Activity getActivity() {
		return activity;
	}

	public void setActivity(Activity activity) {
		this.activity = activity;
	}

	public String getDecision() {
		return decision;
	}

	public void setDecision(String decision) {
		this.decision = decision;
	}

	public Users getSupervisor() {
		return supervisor;
	}

	public void setSupervisor(Users supervisor) {
		this.supervisor = supervisor;
	}

	

}