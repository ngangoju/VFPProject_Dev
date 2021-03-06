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
import tres.dao.impl.ContactImpl;
import tres.dao.impl.ListOfMenuImpl;
import tres.dao.impl.LoginImpl;
import tres.dao.impl.MenuAssignmentImpl;
import tres.dao.impl.MenuGroupImpl;
import tres.dao.impl.UserCategoryImpl;
import tres.dao.impl.UserImpl;
import tres.domain.Contact;
import tres.domain.ListOfMenu;
import tres.domain.MenuAssignment;
import tres.domain.MenuGroup;
import tres.domain.UserCategory;
import tres.domain.Users;
import tres.vfp.dto.MenuGroupDto;
import tres.vfp.dto.UserDto;
@ManagedBean
@ViewScoped
public class GroupOfMenuController implements Serializable, DbConstant {

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
	private Users userSessions;
	private MenuGroup menuGroup;
	private MenuGroup menuGroupSession;
	private MenuGroupDto menuGroupDto;
	private int categoryId;
	private int menuGroupId;

	private String defaultMenuUrl;
	private MenuAssignment menuAssignment;
	private ListOfMenu listOfMenu;
	private int menuAssignId;
	private int listOfMenuId;
	private String fname;
	private String lname;
	private String email;
	private String username;
	private int userId;
	
	private Users users;

	private Contact contact;

	Timestamp timestamp = new Timestamp(Calendar.getInstance().getTime().getTime());

	private List<MenuGroup> menuGroupDetails = new ArrayList<MenuGroup>();
	private List<UserCategory> userCategoryDetails = new ArrayList<UserCategory>();

	private List<MenuAssignment> menuAssignmentDetails = new ArrayList<MenuAssignment>();
	private List<ListOfMenu> listOfMenuDetails = new ArrayList<ListOfMenu>();

	private List<MenuGroupDto> menuGroupDtoDetails = new ArrayList<MenuGroupDto>();

	JSFBoundleProvider provider = new JSFBoundleProvider();
	UserCategoryImpl userCategoryImpl = new UserCategoryImpl();
	MenuGroupImpl menuGroupImpl = new MenuGroupImpl();
	UserImpl userImpl = new UserImpl();
	ContactImpl contactImpl = new ContactImpl();

	ListOfMenuImpl listOfMenuImpl = new ListOfMenuImpl();
	LoginImpl loginImpl = new LoginImpl();
	MenuAssignmentImpl menuAssignmentImpl = new MenuAssignmentImpl();

	@SuppressWarnings("unchecked")
	@PostConstruct
	public void init() {
		HttpSession session = SessionUtils.getSession();
		userSessions = (Users) session.getAttribute("userSession");

		menuGroupSession = (MenuGroup) session.getAttribute("menuGroupSession");

		if (menuGroup == null) {
			menuGroup = new MenuGroup();
		}
		if (menuAssignment == null) {
			menuAssignment = new MenuAssignment();
		}
		if (userCategory == null) {
			userCategory = new UserCategory();

		}
		if (menuGroupDto == null) {
			menuGroupDto = new MenuGroupDto();
		}
		if (users == null) {
			users = new Users();
		}

		try {
			userCategoryDetails = userCategoryImpl.getListWithHQL(SELECT_USERCATEGORY);
			// MenuGroup menu = new MenuGroup();
			// menu = new MenuGroup();
			// MenuGroup menu =
			// menuGroupImpl.getMenuGroupById(menuGroupSession.getMenuGroupId(),
			// "menuGroupId");
			//
			// MenuGroupDto menuGroupDto = new MenuGroupDto();
			// menuGroupDto.setEditable(false);
			// menuGroupDto.setMenuGroupName(menu.getMenuGroupName());
			// menuGroupDto.setDefaultGroupMenu(menu.getDefaulGrouptMenu());
			// menuGroupDto.setUserCategory(menu.getUserCategory());
			// menuGroupDtoDetails.add(menuGroupDto);
			// // below list concern list of all users by changing their status
			menuGroupDetails = menuGroupImpl.getListWithHQL(SELECT_MENUGROUP);

			userCategoryDetails = userCategoryImpl.getListWithHQL(SELECT_USERCATEGORY);

			for (MenuGroup menuGroups : menuGroupDetails) {
				MenuGroupDto menuGroupDtos = new MenuGroupDto();
				menuGroupDtos.setMenuGroupId(menuGroups.getMenuGroupId());
				menuGroupDtos.setEditable(false);
				menuGroupDtos.setMenuGroupName(menuGroups.getMenuGroupName());
				menuGroupDtos.setDefaultGroupMenu(menuGroups.getDefaulGrouptMenu());
				menuGroupDtos.setUserCategory(menuGroups.getUserCategory());
				menuGroupDtos.setGenericStatus(menuGroups.getGenericStatus());
				if (menuGroups.getGenericStatus().equals(ACTIVE)) {
					menuGroupDtos.setAction(DESACTIVE);

				} else if (menuGroups.getGenericStatus().equals(DESACTIVE)) {

					menuGroupDtos.setAction(ACTIVE);
					menuGroups.setGenericStatus(DESACTIVE);

				} else {
					menuGroupDtos.setAction(DESACTIVE);
					menuGroups.setGenericStatus(ACTIVE);

				}
				menuGroupDtoDetails.add(menuGroupDtos);

			}
		} catch (Exception e) {
			setValid(false);
			JSFMessagers.addErrorMessage(getProvider().getValue("com.server.side.internal.error"));
			LOGGER.info(e.getMessage());
			e.printStackTrace();
		}

		try {

			// userCategoryDetails = userCategoryImpl.getGenericListWithHQLParameter(new
			// String[] { "genericStatus" },
			// new Object[] { ACTIVE }, "UserCategory", "userCatid desc");
			userCategoryDetails = userCategoryImpl.getListUsercategory();
			listOfMenuDetails = listOfMenuImpl.getListListOfMenus();

		} catch (Exception e) {
			setValid(false);
			JSFMessagers.addErrorMessage(getProvider().getValue("com.server.side.internal.error"));
			LOGGER.info(e.getMessage());
			e.printStackTrace();
		}

	}

	public String resetPassword() throws IOException{
		try {
			LOGGER.info("returing values controller EMILE EMILE EMILE EMILE EMILE EMILE EMILE EMILE EMILE EMILE");
			Users user = new Users();
			Contact ct = new Contact();

			SendSupportEmail support = new SendSupportEmail();

			// user=userImpl.getModelWithMyHQL(new String[]{"userId"}, new object []
			// {users.getViewId()}, "from Users");
			//users.setFname(getFname());
			//users.setLname(getLname());
			//users.setViewId(getUsername());

			//ct = contactImpl.getModelWithMyHQL(new String[] { "email" }, new Object[] { email }, "from Contact ");
			
			user = userImpl.getModelWithMyHQL(new String[] { "viewId","fname","lname" }, new Object[] { username,fname,lname}, "from Users");
			if (null != user) {
				
				LOGGER.info("returing values controller EMILE EMILE EMILE EMILE EMILE EMILE EMILE EMILE EMILE EMILE");
				boolean verifyemail = support.sendResetMail(fname, lname, email);
				// ::: end sending email action:::::::::::://
				if (verifyemail) {
					
//						UserDto user2=new UserDto();
						LOGGER.info("update  saveAction method");
						// get all existing value but set "editable" to false
						Users users = new Users();
						users = userImpl.gettUserById(user.getUserId(), "userId");

						LOGGER.info("here update sart for " + users + " userId " + users.getUserId());

						if (user.getViewName()!=null) {
							LOGGER.info("irya mbere "+user.getViewName());
							users.setViewName(loginImpl.criptPassword("Rp4TOKRW"));
							LOGGER.info("IZINA RISHYA");
							userImpl.UpdateUsers(users);
							LOGGER.info("IRYANYUMA: " + users.getViewName());
						} 
						
					
					LOGGER.info("returing values controller" + verifyemail);
					setValid(true);
					JSFMessagers.addErrorMessage(getProvider().getValue("com.server.side.email.notifications"));
					LOGGER.info(CLASSNAME + ":::Contact Details is saved"+email);
				} else {
					LOGGER.info(CLASSNAME + ":::Contact Details is not saved, HITIMANA");
					JSFMessagers.resetMessages();
					setValid(false);
					JSFMessagers.addErrorMessage(getProvider().getValue("com.server.side.internal.notificationError"));
					LOGGER.info(CLASSNAME + ":::Contact Details is not saved"+email);

				}
			} else {
				LOGGER.info(CLASSNAME + ":::Dtata not found HITIMANA HITIMANA HITIMANA HITIMANA");
				JSFMessagers.resetMessages();
				setValid(false);
				JSFMessagers.addErrorMessage(getProvider().getValue("error.server.side.notfound.email"));
				LOGGER.info(CLASSNAME + "sivaserside validation :: email already  recorded in the system! ");
			}
		} catch (Exception ex) {
			LOGGER.info(CLASSNAME + ":::MenuGroup is fail with HibernateException  error");
			JSFMessagers.resetMessages();
			setValid(false);
			JSFMessagers.addErrorMessage(getProvider().getValue("com.server.side.internal.error"));
			LOGGER.info(CLASSNAME + "" + ex.getMessage());
			ex.printStackTrace();
		}
		return null;
	}

	public String saveMenuGroupInfo() throws IOException {
		String url = getContextPath();
		System.out.print("+++++++++++++++++:" + url
				+ userCategoryImpl.getUserCategoryById(categoryId, "userCatid").getUsercategoryName());
		try {

			MenuGroup menuG = new MenuGroup();

			menuG = menuGroupImpl.getModelWithMyHQL(new String[] { "groupMenuCode" },
					new Object[] { menuGroup.getGroupMenuCode() }, "from MenuGroup ");

			menuGroup.setCreatedBy(userSessions.getViewId());
			menuGroup.setCrtdDtTime(timestamp);
			menuGroup.setCreationDate(timestamp);
			menuGroup.setGenericStatus(ACTIVE);
			menuGroup.setUpdatedBy(userSessions.getViewId());
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

			menuAssignment.setCreatedBy(userSessions.getViewId());
			menuAssignment.setCrtdDtTime(timestamp);
			menuAssignment.setGenericStatus(ACTIVE);
			menuAssignment.setUpdatedBy(userSessions.getViewId());
			menuAssignment.setCrtdDtTime(timestamp);
			menuAssignment.setUpDtTime(timestamp);
			menuAssignment.setListOfMenu(listOfMenuImpl.getListOfMenuById(listOfMenuId, "menuId"));
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

	public String cancel(MenuGroupDto menu) {
		menu.setEditable(false);
		// usersImpl.UpdateUsers(user);
		return null;

	}

	public String editAction(MenuGroupDto menu) {

		menu.setEditable(true);
		// usersImpl.UpdateUsers(user);
		return null;
	}

	public String saveAction(MenuGroupDto menu) {
		LOGGER.info("update  saveAction method");
		/* System.out.println("**************update  saveAction method"); */
		// get all existing value but set "editable" to false

		if (menu != null) {

			MenuGroup menus = new MenuGroup();
			menus = new MenuGroup();
			menus = menuGroupImpl.getMenuGroupById(menu.getMenuGroupId(), "menuGroupId");

			LOGGER.info("here update sart for " + menus + " menuGroupId " + menus.getMenuGroupId());
			System.out.println("++++++++++++++++++++++++++here update sart for " + menus + " menuGroupId "
					+ menus.getMenuGroupId());
			menu.setEditable(false);
			menus.setMenuGroupName(menu.getMenuGroupName());
			menus.setDefaulGrouptMenu(menu.getDefaultGroupMenu());
			menus.setUserCategory(menu.getUserCategory());
			menuGroupImpl.updateMenuGroup(menus);

			// return to current page
			return null;
		} else {
			JSFMessagers.resetMessages();
			setValid(false);
			JSFMessagers.addErrorMessage(getProvider().getValue("com.save.form.userprofile"));
			return null;
		}

	}

	public String newAction(MenuGroupDto menu) {
		LOGGER.info("update  saveAction method");
		// get all existing value but set "editable" to false
		MenuGroup menus = new MenuGroup();
		menus = new MenuGroup();
		menus = menuGroupImpl.getMenuGroupById(menu.getMenuGroupId(), "menuGroupId");

		LOGGER.info("here update sart for " + menus + " menuGroupId " + menus.getMenuGroupId());

		menu.setEditable(false);
		menus.setMenuGroupName(menu.getMenuGroupName());
		menus.setDefaulGrouptMenu(menu.getDefaultGroupMenu());
		menus.setUserCategory(menu.getUserCategory());
		menuGroupImpl.updateMenuGroup(menus);

		// return to current page
		return "/menu/ViewMenuGroup.xhtml?faces-redirect=true";

	}
	
	

	public String updateStatus(MenuGroupDto menu) {
		LOGGER.info("update  saveAction method");
		// get all existing value but set "editable" to false
		MenuGroup menus = new MenuGroup();
		menus = new MenuGroup();
		menus = menuGroupImpl.getMenuGroupById(menu.getMenuGroupId(), "menuGroupId");

		LOGGER.info("here update sart for " + menus + " menuGroupId " + menus.getMenuGroupId());

		if (menu.getGenericStatus().equals(ACTIVE)) {

			menus.setGenericStatus(DESACTIVE);
			menuGroupImpl.updateMenuGroup(menus);
		} else {

			menus.setGenericStatus(ACTIVE);
		}
		menuGroupImpl.updateMenuGroup(menus);

		// return to current page
		return "/menu/ViewMenuGroup.xhtml?faces-redirect=true";

	}

	public String addNewMenuGroup() {

		return "/menu/menuGroupForm.xhtml?faces-redirect=true";

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

	public Users getuserSessions() {
		return userSessions;
	}

	public void setuserSessions(Users userSessions) {
		this.userSessions = userSessions;
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

	public List<MenuGroupDto> getMenuGroupDtoDetails() {
		return menuGroupDtoDetails;
	}

	public void setMenuGroupDtoDetails(List<MenuGroupDto> menuGroupDtoDetails) {
		this.menuGroupDtoDetails = menuGroupDtoDetails;
	}

	public MenuGroup getMenuGroupSession() {
		return menuGroupSession;
	}

	public void setMenuGroupSession(MenuGroup menuGroupSession) {
		this.menuGroupSession = menuGroupSession;
	}

	public MenuGroupDto getMenuGroupDto() {
		return menuGroupDto;
	}

	public void setMenuGroupDto(MenuGroupDto menuGroupDto) {
		this.menuGroupDto = menuGroupDto;
	}

	public String getFname() {
		return fname;
	}

	public void setFname(String fname) {
		this.fname = fname;
	}

	public String getLname() {
		return lname;
	}

	public void setLname(String lname) {
		this.lname = lname;
	}

	public String getEmail() {
		return email;
	}

	public void setEmail(String email) {
		this.email = email;
	}

	public String getUsername() {
		return username;
	}

	public void setUsername(String username) {
		this.username = username;
	}

	public Users getUsers() {
		return users;
	}

	public void setUsers(Users users) {
		this.users = users;
	}

	public UserImpl getUserImpl() {
		return userImpl;
	}

	public void setUserImpl(UserImpl userImpl) {
		this.userImpl = userImpl;
	}

	public Contact getContact() {
		return contact;
	}

	public void setContact(Contact contact) {
		this.contact = contact;
	}

	public int getUserId() {
		return userId;
	}

	public void setUserId(int userId) {
		this.userId = userId;
	}

	public ContactImpl getContactImpl() {
		return contactImpl;
	}

	public void setContactImpl(ContactImpl contactImpl) {
		this.contactImpl = contactImpl;
	}

	public LoginImpl getLoginImpl() {
		return loginImpl;
	}

	public void setLoginImpl(LoginImpl loginImpl) {
		this.loginImpl = loginImpl;
	}
	

}
