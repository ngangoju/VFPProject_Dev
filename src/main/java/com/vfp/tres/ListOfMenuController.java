package com.vfp.tres;

import java.io.Serializable;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.ViewScoped;
import tres.common.DbConstant;
import java.util.logging.Logger;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.List;
import java.sql.Timestamp;
import javax.annotation.PostConstruct;
import javax.faces.context.FacesContext;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;
import org.hibernate.HibernateException;
import tres.common.JSFBoundleProvider;
import tres.common.JSFMessagers;
import tres.common.SessionUtils;
import tres.dao.impl.ListOfMenuImpl;
import tres.dao.impl.MenuGroupImpl;
import tres.dao.impl.UserCategoryImpl;
import tres.domain.ListOfMenu;
import tres.domain.MenuGroup;
import tres.domain.UserCategory;
import tres.domain.Users;

@SuppressWarnings("unused")
@ManagedBean
@ViewScoped
public class ListOfMenuController implements Serializable, DbConstant{

	/**
	 * 
	 */
	private static final Logger LOGGER = Logger.getLogger(Thread.currentThread().getStackTrace()[0].getClassName());
	private String CLASSNAME = "ListOfMenuController :: ";
	private static final long serialVersionUID = 1L;
	
	/* to manage validation messages */
	private boolean isValid;
	/* end manage validation messages */
	private String genericStatus;
	private String description;
	private String urlName;
	private String urlCode;
	private MenuGroup menuGroup;
	private ListOfMenu listOfMenu;
	private Users userSession;
	private Users users;
	private int groupId;
	
	Timestamp timestamp = new Timestamp(Calendar.getInstance().getTime().getTime());
	
	private List<ListOfMenu> listOfMenuDetails = new ArrayList<ListOfMenu>();
	private List<MenuGroup> menuGroupDetails = new ArrayList<MenuGroup>();
	
	JSFBoundleProvider provider = new JSFBoundleProvider();
	ListOfMenuImpl listOfMenuImpl = new ListOfMenuImpl();
	MenuGroupImpl menuGroupImpl = new MenuGroupImpl();
	
	@SuppressWarnings("unchecked")
	@PostConstruct
	public void init() {
		HttpSession session = SessionUtils.getSession();
		userSession = (Users) session.getAttribute("userSession");

		if (listOfMenu == null) {
			listOfMenu = new ListOfMenu();
		}
		
		try {
			
			//userCategoryDetails = userCategoryImpl.getGenericListWithHQLParameter(new String[] { "genericStatus" },
				//	new Object[] { ACTIVE }, "UserCategory", "userCatid desc");
			menuGroupDetails = menuGroupImpl.getListWithHQL(SELECT_MENUGROUP);
			
		} catch (Exception e) {
			setValid(false);
			JSFMessagers.addErrorMessage(getProvider().getValue("com.server.side.internal.error"));
			LOGGER.info(e.getMessage());
			e.printStackTrace();
		}

	}
	
	public String saveListMenuInfo() throws IOException {
		String url = getContextPath();
		System.out.print("+++++++++++++++++:" + url + "/");
		try {
			
					listOfMenu.setCreatedBy(userSession.getViewId());
					listOfMenu.setCrtdDtTime(timestamp);
					listOfMenu.setCreationDate(timestamp);
					listOfMenu.setGenericStatus(ACTIVE);
					listOfMenu.setUpdatedBy(userSession.getViewId());
					listOfMenu.setCrtdDtTime(timestamp);
					listOfMenu.setUpDtTime(timestamp);
					listOfMenu.setMenuGroup(menuGroupImpl.getMenuGroupById(groupId, "menuGroupId"));
					listOfMenuImpl.saveListOfMenu(listOfMenu);

					JSFMessagers.resetMessages();
					setValid(true);
					JSFMessagers.addErrorMessage(getProvider().getValue("com.save.form.user"));
					LOGGER.info(CLASSNAME + ":::List of Menu is saved");
					clearUserFuileds();
					return "";

		} catch (HibernateException ex) {
			LOGGER.info(CLASSNAME + ":::List of Menu is fail to be sved with HibernateException  error");
			JSFMessagers.resetMessages();
			setValid(false);
			JSFMessagers.addErrorMessage(getProvider().getValue("com.server.side.internal.error"));
			LOGGER.info(CLASSNAME + "" + ex.getMessage());
			ex.printStackTrace();
		}
		return "";
	}
	
	
	

	private void clearUserFuileds() {
		listOfMenu = new ListOfMenu();
		listOfMenuDetails = null;
	}

	private String getContextPath() {
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

	public String getDescription() {
		return description;
	}

	public void setDescription(String description) {
		this.description = description;
	}

	public String getUrlName() {
		return urlName;
	}

	public void setUrlName(String urlName) {
		this.urlName = urlName;
	}

	public String getUrlCode() {
		return urlCode;
	}

	public void setUrlCode(String urlCode) {
		this.urlCode = urlCode;
	}

	public MenuGroup getMenuGroup() {
		return menuGroup;
	}

	public void setMenuGroup(MenuGroup menuGroup) {
		this.menuGroup = menuGroup;
	}

	public ListOfMenu getListOfMenu() {
		return listOfMenu;
	}

	public void setListOfMenu(ListOfMenu listOfMenu) {
		this.listOfMenu = listOfMenu;
	}

	public Users getUserSession() {
		return userSession;
	}

	public void setUserSession(Users userSession) {
		this.userSession = userSession;
	}

	public int getGroupId() {
		return groupId;
	}

	public void setGroupId(int groupId) {
		this.groupId = groupId;
	}

	public Timestamp getTimestamp() {
		return timestamp;
	}

	public void setTimestamp(Timestamp timestamp) {
		this.timestamp = timestamp;
	}

	public List<ListOfMenu> getListOfMenuDetails() {
		return listOfMenuDetails;
	}

	public void setListOfMenuDetails(List<ListOfMenu> listOfMenuDetails) {
		this.listOfMenuDetails = listOfMenuDetails;
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

	public ListOfMenuImpl getListOfMenuImpl() {
		return listOfMenuImpl;
	}

	public void setListOfMenuImpl(ListOfMenuImpl listOfMenuImpl) {
		this.listOfMenuImpl = listOfMenuImpl;
	}

	public MenuGroupImpl getMenuGroupImpl() {
		return menuGroupImpl;
	}

	public void setMenuGroupImpl(MenuGroupImpl menuGroupImpl) {
		this.menuGroupImpl = menuGroupImpl;
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

	public Users getUsers() {
		return users;
	}

	public void setUsers(Users users) {
		this.users = users;
	}
	
	
}
