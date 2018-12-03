package tres.vfp.dto;

import java.io.Serializable;
import java.util.Date;

import javax.security.auth.message.callback.PrivateKeyCallback.Request;

import tres.domain.Country;
import tres.domain.Institution;
import tres.domain.InstitutionRegistrationRequest;
import tres.domain.UploadingFiles;
import tres.domain.Users;
import tres.domain.Village;

public class InstitutionDto implements Serializable {

	private static final long serialVersionUID = 1L;

	private int institutionId;

	private Date institutionRegDate;

	private String institutionType;
	
	private String instLogo;

	private Institution branch;

	private InstitutionRegistrationRequest request;

	private boolean editable;

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

	public String getInstitutionType() {
		return institutionType;
	}

	public void setInstitutionType(String institutionType) {
		this.institutionType = institutionType;
	}

	public String getInstLogo() {
		return instLogo;
	}

	public void setInstLogo(String instLogo) {
		this.instLogo = instLogo;
	}
	

}
