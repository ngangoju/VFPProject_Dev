package tres.domain;

import java.io.Serializable;
import java.util.Date;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.NamedQuery;
import javax.persistence.Table;

/**
 *
 * @author NGANGO
 */
@Entity
@Table(name = "StrategicPlan")
@NamedQuery(name = "strategicPlan.findAll", query = "SELECT r FROM StrategicPlan r order by v desc")
public class StrategicPlan implements Serializable {

	private static final long serialVersionUID = 1L;
	@Id
	@GeneratedValue
	@Column(name = "planId")
	private int planId;

	@Column(name = "details")
	private String details;

	@Column(name = "date")
	private Date date;

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

	public Date getDate() {
		return date;
	}

	public void setDate(Date date) {
		this.date = date;
	}

}
