package com.vfp.tres;

import java.io.IOException;
import java.io.InputStream;
import java.io.Serializable;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.sql.Timestamp;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.List;
import java.util.logging.Logger;

import javax.annotation.PostConstruct;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.ViewScoped;
import javax.faces.context.FacesContext;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;
import javax.servlet.http.Part;

import org.apache.commons.io.FilenameUtils;
import org.hibernate.HibernateException;
import org.primefaces.model.UploadedFile;

import tres.common.DbConstant;
import tres.common.Formating;
import tres.common.JSFBoundleProvider;
import tres.common.JSFMessagers;
import tres.common.SendEmail;
import tres.common.SessionUtils;
import tres.dao.impl.CellImpl;
import tres.dao.impl.ContactImpl;
import tres.dao.impl.CountryImpl;
import tres.dao.impl.DistrictImpl;
import tres.dao.impl.InstitutionImpl;
import tres.dao.impl.InstitutionRegRequestImpl;
import tres.dao.impl.ProvinceImpl;
import tres.dao.impl.SectorImpl;
import tres.dao.impl.UserImpl;
import tres.dao.impl.VillageImpl;
import tres.domain.Cell;
import tres.domain.Contact;
import tres.domain.Country;
import tres.domain.District;
import tres.domain.Institution;
import tres.domain.InstitutionRegistrationRequest;
import tres.domain.Province;
import tres.domain.Sector;
import tres.domain.Users;
import tres.domain.Village;
import tres.vfp.dto.InstitutionDto;
import tres.vfp.dto.UserDto;

@SuppressWarnings("unchecked")
@ManagedBean
@ViewScoped

public class InstitutionController implements Serializable, DbConstant {
	private static final Logger LOGGER = Logger.getLogger(Thread.currentThread().getStackTrace()[0].getClassName());
	private String CLASSNAME = "Institution :: InstitutionRequest ";
	private static final long serialVersionUID = 1L;
	/* to manage validation messages */
	private boolean isValid;
	private boolean nextpage = false;
	private boolean rendered;
	private String key;
	private boolean renderDiv;
	private boolean renderrejected;
	private boolean renderDivdashboard;
	private boolean renderallinstit;
	private String name;
	private Part file;
	/* end manage validation messages */
	private Institution institution;
	private InstitutionRegistrationRequest request;
	private Users usersSession;
	private int userIdNumber;
	private int institutionId;
	private Date from;
	private Date to;
	private Country country;
	private Village village;
	private Province province;
	private District district;
	private Sector sector;
	private Cell cell;
	private Contact contact;
	private int institutionID;
	private InstitutionDto institutionDto;
	private int pid;
	private int did;
	private int cid;
	private int vid;
	private int sid;
	private int cntryId;
	/* arrays */
	private List<Country> countries = new ArrayList<Country>();
	private List<Province> provinces = new ArrayList<Province>();
	private List<District> districts = new ArrayList<District>();
	private List<Sector> sectors = new ArrayList<Sector>();
	private List<Cell> cells = new ArrayList<Cell>();
	private List<Village> villages = new ArrayList<Village>();
	private List<InstitutionDto> institutionDtos = new ArrayList<InstitutionDto>();
	private List<InstitutionRegistrationRequest> Confirmedinstitutions = new ArrayList<InstitutionRegistrationRequest>();
	private List<Institution> institutions = new ArrayList<Institution>();
	private List<InstitutionRegistrationRequest> requests = new ArrayList<InstitutionRegistrationRequest>();
	private List<InstitutionRegistrationRequest> pendinGrequests = new ArrayList<InstitutionRegistrationRequest>();
	private List<InstitutionRegistrationRequest> validInstitution = new ArrayList<InstitutionRegistrationRequest>();
	/* class injection */
	JSFBoundleProvider provider = new JSFBoundleProvider();
	UserImpl usersImpl = new UserImpl();
	InstitutionImpl institutionImpl = new InstitutionImpl();
	InstitutionRegRequestImpl requestImpl = new InstitutionRegRequestImpl();
	Timestamp timestamp = new Timestamp(Calendar.getInstance().getTime().getTime());
	ProvinceImpl provImpl = new ProvinceImpl();
	DistrictImpl districtImpl = new DistrictImpl();
	SectorImpl sectorImpl = new SectorImpl();
	CellImpl cellImpl = new CellImpl();
	VillageImpl villageImpl = new VillageImpl();
	CountryImpl countryImpl = new CountryImpl();
	ContactImpl contactImpl = new ContactImpl();

	@SuppressWarnings("unchecked")
	@PostConstruct
	public void init() {
		HttpSession session = SessionUtils.getSession();
		usersSession = (Users) session.getAttribute("userSession");

		if (institution == null) {
			institution = new Institution();
		}
		if (country == null) {
			country = new Country();
		}

		if (request == null) {
			request = new InstitutionRegistrationRequest();
		}
		if (province == null) {

			province = new Province();
		}
		if (sector == null) {

			sector = new Sector();
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
		if (contact == null) {
			contact = new Contact();
		}
		try {
			countries = countryImpl.getListWithHQL("select f from Country f");
			provinces = provImpl.getListWithHQL("select f from Province f");
			// institutions = institutionImpl
			// .getListWithHQL("select f from Institution f where
			// institutionRepresenative_userId="
			// + usersSession.getUserId() + "");
			// institutions = institutionImpl.getGenericListWithHQLParameter(
			// new String[] { "institutionRepresenative_userId" }, new Object[] {
			// usersSession }, "Institution",
			// "institutionName asc");
			validInstitution = requestImpl.getGenericListWithHQLParameter(
					new String[] { "genericStatus", "instRegReqstStatus", "createdBy" },
					new Object[] { ACTIVE, ACCEPTED, usersSession.getViewId() }, "InstitutionRegistrationRequest",
					"instRegReqstDate desc");

		} catch (Exception e) {
			setValid(false);
			e.printStackTrace();
			JSFMessagers.addErrorMessage(getProvider().getValue("com.server.side.internal.error"));
			LOGGER.info(e.getMessage());
			e.printStackTrace();
		}

		try

		{

			// requests = requestImpl.getGenericListWithHQLParameter(
			// new String[] { "genericStatus", "instRegReqstStatus", "createdBy" },
			// new Object[] { ACTIVE, ACCEPTED, usersSession.getViewId() },
			// "InstitutionRegistrationRequest",
			// "instRegReqstDate asc");
		} catch (Exception e) {
			setValid(false);
			JSFMessagers.addErrorMessage(getProvider().getValue("com.server.side.internal.error"));
			LOGGER.info(e.getMessage());
			e.printStackTrace();
		}

	}

	public String saveInstitutionRequest() throws IOException {
		try {
			institution.setCreatedBy(usersSession.getViewId());
			institution.setCrtdDtTime(timestamp);
			institution.setGenericStatus(ACTIVE);
			institution.setUpDtTime(timestamp);
			institution.setInstitutionRepresenative(usersSession);
			institution.setUpdatedBy(usersSession.getViewId());
			institution.setInstitution(institutionImpl.getInstitutionById(institutionID, "institutionId"));
			institution.setCountry(countryImpl.getCountryById(cntryId, "taskId"));
			institution.setVillage(villageImpl.getVillageById(vid, "villageId"));
			institution.setUpdatedBy(usersSession.getViewId());
			institution.setInstitutionRegDate(timestamp);
			institution.setInstitutionLogo(processFileUpload());
			institutionImpl.saveInstitution(institution);
			request.setCreatedBy(usersSession.getViewId());
			request.setCrtdDtTime(timestamp);
			request.setGenericStatus(ACTIVE);
			request.setInstitution(institution);
			request.setInstRegReqstDate(timestamp);
			request.setUpdatedBy(usersSession.getViewId());
			request.setUpDtTime(timestamp);
			request.setInstRegReqstStatus("pending");
			requestImpl.saveInstitutionRegRequest(request);
			JSFMessagers.resetMessages();
			setValid(true);
			nextpage = false;
			JSFMessagers.addErrorMessage(getProvider().getValue("institutionController.saving.message"));
			LOGGER.info(CLASSNAME + ":::Institution request not sent");
			clearInstitutionFuileds();
			return "";
		} catch (HibernateException e) {
			LOGGER.info(CLASSNAME + ":::Institution request not sent with HibernateException  error");
			JSFMessagers.resetMessages();
			setValid(false);
			JSFMessagers.addErrorMessage(getProvider().getValue("institutionController.savingFail.message"));
			LOGGER.info(CLASSNAME + "" + e.getMessage());
			e.printStackTrace();
			return "";
		}

	}

	public void changeDistrict() {
		try {
			province = provImpl.getProvinceById(pid, "provenceId");
			districts = districtImpl.getGenericListWithHQLParameter(new String[] { "province" },
					new Object[] { province }, "District", "code asc");
		} catch (Exception e) {
			setValid(false);
			JSFMessagers.addErrorMessage(getProvider().getValue("com.server.side.internal.error"));
			LOGGER.info(e.getMessage());
			e.printStackTrace();
		}
	}

	public void changeSector() {
		try {
			district = districtImpl.getDistrictById(did, "districtId");
			sectors = sectorImpl.getGenericListWithHQLParameter(new String[] { "distric" }, new Object[] { district },
					"Sector", "code asc");
		} catch (Exception e) {
			setValid(false);
			JSFMessagers.addErrorMessage(getProvider().getValue("com.server.side.internal.error"));
			LOGGER.info(e.getMessage());
			e.printStackTrace();
		}
	}

	public void changeCell() {
		try {
			sector = sectorImpl.getSectorById(sid, "sectorId");
			cells = sectorImpl.getGenericListWithHQLParameter(new String[] { "sector" }, new Object[] { sector },
					"Cell", "Code asc");
		} catch (Exception e) {
			setValid(false);
			JSFMessagers.addErrorMessage(getProvider().getValue("com.server.side.internal.error"));
			LOGGER.info(e.getMessage());
			e.printStackTrace();
		}
	}

	public void changeVilages() {
		try {
			cell = cellImpl.getCellById(cid, "cellId");
			villages = sectorImpl.getGenericListWithHQLParameter(new String[] { "cell" }, new Object[] { cell },
					"Village", "Code asc");
		} catch (Exception e) {
			setValid(false);
			JSFMessagers.addErrorMessage(getProvider().getValue("com.server.side.internal.error"));
			LOGGER.info(e.getMessage());
			e.printStackTrace();
		}
	}

	public void institutonConfirmation(InstitutionRegistrationRequest reqst) {
		try {
			reqst.setInstRegReqstStatus(ACCEPTED);
			requestImpl.UpdateInstitRegReqsts(reqst);
			// sendEmail(contact.getEmail(), "request rejected",
			// "Your request have been rejected due to certain condition. try again later");
			JSFMessagers.resetMessages();
			setValid(true);
			JSFMessagers.addErrorMessage(getProvider().getValue("institutionController.confirm.message"));
			LOGGER.info(CLASSNAME + ":::Institution request not updated");
			clearInstitutionFuileds();
		} catch (Exception e) {
			setValid(false);
			JSFMessagers.addErrorMessage(getProvider().getValue("com.server.side.internal.error"));
			LOGGER.info(e.getMessage());
			e.printStackTrace();
		}
	}

	public void institutonRejected(InstitutionRegistrationRequest reqst) {
		try {
			reqst.setInstRegReqstStatus(REJECTED);
			reqst.setGenericStatus(DESACTIVE);
			requestImpl.UpdateInstitRegReqsts(reqst);
			contact = contactImpl.getModelWithMyHQL(new String[] { "user" }, new Object[] { usersSession }, "Contact");
			// sendEmail(contact.getEmail(), "request rejected",
			// "Your request have been rejected due to certain condition. try again later");
			JSFMessagers.resetMessages();
			setValid(true);
			JSFMessagers.addErrorMessage(getProvider().getValue("institutionController.reject.message"));
			LOGGER.info(CLASSNAME + ":::Institution request not updated");
			clearInstitutionFuileds();
		} catch (Exception e) {
			setValid(false);
			JSFMessagers.addErrorMessage(getProvider().getValue("com.server.side.internal.error"));
			LOGGER.info(e.getMessage());
			e.printStackTrace();
		}
	}

	private void clearInstitutionFuileds() {
		institution = new Institution();
		request = new InstitutionRegistrationRequest();
	}

	public String saveAction(InstitutionDto dto) {
		LOGGER.info("update  saveAction method");
		// get all existing value but set "editable" to false
		Institution insti = new Institution();
		insti = new Institution();
		insti = institutionImpl.getInstitutionById(dto.getInstitutionId(), "institutionId");
		LOGGER.info("here update sart for " + dto + " Institution id " + dto.getInstitutionId());
		dto.setEditable(false);
		insti.setInstitutionName(dto.getInstitutionName());
		insti.setCountry(dto.getCountry());
		insti.setUpdatedBy(usersSession.getViewId());
		insti.setUpDtTime(getTimestamp());
		insti.setVillage(dto.getVillage());
		insti.setInstitutionAddress(dto.getInstitutionAddress());
		insti.setInstitutionName(dto.getInstitutionName());
		institutionImpl.UpdateInstitution(insti);
		return null;
	}

	public void institutionViewBydate() {
		try {
			pendinGrequests = new ArrayList<InstitutionRegistrationRequest>();
			if ((to.after(from)) && (Formating.daysBetween(from, to) <= 30)) {
				try {
					pendinGrequests = new ArrayList<InstitutionRegistrationRequest>();
					Formating fmt = new Formating();
					for (Object[] data : requestImpl.reportList(
							"select i.instRegReqstId,i.instRegReqstDate from InstitutionRegistrationRequest i where i.instRegReqstDate between '"
									+ fmt.getMysqlFormatV2(from) + "' and '" + fmt.getMysqlFormatV2(to)
									+ "'  and i.instRegReqstStatus='acepted' and i.genericStatus='active' and createdBy='"
									+ usersSession.getViewId() + "'")) {
						InstitutionRegistrationRequest request = new InstitutionRegistrationRequest();
						pendinGrequests.add(requestImpl.getInstitutionRegRequestById(Integer.parseInt(data[0] + ""),
								"instRegReqstId"));
					}
					institutionDtos = display(pendinGrequests);
				} catch (Exception e) {
					e.printStackTrace();
				}
			} else {
				setValid(false);
				JSFMessagers.addErrorMessage(getProvider().getValue("Invalidrange"));
			}

		} catch (Exception e) {
			setValid(false);
			JSFMessagers.addErrorMessage(getProvider().getValue("com.server.side.internal.error"));
			LOGGER.info(e.getMessage());
			e.printStackTrace();

		}
	}

	public void renderProvMethod() {
		try {
			if (countryImpl.getCountryById(cntryId, "taskId").getCountryName_en().equals("RWANDA")) {
				rendered = true;
			} else {
				rendered = false;
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
		/* clear fields */
		institution.setInstitutionType(null);
		/* end clear field */

	}

	public List<InstitutionDto> display(List<InstitutionRegistrationRequest> institutionRegistrationRequests) {

		try

		{
			List<InstitutionDto> dtos = new ArrayList<InstitutionDto>();
			for (InstitutionRegistrationRequest inst : institutionRegistrationRequests) {
				InstitutionDto institutionDto = new InstitutionDto();
				institutionDto.setEditable(false);
				institutionDto.setInstitutionRegDate(inst.getInstitution().getInstitutionRegDate());
				institutionDto.setInstitutionId(inst.getInstitution().getInstitutionId());
				institutionDto.setInstitutionName(inst.getInstitution().getInstitutionName());
				institutionDto.setInstitution(inst.getInstitution());
				institutionDto.setInstitutionAddress(inst.getInstitution().getInstitutionAddress());
				institutionDto.setUser(inst.getInstitution().getInstitutionRepresenative());
				institutionDto.setCountry(inst.getInstitution().getCountry());
				institutionDto.setVillage(inst.getInstitution().getVillage());
				dtos.add(institutionDto);
			}
			return dtos;
		} catch (Exception e) {
			setValid(false);
			JSFMessagers.addErrorMessage(getProvider().getValue("com.server.side.internal.error"));
			LOGGER.info(e.getMessage());
			return null;
		}
	}

	public void displayRequestDiv() {
		pendinGrequests = new ArrayList<InstitutionRegistrationRequest>();
		renderDiv = true;
		renderrejected = false;
		renderDivdashboard = true;

	}

	public void displayRequest() {
		try {
			pendinGrequests = new ArrayList<InstitutionRegistrationRequest>();
			Formating fmt = new Formating();
			for (Object[] data : requestImpl.reportList(
					"select i.instRegReqstId,i.instRegReqstDate,i.institution from InstitutionRegistrationRequest i where i.instRegReqstDate between '"
							+ fmt.getMysqlFormatV2(from) + "' and '" + fmt.getMysqlFormatV2(to)
							+ "'  and i.instRegReqstStatus='pending' and i.genericStatus='active'")) {
				InstitutionRegistrationRequest request = new InstitutionRegistrationRequest();

				request.setInstRegReqstId(Integer.parseInt(data[0] + ""));
				request.setInstRegReqstDate(fmt.getMysqlDateFormt(data[1] + ""));
				request.setInstitution(((Institution) data[2]));
				pendinGrequests.add(request);
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	public void displayRejectedDiv() {
		pendinGrequests = new ArrayList<InstitutionRegistrationRequest>();
		renderrejected = true;
		renderDiv = false;
		renderDivdashboard = true;
	}

	public void displayRejected() {
		try {
			pendinGrequests = new ArrayList<InstitutionRegistrationRequest>();
			Formating fmt = new Formating();
			for (Object[] data : requestImpl.reportList(
					"select i.instRegReqstId,i.instRegReqstDate,i.institution from InstitutionRegistrationRequest i where i.instRegReqstDate between '"
							+ fmt.getMysqlFormatV2(from) + "' and '" + fmt.getMysqlFormatV2(to)
							+ "'  and i.instRegReqstStatus='rejected' and i.genericStatus='desactive'")) {
				InstitutionRegistrationRequest request = new InstitutionRegistrationRequest();

				request.setInstRegReqstId(Integer.parseInt(data[0] + ""));
				request.setInstRegReqstDate(fmt.getMysqlDateFormt(data[1] + ""));
				request.setInstitution(((Institution) data[2]));
				pendinGrequests.add(request);
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	public void displayAllInstitutionsDiv() {
		pendinGrequests = new ArrayList<InstitutionRegistrationRequest>();
		renderrejected = false;
		renderDiv = false;
		renderDivdashboard = true;
		renderallinstit = true;

	}

	public void displayAllInstitutions() {
		try {
			pendinGrequests = new ArrayList<InstitutionRegistrationRequest>();
			Formating fmt = new Formating();
			for (Object[] data : requestImpl.reportList(
					"select i.instRegReqstDate,i.instRegReqstId from InstitutionRegistrationRequest i where i.instRegReqstDate between '"
							+ fmt.getMysqlFormatV2(from) + "' and '" + fmt.getMysqlFormatV2(to)
							+ "'  and i.instRegReqstStatus='acepted' and i.genericStatus='active'")) {
				pendinGrequests.add(
						requestImpl.getInstitutionRegRequestById(Integer.parseInt(data[1] + ""), "instRegReqstId"));
			}
			institutionDtos = display(pendinGrequests);
			from = null;
			to = null;
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	public int countRequests() {
		try {
			pendinGrequests = requestImpl.getGenericListWithHQLParameter(
					new String[] { "genericStatus", "instRegReqstStatus" }, new Object[] { DESACTIVE, REJECTED },
					"InstitutionRegistrationRequest", "instRegReqstDate desc");
			return pendinGrequests.size();
		} catch (Exception e) {
			return 0;
		}
	}

	public int countRequestsrejected() {
		try {

			pendinGrequests = requestImpl.getGenericListWithHQLParameter(
					new String[] { "genericStatus", "instRegReqstStatus" }, new Object[] { DESACTIVE, REJECTED },
					"InstitutionRegistrationRequest", "instRegReqstDate desc");
			return pendinGrequests.size();
		} catch (Exception e) {
			return 0;
			// TODO: handle exception
		}
	}

	public int countRequestsAllacepted() {
		try {
			pendinGrequests = requestImpl.getGenericListWithHQLParameter(
					new String[] { "genericStatus", "instRegReqstStatus" }, new Object[] { ACTIVE, ACCEPTED },
					"InstitutionRegistrationRequest", "instRegReqstDate desc");
			institutionDtos = display(requests);
			return institutionDtos.size();
		} catch (Exception e) {
			return 0;
			// TODO: handle exception
		}
	}

	public String processFileUpload() throws IOException {

		Part uploadedFile = getFile();
		String url = getContextPath();
		final Path destination = Paths
				.get(url + "/resources/upload/" + FilenameUtils.getName(getSubmittedFileName(uploadedFile)));
		LOGGER.info(
				"Uploaded File name::------------>>>>>>" + FilenameUtils.getName(getSubmittedFileName(uploadedFile)));
		// When using servlet 3.1
		// final Path destination = Paths.get("c:/temp/"+
		// FilenameUtils.getName(uploadedFile.getSubmittedFileName()));

		InputStream bytes = null;

		if (null != uploadedFile) {

			bytes = uploadedFile.getInputStream(); //

			// Copies bytes to destination.
			Files.copy(bytes, destination);
		}

		return destination.toString();
	}

	public static String getSubmittedFileName(Part filePart) {
		String header = filePart.getHeader("content-disposition");
		if (header == null)
			return null;
		for (String headerPart : header.split(";")) {
			if (headerPart.trim().startsWith("filename")) {
				return headerPart.substring(headerPart.indexOf('=') + 1).trim().replace("\"", "");
			}
		}
		return null;
	}

	public String getContextPath() {

		HttpServletRequest request = (HttpServletRequest) FacesContext.getCurrentInstance().getExternalContext()
				.getRequest();

		return request.getContextPath();
	}

	public String getKey() {
		return key;
	}

	public void setKey(String key) {
		this.key = key;
	}

	public void renderNext() {
		nextpage = true;
	}

	public void renderBack() {
		nextpage = false;
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

	public Institution getInstitution() {
		return institution;
	}

	public void setInstitution(Institution institution) {
		this.institution = institution;
	}

	public InstitutionRegistrationRequest getRequest() {
		return request;
	}

	public void setRequest(InstitutionRegistrationRequest request) {
		this.request = request;
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

	public InstitutionImpl getInstitutionImpl() {
		return institutionImpl;
	}

	public void setInstitutionImpl(InstitutionImpl institutionImpl) {
		this.institutionImpl = institutionImpl;
	}

	public InstitutionRegRequestImpl getRequestImpl() {
		return requestImpl;
	}

	public void setRequestImpl(InstitutionRegRequestImpl requestImpl) {
		this.requestImpl = requestImpl;
	}

	public Timestamp getTimestamp() {
		return timestamp;
	}

	public void setTimestamp(Timestamp timestamp) {
		this.timestamp = timestamp;
	}

	public static Logger getLogger() {
		return LOGGER;
	}

	public static long getSerialversionuid() {
		return serialVersionUID;
	}

	public int getInstitutionId() {
		return institutionId;
	}

	public void setInstitutionId(int institutionId) {
		this.institutionId = institutionId;
	}

	public Country getCountry() {
		return country;
	}

	public void setCountry(Country country) {
		this.country = country;
	}

	public Village getVillage() {
		return village;
	}

	public void setVillage(Village village) {
		this.village = village;
	}

	public Province getProvince() {
		return province;
	}

	public void setProvince(Province province) {
		this.province = province;
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

	public int getInstitutionID() {
		return institutionID;
	}

	public void setInstitutionID(int institutionID) {
		this.institutionID = institutionID;
	}

	public List<Country> getCountries() {
		return countries;
	}

	public void setCountries(List<Country> countries) {
		this.countries = countries;
	}

	public List<Province> getProvinces() {
		return provinces;
	}

	public void setProvinces(List<Province> provinces) {
		this.provinces = provinces;
	}

	public List<District> getDistricts() {
		return districts;
	}

	public void setDistricts(List<District> districts) {
		this.districts = districts;
	}

	public List<Sector> getSectors() {
		return sectors;
	}

	public void setSectors(List<Sector> sectors) {
		this.sectors = sectors;
	}

	public List<Cell> getCells() {
		return cells;
	}

	public void setCells(List<Cell> cells) {
		this.cells = cells;
	}

	public List<Village> getVillages() {
		return villages;
	}

	public void setVillages(List<Village> villages) {
		this.villages = villages;
	}

	public ProvinceImpl getProvImpl() {
		return provImpl;
	}

	public void setProvImpl(ProvinceImpl provImpl) {
		this.provImpl = provImpl;
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

	public List<Institution> getInstitutions() {
		return institutions;
	}

	public void setInstitutions(List<Institution> institutions) {
		this.institutions = institutions;
	}

	public int getPid() {
		return pid;
	}

	public int getCid() {
		return cid;
	}

	public void setCid(int cid) {
		this.cid = cid;
	}

	public int getVid() {
		return vid;
	}

	public void setVid(int vid) {
		this.vid = vid;
	}

	public int getSid() {
		return sid;
	}

	public void setSid(int sid) {
		this.sid = sid;
	}

	public void setPid(int pid) {
		this.pid = pid;
	}

	public CountryImpl getCountryImpl() {
		return countryImpl;
	}

	public void setCountryImpl(CountryImpl countryImpl) {
		this.countryImpl = countryImpl;
	}

	public int getDid() {
		return did;
	}

	public void setDid(int did) {
		this.did = did;
	}

	public int getCntryId() {
		return cntryId;
	}

	public void setCntryId(int cntryId) {
		this.cntryId = cntryId;
	}

	public List<InstitutionRegistrationRequest> getRequests() {
		return requests;
	}

	public void setRequests(List<InstitutionRegistrationRequest> requests) {
		this.requests = requests;
	}

	public List<InstitutionRegistrationRequest> getConfirmedinstitutions() {
		return Confirmedinstitutions;
	}

	public void setConfirmedinstitutions(List<InstitutionRegistrationRequest> confirmedinstitutions) {
		Confirmedinstitutions = confirmedinstitutions;
	}

	public List<InstitutionRegistrationRequest> getValidInstitution() {
		return validInstitution;
	}

	public void setValidInstitution(List<InstitutionRegistrationRequest> validInstitution) {
		this.validInstitution = validInstitution;
	}

	public Contact getContact() {
		return contact;
	}

	public void setContact(Contact contact) {
		this.contact = contact;
	}

	public InstitutionDto getInstitutionDto() {
		return institutionDto;
	}

	public List<InstitutionDto> getInstitutionDtos() {
		return institutionDtos;
	}

	public void setInstitutionDtos(List<InstitutionDto> institutionDtos) {
		this.institutionDtos = institutionDtos;
	}

	public Date getFrom() {
		return from;
	}

	public void setFrom(Date from) {
		this.from = from;
	}

	public Date getTo() {
		return to;
	}

	public void setTo(Date to) {
		this.to = to;
	}

	public boolean isRendered() {
		return rendered;
	}

	public void setRendered(boolean rendered) {
		this.rendered = rendered;
	}

	public void setInstitutionDto(InstitutionDto institutionDto) {
		this.institutionDto = institutionDto;
	}

	public ContactImpl getContactImpl() {
		return contactImpl;
	}

	public void setContactImpl(ContactImpl contactImpl) {
		this.contactImpl = contactImpl;
	}

	public String cancel(InstitutionDto institutionDto) {
		institutionDto.setEditable(true);
		return "/menu/ViewInstitutionProfile.xhtml";

	}

	public String editAction(InstitutionDto institutionDto) {
		institutionDto.setEditable(true);
		return null;
	}

	public boolean isNextpage() {
		return nextpage;
	}

	public void setNextpage(boolean nextpage) {
		this.nextpage = nextpage;
	}

	public List<InstitutionRegistrationRequest> getPendinGrequests() {
		return pendinGrequests;
	}

	public void setPendinGrequests(List<InstitutionRegistrationRequest> pendinGrequests) {
		this.pendinGrequests = pendinGrequests;
	}

	public boolean isRenderDiv() {
		return renderDiv;
	}

	public void setRenderDiv(boolean renderDiv) {
		this.renderDiv = renderDiv;
	}

	public boolean isRenderrejected() {
		return renderrejected;
	}

	public void setRenderrejected(boolean renderrejected) {
		this.renderrejected = renderrejected;
	}

	public boolean isRenderDivdashboard() {
		return renderDivdashboard;
	}

	public void setRenderDivdashboard(boolean renderDivdashboard) {
		this.renderDivdashboard = renderDivdashboard;
	}

	public boolean isRenderallinstit() {
		return renderallinstit;
	}

	public void setRenderallinstit(boolean renderallinstit) {
		this.renderallinstit = renderallinstit;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public Part getFile() {
		return file;
	}

	public void setFile(Part file) {
		this.file = file;
	}

}
