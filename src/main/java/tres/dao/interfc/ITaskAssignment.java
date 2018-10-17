package tres.dao.interfc;

import java.util.List;
import tres.domain.TaskAssignment;

public interface ITaskAssignment {
	public TaskAssignment saveTaskAssignment(TaskAssignment taskAssignment);

	public List<TaskAssignment> getListTaskAssignments();

	public TaskAssignment getTaskAssignmentById(int taskAssignmentId, String primaryKeyclomunName);

	public TaskAssignment UpdateTask(TaskAssignment taskAssignment);
}
