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
@Table(name = "Task")
@NamedQuery(name = "Task.findAll", query = "select r from Task r order by v desc")
public class Task extends CommonDomain implements Serializable {

	private static final long serialVersionUID = 1L;
	@Id
	@GeneratedValue
	@Column(name = "taskId")
	private int taskId;

	@Column(name = "taskName")
	private String taskName;

	@Column(name = "description")
	private String description;

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
	@JoinColumn(name = "parentTask")
	private Task parentTask;

	@ManyToOne
	@JoinColumn(name = "strategicPlan")
	private StrategicPlan strategicPlan;
	
	@ManyToOne
	@JoinColumn(name = "board")
	private Board board;

	public int getTaskId() {
		return taskId;
	}

	public void setTaskId(int taskId) {
		this.taskId = taskId;
	}

	public String getDescription() {
		return description;
	}

	public void setDescription(String description) {
		this.description = description;
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

	public StrategicPlan getStrategicPlan() {
		return strategicPlan;
	}

	public void setStrategicPlan(StrategicPlan strategicPlan) {
		this.strategicPlan = strategicPlan;
	}

	public Task getParentTask() {
		return parentTask;
	}

	public void setParentTask(Task parentTask) {
		this.parentTask = parentTask;
	}

	public String getTaskName() {
		return taskName;
	}

	public void setTaskName(String taskName) {
		this.taskName = taskName;
	}

	public Board getBoard() {
		return board;
	}

	public void setBoard(Board board) {
		this.board = board;
	}

}
