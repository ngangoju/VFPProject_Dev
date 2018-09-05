package tres.vfp.dto;

import java.util.Date;

import tres.domain.Institution;

public class InstitutionDto {
	private static final long serialVersionUID = 1L;

	private int instRegReqstId;

	private String instRegReqstStatus;

	private String instRegReqstType;

	private Date instRegReqstDate;

	private Institution institution;

	private boolean editable;

	public boolean isEditable() {
		return editable;
	}

	public void setEditable(boolean editable) {
		this.editable = editable;
	}

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

	public Institution getInstitution() {
		return institution;
	}

	public void setInstitution(Institution institution) {
		this.institution = institution;
	}

}
