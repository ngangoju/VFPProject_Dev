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
import javax.persistence.Table;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;

/**
 *
 * @author NGANGO
 */
@Entity
@Table(name = "StrategicPlan")
@NamedQuery(name = "strategicPlan.findAll", query = "SELECT r FROM StrategicPlan r order by v desc")
public class StrategicPlan  extends CommonDomain implements Serializable {

	private static final long serialVersionUID = 1L;
	@Id
	@GeneratedValue
	@Column(name = "planId")
	private int planId;

	@Column(name = "details")
	private String details;

	@Column(name = "recordedDate", columnDefinition = "DATETIME")
	@Temporal(TemporalType.TIMESTAMP)
	private Date recordedDate;
	
	
	@ManyToOne
	@JoinColumn(name = "Board")
	private Board Board;
	

	public int getPlanId() {
		return planId;
	}

	public void setPlanId(int planId) {
		this.planId = planId;
	}

	public String getDetails() {
		return details;
	}

	public void setDetails(String details) {
		this.details = details;
	}

	public Date getRecordedDate() {
		return recordedDate;
	}

	public void setRecordedDate(Date recordedDate) {
		this.recordedDate = recordedDate;
	}

	public Board getBoard() {
		return Board;
	}

	public void setBoard(Board board) {
		Board = board;
	}

	

}
