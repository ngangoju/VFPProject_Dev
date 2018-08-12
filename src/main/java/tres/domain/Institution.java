package tres.domain;

import java.io.Serializable;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.NamedQuery;
import javax.persistence.OneToOne;
import javax.persistence.Table;

/**
 * author James
 */
@Entity
@Table(name = "Institution")
@NamedQuery(name = "Institution.findAll", query = "SELECT r FROM Institution r order by v desc")
public class Institution implements Serializable {
	private static final long serialVersionUID = 1L;
	@Id
	@GeneratedValue
	@Column(name = "institutionId")
	private int institutionId;

	@Column(name = "institutionName")
	private String institutionName;

	@Column(name = "institutionRegDate")
	private String institutionRegDate;

	@Column(name = "institutionType")
	private String institutionType;
	@ManyToOne
	@JoinColumn(name = "village")
	private Village village;
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

	public String getInstitutionRegDate() {
		return institutionRegDate;
	}

	public void setInstitutionRegDate(String institutionRegDate) {
		this.institutionRegDate = institutionRegDate;
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

}
