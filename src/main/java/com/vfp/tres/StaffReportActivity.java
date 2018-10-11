package com.vfp.tres;

import java.io.ByteArrayOutputStream;

import java.io.IOException;
import java.io.OutputStream;
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
import org.primefaces.model.chart.LineChartSeries;
import org.primefaces.model.chart.PieChartModel;

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
import tres.common.DbConstant;
import tres.common.JSFBoundleProvider;
import tres.common.SessionUtils;
import tres.dao.impl.ActivityImpl;
import tres.dao.impl.TaskImpl;
import tres.dao.impl.UserImpl;
import tres.domain.Activity;
import tres.domain.Task;
import tres.domain.Users;
import tres.vfp.dto.ActivityDto;

@ManagedBean
@ViewScoped
public class StaffReportActivity implements Serializable, DbConstant {
	private static final Logger LOGGER = Logger.getLogger(Thread.currentThread().getStackTrace()[0].getClassName());
	private String CLASSNAME = "ActivityController :: ";
	private static final long serialVersionUID = 1L;
	/* to manage validation messages */
	private boolean isValid;
	/* end manage validation messages */
	private Users users;
	private Users usersSession;
	private Task tasks;
	private int myTask;
	private Date first=null;
	private Activity activity;
	private List<Activity> activityDetails = new ArrayList<Activity>();
	private List<ActivityDto> activityDtoDetails = new ArrayList<ActivityDto>();
	private List<Task> taskDetail = new ArrayList<Task>();
	private String[] status = { NOTSTARTED, APPROVED, REJECT, INPROGRESS, COMPLETED };
	private String[] weight = { SHORT, MEDIUM, LONG };

	private LineChartModel animatedModel1;
    private BarChartModel animatedModel2;
    private PieChartModel pieModel1;
    private PieChartModel pieModel2;
    
    
     Task tc= new Task();
    
	TaskImpl taskImpl = new TaskImpl();
	Task t=taskImpl.getTaskById(myTask, "taskId");
	
	/* class injection */

	JSFBoundleProvider provider = new JSFBoundleProvider();
	UserImpl usersImpl = new UserImpl();
	ActivityImpl activityImpl = new ActivityImpl();
	SimpleDateFormat sdf = new SimpleDateFormat("dd/MM/yyyy");

	/* end class injection */
	Timestamp timestamp = new Timestamp(Calendar.getInstance().getTime().getTime());
	@SuppressWarnings("unchecked")
	@PostConstruct
	public void init() {
		createAnimatedModels();
        createPieModels();
        
		HttpSession session = SessionUtils.getSession();
		usersSession = (Users) session.getAttribute("userSession");

		if (users == null) {
			users = new Users();
		}

		if (tasks == null) {
			tasks = new Task();
		}

		try {
			taskDetail = taskImpl.getListWithHQL(SELECT_TASK);
			taskDetail = taskImpl.getGenericListWithHQLParameter(new String[] { "genericStatus" },
					new Object[] { ACTIVE }, "Task", "taskId asc");
		} catch (Exception e) {
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
        animatedModel2.setTitle("Bar Charts");
        animatedModel2.setAnimate(true);
        animatedModel2.setLegendPosition("ne");
        Axis yAxis = animatedModel2.getAxis(AxisType.Y);
        yAxis.setMin(0);
        yAxis.setMax(10);
    } 
    private BarChartModel initBarModel() {
        BarChartModel model = new BarChartModel();
     
        ChartSeries tasks = new ChartSeries();
        for (Object[] data:  taskImpl.reportList("select count(*),tas.taskName from Activity a  join  a.task tas  group by tas.taskId")){
       
        	tasks.set(data[1]+"", Integer.parseInt(data[0]+""));
              }
        model.addSeries(tasks);
        tasks.setLabel(tc.getTaskName());
        return model;
    } 
    private void createPieModel2() {
    	pieModel2 = new PieChartModel();
        UserImpl usImpl=new UserImpl();
       
    for (Object[] data:  usImpl.reportList("select count(*),tas.taskName from Task tas, Activity ac where ac.task=tas.taskId group by tas.taskId")){
       
	  pieModel2.set(data[1]+"", Integer.parseInt(data[0]+""));	
        }
       pieModel2.setTitle("Pie chart ");
        pieModel2.setLegendPosition("e");
        pieModel2.setFill(false);
        pieModel2.setShowDataLabels(true);
        pieModel2.setDiameter(150);
        pieModel2.setShadow(false);
    } 
	// CREATING FOOTER AND HEADER

	class MyFooter extends PdfPageEventHelper {

		Font ffont1 = new Font(Font.FontFamily.UNDEFINED, 12, Font.ITALIC);

		Font ffont2 = new Font(Font.FontFamily.UNDEFINED, 16, Font.ITALIC);

		public void onEndPage(PdfWriter writer, Document document) {
			PdfContentByte cb = writer.getDirectContent();
			Date date = new Date();
			SimpleDateFormat dt = new SimpleDateFormat("dd/MM/yyyy");
			String xdate = dt.format(date);
			Phrase header = new Phrase("Printed On: " + xdate, ffont1);
			Phrase footer = new Phrase("@Copyright ITEME...!\n", ffont2);
			ColumnText.showTextAligned(cb, Element.ALIGN_CENTER, header,
					(document.right() - document.left()) / 2 + document.leftMargin(), document.top() + 10, 0);
			ColumnText.showTextAligned(cb, Element.ALIGN_CENTER, footer,
					(document.right() - document.left()) / 2 + document.leftMargin(), document.bottom() - 10, 0);
		}
	}



	//Codes for creating the table and its contents
	@SuppressWarnings("unchecked")
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
//  Image img = Image.getInstance(
//          "E:\\\\aTERSS NGABO\\\\VFPProject_Dev\\\\VFPProject_Dev\\\\src\\\\main\\\\resources\\\\netsss.PNG");
//  img.scaleAbsolute(520f, 40f);
//  Paragraph heade = new Paragraph();
//  heade.add(img);
//  heade.setAlignment(img.ALIGN_CENTER);
//  document.add(heade);

		document.add(new Paragraph("\n"));
		Font font0 = new Font(Font.FontFamily.TIMES_ROMAN, 11, Font.BOLD);
		PdfPTable table = new PdfPTable(5);
		// Pdf table=new Pdftable(3);
		
		Task t=taskImpl.getTaskById(myTask, "taskId");
		String mytaskNane=t.getTaskName();
		PdfPCell pc = new PdfPCell(new Paragraph("Report for all activities for:\n"+mytaskNane));
		pc.setColspan(5);
		pc.setBackgroundColor(BaseColor.CYAN);
		pc.setHorizontalAlignment(Element.ALIGN_CENTER);
		table.addCell(pc);

		table.setWidthPercentage(110);
		PdfPCell pc1 = new PdfPCell(new Paragraph(" Task name\n Execution period", font0));
		// pc1.setRowspan(3);
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
		HttpSession session = SessionUtils.getSession();
		usersSession = (Users) session.getAttribute("userSession");

		if (users == null) {
			users = new Users();
		}

		if (activity == null) {
			activity = new Activity();
		}

		try {
		activityDetails = activityImpl.getGenericListWithHQLParameter(new String[] { "genericStatus","task"},
			new Object[] { ACTIVE,taskImpl.getTaskById(Integer.parseInt(myTask+""), "taskId") }, "Activity","activityId asc");
			for (Activity activity : activityDetails) {
				table.addCell(activity.getCrtdDtTime()+"");
				table.addCell(activity.getDescription());
				table.addCell(activity.getWeight());
				table.addCell(activity.getStatus());
				table.addCell("" + activity.getCreatedBy());
			}
			document.add(table);

			document.close();

			writePDFToResponse(context.getExternalContext(), baos, "Super_visor_report");

			context.responseComplete();

		} catch (Exception e) {
			LOGGER.info(e.getMessage());
			LOGGER.info("Rachid " + myTask);
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

	/*public void myrepo() throws IOException, DocumentException {

		new StaffReportActivity().createPdf();
		LOGGER.info(myTask + "");
	}
	*/


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

	public Users getUsersSession() {
		return usersSession;
	}

	public void setUsersSession(Users usersSession) {
		this.usersSession = usersSession;
	}

	public Activity getActivity() {
		return activity;
	}

	public void setActivity(Activity activity) {
		this.activity = activity;
	}

	public List<Activity> getActivityDetails() {
		return activityDetails;
	}

	public void setActivityDetails(List<Activity> activityDetails) {
		this.activityDetails = activityDetails;
	}

	public List<ActivityDto> getActivityDtoDetails() {
		return activityDtoDetails;
	}

	public void setActivityDtoDetails(List<ActivityDto> activityDtoDetails) {
		this.activityDtoDetails = activityDtoDetails;
	}

	public String[] getStatus() {
		return status;
	}

	public void setStatus(String[] status) {
		this.status = status;
	}

	public String[] getWeight() {
		return weight;
	}

	public void setWeight(String[] weight) {
		this.weight = weight;
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

	public ActivityImpl getActivityImpl() {
		return activityImpl;
	}

	public void setActivityImpl(ActivityImpl activityImpl) {
		this.activityImpl = activityImpl;
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

	public Task getTask() {
		return tasks;
	}

	public void setTask(Task task) {
		this.tasks = task;
	}

	public int getMyTask() {
		return myTask;
	}

	public void setMyTask(int myTask) {
		this.myTask = myTask;
	}

	public List<Task> getTaskDetail() {
		return taskDetail;
	}

	public void setTaskDetail(List<Task> taskDetail) {
		this.taskDetail = taskDetail;
	}

	public TaskImpl getTaskImpl() {
		return taskImpl;
	}

	public void setTaskImpl(TaskImpl taskImpl) {
		this.taskImpl = taskImpl;
	}

	public Task getTasks() {
		return tasks;
	}

	public void setTasks(Task tasks) {
		this.tasks = tasks;
	}

	public Date getFirst() {
		return first;
	}

	public void setFirst(Date first) {
		this.first = first;
	}

	public SimpleDateFormat getSdf() {
		return sdf;
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
	public Task getT() {
		return t;
	}
	public void setT(Task t) {
		this.t = t;
	}
	public void setSdf(SimpleDateFormat sdf) {
		this.sdf = sdf;
	}

}
