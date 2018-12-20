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

@SuppressWarnings("unchecked")
@ViewScoped
@ManagedBean
public class InstReqController implements Serializable, DbConstant {
	private static final Logger LOGGER = Logger.getLogger(Thread.currentThread().getStackTrace()[0].getClassName());
	private String CLASSNAME = "InstReqController :: ";
	private static final long serialVersionUID = 1L;
	/* to manage validation messages */
	private boolean isValid, selctDiv;
	/* end manage validation messages */
	private Users usersSession;
	private InstitutionRegistrationRequest request;
	private Institution institution;
	private Contact contact;

	private Date from;
	private Date to;

	private List<InstitutionRegistrationRequest> requests = new ArrayList<InstitutionRegistrationRequest>();
	private List<Institution> institutions = new ArrayList<Institution>();

	Timestamp timestamp = new Timestamp(Calendar.getInstance().getTime().getTime());
	JSFBoundleProvider provider = new JSFBoundleProvider();
	UserImpl usersImpl = new UserImpl();
	InstitutionImpl institutionImpl = new InstitutionImpl();
	InstitutionRegRequestImpl requestImpl = new InstitutionRegRequestImpl();
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
		try {
			requests = requestImpl.getGenericListWithHQLParameter(new String[] { "genericStatus" },
					new Object[] { ACTIVE }, "InstitutionRegistrationRequest", "instRegReqstDate desc");
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
	public void confirmRequest(InstitutionRegistrationRequest request) {
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
		} catch (Exception e) {
			setValid(false);
			e.printStackTrace();
			JSFMessagers.addErrorMessage(getProvider().getValue("com.server.side.internal.error"));
			LOGGER.info(e.getMessage());
			e.printStackTrace();
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
			institution.setCreatedBy(usersSession.getViewId());
			institution.setGenericStatus(ACTIVE);
			institution.setCrtdDtTime(timestamp);
			institution.setInstitutionRegDate(timestamp);
			institution.setInstitutionType(request.getInstRegReqstType());
			institution.setInstLogo("waiting");
			institution.setRequest(request);
			institution.setUpdatedBy(usersSession.getViewId());
			institution.setUpDtTime(timestamp);
			institutionImpl.saveInstitution(institution);
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

	/* saving Institution ends */
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

}
