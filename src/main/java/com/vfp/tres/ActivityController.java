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
import tres.dao.impl.ActivityCommentImpl;
import tres.dao.impl.ActivityImpl;
import tres.dao.impl.TaskAssignmentImpl;
import tres.dao.impl.TaskImpl;
import tres.dao.impl.UserImpl;
import tres.domain.Activity;
import tres.domain.ActivityComment;
import tres.domain.InstitutionRegistrationRequest;
import tres.domain.Task;
import tres.domain.TaskAssignment;
import tres.domain.Users;
import tres.vfp.dto.ActivityDto;
import tres.vfp.dto.UserDto;

@ManagedBean
@ViewScoped
public class ActivityController implements Serializable, DbConstant {
	private static final Logger LOGGER = Logger.getLogger(Thread.currentThread().getStackTrace()[0].getClassName());
	private String CLASSNAME = "ActivityController :: ";
	private static final long serialVersionUID = 1L;
	/* to manage validation messages */
	private boolean isValid;
	/* end manage validation messages */
	private Users users;
	private Users userassigned;
	private Users usersSession;
	private Activity activity;
	private Task task;
	TaskAssignment taskAssign;
	private List<Activity> activityDetail = new ArrayList<Activity>();
	private List<Activity> activityDetails = new ArrayList<Activity>();
	private List<Activity> completedActDetails = new ArrayList<Activity>();
	private List<Activity> approvedActDetails = new ArrayList<Activity>();
	private List<TaskAssignment> taskAssignDetails = new ArrayList<TaskAssignment>();
	private List<ActivityDto> activityDtoDetails = new ArrayList<ActivityDto>();
	private List<ActivityDto> approvedActDtoDetails = new ArrayList<ActivityDto>();
	private List<ActivityDto> activityDtoDetail = new ArrayList<ActivityDto>();
	private ActivityComment actComment= new ActivityComment();
	private ActivityCommentImpl actcommentImpl= new ActivityCommentImpl();
	private int listSize;
	private int completedSize;
	private int approvedSize;
	private boolean renderTable;
	private boolean rendered = true;
	private boolean renderTaskForm;
	private boolean renderCompleted;
	private boolean backBtn;
	private boolean planBtn;
	private boolean rendercomment;
	private boolean comment;
	private boolean renderCommentTable;
	private String[] status = { NOTSTARTED, APPROVED, REJECT, INPROGRESS, COMPLETED };

	private String[] weight = { SHORT, MEDIUM, LONG };

	/* class injection */

	JSFBoundleProvider provider = new JSFBoundleProvider();
	UserImpl usersImpl = new UserImpl();
	ActivityImpl activityImpl = new ActivityImpl();
	TaskAssignmentImpl taskAssignImpl = new TaskAssignmentImpl();

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

		if (activity == null) {
			activity = new Activity();
		}

		if (null != usersSession) {
			userassigned = usersImpl.gettUserById(usersSession.getUserId(), "userId");
		}
		
		if(null!=actComment) {	
			actComment= new ActivityComment();
		}
		try {
			/*users = usersImpl.getUsersWithQuery(new String[] { "board" }, new Object[] { usersSession.getBoard() },
					" from Users");*/
			activityDetail = activityImpl.getListWithHQL(SELECT_ACTIVITY);

			/*
			 * activityDetails = activityImpl.getGenericListWithHQLParameter( new String[] {
			 * "genericStatus", "createdBy", "status" }, new Object[] { ACTIVE,
			 * usersSession.getFname() + " " + usersSession.getLname(), NOTSTARTED },
			 * "Activity", "activityId asc");
			 */
			approvedActDetails = activityImpl.getGenericListWithHQLParameter(
					new String[] { "genericStatus", "createdBy", "status" },
					new Object[] { ACTIVE, usersSession.getFname() + " " + usersSession.getLname(), APPROVED },
					"Activity", "activityId asc");
			completedActDetails = activityImpl.getGenericListWithHQLParameter(
					new String[] { "genericStatus", "createdBy", "status" },
					new Object[] { DESACTIVE, usersSession.getFname() + " " + usersSession.getLname(), COMPLETED },
					"Activity", "activityId asc");

			listSize = activityDetails.size();
			approvedSize = approvedActDetails.size();
			completedSize = completedActDetails.size();
			/*
			 * for (Activity activity : activityDetails) { ActivityDto activityDto = new
			 * ActivityDto(); activityDto.setActivityId(activity.getActivityId());
			 * activityDto.setEditable(false);
			 * activityDto.setDescription(activity.getDescription());
			 * activityDto.setStatus(activity.getStatus());
			 * activityDto.setWeight(activity.getWeight());
			 * activityDto.setCreatedDate(activity.getCrtdDtTime());
			 * activityDto.setStartDate(activity.getStartDate());
			 * activityDto.setDueDate(activity.getDueDate());
			 * activityDto.setTask(activity.getTask());
			 * activityDto.setGenericstatus(activity.getGenericStatus());
			 * activityDtoDetails.add(activityDto); }
			 */

			taskAssignDetails = taskAssignImpl.getGenericListWithHQLParameter(new String[] { "genericStatus", "user" },
					new Object[] { ACTIVE, userassigned }, "TaskAssignment", "upDtTime desc");
			this.renderTable = true;
		} catch (Exception e) {
			setValid(false);
			JSFMessagers.addErrorMessage(getProvider().getValue("com.server.side.internal.error"));
			LOGGER.info(e.getMessage());
			e.printStackTrace();
		}

	}

	public List<ActivityDto> showActivity(List<Activity> list) {

		List<ActivityDto> ActivityDtoList = new ArrayList<ActivityDto>();
		for (Activity activity : list) {
			ActivityDto activityDto = new ActivityDto();
			activityDto.setActivityId(activity.getActivityId());
			activityDto.setEditable(false);
			activityDto.setDescription(activity.getDescription());
			activityDto.setStatus(activity.getStatus());
			activityDto.setWeight(activity.getWeight());
			activityDto.setCreatedDate(activity.getCrtdDtTime());
			activityDto.setStartDate(activity.getStartDate());
			activityDto.setDueDate(activity.getDueDate());
			activityDto.setTask(activity.getTask());
			activityDto.setGenericstatus(activity.getGenericStatus());
			if (activityDto.getStatus().equals(PLAN_ACTIVITY)) {
				activityDto.setAction(false);
			}else {
				activityDto.setAction(true);	
			}
			if (activityDto.getStatus().equals(NOTSTARTED)) {
				activityDto.setPlanAction(false);
			}else {
				activityDto.setPlanAction(true);	
			}
			if (activityDto.getStatus().equals(APPROVED)) {
				activityDto.setReportAction(false);
				activityDto.setCommmentAction(false);
			}else {
				activityDto.setReportAction(true);
				activityDto.setCommmentAction(true);	
			}
			
			if (activityDto.getStatus().equals(REJECT)) {
				activityDto.setReplanAction(false);
				activityDto.setCommmentAction(false);
			}else {
				activityDto.setReplanAction(true);
				activityDto.setCommmentAction(true);
			}
			if (activityDto.getStatus().equals(DONE)) {	
				activityDto.setDoneAction(false);
			}else {
				activityDto.setDoneAction(true);
			}

			ActivityDtoList.add(activityDto);
		}
		return ActivityDtoList;
	}

	@SuppressWarnings("unchecked")
	public void viewActivity(TaskAssignment info) {
		try {
			taskAssign = info;
			activityDetails = activityImpl.getGenericListWithHQLParameter(
					new String[] { "genericStatus", "task", "user" },
					new Object[] { ACTIVE, info.getTask(), usersSession }, "Activity", "activityId asc");
			activityDtoDetails = showActivity(activityDetails);
			/*
			 * for (Activity activity : activityDetails) { ActivityDto activityDto = new
			 * ActivityDto(); activityDto.setActivityId(activity.getActivityId());
			 * activityDto.setEditable(false);
			 * activityDto.setDescription(activity.getDescription());
			 * activityDto.setStatus(activity.getStatus());
			 * activityDto.setWeight(activity.getWeight());
			 * activityDto.setCreatedDate(activity.getCrtdDtTime());
			 * activityDto.setStartDate(activity.getStartDate());
			 * activityDto.setDueDate(activity.getDueDate());
			 * activityDto.setTask(activity.getTask());
			 * activityDto.setGenericstatus(activity.getGenericStatus());
			 * activityDtoDetails.add(activityDto); }
			 */
			this.renderTable = false;
			this.renderCompleted = true;
			this.backBtn = true;

		} catch (Exception e) {
			setValid(false);
			JSFMessagers.addErrorMessage(getProvider().getValue("com.server.side.internal.error"));
			LOGGER.info(e.getMessage());
			e.printStackTrace();
		}

	}

	public void saveActivite() {
		try {
			activity.setCreatedBy(usersSession.getFname() + " " + usersSession.getLname());
			activity.setCrtdDtTime(timestamp);
			activity.setGenericStatus(ACTIVE);
			activity.setStatus(NOTSTARTED);
			activity.setUpDtTime(timestamp);
			activity.setEndDate(activity.getDueDate());
			activity.setUpdatedBy(usersSession.getFname() + " " + usersSession.getLname());
			activity.setDate(timestamp);
			users = usersImpl.gettUserById(usersSession.getUserId(), "userId");
			activity.setUser(users);

			/*
			 * taskAssign = taskAssignImpl.getModelWithMyHQL(new String[] { "genericStatus",
			 * "user" }, new Object[] { ACTIVE, users }, " from TaskAssignment");
			 */
			// Getting selected task in the view
			taskAssign = showAssignedTask();
			activity.setTask(taskAssign.getTask());
			LOGGER.info("ASSIGNMENT YINJIjwE na " + taskAssign.getCreatedBy());
			activityImpl.saveActivity(activity);
			JSFMessagers.resetMessages();
			setValid(true);
			JSFMessagers.addErrorMessage(getProvider().getValue("com.save.form.activity"));
			LOGGER.info(CLASSNAME + ":::Activity Details is saved");
			clearActivityFuileds();
			// return "/menu/Activity.xhtml?faces-redirect=true";

		} catch (Exception e) {
			LOGGER.info(CLASSNAME + ":::Activity Details is failling with HibernateException  error");
			JSFMessagers.resetMessages();
			setValid(false);
			JSFMessagers.addErrorMessage(getProvider().getValue("com.server.side.internal.error"));
			LOGGER.info(CLASSNAME + "" + e.getMessage());
			e.printStackTrace();
			// return "";
		}

	}

	public void activityApproval(Activity act) {
		try {
			act.setStatus(APPROVED);
			// if(act.getGenericStatus().equals(DESACTIVE))
			act.setGenericStatus(ACTIVE);
			activityImpl.UpdateActivity(act);
			// sendEmail(contact.getEmail(), "request rejected",
			// "Your request have been rejected due to certain condition. try again later");
			JSFMessagers.resetMessages();
			setValid(true);
			JSFMessagers.addErrorMessage(getProvider().getValue("com.approve.form"));
			LOGGER.info(CLASSNAME + ":::Activity Status is updated");
			clearActivityFuileds();
		} catch (Exception e) {
			LOGGER.info(CLASSNAME + ":::Activity Status is failling with HibernateException  error");
			JSFMessagers.resetMessages();
			setValid(false);
			JSFMessagers.addErrorMessage(getProvider().getValue("com.server.side.internal.error"));
			LOGGER.info(e.getMessage());
			e.printStackTrace();
		}
	}

	public void completeAction(Activity act) {
		try {
			act.setStatus(COMPLETED);
			// if(act.getGenericStatus().equals(DESACTIVE))
			act.setGenericStatus(DESACTIVE);
			activityImpl.UpdateActivity(act);
			// sendEmail(contact.getEmail(), "request rejected",
			// "Your request have been rejected due to certain condition. try again later");
			JSFMessagers.resetMessages();
			setValid(true);
			JSFMessagers.addErrorMessage(getProvider().getValue("completed.form.message"));
			LOGGER.info(CLASSNAME + ":::Activity is completed");
			clearActivityFuileds();
		} catch (Exception e) {
			LOGGER.info(CLASSNAME + ":::Activity Status is failling with HibernateException  error");
			JSFMessagers.resetMessages();
			setValid(false);
			JSFMessagers.addErrorMessage(getProvider().getValue("com.server.side.internal.error"));
			LOGGER.info(e.getMessage());
			e.printStackTrace();
		}
	}

	public void activityRejection(Activity act) {
		try {
			act.setStatus(REJECTED);
			act.setGenericStatus(DESACTIVE);
			activityImpl.UpdateActivity(act);
			// sendEmail(contact.getEmail(), "request rejected",
			// "Your request have been rejected due to certain condition. try again later");
			JSFMessagers.resetMessages();
			setValid(true);
			JSFMessagers.addErrorMessage(getProvider().getValue("com.reject.form"));
			LOGGER.info(CLASSNAME + ":::Activity Status is updated");
			clearActivityFuileds();
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
		renderCompleted = false;
	}

	public void showAssignments() {
		renderTaskForm = true;
		renderTable = false;
		rendered = false;
		renderCompleted = false;
	}

	public void back() {
		renderTaskForm = false;
		renderTable = false;
		rendered = true;
		renderCompleted = false;
	}

	public void showCompleted() {
		renderCompleted = true;
		renderTaskForm = false;
		renderTable = false;
		rendered = false;
	}

	private void clearActivityFuileds() {
		activity = new Activity();
		activityDetails = null;
	}

	public String newActivity() {
		return "/menu/InsertActivity.xhtml?faces-redirect=true";
	}

	public void changeSelectBox(String name) {

		LOGGER.info("Ajax is working with listener::::::" + name);
	}

	public String saveAction(ActivityDto activity) {
		LOGGER.info("update  saveAction method");
		// get all existing value but set "editable" to false
		try {
			Activity act = new Activity();
			act = new Activity();
			act = activityImpl.getActivityById(activity.getActivityId(), "activityId");

			LOGGER.info("here update sart for " + act + " activityiD " + act.getActivityId());
			activity.setEditable(false);
			activity.setAction(false);
			activity.setPlanAction(false);
			activity.setCommmentAction(false);
			activity.setReplanAction(false);
			activity.setReportAction(false);
			activity.setDoneAction(false);	
			act.setDescription(activity.getDescription());
			act.setStatus(activity.getStatus());
			act.setWeight(activity.getWeight());
			activityImpl.UpdateActivity(act);
			JSFMessagers.resetMessages();
			setValid(true);
			JSFMessagers.addErrorMessage(getProvider().getValue("com.update.form.activity"));
			LOGGER.info(CLASSNAME + ":::Activity Details is saved");
		} catch (Exception e) {
			JSFMessagers.resetMessages();
			setValid(false);
			JSFMessagers.addErrorMessage(getProvider().getValue("com.updatefail.form.activity"));
			LOGGER.info(CLASSNAME + ":::Activity Details is saved");
		}

		// return to current page
		return null;

	}

	public String uploadAction(ActivityDto activity) {
		
		return null;
	}

	@SuppressWarnings("unchecked")
	public String planAction(ActivityDto activity) {
		try {
			Activity act = new Activity();
			act = new Activity();
			act = activityImpl.getActivityById(activity.getActivityId(), "activityId");

			LOGGER.info("here update sart for " + act + " activityiD " + act.getActivityId());

			/* activity.setEditable(false); */
			if (activity.getStatus().equals(NOTSTARTED)) {
				act.setUpdatedBy(usersSession.getViewId());
				act.setUpDtTime(timestamp);
				act.setStatus(PLAN_ACTIVITY);
			}
			activityImpl.UpdateActivity(act);
			activityDetails = activityImpl.getGenericListWithHQLParameter(
					new String[] { "genericStatus", "task", "user" },
					new Object[] { ACTIVE, activity.getTask(), usersSession }, "Activity", "activityId asc");
			activityDtoDetails = showActivity(activityDetails);
			JSFMessagers.resetMessages();
			setValid(true);
			JSFMessagers.addErrorMessage(getProvider().getValue("com.update.form.activity"));
			LOGGER.info(CLASSNAME + ":::Activity Details is saved");
		} catch (Exception e) {
			JSFMessagers.resetMessages();
			setValid(false);
			JSFMessagers.addErrorMessage(getProvider().getValue("com.updatefail.form.activity"));
			LOGGER.info(CLASSNAME + ":::Activity Details is saved");
		}

		// return to current page
		return null;
	}

	@SuppressWarnings("unchecked")
	public String reportAction(ActivityDto activity) {
		try {
			Activity act = new Activity();
			act = new Activity();
			act = activityImpl.getActivityById(activity.getActivityId(), "activityId");

			LOGGER.info("here update sart for " + act + " activityiD " + act.getActivityId());

			/* activity.setEditable(false); */
			if (activity.getStatus().equals(APPROVED)) {
				act.setUpdatedBy(usersSession.getViewId());
				act.setUpDtTime(timestamp);
				act.setStatus(DONE);
			}
			activityImpl.UpdateActivity(act);
			activityDetails = activityImpl.getGenericListWithHQLParameter(
					new String[] { "genericStatus", "task", "user" },
					new Object[] { ACTIVE, activity.getTask(), usersSession }, "Activity", "activityId asc");
			activityDtoDetails = showActivity(activityDetails);
			JSFMessagers.resetMessages();
			setValid(true);
			JSFMessagers.addErrorMessage(getProvider().getValue("com.update.form.activity"));
			LOGGER.info(CLASSNAME + ":::Activity Details is saved");
		} catch (Exception e) {
			JSFMessagers.resetMessages();
			setValid(false);
			JSFMessagers.addErrorMessage(getProvider().getValue("com.updatefail.form.activity"));
			LOGGER.info(CLASSNAME + ":::Activity Details is saved");
		}

		// return to current page
		return null;
	}

	public String cancel(ActivityDto activity) {
		activity.setEditable(false);
		activity.setAction(false);
		activity.setPlanAction(false);
		activity.setCommmentAction(false);
		activity.setReplanAction(false);
		activity.setReportAction(false);
		activity.setDoneAction(false);
		// usersImpl.UpdateUsers(user);
		return null;

	}

	public String replyComment() {

		return null;
	}

	public String saveComment() {
		return null;
	}

	public String commentAction(ActivityDto activity) {
		try {
			if(null!=activity) {
				
				Activity act= new Activity();
				act=activityImpl.getModelWithMyHQL(new String[] {"ACTIVITY_ID"}, new Object[] { activity.getActivityId()},
						"from Activity");
				actComment=actcommentImpl.getModelWithMyHQL(new String[] {"activity"}, new Object[] { act},
				"from ActivityComment");
			}
			
			if(null==actComment) {
			JSFMessagers.resetMessages();
			setValid(false);
			JSFMessagers.addErrorMessage(getProvider().getValue("com.commentError.form.activity"));
			}else {
				this.renderCompleted=false;
				this.renderCommentTable=true;
			}
			
		} catch (Exception e) {
			JSFMessagers.resetMessages();
			setValid(false);
			JSFMessagers.addErrorMessage(getProvider().getValue("com.server.side.internal.error"));
		}
		return null;
	}

	public String backBtn() {
		return "/menu/Activity.xhtml?faces-redirect=true";
		// showAssignments();
	}

	public void createActivity(TaskAssignment info) {
		HttpSession sessionuser = SessionUtils.getSession();
		if (null != info) {
			sessionuser.setAttribute("taskinfo", info);
			LOGGER.info("Info Founded are TaskAssId:>>>>>>>>>>>>>>>>>>>>>>>:" + info.getTaskAssignmentId()
					+ "taskname:::" + info.getTask().getTaskName() + "Task desc::" + info.getTask().getDescription()
					+ "USERINFO::" + info.getUser().getUserId());
			taskAssign = showAssignedTask();
			this.renderTable = false;
			this.renderTaskForm = true;
			this.rendered = false;
			this.backBtn = true;
		}
	}

	public TaskAssignment showAssignedTask() {
		HttpSession session = SessionUtils.getSession();
		// Get the values from the session
		taskAssign = (TaskAssignment) session.getAttribute("taskinfo");
		return taskAssign;
	}

	public String editAction(ActivityDto activity) {

		activity.setEditable(true);
		activity.setAction(true);
		activity.setPlanAction(true);
		activity.setCommmentAction(true);
		activity.setReplanAction(true);
		activity.setReportAction(true);
		activity.setDoneAction(true);
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

	public JSFBoundleProvider getProvider() {
		return provider;
	}

	public void setProvider(JSFBoundleProvider provider) {
		this.provider = provider;
	}

	public Activity getActivity() {
		return activity;
	}

	public void setActivity(Activity activity) {
		this.activity = activity;
	}

	public List<Activity> getActivityDetails() {
		return activityDetails;
	}

	public void setActivityDetails(List<Activity> activityDetails) {
		this.activityDetails = activityDetails;
	}

	public ActivityImpl getActivityImpl() {
		return activityImpl;
	}

	public void setActivityImpl(ActivityImpl activityImpl) {
		this.activityImpl = activityImpl;
	}

	public String[] getStatus() {
		return status;
	}

	public void setStatus(String[] status) {
		this.status = status;
	}

	public String[] getWeight() {
		return weight;
	}

	public void setWeight(String[] weight) {
		this.weight = weight;
	}

	public List<Activity> getCompletedActDetails() {
		return completedActDetails;
	}

	public void setCompletedActDetails(List<Activity> completedActDetails) {
		this.completedActDetails = completedActDetails;
	}

	public List<Activity> getApprovedActDetails() {
		return approvedActDetails;
	}

	public void setApprovedActDetails(List<Activity> approvedActDetails) {
		this.approvedActDetails = approvedActDetails;
	}

	public List<Activity> getActivityDetail() {
		return activityDetail;
	}

	public void setActivityDetail(List<Activity> activityDetail) {
		this.activityDetail = activityDetail;
	}

	public List<ActivityDto> getActivityDtoDetails() {
		return activityDtoDetails;
	}

	public void setActivityDtoDetails(List<ActivityDto> activityDtoDetails) {
		this.activityDtoDetails = activityDtoDetails;
	}

	public List<ActivityDto> getActivityDtoDetail() {
		return activityDtoDetail;
	}

	public void setActivityDtoDetail(List<ActivityDto> activityDtoDetail) {
		this.activityDtoDetail = activityDtoDetail;
	}

	public Task getTask() {
		return task;
	}

	public void setTask(Task task) {
		this.task = task;
	}

	public TaskAssignment getTaskAssign() {
		return taskAssign;
	}

	public void setTaskAssign(TaskAssignment taskAssign) {
		this.taskAssign = taskAssign;
	}

	public List<TaskAssignment> getTaskAssignDetails() {
		return taskAssignDetails;
	}

	public void setTaskAssignDetails(List<TaskAssignment> taskAssignDetails) {
		this.taskAssignDetails = taskAssignDetails;
	}

	public int getListSize() {
		return listSize;
	}

	public void setListSize(int listSize) {
		this.listSize = listSize;
	}

	public boolean isRenderTable() {
		return renderTable;
	}

	public void setRenderTable(boolean renderTable) {
		this.renderTable = renderTable;
	}

	public boolean isRenderCompleted() {
		return renderCompleted;
	}

	public void setRenderCompleted(boolean renderCompleted) {
		this.renderCompleted = renderCompleted;
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

	public int getCompletedSize() {
		return completedSize;
	}

	public void setCompletedSize(int completedSize) {
		this.completedSize = completedSize;
	}

	public int getApprovedSize() {
		return approvedSize;
	}

	public void setApprovedSize(int approvedSize) {
		this.approvedSize = approvedSize;
	}

	public List<ActivityDto> getApprovedActDtoDetails() {
		return approvedActDtoDetails;
	}

	public void setApprovedActDtoDetails(List<ActivityDto> approvedActDtoDetails) {
		this.approvedActDtoDetails = approvedActDtoDetails;
	}

	public Users getUserassigned() {
		return userassigned;
	}

	public void setUserassigned(Users userassigned) {
		this.userassigned = userassigned;
	}

	public boolean isBackBtn() {
		return backBtn;
	}

	public void setBackBtn(boolean backBtn) {
		this.backBtn = backBtn;
	}

	public boolean isPlanBtn() {
		return planBtn;
	}

	public void setPlanBtn(boolean planBtn) {
		this.planBtn = planBtn;
	}

	public Users getUsers() {
		return users;
	}

	public void setUsers(Users users) {
		this.users = users;
	}

	public Users getUsersSession() {
		return usersSession;
	}

	public void setUsersSession(Users usersSession) {
		this.usersSession = usersSession;
	}

	public boolean isRendercomment() {
		return rendercomment;
	}

	public void setRendercomment(boolean rendercomment) {
		this.rendercomment = rendercomment;
	}

	public boolean isComment() {
		return comment;
	}

	public void setComment(boolean comment) {
		this.comment = comment;
	}

	public boolean isRenderCommentTable() {
		return renderCommentTable;
	}

	public void setRenderCommentTable(boolean renderCommentTable) {
		this.renderCommentTable = renderCommentTable;
	}

	public UserImpl getUsersImpl() {
		return usersImpl;
	}

	public void setUsersImpl(UserImpl usersImpl) {
		this.usersImpl = usersImpl;
	}

	public ActivityComment getActComment() {
		return actComment;
	}

	public void setActComment(ActivityComment actComment) {
		this.actComment = actComment;
	}

	public ActivityCommentImpl getActcommentImpl() {
		return actcommentImpl;
	}

	public void setActcommentImpl(ActivityCommentImpl actcommentImpl) {
		this.actcommentImpl = actcommentImpl;
	}

}
