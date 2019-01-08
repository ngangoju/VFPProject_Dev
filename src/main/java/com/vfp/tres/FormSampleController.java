
package com.vfp.tres;

import java.io.File;
import java.io.Serializable;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.sql.Timestamp;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.List;
import java.util.UUID;
import java.util.logging.Logger;

import javax.annotation.PostConstruct;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.ViewScoped;
import javax.faces.context.FacesContext;
import javax.mail.MessagingException;
import javax.mail.internet.AddressException;
import javax.servlet.ServletContext;
import javax.servlet.http.HttpSession;

import org.apache.commons.io.FilenameUtils;
import org.primefaces.event.FileUploadEvent;
import tres.common.DbConstant;
import tres.common.GenerateNotificationTemplete;
import tres.common.JSFBoundleProvider;
import tres.common.JSFMessagers;
import tres.common.SessionUtils;
import tres.common.UploadUtility;
import tres.dao.impl.ActivityImpl;
import tres.dao.impl.DocumentsImpl;
import tres.dao.impl.MenuAssignmentImpl;
import tres.dao.impl.MenuGroupImpl;
import tres.dao.impl.StrategicPlanImpl;
import tres.dao.impl.UploadingActivityImpl;
import tres.dao.impl.UploadingFilesImpl;
import tres.dao.impl.UploadingStrategicPlanImpl;
import tres.dao.impl.UserImpl;
import tres.domain.Activity;
import tres.domain.Documents;
import tres.domain.MenuAssignment;
import tres.domain.MenuGroup;
import tres.domain.StrategicPlan;
import tres.domain.UploadingActivity;
import tres.domain.UploadingFiles;
import tres.domain.UploadingStrategicPlan;
import tres.domain.Users;
import tres.vfp.dto.ActivityDto;
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
	StrategicPlanImpl planImpl = new StrategicPlanImpl();
	/* end class injection */
	Timestamp timestamp = new Timestamp(Calendar.getInstance().getTime().getTime());
	private Documents documents;
	private UploadingFiles uploadingFiles;
	private UploadingStrategicPlan uploadingPlan;
	private StrategicPlanDto planDto;
	private StrategicPlan plan;
	private Users usersSession;
	private UploadingStrategicPlanImpl uploadingStrImpl = new UploadingStrategicPlanImpl();
	private List<UploadingStrategicPlan> planList = new ArrayList<UploadingStrategicPlan>();
	private ActivityDto actDto= new ActivityDto();
	private UploadingActivity uploadingAct;
	private UploadingActivityImpl uploadActImpl= new UploadingActivityImpl();
	private Activity activity;
	private ActivityImpl actImpl= new ActivityImpl();
	private List<UploadingFiles>filesUploaded= new ArrayList<UploadingFiles>();
	private DocumentsImpl docsImpl = new DocumentsImpl();
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
		if(actDto==null) {
			actDto=new ActivityDto();
		}
		if(uploadingAct==null) {
			uploadingAct= new UploadingActivity();
		}
		if(activity==null) {
			activity= new Activity();
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
	
	@SuppressWarnings("unchecked")
	public void deleteExistImage() throws Exception {
		ServletContext ctx = (ServletContext) FacesContext.getCurrentInstance().getExternalContext().getContext();
		String realPath = ctx.getRealPath("/");
		LOGGER.info("Filse Reals Path::::" + realPath);
		if(null != usersSession) {
			filesUploaded=uploadingFilesImpl.getGenericListWithHQLParameter(new String[] { "genericStatus", "user"},
					new Object[] { ACTIVE, usersSession},
					"UploadingFiles", "uploadId asc");
			
			for(UploadingFiles files:filesUploaded) {
				Documents doc = new Documents();
				doc = docsImpl.getModelWithMyHQL(new String[] { "DocId" },
						new Object[] { files.getDocuments().getDocId()}, " from Documents");
				final Path destination = Paths.get(realPath + FILELOCATION + doc.getSysFilename());
				LOGGER.info("Path::" + destination);
				File file = new File(destination.toString());
				uploadingFilesImpl.deleteIntable(files);
				docsImpl.deleteIntable(documents);
				LOGGER.info("Delete in db operation done!!!:");
				if (file.delete()) {
					System.out.println(file.getName() + " is deleted!");		
				} else {
					System.out.println("Delete operation is failed.");
					
				}
			}
			
		}
	}
	public String uploadProfile(FileUploadEvent event) {

		try {
			if (null != usersSession) {
				UploadUtility ut = new UploadUtility();
				String validationCode = "PROFILEIMAGE";
				deleteExistImage();
				documents = ut.fileUploadUtilUsers(event, validationCode);
				uploadingFiles.setUser(usersSession);
				uploadingFiles.setCrtdDtTime(timestamp);
				uploadingFiles.setGenericStatus(ACTIVE);
				uploadingFiles.setDocuments(documents);
				uploadingFilesImpl.saveIntable(uploadingFiles);
				LOGGER.info(CLASSNAME + event.getFile().getFileName() + "uploaded successfully ... ");
				JSFMessagers.resetMessages();
				setValid(true);
				JSFMessagers.addInfoMessage(getProvider().getValue("upload.message.success"));
				/* addErrorMessage(getProvider().getValue("upload.message.success")); */
				return "/menu/EditProfile.xhtml?faces-redirect=true";
			} else {
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
					JSFMessagers.addInfoMessage(getProvider().getValue("upload.message.success"));
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
	
	//Activity Files Upload 
	public String activityFilesUpload(FileUploadEvent event) {

		try {
			if (null != usersSession) {
				
				ActivityController actControl= new ActivityController();
				actDto=actControl.saveActivityFiles();
				int actId=actDto.getActivityId();
				LOGGER.info("ACTIVITY INFO :::::::::::::::"+actDto.getActivityId());
				activity=actImpl.getModelWithMyHQL(new String[] { "ACTIVITY_ID" },
						new Object[] { actId}, "from Activity");
				if(null!=activity) {
					UploadUtility ut = new UploadUtility();
					String validationCode = "ActivityFiles";
					documents = ut.fileUploadUtil(event, validationCode);
					
						uploadingAct.setActivity(activity);
						uploadingAct.setDocuments(documents);
						uploadingAct.setCreatedBy(usersSession.getViewId());
						uploadingAct.setGenericStatus(ACTIVE);
						uploadingAct.setCrtdDtTime(timestamp);
						uploadActImpl.saveUploadedActivity(uploadingAct);
						LOGGER.info(CLASSNAME + event.getFile().getFileName() + "uploaded successfully ... ");
						JSFMessagers.resetMessages();
						setValid(true);
						JSFMessagers.addInfoMessage(getProvider().getValue("com.server.side.activityfile.success"));
						/*addErrorMessage(getProvider().getValue("upload.message.success"));*/
				}
					return null;
				}else {
					JSFMessagers.resetMessages();
					setValid(false);
					JSFMessagers.addErrorMessage(getProvider().getValue("com.server.side.internal.errorsession"));
				}		
		} catch (Exception e) {
			LOGGER.info(CLASSNAME + "testing profile upload methode ");
			JSFMessagers.resetMessages();
			setValid(false);
			JSFMessagers.addErrorMessage(getProvider().getValue("com.server.side.activityfile.error"));
			e.printStackTrace();
		}
		return "";
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
			return uploadingStrImpl.getGenericListWithHQLParameter(new String[] { "genericStatus", "strategicPlan" },
					new Object[] { ACTIVE, planImpl.getModelWithMyHQL(new String[] {"genericStatus"}, new Object[] {ACTIVE}, "from StrategicPlan") }, "UploadingStrategicPlan", " upLoadPlanId asc");

		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return null;
	}
	
	@SuppressWarnings("unchecked")
	public List<UploadingActivity> activityFilesDetails(){

		try {

			ActivityController actControl= new ActivityController();
			actDto=actControl.saveActivityFiles();
			int actId=actDto.getActivityId();
			
			LOGGER.info("ACTIVITY INFO :::::::::::::::"+actDto.getActivityId());
			activity=actImpl.getModelWithMyHQL(new String[] { "ACTIVITY_ID" },
					new Object[] { actId}, "from Activity");
			return uploadActImpl.getGenericListWithHQLParameter(new String[] { "genericStatus","activity" },
					new Object[] { ACTIVE,activity }, "UploadingActivity","crtdDtTime desc");

		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return null;
	}


	public void sendMailTest() throws AddressException, MessagingException {
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

	public void sendUserMailTest(String useremail, String userfname, String userlname) throws AddressException, MessagingException {
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

	public ActivityDto getActDto() {
		return actDto;
	}

	public void setActDto(ActivityDto actDto) {
		this.actDto = actDto;
	}

	public UploadingActivity getUploadingAct() {
		return uploadingAct;
	}

	public void setUploadingAct(UploadingActivity uploadingAct) {
		this.uploadingAct = uploadingAct;
	}

	public UploadingActivityImpl getUploadActImpl() {
		return uploadActImpl;
	}

	public void setUploadActImpl(UploadingActivityImpl uploadActImpl) {
		this.uploadActImpl = uploadActImpl;
	}

	public Activity getActivity() {
		return activity;
	}

	public void setActivity(Activity activity) {
		this.activity = activity;
	}

	public ActivityImpl getActImpl() {
		return actImpl;
	}

	public void setActImpl(ActivityImpl actImpl) {
		this.actImpl = actImpl;
	}

	public StrategicPlanImpl getPlanImpl() {
		return planImpl;
	}

	public void setPlanImpl(StrategicPlanImpl planImpl) {
		this.planImpl = planImpl;
	}

	public StrategicPlan getPlan() {
		return plan;
	}

	public void setPlan(StrategicPlan plan) {
		this.plan = plan;
	}
	public List<UploadingFiles> getFilesUploaded() {
		return filesUploaded;
	}
	
	public void setFilesUploaded(List<UploadingFiles> filesUploaded) {
		this.filesUploaded = filesUploaded;
	}

	public DocumentsImpl getDocsImpl() {
		return docsImpl;
	}

	public void setDocsImpl(DocumentsImpl docsImpl) {
		this.docsImpl = docsImpl;
	}

}
