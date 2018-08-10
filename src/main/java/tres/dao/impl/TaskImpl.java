package tres.dao.impl;

import java.util.List;

/**
 *
 * @author NGANGO
 */
import tres.dao.generic.AbstractDao;
import tres.dao.interfc.ITask;
import tres.domain.Task;

public class TaskImpl extends AbstractDao<Long, Task> implements ITask {

	public Task saveTask(Task task) {
		return saveIntable(task); 
	}

	public List<Task> getListTasks() {
		return (List<Task>) (Object) getModelList();
	}

	public Task getTaskById(int taskId, String primaryKeyclomunName) {
		return (Task) getModelById(taskId, primaryKeyclomunName);
	}

	public Task UpdateTask(Task task) {
		return updateIntable(task);
	}

}
