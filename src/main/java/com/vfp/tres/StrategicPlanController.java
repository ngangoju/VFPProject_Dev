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
import tres.dao.impl.UserImpl;
import tres.domain.Activity;
import tres.domain.StrategicPlan;
import tres.domain.Users;
import tres.vfp.dto.ActivityDto;
import tres.vfp.dto.StrategicPlanDto;

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
	private StrategicPlan strategicPlan;
	private List<StrategicPlan> strategicPlanDetails = new ArrayList<StrategicPlan>();
	private List<StrategicPlanDto> strategicPlanDtoDetails = new ArrayList<StrategicPlanDto>();
	
	
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
			
			strategicPlanDetails=strategicPlanImpl.getGenericListWithHQLParameter(new String[] {"createdBy"},new Object[] {usersSession.getFname()+" "+usersSession.getLname()}, "StrategicPlan", "planId asc");
			for(StrategicPlan strategicPlan: strategicPlanDetails) {
				StrategicPlanDto strategicPlanDto = new StrategicPlanDto();
				strategicPlanDto.setStrategicPlanId(strategicPlan.getPlanId());
				strategicPlanDto.setEditable(false);
				strategicPlanDto.setDetails(strategicPlan.getDetails());
				strategicPlanDto.setRecordedDate(strategicPlan.getRecordedDate());
				strategicPlanDto.setGenericStatus(strategicPlan.getGenericStatus());
				strategicPlanDto.setUsers(strategicPlan.getUsers());
				strategicPlanDtoDetails.add(strategicPlanDto);
			}
		} catch (Exception e) {
			setValid(false);
			JSFMessagers.addErrorMessage(getProvider().getValue("com.server.side.internal.error"));
			LOGGER.info(e.getMessage());
			e.printStackTrace();
		}

	
		
	}
	
	public String savePlan() {
		try {
			strategicPlan.setCreatedBy(usersSession.getFname()+" "+usersSession.getLname());
			strategicPlan.setCrtdDtTime(timestamp);
			strategicPlan.setGenericStatus(ACTIVE);
			strategicPlan.setUpDtTime(timestamp);
			strategicPlan.setUsers(usersImpl.gettUserById(usersSession.getUserId(), "userId"));
			strategicPlan.setUpdatedBy(usersSession.getFname()+" "+usersSession.getLname());
			strategicPlan.setRecordedDate(timestamp);
			strategicPlanImpl.saveStrategicPlan(strategicPlan);
			JSFMessagers.resetMessages();
			setValid(true);
			JSFMessagers.addErrorMessage(getProvider().getValue("com.save.form.strategicPlan"));
			LOGGER.info(CLASSNAME+":::StrategicPlan Details is saved");
			clearPlanFuileds();
			return"/menu/StrategicPlan.xhtml?faces-redirect=true";
			
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

public void clearPlanFuileds() {
	strategicPlan=new StrategicPlan();
	strategicPlanDetails=null;
}

public String newPlan() {
	return "/menu/InsertStrategicPlan.xhtml?faces-redirect=true";
}

	public void changeSelectBox(String name) {
		
		LOGGER.info("Ajax is working with listener::::::"+name);
	}
	public String saveAction(StrategicPlanDto strategicPlanDto) {
		LOGGER.info("update  saveAction method");
		//get all existing value but set "editable" to false 
		StrategicPlan act=new StrategicPlan();
		act=new StrategicPlan();
		act=strategicPlanImpl.getStrategicPlanById(strategicPlanDto.getStrategicPlanId(), "planId");
		
			LOGGER.info("here update sart for "+act +" strategicPlaniD "+act.getPlanId());

			strategicPlanDto.setEditable(false);
			act.setDetails(strategicPlanDto.getDetails());
		
			strategicPlanImpl.UpdateStrategicPlan(act);
			
		//return to current page
		return null;
		
	}

	public String cancel(StrategicPlanDto strategicPlanDto) {
		strategicPlanDto.setEditable(false);
		//usersImpl.UpdateUsers(user);
		return null;
		
		
	}

	public String editAction(StrategicPlanDto strategicPlanDto) {
	    
		strategicPlanDto.setEditable(true);
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

	public List<StrategicPlanDto> getStrategicPlanDtoDetails() {
		return strategicPlanDtoDetails;
	}

	public void setStrategicPlanDtoDetails(List<StrategicPlanDto> strategicPlanDtoDetails) {
		this.strategicPlanDtoDetails = strategicPlanDtoDetails;
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
