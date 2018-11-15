package com.vfp.tres;

import java.io.Serializable;
import java.util.logging.Logger;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.List;
import java.sql.Timestamp;
import java.text.SimpleDateFormat;

import javax.annotation.PostConstruct;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.ViewScoped;
import javax.faces.context.FacesContext;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;
import org.hibernate.HibernateException;
import tres.common.DbConstant;
import tres.common.Formating;
import tres.common.JSFBoundleProvider;
import tres.common.JSFMessagers;
import tres.common.SessionUtils;
import tres.dao.impl.InstitutionImpl;
import tres.dao.impl.UserImpl;
import tres.dao.impl.PaymentImpl;
import tres.domain.Institution;
import tres.domain.MenuGroup;
import tres.domain.Users;
import tres.domain.PaymentRecords;
import tres.vfp.dto.PaymentRecordsDto;
import tres.vfp.dto.UserDto;


@SuppressWarnings("unused")
@ManagedBean
@ViewScoped
public class PaymentRecordsContoller implements Serializable, DbConstant{

	private static final Logger LOGGER = Logger.getLogger(Thread.currentThread().getStackTrace()[0].getClassName());
	private String CLASSNAME = "GroupOfMenuController :: ";
	private static final long serialVersionUID = 1L;
	
	/* to manage validation messages */
	private boolean isValid;
	/* end manage validation messages */
	private int paymentId;
	private String paymentCode;
	private Timestamp paymentDate;
	private Timestamp paymentExpiretionDate;
    private String  amount;
	private String   currency;
	private String   paymentChanel;
	private String  paymentStatus;
   	private String  bankRefernceNo;
   	private String  comment;
	private Users paymentApprovedBy;
	private Institution institution;
	private Users userSessions;
	private PaymentRecords paymentRecords;
	private PaymentRecords paymentRecordsSession;
	private PaymentRecordsDto paymentRecordsDto;
	private int institutionId;
	private Date dateofpayment;

	
	Timestamp timestamp = new Timestamp(Calendar.getInstance().getTime().getTime());
	
	private List<PaymentRecords> paymentRecordsDetails = new ArrayList<PaymentRecords>();
	private List<Institution> institutionDetails = new ArrayList<Institution>();
	
	private List<PaymentRecordsDto> paymentRecordsDtoDetails = new ArrayList<PaymentRecordsDto>();
	
	JSFBoundleProvider provider = new JSFBoundleProvider();
	
	InstitutionImpl institutionImpl = new InstitutionImpl();
	
	PaymentImpl paymentImpl = new PaymentImpl();
	
	@SuppressWarnings("unchecked")
	@PostConstruct
	public void init() {
		HttpSession session = SessionUtils.getSession();
		userSessions = (Users) session.getAttribute("userSession");
		
		paymentRecordsSession=(PaymentRecords)session.getAttribute("paymentRecordsSession");
		
		if (paymentRecords == null) {
			paymentRecords = new PaymentRecords();
		}
		if (institution == null) {
			institution = new Institution();

		}
		if (paymentRecordsDto == null) {
			paymentRecordsDto = new PaymentRecordsDto();
		}
		
		try {
			institutionDetails = institutionImpl.getListWithHQL(SELECT_INSTITUTION);
			//MenuGroup menu = new MenuGroup();
			//menu = new MenuGroup();
//			MenuGroup menu = menuGroupImpl.getMenuGroupById(menuGroupSession.getMenuGroupId(), "menuGroupId");
//			
//			MenuGroupDto menuGroupDto = new MenuGroupDto();
//			menuGroupDto.seteditables(false);
//			menuGroupDto.setMenuGroupName(menu.getMenuGroupName());
//			menuGroupDto.setDefaultGroupMenu(menu.getDefaulGrouptMenu());
//			menuGroupDto.setUserCategory(menu.getUserCategory());
//			menuGroupDtoDetails.add(menuGroupDto);
//			// below list concern list of all users by changing their status
			paymentRecordsDetails = paymentImpl.getListWithHQL(SELECT_PAYMENTRECORDS);
			
			
			for (PaymentRecords paymentRecordss : paymentRecordsDetails) {
				PaymentRecordsDto paymentRecordsDtos = new PaymentRecordsDto();
				paymentRecordsDtos.setPaymentId(paymentRecordss.getPaymentId());
				paymentRecordsDtos.seteditables(false);
				paymentRecordsDtos.setInstitution(paymentRecordss.getInstitution());
				paymentRecordsDtos.setAmount(paymentRecordss.getAmount());
				paymentRecordsDtos.setCurrency(paymentRecordss.getCurrency());
				paymentRecordsDtos.setBankRefernceNo(paymentRecordss.getBankRefernceNo());
				paymentRecordsDtos.setBankRefernceNo(paymentRecordss.getBankRefernceNo());
				paymentRecordsDtos.setPaymentChanel(paymentRecordss.getPaymentChanel());
				paymentRecordsDtos.setPaymentDate(paymentRecordss.getPaymentDate());
				paymentRecordsDtos.setPaymentExpiretionDate(paymentRecordss.getPaymentExpiretionDate());
				paymentRecordsDtos.setPaymentStatus(paymentRecordss.getPaymentStatus());
				
				if (paymentRecordss.getPaymentStatus().equals(ACTIVE)) {
					paymentRecordsDtos.setAction(DESACTIVE);

				} else if (paymentRecordss.getPaymentStatus().equals(DESACTIVE)) {

					paymentRecordsDtos.setAction(ACTIVE);
					paymentRecordss.setPaymentStatus(DESACTIVE);

				} else {
					paymentRecordsDtos.setAction(DESACTIVE);
					paymentRecordss.setPaymentStatus(DESACTIVE);

				}
				paymentRecordsDtoDetails.add(paymentRecordsDtos);

			}
		} catch (Exception e) {
			setValid(false);
			JSFMessagers.addErrorMessage(getProvider().getValue("com.server.side.internal.error"));
			LOGGER.info(e.getMessage());
			e.printStackTrace();
		}
		
		

	}
	
	public String savePaymentInfo() throws IOException {
		try {
LOGGER.info("saving "+paymentRecords);

PaymentRecords payment=new PaymentRecords();

payment= paymentImpl.getModelWithMyHQL(new String[] { "paymentCode" },
			new Object[] {paymentRecords.getPaymentCode()}, "from PaymentRecords ");

		paymentRecords.setCreatedBy(userSessions.getViewId());
		paymentRecords.setCrtdDtTime(timestamp);
		paymentRecords.setGenericStatus(ACTIVE);
		paymentRecords.setPaymentStatus(ACTIVE);
		paymentRecords.setUpdatedBy(userSessions.getViewId());
		paymentRecords.setUpDtTime(timestamp);
		
		paymentRecords.setInstitution(institutionImpl.getInstitutionById(institutionId, "institutionId"));
		
		
					paymentImpl.savePaymentRecords(paymentRecords);

					JSFMessagers.resetMessages();
					setValid(true);
					JSFMessagers.addErrorMessage(getProvider().getValue("com.save.form.user"));
					LOGGER.info(CLASSNAME + ":::PaymentRecords is saved");
					clearUserFuileds();
					return "";

		} catch (Exception ex) {
			LOGGER.info(CLASSNAME + ":::PaymentRecords is fail with HibernateException  error");
			JSFMessagers.resetMessages();
			setValid(false);
			JSFMessagers.addErrorMessage(getProvider().getValue("com.server.side.internal.error"));
			LOGGER.info(CLASSNAME + "" + ex.getMessage());
			ex.printStackTrace();
		}
		return "";
	}
	
	public String cancel(PaymentRecordsDto paymentss) {
		LOGGER.info("EMILE  EMILE  EMILE  EMILE  EMILE  EMILE  EMILE  EMILE  EMILE  ");
		paymentss.seteditables(false);
		// usersImpl.UpdateUsers(user);
		return null;

	}

	public String editAction(PaymentRecordsDto paymentss) {

		paymentss.seteditables(true);
		// usersImpl.UpdateUsers(user);
		return null;
	}
	
	public String saveAction(PaymentRecordsDto payment) {
		LOGGER.info("update  saveAction method");
		/* System.out.println("**************update  saveAction method"); */
		// get all existing value but set "editables" to false

		if (payment != null) {

			PaymentRecords payments = new PaymentRecords();
			payments = new PaymentRecords();
			payments = paymentImpl.gettPaymentRecordsById(payment.getPaymentId(), "paymentId");

			LOGGER.info("here update sart for " + payments + " paymentId " + payments.getPaymentId());
			System.out.println("++++++++++++++++++++++++++here update sart for " + payments + " paymentId " + payments.getPaymentId());
			payment.seteditables(false);
			payments.setInstitution(payment.getInstitution());
			payments.setPaymentDate(payment.getPaymentDate());
			payments.setPaymentExpiretionDate(payment.getPaymentExpiretionDate());
			payments.setAmount(payment.getAmount());
			payments.setCurrency(payment.getCurrency());
			payments.setBankRefernceNo(payment.getBankRefernceNo());
			
			paymentImpl.UpdatePaymentRecords(payments);

			// return to current page
			return null;
		} else {
			JSFMessagers.resetMessages();
			setValid(false);
			JSFMessagers.addErrorMessage(getProvider().getValue("com.save.form.userprofile"));
			return null;
		}

	}
	
	public String newAction(PaymentRecordsDto payment) {
		LOGGER.info("update  saveAction method");
		// get all existing value but set "editables" to false
		PaymentRecords payments = new PaymentRecords();
		payments = new PaymentRecords();
		payments = paymentImpl.gettPaymentRecordsById(payment.getPaymentId(), "paymentId");

		LOGGER.info("here update sart for " + payments + " PaymentId " + payments.getPaymentId());

		payment.seteditables(false);
		payments.setInstitution(payment.getInstitution());
		payments.setPaymentDate(payment.getPaymentDate());
		payments.setPaymentExpiretionDate(payment.getPaymentExpiretionDate());
		payments.setAmount(payment.getAmount());
		payments.setCurrency(payment.getCurrency());
		payments.setBankRefernceNo(payment.getBankRefernceNo());
		
		paymentImpl.UpdatePaymentRecords(payments);
	
		// return to current page
		return "/menu/ViewPaymentRecords.xhtml?faces-redirect=true";

	}
	
	public String updateStatus(PaymentRecordsDto payment) {
		LOGGER.info("update  saveAction method");
		// get all existing value but set "editables" to false
		PaymentRecords payments = new PaymentRecords();
		payments = new PaymentRecords();
		payments = paymentImpl.gettPaymentRecordsById(payment.getPaymentId(), "paymentId");

		LOGGER.info("here update sart for " + payments + " PaymentId " + payments.getPaymentId());

		if (payment.getPaymentStatus().equals(ACTIVE)) {

			payments.setPaymentStatus(DESACTIVE);
			paymentImpl.UpdatePaymentRecords(payments);
		} else {

			payments.setPaymentStatus(ACTIVE);
		}
		paymentImpl.UpdatePaymentRecords(payments);

		// return to current page
		return "/menu/ViewPaymentRecords.xhtml?faces-redirect=true";

	}
	
	public String addNewPaymentRecords() {

		return "/menu/PaymentRecords.xhtml?faces-redirect=true";

	}
	
	public void clearUserFuileds() {

		paymentRecords = new PaymentRecords();
		paymentRecordsDetails = null;
	}
	
	public String getContextPath() {

		HttpServletRequest request = (HttpServletRequest) FacesContext.getCurrentInstance().getExternalContext()
				.getRequest();

		return request.getContextPath();
	}
	public boolean isValid() {
		return isValid;
	}

	public void setValid(boolean isValid) {
		this.isValid = isValid;
	}

	public int getPaymentId() {
		return paymentId;
	}

	public void setPaymentId(int paymentId) {
		this.paymentId = paymentId;
	}

	public String getPaymentCode() {
		return paymentCode;
	}

	public void setPaymentCode(String paymentCode) {
		this.paymentCode = paymentCode;
	}

	public Timestamp getPaymentDate() {
		return paymentDate;
	}

	public void setPaymentDate(Timestamp paymentDate) {
		this.paymentDate = paymentDate;
	}

	public Timestamp getPaymentExpiretionDate() {
		return paymentExpiretionDate;
	}

	public void setPaymentExpiretionDate(Timestamp paymentExpiretionDate) {
		this.paymentExpiretionDate = paymentExpiretionDate;
	}

	public String getAmount() {
		return amount;
	}

	public void setAmount(String amount) {
		this.amount = amount;
	}

	public String getCurrency() {
		return currency;
	}

	public void setCurrency(String currency) {
		this.currency = currency;
	}

	public String getPaymentChanel() {
		return paymentChanel;
	}

	public void setPaymentChanel(String paymentChanel) {
		this.paymentChanel = paymentChanel;
	}

	public String getPaymentStatus() {
		return paymentStatus;
	}

	public void setPaymentStatus(String paymentStatus) {
		this.paymentStatus = paymentStatus;
	}

	public String getBankRefernceNo() {
		return bankRefernceNo;
	}

	public void setBankRefernceNo(String bankRefernceNo) {
		this.bankRefernceNo = bankRefernceNo;
	}

	public String getComment() {
		return comment;
	}

	public void setComment(String comment) {
		this.comment = comment;
	}

	public Users getPaymentApprovedBy() {
		return paymentApprovedBy;
	}

	public void setPaymentApprovedBy(Users paymentApprovedBy) {
		this.paymentApprovedBy = paymentApprovedBy;
	}

	public Institution getInstitution() {
		return institution;
	}

	public void setInstitution(Institution institution) {
		this.institution = institution;
	}

	public Users getUserSessions() {
		return userSessions;
	}

	public void setUserSessions(Users userSessions) {
		this.userSessions = userSessions;
	}

	public PaymentRecords getPaymentRecords() {
		return paymentRecords;
	}

	public void setPaymentRecords(PaymentRecords paymentRecords) {
		this.paymentRecords = paymentRecords;
	}

	public PaymentRecords getPaymentRecordsSession() {
		return paymentRecordsSession;
	}

	public void setPaymentRecordsSession(PaymentRecords paymentRecordsSession) {
		this.paymentRecordsSession = paymentRecordsSession;
	}

	public PaymentRecordsDto getPaymentRecordsDto() {
		return paymentRecordsDto;
	}

	public void setPaymentRecordsDto(PaymentRecordsDto paymentRecordsDto) {
		this.paymentRecordsDto = paymentRecordsDto;
	}

	public int getInstitutionId() {
		return institutionId;
	}

	public void setInstitutionId(int institutionId) {
		this.institutionId = institutionId;
	}

	public Timestamp getTimestamp() {
		return timestamp;
	}

	public void setTimestamp(Timestamp timestamp) {
		this.timestamp = timestamp;
	}

	public List<PaymentRecords> getPaymentRecordsDetails() {
		return paymentRecordsDetails;
	}

	public void setPaymentRecordsDetails(List<PaymentRecords> paymentRecordsDetails) {
		this.paymentRecordsDetails = paymentRecordsDetails;
	}

	public List<Institution> getInstitutionDetails() {
		return institutionDetails;
	}

	public void setInstitutionDetails(List<Institution> institutionDetails) {
		this.institutionDetails = institutionDetails;
	}

	public List<PaymentRecordsDto> getPaymentRecordsDtoDetails() {
		return paymentRecordsDtoDetails;
	}

	public void setPaymentRecordsDtoDetails(List<PaymentRecordsDto> paymentRecordsDtoDetails) {
		this.paymentRecordsDtoDetails = paymentRecordsDtoDetails;
	}

	public JSFBoundleProvider getProvider() {
		return provider;
	}

	public void setProvider(JSFBoundleProvider provider) {
		this.provider = provider;
	}

	public InstitutionImpl getInstitutionImpl() {
		return institutionImpl;
	}

	public void setInstitutionImpl(InstitutionImpl institutionImpl) {
		this.institutionImpl = institutionImpl;
	}

	public PaymentImpl getPaymentImpl() {
		return paymentImpl;
	}

	public void setPaymentImpl(PaymentImpl paymentImpl) {
		this.paymentImpl = paymentImpl;
	}

	public String getCLASSNAME() {
		return CLASSNAME;
	}

	public void setCLASSNAME(String cLASSNAME) {
		CLASSNAME = cLASSNAME;
	}

	public Date getDateofpayment() {
		return dateofpayment;
	}

	public void setDateofpayment(Date dateofpayment) {
		this.dateofpayment = dateofpayment;
	}

	
	

}
