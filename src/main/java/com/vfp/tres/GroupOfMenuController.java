package com.vfp.tres;

import java.io.Serializable;
import java.util.logging.Logger;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.List;
import java.sql.Timestamp;
import javax.annotation.PostConstruct;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.ViewScoped;
import javax.faces.context.FacesContext;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;
import org.hibernate.HibernateException;
import tres.common.DbConstant;
import tres.common.JSFBoundleProvider;
import tres.common.JSFMessagers;
import tres.common.SessionUtils;
import tres.dao.impl.ListOfMenuImpl;
import tres.dao.impl.MenuAssignmentImpl;
import tres.dao.impl.MenuGroupImpl;
import tres.dao.impl.UserCategoryImpl;
import tres.domain.ListOfMenu;
import tres.domain.MenuAssignment;
import tres.domain.MenuGroup;
import tres.domain.UserCategory;
import tres.domain.Users;


@SuppressWarnings("unused")
@ManagedBean
@ViewScoped
public class GroupOfMenuController implements Serializable, DbConstant{

	private static final Logger LOGGER = Logger.getLogger(Thread.currentThread().getStackTrace()[0].getClassName());
	private String CLASSNAME = "GroupOfMenuController :: ";
	private static final long serialVersionUID = 1L;
	
	/* to manage validation messages */
	private boolean isValid;
	/* end manage validation messages */
	private String genericStatus;
	private String defaulGrouptMenu;
	private String menuGroupName;
	private String menuGroupCode;
	private UserCategory userCategory;
	private Users userSession;
	private MenuGroup menuGroup;
	private int categoryId;
	private int menuGroupId;
	

	private String defaultMenuUrl;
	private MenuAssignment menuAssignment;
	private ListOfMenu listOfMenu;
	private int menuAssignId;
	private int listOfMenuId;

	
	Timestamp timestamp = new Timestamp(Calendar.getInstance().getTime().getTime());
	
	private List<MenuGroup> menuGroupDetails = new ArrayList<MenuGroup>();
	private List<UserCategory> userCategoryDetails = new ArrayList<UserCategory>();
	
	private List<MenuAssignment> menuAssignmentDetails = new ArrayList<MenuAssignment>();
	private List<ListOfMenu> listOfMenuDetails = new ArrayList<ListOfMenu>();
	
	JSFBoundleProvider provider = new JSFBoundleProvider();
	UserCategoryImpl userCategoryImpl = new UserCategoryImpl();
	MenuGroupImpl menuGroupImpl = new MenuGroupImpl();
	
	ListOfMenuImpl listOfMenuImpl = new ListOfMenuImpl();
	MenuAssignmentImpl menuAssignmentImpl = new MenuAssignmentImpl();
	
	@SuppressWarnings("unchecked")
	@PostConstruct
	public void init() {
		HttpSession session = SessionUtils.getSession();
		userSession = (Users) session.getAttribute("userSession");

		if (menuGroup == null) {
			menuGroup = new MenuGroup();
		}
		if (menuAssignment == null) {
			menuAssignment = new MenuAssignment();
		}
		
		try {
			
			//userCategoryDetails = userCategoryImpl.getGenericListWithHQLParameter(new String[] { "genericStatus" },
				//	new Object[] { ACTIVE }, "UserCategory", "userCatid desc");
			userCategoryDetails = userCategoryImpl.getListWithHQL(SELECT_USERCATEGORY);
			listOfMenuDetails = listOfMenuImpl.getListWithHQL(SELECT_LISTOFMENU);
			
		} catch (Exception e) {
			setValid(false);
			JSFMessagers.addErrorMessage(getProvider().getValue("com.server.side.internal.error"));
			LOGGER.info(e.getMessage());
			e.printStackTrace();
		}

	}
	
	public String saveMenuGroupInfo() throws IOException {
		String url = getContextPath();
		System.out.print("+++++++++++++++++:" + url + userCategoryImpl.getUserCategoryById(categoryId, "userCatid").getUsercategoryName());
		try {
			
			MenuGroup menuG=new MenuGroup();
			
			menuG=
			menuGroupImpl.getModelWithMyHQL(new String[] { "groupMenuCode" },
						new Object[] {menuGroup.getGroupMenuCode() }, "from MenuGroup ");
			
					menuGroup.setCreatedBy(userSession.getViewId());
					menuGroup.setCrtdDtTime(timestamp);
					menuGroup.setCreationDate(timestamp);
					menuGroup.setGenericStatus(ACTIVE);
					menuGroup.setUpdatedBy(userSession.getViewId());
					menuGroup.setCrtdDtTime(timestamp);
					menuGroup.setUpDtTime(timestamp);
				
					menuGroup.setUserCategory(userCategoryImpl.getUserCategoryById(categoryId, "userCatid"));
					
					menuGroupImpl.saveMenuGroupt(menuGroup);

					JSFMessagers.resetMessages();
					setValid(true);
					JSFMessagers.addErrorMessage(getProvider().getValue("com.save.form.user"));
					LOGGER.info(CLASSNAME + ":::MenuGroup is saved");
					clearUserFuileds();
					return "";

		} catch (Exception ex) {
			LOGGER.info(CLASSNAME + ":::MenuGroup is fail with HibernateException  error");
			JSFMessagers.resetMessages();
			setValid(false);
			JSFMessagers.addErrorMessage(getProvider().getValue("com.server.side.internal.error"));
			LOGGER.info(CLASSNAME + "" + ex.getMessage());
			ex.printStackTrace();
		}
		return "";
	}

	public String saveMenuAssignmentInfo() throws IOException {
		String url = getContextPath();
		System.out.print("+++++++++++++++++:" + url + "/");
		try {
			
					menuAssignment.setCreatedBy(userSession.getViewId());
					menuAssignment.setCrtdDtTime(timestamp);
					menuAssignment.setGenericStatus(ACTIVE);
					menuAssignment.setUpdatedBy(userSession.getViewId());
					menuAssignment.setCrtdDtTime(timestamp);
					menuAssignment.setUpDtTime(timestamp);
					menuAssignment.setListOfMenu(listOfMenuImpl.getListOfMenuById( listOfMenuId, "menuId"));
					menuAssignment.setUserCategory(userCategoryImpl.getUserCategoryById(categoryId, "userCatid"));
					menuAssignmentImpl.saveMenuAssignment(menuAssignment);

					JSFMessagers.resetMessages();
					setValid(true);
					JSFMessagers.addErrorMessage(getProvider().getValue("com.save.form.user"));
					LOGGER.info(CLASSNAME + ":::MenuAssignment is saved");
					clearUserFuileds();
					return "";

		} catch (HibernateException ex) {
			LOGGER.info(CLASSNAME + ":::MenuAssignment is fail with HibernateException  error");
			JSFMessagers.resetMessages();
			setValid(false);
			JSFMessagers.addErrorMessage(getProvider().getValue("com.server.side.internal.error"));
			LOGGER.info(CLASSNAME + "" + ex.getMessage());
			ex.printStackTrace();
		}
		return "";
	}
	public void clearUserFuileds() {

		menuGroup = new MenuGroup();
		menuGroupDetails = null;
	}
	public void clearUserFuiledss() {

		menuAssignment = new MenuAssignment();
		menuAssignmentDetails = null;
	}
	public String getContextPath() {

		HttpServletRequest request = (HttpServletRequest) FacesContext.getCurrentInstance().getExternalContext()
				.getRequest();

		return request.getContextPath();
	}
	public boolean isValid() {
		return isValid;
	}

	public void setValid(boolean isValid) {
		this.isValid = isValid;
	}

	public String getGenericStatus() {
		return genericStatus;
	}

	public void setGenericStatus(String genericStatus) {
		this.genericStatus = genericStatus;
	}

	public String getDefaulGrouptMenu() {
		return defaulGrouptMenu;
	}

	public void setDefaulGrouptMenu(String defaulGrouptMenu) {
		this.defaulGrouptMenu = defaulGrouptMenu;
	}

	public String getMenuGroupName() {
		return menuGroupName;
	}

	public void setMenuGroupName(String menuGroupName) {
		this.menuGroupName = menuGroupName;
	}

	public UserCategory getUserCategory() {
		return userCategory;
	}

	public void setUserCategory(UserCategory userCategory) {
		this.userCategory = userCategory;
	}

	public List<UserCategory> getUserCategoryDetails() {
		return userCategoryDetails;
	}

	public void setUserCategoryDetails(List<UserCategory> userCategoryDetails) {
		this.userCategoryDetails = userCategoryDetails;
	}

	public JSFBoundleProvider getProvider() {
		return provider;
	}

	public void setProvider(JSFBoundleProvider provider) {
		this.provider = provider;
	}

	public UserCategoryImpl getUserCategoryImpl() {
		return userCategoryImpl;
	}

	public void setUserCategoryImpl(UserCategoryImpl userCategoryImpl) {
		this.userCategoryImpl = userCategoryImpl;
	}

	public static Logger getLogger() {
		return LOGGER;
	}

	public String getCLASSNAME() {
		return CLASSNAME;
	}

	public void setCLASSNAME(String cLASSNAME) {
		CLASSNAME = cLASSNAME;
	}

	public Users getUserSession() {
		return userSession;
	}

	public void setUserSession(Users userSession) {
		this.userSession = userSession;
	}

	public MenuGroup getMenuGroup() {
		return menuGroup;
	}

	public void setMenuGroup(MenuGroup menuGroup) {
		this.menuGroup = menuGroup;
	}

	public Timestamp getTimestamp() {
		return timestamp;
	}

	public void setTimestamp(Timestamp timestamp) {
		this.timestamp = timestamp;
	}

	public int getCategoryId() {
		return categoryId;
	}

	public void setCategoryId(int categoryId) {
		this.categoryId = categoryId;
	}

	public List<MenuGroup> getMenuGroupDetails() {
		return menuGroupDetails;
	}

	public void setMenuGroupDetails(List<MenuGroup> menuGroupDetails) {
		this.menuGroupDetails = menuGroupDetails;
	}

	public MenuGroupImpl getMenuGroupImpl() {
		return menuGroupImpl;
	}

	public void setMenuGroupImpl(MenuGroupImpl menuGroupImpl) {
		this.menuGroupImpl = menuGroupImpl;
	}

	public String getMenuGroupCode() {
		return menuGroupCode;
	}

	public void setMenuGroupCode(String menuGroupCode) {
		this.menuGroupCode = menuGroupCode;
	}

	public String getDefaultMenuUrl() {
		return defaultMenuUrl;
	}

	public void setDefaultMenuUrl(String defaultMenuUrl) {
		this.defaultMenuUrl = defaultMenuUrl;
	}

	public MenuAssignment getMenuAssignment() {
		return menuAssignment;
	}

	public void setMenuAssignment(MenuAssignment menuAssignment) {
		this.menuAssignment = menuAssignment;
	}

	public ListOfMenu getListOfMenu() {
		return listOfMenu;
	}

	public void setListOfMenu(ListOfMenu listOfMenu) {
		this.listOfMenu = listOfMenu;
	}

	public int getMenuAssignId() {
		return menuAssignId;
	}

	public void setMenuAssignId(int menuAssignId) {
		this.menuAssignId = menuAssignId;
	}

	public int getListOfMenuId() {
		return listOfMenuId;
	}

	public void setListOfMenuId(int listOfMenuId) {
		this.listOfMenuId = listOfMenuId;
	}

	public List<MenuAssignment> getMenuAssignmentDetails() {
		return menuAssignmentDetails;
	}

	public void setMenuAssignmentDetails(List<MenuAssignment> menuAssignmentDetails) {
		this.menuAssignmentDetails = menuAssignmentDetails;
	}

	public List<ListOfMenu> getListOfMenuDetails() {
		return listOfMenuDetails;
	}

	public void setListOfMenuDetails(List<ListOfMenu> listOfMenuDetails) {
		this.listOfMenuDetails = listOfMenuDetails;
	}

	public ListOfMenuImpl getListOfMenuImpl() {
		return listOfMenuImpl;
	}

	public void setListOfMenuImpl(ListOfMenuImpl listOfMenuImpl) {
		this.listOfMenuImpl = listOfMenuImpl;
	}

	public MenuAssignmentImpl getMenuAssignmentImpl() {
		return menuAssignmentImpl;
	}

	public void setMenuAssignmentImpl(MenuAssignmentImpl menuAssignmentImpl) {
		this.menuAssignmentImpl = menuAssignmentImpl;
	}

	public int getMenuGroupId() {
		return menuGroupId;
	}

	public void setMenuGroupId(int menuGroupId) {
		this.menuGroupId = menuGroupId;
	}
	
	
	

}
