package tres.dao.interfc;

/**
 * author Junior
 **/

import java.util.List;
import tres.domain.Task;

public interface ITask {
	public Task saveTask(Task task);
    public List<Task> getListTasks();
    public Task getTaskById(int taskId,String primaryKeyclomunName);
    public Task UpdateTask(Task task);  
}
