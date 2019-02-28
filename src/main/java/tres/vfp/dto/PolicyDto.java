package tres.vfp.dto;

import java.io.Serializable;

import javax.persistence.Column;
import javax.persistence.JoinColumn;
import javax.persistence.OneToOne;

import tres.domain.Institution;

public class PolicyDto implements Serializable{
	private static final long serialVersionUID = 1L;
	
	private int policyId;
 
	private int reschduleTime;
 
	private String Status;
 
	private Institution institution;
 
	private double LongMarks;
 
	private double mediumgMarks;
 
	private double shortMarks;
	
	private boolean editable;
 
	private int planPeriod;
 
	private int variation;
	
	private boolean shwBtn;
	
	

	public boolean isShwBtn() {
		return shwBtn;
	}

	public void setShwBtn(boolean shwBtn) {
		this.shwBtn = shwBtn;
	}

	public boolean isEditable() {
		return editable;
	}

	public void setEditable(boolean editable) {
		this.editable = editable;
	}

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
