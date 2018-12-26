package com.vfp.tres;

import java.io.Serializable;
import java.nio.file.Path;
import java.nio.file.Paths;
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
import javax.servlet.ServletContext;
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
import tres.domain.Users;
import tres.vfp.dto.ClearanceDto;
import tres.vfp.dto.TaskDto;

import com.itextpdf.text.BaseColor;
import com.itextpdf.text.Document;
import com.itextpdf.text.DocumentException;
import com.itextpdf.text.Element;
import com.itextpdf.text.Font;
import com.itextpdf.text.Image;
import com.itextpdf.text.PageSize;
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
	
	//fonts
	Font ffont0 = new Font(Font.FontFamily.UNDEFINED, 10, Font.BOLD,BaseColor.RED);
	Font ffont2 = new Font(Font.FontFamily.UNDEFINED, 16, Font.BOLD);
	Font ffont3 = new Font(Font.FontFamily.UNDEFINED, 12, Font.BOLDITALIC,BaseColor.BLUE);
	
	
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
			
			//ClearanceDtoDetails=myClearance();
		} catch (Exception e) {
		}
	}
	
	// creating pdf header Image and text aside

			public static PdfPCell createTextCell(String text) throws DocumentException, IOException {
				PdfPCell cell = new PdfPCell();
				Font font18 = new Font(Font.FontFamily.TIMES_ROMAN, 24, Font.ITALIC, BaseColor.ORANGE);
				Paragraph p = new Paragraph(text, font18);
				
				p.setAlignment(Element.ALIGN_CENTER);
				cell.addElement(p);
				cell.setFixedHeight(60);
				cell.setVerticalAlignment(Element.ALIGN_RIGHT);
				cell.setBorder(Rectangle.NO_BORDER);
				return cell;
			}

			public static PdfPCell createImageCell(String path) throws DocumentException, IOException {

				ServletContext ctx = (ServletContext) FacesContext.getCurrentInstance().getExternalContext().getContext();
				String realPath = ctx.getRealPath("/");
				LOGGER.info("Filse Reals Path::::" + realPath);
				final Path destination = Paths.get(realPath + FILELOCATION + "logo.jpeg");
				LOGGER.info("Path::" + destination);
				Image img = Image.getInstance("" + destination);
				img.scaleAbsolute(50f, 50f);
				PdfPCell cell = new PdfPCell(img, true);
				cell.setFixedHeight(60);
				cell.setBorder(Rectangle.NO_BORDER);
				return cell;
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
       //Phrase header = new Phrase("Printed On: " + xdate, ffont1);
       Phrase footer = new Phrase("@Copyright ITEME...!\n", ffont2);
       /* ColumnText.showTextAligned(cb, Element.ALIGN_CENTER,
                header,
                (document.right() - document.left()) / 2 + document.leftMargin(),
                 document.top() + 10, 0);*/
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
		Rectangle rect = new Rectangle(100, 100, 700, 700);
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
		Rectangle rect = new Rectangle(100, 100, 700, 700);
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
				

				Users t = usersImpl.gettUserById(usersSession.getUserId(), "userId");
				String myNane = t.getFname()+""+t.getLname();
				//PdfPCell pc = new PdfPCell(new Paragraph("Report for all activities for:\n" + myNane));
				Paragraph header1 = new Paragraph("Report of all activities created by:\n" + myNane, ffont2);
				header1.setAlignment(Element.ALIGN_CENTER);
				Paragraph welcome = new Paragraph();
				// LOGO IMAGE FOR TRESS

				ServletContext ctx = (ServletContext) FacesContext.getCurrentInstance().getExternalContext().getContext();
				String realPath = ctx.getRealPath("/");
				LOGGER.info("Filse Reals Path::::" + realPath);
				final Path destination = Paths.get(realPath + FILELOCATION + "logo.jpeg");
				LOGGER.info("Path::" + destination);
				Image img = Image.getInstance("" + destination);
				img.scaleAbsolute(50f, 50f);
				
				welcome.add(img);
				PdfPTable tableh = new PdfPTable(2);
				tableh.setWidthPercentage(100);
				tableh.setWidths(new int[] { 1, 4 });
				tableh.addCell(createImageCell(img + ""));
				tableh.addCell(createTextCell("TRUST ENGEENERING SOLUTION LTD"));
				document.add(tableh);
				document.add(header1);
				document.add(new Paragraph("..........................................................................................................................................................."));

				document.add(new Paragraph("                                                                                                                               Generated on "+ xdate,ffont0));

				document.add(new Paragraph("............................................................................................................................................................"));
				
				document.add(new Paragraph("                                              "));
				
				PdfPTable table = new PdfPTable(4);
				table.setTotalWidth(PageSize.A4.getWidth());
				table.setLockedWidth(true);
				
				PdfPCell pc1 = new PdfPCell(new Paragraph(" Execution period", ffont3));
				pc1.setHorizontalAlignment(Element.ALIGN_CENTER);
				table.addCell(pc1);

				PdfPCell pc2 = new PdfPCell(new Paragraph(" Activity", ffont3));
				pc2.setHorizontalAlignment(Element.ALIGN_CENTER);
				table.addCell(pc2);

				PdfPCell pc3 = new PdfPCell(new Paragraph(" week", ffont3));
				pc3.setHorizontalAlignment(Element.ALIGN_CENTER);
				table.addCell(pc3);

				PdfPCell pc4 = new PdfPCell(new Paragraph(" Status", ffont3));
				pc4.setHorizontalAlignment(Element.ALIGN_CENTER);
				table.addCell(pc4);
/*
				PdfPCell pc5 = new PdfPCell(new Paragraph(" Staff", ffont3));
				pc5.setHorizontalAlignment(Element.ALIGN_CENTER);
				table.addCell(pc5);
				table.setHeaderRows(1);*/
				
				for (Activity activity : activitydetails) {
					PdfPCell p1 = new PdfPCell(new Paragraph(activity.getFormatedDate1()));
					p1.setHorizontalAlignment(Element.ALIGN_CENTER);
					table.addCell(p1);
					
					PdfPCell p2 = new PdfPCell(new Paragraph(activity.getDescription()));
					p2.setHorizontalAlignment(Element.ALIGN_CENTER);
					table.addCell(p2);
					
					PdfPCell p3 = new PdfPCell(new Paragraph("     "+activity.getFormatedDate1()+"\n "+"to"+" "+activity.getFormatedDate2()));
					p3.setHorizontalAlignment(Element.ALIGN_CENTER);
					table.addCell(p3);
					
					PdfPCell p4 = new PdfPCell(new Paragraph(activity.getStatus()));
					p4.setHorizontalAlignment(Element.ALIGN_CENTER);
					table.addCell(p4);
					

					/*PdfPCell p5 = new PdfPCell(new Paragraph("" + activity.getCreatedBy()));
					p5.setHorizontalAlignment(Element.ALIGN_CENTER);
					table.addCell(p5);*/
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

}

public List<ClearanceDto> myClearance() {
	try {		
		ClearanceDtoDetails = new ArrayList<ClearanceDto>();
		
		
				
                 ClearanceDtoDetails = new ArrayList<ClearanceDto>();
				for (Object[] data : institutionReportViewImpl.reportList("SELECT stategicplan,mytask, sum(case  when  institutionName in ('BRD','kamana')  then 1 else 0 end),\r\n" + 
						"sum(case when (institutionName in ('BRD','kamana') and status='Completed' ) then 1 else 0 end),\r\n" + 
						"(sum(case when (institutionName in ('BRD','kamana') and status='Completed' ) then 1 else 0 end) /sum(case  when institutionName in ('BRD','kamana')\r\n" + 
						"then 1 else 0 end))*100 from InstitutionReportView group by stategicplan,mytask"
		)) {
			
			ClearanceDto userDtos = new ClearanceDto();
			
			userDtos.setStrategicplan(data[0] + "");
			userDtos.setTaskName(data[1] + "");
			userDtos.setNumberOfActivities(Integer.parseInt(data[2] + ""));
			userDtos.setNumberOfFinishedActivities(Integer.parseInt(data[3] + ""));
			if(data[4]==null) {
			userDtos.setRate(Double.parseDouble(data[4]+""));
			}
			ClearanceDtoDetails.add(userDtos);
			
		}	
		return(ClearanceDtoDetails);
		
		
	} catch (Exception e) {
		e.printStackTrace();
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
		
			//JSFMessagers.addErrorMessage("Your institution does not have any Strategic plan");
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
	