package tres.vfp.dto;
import java.sql.Timestamp;

import tres.domain.Task;
import tres.domain.Users;

public class TaskAssignmentDto {
	private int taskAssignmentId;
	private Users user;
	private Task task;
	private String TaskWeight;
	private Timestamp crtdDtTime;
	private boolean redIcon;
	private boolean yellowIcon;
	private boolean greenIcon;
	
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

	public Timestamp getCrtdDtTime() {
		return crtdDtTime;
	}

	public void setCrtdDtTime(Timestamp crtdDtTime) {
		this.crtdDtTime = crtdDtTime;
	}

	public String getTaskWeight() {
		return TaskWeight;
	}

	public void setTaskWeight(String taskWeight) {
		TaskWeight = taskWeight;
	}

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
}
