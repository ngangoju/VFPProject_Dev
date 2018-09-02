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
import tres.dao.impl.MenuAssignmentImpl;
import tres.dao.impl.MenuGroupImpl;
import tres.dao.impl.StrategicPlanImpl;
import tres.dao.impl.UserImpl;
import tres.domain.Contact;
import tres.domain.MenuAssignment;
import tres.domain.MenuGroup;
import tres.domain.StrategicPlan;
import tres.domain.Users;

@ManagedBean
@ViewScoped
public class StrategicPlanController implements Serializable, DbConstant {
	private static final Logger LOGGER = Logger.getLogger(Thread.currentThread().getStackTrace()[0].getClassName());
	private String CLASSNAME = "StrategicPlanController :: ";
	private static final long serialVersionUID = 1L;
	/*to manage validation messages*/
	private boolean isValid;
	/*end  manage validation messages*/
	private Users users;
	private Users usersSession;
	private  int   userIdNumber;
	private StrategicPlan strategicPlan;
	private List<StrategicPlan> strategicPlanDetails = new ArrayList<StrategicPlan>();
	
	/*class injection*/
	
	JSFBoundleProvider provider = new JSFBoundleProvider();
	UserImpl usersImpl = new UserImpl();
	StrategicPlanImpl strategicPlanImpl = new StrategicPlanImpl();
	
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
		
		if (strategicPlan == null) {
			strategicPlan = new StrategicPlan();
		}
		
		try {
			
			strategicPlanDetails=strategicPlanImpl.getGenericListWithHQLParameter(new String[] {"genericStatus"},new Object[] {ACTIVE}, "StrategicPlan", "planId asc");
		} catch (Exception e) {
			setValid(false);
			JSFMessagers.addErrorMessage(getProvider().getValue("com.server.side.internal.error"));
			LOGGER.info(e.getMessage());
			e.printStackTrace();
		}

	
		
	}
	
	public String savePlan() {
		try {
			strategicPlan.setCreatedBy(usersSession.getViewId());
			strategicPlan.setCrtdDtTime(timestamp);
			strategicPlan.setGenericStatus(ACTIVE);
			strategicPlan.setUpDtTime(timestamp);
			strategicPlan.setUsers(usersImpl.gettUserById(usersSession.getUserId(), "userId"));
			strategicPlan.setUpdatedBy(usersSession.getViewId());
			strategicPlan.setRecordedDate(timestamp);
			strategicPlanImpl.saveStrategicPlan(strategicPlan);
			JSFMessagers.resetMessages();
			setValid(true);
			JSFMessagers.addErrorMessage(getProvider().getValue("com.save.form.strategicPlan"));
			LOGGER.info(CLASSNAME+":::StrategicPlan Details is saved");
			clearContactFuileds();
			return"";
			
		} catch (Exception e) {
			LOGGER.info(CLASSNAME+":::Strategic Plan Details is failling with HibernateException  error");
			JSFMessagers.resetMessages();
			setValid(false);
			JSFMessagers.addErrorMessage(getProvider().getValue("com.server.side.internal.error"));
			LOGGER.info(CLASSNAME+""+e.getMessage());
			e.printStackTrace();
			return"";	
		}
		
	}

private void clearContactFuileds() {
	strategicPlan=new StrategicPlan();
	strategicPlanDetails=null;
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

	public StrategicPlan getStrategicPlan() {
		return strategicPlan;
	}

	public void setStrategicPlan(StrategicPlan strategicPlan) {
		this.strategicPlan = strategicPlan;
	}

	public List<StrategicPlan> getStrategicPlanDetails() {
		return strategicPlanDetails;
	}

	public void setStrategicPlanDetails(List<StrategicPlan> strategicPlanDetails) {
		this.strategicPlanDetails = strategicPlanDetails;
	}

	public JSFBoundleProvider getProvider() {
		return provider;
	}

	public void setProvider(JSFBoundleProvider provider) {
		this.provider = provider;
	}

	public StrategicPlanImpl getStrategicPlanImpl() {
		return strategicPlanImpl;
	}

	public void setStrategicPlanImpl(StrategicPlanImpl strategicPlanImpl) {
		this.strategicPlanImpl = strategicPlanImpl;
	}


}
