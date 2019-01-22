package com.vfp.tres;

import java.io.Serializable;
import java.nio.file.attribute.UserPrincipalLookupService;
import java.sql.Timestamp;
import java.text.ParseException;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.List;
import java.util.logging.Logger;
import javax.annotation.PostConstruct;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.ViewScoped;
import javax.servlet.http.HttpSession;

import org.hibernate.HibernateException;

import tres.common.DbConstant;
import tres.common.Formating;
import tres.common.JSFBoundleProvider;
import tres.common.JSFMessagers;
import tres.common.SessionUtils;
import tres.dao.impl.CellImpl;
import tres.dao.impl.ContactImpl;
import tres.dao.impl.CountryImpl;
import tres.dao.impl.DistrictImpl;
import tres.dao.impl.InstitutionContactImpl;
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
import tres.domain.InstitutionContact;
import tres.domain.InstitutionRegistrationRequest;
import tres.domain.Province;
import tres.domain.Sector;
import tres.domain.Users;
import tres.domain.Village;
import tres.vfp.dto.InstitutionDto;

@SuppressWarnings("unchecked")
@ViewScoped
@ManagedBean
public class InstReqController implements Serializable, DbConstant {
	private static final Logger LOGGER = Logger.getLogger(Thread.currentThread().getStackTrace()[0].getClassName());
	private String CLASSNAME = "InstReqController :: ";
	private static final long serialVersionUID = 1L;
	/* to manage validation messages */
	private boolean isValid, selctDiv, nextpage, boolBottn, boolBottEnn, institutionPanel, requestPanel, defaultDiv,rendered;
	/* end manage validation messages */
	private Users usersSession;;
	private InstitutionRegistrationRequest request;
	private Institution institution;
	private Contact contact;

	private int nmbrInst;

	private Date from;
	private Date to;

	private List<InstitutionRegistrationRequest> requests = new ArrayList<InstitutionRegistrationRequest>();
	private List<Institution> institutions = new ArrayList<Institution>();
	private List<InstitutionDto> dtos = new ArrayList<InstitutionDto>();

	private List<Country> countries = new ArrayList<Country>();
	private List<Province> provinces = new ArrayList<Province>();
	private List<District> districts = new ArrayList<District>();
	private List<Sector> sectors = new ArrayList<Sector>();
	private List<Cell> cells = new ArrayList<Cell>();
	private List<Village> villages = new ArrayList<Village>();

	private Country country;
	private Village village;
	private Province province;
	private District district;
	private Sector sector;
	private Cell cell;
	private int cntryId;

	private int pid;
	private int did;
	private int cid;
	private int vid;
	private int sid;

	Timestamp timestamp = new Timestamp(Calendar.getInstance().getTime().getTime());
	JSFBoundleProvider provider = new JSFBoundleProvider();
	UserImpl usersImpl = new UserImpl();
	InstitutionImpl institutionImpl = new InstitutionImpl();
	InstitutionRegRequestImpl requestImpl = new InstitutionRegRequestImpl();
	InstitutionContactImpl instContImpl = new InstitutionContactImpl();

	ProvinceImpl provImpl = new ProvinceImpl();
	DistrictImpl districtImpl = new DistrictImpl();
	SectorImpl sectorImpl = new SectorImpl();
	CellImpl cellImpl = new CellImpl();
	VillageImpl villageImpl = new VillageImpl();
	CountryImpl countryImpl = new CountryImpl();
	ContactImpl contactImpl = new ContactImpl();

	@PostConstruct
	public void init() {
		HttpSession session = SessionUtils.getSession();
		usersSession = (Users) session.getAttribute("userSession");

		if (institution == null) {
			institution = new Institution();
		}

		if (request == null) {
			request = new InstitutionRegistrationRequest();
		}

		if (contact == null) {
			contact = new Contact();
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
		try {
			requests = requestImpl.getGenericListWithHQLParameter(
					new String[] { "genericStatus", "instRegReqstStatus" }, new Object[] { ACTIVE, PENDING },
					"InstitutionRegistrationRequest", "instRegReqstId asc");
			institutions = institutionImpl.getListWithHQL("select f from Institution f");
			nmbrInst = institutions.size();
			dtos = display();
			countries = countryImpl.getListWithHQL("select f from Country f");
			provinces = provImpl.getListWithHQL("select f from Province f");
		} catch (Exception e) {
			setValid(false);
			e.printStackTrace();
			JSFMessagers.addErrorMessage(getProvider().getValue("com.server.side.internal.error"));
			LOGGER.info(e.getMessage());
			e.printStackTrace();
		}
	}

	/* Method for displaying request starts */

	public void displayRequests() throws NumberFormatException, ParseException {
		try {
			Formating fmt = new Formating();
			for (Object[] data : requestImpl.reportList(
					"select i.instRegReqstId,i.instRegReqstDate from InstitutionRegistrationRequest i where i.instRegReqstDate between '"
							+ fmt.getMysqlFormatV2(from) + "' and '" + fmt.getMysqlFormatV2(to)
							+ "' and i.genericStatus='active'")) {
				InstitutionRegistrationRequest request = new InstitutionRegistrationRequest();
				request = requestImpl.getInstitutionRegRequestById(Integer.parseInt(data[0] + ""), "instRegReqstId");
				requests.add(request);
			}
			LOGGER.info("Date FROM::::" + fmt.getMysqlFormatV2(from));
			LOGGER.info("Date TO::::" + fmt.getMysqlFormatV2(to));
			selctDiv = true;
		} catch (Exception e) {
			setValid(false);
			e.printStackTrace();
			JSFMessagers.addErrorMessage(getProvider().getValue("com.server.side.internal.error"));
			LOGGER.info(e.getMessage());
			e.printStackTrace();
		}
	}
	/* Method for displaying request ends */

	/* Confirmation method starts */
	public String confirmRequest(InstitutionRegistrationRequest request) {
		try {
			request.setInstRegReqstStatus(ACCEPTED);
			request.setUpDtTime(timestamp);
			request.setUpdatedBy(usersSession.getViewId());
			requestImpl.UpdateInstitRegReqsts(request);
			InstitutionSave(request);
			SendSupportEmail sendMail = new SendSupportEmail();
			sendMail.sendMailForInstitution(request.getInstitutionRepresenative().getFname(),
					request.getInstitutionRepresenative().getLname(), getContactEmail(request), "Confirmation",
					"Your Institution registration has been Confirmed.");
			setValid(true);
			JSFMessagers.addErrorMessage(getProvider().getValue("com.server.side.institution.confrmation"));
			requests = requestImpl.getGenericListWithHQLParameter(
					new String[] { "genericStatus", "instRegReqstStatus" }, new Object[] { ACTIVE, PENDING },
					"InstitutionRegistrationRequest", "instRegReqstId asc");
			return "loadUserInformationsController.getContextPath()}/menu/manageInstitutionRegistration.xhtml";
		} catch (Exception e) {
			setValid(false);
			e.printStackTrace();
			JSFMessagers.addErrorMessage(getProvider().getValue("com.server.side.internal.error"));
			LOGGER.info(e.getMessage());
			e.printStackTrace();
			return "loadUserInformationsController.getContextPath()}/menu/manageInstitutionRegistration.xhtml";
		}
	}

	// reject
	/* Confirmation method starts */
	public String rejectRequest(InstitutionRegistrationRequest request) {
		try {
			request.setInstRegReqstStatus(REJECTED);
			request.setUpDtTime(timestamp);
			request.setUpdatedBy(usersSession.getViewId());
			requestImpl.UpdateInstitRegReqsts(request);
			InstitutionSave(request);
			SendSupportEmail sendMail = new SendSupportEmail();
			sendMail.sendMailForInstitution(request.getInstitutionRepresenative().getFname(),
					request.getInstitutionRepresenative().getLname(), getContactEmail(request), "Confirmation",
					"Your Institution registration has been Rejected.");
			setValid(true);
			JSFMessagers.addErrorMessage(getProvider().getValue("Institution was Rejected Successfuly"));
			requests = requestImpl.getGenericListWithHQLParameter(
					new String[] { "genericStatus", "instRegReqstStatus" }, new Object[] { ACTIVE, PENDING },
					"InstitutionRegistrationRequest", "instRegReqstId asc");
			return "loadUserInformationsController.getContextPath()}/menu/manageInstitutionRegistration.xhtml";
		} catch (Exception e) {
			setValid(false);
			e.printStackTrace();
			JSFMessagers.addErrorMessage(getProvider().getValue("com.server.side.internal.error"));
			LOGGER.info(e.getMessage());
			e.printStackTrace();
			return "loadUserInformationsController.getContextPath()}/menu/manageInstitutionRegistration.xhtml";
		}
	}

	// returning email
	public String getContactEmail(InstitutionRegistrationRequest instReg) {

		try {
			Contact cnt = new Contact();
			cnt = contactImpl.getModelWithMyHQL(new String[] { "user" },
					new Object[] { instReg.getInstitutionRepresenative() }, "from Contact");
			return cnt.getEmail();
		} catch (Exception e) {
			e.printStackTrace();
			return "";
		}
	}

	/* Confirmation method ends */
	/* saving Institution starts */
	public String InstitutionSave(InstitutionRegistrationRequest request) {
		try {
			institution.setBranch(request.getInstitution());
			institution.setCreatedBy(request.getCreatedBy());
			institution.setGenericStatus(ACTIVE);
			institution.setCrtdDtTime(timestamp);
			institution.setInstitutionRegDate(timestamp);
			institution.setInstitutionType(request.getInstRegReqstType());
			institution.setInstLogo("waiting");
			institution.setRequest(request);
			institution.setUpdatedBy(usersSession.getViewId());
			institution.setUpDtTime(timestamp);
			institutionImpl.saveInstitution(institution);
			nextpage = true;
			LOGGER.info("Test Institution Saved");
			JSFMessagers.resetMessages();
			setValid(true);
			JSFMessagers.addErrorMessage(getProvider().getValue("institutionController.saving.message"));
			LOGGER.info(CLASSNAME + ":::Institution saved");
			LOGGER.info("Institution saved:::::::::" + request.getInstRegReqstType());
			return "";
		} catch (Exception e) {
			setValid(false);
			e.printStackTrace();
			JSFMessagers.addErrorMessage(getProvider().getValue("com.server.side.internal.error"));
			LOGGER.info(e.getMessage());
			e.printStackTrace();
			return "";
		}
	}

	// boolean method
	public void rendButton(Institution inst) {
		if (inst.getGenericStatus().equals(ACTIVE)) {
			boolBottEnn = true;
		} else {
			boolBottn = true;
		}

	}

	// block institution

	public void blockInstitution(InstitutionDto instdto) {
		try {
			Institution inst = new Institution();
			inst = institutionImpl.getInstitutionById(instdto.getInstitutionId(), "institutionId");
			inst.setGenericStatus(DISABLE);
			inst.setUpdatedBy(usersSession.getViewId());
			inst.setUpDtTime(timestamp);
			institutionImpl.UpdateInstitution(inst);
			setValid(true);
			JSFMessagers.addErrorMessage(getProvider().getValue("Institution Blocked"));
			dtos = display();
		} catch (Exception e) {
			setValid(false);
			e.printStackTrace();
			JSFMessagers.addErrorMessage(getProvider().getValue("com.server.side.internal.error"));
			LOGGER.info(e.getMessage());
			e.printStackTrace();
		}
	}

	// activate
	public void activeInstitution(InstitutionDto instdto) {
		try {
			Institution inst = new Institution();
			inst = institutionImpl.getInstitutionById(instdto.getInstitutionId(), "institutionId");
			inst.setGenericStatus(ACTIVE);
			inst.setUpdatedBy(usersSession.getViewId());
			inst.setUpDtTime(timestamp);
			institutionImpl.UpdateInstitution(inst);
			setValid(true);
			JSFMessagers.addErrorMessage(getProvider().getValue("Institution Blocked"));
			dtos = display();
		} catch (Exception e) {
			setValid(false);
			e.printStackTrace();
			JSFMessagers.addErrorMessage(getProvider().getValue("com.server.side.internal.error"));
			LOGGER.info(e.getMessage());
			e.printStackTrace();
		}
	}

	// phone
	public String getContactPhone(InstitutionDto inst) {

		try {
			InstitutionContact cnt1 = new InstitutionContact();
			cnt1 = instContImpl.getModelWithMyHQL(new String[] { "institution" },
					new Object[] { institutionImpl.getInstitutionById(inst.getInstitutionId(), "institutionId") },
					"from InstitutionContact");
			if (cnt1 != null) {
				return cnt1.getPhone();
			} else {
				return "empty";
			}
		} catch (Exception e) {
			e.printStackTrace();
			return "";
		}
	}

	// Email
	public String getContactEmail(InstitutionDto inst) {

		try {
			InstitutionContact cnt1 = new InstitutionContact();
			cnt1 = instContImpl.getModelWithMyHQL(new String[] { "institution" },
					new Object[] { institutionImpl.getInstitutionById(inst.getInstitutionId(), "institutionId") },
					"from InstitutionContact");
			if (cnt1 != null) {
				return cnt1.getEmail();
			} else {
				return "empty";
			}
		} catch (Exception e) {
			e.printStackTrace();
			return "";
		}
	}

	// revers method for displaying request panel
	public void displayRequestDivReverse() {
		requestPanel = false;
		defaultDiv = false;
	}

	// method for displaying institution panel
	public void displayInstitutionDivReverse() {
		institutionPanel = false;
		defaultDiv = false;
	}

	// method for displaying request panel
	public void displayRequestDiv() {
		requestPanel = true;
		defaultDiv = true;
	}

	// method for displaying institution panel
	public void displayInstitutionDiv() {
		institutionPanel = true;
		defaultDiv = true;
	}

	// counting request
	public int countRequests() {
		int a = 0;
		try {
			for (Object[] data : requestImpl.reportList(
					"select count(*),i.instRegReqstStatus from InstitutionRegistrationRequest i where i.instRegReqstStatus='"
							+ PENDING + "' and i.genericStatus='" + ACTIVE + "'")) {
				a = Integer.parseInt(data[0] + "");
			}
			return a;
		} catch (Exception e) {
			e.printStackTrace();
			return 0;
		}
	}

	// counting institution

	public int countInstitution() {
		int a = 0;
		try {
			for (Object[] data : institutionImpl.reportList("select count(*) as amount from Institution")) {
				a = Integer.parseInt(data[0] + "");
			}
			LOGGER.info("Number of institution:::" + a);
			return a;
		} catch (Exception e) {
			e.printStackTrace();
			return 0;
		}
	}

	// dto
	@SuppressWarnings("unchecked")
	public List<InstitutionDto> display() {
		try {
			institutions = institutionImpl.getListWithHQL("select f from Institution f");
			List<InstitutionDto> dtos = new ArrayList<InstitutionDto>();
			for (Institution inst : institutions) {
				InstitutionDto institutionDto = new InstitutionDto();
				if (inst.getGenericStatus().equals(ACTIVE)) {
					institutionDto.setEditable(false);
				} else {
					institutionDto.setEditable(true);
				}
				institutionDto.setInstitutionRegDate(inst.getInstitutionRegDate());
				institutionDto.setInstitutionId(inst.getInstitutionId());
				institutionDto.setBranch(inst.getBranch());
				institutionDto.setRequest(inst.getRequest());
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

	/* saving Institution ends */

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

	public void renderProvMethod() {
		try {
			country = countryImpl.getCountryById(cntryId, "taskId");
			if (country != null) {
				if (country.getCountryName_en().equals("RWANDA")) {
					rendered = true;
				} else {
					rendered = false;
				}
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
		/* clear fields */
		institution.setInstitutionType(null);
		/* end clear field */

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

	public JSFBoundleProvider getProvider() {
		return provider;
	}

	public void setProvider(JSFBoundleProvider provider) {
		this.provider = provider;
	}

	public boolean isValid() {
		return isValid;
	}

	public void setValid(boolean isValid) {
		this.isValid = isValid;
	}

	public String getCLASSNAME() {
		return CLASSNAME;
	}

	public void setCLASSNAME(String cLASSNAME) {
		CLASSNAME = cLASSNAME;
	}

	public Users getUsersSession() {
		return usersSession;
	}

	public void setUsersSession(Users usersSession) {
		this.usersSession = usersSession;
	}

	public InstitutionRegistrationRequest getRequest() {
		return request;
	}

	public void setRequest(InstitutionRegistrationRequest request) {
		this.request = request;
	}

	public Institution getInstitution() {
		return institution;
	}

	public void setInstitution(Institution institution) {
		this.institution = institution;
	}

	public Contact getContact() {
		return contact;
	}

	public void setContact(Contact contact) {
		this.contact = contact;
	}

	public List<InstitutionRegistrationRequest> getRequests() {
		return requests;
	}

	public void setRequests(List<InstitutionRegistrationRequest> requests) {
		this.requests = requests;
	}

	public List<Institution> getInstitutions() {
		return institutions;
	}

	public void setInstitutions(List<Institution> institutions) {
		this.institutions = institutions;
	}

	public Timestamp getTimestamp() {
		return timestamp;
	}

	public void setTimestamp(Timestamp timestamp) {
		this.timestamp = timestamp;
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

	public ContactImpl getContactImpl() {
		return contactImpl;
	}

	public void setContactImpl(ContactImpl contactImpl) {
		this.contactImpl = contactImpl;
	}

	public static Logger getLogger() {
		return LOGGER;
	}

	public static long getSerialversionuid() {
		return serialVersionUID;
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

	public boolean isSelctDiv() {
		return selctDiv;
	}

	public void setSelctDiv(boolean selctDiv) {
		this.selctDiv = selctDiv;
	}

	public boolean isBoolBottn() {
		return boolBottn;
	}

	public void setBoolBottn(boolean boolBottn) {
		this.boolBottn = boolBottn;
	}

	public boolean isBoolBottEnn() {
		return boolBottEnn;
	}

	public void setBoolBottEnn(boolean boolBottEnn) {
		this.boolBottEnn = boolBottEnn;
	}

	public boolean isInstitutionPanel() {
		return institutionPanel;
	}

	public void setInstitutionPanel(boolean institutionPanel) {
		this.institutionPanel = institutionPanel;
	}

	public boolean isRequestPanel() {
		return requestPanel;
	}

	public void setRequestPanel(boolean requestPanel) {
		this.requestPanel = requestPanel;
	}

	public InstitutionContactImpl getInstContImpl() {
		return instContImpl;
	}

	public void setInstContImpl(InstitutionContactImpl instContImpl) {
		this.instContImpl = instContImpl;
	}

	public boolean isDefaultDiv() {
		return defaultDiv;
	}

	public void setDefaultDiv(boolean defaultDiv) {
		this.defaultDiv = defaultDiv;
	}

	public int getNmbrInst() {
		return nmbrInst;
	}

	public void setNmbrInst(int nmbrInst) {
		this.nmbrInst = nmbrInst;
	}

	public List<InstitutionDto> getDtos() {
		return dtos;
	}

	public void setDtos(List<InstitutionDto> dtos) {
		this.dtos = dtos;
	}

	public boolean isNextpage() {
		return nextpage;
	}

	public void setNextpage(boolean nextpage) {
		this.nextpage = nextpage;
	}

	public boolean isRendered() {
		return rendered;
	}

	public void setRendered(boolean rendered) {
		this.rendered = rendered;
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

	public int getCntryId() {
		return cntryId;
	}

	public void setCntryId(int cntryId) {
		this.cntryId = cntryId;
	}

	public int getPid() {
		return pid;
	}

	public void setPid(int pid) {
		this.pid = pid;
	}

	public int getDid() {
		return did;
	}

	public void setDid(int did) {
		this.did = did;
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

	public CountryImpl getCountryImpl() {
		return countryImpl;
	}

	public void setCountryImpl(CountryImpl countryImpl) {
		this.countryImpl = countryImpl;
	}

	
}
