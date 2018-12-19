package com.vfp.tres;
import java.io.File;
import java.io.Serializable;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.sql.Timestamp;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.GregorianCalendar;
import java.util.List;
import java.util.UUID;
import java.util.logging.Logger;

import javax.annotation.PostConstruct;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.ViewScoped;
import javax.servlet.http.HttpSession;
import javax.faces.context.FacesContext;
import javax.servlet.ServletContext;
import javax.servlet.http.HttpSession;

import org.apache.commons.io.FilenameUtils;

import com.itextpdf.text.pdf.PdfDocument.Destination;

import tres.common.DbConstant;
import tres.common.JSFBoundleProvider;
import tres.common.JSFMessagers;
import tres.common.SessionUtils;
import tres.dao.impl.ActivityCommentImpl;
import tres.dao.impl.ActivityImpl;
import tres.dao.impl.CommentImpl;
import tres.dao.impl.DocumentsImpl;
import tres.dao.impl.InstitutionEscaletPolicyImpl;
import tres.dao.impl.TaskAssignmentImpl;
import tres.dao.impl.TaskImpl;
import tres.dao.impl.UploadingActivityImpl;
import tres.dao.impl.UserImpl;
import tres.domain.Activity;
import tres.domain.ActivityComment;
import tres.domain.Comment;
import tres.domain.Documents;
import tres.domain.InstitutionRegistrationRequest;
import tres.domain.Board;
import tres.domain.InstitutionEscaletePolicy;
import tres.domain.Task;
import tres.domain.TaskAssignment;
import tres.domain.UploadingActivity;
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
	private Comment comments = new Comment();
	private InstitutionEscaletePolicy iep;
	private List<Activity> activityDetail = new ArrayList<Activity>();
	private List<Activity> activityDetails = new ArrayList<Activity>();
	private List<Activity> completedActDetails = new ArrayList<Activity>();
	private List<Activity> approvedActDetails = new ArrayList<Activity>();
	private List<Users> usersDetail = new ArrayList<Users>();
	private List<TaskAssignment> taskAssignDetails = new ArrayList<TaskAssignment>();
	private List<ActivityDto> activityDtoDetails = new ArrayList<ActivityDto>();
	private List<ActivityDto> approvedActDtoDetails = new ArrayList<ActivityDto>();
	private List<ActivityDto> activityDtoDetail = new ArrayList<ActivityDto>();
	private List<ActivityComment> commentDetail = new ArrayList<ActivityComment>();
	private List<UploadingActivity> uploadingActivityDetails = new ArrayList<UploadingActivity>();
	private ActivityComment actComment = new ActivityComment();
	private ActivityCommentImpl actcommentImpl = new ActivityCommentImpl();
	private CommentImpl commentImpl = new CommentImpl();
	private ActivityDto actDto = new ActivityDto();
  	private Documents document;
	private DocumentsImpl docsImpl= new DocumentsImpl();
	private UploadingActivityImpl uplActImpl= new UploadingActivityImpl();
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
	private String commentDescription;
	private boolean renderUpload;
	private boolean renderTask;
	private boolean completeRender;
	private boolean rejectRender;
	private boolean approveRender;
	private boolean commentRender;
	private String[] status = { APPROVED, PLAN_ACTIVITY, REJECT, DONE, COMPLETED };

	private String[] weight = { SHORT, MEDIUM, LONG };
	private String selectedStatus;
	private String[] type = { NORMAL, MILESTONE };
	/* class injection */

	JSFBoundleProvider provider = new JSFBoundleProvider();
	UserImpl usersImpl = new UserImpl();
	ActivityImpl activityImpl = new ActivityImpl();
	TaskAssignmentImpl taskAssignImpl = new TaskAssignmentImpl();
	InstitutionEscaletPolicyImpl iepImpl = new InstitutionEscaletPolicyImpl();

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

		if (null != actComment) {
			actComment = new ActivityComment();
		}
		if (comments == null) {
			comments = new Comment();
		}
		if (actDto == null) {
			actDto = new ActivityDto();
		}
		if(document==null) {
			document= new Documents();
		}
		try {
			users = usersImpl.getUsersWithQuery(new String[] { "board" }, new Object[] { usersSession.getBoard() },
					" from Users");
			for (Object[] data : usersImpl.reportList(
					"select distinct us.userId, us.fname, us.lname, us.board from Users us,Activity co where co.user=us.userId and us.board='"
							+ usersSession.getBoard().getBoardId() + "'")) {
				Users user = new Users();
				user.setUserId((Integer) data[0]);
				user.setFname(data[1] + "");
				user.setLname(data[2] + "");
				user.setBoard((Board) data[3]);
				usersDetail.add(user);
//				Activity act = new Activity();
//				act.setDescription(data[4] + "");
//				act.setStatus(data[5] + "");
//				act.setWeight(data[6] + "");
//				act.setStartDate((Date) data[7]);
//				act.setDueDate((Date) data[8]);
//				act.setCrtdDtTime( (Timestamp) data[9]);
//				act.setTask((Task) data[10]);
//				activityDetail.add(act);
			}
//			activityDetail = activityImpl.getGenericListWithHQLParameter(
//			new String[] { "genericStatus", "user", "status" }, 
//			new Object[] { ACTIVE, users, PLANNED },
//			"Activity", "activityId asc");

			activityDetails = activityImpl.getGenericListWithHQLParameter(
					new String[] { "genericStatus", "createdBy", "status" },
					new Object[] { ACTIVE, usersSession.getFname() + " " + usersSession.getLname(), NOTSTARTED },
					"Activity", "activityId asc");

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

			for (Activity activity : activityDetails) {
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
				activityDtoDetails.add(activityDto);
			}

			taskAssignDetails = taskAssignImpl.getGenericListWithHQLParameter(new String[] { "genericStatus", "user" },
					new Object[] { ACTIVE, userassigned }, "TaskAssignment", "upDtTime desc");
			this.renderTask = true;
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
			activityDto.setType(activity.getType());
			activityDto.setGenericstatus(activity.getGenericStatus());
			if (activityDto.getStatus().equals(PLAN_ACTIVITY)) {
				activityDto.setAction(false);
				activityDto.setShowPlanedIcon(true);
			} else {
				activityDto.setAction(true);
				activityDto.setShowPlanedIcon(false);
			}
			if (activityDto.getStatus().equals(NOTSTARTED)) {
				activityDto.setPlanAction(false);
				activityDto.setChangeAction(false);
				activityDto.setShowNotStartedIcon(true);
			} else {
				activityDto.setPlanAction(true);
				activityDto.setChangeAction(true);
				activityDto.setShowNotStartedIcon(false);
			}
			if (activityDto.getStatus().equals(APPROVED)) {
				activityDto.setReportAction(false);
				activityDto.setCommmentAction(false);
				activityDto.setDoneAction(false);
			} else {
				activityDto.setReportAction(true);
				activityDto.setCommmentAction(true);
				activityDto.setDoneAction(true);
			}

			if (activityDto.getStatus().equals(REJECT)) {
				activityDto.setReplanAction(false);
				activityDto.setCommmentAction(false);
				activityDto.setEditAction(false);
				activityDto.setShowAction(true);
			} else {
				activityDto.setReplanAction(true);
				activityDto.setCommmentAction(true);
				activityDto.setEditAction(true);
				activityDto.setShowAction(false);
			}
			if (activityDto.getStatus().equals(DONE)||activityDto.getStatus().equals(COMPLETED)) {
				activityDto.setAction(false);
			} else {
				activityDto.setAction(true);
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
			this.renderTask = false;
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
			iep = iepImpl.getModelWithMyHQL(new String[] { "genericStatus", "institution" },
					new Object[] { ACTIVE, usersSession.getBoard().getInstitution() },
					" from InstitutionEscaletePolicy");
			act.setStatus(APPROVED);
			// if(act.getGenericStatus().equals(DESACTIVE))
			act.setGenericStatus(ACTIVE);
			Calendar cal1 = new GregorianCalendar();
			cal1.setTime(new Date());
			cal1.add(Calendar.DATE, 7);
			java.util.Date dDate = cal1.getTime();
			act.setDueDate(dDate);
			act.setStartDate(timestamp);
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
			EvaluationController ec = new EvaluationController();
			act.setStatus(COMPLETED);
			act.setGenericStatus(ACTIVE);
			ec.evaluationMethod(act);
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
			if (act != null) {
				LOGGER.info("IGIKORWA CYITWA: " + act.getDescription());
				act.setStatus(REJECT);
				act.setGenericStatus(ACTIVE);
				activityImpl.UpdateActivity(act);
				// sendEmail(contact.getEmail(), "request rejected",
				// "Your request have been rejected due to certain condition. try again later");
				JSFMessagers.resetMessages();
				setValid(true);
				JSFMessagers.addErrorMessage(getProvider().getValue("com.reject.form"));
				LOGGER.info(CLASSNAME + ":::Activity Status is updated");
				clearActivityFuileds();
			} else {
				LOGGER.info("NTA GIKORWA GIHARI");
			}
		} catch (Exception e) {
			setValid(false);
			JSFMessagers.addErrorMessage(getProvider().getValue("com.server.side.internal.error"));
			LOGGER.info(e.getMessage());
			e.printStackTrace();
		}
	}

	/**
	 * @param user
	 */
	@SuppressWarnings("unchecked")
	public void showTasks(Users user) {
		try {
			users = user;
			activityDetail = activityImpl.getGenericListWithHQLParameter(
					new String[] { "genericStatus", "status", "user" }, new Object[] { ACTIVE, PLAN_ACTIVITY, users },
					"Activity", "ACTIVITY_ID asc");
			LOGGER.info(user.getFname() + " " + user.getLname() + " has planned activities");
			this.rendered = false;
			this.renderTaskForm = false;
			this.renderTable = true;
			this.renderCompleted = false;
			this.approveRender = true;
			this.rejectRender = true;
			this.completeRender = false;
			this.commentRender = true;
		} catch (Exception e) {
			LOGGER.info(e.getMessage());
		}
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

	@SuppressWarnings("unchecked")
	public void changeSelectStatus() {
		try {
			if (selectedStatus != null) {
				if (selectedStatus.equals(APPROVED) || selectedStatus.equals(REJECTED)) {
					this.approveRender = false;
					this.rejectRender = false;
					this.completeRender = false;
					this.commentRender = true;
				} else if (selectedStatus.equals(PLAN_ACTIVITY)) {
					this.approveRender = true;
					this.rejectRender = true;
					this.completeRender = false;
					this.commentRender = true;
				} else if (selectedStatus.equals(DONE)) {
					this.approveRender = false;
					this.rejectRender = false;
					this.completeRender = true;
					this.commentRender = true;
				} else if (selectedStatus.equals(COMPLETED)) {
					this.approveRender = false;
					this.rejectRender = false;
					this.completeRender = false;
					this.commentRender = true;
				}
				activityDetail = activityImpl.getGenericListWithHQLParameter(
						new String[] { "genericStatus", "status", "user" },
						new Object[] { ACTIVE, selectedStatus, users }, "Activity", "ACTIVITY_ID asc");

			} else {
				LOGGER.info("Status is selected: " + selectedStatus);
			}
		} catch (Exception e) {
			LOGGER.info(e.getMessage());
		}

	}

	public String saveAction(ActivityDto activity) {
		LOGGER.info("update  saveAction method");
		// get all existing value but set "editable" to false
		try {
			Activity act = new Activity();
			act = new Activity();
			act = activityImpl.getActivityById(activity.getActivityId(), "activityId");

			LOGGER.info("here update sart for " + act + " activityiD " + act.getActivityId());
			
			act.setDescription(activity.getDescription());
			act.setStatus(activity.getStatus());
			act.setType(activity.getType());
			act.setWeight(activity.getWeight());
			activityImpl.UpdateActivity(act);
			JSFMessagers.resetMessages();
			setValid(true);
			JSFMessagers.addErrorMessage(getProvider().getValue("com.update.form.activity"));
			LOGGER.info(CLASSNAME + ":::Activity Details is saved");
			if(activity.getStatus().equals(NOTSTARTED)) {
				activity.setEditable(false);
				activity.setAction(false);
				activity.setPlanAction(false);
				activity.setChangeAction(false);	
			}else {
				activity.setEditable(false);
				activity.setAction(false);
				activity.setReplanAction(false);
				activity.setCommmentAction(false);
				activity.setEditAction(false);
			}	
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
		HttpSession sessionuser = SessionUtils.getSession();

		if (null != activity) {
			// Session creation to get user info from dataTable row
			sessionuser.setAttribute("activityFiles", activity);
			LOGGER.info("Info Founded are activity:>>>>>>>>>>>>>>>>>>>>>>>:" + activity.getDescription() + "ID:"
					+ activity.getActivityId());
		}
		return "/menu/ActivityFilesUpload.xhtml?faces-redirect=true";
	}

	public ActivityDto saveActivityFiles() {
		HttpSession session = SessionUtils.getSession();
		// Get the values from the session
		actDto = (ActivityDto) session.getAttribute("activityFiles");
		return (actDto);
	}

	public void showUploadPanel() {

		this.renderUpload = true;
	}

	@SuppressWarnings("unchecked")
	public String planAction(ActivityDto activity) {
		try {
			Activity act = new Activity();
			act = new Activity();
			act = activityImpl.getActivityById(activity.getActivityId(), "activityId");

			LOGGER.info("here update sart for " + act + " activityiD " + act.getActivityId());

			/* activity.setEditable(false); */
			if (activity.getStatus().equals(NOTSTARTED)||activity.getStatus().equals(REJECT)) {
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
			if (activity.getStatus().equals(APPROVED)&&activity.getType().equals(MILESTONE)) {
				uploadingActivityDetails = uplActImpl.getGenericListWithHQLParameter(new String[] { "genericStatus","activity" },
						new Object[] { ACTIVE,act }, "UploadingActivity","crtdDtTime desc");
				if(uploadingActivityDetails.size()>0) {
				act.setUpdatedBy(usersSession.getViewId());
				act.setUpDtTime(timestamp);
				act.setStatus(DONE);
				activityImpl.UpdateActivity(act);
				activityDetails = activityImpl.getGenericListWithHQLParameter(
						new String[] { "genericStatus", "task", "user" },
						new Object[] { ACTIVE, activity.getTask(), usersSession }, "Activity", "activityId asc");
				activityDtoDetails = showActivity(activityDetails);
				JSFMessagers.resetMessages();
				setValid(true);
				JSFMessagers.addErrorMessage(getProvider().getValue("com.update.form.activity"));
				LOGGER.info(CLASSNAME + ":::Activity Details is saved");
				}else {
					JSFMessagers.resetMessages();
					setValid(false);
					JSFMessagers.addErrorMessage(getProvider().getValue("com.milestone.upload.activity"));
					LOGGER.info(CLASSNAME + ":::Activity type Details is foundes");
				}
			}else if(activity.getStatus().equals(APPROVED)) {
				act.setUpdatedBy(usersSession.getViewId());
				act.setUpDtTime(timestamp);
				act.setStatus(DONE);
				activityImpl.UpdateActivity(act);
				activityDetails = activityImpl.getGenericListWithHQLParameter(
						new String[] { "genericStatus", "task", "user" },
						new Object[] { ACTIVE, activity.getTask(), usersSession }, "Activity", "activityId asc");
				activityDtoDetails = showActivity(activityDetails);
				JSFMessagers.resetMessages();
				setValid(true);
				JSFMessagers.addErrorMessage(getProvider().getValue("com.update.form.activity"));
				LOGGER.info(CLASSNAME + ":::Activity Details is saved");
			}
			
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
		if(activity.getStatus().equals(NOTSTARTED)) {
			activity.setEditable(false);
			activity.setAction(false);
			activity.setPlanAction(false);
			activity.setChangeAction(false);	
		}else {
			activity.setEditable(false);
			activity.setAction(false);
			activity.setReplanAction(false);
			activity.setCommmentAction(false);
			activity.setEditAction(false);
		}	
		// usersImpl.UpdateUsers(user);
		return null;

	}

	public String replyComment(ActivityComment coment) {
		HttpSession sessionuser = SessionUtils.getSession();
		if (null != coment) {
			sessionuser.setAttribute("commentinfo", coment);
			this.rendercomment = true;
			this.rendered = false;
		}
		return null;
	}

	@SuppressWarnings("unchecked")
	public String saveComment() {
		try {
			Activity activ = new Activity();
			ActivityComment coments = new ActivityComment();
			HttpSession session = SessionUtils.getSession();
			// Get the values from the session
			coments = (ActivityComment) session.getAttribute("commentinfo");
			if (null != coments) {
				activ = activityImpl.getModelWithMyHQL(new String[] { "ACTIVITY_ID" },
						new Object[] { coments.getActivity().getActivityId() }, "from Activity");
			}

			if (null != activ && null != commentDescription) {
				comments.setCreatedBy(usersSession.getViewId());
				comments.setCrtdDtTime(timestamp);
				comments.setDescription(commentDescription);
				comments.setGenericStatus(ACTIVE);
				commentImpl.saveComment(comments);
				actComment.setActivity(coments.getActivity());
				actComment.setComment(comments);
				actComment.setCreatedBy(usersSession.getViewId());
				actComment.setGenericStatus(ACTIVE);
				actComment.setUpdatedBy(usersSession.getViewId());
				actComment.setCrtdDtTime(timestamp);
				actComment.setUpDtTime(timestamp);
				actcommentImpl.saveActivityComment(actComment);
				commentDetail = actcommentImpl.getGenericListWithHQLParameter(
						new String[] { "genericStatus", "activity" }, new Object[] { ACTIVE, activity },
						"ActivityComment", "commentActId asc");
				clearComment();
				JSFMessagers.resetMessages();
				setValid(true);
				JSFMessagers.addErrorMessage(getProvider().getValue("com.server.comment.form.saved"));
			} else {
				JSFMessagers.resetMessages();
				setValid(false);
				JSFMessagers.addErrorMessage(getProvider().getValue("com.server.comment.nullform.fail"));
			}
		} catch (Exception e) {
			JSFMessagers.resetMessages();
			setValid(false);
			JSFMessagers.addErrorMessage(getProvider().getValue("com.server.comment.form.fail"));
		}
		return null;
	}

	public void cancelComment() {
		this.rendercomment = false;
	}

	public void clearComment() {
		comments = new Comment();
		actComment = new ActivityComment();
		this.commentDescription = null;
	}

	@SuppressWarnings("unchecked")
	public String commentAction(ActivityDto activ) {
		try {
			Activity act = new Activity();
			if (null != activ) {

				act = activityImpl.getModelWithMyHQL(new String[] { "ACTIVITY_ID" },
						new Object[] { activ.getActivityId() }, "from Activity");
				commentDetail = actcommentImpl.getGenericListWithHQLParameter(
						new String[] { "genericStatus", "activity" }, new Object[] { ACTIVE, act }, "ActivityComment",
						"commentActId asc");
			}
			if (commentDetail.size() < 0) {
				JSFMessagers.resetMessages();
				setValid(false);
				JSFMessagers.addErrorMessage(getProvider().getValue("com.commentError.form.activity"));
			} else {
				this.renderCompleted = false;
				this.renderCommentTable = true;
				activity = act;
				commentDetail = actcommentImpl.getGenericListWithHQLParameter(
						new String[] { "genericStatus", "activity" }, new Object[] { ACTIVE, act }, "ActivityComment",
						"commentActId asc");
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
			this.renderTask = false;
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
		activity.setChangeAction(true);
		// usersImpl.UpdateUsers(user);
		return null;
	}
	public String editChangeAction(ActivityDto activity) {

		activity.setEditable(true);
		activity.setAction(true);
		activity.setPlanAction(true);
		activity.setCommmentAction(true);
		activity.setReplanAction(true);
		activity.setReportAction(true);
		activity.setDoneAction(true);
		activity.setEditAction(true);
		// usersImpl.UpdateUsers(user);
		return null;
	}
public String deleteFile(UploadingActivity info) {
		try {
			ServletContext ctx = (ServletContext) FacesContext.getCurrentInstance()
		            .getExternalContext().getContext();
		String realPath = ctx.getRealPath("/");
		LOGGER.info("Filse Reals Path::::" + realPath);
			Documents documents = new Documents();
			documents=docsImpl.getModelWithMyHQL(new String[] {"DocId" }, new Object[] { info.getDocuments().getDocId()}, " from Documents");
			
			if(null!=documents) {
				final Path destination = Paths.get(realPath+FILELOCATION + documents.getSysFilename());
				LOGGER.info("Path::" + destination);
				File file = new File(destination.toString());
				uplActImpl.deleteIntable(info);
				docsImpl.deleteIntable(documents);
				LOGGER.info("Delete in db operation done!!!:");
				if(file.delete()){
	    			System.out.println(file.getName() + " is deleted!");
	    			JSFMessagers.resetMessages();
	    			setValid(true);
	    			JSFMessagers.addErrorMessage(getProvider().getValue("com.server.success.files.delete"));
	    		}else{
	    			System.out.println("Delete operation is failed.");
	    			JSFMessagers.resetMessages();
	    			setValid(false);
	    			JSFMessagers.addErrorMessage(getProvider().getValue("com.server.error.files.delete"));
	    		}
	 
			}
			
			
			
		} catch (Exception e) {
			JSFMessagers.resetMessages();
			setValid(false);
			JSFMessagers.addErrorMessage(getProvider().getValue("com.server.side.internal.error"));
		}
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

	public boolean isRejectRender() {
		return rejectRender;
	}

	public void setRejectRender(boolean rejectRender) {
		this.rejectRender = rejectRender;
	}

	public boolean isApproveRender() {
		return approveRender;
	}

	public void setApproveRender(boolean approveRender) {
		this.approveRender = approveRender;
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

	public InstitutionEscaletePolicy getIep() {
		return iep;
	}

	public void setIep(InstitutionEscaletePolicy iep) {
		this.iep = iep;
	}

	public boolean isCompleteRender() {
		return completeRender;
	}

	public void setCompleteRender(boolean completeRender) {
		this.completeRender = completeRender;
	}

	public boolean isCommentRender() {
		return commentRender;
	}

	public void setCommentRender(boolean commentRender) {
		this.commentRender = commentRender;
	}

	public InstitutionEscaletPolicyImpl getIepImpl() {
		return iepImpl;
	}

	public void setIepImpl(InstitutionEscaletPolicyImpl iepImpl) {
		this.iepImpl = iepImpl;
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

	public List<ActivityComment> getCommentDetail() {
		return commentDetail;
	}

	public void setCommentDetail(List<ActivityComment> commentDetail) {
		this.commentDetail = commentDetail;
	}

	public TaskAssignmentImpl getTaskAssignImpl() {
		return taskAssignImpl;
	}

	public void setTaskAssignImpl(TaskAssignmentImpl taskAssignImpl) {
		this.taskAssignImpl = taskAssignImpl;
	}

	public List<Users> getUsersDetail() {
		return usersDetail;
	}

	public void setUsersDetail(List<Users> usersDetail) {
		this.usersDetail = usersDetail;
	}

	public String getSelectedStatus() {
		return selectedStatus;
	}

	public void setSelectedStatus(String selectedStatus) {
		this.selectedStatus = selectedStatus;
	}

	public String getCommentDescription() {
		return commentDescription;
	}

	public void setCommentDescription(String commentDescription) {
		this.commentDescription = commentDescription;
	}

	public Comment getComments() {
		return comments;
	}

	public void setComments(Comment comments) {
		this.comments = comments;
	}

	public CommentImpl getCommentImpl() {
		return commentImpl;
	}

	public void setCommentImpl(CommentImpl commentImpl) {
		this.commentImpl = commentImpl;
	}

	public ActivityDto getActDto() {
		return actDto;
	}

	public void setActDto(ActivityDto actDto) {
		this.actDto = actDto;
	}

	public boolean isRenderUpload() {
		return renderUpload;
	}

	public void setRenderUpload(boolean renderUpload) {
		this.renderUpload = renderUpload;
	}

	public boolean isRenderTask() {
		return renderTask;
	}

	public void setRenderTask(boolean renderTask) {
		this.renderTask = renderTask;
	}

	public String[] getType() {
		return type;
	}

	public void setType(String[] type) {
		this.type = type;
	}

	public Documents getDocument() {
		return document;
	}

	public void setDocument(Documents document) {
		this.document = document;
	}

	public DocumentsImpl getDocsImpl() {
		return docsImpl;
	}

	public void setDocsImpl(DocumentsImpl docsImpl) {
		this.docsImpl = docsImpl;
	}

	public UploadingActivityImpl getUplActImpl() {
		return uplActImpl;
	}

	public void setUplActImpl(UploadingActivityImpl uplActImpl) {
		this.uplActImpl = uplActImpl;
	}

	public List<UploadingActivity> getUploadingActivityDetails() {
		return uploadingActivityDetails;
	}

	public void setUploadingActivityDetails(List<UploadingActivity> uploadingActivityDetails) {
		this.uploadingActivityDetails = uploadingActivityDetails;
	}

}
