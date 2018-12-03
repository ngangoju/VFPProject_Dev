package tres.domain;

/**
 * author James
 * */
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

@Entity
@Table(name = "InstitutionRegistrationRequest")
@NamedQuery(name = "InstitutionRegistrationRequest.findAll", query = "SELECT r FROM InstitutionRegistrationRequest r order by v desc")
public class InstitutionRegistrationRequest extends CommonDomain implements Serializable {
	private static final long serialVersionUID = 1L;
	@Id
	@GeneratedValue
	@Column(name = "instRegReqstId")
	private int instRegReqstId;

	@Column(name = "institutionName")
	private String institutionName;

	@Column(name = "institutionAddress")
	private String institutionAddress;

	@Column(name = "instRegReqstStatus")
	private String instRegReqstStatus;

	@Column(name = "instRegReqstType")
	private String instRegReqstType;

	@ManyToOne
	@JoinColumn(name = "village")
	private Village village;

	@ManyToOne
	@JoinColumn(name = "country")
	private Country country;

	@Column(name = "instRegReqstDate", columnDefinition = "DATETIME")
	@Temporal(TemporalType.TIMESTAMP)
	private Date instRegReqstDate;

	@ManyToOne
	@JoinColumn(name = "institution")
	private Institution institution;

	@OneToOne
	@JoinColumn
	private Users institutionRepresenative;

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

	public Date getInstRegReqstDate() {
		return instRegReqstDate;
	}

	public void setInstRegReqstDate(Date instRegReqstDate) {
		this.instRegReqstDate = instRegReqstDate;
	}

	public String getInstitutionName() {
		return institutionName;
	}

	public void setInstitutionName(String institutionName) {
		this.institutionName = institutionName;
	}

	public String getInstitutionAddress() {
		return institutionAddress;
	}

	public void setInstitutionAddress(String institutionAddress) {
		this.institutionAddress = institutionAddress;
	}

	public Users getInstitutionRepresenative() {
		return institutionRepresenative;
	}

	public void setInstitutionRepresenative(Users institutionRepresenative) {
		this.institutionRepresenative = institutionRepresenative;
	}

	public Village getVillage() {
		return village;
	}

	public void setVillage(Village village) {
		this.village = village;
	}

	public Country getCountry() {
		return country;
	}

	public void setCountry(Country country) {
		this.country = country;
	}

}
