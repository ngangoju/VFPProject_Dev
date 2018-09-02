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

import tres.common.DbConstant;
import tres.common.JSFBoundleProvider;
import tres.common.JSFMessagers;
import tres.common.SessionUtils;
import tres.dao.impl.ContactImpl;
import tres.dao.impl.UserImpl;
import tres.domain.Contact;
import tres.domain.Users;
import tres.vfp.dto.UserDto;



@ManagedBean
@ViewScoped
public class UserContactController implements Serializable, DbConstant {
	private static final Logger LOGGER = Logger.getLogger(Thread.currentThread().getStackTrace()[0].getClassName());
	private String CLASSNAME = "UserContactController :: ";
	private static final long serialVersionUID = 1L;
	/*to manage validation messages*/
	private boolean isValid;
	/*end  manage validation messages*/
	private Contact contact;
	private Users users;
	private UserDto userDto;
	private Users usersSession;
	private  int   userIdNumber;
	private List<Users> usersDetails = new ArrayList<Users>();
	private List<UserDto> userDtoDetails = new ArrayList<UserDto>();
	private List<UserDto> userDtofiltered = new ArrayList<UserDto>();
	/*class injection*/
	JSFBoundleProvider provider = new JSFBoundleProvider();
	UserImpl usersImpl = new UserImpl();
	ContactImpl contactImpl=new ContactImpl();
	Timestamp timestamp = new Timestamp(Calendar.getInstance().getTime().getTime());
	
	@SuppressWarnings("unchecked")
	@PostConstruct
	public void init() {
		HttpSession session = SessionUtils.getSession();
		usersSession= (Users) session.getAttribute("userSession");
		
		if (contact == null) {
			contact = new Contact();
		}
		
		if (users == null) {
			users = new Users();
		}
		if (userDto == null) {
			userDto = new UserDto();
		}
		
		
		try {
			
			usersDetails=usersImpl.getGenericListWithHQLParameter(new String[] {"genericStatus","status"},new Object[] {ACTIVE,ACTIVE}, "Users", "userId desc");
			for (Users user : usersDetails){
				UserDto userDto = new UserDto();
				userDto.setEditable(false);
				userDto.setFname(user.getFname());
				userDto.setLname(user.getLname());
				userDto.setViewId(user.getViewId());
				userDto.setAddress(user.getAddress());
				userDto.setUserId(user.getUserId());
				userDto.setUserCategory(user.getUserCategory());
				userDtoDetails.add(userDto);
			}
		} catch (Exception e) {
			setValid(false);
			JSFMessagers.addErrorMessage(getProvider().getValue("com.server.side.internal.error"));
			LOGGER.info(e.getMessage());
			e.printStackTrace();
		}
	
		
	}
	
	

public  String saveContact() {
	try {
		contact.setCreatedBy(usersSession.getViewId());
		contact.setCrtdDtTime(timestamp);
		contact.setGenericStatus(ACTIVE);
		contact.setUpDtTime(timestamp);
		contact.setUser(usersImpl.gettUserById(userIdNumber, "userId"));
		contact.setUpdatedBy(usersSession.getViewId());
	contactImpl.saveContact(contact);
	JSFMessagers.resetMessages();
	setValid(true);
	JSFMessagers.addErrorMessage(getProvider().getValue("com.save.form.contact"));
	LOGGER.info(CLASSNAME+":::Contact Details is saved");
	clearContactFuileds();
	return"";
	}catch(HibernateException e) {
		LOGGER.info(CLASSNAME+":::Contact Details is fail with HibernateException  error");
		JSFMessagers.resetMessages();
		setValid(false);
		JSFMessagers.addErrorMessage(getProvider().getValue("com.server.side.internal.error"));
		LOGGER.info(CLASSNAME+""+e.getMessage());
		e.printStackTrace();
		return"";	
	}
	
}
private void clearContactFuileds() {
	contact=new Contact();
	usersDetails=null;
}


public String saveAction(UserDto user) {
	LOGGER.info("update  saveAction method");
	//get all existing value but set "editable" to false 
	Users us=new Users();
	us=new Users();
	us=usersImpl.gettUserById(user.getUserId(), "userId");
	
	//for (UserDto user : userDtoDetails){
		LOGGER.info("here update sart for "+user +" Object ");

		user.setEditable(false);
		us.setFname(user.getFname());
		us.setFname(user.getLname());
	
			usersImpl.UpdateUsers(us);
		
	//return to current page
	return null;
	
}

public String cancel(UserDto user) {
	user.setEditable(false);
	//usersImpl.UpdateUsers(user);
	return null;
	
	
}

public String editAction(UserDto user) {
    
	user.setEditable(true);
	//usersImpl.UpdateUsers(user);
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







}
