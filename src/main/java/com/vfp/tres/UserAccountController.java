package com.vfp.tres;

import java.awt.image.renderable.RenderedImageFactory;
import java.io.IOException;
import java.io.InputStream;
import java.io.Serializable;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.security.NoSuchAlgorithmException;
import java.sql.Timestamp;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.List;
import java.util.UUID;
import java.util.logging.Logger;

import javax.annotation.PostConstruct;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.ViewScoped;
import javax.faces.context.FacesContext;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;

import org.hibernate.HibernateException;
import org.primefaces.model.UploadedFile;

import tres.common.DbConstant;
import tres.common.Formating;
import tres.common.JSFBoundleProvider;
import tres.common.JSFMessagers;
import tres.common.SessionUtils;
import tres.dao.impl.BoardImpl;
import tres.dao.impl.CellImpl;
import tres.dao.impl.ContactImpl;
import tres.dao.impl.DistrictImpl;
import tres.dao.impl.LoginImpl;
import tres.dao.impl.ProvinceImpl;
import tres.dao.impl.SectorImpl;
import tres.dao.impl.UserCategoryImpl;
import tres.dao.impl.UserImpl;
import tres.dao.impl.VillageImpl;
import tres.domain.Board;
import tres.domain.Cell;
import tres.domain.Contact;
import tres.domain.District;
import tres.domain.Province;
import tres.domain.Sector;
import tres.domain.UserCategory;
import tres.domain.Users;
import tres.domain.Village;
import tres.vfp.dto.ContactDto;
import tres.vfp.dto.UserCategoryDto;
import tres.vfp.dto.UserDto;

@SuppressWarnings("unused")
@ManagedBean
@ViewScoped
public class UserAccountController implements Serializable, DbConstant {
	private static final Logger LOGGER = Logger.getLogger(Thread.currentThread().getStackTrace()[0].getClassName());
	private String CLASSNAME = "UserAccountController :: ";
	private static final long serialVersionUID = 1L;
	/* to manage validation messages */
	private boolean isValid;
	/* end manage validation messages */
	private UploadedFile imageUpload;
	private String imageName;
	private Users users;
	private Province province;
	private District district;
	private Sector sector;
	private Cell cell;
	private Village village;
	private Board board;
	private UserCategory usercat;
	private Users usersSession;
	private int userIdNumber;
	private int provinceId;
	private int districtId;
	private int sectorId;
	private int cellId;
	private int villageId;
	private int categoryId;
	private int boardId;
	private String password;
	private String confirmPswd;
	private String useremail;
	private UserDto userDto;
	private List<Users> usersDetails = new ArrayList<Users>();
	private List<Users> useravail = new ArrayList<Users>();
	private List<UserCategory> catDetails = new ArrayList<UserCategory>();
	private List<Province> provinceList = new ArrayList<Province>();
	private List<District> districtList = new ArrayList<District>();
	private List<Sector> sectorList = new ArrayList<Sector>();
	private List<Cell> celList = new ArrayList<Cell>();
	private List<Village> villageList = new ArrayList<Village>();
	private List<Board> boardList = new ArrayList<Board>();

	private List<District> districtByProv = new ArrayList<District>();
	private List<Sector> sectorByDistrict = new ArrayList<Sector>();
	private List<Cell> cellBySector = new ArrayList<Cell>();
	private List<Village> villageByCell = new ArrayList<Village>();
	private List<UserDto> userDtoDetails = new ArrayList<UserDto>();
	private List<UserDto> userDtosDetails = new ArrayList<UserDto>();
	List<ContactDto> contactDtoDetails = new ArrayList<ContactDto>();
	private List<UserDto> repDtosDetails = new ArrayList<UserDto>();
	private List<UserCategory> userCatDetails = new ArrayList<UserCategory>();
	private List<Contact> contactDetails = new ArrayList<Contact>();
	/* class injection */
	JSFBoundleProvider provider = new JSFBoundleProvider();
	UserImpl usersImpl = new UserImpl();
	ProvinceImpl provImpl = new ProvinceImpl();
	DistrictImpl districtImpl = new DistrictImpl();
	SectorImpl sectorImpl = new SectorImpl();
	CellImpl cellImpl = new CellImpl();
	VillageImpl villageImpl = new VillageImpl();
	UserCategoryImpl catImpl = new UserCategoryImpl();
	BoardImpl boardImpl = new BoardImpl();
	Timestamp timestamp = new Timestamp(Calendar.getInstance().getTime().getTime());
	LoginImpl loginImpl = new LoginImpl();
	private String choice;
	private Contact contact;
	private ContactDto contactDto;
	private boolean rendered;
	private boolean renderForeignCountry;
	private boolean rendersaveButton;
	private boolean renderprofile;
	private String option;
	private String selection;
	private Date dateofBirth;
	private int age;
	private int days;
	private int count;
	private int listrepSize;
	private int contactSize;
	private int repavail;
	private int userCatid;
	private Date to;
	private Date from;
	private boolean renderDataTable;
	private boolean nextButoon;
	private String redirect;
	private String range;
	private boolean renderBoard;
	private boolean renderDatePanel;
	private boolean renderEditedTableByDate;
	private boolean renderEditedTableByBoard;
	private boolean renderBoardOption;
	private boolean renderHeading;
	private boolean renderRepTable;
	private boolean renderRepContactDash;
	private boolean availrepSize;
	private boolean renderContactForm;
	private boolean renderRepContactAvailTable;
	private boolean renderOtherContForm;
	ContactImpl contactImpl = new ContactImpl();

	@SuppressWarnings({ "unchecked" })
	@PostConstruct
	public void init() {
		HttpSession session = SessionUtils.getSession();
		usersSession = (Users) session.getAttribute("userSession");

		if (users == null) {
			users = new Users();
		}
		if (province == null) {

			province = new Province();
		}
		if (cell == null) {
			cell = new Cell();
		}

		if (district == null) {
			district = new District();
		}
		if (village == null) {

			village = new Village();
		}
		if (sector == null) {

			sector = new Sector();
		}
		if (usercat == null) {
			usercat = new UserCategory();

		}
		if (board == null) {

			board = new Board();
		}
		if (userDto == null) {
			userDto = new UserDto();
		}
		if (contact == null) {
			contact = new Contact();
		}
		if (contactDto == null) {
			contactDto = new ContactDto();
		}

		try {
			provinceList = provImpl.getListWithHQL(SELECT_PROVINCE);
			boardList = boardImpl.getGenericListWithHQLParameter(new String[] { "genericStatus" },
					new Object[] { ACTIVE }, "Board", "boardId desc");
			Users user = usersImpl.gettUserById(usersSession.getUserId(), "userId");

			UserDto userDto = new UserDto();
			userDto.setEditable(false);
			userDto.setFname(user.getFname());
			userDto.setLname(user.getLname());
			userDto.setViewId(user.getViewId());
			userDto.setAddress(user.getAddress());
			userDto.setUserId(user.getUserId());
			userDto.setUserCategory(user.getUserCategory());
			userDto.setLoginStatus(user.getLoginStatus());
			userDto.setGender(user.getGender());
			userDtoDetails.add(userDto);
			repDtosDetails = displayRepresentativeByDateBetween();
			this.renderRepContactDash = true;
			listrepSize = showAvailRep();
			for (Object[] data : usersImpl.reportList(
					"select us.fname,us.lname, us.viewId,us.address, us.userId from Contact co right  join  co.user us join us.userCategory cat where co.user is null and cat.usercategoryName='"
							+ INSTITUTE_REP + "'")) {

				LOGGER.info("users>>" + data[0] + ":: " + data[1] + "");
				Users users = new Users();
				users.setFname(data[0] + "");
				users.setLname(data[1] + "");
				users.setViewId(data[2] + "");
				users.setAddress(data[3] + "");
				users.setUserId(Integer.parseInt(data[4] + ""));
				usersDetails.add(users);
			}
			contactSize = usersDetails.size();
			contactDtoDetails = displayRepresentContact();
			repavail = contactDtoDetails.size();

			for (Object[] data : catImpl.reportList(
					"select cat.userCatid,cat.status,cat.usercategoryName from UserCategory cat where cat.usercategoryName<>'"
							+ INSTITUTE_REP + "' and cat.usercategoryName<>'" + SUPER_ADMIN + "'")) {
				UserCategory cat = new UserCategory();
				cat.setUserCatid(Integer.parseInt(data[0] + ""));
				cat.setStatus(data[1] + "");
				cat.setUsercategoryName(data[2] + "");
				userCatDetails.add(cat);
			}
			catDetails = catImpl.getGenericListWithHQLParameter(new String[] { "status", "usercategoryName" },
					new Object[] { ACTIVE, INSTITUTE_REP }, "UserCategory", " userCatid desc");
			useravail = usersImpl.getListWithHQL("from Users", 0, endrecord);
			userDtosDetails = showUsersByPageRecords(useravail);
			this.renderBoard=true;
			this.renderDatePanel=true;
		} catch (Exception e) {
			setValid(false);
			JSFMessagers.addErrorMessage(getProvider().getValue("com.server.side.internal.error"));
			LOGGER.info(e.getMessage());
			e.printStackTrace();
		}

	}

	
	
	@SuppressWarnings("unchecked")
	public void showUsers() throws Exception {
		if (range.equals("6") || (range.equals("11")) || (range.equals("16"))) {
			int endRecords = Integer.parseInt(range);
			useravail = usersImpl.getListWithHQL("from Users", 0, endRecords);
			userDtosDetails = showUsersByPageRecords(useravail);
			
		} else {
			this.renderBoardOption=true;
			this.renderDatePanel=false;
		}		
	}

	public List displayRepresentContact() {

		List<ContactDto> contactDtoDetails = new ArrayList<ContactDto>();
		for (Object[] data : contactImpl.reportList(
				"select co.contactDetails,co.email,co.phone,co.contactId,co.user from Contact co right  join  co.user us join us.userCategory cat where co.user is not null and cat.usercategoryName='"
						+ INSTITUTE_REP + "'")) {
			ContactDto contDto = new ContactDto();
			contDto.setEditable(false);
			contDto.setContactDetails(data[0] + "");
			contDto.setEmail(data[1] + "");
			contDto.setPhone(data[2] + "");
			contDto.setContactId(Integer.parseInt(data[3] + ""));
			contDto.setUser((Users) data[4]);
			contactDtoDetails.add(contDto);
		}
		return (contactDtoDetails);
	}

	public void renderContactTable() {
		this.rendered = true;
		this.renderRepContactDash = false;
	}


	public int showAvailRep() {
		List<Users> repDetails = new ArrayList<Users>();
		if (usersSession.getUserCategory().getUsercategoryName().equals(SUPER_ADMIN)) {
			for (Object[] data : usersImpl.reportList(
					"select u.userCategory,cat.usercategoryName from Users u join u.userCategory cat where u.userCategory=cat.userCatid and cat.usercategoryName='"
							+ INSTITUTE_REP + "'")) {
				Users user = new Users();
				user.setUserCategory((UserCategory) data[0]);
				repDetails.add(user);
			}

		}
		return (repDetails.size());
	}

	public void showRepresent() {

		this.renderRepTable = true;
		this.renderHeading = true;
		this.renderRepContactDash = false;
	}

	// Method to display all district by province
	@SuppressWarnings("unchecked")
	public void changeDistrict() {
		try {
			province = provImpl.getProvinceById(provinceId, "provenceId");
			districtByProv = districtImpl.getGenericListWithHQLParameter(new String[] { "province" },
					new Object[] { province }, "District", "districtId asc");
		} catch (Exception e) {
			setValid(false);
			JSFMessagers.addErrorMessage(getProvider().getValue("com.server.side.internal.error"));
			LOGGER.info(e.getMessage());
			e.printStackTrace();
		}
	}

	// Method to display all sector by District
	@SuppressWarnings("unchecked")
	public void changeSector() {

		try {

			district = districtImpl.getDistrictById(districtId, "districtId");
			sectorByDistrict = sectorImpl.getGenericListWithHQLParameter(new String[] { "distric" },
					new Object[] { district }, "Sector", "sectorId asc");
		} catch (Exception e) {
			setValid(false);
			JSFMessagers.addErrorMessage(getProvider().getValue("com.server.side.internal.error"));
			LOGGER.info(e.getMessage());
			e.printStackTrace();
		}

	}

	// Method to display all cell by sector

	@SuppressWarnings("unchecked")
	public void changeCell() {

		try {

			sector = sectorImpl.getSectorById(sectorId, "sectorId");
			cellBySector = cellImpl.getGenericListWithHQLParameter(new String[] { "sector" }, new Object[] { sector },
					"Cell", "cellId asc");
		} catch (Exception e) {
			setValid(false);
			JSFMessagers.addErrorMessage(getProvider().getValue("com.server.side.internal.error"));
			LOGGER.info(e.getMessage());
			e.printStackTrace();
		}

	}
	// Method to display all village by Cell

	@SuppressWarnings("unchecked")
	public void changeVillage() {

		try {

			cell = cellImpl.getCellById(cellId, "cellId");
			villageByCell = villageImpl.getGenericListWithHQLParameter(new String[] { "cell" }, new Object[] { cell },
					"Village", "villageId asc");
		} catch (Exception e) {
			setValid(false);
			JSFMessagers.addErrorMessage(getProvider().getValue("com.server.side.internal.error"));
			LOGGER.info(e.getMessage());
			e.printStackTrace();
		}

	}

	@SuppressWarnings("static-access")
	public String saveUserInfo() throws IOException, NoSuchAlgorithmException {
		String url = getContextPath();
		// System.out.print("+++++++++++++++++:" + url + "/");
		HttpSession session = SessionUtils.getSession();
		usersSession = (Users) session.getAttribute("userSession");
		try {
			try {
				Users user = new Users();
				user = usersImpl.getModelWithMyHQL(new String[] { "viewId" }, new Object[] { users.getViewId() },
						"from Users");
				if (null != user) {
					JSFMessagers.resetMessages();
					setValid(false);
					JSFMessagers.addErrorMessage(getProvider().getValue("error.server.side.dupicate.viewId"));
					LOGGER.info(CLASSNAME + "sivaserside validation :: User Name already  recorded in the system! ");
					return null;
				}

			} catch (Exception e) {
				JSFMessagers.resetMessages();
				setValid(false);
				JSFMessagers.addErrorMessage(getProvider().getValue("com.server.side.internal.error"));
				LOGGER.info(CLASSNAME + "" + e.getMessage());
				e.printStackTrace();
				return null;
			}

			Formating fmt = new Formating();
			/*
			 * Date today = fmt.getCurrentDateFormtNOTime(); Date dob =
			 * fmt.getMysqlTimeFormt(dateofBirth); age=fmt.daysBetween(dob, today);
			 * LOGGER.info(":::::::::::::::::::::::::::::");
			 * LOGGER.info("today Founded::--------------->:"+today);
			 * LOGGER.info("dob Founded::--------------->:"+dateofBirth);
			 */
			SimpleDateFormat simpleDateFormat = new SimpleDateFormat("yyyy-MM-dd");

			Date today = fmt.getCurrentDateFormtNOTime();
			// simpleDateFormat.format(today);
			simpleDateFormat.format(dateofBirth);
			days = fmt.daysBetween(dateofBirth, today);
			age = days / 365;
			LOGGER.info(":::::::::::::::::::::::::::::");
			LOGGER.info("Age Founded::--------------->:" + age);

			LOGGER.info(":::::::::::::::::::::::::::::");
			if (age >= 16) {
				if (password.equalsIgnoreCase(confirmPswd)) {
					users.setImage("us.png");
					users.setCreatedBy(usersSession.getViewId());
					users.setCrtdDtTime(timestamp);
					users.setCreatedDate(timestamp);
					users.setGenericStatus(ACTIVE);
					users.setUpdatedBy(usersSession.getViewId());
					users.setCrtdDtTime(timestamp);
					users.setDateOfBirth(new java.sql.Date(dateofBirth.getTime()));
					if (choice.equalsIgnoreCase(country_rw)) {
						users.setVillage(villageImpl.getVillageById(villageId, "villageId"));
						users.setUserCategory(catImpl.getUserCategoryById(categoryId, "userCatid"));
						if (null != usersSession) {
							if (usersSession.getUserCategory().getUsercategoryName().equals(SUPER_ADMIN)) {
								users.setViewName(loginImpl.criptPassword(password));
								users.setStatus(DESACTIVE);
								users.setLoginStatus(OFFLINE);
								usersImpl.saveUsers(users);
								JSFMessagers.resetMessages();
								setValid(true);
								JSFMessagers.addErrorMessage(getProvider().getValue("com.save.form.user"));
								LOGGER.info(CLASSNAME + ":::User Details is saved");
								clearUserFuileds();
								return "";
							} else {

								users.setBoard(boardImpl.getBoardById(boardId, "boardId"));
								users.setViewName(loginImpl.criptPassword(password));
								users.setStatus(DESACTIVE);
								users.setLoginStatus(OFFLINE);
								usersImpl.saveUsers(users);
								JSFMessagers.resetMessages();
								setValid(true);
								JSFMessagers.addErrorMessage(getProvider().getValue("com.save.form.user"));
								LOGGER.info(CLASSNAME + ":::User Details is saved");
								clearUserFuileds();
								return "";
							}
						} else {

							setValid(false);
							JSFMessagers
									.addErrorMessage(getProvider().getValue("com.server.SessionError.internal.error"));
						}

					} else {
						users.setUserCategory(catImpl.getUserCategoryById(categoryId, "userCatid"));
						users.setBoard(boardImpl.getBoardById(boardId, "boardId"));
						users.setViewName(loginImpl.criptPassword(password));
						users.setStatus(DESACTIVE);
						users.setLoginStatus(OFFLINE);
						usersImpl.saveUsers(users);
						JSFMessagers.resetMessages();
						setValid(true);
						JSFMessagers.addErrorMessage(getProvider().getValue("com.save.form.user"));
						LOGGER.info(CLASSNAME + ":::User Details is saved");
						clearUserFuileds();
						return "";
					}

				} else {
					JSFMessagers.resetMessages();
					setValid(false);
					JSFMessagers.addErrorMessage(getProvider().getValue("com.save.form.pswdMatch"));
				}
			} else {
				JSFMessagers.resetMessages();
				setValid(false);
				JSFMessagers.addErrorMessage(getProvider().getValue("help.userdob.message"));
			}

		} catch (HibernateException ex) {
			LOGGER.info(CLASSNAME + ":::User Details is fail with HibernateException  error");
			JSFMessagers.resetMessages();
			setValid(false);
			JSFMessagers.addErrorMessage(getProvider().getValue("com.server.side.internal.error"));
			LOGGER.info(CLASSNAME + "" + ex.getMessage());
			ex.printStackTrace();
		}
		return "";
	}

	public void clearUserFuileds() {

		users = new Users();
		usersDetails = null;
	}

	public String changeOption() {
		if (option.equals(Yes_Option)) {
			rendered = true;
			renderprofile = false;
			/* renderForeignCountry=true; */
			rendersaveButton = true;
			return (option);
		} else {
			rendered = false;
			renderprofile = false;
			rendersaveButton = true;
			return (option);
		}
	}

	public String getMyFormattedDate() {
		HttpSession session = SessionUtils.getSession();
		Users usersSes = new Users();
		usersSes = (Users) session.getAttribute("userSession");
		return new SimpleDateFormat("dd-MM-yyyy").format(usersSes.getDateOfBirth());
	}

	public void profilePage(UserDto user) {
		if (redirect.equals(Next_Option)) {
			if (null != user) {
				int userId = user.getUserId();
				HttpSession sessionuser = SessionUtils.getSession();
				sessionuser.setAttribute("userProfile", userId);
				nextButoon = true;
				renderRepContactDash = false;
			}
		} else {
			renderprofile = false;
			nextButoon = false;
		}

	}

	public String nextPage() {

		return "/menu/EditProfile.xhtml?faces-redirect=true";
	}

	public String getContextPath() {

		HttpServletRequest request = (HttpServletRequest) FacesContext.getCurrentInstance().getExternalContext()
				.getRequest();

		return request.getContextPath();
	}

	public void optionCombine() {
		rendered = false;
		renderForeignCountry = false;
		rendersaveButton = false;
		renderprofile = false;
		nextButoon = false;
	}

	public String cancel(UserDto user) {
		user.setEditable(false);
		optionCombine();

		// usersImpl.UpdateUsers(user);
		return null;

	}

	public void manageOption() {

		if (selection.equals(Date_opt)) {
			this.renderDatePanel = true;
			this.renderBoardOption = false;
			this.renderEditedTableByBoard = false;
			LOGGER.info("Option founded:::::::::::" + selection);
		} else {
			this.renderDatePanel = false;
			this.renderBoardOption = true;
			this.renderEditedTableByDate = false;
		}

	}

	@SuppressWarnings("unchecked")
	public String renderAction(UserDto user) throws Exception {
		user.setNotify(true);
		Users users= new Users();
		users = usersImpl.getModelWithMyHQL(new String[] { "userId" }, new Object[] { user.getUserId() },
				"from Users");
		if(null!=users) {
		contactDetails=contactImpl.getGenericListWithHQLParameter(new String[] { "genericStatus","user" },
				new Object[] { ACTIVE,users}, "Contact", "contactId asc");
		}
		return null;
	}

	public String cancelChange(UserDto user) {
		user.setNotify(false);
		this.useremail = null;
		return null;
	}

	public String editAction(UserDto user) {

		user.setEditable(true);
		renderForeignCountry = true;
		renderprofile = true;
		rendersaveButton = true;
		// showCategory();
		// usersImpl.UpdateUsers(user);
		return null;
	}

	public boolean confirmPswd() {
		boolean valid = false;
		if (password.equalsIgnoreCase(confirmPswd)) {
			valid = true;
			return (valid);
		}
		return (valid);
	}

	public String backPage() {
		if (usersSession.getUserCategory().getUsercategoryName().equals(INSTITUTE_REP)) {
			return "/menu/ViewUsersDetails.xhtml?faces-redirect=true";
		} else {
			return "/menu/ListOfUsers.xhtml?faces-redirect=true";
		}
	}

	public String saveAction(UserDto user) throws NoSuchAlgorithmException, IOException {
		LOGGER.info("update  saveAction method");
		if (user != null) {

			// Creating Session about user profile
			HttpSession sessionuser = SessionUtils.getSession();
			sessionuser.setAttribute("userProfile", user);
			optionCombine();
			Users us = new Users();
			us = new Users();
			us = usersImpl.gettUserById(user.getUserId(), "userId");
			LOGGER.info("here update sart for " + us + " useriD " + us.getUserId());
			LOGGER.info("++++++++++++++++++++++++++here update sart for " + us + " useriD " + us.getUserId());

			String option = changeOption();
			if (option.equals(Yes_Option)) {
				boolean valid = confirmPswd();
				if (valid) {
					us.setViewName(loginImpl.criptPassword(confirmPswd));
					user.setEditable(false);
					us.setFname(user.getFname());
					us.setLname(user.getLname());
					us.setAddress(user.getAddress());
					us.setUpdatedBy(usersSession.getViewId());
					us.setUpDtTime(timestamp);
					usersImpl.UpdateUsers(us);
					optionCombine();
					JSFMessagers.resetMessages();
					setValid(true);
					JSFMessagers.addErrorMessage(getProvider().getValue("pswd.changed.message"));

				} else {
					JSFMessagers.resetMessages();
					setValid(false);
					JSFMessagers.addErrorMessage(getProvider().getValue("erropswd.changed.message"));
					optionCombine();
				}

			} else {
				user.setEditable(false);
				us.setFname(user.getFname());
				us.setLname(user.getLname());
				us.setAddress(user.getAddress());
				usersImpl.UpdateUsers(us);
				optionCombine();
			}
			return null;
		} else {
			JSFMessagers.resetMessages();
			setValid(false);
			JSFMessagers.addErrorMessage(getProvider().getValue("com.save.form.userprofile"));
			return null;
		}

	}

	public String newAction(UserDto user) {
		LOGGER.info("update  saveAction method");
		// get all existing value but set "editable" to false
		try {
			Users us = new Users();
			us = new Users();
			if (null != user)
				us = usersImpl.gettUserById(user.getUserId(), "userId");
			if (null != us)
				LOGGER.info("here update sart for " + us + " useriD " + us.getUserId());
			user.setEditable(false);
			us.setFname(user.getFname());
			us.setLname(user.getLname());
			us.setUserCategory(user.getUserCategory());
			us.setBoard(user.getBoard());
			us.setUpdatedBy(usersSession.getViewId());
			us.setUpDtTime(timestamp);
			usersImpl.UpdateUsers(us);
			JSFMessagers.resetMessages();
			setValid(true);
			JSFMessagers.addErrorMessage(getProvider().getValue("com.save.form.userupdate"));
			useravail = usersImpl.getListWithHQL("from Users", 0,endrecord);
			userDtosDetails = showUsersByPageRecords(useravail);
			return null;
		} catch (Exception e) {
			setValid(false);
			JSFMessagers.addErrorMessage(getProvider().getValue("com.server.side.internal.updateuserError"));
			LOGGER.info(e.getMessage());
			e.printStackTrace();
		}
		return null;

	}

	public String boardUpdateStatus(UserDto user) {
		LOGGER.info("update  saveAction method");
		try {
			Users us = new Users();
			us = new Users();
			if (user != null)
				us = usersImpl.gettUserById(user.getUserId(), "userId");
			if (us != null)
				LOGGER.info("here update sart for " + us + " useriD " + us.getStatus());

			if (user.getStatus().equals(ACTIVE)) {
				us.setUpdatedBy(usersSession.getViewId());
				us.setUpDtTime(timestamp);
				us.setStatus(DESACTIVE);
				user.setNotify(false);
			} else {
				us.setUpdatedBy(usersSession.getViewId());
				us.setUpDtTime(timestamp);
				us.setStatus(ACTIVE);
				user.setNotify(false);
			}
			Contact ct = new Contact();

			if (null != useremail) {
				ct = contactImpl.getModelWithMyHQL(new String[] { "email" }, new Object[] { useremail },
						"from Contact");
				if (null != ct) {
					usersImpl.UpdateUsers(us);
					/* displayUsersByBoard(); */
					userDtosDetails = showUsersByBoard(choice);
					JSFMessagers.resetMessages();
					setValid(true);
					JSFMessagers.addErrorMessage(getProvider().getValue("com.save.form.userupdate"));
					LOGGER.info("EMAIL TO NOTIFY::::::::::::::::::::::::::::::::::::::" + useremail);
					boolean valid = notifyRepresentativeChange(useremail);
					if (valid) {
						JSFMessagers.resetMessages();
						setValid(true);
						JSFMessagers
								.addErrorMessage(getProvider().getValue("com.server.side.email.notification.status"));
						LOGGER.info(CLASSNAME + "::Email sent successuful!!");
						this.useremail = null;
						// return to current page
					} else {
						JSFMessagers.resetMessages();
						setValid(false);
						JSFMessagers.addErrorMessage(getProvider().getValue("com.notifyError.representative.user"));
						LOGGER.info(CLASSNAME + "::Fail to send Email!!");
						this.useremail = null;
					}
				} else {
					this.useremail = null;
					JSFMessagers.resetMessages();
					setValid(false);
					JSFMessagers.addErrorMessage(getProvider().getValue("error.server.side.notfound.email"));
					LOGGER.info(CLASSNAME + "sivaserside validation :: email not recorded in the system! ");
					return null;
				}

			} else {
				JSFMessagers.resetMessages();
				setValid(false);
				JSFMessagers.addErrorMessage(getProvider().getValue("error.selected.invalid.email"));
			}
			return "";
		} catch (Exception e) {
			setValid(false);
			JSFMessagers.addErrorMessage(getProvider().getValue("com.server.side.internal.updateuserError"));
			LOGGER.info(e.getMessage());
			e.printStackTrace();
		}
		return null;

	}

	public String updateStatus(UserDto user) {
		LOGGER.info("update  saveAction method");
		// get all existing value but set "editable" to false
		try {
			Users us = new Users();
			us = new Users();
			Contact ct = new Contact();
			if (user != null)
				us = usersImpl.gettUserById(user.getUserId(), "userId");
			if (us != null)
				LOGGER.info("here update sart for " + us + " useriD " + us.getStatus());

			if (user.getStatus().equals(ACTIVE)) {
				us.setUpdatedBy(usersSession.getViewId());
				us.setUpDtTime(timestamp);
				us.setStatus(DESACTIVE);
				user.setNotify(false);
			} else {
				us.setUpdatedBy(usersSession.getViewId());
				us.setUpDtTime(timestamp);
				us.setStatus(ACTIVE);
				user.setNotify(false);
			}
					
			if (null != useremail) {
				ct = contactImpl.getModelWithMyHQL(new String[] { "email" }, new Object[] { useremail },
						"from Contact");
			if (null != ct) {
				usersImpl.UpdateUsers(us);
				useravail = usersImpl.getListWithHQL("from Users", 0,endrecord);
				userDtosDetails = showUsersByPageRecords(useravail);
				JSFMessagers.resetMessages();
				setValid(true);
				JSFMessagers
						.addErrorMessage(getProvider().getValue("com.save.form.userupdate"));
				LOGGER.info(CLASSNAME + "::Email sent successuful!!");
				this.useremail = null;
			LOGGER.info("EMAIL TO NOTIFY::::::::::::::::::::::::::::::::::::::" + useremail);
			boolean valid = notifyRepresentativeChange(useremail);
			if (valid) {
				LOGGER.info("returing values controller" + valid);
				setValid(true);
				JSFMessagers.addErrorMessage(getProvider().getValue("com.server.side.email.notifications"));
				LOGGER.info(CLASSNAME + ":::Contact Details is saved");
					} else {
						JSFMessagers.resetMessages();
						setValid(false);
						JSFMessagers.addErrorMessage(getProvider().getValue("com.notifyError.representative.user"));
						LOGGER.info(CLASSNAME + "::Fail to send Email!!");
						this.useremail = null;
					}
				} else {
					this.useremail = null;
					JSFMessagers.resetMessages();
					setValid(false);
					JSFMessagers.addErrorMessage(getProvider().getValue("error.server.side.notfound.email"));
					LOGGER.info(CLASSNAME + "sivaserside validation :: email not recorded in the system! ");
					return null;
				}
			} else {
				JSFMessagers.resetMessages();
				setValid(false);
				JSFMessagers.addErrorMessage(getProvider().getValue("error.selected.invalid.email"));
			}
			return null;
		} catch (Exception e) {
			setValid(false);
			JSFMessagers.addErrorMessage(getProvider().getValue("com.server.side.internal.updateuserError"));
			LOGGER.info(e.getMessage());
			e.printStackTrace();
		}
		return null;
	}

	public Boolean notifyRepresentativeChange(String email) throws Exception {
		boolean valid = false;
		try {
			SendSupportEmail support = new SendSupportEmail();
			Contact ct = new Contact();

			if (null != email) {
				ct = contactImpl.getModelWithMyHQL(new String[] { "email" }, new Object[] { email }, "from Contact");
				if (null != ct) {
					boolean verifyemail = support.sendMailTestVersion(ct.getUser().getFname(), ct.getUser().getLname(),
							ct.getEmail());
					if (verifyemail) {
						valid = true;
					}
				} else {
					JSFMessagers.resetMessages();
					setValid(false);
					JSFMessagers.addErrorMessage(getProvider().getValue("error.server.side.notfound.email"));
					LOGGER.info(CLASSNAME + "sivaserside validation :: email not recorded in the system! ");
					return null;
				}

			} else {
				JSFMessagers.resetMessages();
				setValid(false);
				JSFMessagers.addErrorMessage(getProvider().getValue("error.selected.invalid.email"));
			}
		} catch (HibernateException e) {
			LOGGER.info(CLASSNAME + ":::Contact Details is fail with HibernateException  error");
			JSFMessagers.resetMessages();
			setValid(false);
			JSFMessagers.addErrorMessage(getProvider().getValue("com.server.side.internal.errorsession"));
			LOGGER.info(CLASSNAME + "" + e.getMessage());
			e.printStackTrace();
		}
		return (valid);
	}

	public String updateRepStatus(UserDto user) {
		LOGGER.info("update  saveAction method");
		// get all existing value but set "editable" to false
		try {
			Users us = new Users();
			us = new Users();
			if (user != null)
				us = usersImpl.gettUserById(user.getUserId(), "userId");
			if (us != null)
				LOGGER.info("here update sart for " + us + " useriD " + us.getStatus());

			if (user.getStatus().equals(ACTIVE)) {
				us.setUpdatedBy(usersSession.getViewId());
				us.setUpDtTime(timestamp);
				us.setStatus(DESACTIVE);

			} else {
				us.setUpdatedBy(usersSession.getViewId());
				us.setUpDtTime(timestamp);
				us.setStatus(ACTIVE);
			}
			Contact ct = new Contact();

			if (null != useremail) {
				ct = contactImpl.getModelWithMyHQL(new String[] { "email" }, new Object[] { useremail },
						"from Contact");
				if (null != ct) {
					usersImpl.UpdateUsers(us);
					repDtosDetails = displayRepresentativeByDateBetween();
					JSFMessagers.resetMessages();
					setValid(true);
					JSFMessagers.addErrorMessage(getProvider().getValue("com.save.form.userupdate"));
					LOGGER.info("EMAIL TO NOTIFY::::::::::::::::::::::::::::::::::::::" + useremail);
					boolean valid = notifyRepresentativeChange(useremail);
					if (valid) {
						JSFMessagers.resetMessages();
						setValid(true);
						JSFMessagers.addErrorMessage(getProvider().getValue("com.server.side.email.notification.status"));
						LOGGER.info(CLASSNAME + "::Email sent successuful!!");
						this.useremail = null;

					} else {
						JSFMessagers.resetMessages();
						setValid(false);
						JSFMessagers.addErrorMessage(getProvider().getValue("com.notifyError.representative.user"));
						LOGGER.info(CLASSNAME + "::Fail to send Email!!");
						this.useremail = null;
					}
				} else {
					this.useremail = null;
					JSFMessagers.resetMessages();
					setValid(false);
					JSFMessagers.addErrorMessage(getProvider().getValue("error.server.side.notfound.email"));
					LOGGER.info(CLASSNAME + "sivaserside validation :: email not recorded in the system! ");
					return null;
				}

			} else {
				JSFMessagers.resetMessages();
				setValid(false);
				JSFMessagers.addErrorMessage(getProvider().getValue("error.selected.invalid.email"));
			}
			return "";
			/* return "/menu/ViewUsersList.xhtml?faces-redirect=true"; */

		} catch (Exception e) {
			setValid(false);
			JSFMessagers.addErrorMessage(getProvider().getValue("com.server.side.internal.updateuserError"));
			LOGGER.info(e.getMessage());
			e.printStackTrace();
		}
		return null;

	}

	@SuppressWarnings("unchecked")
	public void updateTable() throws Exception {
		try {

			if (choice.equals(country_rw)
					&& usersSession.getUserCategory().getUsercategoryName().equals(INSTITUTE_REP)) {
				rendered = true;
				renderForeignCountry = true;
				renderBoard = true;
				nextButoon = false;
				LOGGER.info(" REP LIST SIze:::::::" + userCatDetails.size());
			} else if (usersSession.getUserCategory().getUsercategoryName().equals(INSTITUTE_REP)) {
				rendered = false;
				renderForeignCountry = true;
				nextButoon = false;
				renderBoard = true;
				LOGGER.info(" REP LIST SIze:::::::" + userCatDetails.size());
			}

			// CATEGORY ON ADMIN PANEL
			if (choice.equals(country_rw) && usersSession.getUserCategory().getUsercategoryName().equals(SUPER_ADMIN)) {
				rendered = true;
				renderForeignCountry = true;
				renderBoard = false;
				nextButoon = true;
				LOGGER.info("ADMIN LIST SIze:::::::" + catDetails.size());
			} else if (usersSession.getUserCategory().getUsercategoryName().equals(SUPER_ADMIN)) {
				rendered = false;
				renderForeignCountry = true;
				nextButoon = true;
				renderBoard = false;
				LOGGER.info(" ADMIN LIST SIze:::::::" + catDetails.size());
			}
		} catch (Exception e) {
			setValid(false);
			JSFMessagers.addErrorMessage(getProvider().getValue("com.server.side.internal.error"));
			LOGGER.info(e.getMessage());
			e.printStackTrace();
		}
	}

	public void showDataTable() {

		if ((null != to) && (null != from)) {

			renderDataTable = true;
		}
	}

	@SuppressWarnings("static-access")
	public List<UserDto> displayRepresentativeByDateBetween() {

		try {

			userDtosDetails = new ArrayList<UserDto>();
			for (Object[] data : usersImpl.reportList(
					"select us.fname,us.lname,us.viewId,us.userCategory,us.status,us.userId,us.loginStatus,cat.usercategoryName from Users us,UserCategory cat where us.userCategory=cat.userCatid and cat.usercategoryName='"
							+ INSTITUTE_REP + "'")) {

				LOGGER.info("users::::::::::::::::::::::::::::::::::::::::::::::::>>" + data[0] + ":: " + data[1] + "");
				UserDto userDtos = new UserDto();
				userDtos.setEditable(false);
				userDtos.setNotify(false);
				userDtos.setUserId(Integer.parseInt(data[5] + ""));
				userDtos.setFname(data[0] + "");
				userDtos.setLname(data[1] + "");
				userDtos.setViewId(data[2] + "");
				userDtos.setUserCategory((UserCategory) data[3]);
				userDtos.setStatus(data[4] + "");
				/*
				 * userDtos.setEmail(data[6] + ""); userDtos.setPhone(data[7] + "");
				 * userDtos.setInstitution(data[8] + ""); userDtos.setGenericStatus(data[9] +
				 * ""); userDtos.setBoard((Board)data[10]);
				 */
				userDtos.setLoginStatus(data[6] + "");
				if (data[4].equals(ACTIVE)) {
					userDtos.setAction(DESACTIVE);
				} else {
					userDtos.setAction(ACTIVE);
				}
				userDtosDetails.add(userDtos);
			}
			return (userDtosDetails);
		} catch (Exception e) {
			setValid(false);
			JSFMessagers.addErrorMessage(getProvider().getValue("com.server.side.internal.error"));
			LOGGER.info(e.getMessage());
			e.printStackTrace();
		}
		return null;
	}

	@SuppressWarnings("static-access")
	public void listUsersByDateBetween() {
		try {
			if ((null != from) && (null != to)) {
				if (to.after(from)) {
					Formating fmt = new Formating();
					LOGGER.info("Here We are :--------------->>" + "Start Date:" + fmt.getMysqlFormatV2(from)
							+ "End Date:-------->>>" + fmt.getMysqlFormatV2(to));
					days = fmt.daysBetween(from, to);

					LOGGER.info("Days founded:......................" + days);
					if (days <= 30) {
						renderEditedTableByDate = true;
						userDtosDetails = new ArrayList<UserDto>();
						for (Object[] data : usersImpl.reportList(
								"select us.fname,us.lname,us.viewId,us.userCategory,us.status,us.userId,us.board from Users us where us.createdDate between '"
										+ fmt.getMysqlFormatV2(from) + "' and  '" + fmt.getMysqlFormatV2(to) + "'")) {

							LOGGER.info("users::::::::::::::::::::::::::::::::::::::::::::::::>>" + data[0] + ":: "
									+ data[1] + "");
							UserDto userDtos = new UserDto();
							userDtos.setEditable(false);
							userDtos.setNotify(false);
							userDtos.setUserId(Integer.parseInt(data[5] + ""));
							userDtos.setFname(data[0] + "");
							userDtos.setLname(data[1] + "");
							userDtos.setViewId(data[2] + "");
							userDtos.setUserCategory(((UserCategory) data[3]));
							userDtos.setStatus(data[4] + "");
							userDtos.setBoard(((Board) data[6]));
							if (data[4].equals(ACTIVE)) {
								userDtos.setAction(DESACTIVE);
							} else {
								userDtos.setAction(ACTIVE);
							}
							userDtosDetails.add(userDtos);
						}
					} else {
						setValid(false);
						JSFMessagers
								.addErrorMessage(getProvider().getValue("com.server.side.internal.invalidDaysRange"));
					}

				} else {
					setValid(false);
					JSFMessagers.addErrorMessage(getProvider().getValue("com.server.side.internal.invalidRange"));
				}
			} else {
				setValid(false);
				JSFMessagers.addErrorMessage(getProvider().getValue("com.server.side.internal.InvalDate"));
			}

		} catch (Exception e) {
			setValid(false);
			JSFMessagers.addErrorMessage(getProvider().getValue("com.server.side.internal.InvalDate"));
			LOGGER.info(e.getMessage());
			e.printStackTrace();
		}
	}

	@SuppressWarnings("static-access")
	public void displayUsersByDateBetween() {
		try {
			if (to.after(from)) {

				Formating fmt = new Formating();
				LOGGER.info("Here We are :--------------->>" + "Start Date:" + fmt.getMysqlFormatV2(from)
						+ "End Date:-------->>>" + fmt.getMysqlFormatV2(to));
				days = fmt.daysBetween(from, to);

				LOGGER.info("Days founded:......................" + days);
				if (days <= 30) {
					renderDataTable = true;
					userDtosDetails = new ArrayList<UserDto>();
					for (Object[] data : usersImpl.reportList(
							"select DICTINCT us.fname,us.lname,us.viewId,us.userCategory,us.status,us.userId,co.email,co.phone,ins.institutionName,us.genericStatus,us.board from Users us,Contact co,Institution ins,Board b where b.institution=ins.institutionId and b.boardId=us.board and co.user=us.userId and us.createdDate between '"
									+ fmt.getMysqlFormatV2(from) + "' and  '" + fmt.getMysqlFormatV2(to) + "'")) {
						LOGGER.info("users::::::::::::::::::::::::::::::::::::::::::::::::>>" + data[0] + ":: "
								+ data[1] + "");
						UserDto userDtos = new UserDto();
						userDtos.setEditable(false);
						userDtos.setNotify(false);
						userDtos.setUserId(Integer.parseInt(data[5] + ""));
						userDtos.setFname(data[0] + "");
						userDtos.setLname(data[1] + "");
						userDtos.setViewId(data[2] + "");
						userDtos.setUserCategory((UserCategory) data[3]);
						userDtos.setStatus(data[4] + "");
						userDtos.setEmail(data[6] + "");
						userDtos.setPhone(data[7] + "");
						userDtos.setInstitution(data[8] + "");
						userDtos.setGenericStatus(data[9] + "");
						userDtos.setBoard((Board) data[10]);
						if (data[4].equals(ACTIVE)) {
							userDtos.setAction(DESACTIVE);
						} else {
							userDtos.setAction(ACTIVE);
						}
						userDtosDetails.add(userDtos);
					}

				} else {
					setValid(false);
					JSFMessagers.addErrorMessage(getProvider().getValue("com.server.side.internal.invalidDaysRange"));
				}

			} else {
				setValid(false);
				JSFMessagers.addErrorMessage(getProvider().getValue("com.server.side.internal.invalidRange"));
			}

		} catch (Exception e) {
			setValid(false);
			JSFMessagers.addErrorMessage(getProvider().getValue("com.server.side.internal.error"));
			LOGGER.info(e.getMessage());
			e.printStackTrace();
		}
	}

	public List<UserDto> showUsersByPageRecords(List<Users> userslist) {
		try {

			userDtosDetails = new ArrayList<UserDto>();
			for (Users users : userslist) {
				LOGGER.info("users::::::::::::::::::::::::::::::::::::::::::::::::>>" + users.getUserId() + ":: "
						+ users.getFname() + "");
				if(users.getUserCategory().getUserCatid()!=1){
				UserDto userDtos = new UserDto();
				userDtos.setEditable(false);
				userDtos.setNotify(false);
				userDtos.setUserId(users.getUserId());
				userDtos.setFname(users.getFname());
				userDtos.setLname(users.getLname());
				userDtos.setViewId(users.getViewId());
				userDtos.setLoginStatus(users.getLoginStatus());
				userDtos.setUserCategory(users.getUserCategory());
				userDtos.setStatus(users.getStatus());
				userDtos.setBoard(users.getBoard());
				if (users.getStatus().equals(ACTIVE)) {
					userDtos.setAction(DESACTIVE);
				} else {
					userDtos.setAction(ACTIVE);
				}
				userDtosDetails.add(userDtos);
				}
			}
			return (userDtosDetails);

		} catch (Exception e) {
			setValid(false);
			JSFMessagers.addErrorMessage(getProvider().getValue("com.server.side.internal.error"));
			LOGGER.info(e.getMessage());
			e.printStackTrace();
		}
		return (userDtosDetails);

	}

	public List<UserDto> showUsersByBoard(String boardName) {
		try {
			if (null != boardName) {
				userDtosDetails = new ArrayList<UserDto>();
				for (Object[] data : usersImpl.reportList(
						"select us.fname,us.lname,us.viewId,us.userCategory,us.status,us.userId,us.board,b.boardName from Users us ,Board b where us.board=b.boardId and b.boardName='"
								+ boardName + "'")) {
					LOGGER.info(
							"users::::::::::::::::::::::::::::::::::::::::::::::::>>" + data[0] + ":: " + data[1] + "");
					UserDto userDtos = new UserDto();
					userDtos.setEditable(false);
					userDtos.setNotify(false);
					userDtos.setUserId(Integer.parseInt(data[5] + ""));
					userDtos.setFname(data[0] + "");
					userDtos.setLname(data[1] + "");
					userDtos.setViewId(data[2] + "");
					userDtos.setUserCategory(((UserCategory) data[3]));
					userDtos.setStatus(data[4] + "");
					userDtos.setBoard(((Board) data[6]));
					if (data[4].equals(ACTIVE)) {
						userDtos.setAction(DESACTIVE);
					} else {
						userDtos.setAction(ACTIVE);
					}
					userDtosDetails.add(userDtos);
				}
				return (userDtosDetails);
			} else {
				setValid(false);
				JSFMessagers.addErrorMessage(getProvider().getValue("com.server.side.internal.invalidchoice"));
			}

		} catch (Exception e) {
			setValid(false);
			JSFMessagers.addErrorMessage(getProvider().getValue("com.server.side.internal.error"));
			LOGGER.info(e.getMessage());
			e.printStackTrace();
		}
		return (userDtosDetails);

	}

	public void displayUsersByBoard() {
		try {
			if (null != choice) {
				renderEditedTableByBoard = true;
				userDtosDetails = new ArrayList<UserDto>();
				for (Object[] data : usersImpl.reportList(
						"select us.fname,us.lname,us.viewId,us.userCategory,us.status,us.userId,us.board,us.loginStatus from Users us ,Board b where us.board=b.boardId and b.boardName='"
								+ choice + "'")) {
					LOGGER.info(
							"users::::::::::::::::::::::::::::::::::::::::::::::::>>" + data[0] + ":: " + data[1] + "");
						int catId=Integer.parseInt(((UserCategory)data[3]).getUserCatid()+"");
						if(catId!=1) {
						UserDto userDtos = new UserDto();
						userDtos.setEditable(false);
						userDtos.setNotify(false);
						userDtos.setUserId(Integer.parseInt(data[5] + ""));
						userDtos.setFname(data[0] + "");
						userDtos.setLname(data[1] + "");
						userDtos.setViewId(data[2] + "");
						userDtos.setUserCategory((UserCategory) data[3]);
						userDtos.setStatus(data[4] + "");
						userDtos.setBoard(((Board) data[6]));
						userDtos.setLoginStatus(data[7]+"");
						if (data[4].equals(ACTIVE)) {
							userDtos.setAction(DESACTIVE);
						} else {
							userDtos.setAction(ACTIVE);
						}
						userDtosDetails.add(userDtos);
					}
					
				}

			} else {
				setValid(false);
				JSFMessagers.addErrorMessage(getProvider().getValue("com.server.side.internal.invalidchoice"));
			}

		} catch (Exception e) {
			setValid(false);
			JSFMessagers.addErrorMessage(getProvider().getValue("com.server.side.internal.error"));
			LOGGER.info(e.getMessage());
			e.printStackTrace();
		}
	}

	public void addcontacts() {
		HttpSession session = SessionUtils.getSession();
		// Get the values from the session
		users = (Users) session.getAttribute("userinfo");
	}

	public void showDatePanel() {

		if (selection.equals(Date_opt)) {
			renderDatePanel = true;
		}
	}

	public String saveUserNewContact() {

		try {

			try {

				Contact ct = new Contact();
			LOGGER.info("USER DETAILS:::::::::::"+contact.getEmail()+":::::::::"+users.getFname()+"::::"+users.getLname());
				if (null != contact.getEmail())
					ct = contactImpl.getModelWithMyHQL(new String[] { "email" }, new Object[] { contact.getEmail() },
							"from Contact");
				if (null != ct) {

					JSFMessagers.resetMessages();
					setValid(false);
					JSFMessagers.addErrorMessage(getProvider().getValue("error.server.side.dupicate.email"));
					LOGGER.info(CLASSNAME + "sivaserside validation :: email already  recorded in the system! ");
					return null;
				}
				ct = contactImpl.getModelWithMyHQL(new String[] { "phone" }, new Object[] { contact.getPhone() },
						"from Contact");
				if (null != ct) {

					JSFMessagers.resetMessages();
					setValid(false);
					JSFMessagers.addErrorMessage(getProvider().getValue("error.server.side.dupicate.phone.number"));
					LOGGER.info(CLASSNAME + "sivaserside validation :: phone number already  recorded in the system! ");
					return null;
				}

			} catch (Exception e) {
				JSFMessagers.resetMessages();
				setValid(false);
				JSFMessagers.addErrorMessage(getProvider().getValue("com.server.side.internal.error"));
				LOGGER.info(CLASSNAME + "" + e.getMessage());
				e.printStackTrace();
				return null;
			}
			// :::Method to get user's info through session:::::::::://
			addcontacts();
			// :::End of Method :::::::::://

			/* FormSampleController sample = new FormSampleController(); */
			SendSupportEmail support = new SendSupportEmail();
			if (null != users) {
				contact.setCreatedBy(usersSession.getViewId());
				contact.setCrtdDtTime(timestamp);
				contact.setGenericStatus(ACTIVE);
				contact.setUpDtTime(timestamp);
				contact.setUpdatedBy(usersSession.getViewId());
				LOGGER.info(users.getUserId() + "" + users.getFname() + ":::------->>>>>>User searched founded");
				contact.setUser(usersImpl.gettUserById(users.getUserId(), "userId"));
			
				contact.setUpdatedBy(usersSession.getViewId());
				// :::saving contact action:::::::::::://
				contactImpl.saveContact(contact);
				// :::::End of saving:::::::::::::// JSFMessagers.resetMessages();
				//JSFMessagers.resetMessages();
				JSFMessagers.resetMessages();
				setValid(true);
				JSFMessagers.addErrorMessage(getProvider().getValue("com.save.form.contact"));
				LOGGER.info(CLASSNAME + ":::Contact Details is saved"+contact.getEmail()+":::::::"+users.getFname()+":::"+users.getLname());
				boolean verifyemail = support.sendMailTestVersion(users.getFname(), users.getLname(), contact.getEmail());
				// ::: end sending email action:::::::::::://
				if (verifyemail) {
					LOGGER.info("returing values controller" + verifyemail);
					setValid(true);
					JSFMessagers.addErrorMessage(getProvider().getValue("com.server.side.email.notifications"));
					LOGGER.info(CLASSNAME + ":::Contact Details is saved");
				} else {
					JSFMessagers.resetMessages();
					setValid(false);
					JSFMessagers.addErrorMessage(getProvider().getValue("com.notifyError.representative.user"));
				}
			} else {
				JSFMessagers.resetMessages();
				setValid(false);
				JSFMessagers.addErrorMessage(getProvider().getValue("com.server.side.internal.errorsession"));
			}
			clearContactFuileds();
			return null;
		} catch (HibernateException e) {
			LOGGER.info(CLASSNAME + ":::Contact Details is fail with HibernateException  error");
			JSFMessagers.resetMessages();
			setValid(false);
			JSFMessagers.addErrorMessage(getProvider().getValue("com.server.side.internal.errorsession"));
			LOGGER.info(CLASSNAME + "" + e.getMessage());
			e.printStackTrace();
			return "";
		}

	}

	private void clearContactFuileds() {
		this.useremail = null;
		contact = new Contact();
		userIdNumber = 0;
		// usersDetails=null;
	}

	public String cancelContact(ContactDto contact) {
		contact.setEditable(false);
		// usersImpl.UpdateUsers(user);
		return null;

	}

	public void showRepresentContAvail() {
		this.renderRepContactDash = false;
		this.renderRepContactAvailTable = true;
	}

	public String changePage() {

		if (usersSession.getUserCategory().getUsercategoryName().equals(INSTITUTE_REP)) {
			return "/menu/ListOfUsers.xhtml?faces-redirect=true";
		}
		return null;
	}

	public void addOthercontacts() {
		HttpSession session = SessionUtils.getSession();
		// Get the values from the session
		contactDto = (ContactDto) session.getAttribute("continfo");
	}

	public void otherUserContact(ContactDto cont) {
		HttpSession sessionuser = SessionUtils.getSession();

		if (null != cont) {
			/*
			 * renderContactForm = true;
			 * 
			 * rendered = false; renderForeignCountry = false;
			 */

			// Session creation to get user info from dataTable row
			sessionuser.setAttribute("continfo", cont);
			addOthercontacts();
			this.renderOtherContForm = true;
			this.renderRepContactAvailTable = false;

		}

	}

	public String saveContactAction(ContactDto contact) {
		LOGGER.info("update  saveAction method");
		// get all existing value but set "editable" to false
		Contact cont = new Contact();
		cont = new Contact();
		cont = contactImpl.getContactById(contact.getContactId(), "contactId");

		LOGGER.info("here update sart for " + cont + " useriD " + cont.getContactId());

		contact.setEditable(false);
		cont.setContactDetails(contact.getContactDetails());
		cont.setEmail(contact.getEmail());
		cont.setPhone(contact.getPhone());
		cont.setUpdatedBy(usersSession.getViewId());
		cont.setUpDtTime(timestamp);
		contactImpl.UpdateContact(cont);
		JSFMessagers.resetMessages();
		setValid(true);
		JSFMessagers.addErrorMessage(getProvider().getValue("com.save.form.userupdate"));
		// return to current page
		return null;

	}

	public void editUserContact(Users user) {
		HttpSession sessionuser = SessionUtils.getSession();

		if (null != user)
			renderContactForm = true;
		rendered = false;
		// Session creation to get user info from dataTable row
		sessionuser.setAttribute("userinfo", user);
		LOGGER.info("Info Founded are userid:>>>>>>>>>>>>>>>>>>>>>>fname:" + user.getFname());
		addcontacts();
	}

	public String editContactAction(ContactDto contact) {

		contact.setEditable(true);
		// usersImpl.UpdateUsers(user);
		return null;
	}

	public String saveContact() throws Exception {
		try {

			try {

				Contact ct = new Contact();
				if (null != contact.getEmail())
					ct = contactImpl.getModelWithMyHQL(new String[] { "email" }, new Object[] { contact.getEmail() },
							"from Contact");
				if (null != ct) {

					JSFMessagers.resetMessages();
					setValid(false);
					JSFMessagers.addErrorMessage(getProvider().getValue("error.server.side.dupicate.email"));
					LOGGER.info(CLASSNAME + "sivaserside validation :: email already  recorded in the system! ");
					return null;
				}
				ct = contactImpl.getModelWithMyHQL(new String[] { "phone" }, new Object[] { contact.getPhone() },
						"from Contact");
				if (null != ct) {

					JSFMessagers.resetMessages();
					setValid(false);
					JSFMessagers.addErrorMessage(getProvider().getValue("error.server.side.dupicate.phone.number"));
					LOGGER.info(CLASSNAME + "sivaserside validation :: phone number already  recorded in the system! ");
					return null;
				}

			} catch (Exception e) {
				JSFMessagers.resetMessages();
				setValid(false);
				JSFMessagers.addErrorMessage(getProvider().getValue("com.server.side.internal.error"));
				LOGGER.info(CLASSNAME + "" + e.getMessage());
				e.printStackTrace();
				return null;
			}
			// :::Method to get user's info through session:::::::::://
			addOthercontacts();
			// :::End of Method :::::::::://

			/* FormSampleController sample = new FormSampleController(); */
			SendSupportEmail support = new SendSupportEmail();

			if (null != contactDto) {
				users = contactDto.getUser();

				contact.setCreatedBy(usersSession.getViewId());
				contact.setCrtdDtTime(timestamp);
				contact.setGenericStatus(ACTIVE);
				contact.setUpDtTime(timestamp);
				contact.setCreatedBy(usersSession.getViewId());
				contact.setCrtdDtTime(timestamp);
				contact.setGenericStatus(ACTIVE);
				contact.setUpDtTime(timestamp);
				LOGGER.info(users.getUserId() + "" + users.getFname() + ":::------->>>>>>User searched founded");
				contact.setUser(usersImpl.gettUserById(users.getUserId(), "userId"));
				// :::sending email action:::::::::::://
				contact.setUpdatedBy(usersSession.getViewId());
				// :::saving contact action:::::::::::://
				contactImpl.saveContact(contact);
				// :::::End of saving:::::::::::::// 
				JSFMessagers.resetMessages();
				setValid(true); //
				JSFMessagers.addErrorMessage(getProvider().getValue("com.save.form.contact"));
				LOGGER.info(CLASSNAME + ":::Other Contact:::::"+users.getFname()+"::::"+ users.getLname()+"::"+contact.getEmail());
				
				boolean verifyemail = support.sendMailTestVersion(users.getFname(), users.getLname(), contact.getEmail());
				// ::: end sending email action:::::::::::://
				if (verifyemail) {
					LOGGER.info("returing values controller" + verifyemail);
					setValid(true);
					JSFMessagers.addErrorMessage(getProvider().getValue("com.server.side.email.notifications"));
					LOGGER.info(CLASSNAME + ":::Contact Details is saved");
				} else {
					JSFMessagers.resetMessages();
					setValid(false);
					JSFMessagers.addErrorMessage(getProvider().getValue("com.notifyError.representative.user"));
				}
			} else {

				JSFMessagers.resetMessages();
				setValid(false);
				JSFMessagers.addErrorMessage(getProvider().getValue("com.server.side.internal.errorsession"));
			}
			clearContactFuileds();
			return null;

		} catch (HibernateException e) {
			LOGGER.info(CLASSNAME + ":::Contact Details is fail with HibernateException  error");
			JSFMessagers.resetMessages();
			setValid(false);
			JSFMessagers.addErrorMessage(getProvider().getValue("com.server.side.internal.errorsession"));
			LOGGER.info(CLASSNAME + "" + e.getMessage());
			e.printStackTrace();
			return "";
		}

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

	public Users getUsersSession() {
		return usersSession;
	}

	public void setUsersSession(Users usersSession) {
		this.usersSession = usersSession;
	}

	public int getUserIdNumber() {
		return userIdNumber;
	}

	public void setUserIdNumber(int userIdNumber) {
		this.userIdNumber = userIdNumber;
	}

	public List<Users> getUsersDetails() {
		return usersDetails;
	}

	public void setUsersDetails(List<Users> usersDetails) {
		this.usersDetails = usersDetails;
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

	public Province getProvince() {
		return province;
	}

	public void setProvince(Province province) {
		this.province = province;
	}

	public List<Province> getProvinceList() {
		return provinceList;
	}

	public void setProvinceList(List<Province> provinceList) {
		this.provinceList = provinceList;
	}

	public ProvinceImpl getProvImpl() {
		return provImpl;
	}

	public void setProvImpl(ProvinceImpl provImpl) {
		this.provImpl = provImpl;
	}

	public int getProvinceId() {
		return provinceId;
	}

	public void setProvinceId(int provinceId) {
		this.provinceId = provinceId;
	}

	public District getDistrict() {
		return district;
	}

	public void setDistrict(District district) {
		this.district = district;
	}

	public Sector getSector() {
		return sector;
	}

	public void setSector(Sector sector) {
		this.sector = sector;
	}

	public Cell getCell() {
		return cell;
	}

	public void setCell(Cell cell) {
		this.cell = cell;
	}

	public Village getVillage() {
		return village;
	}

	public void setVillage(Village village) {
		this.village = village;
	}

	public List<District> getDistrictList() {
		return districtList;
	}

	public void setDistrictList(List<District> districtList) {
		this.districtList = districtList;
	}

	public List<Sector> getSectorList() {
		return sectorList;
	}

	public void setSectorList(List<Sector> sectorList) {
		this.sectorList = sectorList;
	}

	public List<Cell> getCelList() {
		return celList;
	}

	public void setCelList(List<Cell> celList) {
		this.celList = celList;
	}

	public List<Village> getVillageList() {
		return villageList;
	}

	public void setVillageList(List<Village> villageList) {
		this.villageList = villageList;
	}

	public DistrictImpl getDistrictImpl() {
		return districtImpl;
	}

	public void setDistrictImpl(DistrictImpl districtImpl) {
		this.districtImpl = districtImpl;
	}

	public SectorImpl getSectorImpl() {
		return sectorImpl;
	}

	public void setSectorImpl(SectorImpl sectorImpl) {
		this.sectorImpl = sectorImpl;
	}

	public CellImpl getCellImpl() {
		return cellImpl;
	}

	public void setCellImpl(CellImpl cellImpl) {
		this.cellImpl = cellImpl;
	}

	public VillageImpl getVillageImpl() {
		return villageImpl;
	}

	public void setVillageImpl(VillageImpl villageImpl) {
		this.villageImpl = villageImpl;
	}

	public int getDistrictId() {
		return districtId;
	}

	public void setDistrictId(int districtId) {
		this.districtId = districtId;
	}

	public int getSectorId() {
		return sectorId;
	}

	public void setSectorId(int sectorId) {
		this.sectorId = sectorId;
	}

	public int getCellId() {
		return cellId;
	}

	public void setCellId(int cellId) {
		this.cellId = cellId;
	}

	public int getVillageId() {
		return villageId;
	}

	public void setVillageId(int villageId) {
		this.villageId = villageId;
	}

	public List<District> getDistrictByProv() {
		return districtByProv;
	}

	public void setDistrictByProv(List<District> districtByProv) {
		this.districtByProv = districtByProv;
	}

	public List<Sector> getSectorByDistrict() {
		return sectorByDistrict;
	}

	public void setSectorByDistrict(List<Sector> sectorByDistrict) {
		this.sectorByDistrict = sectorByDistrict;
	}

	public List<Cell> getCellBySector() {
		return cellBySector;
	}

	public void setCellBySector(List<Cell> cellBySector) {
		this.cellBySector = cellBySector;
	}

	public List<Village> getVillageByCell() {
		return villageByCell;
	}

	public void setVillageByCell(List<Village> villageByCell) {
		this.villageByCell = villageByCell;
	}

	public UserCategory getUsercat() {
		return usercat;
	}

	public void setUsercat(UserCategory usercat) {
		this.usercat = usercat;
	}

	public List<UserCategory> getCatDetails() {
		return catDetails;
	}

	public void setCatDetails(List<UserCategory> catDetails) {
		this.catDetails = catDetails;
	}

	public UserCategoryImpl getCatImpl() {
		return catImpl;
	}

	public void setCatImpl(UserCategoryImpl catImpl) {
		this.catImpl = catImpl;
	}

	public int getCategoryId() {
		return categoryId;
	}

	public void setCategoryId(int categoryId) {
		this.categoryId = categoryId;
	}

	public String getImageName() {
		return imageName;
	}

	public void setImageName(String imageName) {
		this.imageName = imageName;
	}

	public UploadedFile getImageUpload() {
		return imageUpload;
	}

	public void setImageUpload(UploadedFile imageUpload) {
		this.imageUpload = imageUpload;
	}

	public int getBoardId() {
		return boardId;
	}

	public void setBoardId(int boardId) {
		this.boardId = boardId;
	}

	public List<Board> getBoardList() {
		return boardList;
	}

	public void setBoardList(List<Board> boardList) {
		this.boardList = boardList;
	}

	public Board getBoard() {
		return board;
	}

	public void setBoard(Board board) {
		this.board = board;
	}

	public String getPassword() {
		return password;
	}

	public void setPassword(String password) {
		this.password = password;
	}

	public String getConfirmPswd() {
		return confirmPswd;
	}

	public void setConfirmPswd(String confirmPswd) {
		this.confirmPswd = confirmPswd;
	}

	public List<UserDto> getUserDtoDetails() {
		return userDtoDetails;
	}

	public void setUserDtoDetails(List<UserDto> userDtoDetails) {
		this.userDtoDetails = userDtoDetails;
	}

	public List<UserDto> getUserDtosDetails() {
		return userDtosDetails;
	}

	public void setUserDtosDetails(List<UserDto> userDtosDetails) {
		this.userDtosDetails = userDtosDetails;
	}

	public UserDto getUserDto() {
		return userDto;
	}

	public void setUserDto(UserDto userDto) {
		this.userDto = userDto;
	}

	public BoardImpl getBoardImpl() {
		return boardImpl;
	}

	public void setBoardImpl(BoardImpl boardImpl) {
		this.boardImpl = boardImpl;
	}

	public LoginImpl getLoginImpl() {
		return loginImpl;
	}

	public void setLoginImpl(LoginImpl loginImpl) {
		this.loginImpl = loginImpl;
	}

	public String getChoice() {
		return choice;
	}

	public void setChoice(String choice) {
		this.choice = choice;
	}

	public boolean isRendered() {
		return rendered;
	}

	public void setRendered(boolean rendered) {
		this.rendered = rendered;
	}

	public boolean isRenderForeignCountry() {
		return renderForeignCountry;
	}

	public void setRenderForeignCountry(boolean renderForeignCountry) {
		this.renderForeignCountry = renderForeignCountry;
	}

	public int getAge() {
		return age;
	}

	public void setAge(int age) {
		this.age = age;
	}

	public Date getDateofBirth() {
		return dateofBirth;
	}

	public void setDateofBirth(Date dateofBirth) {
		this.dateofBirth = dateofBirth;
	}

	public int getDays() {
		return days;
	}

	public void setDays(int days) {
		this.days = days;
	}

	public Date getTo() {
		return to;
	}

	public void setTo(Date to) {
		this.to = to;
	}

	public Date getFrom() {
		return from;
	}

	public void setFrom(Date from) {
		this.from = from;
	}

	public boolean isRenderDataTable() {
		return renderDataTable;
	}

	public void setRenderDataTable(boolean renderDataTable) {
		this.renderDataTable = renderDataTable;
	}

	public String getOption() {
		return option;
	}

	public void setOption(String option) {
		this.option = option;
	}

	public boolean isRendersaveButton() {
		return rendersaveButton;
	}

	public void setRendersaveButton(boolean rendersaveButton) {
		this.rendersaveButton = rendersaveButton;
	}

	public boolean isRenderprofile() {
		return renderprofile;
	}

	public void setRenderprofile(boolean renderprofile) {
		this.renderprofile = renderprofile;
	}

	public String getRedirect() {
		return redirect;
	}

	public void setRedirect(String redirect) {
		this.redirect = redirect;
	}

	public boolean isNextButoon() {
		return nextButoon;
	}

	public void setNextButoon(boolean nextButoon) {
		this.nextButoon = nextButoon;
	}

	public boolean isRenderBoard() {
		return renderBoard;
	}

	public void setRenderBoard(boolean renderBoard) {
		this.renderBoard = renderBoard;
	}

	public List<UserDto> getRepDtosDetails() {
		return repDtosDetails;
	}

	public void setRepDtosDetails(List<UserDto> repDtosDetails) {
		this.repDtosDetails = repDtosDetails;
	}

	public boolean isRenderEditedTableByDate() {
		return renderEditedTableByDate;
	}

	public void setRenderEditedTableByDate(boolean renderEditedTableByDate) {
		this.renderEditedTableByDate = renderEditedTableByDate;
	}

	public boolean isRenderEditedTableByBoard() {
		return renderEditedTableByBoard;
	}

	public void setRenderEditedTableByBoard(boolean renderEditedTableByBoard) {
		this.renderEditedTableByBoard = renderEditedTableByBoard;
	}

	public boolean isRenderDatePanel() {
		return renderDatePanel;
	}

	public void setRenderDatePanel(boolean renderDatePanel) {
		this.renderDatePanel = renderDatePanel;
	}

	public boolean isRenderBoardOption() {
		return renderBoardOption;
	}

	public void setRenderBoardOption(boolean renderBoardOption) {
		this.renderBoardOption = renderBoardOption;
	}

	public String getSelection() {
		return selection;
	}

	public void setSelection(String selection) {
		this.selection = selection;
	}

	public boolean isRenderHeading() {
		return renderHeading;
	}

	public void setRenderHeading(boolean renderHeading) {
		this.renderHeading = renderHeading;
	}

	public boolean isRenderRepTable() {
		return renderRepTable;
	}

	public void setRenderRepTable(boolean renderRepTable) {
		this.renderRepTable = renderRepTable;
	}

	public boolean isRenderRepContactDash() {
		return renderRepContactDash;
	}

	public void setRenderRepContactDash(boolean renderRepContactDash) {
		this.renderRepContactDash = renderRepContactDash;
	}

	public int getListrepSize() {
		return listrepSize;
	}

	public void setListrepSize(int listrepSize) {
		this.listrepSize = listrepSize;
	}

	public int getCount() {
		return count;
	}

	public void setCount(int count) {
		this.count = count;
	}

	public boolean isAvailrepSize() {
		return availrepSize;
	}

	public void setAvailrepSize(boolean availrepSize) {
		this.availrepSize = availrepSize;
	}

	public int getContactSize() {
		return contactSize;
	}

	public void setContactSize(int contactSize) {
		this.contactSize = contactSize;
	}

	public String getUseremail() {
		return useremail;
	}

	public void setUseremail(String useremail) {
		this.useremail = useremail;
	}

	public Contact getContact() {
		return contact;
	}

	public void setContact(Contact contact) {
		this.contact = contact;
	}

	public boolean isRenderContactForm() {
		return renderContactForm;
	}

	public void setRenderContactForm(boolean renderContactForm) {
		this.renderContactForm = renderContactForm;
	}

	public ContactImpl getContactImpl() {
		return contactImpl;
	}

	public void setContactImpl(ContactImpl contactImpl) {
		this.contactImpl = contactImpl;
	}

	public List<ContactDto> getContactDtoDetails() {
		return contactDtoDetails;
	}

	public void setContactDtoDetails(List<ContactDto> contactDtoDetails) {
		this.contactDtoDetails = contactDtoDetails;
	}

	public int getRepavail() {
		return repavail;
	}

	public void setRepavail(int repavail) {
		this.repavail = repavail;
	}

	public boolean isRenderRepContactAvailTable() {
		return renderRepContactAvailTable;
	}

	public void setRenderRepContactAvailTable(boolean renderRepContactAvailTable) {
		this.renderRepContactAvailTable = renderRepContactAvailTable;
	}

	public ContactDto getContactDto() {
		return contactDto;
	}

	public void setContactDto(ContactDto contactDto) {
		this.contactDto = contactDto;
	}

	public boolean isRenderOtherContForm() {
		return renderOtherContForm;
	}

	public void setRenderOtherContForm(boolean renderOtherContForm) {
		this.renderOtherContForm = renderOtherContForm;
	}

	public List<UserCategory> getUserCatDetails() {
		return userCatDetails;
	}

	public void setUserCatDetails(List<UserCategory> userCatDetails) {
		this.userCatDetails = userCatDetails;
	}

	public List<Users> getUseravail() {
		return useravail;
	}

	public void setUseravail(List<Users> useravail) {
		this.useravail = useravail;
	}

	public String getRange() {
		return range;
	}

	public void setRange(String range) {
		this.range = range;
	}

	public int getUserCatid() {
		return userCatid;
	}

	public void setUserCatid(int userCatid) {
		this.userCatid = userCatid;
	}
	public List<Contact> getContactDetails() {
		return contactDetails;
	}

	public void setContactDetails(List<Contact> contactDetails) {
		this.contactDetails = contactDetails;
	}


}
