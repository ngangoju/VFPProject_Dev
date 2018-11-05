package tres.dao.impl;

import java.util.List;

/**
 *
 * @author NGANGO
 */
import tres.dao.generic.AbstractDao;
import tres.dao.interfc.ITaskComment;
import tres.domain.TaskComment;

public class TaskCommentImpl extends AbstractDao<Long, TaskComment> implements ITaskComment {

	public TaskComment saveTaskComment(TaskComment TaskComment) {
		return saveIntable(TaskComment);
	}

	public List<TaskComment> getListTaskComments() {
		return (List<TaskComment>) (Object) getModelList();
	}

	public TaskComment getTaskCommentById(int TaskCommentId, String primaryKeyclomunName) {
		return (TaskComment) getModelById(TaskCommentId, primaryKeyclomunName);
	}

	public TaskComment UpdateTaskComment(TaskComment TaskComment) {
		return updateIntable(TaskComment);
	}

}
