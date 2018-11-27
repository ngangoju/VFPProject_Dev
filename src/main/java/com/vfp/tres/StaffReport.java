package com.vfp.tres;

import java.io.Serializable;
import java.sql.Timestamp;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.List;
import java.util.logging.Logger;
import javax.annotation.PostConstruct;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.ViewScoped;
import javax.faces.context.ExternalContext;
import javax.faces.context.FacesContext;
import javax.servlet.http.HttpSession;
import org.primefaces.model.chart.Axis;
import org.primefaces.model.chart.AxisType;
import org.primefaces.model.chart.BarChartModel;
import org.primefaces.model.chart.ChartSeries;
import org.primefaces.model.chart.LineChartModel;
import org.primefaces.model.chart.PieChartModel;
import tres.common.DbConstant;
import tres.common.JSFBoundleProvider;
import tres.common.JSFMessagers;
import tres.common.SessionUtils;
import tres.dao.impl.ActivityImpl;
import tres.dao.impl.ClearanceImpl;
import tres.dao.impl.InstitutionReportViewImpl;
import tres.dao.impl.TaskImpl;
import tres.dao.impl.UserImpl;
import tres.domain.Activity;
import tres.domain.Clearance;
import tres.domain.Institution;
import tres.domain.InstitutionReportView;
import tres.domain.StrategicPlan;
import tres.domain.Task;
import tres.domain.UserCategory;
import tres.domain.Users;
import tres.vfp.dto.ClearanceDto;
import tres.vfp.dto.TaskDto;
import tres.vfp.dto.UserDto;

import com.itextpdf.text.BaseColor;
import com.itextpdf.text.Document;
import com.itextpdf.text.DocumentException;
import com.itextpdf.text.Element;
import com.itextpdf.text.Font;
import com.itextpdf.text.Paragraph;
import com.itextpdf.text.Phrase;
import com.itextpdf.text.Rectangle;
import com.itextpdf.text.pdf.ColumnText;
import com.itextpdf.text.pdf.PdfContentByte;
import com.itextpdf.text.pdf.PdfPCell;
import com.itextpdf.text.pdf.PdfPTable;
import com.itextpdf.text.pdf.PdfPageEventHelper;
import com.itextpdf.text.pdf.PdfWriter;
import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.OutputStream;

@ManagedBean
@ViewScoped
public class StaffReport implements Serializable, DbConstant {
	private static final Logger LOGGER = Logger.getLogger(Thread.currentThread().getStackTrace()[0].getClassName());
	private String CLASSNAME = "TaskController :: ";
	private static final long serialVersionUID = 1L;
	/*to manage validation messages*/
	private boolean isValid;
   private boolean editable;
	private int taskID;
	/*end  manage validation messages*/
	private Users users;
	private Users usersSession;
	private Task task;
	private StrategicPlan strategicplan;
	private Institution institution;
	private Date first;
	private LineChartModel animatedModel1;
    private BarChartModel animatedModel2;
    private PieChartModel pieModel1;
    private PieChartModel pieModel2;
	private Date second;
	private String login;
	private String myChoice;
	private Activity activity;
	private boolean renderedspdf;
	private boolean renderedchart;
	private List<Task> taskDetails = new ArrayList<Task>();
	private List<Activity>activitydetails=new ArrayList<Activity>();
	private List<TaskDto> taskDtoDetails = new ArrayList<TaskDto>();
	private List<ClearanceDto>ClearanceDtoDetails=new ArrayList<ClearanceDto>();
	private ClearanceDto clearanceDto;
	ActivityImpl activityImpl = new ActivityImpl();
	SimpleDateFormat sdf = new SimpleDateFormat("dd/MM/yyyy");
	Date date = new Date();
    SimpleDateFormat dt = new SimpleDateFormat("dd/MM/yyyy hh:mm:ss");
    String xdate = dt.format(date);
	
	/*class injection*/
	
	JSFBoundleProvider provider = new JSFBoundleProvider();
	UserImpl usersImpl = new UserImpl();
	TaskImpl taskImpl = new TaskImpl();
	InstitutionReportView institutionReportView=new InstitutionReportView();
	InstitutionReportViewImpl institutionReportViewImpl=new InstitutionReportViewImpl();
	private List<InstitutionReportView>institutionreportdetails=new ArrayList<InstitutionReportView>();
	
	Clearance clearance=new Clearance();
	ClearanceImpl clearanceImpl=new ClearanceImpl();
	private List<Clearance>Clearancedetails=new ArrayList<Clearance>();
	Task tc= new Task();
	
	/*end class injection*/
	Timestamp timestamp = new Timestamp(Calendar.getInstance().getTime().getTime());
	
	
	@PostConstruct
	public void init() {
		createAnimatedModels();
        createPieModels();
		HttpSession session = SessionUtils.getSession();
		usersSession = (Users) session.getAttribute("userSession");

		if (users == null) {
			users = new Users();
		}

		if (task == null) {
			task = new Task();
		}


		try {
			
			/*boardDetails= boardImpl.getGenericListWithHQLParameter(new String[] { "genericStatus" },new Object[] { ACTIVE }, "Board", "boardId asc");*/
		
			ClearanceDtoDetails=myClearance();
		} catch (Exception e) {
		}
	}
	
	
	//Codes for setting the footer and header.-->
	
    class MyFooter extends PdfPageEventHelper {

    Font ffont1 = new Font(Font.FontFamily.UNDEFINED, 12, Font.ITALIC);
   
    Font ffont2 = new Font(Font.FontFamily.UNDEFINED, 16, Font.ITALIC);
    public void onEndPage(PdfWriter writer, Document document) {
        PdfContentByte cb = writer.getDirectContent();
        /*Date date = new Date();
        SimpleDateFormat dt = new SimpleDateFormat("dd/MM/yyyy hh-mm-ss");
        String xdate = dt.format(date);*/
       Phrase header = new Phrase("Printed On: " + xdate, ffont1);
       Phrase footer = new Phrase("@Copyright ITEME...!\n", ffont2);
        ColumnText.showTextAligned(cb, Element.ALIGN_CENTER,
                header,
                (document.right() - document.left()) / 2 + document.leftMargin(),
                 document.top() + 10, 0);
        ColumnText.showTextAligned(cb, Element.ALIGN_CENTER,
                footer,
                (document.right() - document.left()) / 2 + document.leftMargin(),
                document.bottom() - 10, 0);
    }
}

//Codes for creating the table and its contents

    @SuppressWarnings("unchecked")
    @PostConstruct
	public void createPDFfoClearance() throws IOException, DocumentException {

		FacesContext context = FacesContext.getCurrentInstance();
		Document document = new Document();
		Rectangle rect = new Rectangle(20, 20, 600, 600);
		ByteArrayOutputStream baos = new ByteArrayOutputStream();
		PdfWriter writer = PdfWriter.getInstance(document, baos);
		MyFooter event = new MyFooter();
		writer.setPageEvent(event);
		writer.setBoxSize("art", rect);
		document.setPageSize(rect);
		if (!document.isOpen()) {
			document.open();
		}
		HttpSession session = SessionUtils.getSession();
		usersSession = (Users) session.getAttribute("userSession");
		

		if (users == null) {
			users = new Users();
		}

		if (activity == null) {
			activity = new Activity();
		}
		try {
			
			//Users t= usersImpl.gettUserById(usersSession.getUserId(), "userId");
			activitydetails = activityImpl.getGenericListWithHQLParameter(new String[] { "genericStatus"},
					new Object[] {ACTIVE}, "Activity", "activityId asc");
					
			if (activitydetails.size() > 0) {
				setValid(true);
				document.add(new Paragraph("\n"));
				Font font0 = new Font(Font.FontFamily.TIMES_ROMAN, 11, Font.BOLD);
				Font font1 = new Font(Font.FontFamily.TIMES_ROMAN, 14, Font.BOLD);
				PdfPTable table = new PdfPTable(5);

				Users t = usersImpl.gettUserById(usersSession.getUserId(), "userId");
				String myNane = t.getFname()+""+t.getLname();
				PdfPCell pc = new PdfPCell(new Paragraph("CLEARANCE REPORT",font1));
				
				pc.setColspan(5);
				pc.setBackgroundColor(BaseColor.CYAN);
				pc.setHorizontalAlignment(Element.ALIGN_CENTER);
				table.addCell(pc);

				table.setWidthPercentage(110);
				PdfPCell pc1 = new PdfPCell(new Paragraph(" Trategic plan", font0));

				pc1.setBackgroundColor(BaseColor.ORANGE);
				table.addCell(pc1);

				PdfPCell pc2 = new PdfPCell(new Paragraph(" Task", font0));
				pc2.setBackgroundColor(BaseColor.ORANGE);
				table.addCell(pc2);

				PdfPCell pc3 = new PdfPCell(new Paragraph(" All activities", font0));
				pc3.setBackgroundColor(BaseColor.ORANGE);
				table.addCell(pc3);

				PdfPCell pc4 = new PdfPCell(new Paragraph(" Closed Activities", font0));
				pc4.setBackgroundColor(BaseColor.ORANGE);
				table.addCell(pc4);

				PdfPCell pc5 = new PdfPCell(new Paragraph(" Rate in %", font0));
				pc5.setBackgroundColor(BaseColor.ORANGE);
				table.addCell(pc5);
				table.setHeaderRows(2);
				
				for (Activity activity : activitydetails) {
					table.addCell(activity.getCrtdDtTime() + "");
					table.addCell(activity.getDescription());
					table.addCell(activity.getWeight());
					table.addCell(activity.getStatus());
					table.addCell("" + activity.getCreatedBy());
				}
				document.add(table);

				document.close();

				writePDFToResponse(context.getExternalContext(), baos, "Staff_report"+xdate);

				context.responseComplete();

			} else {
				setValid(false);
				JSFMessagers.addErrorMessage(getProvider().getValue("com.server.side.internal.errorStaff"));
			}
		} catch (Exception e) {
			LOGGER.info(e.getMessage());
			e.printStackTrace();
		}
	}
    
    @SuppressWarnings("unchecked")
    @PostConstruct
	public void createPdf() throws IOException, DocumentException {

		FacesContext context = FacesContext.getCurrentInstance();
		Document document = new Document();
		Rectangle rect = new Rectangle(20, 20, 600, 600);
		ByteArrayOutputStream baos = new ByteArrayOutputStream();
		PdfWriter writer = PdfWriter.getInstance(document, baos);
		MyFooter event = new MyFooter();
		writer.setPageEvent(event);
		writer.setBoxSize("art", rect);
		document.setPageSize(rect);
		if (!document.isOpen()) {
			document.open();
		}
		HttpSession session = SessionUtils.getSession();
		usersSession = (Users) session.getAttribute("userSession");
		

		if (users == null) {
			users = new Users();
		}

		if (activity == null) {
			activity = new Activity();
		}
		try {
			
			//Users t= usersImpl.gettUserById(usersSession.getUserId(), "userId");
			activitydetails = activityImpl.getGenericListWithHQLParameter(new String[] { "genericStatus", "user"},
					new Object[] {ACTIVE, usersImpl.gettUserById(usersSession.getUserId(), "userId")}, "Activity", "activityId asc");
					
			if (activitydetails.size() > 0) {
				setValid(true);
				document.add(new Paragraph("\n"));
				Font font0 = new Font(Font.FontFamily.TIMES_ROMAN, 11, Font.BOLD);
				PdfPTable table = new PdfPTable(5);

				Users t = usersImpl.gettUserById(usersSession.getUserId(), "userId");
				String myNane = t.getFname()+""+t.getLname();
				PdfPCell pc = new PdfPCell(new Paragraph("Report for all activities for:\n" + myNane));
				pc.setColspan(5);
				pc.setBackgroundColor(BaseColor.CYAN);
				pc.setHorizontalAlignment(Element.ALIGN_CENTER);
				table.addCell(pc);

				table.setWidthPercentage(110);
				PdfPCell pc1 = new PdfPCell(new Paragraph(" Execution period", font0));

				pc1.setBackgroundColor(BaseColor.ORANGE);
				table.addCell(pc1);

				PdfPCell pc2 = new PdfPCell(new Paragraph(" Activity", font0));
				pc2.setBackgroundColor(BaseColor.ORANGE);
				table.addCell(pc2);

				PdfPCell pc3 = new PdfPCell(new Paragraph(" week", font0));
				pc3.setBackgroundColor(BaseColor.ORANGE);
				table.addCell(pc3);

				PdfPCell pc4 = new PdfPCell(new Paragraph(" Status", font0));
				pc4.setBackgroundColor(BaseColor.ORANGE);
				table.addCell(pc4);

				PdfPCell pc5 = new PdfPCell(new Paragraph(" Staff", font0));
				pc5.setBackgroundColor(BaseColor.ORANGE);
				table.addCell(pc5);
				table.setHeaderRows(2);
				
				for (Activity activity : activitydetails) {
					table.addCell(activity.getFormatedDate1());
					table.addCell(activity.getDescription());
					table.addCell("     "+activity.getFormatedDate1()+"\n "+"to"+" "+activity.getFormatedDate2());
					table.addCell(activity.getStatus());
					table.addCell("" + activity.getCreatedBy());
				}
				document.add(table);

				document.close();

				writePDFToResponse(context.getExternalContext(), baos, "Staff_report"+xdate);

				context.responseComplete();

			} else {
				setValid(false);
				JSFMessagers.addErrorMessage(getProvider().getValue("com.server.side.internal.errorStaff"));
			}
		} catch (Exception e) {
			LOGGER.info(e.getMessage());
			e.printStackTrace();
		}
	}
public void writePDFToResponse(ExternalContext externalContext, ByteArrayOutputStream baos, String fileName) {
    try {
        externalContext.responseReset();
        externalContext.setResponseContentType("application/pdf");
        externalContext.setResponseHeader("Expires", "0");
        externalContext.setResponseHeader("Cache-Control", "must-revalidate, post-check=0, pre-check=0");
        externalContext.setResponseHeader("Pragma", "public");
        externalContext.setResponseHeader("Content-disposition", "attachment;filename=" + fileName + ".pdf");
        externalContext.setResponseContentLength(baos.size());
        OutputStream out = externalContext.getResponseOutputStream();
        baos.writeTo(out);
        externalContext.responseFlushBuffer();
    } catch (IOException e) {
        e.printStackTrace();
    }
}

private void createPieModels() {
    // createPieModel1();
     createPieModel2();
 }
 //see reference on this example how to get data from the database     
//end  example 
 private void createAnimatedModels() {
     animatedModel2 = initBarModel();
     animatedModel2.setTitle("Bar Chart");
     animatedModel2.setAnimate(true);
     animatedModel2.setLegendPosition("ne");
     Axis yAxis = animatedModel2.getAxis(AxisType.Y);
     yAxis.setMin(0);
     yAxis.setMax(10);
 } 
 
 public void testMethod() {
 	
 	 for (Object[] data:  taskImpl.reportList("select t.taskName,u.fname,b.boardName from Task t,Board b,Users u,Activity a where t.taskId=a.task and u.userId=a.user and a.user=u.userId and b.boardId=u.board")){
 	       
      	//select t.taskName,u.fname,b.boardName from Task t,Board b,Users u,Activity a where t.taskId=a.task and u.userId=a.user and a.user=u.userId and b.boardId=u.board;
      	LOGGER.info("tes1 1::"+data[0]+" ::"+ data[1]+" ::"+ data[2]);
            }
 	 for (Object[] data:  taskImpl.reportList("select count(*),t.taskName,t.endDate,u.fname,b.boardName from Task t,Board b,Users u,Activity a where t.taskId=a.task and u.userId=a.user and a.user=u.userId and b.boardId=u.board")){
	       
       	//select t.taskName,u.fname,b.boardName from Task t,Board b,Users u,Activity a where t.taskId=a.task and u.userId=a.user and a.user=u.userId and b.boardId=u.board;
       	LOGGER.info("tes1 1::"+Integer.parseInt(data[0]+"")+" ::"+ data[1]+" ::"+ data[2]);
             }
 }
 
 public void testMethod1() {
	 LOGGER.info("tes1 1 testMethod1 :::");
 	 for(Object[]data:institutionReportViewImpl.reportList("select mytask,myName from InstitutionReportView")) {
 		 LOGGER.info("tes1 1::"+data[0]+" ::"+ data[1]+"james and Rashid");
 		 
 	 }
 	 LOGGER.info("tes1 done testMethod1 :::");
 }
 private BarChartModel initBarModel() {
	//select t.taskName,u.fname,b.boardName from Task t,Board b,Users u,Activity a where t.taskId=a.task and u.userId=a.user and a.user=u.userId and b.boardId=u.board;
     
	 BarChartModel model = new BarChartModel(); 
     ChartSeries tasks = new ChartSeries();
     for (Object[] data:  usersImpl.reportList("select count(*),tas.fname from Activity a  join  a.user tas   group by tas.userId")){
    	 tasks.set(data[1]+"",Integer.parseInt(data[0]+"" ));
    	 }   
     model.addSeries(tasks);
     return model;
 } 
 private void createPieModel2() {
 	pieModel2 = new PieChartModel();
     UserImpl usImpl=new UserImpl();
    
 for (Object[] data:  usImpl.reportList("select count(*),tas.fname from Activity a  join  a.user tas   group by tas.userId")){
    
	  pieModel2.set(data[1]+"", Integer.parseInt(data[0]+""));	
     }
    pieModel2.setTitle("Pie chart ");
     pieModel2.setLegendPosition("e");
     pieModel2.setFill(false);
     pieModel2.setShowDataLabels(true);
     pieModel2.setDiameter(150);
     pieModel2.setShadow(false);
 } 

    
public void updateTable() throws Exception {
	
	if (myChoice.equalsIgnoreCase(pdfFormat)) {
		renderedspdf = true;
		renderedchart=false;
	} else {
		renderedspdf = false;
		renderedchart=true;
	}

}public void anotherClearance() {
	
}


public List<ClearanceDto> myClearance() {
	try {		
		ClearanceDtoDetails = new ArrayList<ClearanceDto>();
		for (Object[] data : institutionReportViewImpl.reportList(
				"SELECT p.details,t.taskName, sum(case  when  i.institutionName in ('BRD','kamana')  then 1 else 0 end),"
				+ "sum(case when (i.institutionName in ('BRD','kamana') and a.status='done' ) then 1 else 0 end),(sum(case when (i.institutionName in ('BRD','kamana') and a.status='done' ) then 1 else 0 end) /sum(case  when i.institutionName in ('BRD','kamana')\r\n" + 
				"then 1 else 0 end))*100 as rate from Institution i,StrategicPlan p,Task t,Board b,Users u,Activity a where t.taskId=a.task and u.userId=a.user \r\n" + 
				"and a.user=u.userId and b.boardId=u.board and t.strategicplan=p.planId and b.institution=i.institutionId\r\n" + 
				"group by p.details,t.taskName"
					)) {
			
			LOGGER.info("users::::::::::::::::::::::::::::::::::::::::::::::::>>" + data[0] + ":: "+ data[1] + "");
			
			ClearanceDto userDtos = new ClearanceDto();
			
			userDtos.setStrategicplan(data[0] + "");
			userDtos.setTaskName(data[1] + "");
			userDtos.setNumberOfActivities(Integer.parseInt(data[2] + ""));
			userDtos.setNumberOfFinishedActivities(Integer.parseInt(data[3] + ""));
			userDtos.setRate(Double.parseDouble(data[4]+""));
			
			ClearanceDtoDetails.add(userDtos);
		}
			
		return(ClearanceDtoDetails);
		
		
	} catch (Exception e) {
		// TODO: handle exception
	}
	
	
	return null;
}
@SuppressWarnings("unchecked")
public List<Clearance>Clearancedetailss() throws Exception{
	try {

		Clearancedetails = clearanceImpl.getGenericListWithHQLParameter(new String[] { "genericStatus"},
		new Object[] {ACTIVE}, "Clearance", "activities");
		if (Clearancedetails.size() > 0) {
			
			for(Clearance activit : Clearancedetails) {
				
				Clearancedetails.add(activit);
			}

		}else {
		
			JSFMessagers.addErrorMessage("Your institution does not have any Strategic plan");
		}
		
	} catch (Exception e) {
		e.getMessage();
       
	}
	return Clearancedetails;
		
}

@SuppressWarnings("unchecked")
public List<Activity>activities() throws Exception{
	try {

		activitydetails = activityImpl.getGenericListWithHQLParameter(new String[] { "genericStatus", "user"},
		new Object[] {ACTIVE, usersImpl.gettUserById(usersSession.getUserId(), "userId")}, "Activity", "activityId asc");
		
		if (activitydetails.size() > 0) {
			for(Activity activit : activitydetails) {
				
				activitydetails.add(activit);
			}

		}else {
			JSFMessagers.addErrorMessage(getProvider().getValue("com.server.side.internal.errorStaff"));
		}
		
	} catch (Exception e) {
		e.getMessage();
       
	}
	return activitydetails;	
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

	public int getTaskID() {
		return taskID;
	}

	public void setTaskID(int taskID) {
		this.taskID = taskID;
	}

	public Users getUsers() {
		return users;
	}

	public void setUsers(Users users) {
		this.users = users;
	}

	public Users getUsersSession() {
		return usersSession;
	}

	public void setUsersSession(Users usersSession) {
		this.usersSession = usersSession;
	}

	public Task getTask() {
		return task;
	}

	public void setTask(Task task) {
		this.task = task;
	}

	public List<Task> getTaskDetails() {
		return taskDetails;
	}

	public void setTaskDetails(List<Task> taskDetails) {
		this.taskDetails = taskDetails;
	}

	public List<TaskDto> getTaskDtoDetails() {
		return taskDtoDetails;
	}

	public void setTaskDtoDetails(List<TaskDto> taskDtoDetails) {
		this.taskDtoDetails = taskDtoDetails;
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

	public TaskImpl getTaskImpl() {
		return taskImpl;
	}

	public void setTaskImpl(TaskImpl taskImpl) {
		this.taskImpl = taskImpl;
	}

	public Timestamp getTimestamp() {
		return timestamp;
	}

	public void setTimestamp(Timestamp timestamp) {
		this.timestamp = timestamp;
	}

	public static Logger getLogger() {
		return LOGGER;
	}

	public static long getSerialversionuid() {
		return serialVersionUID;
	}
	public Date getFirst() {
		return first;
	}
	public void setFirst(Date first) {
		this.first = first;
	}
	public String getLogin() {
		return login;
	}
	public void setLogin(String login) {
		this.login = login;
	}
	public SimpleDateFormat getSdf() {
		return sdf;
	}
	public void setSdf(SimpleDateFormat sdf) {
		this.sdf = sdf;
	}
	public Date getSecond() {
		return second;
	}
	public void setSecond(Date second) {
		this.second = second;
	}
	public String getMyChoice() {
		return myChoice;
	}
	public void setMyChoice(String myChoice) {
		this.myChoice = myChoice;
	}
	public boolean isRenderedspdf() {
		return renderedspdf;
	}
	public void setRenderedspdf(boolean renderedspdf) {
		this.renderedspdf = renderedspdf;
	}
	public List<Activity> getActivitydetails() {
		return activitydetails;
	}
	public void setActivitydetails(List<Activity> activitydetails) {
		this.activitydetails = activitydetails;
	}
	public LineChartModel getAnimatedModel1() {
		return animatedModel1;
	}
	public void setAnimatedModel1(LineChartModel animatedModel1) {
		this.animatedModel1 = animatedModel1;
	}
	public BarChartModel getAnimatedModel2() {
		return animatedModel2;
	}
	public void setAnimatedModel2(BarChartModel animatedModel2) {
		this.animatedModel2 = animatedModel2;
	}
	public PieChartModel getPieModel1() {
		return pieModel1;
	}
	public void setPieModel1(PieChartModel pieModel1) {
		this.pieModel1 = pieModel1;
	}
	public PieChartModel getPieModel2() {
		return pieModel2;
	}
	public void setPieModel2(PieChartModel pieModel2) {
		this.pieModel2 = pieModel2;
	}
	public Activity getActivity() {
		return activity;
	}
	public void setActivity(Activity activity) {
		this.activity = activity;
	}
	public boolean isRenderedchart() {
		return renderedchart;
	}
	public void setRenderedchart(boolean renderedchart) {
		this.renderedchart = renderedchart;
	}
	public ActivityImpl getActivityImpl() {
		return activityImpl;
	}
	public void setActivityImpl(ActivityImpl activityImpl) {
		this.activityImpl = activityImpl;
	}
	public Date getDate() {
		return date;
	}
	public void setDate(Date date) {
		this.date = date;
	}
	public SimpleDateFormat getDt() {
		return dt;
	}
	public void setDt(SimpleDateFormat dt) {
		this.dt = dt;
	}
	public String getXdate() {
		return xdate;
	}
	public void setXdate(String xdate) {
		this.xdate = xdate;
	}
	public Task getTc() {
		return tc;
	}
	public void setTc(Task tc) {
		this.tc = tc;
	}
	public StrategicPlan getStrategicplan() {
		return strategicplan;
	}
	public void setStrategicplan(StrategicPlan strategicplan) {
		this.strategicplan = strategicplan;
	}
	public Institution getInstitution() {
		return institution;
	}
	public void setInstitution(Institution institution) {
		this.institution = institution;
	}
	public boolean isEditable() {
		return editable;
	}
	public void setEditable(boolean editable) {
		this.editable = editable;
	}
	public List<ClearanceDto> getClearanceDtoDetails() {
		return ClearanceDtoDetails;
	}
	public void setClearanceDtoDetails(List<ClearanceDto> clearanceDtoDetails) {
		ClearanceDtoDetails = clearanceDtoDetails;
	}

	public ClearanceDto getClearanceDto() {
		return clearanceDto;
	}

	public void setClearanceDto(ClearanceDto clearanceDto) {
		this.clearanceDto = clearanceDto;
	}

	public InstitutionReportView getInstitutionReportView() {
		return institutionReportView;
	}

	public void setInstitutionReportView(InstitutionReportView institutionReportView) {
		this.institutionReportView = institutionReportView;
	}

	public InstitutionReportViewImpl getInstitutionReportViewImpl() {
		return institutionReportViewImpl;
	}

	public void setInstitutionReportViewImpl(InstitutionReportViewImpl institutionReportViewImpl) {
		this.institutionReportViewImpl = institutionReportViewImpl;
	}

	public List<InstitutionReportView> getInstitutionreportdetails() {
		return institutionreportdetails;
	}

	public void setInstitutionreportdetails(List<InstitutionReportView> institutionreportdetails) {
		this.institutionreportdetails = institutionreportdetails;
	}

	public Clearance getClearance() {
		return clearance;
	}

	public void setClearance(Clearance clearance) {
		this.clearance = clearance;
	}

	public ClearanceImpl getClearanceImpl() {
		return clearanceImpl;
	}

	public void setClearanceImpl(ClearanceImpl clearanceImpl) {
		this.clearanceImpl = clearanceImpl;
	}

	public List<Clearance> getClearancedetails() {
		return Clearancedetails;
	}

	public void setClearancedetails(List<Clearance> clearancedetails) {
		Clearancedetails = clearancedetails;
	}
	
}
	