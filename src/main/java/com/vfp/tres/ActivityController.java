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
import tres.dao.impl.ActivityImpl;
import tres.dao.impl.TaskAssignmentImpl;
import tres.dao.impl.TaskImpl;
import tres.dao.impl.UserImpl;
import tres.domain.Activity;
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
	private Users usersSession;
	private Activity activity;
	private Task task;
	private TaskAssignment taskAssign;
	private List<Activity> activityDetail = new ArrayList<Activity>();
	private List<Activity> activityDetails = new ArrayList<Activity>();
	private List<TaskAssignment> taskAssignDetails = new ArrayList<TaskAssignment>();
	private List<ActivityDto> activityDtoDetails = new ArrayList<ActivityDto>();
	private List<ActivityDto> activityDtoDetail = new ArrayList<ActivityDto>();

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

		try {
			users = usersImpl.getUsersWithQuery(new String[] { "board" }, new Object[] { usersSession.getBoard() },
					"Users");
			activityDetail = activityImpl.getListWithHQL(SELECT_ACTIVITY);
			activityDetails = activityImpl.getGenericListWithHQLParameter(new String[] { "genericStatus", "createdBy" },
					new Object[] { ACTIVE, usersSession.getFname() + " " + usersSession.getLname() }, "Activity",
					"activityId asc");
			for (Activity activity : activityDetails) {
				ActivityDto activityDto = new ActivityDto();
				activityDto.setActivityId(activity.getActivityId());
				activityDto.setEditable(false);
				activityDto.setDescription(activity.getDescription());
				activityDto.setStatus(activity.getStatus());
				activityDto.setWeight(activity.getWeight());
				activityDto.setCreatedDate(activity.getCrtdDtTime());
				activityDto.setTask(activity.getTask());
				activityDto.setGenericstatus(activity.getGenericStatus());
				activityDtoDetails.add(activityDto);
			}
		} catch (Exception e) {
			setValid(false);
			JSFMessagers.addErrorMessage(getProvider().getValue("com.server.side.internal.error"));
			LOGGER.info(e.getMessage());
			e.printStackTrace();
		}

	}

	public String saveActivite() {
		try {
			activity.setCreatedBy(usersSession.getFname() + " " + usersSession.getLname());
			activity.setCrtdDtTime(timestamp);
			activity.setGenericStatus(ACTIVE);
			activity.setStatus(NOTSTARTED);
			activity.setUpDtTime(timestamp);
			activity.setEndDate(activity.getDueDate());
			activity.setUpdatedBy(usersSession.getFname() + " " + usersSession.getLname());
			activity.setDate(timestamp);
			activity.setUser(usersImpl.gettUserById(usersSession.getUserId(), "userId"));
			taskAssign = taskAssignImpl.getModelWithMyHQL(new String[] { "genericStatus", "user" },
					new Object[] { ACTIVE, usersSession.getUserId() }, "TaskAssignment");
			activity.setTask(taskAssign.getTask());
			activityImpl.saveActivity(activity);
			JSFMessagers.resetMessages();
			setValid(true);
			JSFMessagers.addErrorMessage(getProvider().getValue("com.save.form.activity"));
			LOGGER.info(CLASSNAME + ":::Activity Details is saved");
			clearActivityFuileds();
			return "/menu/Activity.xhtml?faces-redirect=true";

		} catch (Exception e) {
			LOGGER.info(CLASSNAME + ":::Activity Details is failling with HibernateException  error");
			JSFMessagers.resetMessages();
			setValid(false);
			JSFMessagers.addErrorMessage(getProvider().getValue("com.server.side.internal.error"));
			LOGGER.info(CLASSNAME + "" + e.getMessage());
			e.printStackTrace();
			return "";
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
		Activity act = new Activity();
		act = new Activity();
		act = activityImpl.getActivityById(activity.getActivityId(), "activityId");

		LOGGER.info("here update sart for " + act + " activityiD " + act.getActivityId());

		activity.setEditable(false);
		act.setDescription(activity.getDescription());
		act.setStatus(activity.getStatus());

		activityImpl.UpdateActivity(act);

		// return to current page
		return null;

	}

	public String cancel(ActivityDto activity) {
		activity.setEditable(false);
		// usersImpl.UpdateUsers(user);
		return null;

	}

	public String editAction(ActivityDto activity) {

		activity.setEditable(true);
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

}
