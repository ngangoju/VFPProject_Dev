package tres.domain;
import java.io.Serializable;
import java.util.Date;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.NamedQuery;
import javax.persistence.Table;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;

import org.hibernate.annotations.Immutable;

@Entity
@Immutable
@Table(name = "StaffReportView")
public class StaffReportView extends CommonDomain implements Serializable {
	private static final long serialVersionUID = 1L;
	@Id
	@Column(name = "activity")
	private int activity;
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
	@Column(name = "dueDate", columnDefinition = "DATETIME")
	@Temporal(TemporalType.TIMESTAMP)
	private Date dueDate;
	@Column(name = "startDate", columnDefinition = "DATETIME")
	@Temporal(TemporalType.TIMESTAMP)
	private Date startDate;
	@Column(name="ActivityApproved",nullable = false, columnDefinition ="int default 0")
	private int ActivityApproved;
	@Column(name="ActivityPlanned",nullable = false, columnDefinition ="int default 0")
	private int ActivityPlanned;
	@Column(name="ActivityReported",nullable = false, columnDefinition ="int default 0")
	private int ActivityReported;
	@Column(name="ActivityFailed",nullable = false, columnDefinition ="int default 0")
	private int ActivityFailed;
	
	public int getActivityFailed() {
		return ActivityFailed;
	}

	public void setActivityFailed(int activityFailed) {
		ActivityFailed = activityFailed;
	}

	public int getActivityReported() {
		return ActivityReported;
	}

	public void setActivityReported(int activityReported) {
		ActivityReported = activityReported;
	}

	public int getActivityPlanned() {
		return ActivityPlanned;
	}

	public void setActivityPlanned(int activityPlanned) {
		ActivityPlanned = activityPlanned;
	}

	public int getActivity() {
		return activity;
	}

	public void setActivity(int activity) {
		this.activity = activity;
	}

	public Date getDueDate() {
		return dueDate;
	}

	public void setDueDate(Date dueDate) {
		this.dueDate = dueDate;
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

	public int getActivityApproved() {
		return ActivityApproved;
	}

	public void setActivityApproved(int activityApproved) {
		ActivityApproved = activityApproved;
	}

	public Date getStartDate() {
		return startDate;
	}

	public void setStartDate(Date startDate) {
		this.startDate = startDate;
	}
	
}
