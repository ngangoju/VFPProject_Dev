package tres.vfp.dto;

import java.io.Serializable;
import java.util.Date;

import tres.domain.Country;
import tres.domain.Institution;
import tres.domain.UploadingFiles;
import tres.domain.Users;
import tres.domain.Village;

public class InstitutionDto implements Serializable {

	private static final long serialVersionUID = 1L;

	private int institutionId;

	private String institutionName;

	private String institutionAddress;

	private Date institutionRegDate;

	private Institution institution;

	private Country country;

	private Village village;

	private Users user;

	private boolean editable;

	private UploadingFiles institutionLogo;

	public boolean isEditable() {
		return editable;
	}

	public void setEditable(boolean editable) {
		this.editable = editable;
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

	public String getInstitutionAddress() {
		return institutionAddress;
	}

	public void setInstitutionAddress(String institutionAddress) {
		this.institutionAddress = institutionAddress;
	}

	public Date getInstitutionRegDate() {
		return institutionRegDate;
	}

	public void setInstitutionRegDate(Date institutionRegDate) {
		this.institutionRegDate = institutionRegDate;
	}

	public Institution getInstitution() {
		return institution;
	}

	public void setInstitution(Institution institution) {
		this.institution = institution;
	}

	public Users getUser() {
		return user;
	}

	public void setUser(Users user) {
		this.user = user;
	}

	public Country getCountry() {
		return country;
	}

	public void setCountry(Country country) {
		this.country = country;
	}

	public Village getVillage() {
		return village;
	}

	public void setVillage(Village village) {
		this.village = village;
	}

	@Override
	public String toString() {
		return "InstitutionDto [institutionName=" + institutionName + "]";
	}

	public UploadingFiles getInstitutionLogo() {
		return institutionLogo;
	}

	public void setInstitutionLogo(UploadingFiles institutionLogo) {
		this.institutionLogo = institutionLogo;
	}

}
