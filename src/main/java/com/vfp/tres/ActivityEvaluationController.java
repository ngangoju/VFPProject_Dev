package com.vfp.tres;

import java.io.Serializable;
import java.security.Policy;
import java.util.Date;
import java.sql.Timestamp;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
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
import tres.dao.impl.ActivityEvaluationImpl;
import tres.dao.impl.ActivityImpl;
import tres.dao.impl.ContactImpl;
import tres.dao.impl.EvaluationImpl;
import tres.dao.impl.InstitutionEscaletPolicyImpl;
import tres.dao.impl.InstitutionImpl;
import tres.dao.impl.UploadingActivityImpl;
import tres.dao.impl.UserImpl;
import tres.domain.Activity;
import tres.domain.ActivityEvaluation;
import tres.domain.Board;
import tres.domain.Contact;
import tres.domain.Evaluation;
import tres.domain.Institution;
import tres.domain.InstitutionEscaletePolicy;
import tres.domain.InstitutionRegistrationRequest;
import tres.domain.UploadingActivity;
import tres.domain.Users;
import tres.vfp.dto.ActivityDto;
import tres.vfp.dto.EvaluationDto;
import tres.vfp.dto.InstitutionDto;
@ManagedBean
@SessionScoped
public class ActivityEvaluationController implements Serializable, DbConstant {
	private static final Logger LOGGER = Logger.getLogger(Thread.currentThread().getStackTrace()[0].getClassName());
	private String CLASSNAME = "ActivityEvaluationController :: ";
	private static final long serialVersionUID = 1L;
	/* to manage validation messages */
	private boolean isValid, viewStaffActiv, viewSpecfcStaff, dwnBtn, apDltBtn, viewAttached, rsbtn;
	/* end manage validation messages */

	private ActivityEvaluation evaluation;
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

	ActivityEvaluationImpl evaluationImpl = new ActivityEvaluationImpl();
	ActivityImpl activityImpl = new ActivityImpl();
	InstitutionImpl instImpl = new InstitutionImpl();
	JSFBoundleProvider provider = new JSFBoundleProvider();
	UserImpl usersImpl = new UserImpl();
	Timestamp timestamp = new Timestamp(Calendar.getInstance().getTime().getTime());
	InstitutionEscaletPolicyImpl policyImpl = new InstitutionEscaletPolicyImpl();
	ContactImpl contactImpl = new ContactImpl();
	UploadingActivityImpl upimpl = new UploadingActivityImpl();
	private List<EvaluationDto> evaluationDtos = new ArrayList<EvaluationDto>();
	@SuppressWarnings("unchecked")
	@PostConstruct
	public void init() {
		HttpSession session = SessionUtils.getSession();
		usersSession = (Users) session.getAttribute("userSession");
		if (evaluation == null) {
			evaluation = new ActivityEvaluation();
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
			staffs = getUserDetails();// staff with complete activities
			// staffsComplete = getUserDetailsCompletedActivities(usersSession);
			evaluationDtos=completedEvaByStaff();
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
					"select distinct us.userId, us.fname, us.lname, us.board from Users us,Activity co where co.user=us.userId and us.board="
							+ usersSession.getBoard().getBoardId() + " and co.status='" + COMPLETED + "' or co.status='"
							+ FAILED + "' ")) {
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
		// viewSpecfcStaff = false;
		viewStaffActiv = false;
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
			System.out.println("viewSpecfcStaff::" + viewSpecfcStaff);
			activities = activityImpl.getGenericListWithHQLParameter(new String[] { "genericStatus", "status", "user" },
					new Object[] { ACTIVE, DONE, user }, "Activity", "ACTIVITY_ID desc");
		} catch (Exception e) {
		}
	}

	/*
	 * // list of complete ativities by staff
	 * 
	 * @SuppressWarnings("unchecked") public void staffComplAct(Users user) { try {
	 * viewStaffActiv = true; viewSpecfcStaff = true; activities = activityImpl
	 * .getListWithHQL("select f from Activity f where f.genericStatus='" + ACTIVE +
	 * "' and f.user=" + user.getUserId() + " and f.status='" + COMPLETED +
	 * "' or f.status='" + FAILED + "'  "); } catch (Exception e) { } }
	 */

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
	@SuppressWarnings("unchecked")
	public List<EvaluationDto> completedEvaByStaff() {
		try {  
			evaluationDtos = new ArrayList<EvaluationDto>();
			for (Object[] object : evaluationImpl.reportList(
					"select us.evaluationId,us.decision,us.EvaluationMarks,us.activity,us.supervisor from ActivityEvaluation us,Activity co where us.activity=co.activityId and co.user="
							+ usersSession.getUserId() + "")) {
				EvaluationDto dto = new EvaluationDto();
				LOGGER.info(":::EVALUATION:::"+object[0]+":::::::::"+object[2]);
				dto.setEvaluationId(Integer.parseInt(object[0] + ""));
				dto.setDecision(object[1] + "");
				dto.setFailedBtn(false);
				dto.setEvaluationMarks(Integer.parseInt(object[2] + ""));
				dto.setActivity((Activity) object[3]);
				dto.setUser((Users) object[4]);
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
	@SuppressWarnings("unchecked")
	public String checkActivityReportedBeforeAndWithinPeriod(Activity activity) {
		try {
			Activity act = new Activity();
			act = new Activity();
			act = activityImpl.getActivityById(activity.getActivityId(), "activityId");

			act.setStatus(COMPLETED);
			act.setUpdatedBy(usersSession.getViewId());
			act.setUpDtTime(timestamp);
			activityImpl.UpdateActivity(act);
			ActivityEvaluation evaluate= new ActivityEvaluation();
			evaluate = new ActivityEvaluation();
			evaluate.setSupervisor(usersSession);
			evaluate.setActivity(act);
			evaluate.setCreatedBy(usersSession.getFname() + " " + usersSession.getLname());
			evaluate.setCrtdDtTime(timestamp);
			evaluate.setDecision(COMPLETED);
			evaluate.setEvaluationDate(timestamp);
			evaluate.setEvaluationMarks(getMarks(activity));
			evaluate.setEvaluationOverAllMarks(getMarks(activity));
			evaluate.setGenericStatus(ACTIVE);
			evaluate.setUpdatedBy(usersSession.getViewId());
			evaluate.setUpDtTime(timestamp);
			evaluationImpl.saveActivityEvaluation(evaluate);
			JSFMessagers.resetMessages();
			setValid(true);
			JSFMessagers.addErrorMessage(getProvider().getValue("evaluation.complete.Negform"));
			staffs = getUserDetails();// staff with complete activities
			staffCompl(activity.getUser());
		} catch (Exception e) {
			JSFMessagers.resetMessages();
			setValid(false);
			JSFMessagers.addErrorMessage(getProvider().getValue("com.updatefail.form.activity"));
			LOGGER.info(CLASSNAME + ":::Activity Details is saved");
		}
		return null;
	}
	@SuppressWarnings("unchecked")
	public String checkActReportedAfterPeriod(Activity activity) {
		try {
			Activity act = new Activity();
			act = new Activity();
			act = activityImpl.getActivityById(activity.getActivityId(), "activityId");
			act.setStatus(FAILED);
			act.setUpdatedBy(usersSession.getViewId());
			act.setUpDtTime(timestamp);
			activityImpl.UpdateActivity(act);
			ActivityEvaluation evaluate= new ActivityEvaluation();
			evaluate = new ActivityEvaluation();
			evaluate.setActivity(act);
			evaluate.setSupervisor(usersSession);
			evaluate.setCreatedBy(usersSession.getFname() + " " + usersSession.getLname());
			evaluate.setCrtdDtTime(timestamp);
			evaluate.setDecision(FAILED);
			evaluate.setEvaluationDate(timestamp);
			evaluate.setEvaluationMarks(defaultCount);
			evaluate.setEvaluationOverAllMarks(getMarks(activity));
			evaluate.setGenericStatus(ACTIVE);
			evaluate.setUpdatedBy(usersSession.getViewId());
			evaluate.setUpDtTime(timestamp);
			evaluationImpl.saveActivityEvaluation(evaluate);
			setValid(true);
			JSFMessagers.addErrorMessage(getProvider().getValue("evaluation.complete.Negform"));
			staffs = getUserDetails();// staff with complete activities
			staffCompl(activity.getUser());
		} catch (Exception e) {
			JSFMessagers.resetMessages();
			setValid(false);
			JSFMessagers.addErrorMessage(getProvider().getValue("com.updatefail.form.activity"));
			LOGGER.info(CLASSNAME + ":::Activity Details is saved");
		}
		return null;
	}
	
	public String savingEvaluationData(Activity activity) {
		try {
			Activity act = new Activity();
			act = new Activity();
			act = activityImpl.getActivityById(activity.getActivityId(), "activityId");

			LOGGER.info("here update sart for " + act + " activityiD " + act.getActivityId());

			SimpleDateFormat smf = new SimpleDateFormat("yyyy-MM-dd");
			String dt = smf.format(activity.getEndDate());
			Date date1 = smf.parse(dt);
			Date date2 =addDay(act);

			LOGGER.info("TODAY DATE:::" + smf.format(date1));
			LOGGER.info("::::DUE DATE::::"+smf.format(date2));
			 
			 if (date2.compareTo(date1) > 0 ||date1.compareTo(date2) == 0) {
				 LOGGER.info("DueDate is after ReportedDate(EndDate) or DueDate is equal to Reported(EndDate)");
				 checkActivityReportedBeforeAndWithinPeriod(activity);
			    } else {
			    	checkActReportedAfterPeriod(activity);
			    }
		} catch (Exception e) {
			LOGGER.info("DB ERROR:::");
			setValid(false);
			e.printStackTrace();
			JSFMessagers.addErrorMessage(getProvider().getValue("com.server.side.internal.error"));
			LOGGER.info(e.getMessage());
			e.printStackTrace();
		}
		return null;
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
			
			ActivityEvaluation evaluate= new ActivityEvaluation();
			evaluate.setActivity(act);
			evaluate.setCreatedBy(usersSession.getFname() + " " + usersSession.getLname());
			evaluate.setCrtdDtTime(timestamp);
			evaluate.setDecision(FAILED);
			evaluate.setEvaluationDate(timestamp);
			evaluate.setEvaluationMarks(defaultCount);
			evaluate.setEvaluationOverAllMarks(getMarks(act));
			evaluate.setGenericStatus(ACTIVE);
			evaluate.setSupervisor(usersSession);
			evaluationImpl.saveActivityEvaluation(evaluate);
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
			policy = null;
			policy = policyImpl.getModelWithMyHQL(new String[] { "institution", "genericStatus" },
					new Object[] { institution, ACTIVE }, "from InstitutionEscaletePolicy");
			if (act.getWeight().equals(LONG))
				return (int) policy.getLongMarks();
			else if (act.getWeight().equals(MEDIUM))
				return (int) policy.getMediumgMarks();
			else if (act.getWeight().equals(SHORT)) {
				return (int) policy.getShortMarks();
			} else {
				return 0;
			}
		} catch (Exception e) {
			return 0;
		}
	}

	/* activity rejection ends */
	public int getMarksOfEvaluation(Activity act) {
		try {
			evaluation = new ActivityEvaluation();
			evaluation = evaluationImpl.getModelWithMyHQL(new String[] { "activity", "genericStatus", "decision" },
					new Object[] { act, ACTIVE, COMPLETED }, "from Evaluation");
			return evaluation.getEvaluationMarks();
		} catch (Exception e) {
			return 0;
		}
	}

	public String getStatusOfEvaluation(Activity act) {
		try {
			evaluation = new ActivityEvaluation();
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

	public ActivityEvaluation getEvaluation() {
		return evaluation;
	}

	public void setEvaluation(ActivityEvaluation evaluation) {
		this.evaluation = evaluation;
	}

	public ActivityEvaluationImpl getEvaluationImpl() {
		return evaluationImpl;
	}

	public void setEvaluationImpl(ActivityEvaluationImpl evaluationImpl) {
		this.evaluationImpl = evaluationImpl;
	}

	public List<EvaluationDto> getEvaluationDtos() {
		return evaluationDtos;
	}

	public void setEvaluationDtos(List<EvaluationDto> evaluationDtos) {
		this.evaluationDtos = evaluationDtos;
	}

	/* Getter and setters ends */

}
