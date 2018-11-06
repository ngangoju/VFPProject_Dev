
package com.vfp.tres;

import java.io.Serializable;
import java.sql.Timestamp;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.List;
import java.util.UUID;
import java.util.logging.Logger;

import javax.annotation.PostConstruct;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.ViewScoped;
import javax.servlet.http.HttpSession;

import org.apache.commons.io.FilenameUtils;
import org.primefaces.event.FileUploadEvent;

import tres.common.DbConstant;
import tres.common.GenerateNotificationTemplete;
import tres.common.JSFBoundleProvider;
import tres.common.JSFMessagers;
import tres.common.SessionUtils;
import tres.common.UploadUtility;
import tres.dao.impl.DocumentsImpl;
import tres.dao.impl.MenuAssignmentImpl;
import tres.dao.impl.MenuGroupImpl;
import tres.dao.impl.UploadingFilesImpl;
import tres.dao.impl.UploadingStrategicPlanImpl;
import tres.dao.impl.UserImpl;
import tres.domain.Documents;
import tres.domain.MenuAssignment;
import tres.domain.MenuGroup;
import tres.domain.StrategicPlan;
import tres.domain.UploadingFiles;
import tres.domain.UploadingStrategicPlan;
import tres.domain.Users;
import tres.vfp.dto.StrategicPlanDto;

@ManagedBean
@ViewScoped
public class FormSampleController implements Serializable, DbConstant {
	private static final Logger LOGGER = Logger.getLogger(Thread.currentThread().getStackTrace()[0].getClassName());
	private String CLASSNAME = "FormSampleController :: ";
	private static final long serialVersionUID = 1L;
	/* to manage validation messages */
	private boolean isValid;
	/* end manage validation messages */
	private Users users;
	private MenuAssignment menuAssignment;
	private MenuGroup menuGroup;
	private List<MenuGroup> menuGroupDetails = new ArrayList<MenuGroup>();

	/* class injection */
	GenerateNotificationTemplete gen = new GenerateNotificationTemplete();
	JSFBoundleProvider provider = new JSFBoundleProvider();
	UserImpl usersImpl = new UserImpl();
	MenuAssignmentImpl menuAssignmentImpl = new MenuAssignmentImpl();
	MenuGroupImpl menuGroupImpl = new MenuGroupImpl();
	DocumentsImpl documentsImpl = new DocumentsImpl();
	UploadingFilesImpl uploadingFilesImpl = new UploadingFilesImpl();
	/* end class injection */
	Timestamp timestamp = new Timestamp(Calendar.getInstance().getTime().getTime());
	private Documents documents;
	private UploadingFiles uploadingFiles;
	private UploadingStrategicPlan uploadingPlan;
	private StrategicPlanDto planDto;
	private Users usersSession;
	private UploadingStrategicPlanImpl uploadingStrImpl = new UploadingStrategicPlanImpl();
	private List<UploadingStrategicPlan> planList = new ArrayList<UploadingStrategicPlan>();

	@SuppressWarnings("unchecked")
	@PostConstruct
	public void init() {
		HttpSession session = SessionUtils.getSession();
		usersSession = (Users) session.getAttribute("userSession");
		if (users == null) {
			users = new Users();
		}

		if (documents == null) {
			documents = new Documents();
		}
		if (uploadingFiles == null) {
			uploadingFiles = new UploadingFiles();
		}
		if (menuGroup == null) {
			menuGroup = new MenuGroup();
		}
		if (uploadingPlan == null) {
			uploadingPlan = new UploadingStrategicPlan();
		}
		if (planDto == null) {
			planDto = new StrategicPlanDto();
		}
		try {
			// menuGroupDetails=menuGroupImpl.getGenericListWithHQLParameter(new String[]
			// {"genericStatus"},new Object[] {ACTIVE}, "MenuGroup", "menuGroupId asc");
			stratPlanFileList();
		} catch (Exception e) {
			setValid(false);
			JSFMessagers.addErrorMessage(getProvider().getValue("com.server.side.internal.error"));
			LOGGER.info(e.getMessage());
			e.printStackTrace();
		}

	}

	public void fileUpload(FileUploadEvent event) {

		UploadUtility ut = new UploadUtility();
		String validationCode = "PROFILEIMAGE";
		try {
			documents = ut.fileUploadUtil(event, validationCode);

			// need to put exact users
			Users u = new Users();
			u.setUserId(1);
			uploadingFiles.setUser(u);
			uploadingFiles.setCrtdDtTime(timestamp);
			uploadingFiles.setGenericStatus(ACTIVE);
			uploadingFiles.setDocuments(documents);
			uploadingFiles.setCreatedBy(usersSession.getViewId());
			uploadingFilesImpl.saveIntable(uploadingFiles);

			LOGGER.info(CLASSNAME + event.getFile().getFileName() + "uploaded successfully ... ");
			JSFMessagers.resetMessages();
			setValid(true);
			JSFMessagers.addErrorMessage(getProvider().getValue("upload.message.success"));
		} catch (Exception e) {
			LOGGER.info(CLASSNAME + "testing save methode ");
			JSFMessagers.resetMessages();
			setValid(false);
			JSFMessagers.addErrorMessage(getProvider().getValue("com.server.side.internal.error"));
			e.printStackTrace();
		}

	}
	public String uploadProfile(FileUploadEvent event) {

		try {
			if (null != usersSession) {
				UploadUtility ut = new UploadUtility();
				String validationCode = "PROFILEIMAGE";
				documents = ut.fileUploadUtilUsers(event, validationCode);
				
					uploadingFiles.setUser(usersSession);
					uploadingFiles.setCrtdDtTime(timestamp);
					uploadingFiles.setGenericStatus(ACTIVE);
					uploadingFiles.setDocuments(documents);
					uploadingFilesImpl.saveIntable(uploadingFiles);
					LOGGER.info(CLASSNAME + event.getFile().getFileName() + "uploaded successfully ... ");
					JSFMessagers.resetMessages();
					setValid(true);
					JSFMessagers.addErrorMessage(getProvider().getValue("upload.message.success"));
					return "/menu/EditProfile.xhtml?faces-redirect=true";
				}else {
					JSFMessagers.resetMessages();
					setValid(false);
					JSFMessagers.addErrorMessage(getProvider().getValue("com.server.side.internal.errorsession"));
				}		
		} catch (Exception e) {
			LOGGER.info(CLASSNAME + "testing profile upload methode ");
			JSFMessagers.resetMessages();
			setValid(false);
			JSFMessagers.addErrorMessage(getProvider().getValue("com.server.side.userprofile.error"));
			e.printStackTrace();
		}
		return "/menu/EditProfile.xhtml?faces-redirect=true";
	}

	// UPLOADING STRATEGIC PLAN DOCUMENTS
	public void strategicPlanFileUpload(FileUploadEvent event) {
		HttpSession session = SessionUtils.getSession();
		// Get the values from the session
		boolean valid = checkStrategicUploadedFile();
		try {
			if (valid) {
				planDto = (StrategicPlanDto) session.getAttribute("StratPlanInfo");
				int planId = planDto.getStrategicPlanId();

				LOGGER.info("STRATEGIC PLAN ID:::::::::::::" + planId);

				if ((0 != planId) && (planDto.getGenericStatus().equals(ACTIVE))) {
					UploadUtility ut = new UploadUtility();
					String validationCode = "PROFILEIMAGE";

					documents = ut.fileUploadUtil(event, validationCode);

					// need to put exact users
					/* Users u=new Users(); */
					StrategicPlan pl = new StrategicPlan();
					pl.setPlanId(planId);
					uploadingPlan.setStrategicPlan(pl);
					uploadingPlan.setCrtdDtTime(timestamp);
					uploadingPlan.setGenericStatus(ACTIVE);
					uploadingPlan.setCreatedBy(usersSession.getViewId());
					uploadingPlan.setDocuments(documents);
					uploadingStrImpl.saveIntable(uploadingPlan);
					LOGGER.info(CLASSNAME + event.getFile().getFileName() + "uploaded successfully ... ");
					JSFMessagers.resetMessages();
					setValid(true);
					JSFMessagers.addErrorMessage(getProvider().getValue("upload.message.success"));
					stratPlanFileList();
				}
			} else {
				JSFMessagers.resetMessages();
				setValid(false);
				JSFMessagers.addErrorMessage(getProvider().getValue("com.server.size.internal.errosize"));
			}
		} catch (Exception e) {
			LOGGER.info(CLASSNAME + "testing save methode ");
			JSFMessagers.resetMessages();
			setValid(false);
			JSFMessagers.addErrorMessage(getProvider().getValue("com.server.side.internal.error"));
			e.printStackTrace();
		}

	}

	public void downloadFile() {
		UploadUtility ut = new UploadUtility();
		try {
			ut.downloadFileUtil();
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}

	public List<UploadingFiles> fileList() {

		try {
			return uploadingFilesImpl.getListWithHQL("from UploadingFiles");

		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return null;
	}

	public boolean checkStrategicUploadedFile() {
		int count = 0;
		boolean valid = false;
		HttpSession session = SessionUtils.getSession();
		// Get the values from the session
		planDto = (StrategicPlanDto) session.getAttribute("StratPlanInfo");
		int planId = planDto.getStrategicPlanId();
		LOGGER.info("SELECTED Strategic Id  Is:::::::::::::::::::" + planId);
		List<UploadingStrategicPlan> list = stratPlanFileList();

		try {

			if (list.size() > 0) {
				for (UploadingStrategicPlan plan : list) {
					if (plan.getStrategicPlan().getPlanId() == planId) {

						count++;
					}

				}
				if (count < 3) {
					valid = true;
					LOGGER.info("COUNT FOUND:::::::::::::::" + count);
					LOGGER.info("List Size:::::::::::::::" + list.size());
					return (valid);
				}
			} else {
				valid = true;
				return (valid);

			}

			return (valid);

		} catch (Exception e) {
			JSFMessagers.resetMessages();
			setValid(false);
			JSFMessagers.addErrorMessage(getProvider().getValue("com.server.side.internal.errorfilter"));
			LOGGER.info(CLASSNAME + "" + e.getMessage());
			e.printStackTrace();
		}
		return (valid);
	}

	// listing strategicPlan documents
	@SuppressWarnings("unchecked")
	public List<UploadingStrategicPlan> stratPlanFileList() {

		try {
			return uploadingStrImpl.getGenericListWithHQLParameter(new String[] { "genericStatus" },
					new Object[] { ACTIVE }, "UploadingStrategicPlan", " upLoadPlanId asc");

		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return null;
	}

	public void sendMailTest() {
		/* sending content in a table example */
		String name = "Mukamana";
		String fname = "Eric";

		String msg = "<p>Kindly refer to the  below status.</p>" + "<table width=\"50%\" border=\"5px\">\n"
				+ "  <tbody>\n" + "	<tr>\n" + "      <td class=\"labelbold\">Fname</td>\n" + "      <td>\n" + "		  "
				+ name + "\n" + "	  </td>\n" + "    </tr>\n" + "	<tr>\n"
				+ "      <td class=\"labelbold\">Lname</td>\n" + "      <td>\n" + "		  " + fname + "\n"
				+ "	  </td>\n"

				+ "  </tbody>\n" + "</table>\n";
		/* End send content in table sample */
		gen.sendEmailNotification("sibo2540@gmail.com", "Sibo Emma", "Test Email", msg);
		LOGGER.info("::: notidficatio sent   ");
	}

	public void sendUserMailTest(String useremail, String userfname, String userlname) {
		/* sending content in a table example */
		String name = "Mukamana";
		String fname = "Eric";

		String msg = "<p>Kindly refer to the  below status.</p>" + "<table width=\"50%\" border=\"5px\">\n"
				+ "  <tbody>\n" + "	<tr>\n" + "      <td class=\"labelbold\">Fname</td>\n" + "      <td>\n" + "		  "
				+ name + "\n" + "	  </td>\n" + "    </tr>\n" + "	<tr>\n"
				+ "      <td class=\"labelbold\">Lname</td>\n" + "      <td>\n" + "		  " + fname + "\n"
				+ "	  </td>\n"

				+ "  </tbody>\n" + "</table>\n";
		/* End send content in table sample */
		gen.sendEmailNotification(useremail, userfname + " " + userlname, "Test Email", msg);
		LOGGER.info("::: notidficatio sent   ");
	}

	public void saveData() {
		LOGGER.info(CLASSNAME + "testing save methode ");
		JSFMessagers.resetMessages();
		setValid(false);
		JSFMessagers.addErrorMessage(getProvider().getValue("com.server.side.internal.error"));

	}

	public void changeSelectBox(String name) {

		LOGGER.info("Ajax is working with listener::::::" + name);
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

	public MenuAssignment getMenuAssignment() {
		return menuAssignment;
	}

	public void setMenuAssignment(MenuAssignment menuAssignment) {
		this.menuAssignment = menuAssignment;
	}

	public MenuGroup getMenuGroup() {
		return menuGroup;
	}

	public void setMenuGroup(MenuGroup menuGroup) {
		this.menuGroup = menuGroup;
	}

	public List<MenuGroup> getMenuGroupDetails() {
		return menuGroupDetails;
	}

	public void setMenuGroupDetails(List<MenuGroup> menuGroupDetails) {
		this.menuGroupDetails = menuGroupDetails;
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

	public MenuAssignmentImpl getMenuAssignmentImpl() {
		return menuAssignmentImpl;
	}

	public void setMenuAssignmentImpl(MenuAssignmentImpl menuAssignmentImpl) {
		this.menuAssignmentImpl = menuAssignmentImpl;
	}

	public MenuGroupImpl getMenuGroupImpl() {
		return menuGroupImpl;
	}

	public void setMenuGroupImpl(MenuGroupImpl menuGroupImpl) {
		this.menuGroupImpl = menuGroupImpl;
	}

	public Timestamp getTimestamp() {
		return timestamp;
	}

	public void setTimestamp(Timestamp timestamp) {
		this.timestamp = timestamp;
	}

	public DocumentsImpl getDocumentsImpl() {
		return documentsImpl;
	}

	public void setDocumentsImpl(DocumentsImpl documentsImpl) {
		this.documentsImpl = documentsImpl;
	}

	public UploadingFilesImpl getUploadingFilesImpl() {
		return uploadingFilesImpl;
	}

	public void setUploadingFilesImpl(UploadingFilesImpl uploadingFilesImpl) {
		this.uploadingFilesImpl = uploadingFilesImpl;
	}

	public Documents getDocuments() {
		return documents;
	}

	public void setDocuments(Documents documents) {
		this.documents = documents;
	}

	public UploadingFiles getUploadingFiles() {
		return uploadingFiles;
	}

	public void setUploadingFiles(UploadingFiles uploadingFiles) {
		this.uploadingFiles = uploadingFiles;
	}

	public UploadingStrategicPlan getUploadingPlan() {
		return uploadingPlan;
	}

	public void setUploadingPlan(UploadingStrategicPlan uploadingPlan) {
		this.uploadingPlan = uploadingPlan;
	}

	public StrategicPlanDto getPlanDto() {
		return planDto;
	}

	public void setPlanDto(StrategicPlanDto planDto) {
		this.planDto = planDto;
	}

	public UploadingStrategicPlanImpl getUploadingStrImpl() {
		return uploadingStrImpl;
	}

	public void setUploadingStrImpl(UploadingStrategicPlanImpl uploadingStrImpl) {
		this.uploadingStrImpl = uploadingStrImpl;
	}

	public List<UploadingStrategicPlan> getPlanList() {
		return planList;
	}

	public void setPlanList(List<UploadingStrategicPlan> planList) {
		this.planList = planList;
	}

	public Users getUsersSession() {
		return usersSession;
	}

	public void setUsersSession(Users usersSession) {
		this.usersSession = usersSession;
	}

}
