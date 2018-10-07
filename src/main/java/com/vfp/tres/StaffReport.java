package com.vfp.tres;
import java.io.Serializable;
import java.sql.Timestamp;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.List;
import java.util.logging.Logger;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.ViewScoped;
import javax.faces.context.ExternalContext;
import javax.faces.context.FacesContext;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;

import tres.common.DbConstant;
import tres.common.Formating;
import tres.common.JSFBoundleProvider;
import tres.common.SessionUtils;
import tres.dao.impl.TaskImpl;
import tres.dao.impl.UserImpl;
import tres.domain.Task;
import tres.domain.Users;
import tres.vfp.dto.TaskDto;

import com.itextpdf.text.BaseColor;
import com.itextpdf.text.Document;
import com.itextpdf.text.DocumentException;
import com.itextpdf.text.Element;
import com.itextpdf.text.Font;
import com.itextpdf.text.Image;
import com.itextpdf.text.Paragraph;
import com.itextpdf.text.Phrase;
import com.itextpdf.text.Rectangle;
import com.itextpdf.text.pdf.ColumnText;
import com.itextpdf.text.pdf.PdfContentByte;
import com.itextpdf.text.pdf.PdfPCell;
import com.itextpdf.text.pdf.PdfPTable;
import com.itextpdf.text.pdf.PdfPageEventHelper;
import com.itextpdf.text.pdf.PdfWriter;
import com.vfp.tres.StaffReportActivity.MyFooter;

import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.FileOutputStream;
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
	private int taskID;
	/*end  manage validation messages*/
	private Users users;
	private Users usersSession;
	private Task task;
	private Date first;
	private Date second;
	private String login;
	
	private List<Task> taskDetails = new ArrayList<Task>();
	private List<TaskDto> taskDtoDetails = new ArrayList<TaskDto>();
	SimpleDateFormat sdf = new SimpleDateFormat("dd/MM/yyyy");
	
	/*class injection*/
	
	JSFBoundleProvider provider = new JSFBoundleProvider();
	UserImpl usersImpl = new UserImpl();
	TaskImpl taskImpl = new TaskImpl();
	
	/*end class injection*/
	Timestamp timestamp = new Timestamp(Calendar.getInstance().getTime().getTime());
	
//public void reportByDate() {
//
//		   Formating fmt = new Formating();
//		   
//			try {
//				taskDetails=taskImpl.getListByDateBewteenOtherCriteria("startDate",
//						fmt.getMysqlDateFormt(first + ""), fmt.getMysqlDateFormt(second + ""),
//						new String[] { "genericStatus"},
//						new Object[] { ACTIVE});
//				
//			} catch (ParseException e) {
//				LOGGER.info(e.getMessage());
//				e.printStackTrace();
//			}
//			for (Task task : taskDetails){
//				
//				if (use.get) {
//					
//				}
//			

//}
	
	//Codes for setting the footer and header.-->
	
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
        document.add(new Paragraph("\n"));
        Font font0 = new Font(Font.FontFamily.TIMES_ROMAN, 11, Font.BOLD);
        PdfPTable table = new PdfPTable(5);
     //Pdf table=new Pdftable(3);
        
    PdfPCell pc = new PdfPCell(new Paragraph("TASK NAME",font0));
        pc.setColspan(5);
        pc.setBackgroundColor(BaseColor.MAGENTA);
        pc.setHorizontalAlignment(Element.ALIGN_CENTER);
        table.addCell(pc);
        
    table.setWidthPercentage(110);
    PdfPCell pc1 = new PdfPCell(new Paragraph(" Task name\n Execution period", font0));
    //pc1.setRowspan(3);
    pc1.setBackgroundColor(BaseColor.ORANGE);
    table.addCell(pc1);
    
    PdfPCell pc2 = new PdfPCell(new Paragraph(" Activity", font0));
    pc2.setBackgroundColor(BaseColor.ORANGE);
    table.addCell(pc2);
   
    
    PdfPCell pc3 = new PdfPCell(new Paragraph(" week", font0));
    pc3.setBackgroundColor(BaseColor.ORANGE);
    table.addCell(pc3);
    
    
    PdfPCell pc4 = new PdfPCell(new Paragraph(" Marks", font0));
    pc4.setBackgroundColor(BaseColor.ORANGE);
    table.addCell(pc4);
    
    
    PdfPCell pc5 = new PdfPCell(new Paragraph(" Status", font0));
    pc5.setBackgroundColor(BaseColor.ORANGE);
    table.addCell(pc5);
    table.setHeaderRows(2);
    HttpSession session = SessionUtils.getSession();
	usersSession= (Users) session.getAttribute("userSession");
	
	if (users == null) {
		users = new Users();
	}
	
	if (task == null) {
		task = new Task();
	}
	
	try {
		taskDetails=taskImpl.getGenericListWithHQLParameter(new String[] {"genericStatus"},new Object[] {ACTIVE},"Task", "taskId asc");
		for (Task task : taskDetails){
			
			table.addCell(task.getTaskName());
			table.addCell(task.getDescription());
			table.addCell(""+task.getDueDate());
			table.addCell(""+task.getTaskId());
			table.addCell(task.getGenericStatus());		
			
	}
		document.add(table);
		
		document.close();
		writePDFToResponse(context.getExternalContext(), baos, "Staff_report");

		context.responseComplete();
		}catch(Exception e) {
			LOGGER.info(e.getMessage());
			e.printStackTrace();
		}
		}
public void myrepo() throws IOException, DocumentException {
    new StaffReport().createPdf();
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
	
}
	