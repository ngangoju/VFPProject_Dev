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
import tres.dao.impl.TaskAssignmentImpl;
import tres.dao.impl.TaskImpl;
import tres.dao.impl.UserCategoryImpl;
import tres.dao.impl.UserImpl;
import tres.domain.Activity;
import tres.domain.StrategicPlan;
import tres.domain.Task;
import tres.domain.TaskAssignment;
import tres.domain.Users;
import tres.vfp.dto.TaskDto;

@ManagedBean
@ViewScoped
public class TaskController implements Serializable, DbConstant {
	private static final Logger LOGGER = Logger.getLogger(Thread.currentThread().getStackTrace()[0].getClassName());
	private String CLASSNAME = "TaskController :: ";
	private static final long serialVersionUID = 1L;
	/* to manage validation messages */
	private boolean isValid;
	private int taskID;
	private int userId;
	/* end manage validation messages */
	private Users users;
	private Users usersSession;
	private Task task;
	private TaskAssignment assignment;
	private StrategicPlan plan;
	private List<StrategicPlan> planDetails = new ArrayList<StrategicPlan>();
	private List<TaskAssignment> taskAssignDetails = new ArrayList<TaskAssignment>();
	private List<Task> taskDetails = new ArrayList<Task>();
	private List<Task> taskDetail = new ArrayList<Task>();
	private List<TaskDto> taskDtoDetails = new ArrayList<TaskDto>();
	private List<TaskDto> taskDtoDetail = new ArrayList<TaskDto>();
	private List<Users> userDetails = new ArrayList<Users>();
	private int listSize;
	private int assignmentSize;
	private boolean renderTable;
	private boolean rendered = true;
	private boolean renderTaskForm;

	/* class injection */

	JSFBoundleProvider provider = new JSFBoundleProvider();
	UserImpl usersImpl = new UserImpl();
	TaskImpl taskImpl = new TaskImpl();
	StrategicPlanImpl planImpl = new StrategicPlanImpl();
	TaskAssignmentImpl taskAssignImpl = new TaskAssignmentImpl();
	UserCategoryImpl categoryImpl = new UserCategoryImpl();

	/* end class injection */
	Timestamp timestamp = new Timestamp(Calendar.getInstance().getTime().getTime());

	@SuppressWarnings("unchecked")
	@PostConstruct
	public void init() {
		HttpSession session = SessionUtils.getSession();
		usersSession = (Users) session.getAttribute("userSession");

		if (users == null) {
			users = new Users();
		}

		if (task == null) {
			task = new Task();
		}
		if (assignment == null) {
			assignment = new TaskAssignment();
		}

		try {
			userDetails = usersImpl.getGenericListWithHQLParameter(new String[] { "genericStatus", "userCategory" },
					new Object[] { ACTIVE, categoryImpl.getUserCategoryById(2, "userCatid") }, "Users", "userId asc");
			taskAssignDetails = taskAssignImpl.getGenericListWithHQLParameter(new String[] { "createdBy" },
					new Object[] { usersSession.getFname() + " " + usersSession.getLname() }, "TaskAssignment",
					"taskAssignmentId asc");
			taskDetail = taskImpl.getListWithHQL(SELECT_TASK);
			taskDetails = taskImpl.getGenericListWithHQLParameter(new String[] { "genericStatus", "createdBy" },
					new Object[] { ACTIVE, usersSession.getFname() + " " + usersSession.getLname() }, "Task",
					"taskId asc");
			listSize = taskDetails.size();
			assignmentSize = taskAssignDetails.size();
			for (Task task : taskDetails) {
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

	public void saveTash() {
		try {
			task.setCreatedBy(usersSession.getFname() + " " + usersSession.getLname());
			task.setCrtdDtTime(timestamp);
			task.setGenericStatus(ACTIVE);
			task.setUpDtTime(timestamp);
			task.setUpdatedBy(usersSession.getFname() + " " + usersSession.getLname());
			task.setParentTask(taskImpl.getTaskById(taskID, "taskId"));
			task.setEndDate(task.getDueDate());
			plan = planImpl.getModelWithMyHQL(new String[] { "genericStatus" }, new Object[] { ACTIVE },
					SELECT_STRATEGIC_PLAN);
			task.setStrategicPlan(plan);
			taskImpl.saveTask(task);
			JSFMessagers.resetMessages();
			setValid(true);
			JSFMessagers.addErrorMessage(getProvider().getValue("com.save.form.task"));
			LOGGER.info(CLASSNAME + ":::Task Details is saved");
			clearTaskFuileds();
			// return"/menu/Task.xhtml?faces-redirect=true";
			// showTasks();

		} catch (Exception e) {
			LOGGER.info(CLASSNAME + ":::Task Details is failling with HibernateException  error");
			JSFMessagers.resetMessages();
			setValid(false);
			JSFMessagers.addErrorMessage(getProvider().getValue("plan.required.message"));
			LOGGER.info(CLASSNAME + "" + e.getMessage());
			e.printStackTrace();
		}

	}

	public void saveAssign() {
		try {
			assignment.setCreatedBy(usersSession.getFname() + " " + usersSession.getLname());
			LOGGER.info(assignment.getCreatedBy());
			assignment.setCrtdDtTime(timestamp);
			assignment.setGenericStatus(ACTIVE);
			assignment.setUpDtTime(timestamp);
			assignment.setUpdatedBy(usersSession.getFname() + " " + usersSession.getLname());
			assignment.setTask(taskImpl.getTaskById(taskID, "taskId"));
			LOGGER.info(assignment.getTask().getTaskName());
			assignment.setUser(usersImpl.gettUserById(userId, "userId"));
			LOGGER.info(assignment.getUser().getLname());
			taskAssignImpl.saveTaskAssignment(assignment);
			JSFMessagers.resetMessages();
			setValid(true);
			JSFMessagers.addErrorMessage(getProvider().getValue("com.save.form.assignment"));
			LOGGER.info(CLASSNAME + ":::Task Assignment is saved");
			clearTaskFuileds();
			// showAssignments();
			// return"/menu/Task.xhtml?faces-redirect=true";

		} catch (Exception e) {
			LOGGER.info(CLASSNAME + ":::Task Details is failling with HibernateException  error");
			JSFMessagers.resetMessages();
			setValid(false);
			JSFMessagers.addErrorMessage(getProvider().getValue("com.server.side.internal.error"));
			LOGGER.info(CLASSNAME + "" + e.getMessage());
			e.printStackTrace();
			// return"";
		}

	}

	public void taskApproval(Task act) {
		try {
			act.setGenericStatus(ACTIVE);
			taskImpl.UpdateTask(act);
			// sendEmail(contact.getEmail(), "request rejected",
			// "Your request have been rejected due to certain condition. try again later");
			JSFMessagers.resetMessages();
			setValid(true);
			JSFMessagers.addErrorMessage(getProvider().getValue("com.approve.form"));
			LOGGER.info(CLASSNAME + ":::Task Status is updated");
			clearTaskFuileds();
		} catch (Exception e) {
			LOGGER.info(CLASSNAME + ":::Task GenericStatus is failling with HibernateException  error");
			JSFMessagers.resetMessages();
			setValid(false);
			JSFMessagers.addErrorMessage(getProvider().getValue("com.server.side.internal.error"));
			LOGGER.info(e.getMessage());
			e.printStackTrace();
		}
	}

	public void taskRejection(Task act) {
		try {
			act.setGenericStatus(DESACTIVE);
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

	public void showTasks() {
		rendered = false;
		renderTaskForm = false;
		renderTable = true;
	}

	public void showAssignments() {
		renderTaskForm = true;
		renderTable = false;
		rendered = false;
	}

	public void back() {
		renderTaskForm = false;
		renderTable = false;
		rendered = true;
	}

	public String backBtn() {
		return "/menu/Task.xhtml?faces-redirect=true";
		// showAssignments();
	}

	private void clearTaskFuileds() {
		task = new Task();
		taskDetails = null;
	}

	public String newTask() {
		return "/menu/InsertTask.xhtml?faces-redirect=true";
	}

	public String newAssign() {
		return "/menu/TaskAssignment.xhtml?faces-redirect=true";
	}

	public void changeSelectBox(String name) {

		LOGGER.info("Ajax is working with listener::::::" + name);
	}

	public String saveAction(TaskDto task) {
		LOGGER.info("update  saveAction method");
		// get all existing value but set "editable" to false
		Task act = new Task();
		act = new Task();
		act = taskImpl.getTaskById(task.getTaskId(), "taskId");

		LOGGER.info("here update sart for " + act + " taskiD " + act.getTaskId());

		task.setEditable(false);
		act.setDescription(task.getDescription());
		act.setTaskName(task.getTaskName());

		taskImpl.UpdateTask(act);

		// return to current page
		return null;

	}

	public String cancel(TaskDto task) {
		task.setEditable(false);
		// usersImpl.UpdateUsers(user);
		return null;

	}

	public String editAction(TaskDto task) {

		task.setEditable(true);
		// usersImpl.UpdateUsers(user);
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

	public int getUserId() {
		return userId;
	}

	public void setUserId(int userId) {
		this.userId = userId;
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

	public TaskAssignment getAssignment() {
		return assignment;
	}

	public void setAssignment(TaskAssignment assignment) {
		this.assignment = assignment;
	}

	public StrategicPlan getPlan() {
		return plan;
	}

	public void setPlan(StrategicPlan plan) {
		this.plan = plan;
	}

	public List<StrategicPlan> getPlanDetails() {
		return planDetails;
	}

	public void setPlanDetails(List<StrategicPlan> planDetails) {
		this.planDetails = planDetails;
	}

	public List<TaskAssignment> getTaskAssignDetails() {
		return taskAssignDetails;
	}

	public void setTaskAssignDetails(List<TaskAssignment> taskAssignDetails) {
		this.taskAssignDetails = taskAssignDetails;
	}

	public List<Users> getUserDetails() {
		return userDetails;
	}

	public void setUserDetails(List<Users> userDetails) {
		this.userDetails = userDetails;
	}

	public int getListSize() {
		return listSize;
	}

	public void setListSize(int listSize) {
		this.listSize = listSize;
	}

	public int getAssignmentSize() {
		return assignmentSize;
	}

	public void setAssignmentSize(int assignmentSize) {
		this.assignmentSize = assignmentSize;
	}

	public StrategicPlanImpl getPlanImpl() {
		return planImpl;
	}

	public void setPlanImpl(StrategicPlanImpl planImpl) {
		this.planImpl = planImpl;
	}

	public TaskAssignmentImpl getTaskAssignImpl() {
		return taskAssignImpl;
	}

	public void setTaskAssignImpl(TaskAssignmentImpl taskAssignImpl) {
		this.taskAssignImpl = taskAssignImpl;
	}

	public boolean isRenderTable() {
		return renderTable;
	}

	public void setRenderTable(boolean renderTable) {
		this.renderTable = renderTable;
	}

	public boolean isRendered() {
		return rendered;
	}

	public void setRendered(boolean rendered) {
		this.rendered = rendered;
	}

	public boolean isRenderTaskForm() {
		return renderTaskForm;
	}

	public void setRenderTaskForm(boolean renderTaskForm) {
		this.renderTaskForm = renderTaskForm;
	}

}
