package tres.dao.impl;

import java.util.List;

import tres.dao.generic.AbstractDao;
import tres.dao.interfc.ITaskAssignment;
import tres.domain.TaskAssignment;

public class TaskAssignmentImpl extends AbstractDao<Long, TaskAssignment> implements ITaskAssignment {
	public TaskAssignment saveTaskAssignment(TaskAssignment taskAssignment) {
		return saveIntable(taskAssignment); 
	}

	public List<TaskAssignment> getListTaskAssignments() {
		return (List<TaskAssignment>) (Object) getModelList();
	}

	public TaskAssignment getTaskAssignmentById(int taskAssignmentId, String primaryKeyclomunName) {
		return (TaskAssignment) getModelById(taskAssignmentId, primaryKeyclomunName);
	}

	public TaskAssignment UpdateTask(TaskAssignment taskAssignment) {
		return updateIntable(taskAssignment);
	}

}
