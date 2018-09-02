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
import tres.dao.impl.UserImpl;
import tres.domain.Activity;
import tres.domain.Users;

@ManagedBean
@ViewScoped
public class ActivityController implements Serializable, DbConstant {
	private static final Logger LOGGER = Logger.getLogger(Thread.currentThread().getStackTrace()[0].getClassName());
	private String CLASSNAME = "ActivityController :: ";
	private static final long serialVersionUID = 1L;
	/*to manage validation messages*/
	private boolean isValid;
	/*end  manage validation messages*/
	private Users users;
	private Users usersSession;
	private Activity activity;
	private List<Activity> activityDetails = new ArrayList<Activity>();

	private String[] status= {NOTSTARTED,APPROVED,REJECT,INPROGRESS,COMPLETED};
	
	private String[] weight= {SHORT,MEDIUM,LONG};
	
	/*class injection*/
	
	JSFBoundleProvider provider = new JSFBoundleProvider();
	UserImpl usersImpl = new UserImpl();
	ActivityImpl activityImpl = new ActivityImpl();
	
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
		
		if (activity == null) {
			activity = new Activity();
		}
		
		try {
			
			activityDetails=activityImpl.getGenericListWithHQLParameter(new String[] {"genericStatus"},new Object[] {ACTIVE}, "Activity", "activityId asc");
		} catch (Exception e) {
			setValid(false);
			JSFMessagers.addErrorMessage(getProvider().getValue("com.server.side.internal.error"));
			LOGGER.info(e.getMessage());
			e.printStackTrace();
		}

	
		
	}
	
	public String savePlan() {
		try {
			activity.setCreatedBy(usersSession.getViewId());
			activity.setCrtdDtTime(timestamp);
			activity.setGenericStatus(ACTIVE);
			activity.setUpDtTime(timestamp);
			activity.setUpdatedBy(usersSession.getViewId());
			activity.setDate(timestamp);
			activityImpl.saveActivity(activity);
			JSFMessagers.resetMessages();
			setValid(true);
			JSFMessagers.addErrorMessage(getProvider().getValue("com.save.form.activity"));
			LOGGER.info(CLASSNAME+":::Activity Details is saved");
			clearContactFuileds();
			return"";
			
		} catch (Exception e) {
			LOGGER.info(CLASSNAME+":::Activity Details is failling with HibernateException  error");
			JSFMessagers.resetMessages();
			setValid(false);
			JSFMessagers.addErrorMessage(getProvider().getValue("com.server.side.internal.error"));
			LOGGER.info(CLASSNAME+""+e.getMessage());
			e.printStackTrace();
			return"";	
		}
		
	}

private void clearContactFuileds() {
	activity=new Activity();
	activityDetails=null;
}

	public void changeSelectBox(String name) {
		
		LOGGER.info("Ajax is working with listener::::::"+name);
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


}
