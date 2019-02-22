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
	
	@Column(name = "mytaskName")
	private String mytaskName;
	
	public String getMytaskName() {
		return mytaskName;
	}

	public void setMytaskName(String mytaskName) {
		this.mytaskName = mytaskName;
	}

	public String getBoard() {
		return board;
	}

	public void setBoard(String board) {
		this.board = board;
	}

	public String getStatus() {
		return status;
	}

	public void setStatus(String status) {
		this.status = status;
	}

	@Column(name="activityName")
	private String activityName;
	
	@Column(name = "board")
	private String board;
	
	@Column(name = "status")
	private String status;
	
	

	public int getActivity() {
		return activity;
	}

	public void setActivity(int activity) {
		this.activity = activity;
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
