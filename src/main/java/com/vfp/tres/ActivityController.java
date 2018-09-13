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
import tres.vfp.dto.ActivityDto;
import tres.vfp.dto.UserDto;

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
	private List<ActivityDto> activityDtoDetails = new ArrayList<ActivityDto>();

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
			for (Activity activity : activityDetails){
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
			return"/menu/Activity.xhtml?faces-redirect=true";
			
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

public String newActivity() {
	return "/menu/InsertActivity.xhtml?faces-redirect=true";
}

	public void changeSelectBox(String name) {
		
		LOGGER.info("Ajax is working with listener::::::"+name);
	}
	public String saveAction(ActivityDto activity) {
		LOGGER.info("update  saveAction method");
		//get all existing value but set "editable" to false 
		Activity act=new Activity();
		act=new Activity();
		act=activityImpl.getActivityById(activity.getActivityId(), "activityId");
		
			LOGGER.info("here update sart for "+act +" activityiD "+act.getActivityId());

			activity.setEditable(false);
			act.setDescription(activity.getDescription());
			act.setStatus(activity.getStatus());
		
			activityImpl.UpdateActivity(act);
			
		//return to current page
		return null;
		
	}

	public String cancel(ActivityDto activity) {
		activity.setEditable(false);
		//usersImpl.UpdateUsers(user);
		return null;
		
		
	}

	public String editAction(ActivityDto activity) {
	    
		activity.setEditable(true);
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

	public List<ActivityDto> getActivityDtoDetails() {
		return activityDtoDetails;
	}

	public void setActivityDtoDetails(List<ActivityDto> activityDtoDetails) {
		this.activityDtoDetails = activityDtoDetails;
	}


}
