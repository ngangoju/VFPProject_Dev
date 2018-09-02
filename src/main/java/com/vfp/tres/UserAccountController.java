package com.vfp.tres;

import java.io.IOException;
import java.io.InputStream;
import java.io.Serializable;
import java.io.UncheckedIOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.sql.Timestamp;
import java.util.ArrayList;
import java.util.Calendar;
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
import tres.common.JSFBoundleProvider;
import tres.common.JSFMessagers;
import tres.common.SessionUtils;
import tres.dao.impl.BoardImpl;
import tres.dao.impl.CellImpl;
import tres.dao.impl.DistrictImpl;
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
	/* class injection */
	JSFBoundleProvider provider = new JSFBoundleProvider();
	UserImpl usersImpl = new UserImpl();
	ProvinceImpl provImpl = new ProvinceImpl();
	DistrictImpl districtImpl = new DistrictImpl();
	SectorImpl sectorImpl = new SectorImpl();
	CellImpl cellImpl = new CellImpl();
	VillageImpl villageImpl = new VillageImpl();
	UserCategoryImpl catImpl = new UserCategoryImpl();
	
	Timestamp timestamp = new Timestamp(Calendar.getInstance().getTime().getTime());

	@SuppressWarnings("unchecked")
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
		try {
			usersDetails = usersImpl.getGenericListWithHQLParameter(new String[] { "genericStatus", "status" },
					new Object[] { ACTIVE, ACTIVE }, "Users", "userId desc");
			catDetails = catImpl.getGenericListWithHQLParameter(new String[] { "genericStatus" },
					new Object[] { ACTIVE }, "UserCategory", "userCatid desc");

			/*
			 * provinceList = provImpl.getGenericListWithHQLParameter(new String[] {
			 * "genericStatus" }, new Object[] { ACTIVE }, "Province", "provenceId asc");
			 */

			provinceList = provImpl.getListWithHQL("select f from Province f");
			districtList = districtImpl.getGenericListWithHQLParameter(new String[] { "genericStatus" },
					new Object[] { ACTIVE }, "District", "districtId asc");
			sectorList = sectorImpl.getGenericListWithHQLParameter(new String[] { "genericStatus" },
					new Object[] { ACTIVE }, "Sector", "sectorId asc");

			celList = cellImpl.getGenericListWithHQLParameter(new String[] { "genericStatus" }, new Object[] { ACTIVE },
					"Cell", "cellId asc");

			villageList = villageImpl.getGenericListWithHQLParameter(new String[] { "genericStatus" },
					new Object[] { ACTIVE }, "Village", "villageId asc");
		} catch (Exception e) {
			setValid(false);
			JSFMessagers.addErrorMessage(getProvider().getValue("com.server.side.internal.error"));
			LOGGER.info(e.getMessage());
			e.printStackTrace();
		}

	}

	// Method to display all district by province
	public void changeDistrict() {
		try {
			/*
			 * String code = String.valueOf(provinceId);
			 * System.out.println("Province Id goes here:-------------------->>>" +
			 * provinceId); LOGGER.info(provinceId + ":::Province Details");
			 */

			province = provImpl.getProvinceById(provinceId, "provenceId");
			districtByProv = districtImpl.getGenericListWithHQLParameter(new String[] { "genericStatus", "province" },
					new Object[] { ACTIVE, province }, "District", "districtId asc");
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
			sectorByDistrict = sectorImpl.getGenericListWithHQLParameter(new String[] { "genericStatus", "distric" },
					new Object[] { ACTIVE, district }, "Sector", "sectorId asc");
		} catch (Exception e) {
			setValid(false);
			JSFMessagers.addErrorMessage(getProvider().getValue("com.server.side.internal.error"));
			LOGGER.info(e.getMessage());
			e.printStackTrace();
		}

	}

	// Method to display all cell by sector

	public void changeCell() {

		try {

			sector = sectorImpl.getSectorById(sectorId, "sectorId");
			cellBySector = cellImpl.getGenericListWithHQLParameter(new String[] { "genericStatus", "sector" },
					new Object[] { ACTIVE, sector }, "Cell", "cellId asc");
		} catch (Exception e) {
			setValid(false);
			JSFMessagers.addErrorMessage(getProvider().getValue("com.server.side.internal.error"));
			LOGGER.info(e.getMessage());
			e.printStackTrace();
		}

	}
	// Method to display all village by Cell

	public void changeVillage() {

		try {

			cell = cellImpl.getCellById(cellId, "cellId");
			villageByCell = villageImpl.getGenericListWithHQLParameter(new String[] { "genericStatus", "cell" },
					new Object[] { ACTIVE, cell }, "Village", "villageId asc");
		} catch (Exception e) {
			setValid(false);
			JSFMessagers.addErrorMessage(getProvider().getValue("com.server.side.internal.error"));
			LOGGER.info(e.getMessage());
			e.printStackTrace();
		}

	}

	public String saveUserInfo() throws IOException {
		String url = getContextPath();
		System.out.print("+++++++++++++++++:" + url + "/");
		try {
			/*if (imageUpload != null) {
				if (imageUpload.getContentType().startsWith("image")) {

					InputStream input = imageUpload.getInputstream();
					imageName = UUID.randomUUID().toString() + "." + imageUpload.getFileName();
					Path path = Paths.get(url + "/resources/image/" + imageName);
					Files.copy(input, path);*/
					users.setImage("us.png");
					users.setCreatedBy(usersSession.getViewId());
					users.setCrtdDtTime(timestamp);
					users.setCreatedDate(timestamp);
					users.setGenericStatus(ACTIVE);
					users.setUpdatedBy(usersSession.getViewId());
					users.setCrtdDtTime(timestamp);
					users.setVillage(villageImpl.getVillageById(villageId, "villageId"));
					users.setUserCategory(catImpl.getUserCategoryById(categoryId, "userCatid"));
					usersImpl.saveUsers(users);

					JSFMessagers.resetMessages();
					setValid(true);
					JSFMessagers.addErrorMessage(getProvider().getValue("com.save.form.user"));
					LOGGER.info(CLASSNAME + ":::User Details is saved");
					clearUserFuileds();
					return "";
			/*	}
			}*/

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

	public String getContextPath() {

		HttpServletRequest request = (HttpServletRequest) FacesContext.getCurrentInstance().getExternalContext()
				.getRequest();

		return request.getContextPath();
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

}
