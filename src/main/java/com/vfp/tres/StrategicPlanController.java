package com.vfp.tres;

import java.io.File;
import java.io.Serializable;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.sql.Timestamp;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.List;
import java.util.logging.Logger;

import javax.annotation.PostConstruct;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.ViewScoped;
import javax.faces.context.FacesContext;
import javax.servlet.ServletContext;
import javax.servlet.http.HttpSession;

import org.hibernate.ejb.criteria.Renderable;

import tres.common.DbConstant;
import tres.common.GenerateNotificationTemplete;
import tres.common.JSFBoundleProvider;
import tres.common.JSFMessagers;
import tres.common.SessionUtils;
import tres.dao.impl.ContactImpl;
import tres.dao.impl.DocumentsImpl;
import tres.dao.impl.StrategicPlanImpl;
import tres.dao.impl.UploadingActivityImpl;
import tres.dao.impl.UploadingStrategicPlanImpl;
import tres.dao.impl.UserCategoryImpl;
import tres.dao.impl.UserImpl;
import tres.domain.Activity;
import tres.domain.Contact;
import tres.domain.Documents;
import tres.domain.StrategicPlan;
import tres.domain.UploadingActivity;
import tres.domain.UploadingStrategicPlan;
import tres.domain.UserCategory;
import tres.domain.Users;
import tres.vfp.dto.ActivityDto;
import tres.vfp.dto.StrategicPlanDto;

@ManagedBean
@ViewScoped
public class StrategicPlanController implements Serializable, DbConstant {
	private static final Logger LOGGER = Logger.getLogger(Thread.currentThread().getStackTrace()[0].getClassName());
	private String CLASSNAME = "StrategicPlanController :: ";
	private static final long serialVersionUID = 1L;
	/* to manage validation messages */
	private boolean isValid;
	/* end manage validation messages */
	private Users users;
	private Users usersSession;
	private UserCategory userCategory;
	private StrategicPlan strategicPlan;
	private Contact contact;
	private List<StrategicPlan> strategicPlanDetails = new ArrayList<StrategicPlan>();
	private List<StrategicPlanDto> strategicPlanDtoDetails = new ArrayList<StrategicPlanDto>();
	private List<Users> userDetails = new ArrayList<Users>();
	private UploadingStrategicPlanImpl uplPlanImpl = new UploadingStrategicPlanImpl();
	private boolean renderUpload;
	private boolean renderStPlan = true;
	private boolean renderTable = true;
	private boolean rendered;
	private boolean renderHideBtn;
	/* class injection */

	JSFBoundleProvider provider = new JSFBoundleProvider();
	UserImpl usersImpl = new UserImpl();
	StrategicPlanImpl strategicPlanImpl = new StrategicPlanImpl();
	UserCategoryImpl categoryImpl = new UserCategoryImpl();
	ContactImpl contactImpl = new ContactImpl();
	private DocumentsImpl docsImpl = new DocumentsImpl();

	/* end class injection */
	Timestamp timestamp = new Timestamp(Calendar.getInstance().getTime().getTime());

	@SuppressWarnings("unchecked")
	@PostConstruct
	public void init() {
		HttpSession session = SessionUtils.getSession();
		usersSession = (Users) session.getAttribute("userSession");

		if (users == null) {
			users = new Users();
		}

		if (strategicPlan == null) {
			strategicPlan = new StrategicPlan();
		}

		try {
			strategicPlanDetails = strategicPlanImpl.getGenericListWithHQLParameter(new String[] { "createdBy" },
					new Object[] { usersSession.getFname() + " " + usersSession.getLname() }, "StrategicPlan",
					"planId asc");
			for (StrategicPlan strategicPlan : strategicPlanDetails) {
				StrategicPlanDto strategicPlanDto = new StrategicPlanDto();
				strategicPlanDto.setStrategicPlanId(strategicPlan.getPlanId());
				strategicPlanDto.setEditable(false);
				strategicPlanDto.setDetails(strategicPlan.getDetails());
				strategicPlanDto.setRecordedDate(strategicPlan.getRecordedDate());
				strategicPlanDto.setGenericStatus(strategicPlan.getGenericStatus());
				strategicPlanDto.setDueDate(strategicPlan.getDueDate());
				strategicPlanDto.setStartDate(strategicPlan.getStartDate());
				strategicPlanDto.setUsers(strategicPlan.getUsers());
				strategicPlanDtoDetails.add(strategicPlanDto);
			}
		} catch (Exception e) {
			setValid(false);
			JSFMessagers.addErrorMessage(getProvider().getValue("com.server.side.internal.error"));
			LOGGER.info(e.getMessage());
			e.printStackTrace();
		}

	}

	@SuppressWarnings("unchecked")
	public void savePlan() {
		try {
			strategicPlanDetails = strategicPlanImpl.getGenericListWithHQLParameter(new String[] { "createdBy" },
					new Object[] { usersSession.getFname() + " " + usersSession.getLname() }, "StrategicPlan",
					"planId asc");
			strategicPlan.setCreatedBy(usersSession.getFname() + " " + usersSession.getLname());
			strategicPlan.setCrtdDtTime(timestamp);
			strategicPlan.setGenericStatus(ACTIVE);
			strategicPlan.setUpDtTime(timestamp);
			strategicPlan.setUsers(usersImpl.gettUserById(usersSession.getUserId(), "userId"));
			strategicPlan.setUpdatedBy(usersSession.getFname() + " " + usersSession.getLname());
			strategicPlan.setRecordedDate(timestamp);
			strategicPlan.setEndDate(strategicPlan.getDueDate());
			strategicPlanImpl.saveStrategicPlan(strategicPlan);
			for (StrategicPlan strategicP : strategicPlanDetails) {
				strategicP.setGenericStatus(DESACTIVE);
				strategicPlanImpl.UpdateStrategicPlan(strategicP);
			}
			JSFMessagers.resetMessages();
			setValid(true);
			JSFMessagers.addErrorMessage(getProvider().getValue("com.save.form.strategicPlan"));
			LOGGER.info(CLASSNAME + ":::StrategicPlan Details is saved");
			clearPlanFuileds();
			SendSupportEmail email = new SendSupportEmail();
			userDetails = usersImpl.getGenericListWithHQLParameter(new String[] { "genericStatus", "userCategory" },
					new Object[] { ACTIVE, categoryImpl.getUserCategoryById(4, "userCatid") }, "Users", "userId asc");
			for (Users us : userDetails) {
				contact = contactImpl.getModelWithMyHQL(new String[] { "genericStatus", "user" },
						new Object[] { ACTIVE, us }, "from Contact");
				email.sendMailStrategicPlan("plan", us.getFname(),
						usersSession.getFname() + " " + usersSession.getLname(), contact.getEmail());
				LOGGER.info(us.getFname() + " receives email from " + usersSession.getFname() + " "
						+ usersSession.getLname() + " on this email ");
			}

		} catch (Exception e) {
			LOGGER.info(CLASSNAME + ":::Strategic Plan Details is failling with HibernateException  error");
			JSFMessagers.resetMessages();
			setValid(false);
			JSFMessagers.addErrorMessage(getProvider().getValue("com.server.side.internal.error"));
			LOGGER.info(CLASSNAME + "" + e.getMessage());
			e.printStackTrace();
		}

	}

	public String deleteFile(UploadingStrategicPlan info) {
		try {
			ServletContext ctx = (ServletContext) FacesContext.getCurrentInstance().getExternalContext().getContext();
			String realPath = ctx.getRealPath("/");
			LOGGER.info("Filse Reals Path::::" + realPath);
			Documents documents = new Documents();
			documents = docsImpl.getModelWithMyHQL(new String[] { "DocId" },
					new Object[] { info.getDocuments().getDocId() }, " from Documents");

			if (null != documents) {
				final Path destination = Paths.get(realPath + FILELOCATION + documents.getSysFilename());
				LOGGER.info("Path::" + destination);
				File file = new File(destination.toString());
				uplPlanImpl.deleteIntable(info);
				docsImpl.deleteIntable(documents);
				LOGGER.info("Delete in db operation done!!!:");
				if (file.delete()) {
					System.out.println(file.getName() + " is deleted!");
					JSFMessagers.resetMessages();
					setValid(true);
					JSFMessagers.addErrorMessage(getProvider().getValue("com.server.success.files.delete"));
				} else {
					System.out.println("Delete operation is failed.");
					JSFMessagers.resetMessages();
					setValid(false);
					JSFMessagers.addErrorMessage(getProvider().getValue("com.server.error.files.delete"));
				}

			}

		} catch (Exception e) {
			JSFMessagers.resetMessages();
			setValid(false);
			JSFMessagers.addErrorMessage(getProvider().getValue("com.server.side.internal.error"));
		}
		return null;
	}

	public void clearPlanFuileds() {
		strategicPlan = new StrategicPlan();
		strategicPlanDetails = null;
	}

	public String backBtn() {
		return "/menu/StrategicPlan.xhtml?faces-redirect=true";
	}

	public String newPlan() {
		return "/menu/InsertStrategicPlan.xhtml?faces-redirect=true";
	}

	public void changeSelectBox(String name) {

		LOGGER.info("Ajax is working with listener::::::" + name);
	}

	public String uploadStratDocs(StrategicPlanDto act) {
		HttpSession sessionuser = SessionUtils.getSession();
		if (null != act) {
			sessionuser.setAttribute("StratPlanInfo", act);
			LOGGER.info("Info Founded are strategicId:>>>>>>>>>>>>>>>>>>>>>>>:" + act.getStrategicPlanId()
					+ "Description:" + act.getDetails());

			return "/menu/FileUpload.xhtml?faces-redirect=true";
		}
		return null;

	}

	public String saveAction(StrategicPlanDto strategicPlanDto) {
		LOGGER.info("update  saveAction method");
		// get all existing value but set "editable" to false
		StrategicPlan act = new StrategicPlan();
		act = new StrategicPlan();
		act = strategicPlanImpl.getStrategicPlanById(strategicPlanDto.getStrategicPlanId(), "planId");

		LOGGER.info("here update sart for " + act + " strategicPlaniD " + act.getPlanId());

		strategicPlanDto.setEditable(false);
		act.setDetails(strategicPlanDto.getDetails());

		strategicPlanImpl.UpdateStrategicPlan(act);

		// return to current page
		return null;

	}

	public String cancel(StrategicPlanDto strategicPlanDto) {
		strategicPlanDto.setEditable(false);
		// usersImpl.UpdateUsers(user);
		return null;

	}

	public String editAction(StrategicPlanDto strategicPlanDto) {

		strategicPlanDto.setEditable(true);
		// usersImpl.UpdateUsers(user);
		return null;
	}

	public void uploadnewDoc() {
		LOGGER.info("RENDER IS WORKING");
		rendered = true;
		renderTable = false;
		renderUpload = true;
	}

	public void showUploaded() {
		this.renderUpload = false;
		this.renderHideBtn = true;
		this.renderTable = true;
		this.renderHideBtn = false;

	}

	public void hideUploaded() {
		renderHideBtn = false;
		renderUpload = true;
		renderTable = false;
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

	public StrategicPlan getStrategicPlan() {
		return strategicPlan;
	}

	public void setStrategicPlan(StrategicPlan strategicPlan) {
		this.strategicPlan = strategicPlan;
	}

	public List<StrategicPlan> getStrategicPlanDetails() {
		return strategicPlanDetails;
	}

	public void setStrategicPlanDetails(List<StrategicPlan> strategicPlanDetails) {
		this.strategicPlanDetails = strategicPlanDetails;
	}

	public List<StrategicPlanDto> getStrategicPlanDtoDetails() {
		return strategicPlanDtoDetails;
	}

	public void setStrategicPlanDtoDetails(List<StrategicPlanDto> strategicPlanDtoDetails) {
		this.strategicPlanDtoDetails = strategicPlanDtoDetails;
	}

	public JSFBoundleProvider getProvider() {
		return provider;
	}

	public void setProvider(JSFBoundleProvider provider) {
		this.provider = provider;
	}

	public StrategicPlanImpl getStrategicPlanImpl() {
		return strategicPlanImpl;
	}

	public void setStrategicPlanImpl(StrategicPlanImpl strategicPlanImpl) {
		this.strategicPlanImpl = strategicPlanImpl;
	}

	public boolean isRenderUpload() {
		return renderUpload;
	}

	public void setRenderUpload(boolean renderUpload) {
		this.renderUpload = renderUpload;
	}

	public boolean isRenderStPlan() {
		return renderStPlan;
	}

	public void setRenderStPlan(boolean renderStPlan) {
		this.renderStPlan = renderStPlan;
	}

	public boolean isRenderTable() {
		return renderTable;
	}

	public void setRenderTable(boolean renderTable) {
		this.renderTable = renderTable;
	}

	public boolean isRendered() {
		return rendered;
	}

	public void setRendered(boolean rendered) {
		this.rendered = rendered;
	}

	public boolean isRenderHideBtn() {
		return renderHideBtn;
	}

	public void setRenderHideBtn(boolean renderHideBtn) {
		this.renderHideBtn = renderHideBtn;
	}

	public UploadingStrategicPlanImpl getUplPlanImpl() {
		return uplPlanImpl;
	}

	public void setUplPlanImpl(UploadingStrategicPlanImpl uplPlanImpl) {
		this.uplPlanImpl = uplPlanImpl;
	}

}
