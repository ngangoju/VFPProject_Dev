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
	private UserDto userDto;
	private List<Users> usersDetails = new ArrayList<Users>();
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
	private boolean rendered;
	private boolean renderForeignCountry;
	private String option;
	private Date dateofBirth;
	private int age;
	private int days;
	private Date to;
	private Date from;
	private boolean renderDataTable;

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
		try {
			catDetails = catImpl.getListWithHQL(SELECT_USERCATEGORY);
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
			userDtoDetails.add(userDto);	

		} catch (Exception e) {
			setValid(false);
			JSFMessagers.addErrorMessage(getProvider().getValue("com.server.side.internal.error"));
			LOGGER.info(e.getMessage());
			e.printStackTrace();
		}

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
						users.setBoard(boardImpl.getBoardById(boardId, "boardId"));
						users.setViewName(loginImpl.criptPassword(password));
						users.setStatus(DESACTIVE);
						usersImpl.saveUsers(users);
						JSFMessagers.resetMessages();
						setValid(true);
						JSFMessagers.addErrorMessage(getProvider().getValue("com.save.form.user"));
						LOGGER.info(CLASSNAME + ":::User Details is saved");
						clearUserFuileds();
						return "/menu/ViewUsersList.xhtml?faces-redirect=true";
					} else {
						users.setUserCategory(catImpl.getUserCategoryById(categoryId, "userCatid"));
						users.setBoard(boardImpl.getBoardById(boardId, "boardId"));
						users.setViewName(loginImpl.criptPassword(password));
						users.setStatus(DESACTIVE);
						usersImpl.saveUsers(users);
						JSFMessagers.resetMessages();
						setValid(true);
						JSFMessagers.addErrorMessage(getProvider().getValue("com.save.form.user"));
						LOGGER.info(CLASSNAME + ":::User Details is saved");
						clearUserFuileds();
						return "/menu/ViewUsersList.xhtml?faces-redirect=true";
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

	public void changeOption() {
		if (option.equals(Yes_Option)) {
			rendered = true;
		}else {
			rendered = false;
		}
	}

	public String getContextPath() {

		HttpServletRequest request = (HttpServletRequest) FacesContext.getCurrentInstance().getExternalContext()
				.getRequest();

		return request.getContextPath();
	}

	public void optionCombine() {
		rendered=false;
		renderForeignCountry=false;
	}
	public String cancel(UserDto user) {
		user.setEditable(false);
		optionCombine();
		// usersImpl.UpdateUsers(user);
		return null;

	}

	public String editAction(UserDto user) {

		user.setEditable(true);
		renderForeignCountry=true;
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
	public String saveAction(UserDto user) throws NoSuchAlgorithmException, IOException {
		LOGGER.info("update  saveAction method");
		/* System.out.println("**************update  saveAction method"); */
		// get all existing value but set "editable" to false

		if (user != null) {
			optionCombine();
			Users us = new Users();
			us = new Users();
			us = usersImpl.gettUserById(user.getUserId(), "userId");
			boolean valid=confirmPswd() ;
			LOGGER.info("here update sart for " + us + " useriD " + us.getUserId());
			LOGGER.info("++++++++++++++++++++++++++here update sart for " + us + " useriD " + us.getUserId());
			FileUploadController upload= new FileUploadController();
			
			/*String filename=upload.processImageUploaded();*/
			/*if(null!=filename) {
			LOGGER.info("File Name Uploaded ::::::::--->>"+filename);*/
			user.setEditable(false);
			us.setFname(user.getFname());
			us.setLname(user.getLname());
			us.setAddress(user.getAddress());
			/*us.setImage(filename);*/
			usersImpl.UpdateUsers(us);
			/*}*/
			
			
			if(valid) {
				us.setViewName(loginImpl.criptPassword(confirmPswd));
				usersImpl.UpdateUsers(us);
				JSFMessagers.resetMessages();
				setValid(true);
				JSFMessagers.addErrorMessage(getProvider().getValue("pswd.changed.message"));
			}else {
				JSFMessagers.resetMessages();
				setValid(false);
				JSFMessagers.addErrorMessage(getProvider().getValue("erropswd.changed.message"));
			}
			
			
			// return to current page
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
		usersImpl.UpdateUsers(us);
		displayUsersByDateBetween();
		// return to current page
		return "null";
		// return "/menu/ViewUsersList.xhtml?faces-redirect=true";

	}

	public String updateStatus(UserDto user) {
		LOGGER.info("update  saveAction method");
		// get all existing value but set "editable" to false
		Users us = new Users();
		us = new Users();
		if (user != null)
			us = usersImpl.gettUserById(user.getUserId(), "userId");
		if (us != null)
			LOGGER.info("here update sart for " + us + " useriD " + us.getStatus());

		if (user.getStatus().equals(ACTIVE)) {

			us.setStatus(DESACTIVE);

		} else {

			us.setStatus(ACTIVE);
		}
		usersImpl.UpdateUsers(us);
		displayUsersByDateBetween();
		// return to current page
		return "null";
		/* return "/menu/ViewUsersList.xhtml?faces-redirect=true"; */

	}

	public void updateTable() throws Exception {
		if (choice.equalsIgnoreCase(country_rw)) {

			rendered = true;
			renderForeignCountry = true;
		} else {
			rendered = false;
			renderForeignCountry = true;
		}

	}

	public void showDataTable() {

		if ((null != to) && (null != from)) {

			renderDataTable = true;
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
							"select us.fname,us.lname,us.viewId,us.userCategory,us.status,us.userId from Users us where us.createdDate between '"
									+ fmt.getMysqlFormatV2(from) + "' and  '" + fmt.getMysqlFormatV2(to) + "'")) {

						LOGGER.info("users::::::::::::::::::::::::::::::::::::::::::::::::>>" + data[0] + ":: "
								+ data[1] + "");
						UserDto userDtos = new UserDto();
						userDtos.setEditable(false);
						userDtos.setUserId(Integer.parseInt(data[5] + ""));
						userDtos.setFname(data[0] + "");
						userDtos.setLname(data[1] + "");
						userDtos.setViewId(data[2] + "");
						userDtos.setUserCategory(((UserCategory) data[3]));
						userDtos.setStatus(data[4] + "");
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

}
