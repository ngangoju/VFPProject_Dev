package com.vfp.tres;

import java.io.Serializable;
import java.sql.Timestamp;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.List;
import java.util.logging.Logger;

import javax.annotation.PostConstruct;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.ViewScoped;
import javax.servlet.http.HttpSession;

import org.hibernate.HibernateException;
import org.jboss.marshalling.ClassExternalizerFactory;

import tres.common.DbConstant;
import tres.common.Formating;
import tres.common.JSFBoundleProvider;
import tres.common.JSFMessagers;
import tres.common.SessionUtils;
import tres.dao.impl.ContactImpl;
import tres.dao.impl.UserCategoryImpl;
import tres.dao.impl.UserImpl;
import tres.domain.Contact;
import tres.domain.UserCategory;
import tres.domain.Users;
import tres.vfp.dto.ContactDto;
import tres.vfp.dto.UserCategoryDto;
import tres.vfp.dto.UserDto;

@ManagedBean
@ViewScoped
public class UserCategoryController implements Serializable, DbConstant {
	private static final Logger LOGGER = Logger.getLogger(Thread.currentThread().getStackTrace()[0].getClassName());
	private String CLASSNAME = "UserCategoryController :: ";
	private static final long serialVersionUID = 1L;
	/* to manage validation messages */
	private boolean isValid;
	/* end manage validation messages */
	private UserCategory userCategory;
	private Users usersSession;
	private List<UserCategory> categoryDetails = new ArrayList<UserCategory>();
	private List<Contact> contactDetails = new ArrayList<Contact>();
	private List<ContactDto> contactDtoDetails = new ArrayList<ContactDto>();
	private List<UserCategoryDto> categoryDtoDetails = new ArrayList<UserCategoryDto>();
	/* class injection */
	JSFBoundleProvider provider = new JSFBoundleProvider();
	UserImpl usersImpl = new UserImpl();
	ContactImpl contactImpl = new ContactImpl();
	private boolean rendered;
	UserCategoryImpl userCatImpl = new UserCategoryImpl();
	Timestamp timestamp = new Timestamp(Calendar.getInstance().getTime().getTime());
	private String repEmail;
	private String option;
	private Contact cont = new Contact();

	@SuppressWarnings("unchecked")
	@PostConstruct
	public void init() {
		HttpSession session = SessionUtils.getSession();
		usersSession = (Users) session.getAttribute("userSession");

		if (userCategory == null) {
			userCategory = new UserCategory();
		}
		if (cont == null) {
			cont = new Contact();
		}

		try {
			categoryDetails = userCatImpl.getGenericListWithHQLParameter(new String[] { "genericStatus" },
					new Object[] { ACTIVE }, "UserCategory", "userCatid desc");
			/*
			 * categoryDetails=userCatImpl.getListWithHQL(SELECT_USERCATEGORY);
			 * 
			 * for (UserCategory userCat : categoryDetails) { UserCategoryDto catDto = new
			 * UserCategoryDto(); catDto.setEditable(false);
			 * catDto.setUserCatid(userCat.getUserCatid());
			 * catDto.setUsercategoryName(userCat.getUsercategoryName()); if
			 * (userCat.getStatus().equals(ACTIVE)) { catDto.setAction(DESACTIVE); } else {
			 * catDto.setAction(ACTIVE); } categoryDtoDetails.add(catDto); }
			 */

			categoryDtoDetails = listCategory(categoryDetails);
		} catch (Exception e) {
			setValid(false);
			JSFMessagers.addErrorMessage(getProvider().getValue("com.server.side.internal.error"));
			LOGGER.info(e.getMessage());
			e.printStackTrace();
		}

	}

	@SuppressWarnings("rawtypes")
	public List listCategory(List<UserCategory> userCatDetails) {
		List<UserCategoryDto> categoryDtoDetails = new ArrayList<UserCategoryDto>();
		for (UserCategory userCat : userCatDetails) {
			UserCategoryDto catDto = new UserCategoryDto();
			catDto.setEditable(false);
			catDto.setNotify(false);
			catDto.setUserCatid(userCat.getUserCatid());
			catDto.setUsercategoryName(userCat.getUsercategoryName());
			catDto.setStatus(userCat.getStatus());
			if (userCat.getStatus().equals(ACTIVE)) {
				catDto.setAction(DESACTIVE);
			} else {
				catDto.setAction(ACTIVE);
			}
			categoryDtoDetails.add(catDto);
		}
		return (categoryDtoDetails);
	}

	@SuppressWarnings("unchecked")
	public String updateStatus(UserCategoryDto categ) throws Exception {
		LOGGER.info("update  saveAction method");
		// get all existing value but set "editable" to false
		UserCategory cat = new UserCategory();

		if (categ != null)
			cat = userCatImpl.getUserCategoryById(categ.getUserCatid(), "userCatid");
		if (cat != null)
			LOGGER.info("here update sart for " + cat + " useriD " + cat.getStatus());
		if (categ.getStatus().equals(ACTIVE)) {
			cat.setUpdatedBy(usersSession.getViewId());
			cat.setUpDtTime(timestamp);
			cat.setStatus(DESACTIVE);
			categ.setNotify(false);
		} else {
			cat.setUpdatedBy(usersSession.getViewId());
			cat.setUpDtTime(timestamp);
			cat.setStatus(ACTIVE);
			categ.setNotify(false);
		}
		Contact ct = new Contact();

		if (null != repEmail) {
			ct = contactImpl.getModelWithMyHQL(new String[] { "email" }, new Object[] { repEmail }, "from Contact");
			if (null != ct) {
				userCatImpl.UpdateUsercategory(cat);
				categoryDetails = userCatImpl.getGenericListWithHQLParameter(new String[] { "genericStatus" },
						new Object[] { ACTIVE }, "UserCategory", "userCatid desc");
				categoryDtoDetails = listCategory(categoryDetails);
				LOGGER.info("EMAIL TO NOTIFY::::::::::::::::::::::::::::::::::::::" + repEmail);
				boolean valid = notifyRepresentativeChange(repEmail);
				if (valid) {
					JSFMessagers.resetMessages();
					setValid(true);
					JSFMessagers.addErrorMessage(getProvider().getValue("com.notify.representative.cat"));
					LOGGER.info(CLASSNAME + "::Email sent successuful!!");
					this.repEmail=null;
					// return to current page
				} else {
					JSFMessagers.resetMessages();
					setValid(false);
					JSFMessagers.addErrorMessage(getProvider().getValue("com.notifyError.representative.cat"));
					LOGGER.info(CLASSNAME + "::Fail to send Email!!");
					this.repEmail=null;
				}
			} else {
				this.repEmail=null;
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

	public String SaveUserCategory() {

		try {
			try {
				UserCategory userCat = new UserCategory();
				userCat = userCatImpl.getModelWithMyHQL(new String[] { "usercategoryName" },
						new Object[] { userCategory.getUsercategoryName() }, "from UserCategory");
				if (null != userCat) {
					JSFMessagers.resetMessages();
					setValid(false);
					JSFMessagers.addErrorMessage(getProvider().getValue("error.server.side.dupicate.categoryname"));
					LOGGER.info(
							CLASSNAME + "sivaserside validation ::  Category Name already  recorded in the system! ");
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
			userCategory.setCreatedBy(usersSession.getViewId());
			userCategory.setCrtdDtTime(timestamp);
			userCategory.setGenericStatus(ACTIVE);
			userCategory.setStatus(ACTIVE);
			userCategory.setUpdatedBy(usersSession.getViewId());
			userCategory.setCrtdDtTime(timestamp);
			userCatImpl.saveUsercategory(userCategory);
			JSFMessagers.resetMessages();
			setValid(true);
			JSFMessagers.addErrorMessage(getProvider().getValue("com.save.form.usercategory"));
			LOGGER.info(CLASSNAME + "::UserCategory Details is saved");
			clearCategoryFuileds();
			return "/menu/ListOfUserCategory.xhtml?faces-redirect=true";
		} catch (HibernateException e) {
			// TODO: handle exception
			LOGGER.info(CLASSNAME + ":::Contact Details is fail with HibernateException  error");
			JSFMessagers.resetMessages();
			setValid(false);
			JSFMessagers.addErrorMessage(getProvider().getValue("com.server.side.internal.error"));
			LOGGER.info(CLASSNAME + "" + e.getMessage());
			e.printStackTrace();
		}
		return "";
	}

	public void showCatTable() {

		if (categoryDtoDetails.size() != 0)
			rendered = true;
	}

	private void clearCategoryFuileds() {
		userCategory = new UserCategory();
		categoryDetails = null;
	}

	public String saveAction(UserCategoryDto cat) {
		LOGGER.info("update  saveAction method");
		// get all existing value but set "editable" to false

		LOGGER.info("UserCat:++++++++++++++++++++++++++" + cat.getUserCatid());
		UserCategory usercat = new UserCategory();
		usercat = new UserCategory();
		usercat = userCatImpl.getUserCategoryById(cat.getUserCatid(), "userCatid");

		LOGGER.info("here update sart for " + usercat + " useriD " + usercat.getUserCatid());

		cat.setEditable(false);
		usercat.setUpdatedBy(usersSession.getViewId());
		usercat.setUpDtTime(timestamp);
		usercat.setUsercategoryName(cat.getUsercategoryName());

		userCatImpl.UpdateUsercategory(usercat);

		// return to current page
		return null;

	}

	public String addNewCategory() {

		return "/menu/UserCategory.xhtml?faces-redirect=true";
	}

	public String cancel(UserCategoryDto cat) {
		cat.setEditable(false);
		return null;
	}

	public String cancelChange(UserCategoryDto cat) {
		cat.setNotify(false);
		this.repEmail=null;
		return null;
	}

	public String otherUserCategory(UserCategoryDto cat) {
		if (null != cat) {
			return "/menu/UserCategory.xhtml?faces-redirect=true";
		}
		return null;
	}

	public String editAction(UserCategoryDto cat) {

		cat.setEditable(true);
		return null;
	}

	public String renderAction(UserCategoryDto cat) {
		cat.setNotify(true);
		return null;
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

	public UserCategory getUserCategory() {
		return userCategory;
	}

	public void setUserCategory(UserCategory userCategory) {
		this.userCategory = userCategory;
	}

	public Users getUsersSession() {
		return usersSession;
	}

	public void setUsersSession(Users usersSession) {
		this.usersSession = usersSession;
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

	public Timestamp getTimestamp() {
		return timestamp;
	}

	public void setTimestamp(Timestamp timestamp) {
		this.timestamp = timestamp;
	}

	public List<UserCategory> getCategoryDetails() {
		return categoryDetails;
	}

	public void setCategoryDetails(List<UserCategory> categoryDetails) {
		this.categoryDetails = categoryDetails;
	}

	public List<UserCategoryDto> getCategoryDtoDetails() {
		return categoryDtoDetails;
	}

	public void setCategoryDtoDetails(List<UserCategoryDto> categoryDtoDetails) {
		this.categoryDtoDetails = categoryDtoDetails;
	}

	public UserCategoryImpl getUserCatImpl() {
		return userCatImpl;
	}

	public void setUserCatImpl(UserCategoryImpl userCatImpl) {
		this.userCatImpl = userCatImpl;
	}

	public boolean isRendered() {
		return rendered;
	}

	public void setRendered(boolean rendered) {
		this.rendered = rendered;
	}

	public String getRepEmail() {
		return repEmail;
	}

	public void setRepEmail(String repEmail) {
		this.repEmail = repEmail;
	}

	public List<Contact> getContactDetails() {
		return contactDetails;
	}

	public void setContactDetails(List<Contact> contactDetails) {
		this.contactDetails = contactDetails;
	}

	public ContactImpl getContactImpl() {
		return contactImpl;
	}

	public void setContactImpl(ContactImpl contactImpl) {
		this.contactImpl = contactImpl;
	}

	public String getOption() {
		return option;
	}

	public void setOption(String option) {
		this.option = option;
	}

	public Contact getCont() {
		return cont;
	}

	public void setCont(Contact cont) {
		this.cont = cont;
	}

	public List<ContactDto> getContactDtoDetails() {
		return contactDtoDetails;
	}

	public void setContactDtoDetails(List<ContactDto> contactDtoDetails) {
		this.contactDtoDetails = contactDtoDetails;
	}
}
