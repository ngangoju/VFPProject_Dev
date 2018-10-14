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

import com.itextpdf.text.BaseColor;
import com.itextpdf.text.Chunk;
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


import tres.common.DbConstant;
import tres.common.JSFBoundleProvider;
import tres.common.SessionUtils;
import tres.dao.impl.ActivityImpl;
import tres.dao.impl.BoardImpl;
import tres.dao.impl.TaskImpl;
import tres.dao.impl.UserImpl;
import tres.domain.Activity;
import tres.domain.Board;
import tres.domain.Task;
import tres.domain.Users;
import tres.vfp.dto.ActivityDto;
import tres.vfp.dto.TaskDto;

@ManagedBean
@ViewScoped
public class MdReportActivity implements Serializable, DbConstant {
	
	private static final Logger LOGGER = Logger.getLogger(Thread.currentThread().getStackTrace()[0].getClassName());
	private String CLASSNAME = "ActivityController :: ";
	private static final long serialVersionUID = 1L;
	/*to manage validation messages*/
	private boolean isValid;
	/*end  manage validation messages*/
	private Users users;
	private Users usersSession;
	private List<Task> taskDetails = new ArrayList<Task>();
	private Task task;
	private Board board;
	private String selectedBoard;
	private List<TaskDto> taskDtoDetails = new ArrayList<TaskDto>();
	private Activity activity;
	private List<Activity> activityDetails = new ArrayList<Activity>();
	private List<ActivityDto> activityDtoDetails = new ArrayList<ActivityDto>();
	private List<Board>boardDetails=new ArrayList<Board>();
    private String[] status= {NOTSTARTED,APPROVED,REJECT,INPROGRESS,COMPLETED};
	private String[] weight= {SHORT,MEDIUM,LONG};
	
	/*class injection*/
	
	JSFBoundleProvider provider = new JSFBoundleProvider();
	UserImpl usersImpl = new UserImpl();
	ActivityImpl activityImpl = new ActivityImpl();
	TaskImpl taskImpl = new TaskImpl();
	BoardImpl boardImpl=new BoardImpl();
	/*end class injection*/
	Timestamp timestamp = new Timestamp(Calendar.getInstance().getTime().getTime());
	@SuppressWarnings("unchecked")
	@PostConstruct
	public void init() {
		
		HttpSession session = SessionUtils.getSession();
		usersSession = (Users) session.getAttribute("userSession");

		if (users == null) {
			users = new Users();
		}

		if (task == null) {
			task = new Task();
		}
		if (board == null) {
			board = new Board();
		}
   
		try {
			/*boardDetails = boardImpl.getListWithHQL(SELECT_BOARD);*/
			boardDetails= boardImpl.getGenericListWithHQLParameter(new String[] { "genericStatus" },new Object[] { ACTIVE }, "Board", "boardId asc");
		} catch (Exception e) {
		}
	}
	
    class MyFooter extends PdfPageEventHelper {

    Font ffont1 = new Font(Font.FontFamily.UNDEFINED, 12, Font.ITALIC);
   
    Font ffont2 = new Font(Font.FontFamily.UNDEFINED, 16, Font.ITALIC);
    public void onEndPage(PdfWriter writer, Document document) {
        PdfContentByte cb = writer.getDirectContent();
       Phrase header = new Phrase("");
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
	Date date = new Date();
    SimpleDateFormat dt = new SimpleDateFormat("dd/MM/yyyy");
	String xdate = dt.format(date);
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

      document.add(new Paragraph("\n"));
      
      
      Font font0 = new Font(Font.FontFamily.TIMES_ROMAN, 11, Font.BOLD);
      PdfPTable table = new PdfPTable(5);
   
//  PdfPCell pc = new PdfPCell(new Paragraph("TASK NAME "));
//      pc.setColspan(5);  
//      pc.setBackgroundColor(BaseColor.BLUE);
//      pc.setHorizontalAlignment(Element.ALIGN_CENTER);
//      table.addCell(pc);     
//  table.setWidthPercentage(110);
      Paragraph header1 = new Paragraph("MANAGER DIRECTOR REPORT MADE ON" + xdate + " REPORT");
		// header.setAlignment(Element.ALIGN_RIGHT);
		header1.setAlignment(Element.ALIGN_CENTER);
		// header.add(header1);
		document.add(header1);
		document.add(new Paragraph("                                          "));
      
  PdfPCell pc1 = new PdfPCell(new Paragraph("TASK NAME", font0));
  //pc1.setRowspan(3);
  pc1.setBackgroundColor(BaseColor.ORANGE);
  table.addCell(pc1);
  
  PdfPCell pc2 = new PdfPCell(new Paragraph("EXECUTION PERIOD", font0));
  pc2.setBackgroundColor(BaseColor.ORANGE);
  table.addCell(pc2);
 
  
  PdfPCell pc3 = new PdfPCell(new Paragraph("STATUS", font0));
  pc3.setBackgroundColor(BaseColor.ORANGE);
  table.addCell(pc3);
  
  
  PdfPCell pc4 = new PdfPCell(new Paragraph("DEPARTMENT", font0));
  pc4.setBackgroundColor(BaseColor.ORANGE);
  table.addCell(pc4);
  
  
  PdfPCell pc5 = new PdfPCell(new Paragraph(" MARKS", font0));
  pc5.setBackgroundColor(BaseColor.ORANGE);
  table.addCell(pc5);
  table.setHeaderRows(1);
  
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
		SimpleDateFormat sdf=new SimpleDateFormat("dd-MM-yyyy");
		for (Task task : taskDetails){
			table.addCell(task.getTaskName());
			table.addCell(""+sdf.format(task.getDueDate()));
			table.addCell(task.getGenericStatus());
			table.addCell("");
			if (task.getGenericStatus().equals(ACTIVE)) {
				table.addCell("+5");
			}else {
				table.addCell("-5");
			}
			
		}
        document.add(table);
		
		document.close();
		
		writePDFToResponse(context.getExternalContext(), baos, "MD_report");

		context.responseComplete();
		
		}catch(Exception e) {
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

public void myrepo() throws IOException, DocumentException {

    new MdReportActivity().createPdf();
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

public List<Task> getTaskDetails() {
	return taskDetails;
}

public void setTaskDetails(List<Task> taskDetails) {
	this.taskDetails = taskDetails;
}

public Task getTask() {
	return task;
}

public void setTask(Task task) {
	this.task = task;
}

public List<TaskDto> getTaskDtoDetails() {
	return taskDtoDetails;
}

public void setTaskDtoDetails(List<TaskDto> taskDtoDetails) {
	this.taskDtoDetails = taskDtoDetails;
}

public TaskImpl getTaskImpl() {
	return taskImpl;
}

public void setTaskImpl(TaskImpl taskImpl) {
	this.taskImpl = taskImpl;
}

public Board getBoard() {
	return board;
}

public void setBoard(Board board) {
	this.board = board;
}

public String getSelectedBoard() {
	return selectedBoard;
}

public void setSelectedBoard(String selectedBoard) {
	selectedBoard = selectedBoard;
}

public List<Board> getBoardDetails() {
	return boardDetails;
}

public void setBoardDetails(List<Board> boardDetails) {
	this.boardDetails = boardDetails;
}

public BoardImpl getBoardImpl() {
	return boardImpl;
}

public void setBoardImpl(BoardImpl boardImpl) {
	this.boardImpl = boardImpl;
}

}