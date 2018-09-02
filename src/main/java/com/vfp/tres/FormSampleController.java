


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
import tres.dao.impl.UserImpl;
import tres.domain.MenuAssignment;
import tres.domain.MenuGroup;
import tres.domain.Users;

@ManagedBean
@ViewScoped
public class FormSampleController implements Serializable, DbConstant {
	private static final Logger LOGGER = Logger.getLogger(Thread.currentThread().getStackTrace()[0].getClassName());
	private String CLASSNAME = "FormSampleController :: ";
	private static final long serialVersionUID = 1L;
	/*to manage validation messages*/
	private boolean isValid;
	/*end  manage validation messages*/
	private Users users;
	private MenuAssignment menuAssignment;
	private MenuGroup menuGroup;
	
	private List<MenuGroup> menuGroupDetails = new ArrayList<MenuGroup>();
	
	/*class injection*/
	
	JSFBoundleProvider provider = new JSFBoundleProvider();
	UserImpl usersImpl = new UserImpl();
	MenuAssignmentImpl menuAssignmentImpl=new MenuAssignmentImpl();
	MenuGroupImpl menuGroupImpl=new MenuGroupImpl();
	
	/*end class injection*/
	Timestamp timestamp = new Timestamp(Calendar.getInstance().getTime().getTime());
	
	@SuppressWarnings("unchecked")
	@PostConstruct
	public void init() {
		HttpSession session = SessionUtils.getSession();
		if (users == null) {
			users = new Users();
		}
		
		if (menuGroup == null) {
			menuGroup = new MenuGroup();
		}
		
		try {
			menuGroupDetails=menuGroupImpl.getGenericListWithHQLParameter(new String[] {"genericStatus"},new Object[] {ACTIVE}, "MenuGroup", "menuGroupId asc");
		} catch (Exception e) {
			setValid(false);
			JSFMessagers.addErrorMessage(getProvider().getValue("com.server.side.internal.error"));
			LOGGER.info(e.getMessage());
			e.printStackTrace();
		}

	
		
	}
	
	public void saveData() {
		LOGGER.info(CLASSNAME + "testing save methode ");
		JSFMessagers.resetMessages();
		setValid(false);
		JSFMessagers.addErrorMessage(getProvider().getValue("com.server.side.internal.error"));
		
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

	public Users getUsers() {
		return users;
	}

	public void setUsers(Users users) {
		this.users = users;
	}

	public MenuAssignment getMenuAssignment() {
		return menuAssignment;
	}

	public void setMenuAssignment(MenuAssignment menuAssignment) {
		this.menuAssignment = menuAssignment;
	}

	public MenuGroup getMenuGroup() {
		return menuGroup;
	}

	public void setMenuGroup(MenuGroup menuGroup) {
		this.menuGroup = menuGroup;
	}

	public List<MenuGroup> getMenuGroupDetails() {
		return menuGroupDetails;
	}

	public void setMenuGroupDetails(List<MenuGroup> menuGroupDetails) {
		this.menuGroupDetails = menuGroupDetails;
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

	public MenuAssignmentImpl getMenuAssignmentImpl() {
		return menuAssignmentImpl;
	}

	public void setMenuAssignmentImpl(MenuAssignmentImpl menuAssignmentImpl) {
		this.menuAssignmentImpl = menuAssignmentImpl;
	}

	public MenuGroupImpl getMenuGroupImpl() {
		return menuGroupImpl;
	}

	public void setMenuGroupImpl(MenuGroupImpl menuGroupImpl) {
		this.menuGroupImpl = menuGroupImpl;
	}

	public Timestamp getTimestamp() {
		return timestamp;
	}

	public void setTimestamp(Timestamp timestamp) {
		this.timestamp = timestamp;
	}

}
