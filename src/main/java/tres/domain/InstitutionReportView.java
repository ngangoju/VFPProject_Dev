package tres.domain;

import java.io.Serializable;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.NamedQuery;
import javax.persistence.Table;

import org.hibernate.annotations.Immutable;


@Entity
@Immutable
@Table(name = "InstitutionReportView")
//@NamedQuery(name = "InstitutionReportView.findAll", query = "select r from InstitutionReportView r order by v desc")
public class InstitutionReportView extends CommonDomain implements Serializable {
	private static final long serialVersionUID = 1L;
	@Id
	@Column(name = "activity")
	private int activity;
	/*
	@Column(name = "createdBy")
	private String createdBy;
	
	@Column(name = "crtdDtTime")
	private String crtdDtTime;
	
	@Column(name = "genericStatus")
	private String genericStatus;
	
	@Column(name = "optLock")
	private String optLock;
	
	@Column(name = "upDtTime")
	private String upDtTime;
	
	@Column(name = "updatedBy")
	private String updatedBy;*/
	
	@Column(name = "stategicplan")
	private String stategicplan;
	
	@Column(name = "mytask")
	private String mytask;
	
	@Column(name = "myName")
	private String myName;
	
	@Column(name="activityName")
	private String activityName;
	
	@Column(name = "boardname")
	private String boardname;
	
	@Column(name = "status")
	private String status;
	
	@Column(name = "institutionName")
	private String institutionName;

	public int getActivity() {
		return activity;
	}

	public void setActivity(int activity) {
		this.activity = activity;
	}




	public String getBoardname() {
		return boardname;
	}

	public void setBoardname(String boardname) {
		this.boardname = boardname;
	}

	public String getStatus() {
		return status;
	}

	public void setStatus(String status) {
		this.status = status;
	}

	public String getInstitutionName() {
		return institutionName;
	}

	public void setInstitutionName(String institutionName) {
		this.institutionName = institutionName;
	}

	public String getStategicplan() {
		return stategicplan;
	}

	public void setStategicplan(String stategicplan) {
		this.stategicplan = stategicplan;
	}

	public String getMytask() {
		return mytask;
	}

	public void setMytask(String mytask) {
		this.mytask = mytask;
	}

	public String getMyName() {
		return myName;
	}

	public void setMyName(String myName) {
		this.myName = myName;
	}

	public String getActivityName() {
		return activityName;
	}

	public void setActivityName(String activityName) {
		this.activityName = activityName;
	}

	public static long getSerialversionuid() {
		return serialVersionUID;
	}
	
	
}
