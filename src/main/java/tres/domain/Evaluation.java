package tres.domain;

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


public class Evaluation {
	private static final long serialVersionUID = 1L;
	@Id
	@GeneratedValue
	@Column(name = "EavaluationId")
	private int EavaluationId;

	@Column(name = "EvaluationType")
	private String EvaluationType;

	@Column(name = "EvaluationDate")
	private Date EvaluationDate;
	
	@Column(name = "EvaluationOverAllMarks")
	private int EvaluationOverAllMarks;
	
	public int getEvaluationOverAllMarks() {
		return EvaluationOverAllMarks;
	}

	public void setEvaluationOverAllMarks(int evaluationOverAllMarks) {
		EvaluationOverAllMarks = evaluationOverAllMarks;
	}

	public int getEvaluationMarks() {
		return EvaluationMarks;
	}

	public void setEvaluationMarks(int evaluationMarks) {
		EvaluationMarks = evaluationMarks;
	}

	@Column(name = "EvaluationMarks")
	private int EvaluationMarks;
	
	
	
	@ManyToOne
	@JoinColumn(name = "ACTIVITYID")
	private Activity activity;

	public int getEavaluationId() {
		return EavaluationId;
	}

	public void setEavaluationId(int eavaluationId) {
		EavaluationId = eavaluationId;
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

	public static long getSerialversionuid() {
		return serialVersionUID;
	}
	

}