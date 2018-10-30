package tres.dao.interfc;

import java.util.List;
import tres.domain.TaskComment;

/**
 *
 * @author NGANGO
 */
public interface ITaskComment {
	public TaskComment saveTaskComment(TaskComment taskComment);

	public List<TaskComment> getListTaskComments();

	public TaskComment getTaskCommentById(int taskCommentId, String primaryKeyclomunName);

	public TaskComment UpdateTaskComment(TaskComment taskComment);
}
