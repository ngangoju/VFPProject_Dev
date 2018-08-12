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
public class Institution  extends CommonDomain implements Serializable {
	private static final long serialVersionUID = 1L;
	@Id
	@GeneratedValue
	@Column(name = "institutionId")
	private int institutionId;

	@Column(name = "institutionName")
	private String institutionName;

	@Column(name = "institutionRegDate", columnDefinition = "DATETIME")
	@Temporal(TemporalType.TIMESTAMP)
	private Date  institutionRegDate;

	@Column(name = "institutionType")
	private String institutionType;
	
	@ManyToOne
	@JoinColumn(name = "village")
	private Village village;
	
	@ManyToOne
	@JoinColumn(name = "country")
	private Country country;
	
	
	@OneToOne
	@JoinColumn
	private Users institutionRepresenative;
	
	@ManyToOne
	@JoinColumn(name = "institution")
	private Institution institution;

	public Users getInstitutionRepresenative() {
		return institutionRepresenative;
	}

	public void setInstitutionRepresenative(Users institutionRepresenative) {
		this.institutionRepresenative = institutionRepresenative;
	}

	public int getInstitutionId() {
		return institutionId;
	}

	public void setInstitutionId(int institutionId) {
		this.institutionId = institutionId;
	}

	public String getInstitutionName() {
		return institutionName;
	}

	public void setInstitutionName(String institutionName) {
		this.institutionName = institutionName;
	}


	public String getInstitutionType() {
		return institutionType;
	}

	public void setInstitutionType(String institutionType) {
		this.institutionType = institutionType;
	}

	public Village getVillage() {
		return village;
	}

	public void setVillage(Village village) {
		this.village = village;
	}

	public Date getInstitutionRegDate() {
		return institutionRegDate;
	}

	public void setInstitutionRegDate(Date institutionRegDate) {
		this.institutionRegDate = institutionRegDate;
	}

	public Country getCountry() {
		return country;
	}

	public void setCountry(Country country) {
		this.country = country;
	}

	public Institution getInstitution() {
		return institution;
	}

	public void setInstitution(Institution institution) {
		this.institution = institution;
	}
	
	

}
