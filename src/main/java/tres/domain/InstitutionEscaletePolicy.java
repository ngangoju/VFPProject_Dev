package tres.domain;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.OneToOne;
import javax.persistence.Table;

@Entity
@Table(name = "InstitutionEscaletePolicy")
public class InstitutionEscaletePolicy extends CommonDomain {
	private static final long serialVersionUID = 1L;
	@Id
	@GeneratedValue
	@Column(name = "policyId")
	private int policyId;

	@Column(name = "reschduleTime")
	private int reschduleTime;

	@Column(name = "Status")
	private String Status;

	@OneToOne
	@JoinColumn(name = "institution")
	private Institution institution;

	@Column(name = "LongMarks")
	private double LongMarks;

	@Column(name = "mediumgMarks")
	private double mediumgMarks;

	@Column(name = "shortMarks")
	private double shortMarks;

	@Column(name = "planPeriod")
	private int planPeriod;

	@Column(name = "variation")
	private int variation;

	public int getPolicyId() {
		return policyId;
	}

	public void setPolicyId(int policyId) {
		this.policyId = policyId;
	}

	public int getReschduleTime() {
		return reschduleTime;
	}

	public void setReschduleTime(int reschduleTime) {
		this.reschduleTime = reschduleTime;
	}

	public String getStatus() {
		return Status;
	}

	public void setStatus(String status) {
		Status = status;
	}

	public Institution getInstitution() {
		return institution;
	}

	public void setInstitution(Institution institution) {
		this.institution = institution;
	}

	public double getLongMarks() {
		return LongMarks;
	}

	public void setLongMarks(double longMarks) {
		LongMarks = longMarks;
	}

	public double getMediumgMarks() {
		return mediumgMarks;
	}

	public void setMediumgMarks(double mediumgMarks) {
		this.mediumgMarks = mediumgMarks;
	}

	public double getShortMarks() {
		return shortMarks;
	}

	public void setShortMarks(double shortMarks) {
		this.shortMarks = shortMarks;
	}

	public int getPlanPeriod() {
		return planPeriod;
	}

	public void setPlanPeriod(int planPeriod) {
		this.planPeriod = planPeriod;
	}

	public int getVariation() {
		return variation;
	}

	public void setVariation(int variation) {
		this.variation = variation;
	}
	

}
