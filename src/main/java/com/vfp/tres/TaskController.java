package com.vfp.tres;

import java.io.Serializable;
import java.sql.Timestamp;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.List;
import java.util.logging.Logger;

import javax.annotation.PostConstruct;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.ViewScoped;
import javax.servlet.http.HttpSession;

import tres.common.DbConstant;
import tres.common.JSFBoundleProvider;
import tres.common.JSFMessagers;
import tres.common.SessionUtils;
import tres.dao.impl.StrategicPlanImpl;
import tres.dao.impl.TaskImpl;
import tres.dao.impl.UserImpl;
import tres.domain.Activity;
import tres.domain.StrategicPlan;
import tres.domain.Task;
import tres.domain.Users;
import tres.vfp.dto.ActivityDto;
import tres.vfp.dto.TaskDto;

@ManagedBean
@ViewScoped
public class TaskController implements Serializable, DbConstant {
	private static final Logger LOGGER = Logger.getLogger(Thread.currentThread().getStackTrace()[0].getClassName());
	private String CLASSNAME = "TaskController :: ";
	private static final long serialVersionUID = 1L;
	/*to manage validation messages*/
	private boolean isValid;
	private int taskID;
	/*end  manage validation messages*/
	private Users users;
	private Users usersSession;
	private Task task;
	private List<Task> taskDetails = new ArrayList<Task>();
	private List<Task> taskDetail = new ArrayList<Task>();
	private List<TaskDto> taskDtoDetails = new ArrayList<TaskDto>();
	private List<TaskDto> taskDtoDetail = new ArrayList<TaskDto>();
	
	/*class injection*/
	
	JSFBoundleProvider provider = new JSFBoundleProvider();
	UserImpl usersImpl = new UserImpl();
	TaskImpl taskImpl = new TaskImpl();
	
	/*end class injection*/
	Timestamp timestamp = new Timestamp(Calendar.getInstance().getTime().getTime());
	
	@SuppressWarnings("unchecked")
	@PostConstruct
	public void init() {
		HttpSession session = SessionUtils.getSession();
		usersSession= (Users) session.getAttribute("userSession");
		
		if (users == null) {
			users = new Users();
		}
		
		if (task == null) {
			task = new Task();
		}
		
		try {
			taskDetail=taskImpl.getGenericListWithHQLParameter(new String[] {"genericStatus"},new Object[] {ACTIVE}, "Task", "taskId asc");
			for (Task task : taskDetail){
				TaskDto taskDto = new TaskDto();
				taskDto.setTaskId(task.getTaskId());
				taskDto.setEditable(false);
				taskDto.setDescription(task.getDescription());
				taskDto.setTaskName(task.getTaskName());
				taskDto.setStartDate(task.getStartDate());
				taskDto.setCreatedDate(task.getCrtdDtTime());
				taskDto.setTask(task.getParentTask());
				taskDto.setGenericstatus(task.getGenericStatus());
				taskDto.setDueDate(task.getDueDate());
				taskDto.setStatus(task.getGenericStatus());
				taskDto.setCreatedBy(task.getCreatedBy());
				taskDtoDetail.add(taskDto);
			}
			taskDetails=taskImpl.getGenericListWithHQLParameter(new String[] {"genericStatus", "createdBy"},new Object[] {ACTIVE, usersSession.getViewId()}, "Task", "taskId asc");
			for (Task task : taskDetails){
				TaskDto taskDto = new TaskDto();
				taskDto.setTaskId(task.getTaskId());
				taskDto.setEditable(false);
				taskDto.setDescription(task.getDescription());
				taskDto.setTaskName(task.getTaskName());
				taskDto.setStartDate(task.getStartDate());
				taskDto.setCreatedDate(task.getCrtdDtTime());
				taskDto.setTask(task.getParentTask());
				taskDto.setGenericstatus(task.getGenericStatus());
				taskDto.setDueDate(task.getDueDate());
				taskDto.setStatus(task.getGenericStatus());
				taskDtoDetails.add(taskDto);
			}
			
		} catch (Exception e) {
			setValid(false);
			JSFMessagers.addErrorMessage(getProvider().getValue("com.server.side.internal.error"));
			LOGGER.info(e.getMessage());
			e.printStackTrace();
		}

	
		
	}

	public String saveTash() {
		try {
			task.setCreatedBy(usersSession.getViewId());
			task.setCrtdDtTime(timestamp);
			task.setGenericStatus(ACTIVE);
			task.setUpDtTime(timestamp);
			task.setUpdatedBy(usersSession.getViewId());
			task.setParentTask(taskImpl.getTaskById(taskID, "taskId"));
			task.setEndDate(task.getDueDate());
			taskImpl.saveTask(task);
			JSFMessagers.resetMessages();
			setValid(true);
			JSFMessagers.addErrorMessage(getProvider().getValue("com.save.form.task"));
			LOGGER.info(CLASSNAME+":::Task Details is saved");
			clearTaskFuileds();
			return"/menu/Task.xhtml?faces-redirect=true";
			
		} catch (Exception e) {
			LOGGER.info(CLASSNAME+":::Task Details is failling with HibernateException  error");
			JSFMessagers.resetMessages();
			setValid(false);
			JSFMessagers.addErrorMessage(getProvider().getValue("com.server.side.internal.error"));
			LOGGER.info(CLASSNAME+""+e.getMessage());
			e.printStackTrace();
			return"";	
		}
		
	}

	public void taskApproval(Task act) {
		try {
			act.setGenericStatus(APPROVED);
			taskImpl.UpdateTask(act);
			// sendEmail(contact.getEmail(), "request rejected",
			// "Your request have been rejected due to certain condition. try again later");
			JSFMessagers.resetMessages();
			setValid(true);
			JSFMessagers.addErrorMessage(getProvider().getValue("com.approve.form"));
			LOGGER.info(CLASSNAME + ":::Task Status is updated");
			clearTaskFuileds();
			} catch (Exception e) {
			LOGGER.info(CLASSNAME+":::Task GenericStatus is failling with HibernateException  error");
			JSFMessagers.resetMessages();
			setValid(false);
			JSFMessagers.addErrorMessage(getProvider().getValue("com.server.side.internal.error"));
			LOGGER.info(e.getMessage());
			e.printStackTrace();
		}
	}

	public void taskRejection(Task act) {
		try {
			act.setGenericStatus(REJECTED);
			taskImpl.UpdateTask(act);
			// sendEmail(contact.getEmail(), "request rejected",
			// "Your request have been rejected due to certain condition. try again later");
			JSFMessagers.resetMessages();
			setValid(true);
			JSFMessagers.addErrorMessage(getProvider().getValue("com.reject.form"));
			LOGGER.info(CLASSNAME + ":::Task GenericStatus is updated");
			clearTaskFuileds();
		} catch (Exception e) {
			setValid(false);
			JSFMessagers.addErrorMessage(getProvider().getValue("com.server.side.internal.error"));
			LOGGER.info(e.getMessage());
			e.printStackTrace();
		}
	}
private void clearTaskFuileds() {
	task=new Task();
	taskDetails=null;
}

public String newTask() {
	return "/menu/InsertTask.xhtml?faces-redirect=true";
}

	public void changeSelectBox(String name) {
		
		LOGGER.info("Ajax is working with listener::::::"+name);
	}

	public String saveAction(TaskDto task) {
		LOGGER.info("update  saveAction method");
		//get all existing value but set "editable" to false 
		Task act=new Task();
		act=new Task();
		act=taskImpl.getTaskById(task.getTaskId(), "taskId");
		
			LOGGER.info("here update sart for "+act +" taskiD "+act.getTaskId());

			task.setEditable(false);
			act.setDescription(task.getDescription());
			act.setTaskName(task.getTaskName());
		
			taskImpl.UpdateTask(act);
			
		//return to current page
		return null;
		
	}

	public String cancel(TaskDto task) {
		task.setEditable(false);
		//usersImpl.UpdateUsers(user);
		return null;
		
		
	}

	public String editAction(TaskDto task) {
	    
		task.setEditable(true);
		//usersImpl.UpdateUsers(user);
		return null;
	}

	public String getCLASSNAME() {
		return CLASSNAME;
	}

	public void setCLASSNAME(String cLASSNAME) {
		CLASSNAME = cLASSNAME;
	}

	public boolean isValid() {
		return isValid;
	}

	public void setValid(boolean isValid) {
		this.isValid = isValid;
	}

	public int getTaskID() {
		return taskID;
	}

	public void setTaskID(int taskID) {
		this.taskID = taskID;
	}

	public JSFBoundleProvider getProvider() {
		return provider;
	}

	public void setProvider(JSFBoundleProvider provider) {
		this.provider = provider;
	}

	public Task getTask() {
		return task;
	}

	public void setTask(Task task) {
		this.task = task;
	}

	public List<Task> getTaskDetails() {
		return taskDetails;
	}

	public void setTaskDetails(List<Task> taskDetails) {
		this.taskDetails = taskDetails;
	}

	public TaskImpl getTaskImpl() {
		return taskImpl;
	}

	public void setTaskImpl(TaskImpl taskImpl) {
		this.taskImpl = taskImpl;
	}

	public List<TaskDto> getTaskDtoDetails() {
		return taskDtoDetails;
	}

	public void setTaskDtoDetails(List<TaskDto> taskDtoDetails) {
		this.taskDtoDetails = taskDtoDetails;
	}

	public List<Task> getTaskDetail() {
		return taskDetail;
	}

	public void setTaskDetail(List<Task> taskDetail) {
		this.taskDetail = taskDetail;
	}

	public List<TaskDto> getTaskDtoDetail() {
		return taskDtoDetail;
	}

	public void setTaskDtoDetail(List<TaskDto> taskDtoDetail) {
		this.taskDtoDetail = taskDtoDetail;
	}


}
