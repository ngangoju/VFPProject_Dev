package tres.domain;

import java.io.Serializable;
import java.text.DecimalFormat;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;
import org.hibernate.annotations.Immutable;

@Entity
@Immutable
@Table(name = "Clearance")
public class Clearance extends CommonDomain implements Serializable {
	private static final long serialVersionUID = 1L;
	@Id
	@Column(name = "activities")
	private int activityies;
	
	@Column(name = "strategicPlan")
	private String strategicPlan;
	
	@Column(name = "task")
	private String task;
	
	@Column(name = "created")
	private int created;
	
	@Column(name = "done")
	private int done;
	
	@Column(name = "rate")
	private Double rate;
	
	private static DecimalFormat df2 = new DecimalFormat(".##");
	
	public String getFormatedRate() {
		return df2.format(rate);
		
	}

	public int getActivityies() {
		return activityies;
	}

	public void setActivityies(int activityies) {
		this.activityies = activityies;
	}

	public String getStrategicPlan() {
		return strategicPlan;
	}

	public void setStrategicPlan(String strategicPlan) {
		this.strategicPlan = strategicPlan;
	}

	public String getTask() {
		return task;
	}

	public void setTask(String task) {
		this.task = task;
	}

	public int getCreated() {
		return created;
	}

	public void setCreated(int created) {
		this.created = created;
	}

	public int getDone() {
		return done;
	}

	public void setDone(int done) {
		this.done = done;
	}

	public Double getRate() {
		return rate;
	}

	public void setRate(Double rate) {
		this.rate = rate;
	}

	public static long getSerialversionuid() {
		return serialVersionUID;
	}
	
}
