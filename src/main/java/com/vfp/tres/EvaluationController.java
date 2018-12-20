package com.vfp.tres;

import java.io.Serializable;
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
import javax.servlet.http.HttpSession;
import tres.common.DbConstant;
import tres.common.JSFBoundleProvider;
import tres.common.JSFMessagers;
import tres.common.SessionUtils;
import tres.dao.impl.ActivityImpl;
import tres.dao.impl.ContactImpl;
import tres.dao.impl.EvaluationImpl;
import tres.dao.impl.InstitutionEscaletPolicyImpl;
import tres.dao.impl.InstitutionImpl;
import tres.dao.impl.UploadingActivityImpl;
import tres.dao.impl.UserImpl;
import tres.domain.Activity;
import tres.domain.Board;
import tres.domain.Contact;
import tres.domain.Evaluation;
import tres.domain.Institution;
import tres.domain.InstitutionEscaletePolicy;
import tres.domain.InstitutionRegistrationRequest;
import tres.domain.UploadingActivity;
import tres.domain.Users;
import tres.vfp.dto.InstitutionDto;

@ManagedBean
@SessionScoped
public class EvaluationController implements Serializable, DbConstant {
	private static final Logger LOGGER = Logger.getLogger(Thread.currentThread().getStackTrace()[0].getClassName());
	private String CLASSNAME = "EvaluationController :: ";
	private static final long serialVersionUID = 1L;
	/* to manage validation messages */
	private boolean isValid, viewStaffActiv, viewSpecfcStaff, dwnBtn, apDltBtn, viewAttached;
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

	EvaluationImpl evaluationImpl = new EvaluationImpl();
	ActivityImpl activityImpl = new ActivityImpl();
	InstitutionImpl instImpl = new InstitutionImpl();
	JSFBoundleProvider provider = new JSFBoundleProvider();
	UserImpl usersImpl = new UserImpl();
	Timestamp timestamp = new Timestamp(Calendar.getInstance().getTime().getTime());
	InstitutionEscaletPolicyImpl policyImpl = new InstitutionEscaletPolicyImpl();
	ContactImpl contactImpl = new ContactImpl();
	UploadingActivityImpl upimpl = new UploadingActivityImpl();

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

			staffs = getUserDetails(usersSession);// staff with complete activities
		} catch (Exception e) {
			setValid(false);
			e.printStackTrace();
			JSFMessagers.addErrorMessage(getProvider().getValue("com.server.side.internal.error"));
			LOGGER.info(e.getMessage());
			e.printStackTrace();
		}
	}

	public List<Users> getUserDetails(Users user1) {
		try {
			staffs = new ArrayList<Users>();
			for (Object[] object : usersImpl.reportList(
					"select distinct us.userId, us.fname, us.lname, us.board from Users us,Activity co where co.user=us.userId and us.board="
							+ user1.getBoard().getBoardId() + " and co.status='" + COMPLETED + "'")) {
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
			activities = activityImpl.getGenericListWithHQLParameter(new String[] { "genericStatus", "status", "user" },
					new Object[] { ACTIVE, COMPLETED, user }, "Activity", "ACTIVITY_ID desc");
		} catch (Exception e) {
		}
	}

	/* Evaluation method for checking if time rang is valid starts */

	public boolean isOnTime(Activity activity) {
		try {
			LOGGER.info("Is on time due date is::::");
			if (timestamp.after(activity.getDueDate())) {
				LOGGER.info("Founded::::");
				return false;
			}
		} catch (Exception e) {
			e.printStackTrace();
			return false;
		}
		return isValid;
	}

	/* Evaluation Method For making */

	public double activityMarksByWeight(Activity activity) {
		try {
			double mark = 0;
			institution = activity.getUser().getBoard().getInstitution();
			policy = policyImpl.getModelWithMyHQL(new String[] { "institution" }, new Object[] { institution },
					"from InstitutionEscaletePolicy");
			Date dt = addDay(activity.getDueDate());
			if (activity.getWeight().equals(SHORT)) {
				if (isOnTime(activity)) {
					mark = policy.getShortMarks();
				} else if ((!isOnTime(activity)) && (timestamp.before(addDay(activity.getDueDate())))) {
					mark = policy.getShortMarks() / 2;
				} else if ((!isOnTime(activity)) && (!timestamp.before(addDay(activity.getDueDate())))) {
					mark = 0;
					LOGGER.info("We are giving Date Conditions::::" + activity.getDueDate());
				} else {
					LOGGER.info("Lost Date Conditions::::" + activity.getDueDate());
				}

			} else if (activity.getWeight().equals(MEDIUM)) {
				if (isOnTime(activity)) {
					mark = policy.getMediumgMarks();
				} else if ((!isOnTime(activity)) && (timestamp.before(addDay(activity.getDueDate())))) {
					mark = policy.getMediumgMarks() / 2;
				} else {
					mark = 0;
				}
			} else if (activity.getWeight().equals(LONG)) {
				if (isOnTime(activity)) {
					mark = policy.getLongMarks();
				} else if ((!isOnTime(activity)) && (timestamp.before(addDay(activity.getDueDate())))) {
					mark = policy.getLongMarks() / 2;
				} else {
					mark = 0;
				}
			}
			return mark;
		} catch (Exception e) {
			e.printStackTrace();
			return 0.0;
		}
	}

	/* Evaluation Method For Completed task */

	public boolean isCompleted(Activity activity) {
		try {
			if (activity.getStatus().equals(COMPLETED)) {
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

	public String savingEvaluationData(Activity activity, double mrks, String decsn) {
		try {
			evaluation.setActivity(activity);
			evaluation.setCreatedBy(usersSession.getFname() + " " + usersSession.getLname());
			evaluation.setCrtdDtTime(timestamp);
			evaluation.setDecision(decsn);
			evaluation.setEvaluationDate(timestamp);
			evaluation.setEvaluationMarks((int) mrks);
			evaluation.setGenericStatus(ACTIVE);
			evaluation.setUpdatedBy(usersSession.getViewId());
			evaluation.setUpDtTime(timestamp);
			evaluationImpl.saveEvaluation(evaluation);
			LOGGER.info("Evaluation information saved:::::");
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

	/* Evaluation Method */

	public void evaluationMethod(Activity activity) {
		try {
			if (isCompleted(activity)) {
				savingEvaluationData(activity, activityMarksByWeight(activity), evalutionDecition(activity));
				JSFMessagers.resetMessages();
				setValid(true);
				JSFMessagers.addErrorMessage(getProvider().getValue("evaluationController.confirm.message"));
				LOGGER.info(CLASSNAME + ":::Institution request not updated");
			} else {
				savingEvaluationData(activity, activityMarksByWeight(activity), evalutionDecition(activity));
				JSFMessagers.resetMessages();
				setValid(true);
				JSFMessagers.addErrorMessage(getProvider().getValue("evaluationController.confirm.message"));
			}
		} catch (Exception e) {
			LOGGER.info("DB ERROR:::");
			setValid(false);
			e.printStackTrace();
			JSFMessagers.addErrorMessage(getProvider().getValue("com.server.side.internal.error"));
			LOGGER.info(e.getMessage());
			e.printStackTrace();
		}
	}
	// evaluating due date

	public Date addDay(Date dueDate) {
		institution = activity.getUser().getBoard().getInstitution();
		try {
			SimpleDateFormat smf = new SimpleDateFormat("dd-MM-yyyy");
			policy = policyImpl.getModelWithMyHQL(new String[] { "institution" }, new Object[] { institution },
					"from InstitutionEscaletePolicy");
			Calendar cal = Calendar.getInstance();
			cal.setTime(dueDate);
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
	public void showFiles(Activity activity) {
		try {
			try {
				viewSpecfcStaff = false;
				viewAttached = true;
				docmts = upimpl.getGenericListWithHQLParameter(new String[] { "activity" }, new Object[] { activity },
						"UploadingActivity", "activity desc");
			} catch (Exception e) {
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	// backToPrevousPage
	public void backPrsPage() {
		viewAttached = false;
		viewSpecfcStaff = true;
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

	/* Getter and setters ends */

}
