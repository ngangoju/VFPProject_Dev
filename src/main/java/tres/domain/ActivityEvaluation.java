package tres.domain;

import java.io.Serializable;
import java.util.Date;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.NamedQuery;
import javax.persistence.Table;
/**
 *
 * @author Emma
 */
@Entity
@Table(name = "ActivityEvaluation")
@NamedQuery(name = "ActivityEvaluation.findAll", query = "select r from ActivityEvaluation r order by v desc")
public class ActivityEvaluation extends CommonDomain implements Serializable {
	private static final long serialVersionUID = 1L;
	@Id
	@GeneratedValue
	@Column(name = "evaluationId")
	private int evaluationId;
	@Column(name = "decision")
	private String decision;

	@Column(name = "EvaluationDate")
	private Date EvaluationDate; 

	@Column(name = "EvaluationMarks")
	private int EvaluationMarks;
	
	@Column(name = "EvaluationOverAllMarks")
	private int EvaluationOverAllMarks;
	@ManyToOne
	@JoinColumn(name = "activity")
	private Activity activity; 

	@ManyToOne
	@JoinColumn(name = "supervisor")
	private Users supervisor;

	public int getEvaluationId() {
		return evaluationId;
	}

	public void setEvaluationId(int evaluationId) {
		this.evaluationId = evaluationId;
	}

	public String getDecision() {
		return decision;
	}

	public void setDecision(String decision) {
		this.decision = decision;
	}

	public Date getEvaluationDate() {
		return EvaluationDate;
	}

	public void setEvaluationDate(Date evaluationDate) {
		EvaluationDate = evaluationDate;
	}

	public int getEvaluationMarks() {
		return EvaluationMarks;
	}

	public void setEvaluationMarks(int evaluationMarks) {
		EvaluationMarks = evaluationMarks;
	}

	public Activity getActivity() {
		return activity;
	}

	public void setActivity(Activity activity) {
		this.activity = activity;
	}

	public Users getSupervisor() {
		return supervisor;
	}

	public void setSupervisor(Users supervisor) {
		this.supervisor = supervisor;
	}

	public int getEvaluationOverAllMarks() {
		return EvaluationOverAllMarks;
	}

	public void setEvaluationOverAllMarks(int evaluationOverAllMarks) {
		EvaluationOverAllMarks = evaluationOverAllMarks;
	} 		
}

