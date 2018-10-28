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

}
