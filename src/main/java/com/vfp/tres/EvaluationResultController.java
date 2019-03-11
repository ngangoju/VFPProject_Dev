package com.vfp.tres;

import java.io.Serializable;
import java.sql.Timestamp;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.List;
import java.util.logging.Logger;

import javax.annotation.PostConstruct;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.SessionScoped;
import javax.faces.context.FacesContext;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;

import tres.common.DbConstant;
import tres.common.JSFBoundleProvider;
import tres.common.JSFMessagers;
import tres.common.SessionUtils;
import tres.common.UploadUtility;
import tres.dao.impl.ActivityImpl;
import tres.dao.impl.ContactImpl;
import tres.dao.impl.EvaluationImpl;
import tres.dao.impl.InstitutionEscaletPolicyImpl;
import tres.dao.impl.InstitutionImpl;
import tres.dao.impl.TaskImpl;
import tres.dao.impl.UploadingActivityImpl;
import tres.dao.impl.UserImpl;
import tres.domain.Activity;
import tres.domain.ActivityEvaluation;
import tres.domain.Board;
import tres.domain.Contact;
import tres.domain.Evaluation;
import tres.domain.Institution;
import tres.domain.InstitutionEscaletePolicy;
import tres.domain.Task;
import tres.domain.UploadingActivity;
import tres.domain.Users;
import tres.vfp.dto.ActivityDto;
import tres.vfp.dto.EvaluationDto;

@ManagedBean
@SessionScoped
public class EvaluationResultController implements Serializable, DbConstant {
	private static final Logger LOGGER = Logger.getLogger(Thread.currentThread().getStackTrace()[0].getClassName());
	private String CLASSNAME = "EvaluationResultController:: ";
	private static final long serialVersionUID = 1L;
	/* to manage validation messages */
	private boolean isValid, viewStaffActiv, viewSpecfcStaff, dwnBtn, apDltBtn, viewAttached,viewactivity, rsbtn,backHome,staffEvaluation;
	/* end manage validation messages */

	private Evaluation evaluation;
	private Activity activity;
	private Users users;
	private InstitutionEscaletePolicy policy;
	private Users usersSession;
	private Institution institution;
	private UploadingActivity uploadingActivity;

	private List<Activity> activities = new ArrayList<Activity>();
	private List<Evaluation> evaluations = new ArrayList<Evaluation>();
	private List<Users> staffs = new ArrayList<Users>();
	private List<UploadingActivity> docmts = new ArrayList<UploadingActivity>();
	private List<Users> staffsComplete = new ArrayList<Users>();
	private List<ActivityDto> activitiesDtos = new ArrayList<ActivityDto>();
	private List<EvaluationDto> evaluationDtos = new ArrayList<EvaluationDto>();
	private List<EvaluationDto> activityAvaluationDtos = new ArrayList<EvaluationDto>();
	private List<EvaluationDto> evaluationStaffDtos = new ArrayList<EvaluationDto>();
	private List<EvaluationDto> activityAvaluationStaffDtos = new ArrayList<EvaluationDto>();
	EvaluationImpl evaluationImpl = new EvaluationImpl();
	ActivityImpl activityImpl = new ActivityImpl();
	InstitutionImpl instImpl = new InstitutionImpl();
	JSFBoundleProvider provider = new JSFBoundleProvider();
	UserImpl usersImpl = new UserImpl();
	Timestamp timestamp = new Timestamp(Calendar.getInstance().getTime().getTime());
	InstitutionEscaletPolicyImpl policyImpl = new InstitutionEscaletPolicyImpl();
	ContactImpl contactImpl = new ContactImpl();
	UploadingActivityImpl upimpl = new UploadingActivityImpl();
	TaskImpl taskImpl = new TaskImpl();
	private Task task;
	@SuppressWarnings("unchecked")
	@PostConstruct
	public void init() {
		HttpSession session = SessionUtils.getSession();
		usersSession = (Users) session.getAttribute("userSession");
		if (evaluation == null) {
			evaluation = new Evaluation();
		}
		if (activity == null) {
			activity = new Activity();
		}
		if (policy == null) {
			policy = new InstitutionEscaletePolicy();
		}
		if (institution == null) {
			institution = new Institution();
		}
		try {
			staffsComplete = getUserDetailsCompletedActivities();
			evaluationDtos=completedEvaByStaff();
			evaluationStaffDtos=ActEvaluatedByStaff();
			LOGGER.info("::::LIST SIZE::::::::"+evaluationDtos.size());
			staffEvaluation=true;
		} catch (Exception e) {
			setValid(false);
			e.printStackTrace();
			JSFMessagers.addErrorMessage(getProvider().getValue("com.server.side.internal.error"));
			LOGGER.info(e.getMessage());
			e.printStackTrace();
		}
	}

	public List<Users> getUserDetails() {
		try {
			staffs = new ArrayList<Users>();
			for (Object[] object : usersImpl.reportList(
					"select distinct us.userId, us.fname, us.lname, us.board from Users us,Activity co where co.user=us.userId and us.board="
							+ usersSession.getBoard().getBoardId() + " and co.status='" + DONE + "'")) {
				Users user = new Users();
				user.setUserId((Integer) object[0]);
				user.setFname(object[1] + "");
				user.setLname(object[2] + "");
				user.setBoard((Board) object[3]);
				staffs.add(user);
			}
			return staffs;
		} catch (Exception e) {
			setValid(false);
			e.printStackTrace();
			JSFMessagers.addErrorMessage(getProvider().getValue("com.server.side.internal.error"));
			LOGGER.info(e.getMessage());
			e.printStackTrace();
			return null;
		}
	}

	// List of staff with completed Activities

	public List<Users> getUserDetailsCompletedActivities() {
		try {
			staffs = new ArrayList<Users>();
			for (Object[] object : usersImpl.reportList(
					"select distinct us.userId, us.fname, us.lname, us.board from Users us,Activity co,ActivityEvaluation ev where co.user=us.userId and ev.activity=co.activityId and us.board="
							+ usersSession.getBoard().getBoardId() + "")) {
				Users user = new Users();
				user.setUserId((Integer) object[0]);
				user.setFname(object[1] + "");
				user.setLname(object[2] + "");
				user.setBoard((Board) object[3]);
				staffs.add(user);
			}
			return staffs;
		} catch (Exception e) {
			setValid(false);
			e.printStackTrace();
			JSFMessagers.addErrorMessage(getProvider().getValue("com.server.side.internal.error"));
			LOGGER.info(e.getMessage());
			e.printStackTrace();
			return null;
		}
	}

	// backtohome
	public void backHome() {
		viewSpecfcStaff = false;
		viewStaffActiv = false;
		viewactivity=false;
		backHome=false;
	}

	// desplay name of user
	public String getBothUserName() {
		try {
			HttpSession session = SessionUtils.getSession();
			Users user1 = new Users();
			user1 = (Users) session.getAttribute("usrSession");
			return user1.getFname() + " " + user1.getLname();
		} catch (Exception e) {
			return "";
		}
	}

	// renderdownload

	public void getDownoadPage(Activity activity) {
		try {
			HttpSession ses = SessionUtils.getSession();
			ses.setAttribute("activitySession", activity);
			LoadUserInformationsController loadUserInformationsController = new LoadUserInformationsController();
			HttpServletRequest request = (HttpServletRequest) FacesContext.getCurrentInstance().getExternalContext()
					.getRequest();
			String url = loadUserInformationsController.getContextPath() + "/menu/activitFiledown.xhtml";
			FacesContext.getCurrentInstance().getExternalContext().redirect(url);
		} catch (Exception e) {
			e.printStackTrace();
			LOGGER.info(e.getMessage());
		}
	}

	// contact

	public String getContactPhone(Users usr) {

		try {
			Contact cnt = new Contact();
			cnt = contactImpl.getModelWithMyHQL(new String[] { "user" }, new Object[] { usr }, "from Contact");
			LOGGER.info("Here we Are" + cnt);
			return cnt.getPhone();
		} catch (Exception e) {
			e.printStackTrace();
			return "";
		}
	}
	// downloading

	// email
	public String getContactEmail(Users usr) {

		try {
			Contact cnt = new Contact();
			cnt = contactImpl.getModelWithMyHQL(new String[] { "user" }, new Object[] { usr }, "from Contact");
			return cnt.getEmail();
		} catch (Exception e) {
			e.printStackTrace();
			return "";
		}
	}

	// list of complete ativities by staff
	@SuppressWarnings("unchecked")
	public void staffCompl(Users user) {
		try {
			viewStaffActiv = true;
			viewSpecfcStaff = true;
			backHome=true;
			evaluationDtos = OverallTargetsEvaluation(user);
			HttpSession ses = SessionUtils.getSession();
			ses.setAttribute("usrSession", user);
			LoadUserInformationsController loadUserInformationsController = new LoadUserInformationsController();
			HttpServletRequest request = (HttpServletRequest) FacesContext.getCurrentInstance().getExternalContext()
					.getRequest();
		} catch (Exception e) {
		}
	}

	// list of complete ativities by staff
	@SuppressWarnings("unchecked")
	public void staffComplAct(Users user) {
		try {
			viewStaffActiv = true;
			viewSpecfcStaff = true;
			activitiesDtos = actDtos(user);
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	/* Evaluation Method For Completed task */

	public boolean isCompleted(Activity activity) {
		try {
			if (activity.getStatus().equals(DONE)) {
				return true;
			} else {
				return false;
			}
		} catch (Exception e) {
			LOGGER.info("DB ERROR:::");
			setValid(false);
			e.printStackTrace();
			JSFMessagers.addErrorMessage(getProvider().getValue("com.server.side.internal.error"));
			LOGGER.info(e.getMessage());
			e.printStackTrace();
			return false;
		}
	}

	/* Evaluation Decision Making */
	@SuppressWarnings("unchecked")
	public String evalutionDecition(Activity activity) {
		String decision = "";
		int cnt = 0;
		institution = activity.getUser().getBoard().getInstitution();
		try {
			policy = policyImpl.getModelWithMyHQL(new String[] { "institution" }, new Object[] { institution },
					"from InstitutionEscaletePolicy");
			for (Object[] data : evaluationImpl
					.reportList("select count(*),i.EavaluationId from Evaluation i where i.activity="
							+ activity.getActivityId() + " and decision='" + RESCHDULER + "'")) {
				cnt = Integer.parseInt(data[0] + "");
			}
			/* Condition for Decision making */
			if (cnt == 0) {
				decision = "Conglatulation";
			} else {
				if (cnt <= policy.getReschduleTime()) {
					decision = RESCHDULER;
				} else if (cnt > policy.getReschduleTime()) {
					decision = ESCALET;
				}
			}
			LOGGER.info("EXEUTE WITH SUCCESS:::" + decision);
			return decision;

		} catch (Exception e) {
			LOGGER.info("DB ERROR:::");
			setValid(false);
			e.printStackTrace();
			JSFMessagers.addErrorMessage(getProvider().getValue("com.server.side.internal.error"));
			LOGGER.info(e.getMessage());
			e.printStackTrace();
			return "";
		}
	}

	/* Saving Evaluation */

	public String savingEvaluationData(ActivityDto act) {
		try {
			activity = new Activity();
			activityImpl.getActivityById(act.getActivityId(), "activityId");
			activity.setStatus(COMPLETED);
			activity.setUpdatedBy(usersSession.getViewId());
			activity.setUpDtTime(timestamp);
			activityImpl.UpdateActivity(activity);
			if (addDay(activity).before(new Date())) {
				evaluation = new Evaluation();
				evaluation.setActivity(activity);
				evaluation.setCreatedBy(usersSession.getFname() + " " + usersSession.getLname());
				evaluation.setCrtdDtTime(timestamp);
				evaluation.setDecision(COMPLETED);
				evaluation.setEvaluationDate(timestamp);
				evaluation.setEvaluationMarks(getMarks(activity));
				evaluation.setGenericStatus(ACTIVE);
				evaluation.setUpdatedBy(usersSession.getViewId());
				evaluation.setUpDtTime(timestamp);
				evaluationImpl.saveEvaluation(evaluation);
				staffs = getUserDetails();// staff with complete activities
				activitiesDtos = actDtos(activity.getUser());
				LOGGER.info("Evaluation information saved:::::");
				setValid(true);
				JSFMessagers.addErrorMessage(getProvider().getValue("Activity completed."));
			} else {
				evaluation = new Evaluation();
				evaluation.setActivity(activity);
				evaluation.setCreatedBy(usersSession.getFname() + " " + usersSession.getLname());
				evaluation.setCrtdDtTime(timestamp);
				evaluation.setDecision(COMPLETED);
				evaluation.setEvaluationDate(timestamp);
				evaluation.setEvaluationMarks(0);
				evaluation.setGenericStatus(ACTIVE);
				evaluation.setUpdatedBy(usersSession.getViewId());
				evaluation.setUpDtTime(timestamp);
				evaluationImpl.saveEvaluation(evaluation);
				staffs = getUserDetails();// staff with complete activities
				activitiesDtos = actDtos(activity.getUser());
				setValid(true);
				JSFMessagers
						.addErrorMessage(getProvider().getValue("Activity completed with Due date rules violetion."));
			}
			return "";
		} catch (Exception e) {
			LOGGER.info("DB ERROR:::");
			setValid(false);
			e.printStackTrace();
			JSFMessagers.addErrorMessage(getProvider().getValue("com.server.side.internal.error"));
			LOGGER.info(e.getMessage());
			e.printStackTrace();
			return "";
		}
	}

	public String reconformActivity(EvaluationDto ev) {
		try {
			activity = new Activity();
			activity = activityImpl.getActivityById(ev.getActivity().getActivityId(), "activityId");
			if (activity != null) {
				if (ev.getDecision().equals(FAILED)) {
					activity.setStatus(COMPLETED);
					activity.setUpdatedBy(usersSession.getViewId());
					activity.setUpDtTime(timestamp);
					activityImpl.UpdateActivity(activity);
					evaluation = evaluationImpl.getModelWithMyHQL(new String[] { "activity", "decision" },
							new Object[] { activity, FAILED }, "from Evaluation");
					evaluation.setEvaluationMarks(getMarks(activity));
					evaluation.setDecision(COMPLETED);
					evaluation.setUpdatedBy(usersSession.getViewId());
					evaluation.setUpDtTime(timestamp);
					evaluationImpl.UpdateEvaluation(evaluation);
					evaluationDtos = completedEva(activity.getUser());
					setValid(true);
					JSFMessagers.addErrorMessage(getProvider().getValue("evaluation.reconfirm.message"));
					return "";
				} else {
					setValid(true);
					JSFMessagers.addErrorMessage(getProvider().getValue("evaluation.reconfirm.message"));
					return "";
				}
			} else {
				setValid(false);
				JSFMessagers.addErrorMessage(getProvider().getValue("com.server.side.internal.error"));
			}
			return "";

		} catch (Exception e) {
			LOGGER.info("DB ERROR:::");
			setValid(false);
			e.printStackTrace();
			JSFMessagers.addErrorMessage(getProvider().getValue("com.server.side.internal.error"));
			LOGGER.info(e.getMessage());
			e.printStackTrace();
			return "";
		}
	}

	/* Add variant days to due date */

	public Date addDay(Activity activity) {
		institution = activity.getUser().getBoard().getInstitution();
		try {
			SimpleDateFormat smf = new SimpleDateFormat("dd-MM-yyyy");
			policy = policyImpl.getModelWithMyHQL(new String[] { "institution", "genericStatus" },
					new Object[] { institution, ACTIVE }, "from InstitutionEscaletePolicy");
			Calendar cal = Calendar.getInstance();
			cal.setTime(activity.getDueDate());
			cal.add(Calendar.DAY_OF_MONTH, policy.getVariation());
			String dt = smf.format(cal.getTime());
			LOGGER.info("Due date is ::" + cal.getTime());
			return smf.parse(dt);
		} catch (Exception e) {
			LOGGER.info("Due date Failed");
			return null;
		}
	}
	
	// returning files forms

	@SuppressWarnings("unchecked")
	public List<UploadingActivity> showFiles() {
		try {
			List<UploadingActivity> doc1 = new ArrayList<UploadingActivity>();
			Activity activ = new Activity();
			HttpSession ses = SessionUtils.getSession();
			activ = (Activity) ses.getAttribute("activitySession");
			if (activ != null) {
				doc1 = upimpl.getGenericListWithHQLParameter(new String[] { "activity" }, new Object[] { activ },
						"UploadingActivity", "activity desc");
				return doc1;
			} else {
				return null;
			}
		} catch (Exception e) {
			e.printStackTrace();
			return null;
		}
	}
	// download

	public void downloadFile() {
		UploadUtility ut = new UploadUtility();
		try {
			ut.downloadFileUtil();
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}

	// backToPrevousPage
	public void backPrsPage() {
		viewAttached = false;
		viewSpecfcStaff = true;
	}

	/* Activity completion starts */
	public void completeAction(Activity act) {
		try {
			/*
			 * act.setStatus(COMPLETED); act.setGenericStatus(ACTIVE);
			 * activityImpl.UpdateActivity(act);
			 */
			// evaluationMethod(act);
			// sendEmail(contact.getEmail(), "request rejected",
			// "Your request have been rejected due to certain condition. try again later");
			staffCompl(act.getUser());
			JSFMessagers.resetMessages();
			setValid(true);
			JSFMessagers.addErrorMessage(getProvider().getValue("completed.form.message"));
			LOGGER.info(CLASSNAME + ":::Activity is completed");
		} catch (Exception e) {
			LOGGER.info(CLASSNAME + ":::Activity Status is failling with HibernateException  error");
			JSFMessagers.resetMessages();
			setValid(false);
			JSFMessagers.addErrorMessage(getProvider().getValue("com.server.side.internal.error"));
			LOGGER.info(e.getMessage());
			e.printStackTrace();
		}
	}

	/* activity completion ends */
	/* activity rejection starts */
	public void rejectAction(Activity act) {
		try {
			LOGGER.info("IGIKORWA CYITWA: " + act.getDescription());
			act.setUpdatedBy(usersSession.getViewId());
			act.setUpDtTime(timestamp);
			act.setStatus(FAILED);
			act.setDueDate(act.getDueDate());
			act.setStartDate(act.getStartDate());
			act.setEndDate(act.getEndDate());
			act.setGenericStatus(ACTIVE);
			act.setCountActivityFailed(act.getCountActivityFailed() + incrementCount);
			activityImpl.UpdateActivity(act);
			evaluation = new Evaluation();
			evaluation.setActivity(act);
			evaluation.setCreatedBy(usersSession.getFname() + " " + usersSession.getLname());
			evaluation.setCrtdDtTime(timestamp);
			evaluation.setDecision(FAILED);
			evaluation.setEvaluationDate(timestamp);
			evaluation.setEvaluationMarks(0);
			evaluation.setGenericStatus(ACTIVE);
			evaluationImpl.saveEvaluation(evaluation);
			JSFMessagers.resetMessages();
			setValid(true);
			staffCompl(act.getUser());
			JSFMessagers.addErrorMessage(getProvider().getValue("com.reject.form"));
			LOGGER.info(CLASSNAME + ":::Activity Status is updated");
			LOGGER.info(CLASSNAME + ":::Activity is completed");
		} catch (Exception e) {
			LOGGER.info(CLASSNAME + ":::Activity Status is failling with HibernateException  error");
			JSFMessagers.resetMessages();
			setValid(false);
			JSFMessagers.addErrorMessage(getProvider().getValue("com.server.side.internal.error"));
			LOGGER.info(e.getMessage());
			e.printStackTrace();
		}
	}

	/* activity rejection ends */
	public int getMarks(Activity act) {
		try {
			institution = act.getUser().getBoard().getInstitution();
			policy = policyImpl.getModelWithMyHQL(new String[] { "institution", "genericStatus" },
					new Object[] { institution, ACTIVE }, "from InstitutionEscaletePolicy");
			if (act.getWeight().equals(LONG))
				return (int) policy.getLongMarks();
			else if ((act.getWeight().equals(MEDIUM)))
				return (int) policy.getMediumgMarks();
			else {
				return (int) policy.getShortMarks();
			}
		} catch (Exception e) {
			e.printStackTrace();
			return 0;
		}
	}

	/* activity rejection ends */
	public int getMarksOfEvaluation(ActivityDto act) {
		try {
			activity = new Activity();
			activity = activityImpl.getActivityById(act.getActivityId(), "activityId");
			evaluation = new Evaluation();
			evaluation = evaluationImpl.getModelWithMyHQL(new String[] { "activity", "genericStatus", "decision" },
					new Object[] { activity, ACTIVE, COMPLETED }, "from Evaluation");
			return evaluation.getEvaluationMarks();
		} catch (Exception e) {
			return 0;
		}
	}

	public String getStatusOfEvaluation(Activity act) {
		try {
			evaluation = new Evaluation();
			evaluation = evaluationImpl.getModelWithMyHQL(new String[] { "activity", "genericStatus", "decision" },
					new Object[] { act, ACTIVE, COMPLETED }, "from Evaluation");
			return evaluation.getDecision();
		} catch (Exception e) {
			return "";
		}
	}

	public String rendeButton(Activity act) {
		try {
			if (act.getStatus().equals(COMPLETED)) {
				return "rsbtn";
			} else {
				return "not rsbtn";
			}
		} catch (Exception e) {
			return "rsbtn";
		}
	}

	@SuppressWarnings("unchecked")
	public List<ActivityDto> actDtos(Users user) {
		try {
			activitiesDtos = new ArrayList<ActivityDto>();
			activities = new ArrayList<Activity>();
			activities = activityImpl
					.getListWithHQL("select f from Activity f where f.genericStatus='" + ACTIVE + "' and f.user="
							+ user.getUserId() + " and f.status='" + COMPLETED + "' or f.status='" + FAILED + "'  ");
			for (Activity dto : activities) {
				ActivityDto dt1 = new ActivityDto();
				if (dto.getStatus().equals(COMPLETED)) {
					dt1.setFailedEvButton(false);
				} else if (dto.getStatus().equals(FAILED)) {
					dt1.setFailedEvButton(true);
				} else {
					dt1.setFailedEvButton(true);
				}
				dt1.setActivityId(dto.getActivityId());
				dt1.setEndDate(dto.getEndDate());
				dt1.setDate(dto.getDate());
				dt1.setCreatedDate(dto.getCrtdDtTime());
				dt1.setDescription(dto.getDescription());
				dt1.setWeight(dto.getWeight());
				dt1.setType(dto.getType());
				dt1.setGenericstatus(dto.getGenericStatus());
				dt1.setDueDate(dto.getDueDate());
				dt1.setTask(dto.getTask());
				dt1.setStatus(dto.getStatus());
				dt1.setUser(user);
				activitiesDtos.add(dt1);
			}
			return activitiesDtos;
		} catch (Exception e) {
			e.printStackTrace();
			return null;
		}
	}

	@SuppressWarnings("unchecked")
	public List<EvaluationDto> completedEva(Users usr) {
		try {
			evaluationDtos = new ArrayList<EvaluationDto>();
			String chk;
			for (Object[] object : evaluationImpl.reportList(
					"select  us.evaluationId,us.decision,us.EvaluationMarks,us.activity,us.supervisor from Evaluation us,Activity co where us.activity=co.activityId and co.user="
							+ usr.getUserId() + "")) {
				EvaluationDto dto = new EvaluationDto();
				chk = "";
				dto.setEvaluationId(Integer.parseInt(object[0] + ""));
				dto.setDecision(object[1] + "");
				chk = object[1] + "";
				if (chk.equals(COMPLETED)) {
					dto.setFailedBtn(false);
				} else if (chk.equals(FAILED)) {
					dto.setFailedBtn(true);
				} else {
					dto.setFailedBtn(true);
				}
				dto.setEvaluationMarks(Integer.parseInt(object[2] + ""));
				dto.setActivity((Activity) object[3]);
				dto.setUser((Users) object[4]);
				evaluationDtos.add(dto);
			}
			return evaluationDtos;
		} catch (Exception e) {
			e.printStackTrace();
			return null;
		}
	}

	
	@SuppressWarnings("unchecked")
	public List<EvaluationDto> OverallTargetsEvaluation(Users usr) {
		try {
			evaluationDtos = new ArrayList<EvaluationDto>();
			String chk;
			for (Object[] object : evaluationImpl.reportList(
					"select ev.evaluationId,count(ac.activityId) as TotalEvaluatedActivity,Sum(ev.EvaluationMarks) as TotalEvaluationMarks,Sum(ev.EvaluationOverAllMarks) as TotalExpectedMarks,sum(case when (ev.decision='Failed') then 1 else 0 end) as TotalFailed,sum(case when (ev.decision='Completed') then 1 else 0 end) as TotalCompleted,ac.status,ac.type,ac.weight,ac.task,ac.user,ev.supervisor,ev.activity,tsk.taskWeight from ActivityEvaluation ev,Activity ac,Task tsk where ev.activity=ac.activityId and ac.task=tsk.taskId and ac.user="+ usr.getUserId()+" group by ac.task")) {
				EvaluationDto dto = new EvaluationDto();
				dto.setEvaluationId(Integer.parseInt(object[0]+""));
				dto.setTotalEvalActivity(Integer.parseInt(object[1]+""));
				dto.setTotalEvalMarks(Integer.parseInt(object[2]+""));
				dto.setTotalExpMarks(Integer.parseInt(object[3]+""));
				dto.setTotalActFailed(Integer.parseInt(object[4]+""));
				dto.setTotalActCompleted(Integer.parseInt(object[5]+""));
				dto.setTask((Task)object[9]);
				dto.setUser((Users)object[10]);
				dto.setSupervisor((Users)object[11]);
				dto.setActivity((Activity)object[12]);
				dto.setTaskWeight(object[13]+"");
				if (object[13].equals(LONG)) {
					dto.setRedIcon(false);	
				}else {
					dto.setRedIcon(true);	
				}
				if (object[13].equals(SHORT)) {
					dto.setGreenIcon(false);	
				}else {
					dto.setGreenIcon(true);	
				}
				
				if (object[13].equals(MEDIUM)) {
					dto.setYellowIcon(false);	
				}else {
					dto.setYellowIcon(true);	
				}
				evaluationDtos.add(dto);
			}
			return evaluationDtos;
		} catch (Exception e) {
			e.printStackTrace();
			return null;
		}
	}
	@SuppressWarnings("unchecked")
	public List<EvaluationDto> OverallActivityEvaluationByStaff(EvaluationDto task) {
		try {
			activityAvaluationStaffDtos = new ArrayList<EvaluationDto>();
			for (Object[] object : evaluationImpl.reportList("select ev.evaluationId,ev.EvaluationMarks,ev.EvaluationOverAllMarks as ExpectedMarks,ac.weight ,ev.decision,ev.EvaluationDate,ac.description,ev.supervisor,tsk.taskWeight from ActivityEvaluation ev,Activity ac,Task tsk where ev.activity=ac.activityId and ac.task=tsk.taskId and tsk.taskId="+task.getTask().getTaskId()+"")){
				EvaluationDto dto = new EvaluationDto();
				dto.setEvaluationId(Integer.parseInt(object[0]+""));
				dto.setEvaluationMarks(Integer.parseInt(object[1]+""));
				dto.setEvaluationOverAllMarks(Integer.parseInt(object[2]+""));
				dto.setWeight(object[3]+"");
				dto.setDecision(object[4]+"");
				dto.setEvaluationDate((Date)object[5]);
				dto.setDescription(object[6]+"");
				dto.setSupervisor((Users)object[7]);
				dto.setTaskWeight(object[8]+"");
				if (object[3].equals(LONG)) {
					dto.setRedIcon(false);	
				}else {
					dto.setRedIcon(true);	
				}
				if (object[3].equals(SHORT)) {
					dto.setGreenIcon(false);	
				}else {
					dto.setGreenIcon(true);	
				}			
				if (object[3].equals(MEDIUM)) {
					dto.setYellowIcon(false);	
				}else {
					dto.setYellowIcon(true);	
				}
				activityAvaluationStaffDtos.add(dto);
			}
			staffEvaluation=false;
			this.viewactivity=true;
			backHome=true;
			return activityAvaluationStaffDtos;	
			
		} catch (Exception e) {
			e.printStackTrace();
			return null;
		}
	}
	public void backStaffHome() {
		staffEvaluation=true;
		viewactivity=false;
		backHome=false;
	}
	@SuppressWarnings("unchecked")
	public List<EvaluationDto> OverallActivityEvaluation(EvaluationDto task) {
		try {
			activityAvaluationDtos = new ArrayList<EvaluationDto>();
			for (Object[] object : evaluationImpl.reportList("select ev.evaluationId,ev.EvaluationMarks,ev.EvaluationOverAllMarks as ExpectedMarks,ac.weight ,ev.decision,ev.EvaluationDate,ac.description,ev.supervisor,tsk.taskWeight from ActivityEvaluation ev,Activity ac,Task tsk where ev.activity=ac.activityId and ac.task=tsk.taskId and tsk.taskId="+task.getTask().getTaskId()+"")){
				EvaluationDto dto = new EvaluationDto();
				dto.setEvaluationId(Integer.parseInt(object[0]+""));
				dto.setEvaluationMarks(Integer.parseInt(object[1]+""));
				dto.setEvaluationOverAllMarks(Integer.parseInt(object[2]+""));
				dto.setWeight(object[3]+"");
				dto.setDecision(object[4]+"");
				dto.setEvaluationDate((Date)object[5]);
				dto.setDescription(object[6]+"");
				dto.setSupervisor((Users)object[7]);
				dto.setTaskWeight(object[8]+"");
				if (object[3].equals(LONG)) {
					dto.setRedIcon(false);	
				}else {
					dto.setRedIcon(true);	
				}
				if (object[3].equals(SHORT)) {
					dto.setGreenIcon(false);	
				}else {
					dto.setGreenIcon(true);	
				}
				
				if (object[3].equals(MEDIUM)) {
					dto.setYellowIcon(false);	
				}else {
					dto.setYellowIcon(true);	
				}
				activityAvaluationDtos.add(dto);
			}
			this.viewSpecfcStaff=true;
			this.viewactivity=true;
			viewStaffActiv=false;
			backHome=true;
			return activityAvaluationDtos;	
			
		} catch (Exception e) {
			e.printStackTrace();
			return null;
		}
	}
	// user results
	@SuppressWarnings("unchecked")
	public List<EvaluationDto> completedEvaByStaff() {
		try {  
			evaluationDtos = new ArrayList<EvaluationDto>();
			for (Object[] object : evaluationImpl.reportList(
					"select us.evaluationId,us.decision,us.EvaluationMarks,us.activity,us.supervisor,tsk.taskWeight from ActivityEvaluation us,Activity co,Task tsk where us.activity=co.activityId and co.task=tsk.taskId and co.user="
							+ usersSession.getUserId() + "")) {
				EvaluationDto dto = new EvaluationDto();
				LOGGER.info(":::EVALUATION:::"+object[0]+":::::::::"+object[2]);
				dto.setEvaluationId(Integer.parseInt(object[0] + ""));
				dto.setDecision(object[1] + "");
				dto.setFailedBtn(false);
				dto.setEvaluationMarks(Integer.parseInt(object[2] + ""));
				dto.setActivity((Activity) object[3]);
				dto.setUser((Users) object[4]);
				dto.setTaskWeight(object[5]+"");
				if (object[5].equals(LONG)) {
					dto.setRedIcon(false);	
				}else {
					dto.setRedIcon(true);	
				}
				if (object[5].equals(SHORT)) {
					dto.setGreenIcon(false);	
				}else {
					dto.setGreenIcon(true);	
				}
				
				if (object[5].equals(MEDIUM)) {
					dto.setYellowIcon(false);	
				}else {
					dto.setYellowIcon(true);	
				}
				evaluationDtos.add(dto);
			}
			return evaluationDtos;
		} catch (Exception e) {
			LOGGER.info(CLASSNAME + ":::Activity Status is failling with HibernateException  error");
			JSFMessagers.resetMessages();
			setValid(false);
			JSFMessagers.addErrorMessage(getProvider().getValue("com.server.side.internal.error"));
			LOGGER.info(e.getMessage());
			e.printStackTrace();
			return null;
		}
	}

	public int getTargetWeightMarks(EvaluationDto evaluationDto) {
		try {
			institution = evaluationDto.getActivity().getUser().getBoard().getInstitution();
			policy = null;
			policy = policyImpl.getModelWithMyHQL(new String[] { "institution", "genericStatus" },
					new Object[] { institution, ACTIVE }, "from InstitutionEscaletePolicy");
			task=taskImpl.getTaskById(evaluationDto.getActivity().getTask().getTaskId(),"taskId");
			if (task.getTaskWeight().equals(LONG))
				return (int) policy.getLongMarks();
			else if (task.getTaskWeight().equals(MEDIUM))
				return (int) policy.getMediumgMarks();
			else if (task.getTaskWeight().equals(SHORT)) {
				return (int) policy.getShortMarks();
			} else {
				return 0;
			}
		} catch (Exception e) {
			return 0;
		}
	}
	@SuppressWarnings("unchecked")
	public List<EvaluationDto> ActEvaluatedByStaff() {
		try {
			evaluationStaffDtos = new ArrayList<EvaluationDto>();
			for (Object[] object : evaluationImpl.reportList(
					"select ev.evaluationId,count(ac.activityId) as TotalEvaluatedActivity,Sum(ev.EvaluationMarks) as TotalEvaluationMarks,Sum(ev.EvaluationOverAllMarks) as TotalExpectedMarks,sum(case when (ev.decision='Failed') then 1 else 0 end) as TotalFailed,sum(case when (ev.decision='Completed') then 1 else 0 end) as TotalCompleted,ac.status,ac.type,ac.weight,ac.task,ac.user,ev.supervisor,ev.activity,tsk.taskWeight from ActivityEvaluation ev,Activity ac,Task tsk where ev.activity=ac.activityId and ac.task=tsk.taskId and ac.user="+ usersSession.getUserId()+" group by ac.task")) {
				EvaluationDto dto = new EvaluationDto();
				dto.setEvaluationId(Integer.parseInt(object[0]+""));
				dto.setTotalEvalActivity(Integer.parseInt(object[1]+""));
				dto.setTotalEvalMarks(Integer.parseInt(object[2]+""));
				dto.setTotalExpMarks(Integer.parseInt(object[3]+""));
				dto.setTotalActFailed(Integer.parseInt(object[4]+""));
				dto.setTotalActCompleted(Integer.parseInt(object[5]+""));
				dto.setTask((Task)object[9]);
				dto.setUser((Users)object[10]);
				dto.setSupervisor((Users)object[11]);
				dto.setActivity((Activity)object[12]);
				dto.setTaskWeight(object[13]+"");
				if (object[13].equals(LONG)) {
					dto.setRedIcon(false);	
				}else {
					dto.setRedIcon(true);	
				}
				if (object[13].equals(SHORT)) {
					dto.setGreenIcon(false);	
				}else {
					dto.setGreenIcon(true);	
				}
				
				if (object[13].equals(MEDIUM)) {
					dto.setYellowIcon(false);	
				}else {
					dto.setYellowIcon(true);	
				}
				evaluationStaffDtos.add(dto);
			}
			return evaluationStaffDtos;
		} catch (Exception e) {
			e.printStackTrace();
			return null;
		}
	}

	/* Getter and setters starts */
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

	public Evaluation getEvaluation() {
		return evaluation;
	}

	public void setEvaluation(Evaluation evaluation) {
		this.evaluation = evaluation;
	}

	public Activity getActivity() {
		return activity;
	}

	public void setActivity(Activity activity) {
		this.activity = activity;
	}

	public Users getUsers() {
		return users;
	}

	public void setUsers(Users users) {
		this.users = users;
	}

	public InstitutionEscaletePolicy getPolicy() {
		return policy;
	}

	public void setPolicy(InstitutionEscaletePolicy policy) {
		this.policy = policy;
	}

	public Users getUsersSession() {
		return usersSession;
	}

	public void setUsersSession(Users usersSession) {
		this.usersSession = usersSession;
	}

	public EvaluationImpl getEvaluationImpl() {
		return evaluationImpl;
	}

	public void setEvaluationImpl(EvaluationImpl evaluationImpl) {
		this.evaluationImpl = evaluationImpl;
	}

	public ActivityImpl getActivityImpl() {
		return activityImpl;
	}

	public void setActivityImpl(ActivityImpl activityImpl) {
		this.activityImpl = activityImpl;
	}

	public JSFBoundleProvider getProvider() {
		return provider;
	}

	public void setProvider(JSFBoundleProvider provider) {
		this.provider = provider;
	}

	public UserImpl getUsersImpl() {
		return usersImpl;
	}

	public void setUsersImpl(UserImpl usersImpl) {
		this.usersImpl = usersImpl;
	}

	public static Logger getLogger() {
		return LOGGER;
	}

	public static long getSerialversionuid() {
		return serialVersionUID;
	}

	public List<Activity> getActivities() {
		return activities;
	}

	public void setActivities(List<Activity> activities) {
		this.activities = activities;
	}

	public Timestamp getTimestamp() {
		return timestamp;
	}

	public void setTimestamp(Timestamp timestamp) {
		this.timestamp = timestamp;
	}

	public InstitutionEscaletPolicyImpl getPolicyImpl() {
		return policyImpl;
	}

	public void setPolicyImpl(InstitutionEscaletPolicyImpl policyImpl) {
		this.policyImpl = policyImpl;
	}

	public boolean isViewStaffActiv() {
		return viewStaffActiv;
	}

	public void setViewStaffActiv(boolean viewStaffActiv) {
		this.viewStaffActiv = viewStaffActiv;
	}

	public boolean isViewSpecfcStaff() {
		return viewSpecfcStaff;
	}

	public void setViewSpecfcStaff(boolean viewSpecfcStaff) {
		viewSpecfcStaff = viewSpecfcStaff;
	}

	public Institution getInstitution() {
		return institution;
	}

	public void setInstitution(Institution institution) {
		this.institution = institution;
	}

	public List<Evaluation> getEvaluations() {
		return evaluations;
	}

	public void setEvaluations(List<Evaluation> evaluations) {
		this.evaluations = evaluations;
	}

	public InstitutionImpl getInstImpl() {
		return instImpl;
	}

	public void setInstImpl(InstitutionImpl instImpl) {
		this.instImpl = instImpl;
	}

	public List<Users> getStaffs() {
		return staffs;
	}

	public void setStaffs(List<Users> staffs) {
		this.staffs = staffs;
	}

	public boolean isDwnBtn() {
		return dwnBtn;
	}

	public void setDwnBtn(boolean dwnBtn) {
		this.dwnBtn = dwnBtn;
	}

	public boolean isApDltBtn() {
		return apDltBtn;
	}

	public void setApDltBtn(boolean apDltBtn) {
		this.apDltBtn = apDltBtn;
	}

	public ContactImpl getContactImpl() {
		return contactImpl;
	}

	public void setContactImpl(ContactImpl contactImpl) {
		this.contactImpl = contactImpl;
	}

	public boolean isViewAttached() {
		return viewAttached;
	}

	public void setViewAttached(boolean viewAttached) {
		this.viewAttached = viewAttached;
	}

	public UploadingActivity getUploadingActivity() {
		return uploadingActivity;
	}

	public void setUploadingActivity(UploadingActivity uploadingActivity) {
		this.uploadingActivity = uploadingActivity;
	}

	public UploadingActivityImpl getUpimpl() {
		return upimpl;
	}

	public void setUpimpl(UploadingActivityImpl upimpl) {
		this.upimpl = upimpl;
	}

	public List<UploadingActivity> getDocmts() {
		return docmts;
	}

	public void setDocmts(List<UploadingActivity> docmts) {
		this.docmts = docmts;
	}

	public List<Users> getStaffsComplete() {
		return staffsComplete;
	}

	public void setStaffsComplete(List<Users> staffsComplete) {
		this.staffsComplete = staffsComplete;
	}

	public boolean isRsbtn() {
		return rsbtn;
	}

	public void setRsbtn(boolean rsbtn) {
		this.rsbtn = rsbtn;
	}

	public List<ActivityDto> getActivitiesDtos() {
		return activitiesDtos;
	}

	public void setActivitiesDtos(List<ActivityDto> activitiesDtos) {
		this.activitiesDtos = activitiesDtos;
	}

	public List<EvaluationDto> getEvaluationDtos() {
		return evaluationDtos;
	}

	public void setEvaluationDtos(List<EvaluationDto> evaluationDtos) {
		this.evaluationDtos = evaluationDtos;
	}

	public List<EvaluationDto> getActivityAvaluationDtos() {
		return activityAvaluationDtos;
	}

	public void setActivityAvaluationDtos(List<EvaluationDto> activityAvaluationDtos) {
		this.activityAvaluationDtos = activityAvaluationDtos;
	}

	public boolean isViewactivity() {
		return viewactivity;
	}

	public void setViewactivity(boolean viewactivity) {
		this.viewactivity = viewactivity;
	}

	public boolean isBackHome() {
		return backHome;
	}

	public void setBackHome(boolean backHome) {
		this.backHome = backHome;
	}

	public List<EvaluationDto> getEvaluationStaffDtos() {
		return evaluationStaffDtos;
	}

	public void setEvaluationStaffDtos(List<EvaluationDto> evaluationStaffDtos) {
		this.evaluationStaffDtos = evaluationStaffDtos;
	}

	public List<EvaluationDto> getActivityAvaluationStaffDtos() {
		return activityAvaluationStaffDtos;
	}

	public void setActivityAvaluationStaffDtos(List<EvaluationDto> activityAvaluationStaffDtos) {
		this.activityAvaluationStaffDtos = activityAvaluationStaffDtos;
	}

	public boolean isStaffEvaluation() {
		return staffEvaluation;
	}

	public void setStaffEvaluation(boolean staffEvaluation) {
		this.staffEvaluation = staffEvaluation;
	}

	public TaskImpl getTaskImpl() {
		return taskImpl;
	}

	public void setTaskImpl(TaskImpl taskImpl) {
		this.taskImpl = taskImpl;
	}

	public Task getTask() {
		return task;
	}

	public void setTask(Task task) {
		this.task = task;
	}

	/* Getter and setters ends */

}
