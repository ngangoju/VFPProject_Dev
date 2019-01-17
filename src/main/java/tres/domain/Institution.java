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
import javax.persistence.OneToOne;
import javax.persistence.Table;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;

/**
 * author James
 */
@Entity
@Table(name = "Institution")
@NamedQuery(name = "Institution.findAll", query = "SELECT r FROM Institution r order by v desc")
public class Institution extends CommonDomain implements Serializable {
	private static final long serialVersionUID = 1L;
	@Id
	@GeneratedValue
	@Column(name = "institutionId")
	private int institutionId;

	@Column(name = "institutionRegDate", columnDefinition = "DATETIME")
	@Temporal(TemporalType.TIMESTAMP)
	private Date institutionRegDate;

	@ManyToOne
	@JoinColumn(name = "branch")
	private Institution branch;

	@Column(name = "institutionType")
	private String institutionType;
	
	@Column(name="instLogo")
	private String instLogo;

	@OneToOne
	@JoinColumn(name = "request")
	private InstitutionRegistrationRequest request;
 
	
	public int getInstitutionId() {
		return institutionId;
	}

	public void setInstitutionId(int institutionId) {
		this.institutionId = institutionId;
	}

	public String getInstitutionType() {
		return institutionType;
	}

	public void setInstitutionType(String institutionType) {
		this.institutionType = institutionType;
	}

	public Date getInstitutionRegDate() {
		return institutionRegDate;
	}

	public void setInstitutionRegDate(Date institutionRegDate) {
		this.institutionRegDate = institutionRegDate;
	}

	public Institution getBranch() {
		return branch;
	}

	public void setBranch(Institution branch) {
		this.branch = branch;
	}

	public InstitutionRegistrationRequest getRequest() {
		return request;
	}

	public void setRequest(InstitutionRegistrationRequest request) {
		this.request = request;
	}

	public String getInstLogo() {
		return instLogo;
	}

	public void setInstLogo(String instLogo) {
		this.instLogo = instLogo;
	}
	

}
