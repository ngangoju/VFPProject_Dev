package tres.domain;

import java.io.Serializable;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.NamedQuery;
import javax.persistence.Table;
import javax.persistence.Transient;
import javax.persistence.UniqueConstraint;

@Entity
@Table(name = "TaskAssignment", uniqueConstraints = @UniqueConstraint(columnNames = { "user", "task" }))
@NamedQuery(name = "TaskAssignment.findAll", query = "select r from TaskAssignment r order by v desc")
public class TaskAssignment extends CommonDomain implements Serializable {

	private static final long serialVersionUID = 1L;
	@Id
	@GeneratedValue
	@Column(name = "taskAssignmentId")
	private int taskAssignmentId;

	@ManyToOne
	@JoinColumn(name = "user")
	private Users user;

	@ManyToOne
	@JoinColumn(name = "task")
	private Task task;
	@Transient
	private boolean redIcon;
	@Transient
	private boolean yellowIcon;
	@Transient
	private boolean greenIcon;
	@Transient
	private String taskWeight;
	public int getTaskAssignmentId() {
		return taskAssignmentId;
	}

	public void setTaskAssignmentId(int taskAssignmentId) {
		this.taskAssignmentId = taskAssignmentId;
	}

	public Users getUser() {
		return user;
	}

	public void setUser(Users user) {
		this.user = user;
	}

	public Task getTask() {
		return task;
	}

	public void setTask(Task task) {
		this.task = task;
	}

	public boolean isRedIcon() {
		return redIcon;
	}

	public void setRedIcon(boolean redIcon) {
		this.redIcon = redIcon;
	}

	public boolean isYellowIcon() {
		return yellowIcon;
	}

	public void setYellowIcon(boolean yellowIcon) {
		this.yellowIcon = yellowIcon;
	}

	public boolean isGreenIcon() {
		return greenIcon;
	}

	public void setGreenIcon(boolean greenIcon) {
		this.greenIcon = greenIcon;
	}

	public String getTaskWeight() {
		return taskWeight;
	}

	public void setTaskWeight(String taskWeight) {
		this.taskWeight = taskWeight;
	}
}
