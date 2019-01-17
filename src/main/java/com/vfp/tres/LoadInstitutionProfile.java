package com.vfp.tres;

import java.io.Serializable;
import java.sql.Timestamp;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.List;
import java.util.logging.Logger;

import javax.annotation.PostConstruct;
import javax.faces.application.FacesMessage;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.SessionScoped;
import javax.faces.context.ExternalContext;
import javax.faces.context.FacesContext;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;

import org.hibernate.HibernateException;
import org.primefaces.event.FileUploadEvent;
import org.primefaces.event.FlowEvent;

import tres.common.DbConstant;
import tres.common.JSFBoundleProvider;
import tres.common.JSFMessagers;
import tres.common.SessionUtils;
import tres.common.UploadUtility;
import tres.dao.impl.CellImpl;
import tres.dao.impl.ContactImpl;
import tres.dao.impl.CountryImpl;
import tres.dao.impl.DistrictImpl;
import tres.dao.impl.DocumentsImpl;
import tres.dao.impl.EvaluationImpl;
import tres.dao.impl.InstitutionContactImpl;
import tres.dao.impl.InstitutionEscaletPolicyImpl;
import tres.dao.impl.InstitutionImpl;
import tres.dao.impl.InstitutionLogoImpl;
import tres.dao.impl.InstitutionRegRequestImpl;
import tres.dao.impl.ProvinceImpl;
import tres.dao.impl.SectorImpl;
import tres.dao.impl.UserImpl;
import tres.dao.impl.VillageImpl;
import tres.domain.Cell;
import tres.domain.Contact;
import tres.domain.Country;
import tres.domain.District;
import tres.domain.Documents;
import tres.domain.Evaluation;
import tres.domain.Institution;
import tres.domain.InstitutionContact;
import tres.domain.InstitutionEscaletePolicy;
import tres.domain.InstitutionLogo;
import tres.domain.InstitutionRegistrationRequest;
import tres.domain.Province;
import tres.domain.Sector;
import tres.domain.Users;
import tres.domain.Village;
import tres.vfp.dto.InstitutionDto;
import tres.vfp.dto.PolicyDto;

@ManagedBean
@SessionScoped
public class LoadInstitutionProfile implements Serializable, DbConstant {
	private static final Logger LOGGER = Logger.getLogger(Thread.currentThread().getStackTrace()[0].getClassName());
	private String CLASSNAME = "LoadUserInformationsController :: ";
	private static final long serialVersionUID = 1L;
	/* to manage validation messages */
	private boolean isValid, chngDiv, skip, nextpage, frstDiv, cmpltDiv, bnchDiv, div1, div4, div2, div3, div3_1,
			hasContact, profileEditable;
	private int cntryId, vid, pid, cid, did, sid;
	/* end manage validation messages */
	private Users usersSession;
	private InstitutionRegistrationRequest request;
	private Institution institution;
	private InstitutionContact contact;
	private InstitutionLogo logoPic;
	private String useremail, tel, pobx, nmbrTime, shrtActivityMark, mdumActivityMark, lngActivityMark, plp, variation;
	private Village village;
	private Province province;
	private District district;
	private Sector sector;
	private Cell cell;
	private String rootpath = Root_Path;
	private Country country;
	private String instName, instaddress;
	private int rid;
	private int nmbr[] = { 1, 2, 3, 4, 5, 6, 7, 8, 9 };

	private Documents documents;
	private InstitutionDto dto = new InstitutionDto();
	DocumentsImpl documentsImpl = new DocumentsImpl();
	private List<InstitutionRegistrationRequest> validInstitution = new ArrayList<InstitutionRegistrationRequest>();
	private List<Institution> institutions = new ArrayList<Institution>();
	private List<InstitutionDto> instDtos = new ArrayList<InstitutionDto>();
	private List<Province> provinces = new ArrayList<Province>();
	private List<District> districts = new ArrayList<District>();
	private List<Sector> sectors = new ArrayList<Sector>();
	private List<Cell> cells = new ArrayList<Cell>();
	private List<Village> villages = new ArrayList<Village>();
	private List<Country> countries = new ArrayList<Country>();
	private List<InstitutionEscaletePolicy> Policies = new ArrayList<InstitutionEscaletePolicy>();
	private List<PolicyDto> policyDtos = new ArrayList<PolicyDto>();
	private Evaluation evaluation;
	private InstitutionEscaletePolicy policy;

	JSFBoundleProvider provider = new JSFBoundleProvider();
	UserImpl usersImpl = new UserImpl();
	InstitutionImpl institutionImpl = new InstitutionImpl();
	InstitutionRegRequestImpl requestImpl = new InstitutionRegRequestImpl();
	ContactImpl contactImpl = new ContactImpl();
	InstitutionLogoImpl logoImpl = new InstitutionLogoImpl();
	InstitutionContactImpl instContactImpl = new InstitutionContactImpl();
	ProvinceImpl provImpl = new ProvinceImpl();
	DistrictImpl districtImpl = new DistrictImpl();
	SectorImpl sectorImpl = new SectorImpl();
	CellImpl cellImpl = new CellImpl();
	VillageImpl villageImpl = new VillageImpl();
	CountryImpl countryImpl = new CountryImpl();
	Timestamp timestamp = new Timestamp(Calendar.getInstance().getTime().getTime());
	InstitutionEscaletPolicyImpl policyImpl = new InstitutionEscaletPolicyImpl();
	EvaluationImpl evalImpl = new EvaluationImpl();

	@SuppressWarnings("unchecked")
	@PostConstruct
	public void init() {
		HttpSession session = SessionUtils.getSession();
		usersSession = (Users) session.getAttribute("userSession");

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
		if (institution == null) {
			institution = new Institution();
		}

		if (contact == null) {
			contact = new InstitutionContact();
		}
		if (logoPic == null) {
			logoPic = new InstitutionLogo();
		}
		if (dto == null) {
			dto = new InstitutionDto();
		}
		if (policy == null) {
			policy = new InstitutionEscaletePolicy();
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
		if (null != usersSession) {
			try {
				request = requestImpl.getModelWithMyHQL(
						new String[] { "institutionRepresenative", "instRegReqstType", "genericStatus" },
						new Object[] { usersSession, "HeadQuoter", ACTIVE }, "from InstitutionRegistrationRequest");
				if (request != null) {
					if (request.getInstRegReqstStatus().equals(ACCEPTED)) {
						institution = institutionImpl.getModelWithMyHQL(new String[] { "request", "institutionType" },
								new Object[] { request, "HeadQuoter" }, "from Institution");
						if (null != institution) {
							chngDiv = true;
							instDtos = display(institution);
							dto = dtoObject(institution);
							policyDtos = displayPolicy(institution);
							try {
								contact = instContactImpl.getModelWithMyHQL(new String[] { "institution" },
										new Object[] { institution }, "from InstitutionContact");
							} catch (Exception e) {
								LOGGER.info("Contact Message::" + e.getMessage());
							}
						}
					} else {
						nextpage = true;
						frstDiv = true;
					}
				} else {
					frstDiv = true;
					countries = countryImpl.getListWithHQL("select f from Country f");
					provinces = provImpl.getListWithHQL("select f from Province f");
				}
			} catch (Exception e) {
				setValid(false);
				e.printStackTrace();
				JSFMessagers.addErrorMessage(getProvider().getValue("com.server.side.internal.error"));
				LOGGER.info(e.getMessage());
				e.printStackTrace();
			}
		}

	}

	/* Method for navigation to page */

	/* method for render province panel starts */
	public void renderProvMethod() {
		try {
			country = countryImpl.getCountryById(cntryId, "taskId");
			if (country != null) {
				if (country.getCountryName_en().equals("RWANDA")) {
					skip = true;
				} else {
					skip = false;
				}
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	/* method for render province panel ends */

	/* branch div method starts */
	public void SaveBranchContact() {
		try {
			Institution institutn = new Institution();
			institutn = institutionImpl.getInstitutionById(rid, "institutionId");
			saveContact(institutn);
		} catch (Exception e) {
			setValid(false);
			e.printStackTrace();
			JSFMessagers.addErrorMessage(getProvider().getValue("com.server.side.internal.error"));
			LOGGER.info(e.getMessage());
			e.printStackTrace();
		}
	}
	/* branch div method ends */

	/* provinces and cells methods starts */

	@SuppressWarnings("unchecked")
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

	@SuppressWarnings("unchecked")
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

	@SuppressWarnings("unchecked")
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

	@SuppressWarnings("unchecked")
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

	/* provinces and cells methods ends */
	/* uploading institution logo starts */
	public void UploadLogo(FileUploadEvent event) {
		UploadUtility ut = new UploadUtility();
		String validationCode = "INSTITUTIONLOGO";
		InstitutionLogo logoPic = new InstitutionLogo();
		try {

			institution = institutionImpl.getModelWithMyHQL(new String[] { "request" },
					new Object[] { request = requestImpl.getModelWithMyHQL(
							new String[] { "institutionRepresenative", "instRegReqstType", "genericStatus" },
							new Object[] { usersSession, "HeadQuoter", ACTIVE },
							"from InstitutionRegistrationRequest") },
					"from Institution");
			documents = ut.fileUploadUtil(event, validationCode);
			institution.setInstLogo(documents.getSysFilename());
			institutionImpl.UpdateInstitution(institution);
			logoPic.setDocuments(documents);
			logoPic.setInstitution(institution);
			logoPic.setInstitutionRegDate(timestamp);
			logoPic.setGenericStatus(ACTIVE);
			logoImpl.saveInstitutionLogo(logoPic);
			profileEditable = false;
			frstDiv = false;
			LOGGER.info(CLASSNAME + event.getFile().getFileName() + "uploaded successfully ... ");
			JSFMessagers.resetMessages();
			setValid(true);
			JSFMessagers.addErrorMessage(getProvider().getValue("uploaded successfully"));
		} catch (Exception e) {
			LOGGER.info(CLASSNAME + "testing save methode ");
			JSFMessagers.resetMessages();
			setValid(false);
			JSFMessagers.addErrorMessage(getProvider().getValue("com.server.side.internal.error"));
			e.printStackTrace();
		}

	}
	/* uploading institution logo ends */

	/* method for saving contact starts */
	public String saveContact() throws Exception {
		try {
			institution = institutionImpl.getModelWithMyHQL(new String[] { "request" },
					new Object[] { requestImpl.getModelWithMyHQL(
							new String[] { "institutionRepresenative", "instRegReqstType", "genericStatus" },
							new Object[] { usersSession, "HeadQuoter", ACTIVE },
							"from InstitutionRegistrationRequest") },
					"from Institution");
			saveContact(institution);
			return "";
		} catch (HibernateException e) {
			return "";
		}

	}

	/* saving contact ends */
	/* Contact method starts */
	public void saveContact(Institution institution) {
		try {
			contact = new InstitutionContact();
			contact.setCreatedBy(usersSession.getViewId());
			contact.setPhone(tel);
			contact.setPobox(pobx);
			contact.setCrtdDtTime(timestamp);
			contact.setGenericStatus(ACTIVE);
			contact.setUpDtTime(timestamp);
			contact.setInstitution(institution);
			contact.setEmail(useremail);
			contact.setUpdatedBy(usersSession.getViewId());
			instContactImpl.saveContact(contact);
			saveInstitutionContact();
			JSFMessagers.resetMessages();
			setValid(true);
			JSFMessagers.addErrorMessage(getProvider().getValue("com.save.form.contact"));
			JSFMessagers.addErrorMessage(getProvider().getValue("com.server.side.email.notification"));
			LOGGER.info(CLASSNAME + ":::Contact Details is saved");
			backToprofile();
		} catch (HibernateException e) {
			div2 = true;
			LOGGER.info(CLASSNAME + ":::Contact Details is fail with HibernateException  error");
			JSFMessagers.resetMessages();
			setValid(false);
			JSFMessagers.addErrorMessage(getProvider().getValue("com.server.side.internal.error" + e.getMessage()));
			LOGGER.info(CLASSNAME + "" + e.getMessage());
			e.printStackTrace();
		}
	}

	/* Contact method ends */
	/* saving branch starts */
	public String saveBranchRequest() {
		try {
			InstitutionRegistrationRequest req = new InstitutionRegistrationRequest();
			req = requestImpl.getModelWithMyHQL(
					new String[] { "institutionRepresenative", "instRegReqstType", "genericStatus" },
					new Object[] { usersSession, "HeadQuoter", ACTIVE }, "from InstitutionRegistrationRequest");
			institution = institutionImpl.getModelWithMyHQL(new String[] { "genericStatus", "request" },
					new Object[] { ACTIVE, req }, "from Institution");
			request = new InstitutionRegistrationRequest();
			request.setInstitutionName(instName);
			request.setInstitutionAddress(instaddress);
			request.setCreatedBy(usersSession.getViewId());
			request.setCrtdDtTime(timestamp);
			request.setGenericStatus(ACTIVE);
			request.setInstRegReqstDate(timestamp);
			request.setUpdatedBy(usersSession.getViewId());
			request.setUpDtTime(timestamp);
			request.setInstitution(institution);
			request.setInstRegReqstStatus("pending");
			request.setCountry(countryImpl.getCountryById(cntryId, "taskId"));
			request.setVillage(villageImpl.getVillageById(vid, "villageId"));
			request.setInstitutionRepresenative(usersSession);
			request.setInstRegReqstType("branch");
			requestImpl.saveInstitutionRegRequest(request);
			JSFMessagers.resetMessages();
			setValid(true);
			JSFMessagers.addErrorMessage(getProvider().getValue("institutionController.saving.message"));
			LOGGER.info(CLASSNAME + ":::Institution request   sent");
			return "";
		} catch (Exception e) {
			frstDiv = false;
			LOGGER.info(CLASSNAME + ":::Institution request not sent with HibernateException  error");
			JSFMessagers.resetMessages();
			setValid(false);
			JSFMessagers.addErrorMessage(getProvider().getValue("institutionController.savingFail.message"));
			LOGGER.info(CLASSNAME + "" + e.getMessage());
			e.printStackTrace();
			return "";
		}
	}

	/* saving branch ends */
	/* saving institution request starts */
	public String saveInstitutionRequest() {
		try {
			request = new InstitutionRegistrationRequest();
			request.setCreatedBy(usersSession.getViewId());
			request.setCrtdDtTime(timestamp);
			request.setInstitutionName(instName);
			request.setInstitutionAddress(instaddress);
			request.setGenericStatus(ACTIVE);
			request.setInstRegReqstDate(timestamp);
			request.setUpdatedBy(usersSession.getViewId());
			request.setUpDtTime(timestamp);
			request.setInstRegReqstStatus("pending");
			request.setCountry(countryImpl.getCountryById(cntryId, "taskId"));
			request.setVillage(villageImpl.getVillageById(vid, "villageId"));
			request.setInstitutionRepresenative(usersSession);
			request.setInstRegReqstType("HeadQuoter");
			requestImpl.saveInstitutionRegRequest(request);
			JSFMessagers.resetMessages();
			setValid(true);
			JSFMessagers.addErrorMessage(getProvider().getValue("institutionController.saving.message"));
			LOGGER.info(CLASSNAME + ":::Institution request sent");
			nextpage = true;
			return "";
		} catch (Exception e) {
			LOGGER.info(CLASSNAME + ":::Institution request not sent with HibernateException  error");
			JSFMessagers.resetMessages();
			setValid(false);
			JSFMessagers.addErrorMessage(getProvider().getValue("institutionController.savingFail.message"));
			LOGGER.info(CLASSNAME + "" + e.getMessage());
			e.printStackTrace();
			return "";
		}
	}
	/* saving institution request ends */

	/* dto methods starts */
	@SuppressWarnings("unchecked")
	public List<InstitutionDto> display(Institution institution) {

		try

		{
			institutions = institutionImpl.getGenericListWithHQLParameter(
					new String[] { "genericStatus", "institutionType", "branch" },
					new Object[] { ACTIVE, "branch", institution }, "Institution", "institutionRegDate desc");
			List<InstitutionDto> dtos = new ArrayList<InstitutionDto>();
			for (Institution inst : institutions) {
				InstitutionDto institutionDto = new InstitutionDto();
				institutionDto.setEditable(false);
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
	/* dto method ends */

	/* dto methods starts for Policy */
	@SuppressWarnings("unchecked")
	public List<PolicyDto> displayPolicy(Institution institution) {

		try

		{
			Policies = policyImpl.getGenericListWithHQLParameter(new String[] { "genericStatus", "institution" },
					new Object[] { ACTIVE, institution }, "InstitutionEscaletePolicy", "crtdDtTime desc");
			List<PolicyDto> dtos = new ArrayList<PolicyDto>();
			for (InstitutionEscaletePolicy inst : Policies) {

				PolicyDto poldto = new PolicyDto();
				poldto.setInstitution(inst.getInstitution());
				poldto.setLongMarks(inst.getLongMarks());
				poldto.setMediumgMarks(inst.getMediumgMarks());
				poldto.setShortMarks(inst.getShortMarks());
				poldto.setReschduleTime(inst.getReschduleTime());
				poldto.setStatus(inst.getStatus());
				poldto.setPolicyId(inst.getPolicyId());
				poldto.setEditable(false);
				dtos.add(poldto);
			}
			return dtos;
		} catch (Exception e) {
			setValid(false);
			JSFMessagers.addErrorMessage(getProvider().getValue("com.server.side.internal.error"));
			LOGGER.info(e.getMessage());
			return null;
		}
	}

	/* dto method ends */

	/* method for displaying adding policy form starts */
	public void displyPolicy() {
		div1 = true;
	}

	/* method for displaying adding policy form starts reverse */
	public void displyPolicyReverse() {
		div1 = false;
	}

	/* method for displaying adding policy form ends */
	/* dto object starts */
	public InstitutionDto dtoObject(Institution institution) {
		try {
			InstitutionDto dto = new InstitutionDto();
			dto.setBranch(institution.getBranch());
			dto.setInstitutionId(institution.getInstitutionId());
			dto.setInstitutionRegDate(institution.getInstitutionRegDate());
			dto.setRequest(institution.getRequest());
			dto.setInstitutionType(institution.getInstitutionType());
			LOGGER.info(dto.getRequest().getInstitutionName() + "TEST :::");
			return dto;
		} catch (Exception e) {
			return null;
		}
	}

	/* dto object ends */
	/* policy save starts */

	@SuppressWarnings("unused")
	public void createEscaletePolicy() {
		try {
			policy = policyImpl.getModelWithMyHQL(new String[] { "institution", "genericStatus" },
					new Object[] { institution, ACTIVE }, "from InstitutionEscaletePolicy");

			if (policy != null) {
				policy.setGenericStatus(DESACTIVE);
				policy.setUpdatedBy(usersSession.getViewId());
				policy.setUpDtTime(timestamp);
				policyImpl.UpdateInstEscalPolicy(policy);
				savePolicy();
			} else {
				savePolicy();
			}

		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	public void savePolicy() {
		try {
			policy.setCreatedBy(usersSession.getViewId());
			policy.setCrtdDtTime(timestamp);
			policy.setGenericStatus(ACTIVE);
			policy.setUpDtTime(timestamp);
			policy.setReschduleTime(Integer.parseInt(nmbrTime));
			policy.setInstitution(institution);
			policy.setShortMarks(Double.parseDouble(shrtActivityMark));
			policy.setMediumgMarks(Double.parseDouble(mdumActivityMark));
			policy.setLongMarks(Double.parseDouble(lngActivityMark));
			policy.setPlanPeriod(Integer.parseInt(plp));
			policy.setVariation(Integer.parseInt(variation));
			policyImpl.saveInstEscalPolicy(policy);
			JSFMessagers.resetMessages();
			setValid(true);
			JSFMessagers.addErrorMessage(getProvider().getValue("institutionController.saving.Policy"));
			LOGGER.info(CLASSNAME + ":::Contact creation failed");
			div3_1 = false;
		} catch (Exception e) {
			div3 = true;
			setValid(false);
			LOGGER.info(CLASSNAME + ":::Policy Details is fail with HibernateException  error");
			JSFMessagers.resetMessages();
			JSFMessagers.addErrorMessage(getProvider().getValue("com.server.side.internal.error" + e.getMessage()));
			LOGGER.info(CLASSNAME + "" + e.getMessage());
			e.printStackTrace();
		}
	}

	/* policy ends */
	public boolean isValid() {
		return isValid;
	}

	public String cancel(InstitutionDto institutionDto) {
		institutionDto.setEditable(false);
		backToprofile();
		return "/menu/ViewInstitutionProfile.xhtml";

	}

	public String saveAction(InstitutionDto dto) {
		LOGGER.info("update  saveAction method");
		// get all existing value but set "editable" to false
		Institution insti = new Institution();
		insti = new Institution();
		insti = institutionImpl.getInstitutionById(dto.getInstitutionId(), "institutionId");
		LOGGER.info("here update sart for " + dto + " Institution id " + dto.getInstitutionId());
		dto.setEditable(false);
		insti.setUpdatedBy(usersSession.getViewId());
		insti.setUpDtTime(getTimestamp());
		insti.setBranch(dto.getBranch());
		insti.setInstitutionType(dto.getInstitutionType());
		insti.setRequest(dto.getRequest());
		insti.setInstLogo(dto.getInstLogo());
		institutionImpl.UpdateInstitution(insti);
		return null;
	}

	public String editAction(InstitutionDto institutionDto) {
		institutionDto.setEditable(true);
		return null;
	}

	public String cancelPolicy(PolicyDto dto) {
		dto.setEditable(false);
		return "/menu/ViewInstitutionProfile.xhtml";

	}

	public String editActionPolicy(PolicyDto institutionDto) {
		institutionDto.setEditable(true);
		return null;
	}

	public String saveActionPolicy(PolicyDto dto) {
		LOGGER.info("update  saveAction method");
		// get all existing value but set "editable" to false
		InstitutionEscaletePolicy escaletePolicy = new InstitutionEscaletePolicy();
		escaletePolicy = policyImpl.getInstEscalPolicyById(dto.getPolicyId(), "policyId");
		dto.setEditable(false);
		escaletePolicy.setCreatedBy(usersSession.getViewId());
		escaletePolicy.setUpDtTime(getTimestamp());
		escaletePolicy.setInstitution(dto.getInstitution());
		escaletePolicy.setLongMarks(dto.getLongMarks());
		escaletePolicy.setMediumgMarks(dto.getMediumgMarks());
		escaletePolicy.setShortMarks(dto.getShortMarks());
		escaletePolicy.setStatus(dto.getStatus());
		policyImpl.UpdateInstEscalPolicy(escaletePolicy);
		return null;
	}

	/* Editing Profile */

	public String editAction() {
		dto.setEditable(true);
		return null;
	}

	/* Cancer Editing Profile */
	public String cancelPolicy() {
		dto.setEditable(false);
		return "/menu/ViewInstitutionProfile.xhtml";

	}

	/* Saving changes */
	public String saveAction() {
		LOGGER.info("update  saveAction method");
		// get all existing value but set "editable" to false
		Institution insti = new Institution();
		insti = new Institution();
		insti = institutionImpl.getInstitutionById(dto.getInstitutionId(), "institutionId");
		LOGGER.info("here update sart for " + dto + " Institution id " + dto.getInstitutionId());
		dto.setEditable(false);
		insti.setUpdatedBy(usersSession.getViewId());
		insti.setUpDtTime(getTimestamp());
		insti.setBranch(dto.getBranch());
		insti.setInstitutionType(dto.getInstitutionType());
		insti.setRequest(dto.getRequest());
		insti.setInstLogo(dto.getInstLogo());
		institutionImpl.UpdateInstitution(insti);
		return null;
	}

	// adding logo view

	public void addLogview() {
		frstDiv = true;
		profileEditable = true;
	}

	public void save() {
		FacesMessage msg = new FacesMessage("Successful", "Welcome :" + usersSession.getFname());
		FacesContext.getCurrentInstance().addMessage(null, msg);
	}

	/* method for displaying adding branch div */

	public void addbranchDiv() {
		chngDiv = true;
		frstDiv = true;
		div1 = true;
	}

	public void institutionDiv() {
		chngDiv = true;
		frstDiv = true;
		div1 = true;
		cmpltDiv = true;
	}

	/* method for displaying default div */
	public void defaultDiv() {
		frstDiv = false;
	}

	/* Saving institution details */
	public String backToprofile() {
		try {
			div1 = false;
			frstDiv = false;
			bnchDiv = false;
			div2 = false;
			LOGGER.info("Here Weare:::::" + frstDiv);
			return "";
		} catch (Exception e) {
			return "";
		}
	}

	/* show complete starts */
	public String showComplete() {
		try {
			frstDiv = true;
			div3 = true;
			return "";
		} catch (Exception e) {
			return "";
		}
	}
	/* saving contact */

	public String saveInstitutionContact() {
		try {
			frstDiv = true;
			div2 = true;
			return "";
		} catch (Exception e) {
			return "";
		}
	}

	public String saveInstitutionBranchContact() {
		try {
			frstDiv = true;
			div4 = true;
			return "";
		} catch (Exception e) {
			return "";
		}
	}

	public String bacKnstitutionBranchContact() {
		try {
			div4 = false;
			frstDiv = false;
			return "";
		} catch (Exception e) {
			return "";
		}
	}

	/* saving policy */
	public String saveInstitutionPolicy() {
		try {
			frstDiv = true;
			div3 = true;
			return "";
		} catch (Exception e) {
			return "";
		}
	}

	public List<InstitutionDto> getInstDtos() {
		return instDtos;
	}

	public void setInstDtos(List<InstitutionDto> instDtos) {
		this.instDtos = instDtos;
	}

	public InstitutionDto getDto() {
		return dto;
	}

	public void setDto(InstitutionDto dto) {
		this.dto = dto;
	}

	public String getContextPath() {

		HttpServletRequest request = (HttpServletRequest) FacesContext.getCurrentInstance().getExternalContext()
				.getRequest();
		return request.getContextPath();
	}

	public String getRootpath() {
		return rootpath;
	}

	public void setRootpath(String rootpath) {
		this.rootpath = rootpath;
	}

	public boolean isNextpage() {
		return nextpage;
	}

	public void setNextpage(boolean nextpage) {
		this.nextpage = nextpage;
	}

	public boolean isFrstDiv() {
		return frstDiv;
	}

	public void setFrstDiv(boolean frstDiv) {
		this.frstDiv = frstDiv;
	}

	public boolean isCmpltDiv() {
		return cmpltDiv;
	}

	public void setCmpltDiv(boolean cmpltDiv) {
		this.cmpltDiv = cmpltDiv;
	}

	public boolean isBnchDiv() {
		return bnchDiv;
	}

	public void setBnchDiv(boolean bnchDiv) {
		this.bnchDiv = bnchDiv;
	}

	public boolean isDiv1() {
		return div1;
	}

	public void setDiv1(boolean div1) {
		this.div1 = div1;
	}

	public boolean isDiv2() {
		return div2;
	}

	public void setDiv2(boolean div2) {
		this.div2 = div2;
	}

	public boolean isDiv3() {
		return div3;
	}

	public void setDiv3(boolean div3) {
		this.div3 = div3;
	}

	public int getPid() {
		return pid;
	}

	public void setPid(int pid) {
		this.pid = pid;
	}

	public int getCid() {
		return cid;
	}

	public void setCid(int cid) {
		this.cid = cid;
	}

	public int getDid() {
		return did;
	}

	public void setDid(int did) {
		this.did = did;
	}

	public int getSid() {
		return sid;
	}

	public void setSid(int sid) {
		this.sid = sid;
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

	public List<Country> getCountries() {
		return countries;
	}

	public void setCountries(List<Country> countries) {
		this.countries = countries;
	}

	public Evaluation getEvaluation() {
		return evaluation;
	}

	public void setEvaluation(Evaluation evaluation) {
		this.evaluation = evaluation;
	}

	public InstitutionEscaletePolicy getPolicy() {
		return policy;
	}

	public void setPolicy(InstitutionEscaletePolicy policy) {
		this.policy = policy;
	}

	public InstitutionEscaletPolicyImpl getPolicyImpl() {
		return policyImpl;
	}

	public void setPolicyImpl(InstitutionEscaletPolicyImpl policyImpl) {
		this.policyImpl = policyImpl;
	}

	public EvaluationImpl getEvalImpl() {
		return evalImpl;
	}

	public void setEvalImpl(EvaluationImpl evalImpl) {
		this.evalImpl = evalImpl;
	}

	public String getNmbrTime() {
		return nmbrTime;
	}

	public void setNmbrTime(String nmbrTime) {
		this.nmbrTime = nmbrTime;
	}

	public String getShrtActivityMark() {
		return shrtActivityMark;
	}

	public void setShrtActivityMark(String shrtActivityMark) {
		this.shrtActivityMark = shrtActivityMark;
	}

	public String getMdumActivityMark() {
		return mdumActivityMark;
	}

	public void setMdumActivityMark(String mdumActivityMark) {
		this.mdumActivityMark = mdumActivityMark;
	}

	public String getLngActivityMark() {
		return lngActivityMark;
	}

	public void setLngActivityMark(String lngActivityMark) {
		this.lngActivityMark = lngActivityMark;
	}

	public List<InstitutionEscaletePolicy> getPolicies() {
		return Policies;
	}

	public void setPolicies(List<InstitutionEscaletePolicy> policies) {
		Policies = policies;
	}

	public boolean isDiv3_1() {
		return div3_1;
	}

	public void setDiv3_1(boolean div3_1) {
		this.div3_1 = div3_1;
	}

	public List<PolicyDto> getPolicyDtos() {
		return policyDtos;
	}

	public void setPolicyDtos(List<PolicyDto> policyDtos) {
		this.policyDtos = policyDtos;
	}

	public boolean isProfileEditable() {
		return profileEditable;
	}

	public void setProfileEditable(boolean profileEditable) {
		this.profileEditable = profileEditable;
	}

	public String getInstName() {
		return instName;
	}

	public void setInstName(String instName) {
		this.instName = instName;
	}

	public String getInstaddress() {
		return instaddress;
	}

	public void setInstaddress(String instaddress) {
		this.instaddress = instaddress;
	}

	public String getTel() {
		return tel;
	}

	public void setTel(String tel) {
		this.tel = tel;
	}

	public String getPobx() {
		return pobx;
	}

	public void setPobx(String pobx) {
		this.pobx = pobx;
	}

	public boolean isHasContact() {
		return hasContact;
	}

	public void setHasContact(boolean hasContact) {
		this.hasContact = hasContact;
	}

	public boolean isDiv4() {
		return div4;
	}

	public void setDiv4(boolean div4) {
		this.div4 = div4;
	}

	public int getRid() {
		return rid;
	}

	public void setRid(int rid) {
		this.rid = rid;
	}

	public String getPlp() {
		return plp;
	}

	public void setPlp(String plp) {
		this.plp = plp;
	}

	public String getVariation() {
		return variation;
	}

	public void setVariation(String variation) {
		this.variation = variation;
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

	public InstitutionContact getContact() {
		return contact;
	}

	public void setContact(InstitutionContact contact) {
		this.contact = contact;
	}

	public InstitutionContactImpl getInstContactImpl() {
		return instContactImpl;
	}

	public void setInstContactImpl(InstitutionContactImpl instContactImpl) {
		this.instContactImpl = instContactImpl;
	}

	public List<InstitutionRegistrationRequest> getValidInstitution() {
		return validInstitution;
	}

	public void setValidInstitution(List<InstitutionRegistrationRequest> validInstitution) {
		this.validInstitution = validInstitution;
	}

	public List<Institution> getInstitutions() {
		return institutions;
	}

	public void setInstitutions(List<Institution> institutions) {
		this.institutions = institutions;
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

	public boolean isChngDiv() {
		return chngDiv;
	}

	public void setChngDiv(boolean chngDiv) {
		this.chngDiv = chngDiv;
	}

	public InstitutionLogo getLogoPic() {
		return logoPic;
	}

	public void setLogoPic(InstitutionLogo logoPic) {
		this.logoPic = logoPic;
	}

	public Documents getDocuments() {
		return documents;
	}

	public void setDocuments(Documents documents) {
		this.documents = documents;
	}

	public DocumentsImpl getDocumentsImpl() {
		return documentsImpl;
	}

	public void setDocumentsImpl(DocumentsImpl documentsImpl) {
		this.documentsImpl = documentsImpl;
	}

	public InstitutionLogoImpl getLogoImpl() {
		return logoImpl;
	}

	public void setLogoImpl(InstitutionLogoImpl logoImpl) {
		this.logoImpl = logoImpl;
	}

	public Timestamp getTimestamp() {
		return timestamp;
	}

	public void setTimestamp(Timestamp timestamp) {
		this.timestamp = timestamp;
	}

	public String getUseremail() {
		return useremail;
	}

	public void setUseremail(String useremail) {
		this.useremail = useremail;
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

	public Country getCountry() {
		return country;
	}

	public void setCountry(Country country) {
		this.country = country;
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

	public int getCntryId() {
		return cntryId;
	}

	public void setCntryId(int cntryId) {
		this.cntryId = cntryId;
	}

	public int getVid() {
		return vid;
	}

	public void setVid(int vid) {
		this.vid = vid;
	}

	public boolean isSkip() {
		return skip;
	}

	public void setSkip(boolean skip) {
		this.skip = skip;
	}

	public int[] getNmbr() {
		return nmbr;
	}

	public void setNmbr(int[] nmbr) {
		this.nmbr = nmbr;
	}

}
