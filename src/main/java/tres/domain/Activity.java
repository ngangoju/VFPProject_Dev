package tres.domain;

import java.io.Serializable;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

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
@Table(name = "Activity")
@NamedQuery(name = "Activity.findAll", query = "select r from Activity r order by v desc")
public class Activity extends CommonDomain implements Serializable {

	private static final long serialVersionUID = 1L;
	@Id
	@GeneratedValue
	@Column(name = "ACTIVITY_ID")
	private int activityId;

	@Column(name = "description")
	private String description;

	@Column(name = "status")
	private String status;

	@Column(name = "weight")
	private String weight;

	@Column(name = "type")
	private String type;
	
	@Column(name="ActivityFailed",nullable = false, columnDefinition ="int default 0")
	private int countActivityFailed;
	@Column(name="ActivityApproved",nullable = false, columnDefinition ="int default 0")
	private int countApproved;
	@Column(name="ActivityPlanned",nullable = false, columnDefinition ="int default 0")
	private int countPlanned;
	@Column(name="ActivityReported",nullable = false, columnDefinition ="int default 0")
	private int countReported;
	@Column(name = "creationDate", columnDefinition = "DATETIME")
	@Temporal(TemporalType.TIMESTAMP)
	private Date date;

	@Column(name = "startDate", columnDefinition = "DATETIME")
	@Temporal(TemporalType.TIMESTAMP)
	private Date startDate;

	@Column(name = "dueDate", columnDefinition = "DATETIME")
	@Temporal(TemporalType.TIMESTAMP)
	private Date dueDate;

	@Column(name = "endDate", columnDefinition = "DATETIME")
	@Temporal(TemporalType.TIMESTAMP)
	private Date endDate;

	@ManyToOne
	@JoinColumn(name = "task")
	private Task task;

	@ManyToOne
	@JoinColumn(name = "user")
	private Users user;
	
	public int getActivityId() {
		return activityId;
	}

	public void setActivityId(int activityId) {
		this.activityId = activityId;
	}

	public String getDescription() {
		return description;
	}

	public void setDescription(String description) {
		this.description = description;
	}

	public String getStatus() {
		return status;
	}

	public void setStatus(String status) {
		this.status = status;
	}

	public String getWeight() {
		return weight;
	}

	public void setWeight(String weight) {
		this.weight = weight;
	}

	public Date getDate() {
		return date;
	}

	public void setDate(Date date) {
		this.date = date;
	}

	public Date getStartDate() {
		return startDate;
	}

	public void setStartDate(Date startDate) {
		this.startDate = startDate;
	}

	public Date getDueDate() {
		return dueDate;
	}

	public void setDueDate(Date dueDate) {
		this.dueDate = dueDate;
	}

	public Date getEndDate() {
		return endDate;
	}

	public void setEndDate(Date endDate) {
		this.endDate = endDate;
	}

	public Task getTask() {
		return task;
	}

	public void setTask(Task task) {
		this.task = task;
	}

	public Users getUser() {
		return user;
	}

	public void setUser(Users user) {
		this.user = user;
	}
	
	public String getType() {
		return type;
	}

	public void setType(String type) {
		this.type = type;
	}

	public int getCountActivityFailed() {
		return countActivityFailed;
	}

	public void setCountActivityFailed(int countActivityFailed) {
		this.countActivityFailed = countActivityFailed;
	}

	public int getCountApproved() {
		return countApproved;
	}

	public void setCountApproved(int countApproved) {
		this.countApproved = countApproved;
	}
	
	public int getCountPlanned() {
		return countPlanned;
	}

	public void setCountPlanned(int countPlanned) {
		this.countPlanned = countPlanned;
	}

	public int getCountReported() {
		return countReported;
	}

	public void setCountReported(int countReported) {
		this.countReported = countReported;
	}	
	
}

