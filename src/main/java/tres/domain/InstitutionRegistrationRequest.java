package tres.domain;

/**
 * author James
 * */
import java.io.Serializable;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.NamedQuery;
import javax.persistence.Table;

@Entity
@Table(name = "InstitutionRegistrationRequest")
@NamedQuery(name = "InstitutionRegistrationRequest.findAll", query = "SELECT r FROM InstitutionRegistrationRequest r order by v desc")
public class InstitutionRegistrationRequest implements Serializable {
	private static final long serialVersionUID = 1L;
	@Id
	@GeneratedValue
	@Column(name = "instRegReqstId")
	private int instRegReqstId;

	@Column(name = "instRegReqstStatus")
	private String instRegReqstStatus;

	@Column(name = "instRegReqstType")
	private String instRegReqstType;

	@Column(name = "instRegReqstDate")
	private String instRegReqstDate;

	@ManyToOne
	@JoinColumn(name = "institution")
	private Institution institution;

	public int getInstRegReqstId() {
		return instRegReqstId;
	}

	public void setInstRegReqstId(int instRegReqstId) {
		this.instRegReqstId = instRegReqstId;
	}

	public String getInstRegReqstStatus() {
		return instRegReqstStatus;
	}

	public void setInstRegReqstStatus(String instRegReqstStatus) {
		this.instRegReqstStatus = instRegReqstStatus;
	}

	public String getInstRegReqstDate() {
		return instRegReqstDate;
	}

	public void setInstRegReqstDate(String instRegReqstDate) {
		this.instRegReqstDate = instRegReqstDate;
	}

	public Institution getInstitution() {
		return institution;
	}

	public void setInstitution(Institution institution) {
		this.institution = institution;
	}

	public String getInstRegReqstType() {
		return instRegReqstType;
	}

	public void setInstRegReqstType(String instRegReqstType) {
		this.instRegReqstType = instRegReqstType;
	}

}
