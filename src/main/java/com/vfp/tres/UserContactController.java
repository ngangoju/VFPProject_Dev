package com.vfp.tres;

import java.io.Serializable;
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
import tres.dao.impl.ContactImpl;
import tres.dao.impl.UserImpl;
import tres.domain.Contact;
import tres.domain.UserCategory;
import tres.domain.Users;
import tres.vfp.dto.ContactDto;
import tres.vfp.dto.UserDto;

@ManagedBean
@ViewScoped
public class UserContactController implements Serializable, DbConstant {
	private static final Logger LOGGER = Logger.getLogger(Thread.currentThread().getStackTrace()[0].getClassName());
	private String CLASSNAME = "UserContactController :: ";
	private static final long serialVersionUID = 1L;
	/* to manage validation messages */
	private boolean isValid;
	/* end manage validation messages */
	private Contact contact;
	private Users users;
	private UserDto userDto;
	private ContactDto contactDto;
	private Users usersSession;
	private int userIdNumber;
	private List<Users> usersDetails = new ArrayList<Users>();
	private List<UserDto> userDtoDetails = new ArrayList<UserDto>();
	private List<UserDto> userDtofiltered = new ArrayList<UserDto>();
	private List<ContactDto> contactDtoDetails = new ArrayList<ContactDto>();
	private List<Contact> contactDetails = new ArrayList<Contact>();
	private List<Users> countUsersList = new ArrayList<Users>();
	private List<Users> holdCountedList = new ArrayList<Users>();
	private String choice;
	private boolean rendered;
	private boolean renderForeignCountry = true;
	private boolean renderContactForm;
	private boolean renderTable;
	private boolean renderLoginTable;
	private String useremail;
	private int listSize;
	private int contactSize;
	private int userId;
	private int countUserSize;
	private int userLoginSize;
	private Date from, to;
	private int days;
	/*
	 * private String viewId; private String fname; private String lname;
	 */
	/* class injection */
	JSFBoundleProvider provider = new JSFBoundleProvider();
	UserImpl usersImpl = new UserImpl();
	ContactImpl contactImpl = new ContactImpl();
	Timestamp timestamp = new Timestamp(Calendar.getInstance().getTime().getTime());

	@SuppressWarnings("unchecked")
	@PostConstruct
	public void init() {
		addOthercontacts();
		HttpSession session = SessionUtils.getSession();
		usersSession = (Users) session.getAttribute("userSession");

		if (contact == null) {
			contact = new Contact();
		}

		if (users == null) {
			users = new Users();
		}
		if (userDto == null) {
			userDto = new UserDto();
		}
		if (contactDto == null) {
			contactDto = new ContactDto();
		}
		try {
			/*
			 * usersDetails = usersImpl.getGenericListWithHQLParameter(new String[] {
			 * "genericStatus", "status" }, new Object[] { ACTIVE, ACTIVE }, "Users",
			 * "userId desc"); for (Users user : usersDetails) { UserDto userDto = new
			 * UserDto(); userDto.setEditable(false); userDto.setFname(user.getFname());
			 * userDto.setLname(user.getLname()); userDto.setViewId(user.getViewId());
			 * userDto.setAddress(user.getAddress()); userDto.setUserId(user.getUserId());
			 * userDto.setUserCategory(user.getUserCategory()); userDtoDetails.add(userDto);
			 * }
			 */

			contactDetails = contactImpl.getGenericListWithHQLParameter(new String[] { "genericStatus", "createdBy" },
					new Object[] { ACTIVE, usersSession.getViewId() }, "Contact", "contactId asc");

			/*
			 * for (Contact contact : contactDetails) { ContactDto contDto = new
			 * ContactDto(); contDto.setEditable(false);
			 * contDto.setContactDetails(contact.getContactDetails());
			 * contDto.setEmail(contact.getEmail()); contDto.setPhone(contact.getPhone());
			 * contDto.setContactId(contact.getContactId());
			 * contDto.setUser(contact.getUser()); contactDtoDetails.add(contDto); }
			 */

			contactDtoDetails = displayContactByDateBetween(contactDetails);

			contactSize = contactDtoDetails.size();
			for (Object[] data : usersImpl.reportList(
					"select us.fname,us.lname, us.viewId,us.address, us.userId from Contact co right  join  co.user us where co.user is null")) {

				LOGGER.info("users>>" + data[0] + ":: " + data[1] + "");
				Users users = new Users();
				users.setFname(data[0] + "");
				users.setLname(data[1] + "");
				users.setViewId(data[2] + "");
				users.setAddress(data[3] + "");
				users.setUserId(Integer.parseInt(data[4] + ""));
				usersDetails.add(users);
			}
			listSize = usersDetails.size();
			// count total number of registered users

			countUsersList = usersImpl.getGenericListWithHQLParameter(new String[] { "genericStatus" },
					new Object[] { ACTIVE }, "Users", "userId desc");

			countUserSize = showAllUsersSize(countUsersList);
			// End of the registers users

			// count total number of user and showing users able to login
			countUsersList = usersImpl.getGenericListWithHQLParameter(new String[] { "genericStatus", "status" },
					new Object[] { ACTIVE, ACTIVE }, "Users", "userId desc");

			userLoginSize = showAllLoginUsersSize(countUsersList);

			// End of login users size

		} catch (Exception e) {
			setValid(false);
			JSFMessagers.addErrorMessage(getProvider().getValue("com.server.side.internal.error"));
			LOGGER.info(e.getMessage());
			e.printStackTrace();
		}

	}

	@SuppressWarnings({ "static-access", "unchecked" })
	public void showContactFiltered() {
		try {
			if (to.after(from)) {
				Formating fmt = new Formating();
				LOGGER.info("Start Date found:-----------:" + fmt.getMysqlFormatV2(from) + "End Date:::::::::"
						+ fmt.getMysqlFormatV2(to));
				days = fmt.daysBetween(from, to);
				if (days <= 30) {
					renderTable = true;
					contactDtoDetails = filterContactByDate(from, to);

				} else {
					setValid(false);
					JSFMessagers.addErrorMessage(getProvider().getValue("com.server.side.internal.invalidDaysRange"));
				}
			} else {

				setValid(false);
				JSFMessagers.addErrorMessage(getProvider().getValue("com.server.side.internal.invalidRange"));
			}
		} catch (Exception e) {
			JSFMessagers.resetMessages();
			setValid(false);
			JSFMessagers.addErrorMessage(getProvider().getValue("com.server.side.internal.errorDate"));
			LOGGER.info(CLASSNAME + "" + e.getMessage());
			e.printStackTrace();
		}
	}

	@SuppressWarnings({ "static-access" })
	public List<ContactDto> filterContactByDate(Date from, Date to) {

		Formating fmt = new Formating();
		contactDtoDetails = new ArrayList<ContactDto>();

		try {
			for (Object[] data : contactImpl.reportList(
					"select co.contactId,co.contactDetails,co.email,co.phone,co.user from Users us,Contact co where co.user=us.userId and co.crtdDtTime between '"
							+ fmt.getMysqlFormatV2(from) + "' and  '" + Formating.getMysqlFormatV2(to) + "'")) {

				ContactDto contDto = new ContactDto();
				contDto.setEditable(false);
				contDto.setContactDetails(data[1] + "");
				contDto.setEmail(data[2] + "");
				contDto.setPhone(data[3] + "");
				contDto.setContactId(Integer.parseInt(data[0] + ""));
				contDto.setUser((Users) data[4]);
				contactDtoDetails.add(contDto);
			}
		} catch (ParseException e) {
			JSFMessagers.resetMessages();
			setValid(false);
			JSFMessagers.addErrorMessage(getProvider().getValue("com.server.side.internal.errorfilter"));
			LOGGER.info(CLASSNAME + "" + e.getMessage());
			e.printStackTrace();
		}

		return contactDtoDetails;

	}

	public int showAllUsersSize(List<Users> Usersize) {
		List<Users> addusers = new ArrayList<Users>();
		for (Users user : Usersize) {
			addusers.add(user);
		}
		return (addusers.size());
	}

	public int showAllLoginUsersSize(List<Users> Usersize) {
		List<Users> addusers = new ArrayList<Users>();
		for (Users user : Usersize) {
			addusers.add(user);
		}
		return (addusers.size());
	}

	public void addcontacts() {
		HttpSession session = SessionUtils.getSession();
		// Get the values from the session
		users = (Users) session.getAttribute("userinfo");
	}

	public void showAllusers() {
		if (listSize != 0)
			rendered = false;
		renderContactForm = false;
		renderTable = true;
		renderLoginTable = false;
	}

	public void showContacts() {
		if (listSize != 0)
			rendered = true;
		renderTable = false;
		renderLoginTable = false;
	}

	public void showLoginUsers() {
		if (userLoginSize != 0)
			renderTable = false;
		renderLoginTable = true;
		rendered = false;
	}

	public List displayContactByDateBetween(List<Contact> contactDto) {

		List<ContactDto> contactDtoDetails = new ArrayList<ContactDto>();
		for (Contact contact : contactDetails) {
			ContactDto contDto = new ContactDto();
			contDto.setEditable(false);
			contDto.setContactDetails(contact.getContactDetails());
			contDto.setEmail(contact.getEmail());
			contDto.setPhone(contact.getPhone());
			contDto.setContactId(contact.getContactId());
			contDto.setUser(contact.getUser());
			contactDtoDetails.add(contDto);
		}
		return (contactDtoDetails);
	}
	public String saveUserNewContact() {

		try {

			try {

				Contact ct = new Contact();
				if (null != useremail)
					ct = contactImpl.getModelWithMyHQL(new String[] { "email" }, new Object[] { useremail },
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

			FormSampleController sample = new FormSampleController();
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

			contact.setEmail(useremail);
			contact.setUpdatedBy(usersSession.getViewId());
			// :::saving contact action:::::::::::://

			contactImpl.saveContact(contact);
			return "/menu/ViewUsersContacts.xhtml?faces-redirect=true";
			/*
			 * sample.sendUserMailTest(useremail, users.getFname(), users.getLname());
			 * isValid = sample.isValid(); if (isValid) { contact.setEmail(useremail);
			 * contact.setUpdatedBy(usersSession.getViewId()); // :::saving contact
			 * action:::::::::::://
			 * 
			 * contactImpl.saveContact(contact);
			 * 
			 * // :::::End of saving:::::::::::::// JSFMessagers.resetMessages();
			 * setValid(true); //
			 * JSFMessagers.addErrorMessage(getProvider().getValue("com.save.form.contact"))
			 * ; JSFMessagers.addErrorMessage(getProvider().getValue(
			 * "com.server.side.email.notification")); LOGGER.info(CLASSNAME +
			 * ":::Contact Details is saved"); clearContactFuileds(); return
			 * "/menu/ViewUsersContacts.xhtml?faces-redirect=true"; } else {
			 * JSFMessagers.resetMessages(); setValid(false);
			 * JSFMessagers.addErrorMessage(getProvider().getValue(
			 * "com.server.side.email.notifail")); return null; }
			 */

		} catch (HibernateException e) {
			LOGGER.info(CLASSNAME + ":::Contact Details is fail with HibernateException  error");
			JSFMessagers.resetMessages();
			setValid(false);
			JSFMessagers.addErrorMessage(getProvider().getValue("com.server.side.internal.error"));
			LOGGER.info(CLASSNAME + "" + e.getMessage());
			e.printStackTrace();
			return "";
		}

	}

	public String saveContact() throws Exception {
		try {

			try {

				Contact ct = new Contact();
				if (null != useremail)
					ct = contactImpl.getModelWithMyHQL(new String[] { "email" }, new Object[] { useremail },
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
			FormSampleController sample = new FormSampleController();
			int userFrk = contactDto.getUser().getUserId();
			if (0 != userFrk) {
				contact.setCreatedBy(usersSession.getViewId());
				contact.setCrtdDtTime(timestamp);
				contact.setGenericStatus(ACTIVE);
				contact.setUpDtTime(timestamp);
				LOGGER.info("User Id:::::::::" + contactDto.getUser().getUserId() + "Fname:::::::"
						+ contactDto.getUser().getFname() + ":::------->>>>>>User searched founded");
				contact.setUser(usersImpl.gettUserById(userFrk, "userId"));

				contact.setEmail(useremail);
				contact.setUpdatedBy(usersSession.getViewId());
				// :::saving contact action:::::::::::://
				contactImpl.saveContact(contact);
				// :::::End of saving::::::::::::://

				return "/menu/ViewUsersContacts.xhtml?faces-redirect=true";

				/*
				 * sample.sendUserMailTest(useremail, contactDto.getUser().getFname(),
				 * contactDto.getUser().getLname()); isValid = sample.isValid(); if (isValid) {
				 * contact.setEmail(useremail); contact.setUpdatedBy(usersSession.getViewId());
				 * // :::saving contact action::::::::::::// contactImpl.saveContact(contact);
				 * // :::::End of saving:::::::::::::// JSFMessagers.resetMessages();
				 * setValid(true); //
				 * JSFMessagers.addErrorMessage(getProvider().getValue("com.save.form.contact"))
				 * ; JSFMessagers.addErrorMessage(getProvider().getValue(
				 * "com.server.side.email.notification")); LOGGER.info(CLASSNAME +
				 * ":::Contact Details is saved"); clearContactFuileds(); return
				 * "/menu/ViewUsersContacts.xhtml?faces-redirect=true"; } else {
				 * JSFMessagers.resetMessages(); setValid(false);
				 * JSFMessagers.addErrorMessage(getProvider().getValue(
				 * "com.server.side.email.notifail")); return null; }
				 */

			} else {
				JSFMessagers.resetMessages();
				setValid(false);
				JSFMessagers.addErrorMessage(getProvider().getValue("com.server.side.sessionuser.error"));
				return null;
			}

		} catch (HibernateException e) {
			LOGGER.info(CLASSNAME + ":::Contact Details is fail with HibernateException  error");
			JSFMessagers.resetMessages();
			setValid(false);
			JSFMessagers.addErrorMessage(getProvider().getValue("com.server.side.internal.error"));
			LOGGER.info(CLASSNAME + "" + e.getMessage());
			e.printStackTrace();
			return "";
		}

	}

	private void clearContactFuileds() {
		contact = new Contact();
		userIdNumber = 0;
		// usersDetails=null;
	}

	public String saveAction(UserDto user) {
		LOGGER.info("update  saveAction method");
		// get all existing value but set "editable" to false
		Users us = new Users();
		us = new Users();
		us = usersImpl.gettUserById(user.getUserId(), "userId");

		LOGGER.info("here update sart for " + us + " useriD " + us.getUserId());

		user.setEditable(false);
		us.setFname(user.getFname());
		us.setLname(user.getLname());

		usersImpl.UpdateUsers(us);

		// return to current page
		return "/menu/ListOfUsers.xhtml?faces-redirect=true";

	}

	public String cancel(UserDto user) {
		user.setEditable(false);
		// usersImpl.UpdateUsers(user);
		return null;

	}

	public String editAction(UserDto user) {

		user.setEditable(true);
		// usersImpl.UpdateUsers(user);
		return null;
	}

	public void editUserContact(Users user) {
		HttpSession sessionuser = SessionUtils.getSession();

		if (null != user)
			renderContactForm = true;
		rendered = false;
		renderForeignCountry = false;
		// Session creation to get user info from dataTable row
		sessionuser.setAttribute("userinfo", user);
		LOGGER.info("Info Founded are userid:>>>>>>>>>>>>>>>>>>>>>>>:" + userId + "fname:" + user.getFname());
		addcontacts();
	}

	public String otherUserContact(ContactDto cont) {
		HttpSession sessionuser = SessionUtils.getSession();

		if (null != cont) {
			/*
			 * renderContactForm = true;
			 * 
			 * rendered = false; renderForeignCountry = false;
			 */

			// Session creation to get user info from dataTable row
			sessionuser.setAttribute("continfo", cont);
			LOGGER.info("Info Founded are userid:>>>>>>>>>>>>>>>>>>>>>>>:" + userId + "fname:"
					+ cont.getUser().getUserId());
			addOthercontacts();
			return "/menu/UserContacts.xhtml?faces-redirect=true";
		}
		return null;
	}

	public void addOthercontacts() {
		HttpSession session = SessionUtils.getSession();
		// Get the values from the session
		contactDto = (ContactDto) session.getAttribute("continfo");
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
		contactImpl.UpdateContact(cont);

		// return to current page
		return null;

	}

	public String cancelContact(ContactDto contact) {
		contact.setEditable(false);
		// usersImpl.UpdateUsers(user);
		return null;

	}

	public String editContactAction(ContactDto contact) {

		contact.setEditable(true);
		// usersImpl.UpdateUsers(user);
		return null;
	}

	public String addNewContact() {

		return "/menu/UserContacts.xhtml?faces-redirect=true";
	}

	public void updateTable() throws Exception {

		Users user = new Users();
		user = usersImpl.getModelWithMyHQL(new String[] { "viewId", "genericstatus" }, new Object[] { choice, ACTIVE },
				"from Users");
		if (null != user) {
			rendered = true;
			renderForeignCountry = false;
			JSFMessagers.resetMessages();
			setValid(true);
			// JSFMessagers.addErrorMessage(getProvider().getValue("error.server.side.message.viewid"));
			LOGGER.info(CLASSNAME + "sivaserside validation :: username found in the system! ");
		} else {
			rendered = false;
			renderForeignCountry = true;
			JSFMessagers.resetMessages();
			setValid(false);
			JSFMessagers.addErrorMessage(getProvider().getValue("error.server.side.message.viewid"));
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

	public Contact getContact() {
		return contact;
	}

	public void setContact(Contact contact) {
		this.contact = contact;
	}

	public Users getUsers() {
		return users;
	}

	public void setUsers(Users users) {
		this.users = users;
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

	public ContactImpl getContactImpl() {
		return contactImpl;
	}

	public void setContactImpl(ContactImpl contactImpl) {
		this.contactImpl = contactImpl;
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

	public UserDto getUserDto() {
		return userDto;
	}

	public void setUserDto(UserDto userDto) {
		this.userDto = userDto;
	}

	public List<Users> getUsersDetails() {
		return usersDetails;
	}

	public void setUsersDetails(List<Users> usersDetails) {
		this.usersDetails = usersDetails;
	}

	public List<UserDto> getUserDtoDetails() {
		return userDtoDetails;
	}

	public void setUserDtoDetails(List<UserDto> userDtoDetails) {
		this.userDtoDetails = userDtoDetails;
	}

	public void setUserIdNumber(int userIdNumber) {
		this.userIdNumber = userIdNumber;
	}

	public List<UserDto> getUserDtofiltered() {
		return userDtofiltered;
	}

	public void setUserDtofiltered(List<UserDto> userDtofiltered) {
		this.userDtofiltered = userDtofiltered;
	}

	public List<ContactDto> getContactDtoDetails() {
		return contactDtoDetails;
	}

	public void setContactDtoDetails(List<ContactDto> contactDtoDetails) {
		this.contactDtoDetails = contactDtoDetails;
	}

	public List<Contact> getContactDetails() {
		return contactDetails;
	}

	public void setContactDetails(List<Contact> contactDetails) {
		this.contactDetails = contactDetails;
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

	public String getUseremail() {
		return useremail;
	}

	public void setUseremail(String useremail) {
		this.useremail = useremail;
	}

	public int getListSize() {
		return listSize;
	}

	public void setListSize(int listSize) {
		this.listSize = listSize;
	}

	public int getUserId() {
		return userId;
	}

	public void setUserId(int userId) {
		this.userId = userId;
	}

	public boolean isRenderContactForm() {
		return renderContactForm;
	}

	public void setRenderContactForm(boolean renderContactForm) {
		this.renderContactForm = renderContactForm;
	}

	public boolean isRenderTable() {
		return renderTable;
	}

	public void setRenderTable(boolean renderTable) {
		this.renderTable = renderTable;
	}

	public int getContactSize() {
		return contactSize;
	}

	public void setContactSize(int contactSize) {
		this.contactSize = contactSize;
	}

	public List<Users> getCountUsersList() {
		return countUsersList;
	}

	public void setCountUsersList(List<Users> countUsersList) {
		this.countUsersList = countUsersList;
	}

	public int getCountUserSize() {
		return countUserSize;
	}

	public void setCountUserSize(int countUserSize) {
		this.countUserSize = countUserSize;
	}

	public List<Users> getHoldCountedList() {
		return holdCountedList;
	}

	public void setHoldCountedList(List<Users> holdCountedList) {
		this.holdCountedList = holdCountedList;
	}

	public int getUserLoginSize() {
		return userLoginSize;
	}

	public void setUserLoginSize(int userLoginSize) {
		this.userLoginSize = userLoginSize;
	}

	public boolean isRenderLoginTable() {
		return renderLoginTable;
	}

	public void setRenderLoginTable(boolean renderLoginTable) {
		this.renderLoginTable = renderLoginTable;
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

	public int getDays() {
		return days;
	}

	public void setDays(int days) {
		this.days = days;
	}

	public ContactDto getContactDto() {
		return contactDto;
	}

	public void setContactDto(ContactDto contactDto) {
		this.contactDto = contactDto;
	}

}
