package com.vfp.tres;

import java.awt.Color;
import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.OutputStream;
import java.io.Serializable;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.sql.Timestamp;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.List;
import java.util.UUID;
import java.util.logging.Logger;
import javax.annotation.PostConstruct;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.ViewScoped;
import javax.faces.context.ExternalContext;
import javax.faces.context.FacesContext;
import javax.servlet.ServletContext;
import javax.servlet.http.HttpSession;
import javax.swing.text.StyleConstants.FontConstants;
//import com.itextpdf.layout.element.Text;

import org.apache.commons.io.FilenameUtils;
import org.apache.poi.hssf.usermodel.HSSFFont;
import org.apache.poi.hssf.usermodel.HSSFSheet;
import org.apache.poi.hssf.usermodel.HSSFWorkbook;
import org.apache.poi.ss.usermodel.Cell;
import org.apache.poi.ss.usermodel.CellStyle;
import org.apache.poi.ss.usermodel.IndexedColors;
import org.apache.poi.ss.usermodel.Row;
import org.primefaces.model.chart.Axis;
import org.primefaces.model.chart.AxisType;
import org.primefaces.model.chart.BarChartModel;
import org.primefaces.model.chart.ChartSeries;
import org.primefaces.model.chart.LineChartModel;
import org.primefaces.model.chart.PieChartModel;

import com.google.gson.Gson;
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
import tres.common.DbConstant;
import tres.common.JSFBoundleProvider;
import tres.common.JSFMessagers;
import tres.common.SessionUtils;
import tres.dao.impl.ActivityImpl;
import tres.dao.impl.BoardImpl;
import tres.dao.impl.ClearanceImpl;
import tres.dao.impl.InstitutionReportViewImpl;
import tres.dao.impl.StaffReportViewImpl;
import tres.dao.impl.TaskImpl;
import tres.dao.impl.UserImpl;
import tres.domain.Activity;
import tres.domain.Board;
import tres.domain.Clearance;
import tres.domain.Documents;
import tres.domain.InstitutionReportView;
import tres.domain.Task;
import tres.domain.TaskAssignment;
import tres.domain.Users;
import tres.vfp.dto.ActivityDto;
import tres.vfp.dto.ClearanceDto;
import tres.vfp.dto.ClearanceDto;
import tres.vfp.dto.TaskDto;

@ManagedBean
@ViewScoped
public class MdReportActivity implements Serializable, DbConstant {

	private static final Logger LOGGER = Logger.getLogger(Thread.currentThread().getStackTrace()[0].getClassName());
	private String CLASSNAME = "ActivityController :: ";
	private static final long serialVersionUID = 1L;
	/* to manage validation messages */
	private boolean isValid;
	/* end manage validation messages */
	private Users users;
	private Users usersSession;
	private List<Task> taskDetails = new ArrayList<Task>();
	private Task task;
	private String myChoice;
	private String performance;
	private Board board;
	private boolean rendered;
	private boolean renderedx;
	private boolean renderedchart;
	private boolean renderedclear;
	private boolean rendertable;
	private boolean renderEditedTableByBoard;
	private boolean renderTableByBoard;
	private int selectedBoard;
	private LineChartModel animatedModel1;
	private BarChartModel animatedModel2;
	private PieChartModel pieModel1;
	private PieChartModel pieModel2;
	private List<TaskDto> taskDtoDetails = new ArrayList<TaskDto>();
	private Activity activity;
	private List<ClearanceDto> staffPerformanceDtoDetails = new ArrayList<ClearanceDto>();
	private List<Activity> activityDetails = new ArrayList<Activity>();
	private List<ActivityDto> activityDtoDetails = new ArrayList<ActivityDto>();
	private List<ClearanceDto> activityMddtodetails = new ArrayList<ClearanceDto>();
	private List<Board> boardDetails = new ArrayList<Board>();
	private List<ClearanceDto> ClearanceDtoDetails = new ArrayList<ClearanceDto>();
	private List<ClearanceDto> ClearanceDtoDetailsy = new ArrayList<ClearanceDto>();
	private ClearanceDto clearanceDto;
	private TaskAssignment taskAssign;
	private String overallPerformance;
	StaffReportViewImpl staffReportViewImpl = new StaffReportViewImpl();
	private String[] timespan = { DAILY, WEEKLY, MONTHLY, YEARLY };
	private String[] graphType = { ACTIVITYAPROVAL, ACTIVITYPLANNED, ACTIVITYCOMPLETED };
	SimpleDateFormat dt = new SimpleDateFormat("dd/MM/yyyy  HH:mm:ss");
	SimpleDateFormat dtf = new SimpleDateFormat("dd/MM/yyyy");
	private String[] type;
	private boolean renderTask;
	private boolean renderedd;
	private boolean backBtn;
	private boolean renderTaskForm;
	private boolean renderSelectedBoard;
	private String name;
	private String namess;
	private String convertedData="test";
	private String[] number;
	private String[] values;
	/* class injection */

	JSFBoundleProvider provider = new JSFBoundleProvider();
	UserImpl usersImpl = new UserImpl();
	ActivityImpl activityImpl = new ActivityImpl();
	TaskImpl taskImpl = new TaskImpl();
	BoardImpl boardImpl = new BoardImpl();
	
	Task tc = new Task();
	InstitutionReportView institutionReportView = new InstitutionReportView();
	InstitutionReportViewImpl institutionReportViewImpl = new InstitutionReportViewImpl();
	private List<InstitutionReportView> institutionreportdetails = new ArrayList<InstitutionReportView>();
	SimpleDateFormat sdf = new SimpleDateFormat("dd-MM-yyyy ");
	Clearance clearance = new Clearance();
	ClearanceImpl clearanceImpl = new ClearanceImpl();
	private List<Clearance> Clearancedetails = new ArrayList<Clearance>();

	/* end class injection */
	Timestamp timestamp = new Timestamp(Calendar.getInstance().getTime().getTime());

	@SuppressWarnings("unchecked")
	@PostConstruct
	public void init() {
		myClearances();

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
		if (board == null) {
			board = new Board();
		}

		try {
			boardDetails = boardImpl.getGenericListWithHQLParameter(new String[] { "genericStatus" },
					new Object[] { ACTIVE }, "Board", "boardId asc");
		} catch (Exception e) {
		}
	}
   
	private void createPieModels() {
		// createPieModel1();
		createPieModel2();
	}

	// see reference on this example how to get data from the database
	// end example
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

		for (Object[] data : taskImpl.reportList(
				"select t.taskName,u.fname,b.boardName from Task t,Board b,Users u,Activity a where t.taskId=a.task and u.userId=a.user and a.user=u.userId and b.boardId=u.board")) {

			// select t.taskName,u.fname,b.boardName from Task t,Board b,Users u,Activity a
			// where t.taskId=a.task and u.userId=a.user and a.user=u.userId and
			// b.boardId=u.board;
			LOGGER.info("tes1 1::" + data[0] + " ::" + data[1] + " ::" + data[2]);
		}

		for (Object[] data : taskImpl.reportList(
				"select count(*),t.taskName,t.endDate,u.fname,b.boardName from Task t,Board b,Users u,Activity a where t.taskId=a.task and u.userId=a.user and a.user=u.userId and b.boardId=u.board")) {

			// select t.taskName,u.fname,b.boardName from Task t,Board b,Users u,Activity a
			// where t.taskId=a.task and u.userId=a.user and a.user=u.userId and
			// b.boardId=u.board;
			LOGGER.info("tes1 1::" + Integer.parseInt(data[0] + "") + " ::" + data[1] + " ::" + data[2]);
		}
	}

	private BarChartModel initBarModel() {
		BarChartModel model = new BarChartModel();

		ChartSeries tasks = new ChartSeries();
		for (Object[] data : taskImpl.reportList(
				"select count(*),a.description,tas.taskName from Activity a  join  a.task tas  group by tas.taskId")) {

			// select t.taskName,u.fname,b.boardName from Task t,Board b,Users u,Activity a
			// where t.taskId=a.task and u.userId=a.user and a.user=u.userId and
			// b.boardId=u.board;
			tasks.set(data[2] + "", Integer.parseInt(data[0] + ""));
		}
		model.addSeries(tasks);
		return model;
	}

	private void createPieModel2() {
		pieModel2 = new PieChartModel();
		UserImpl usImpl = new UserImpl();

		for (Object[] data : usImpl.reportList(
				"select count(*),tas.taskName from Task tas, Activity ac where ac.task=tas.taskId group by tas.taskId")) {

			pieModel2.set(data[1] + "", Integer.parseInt(data[0] + ""));
		}
		pieModel2.setTitle("Pie chart ");
		pieModel2.setLegendPosition("e");
		pieModel2.setFill(false);
		pieModel2.setShowDataLabels(true);
		pieModel2.setDiameter(150);
		pieModel2.setShadow(false);
	}

	// CREATING FOOTER AND HEADER FOR PAGES

	class MyFooter extends PdfPageEventHelper {

		Font ffont1 = new Font(Font.FontFamily.UNDEFINED, 12, Font.ITALIC);

		Font ffont2 = new Font(Font.FontFamily.UNDEFINED, 16, Font.ITALIC);

		public void onEndPage(PdfWriter writer, Document document) {
			PdfContentByte cb = writer.getDirectContent();
			Phrase header = new Phrase("");
			Phrase footer = new Phrase("                                   @Copyright ITEME...!\n", ffont2);
			ColumnText.showTextAligned(cb, Element.ALIGN_CENTER, header,
					(document.right() - document.left()) / 2 + document.leftMargin(), document.top() + 10, 0);
			ColumnText.showTextAligned(cb, Element.ALIGN_CENTER, footer,
					(document.right() - document.left()) / 2 + document.leftMargin(), document.bottom() - 10, 0);
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
		String OS = null;
		// String path="c:/some\\\\path/file.txt";
		if (OS == null) {
			OS = System.getProperty("os.name");
		}

		if (OS.startsWith("Windows")) {
			final Path destination = Paths.get(realPath + FILELOCATION + "logo.jpeg");
			// System.out.println(path);
			LOGGER.info("Filse Reals Path::::" + realPath);
			LOGGER.info("THE PATH IS FOR WINDOWS::::" + realPath);
			LOGGER.info("Path::" + destination);
			Image img = Image.getInstance("" + destination);
			img.scaleAbsolute(50f, 50f);
			PdfPCell cell = new PdfPCell(img, true);
			cell.setFixedHeight(60);
			cell.setBorder(Rectangle.NO_BORDER);
			return cell;

		} else {
			LOGGER.info("THE PATH IS FOR unix operating system::::" + realPath);
			final Path destination = Paths.get(realPath + FILELOCATIONUNIX + "logo.jpeg");
			LOGGER.info("THE PATH IS FOR unix operating system finish::::" + realPath);
			// System.out.println(path);
			Image img = Image.getInstance("" + destination);
			img.scaleAbsolute(50f, 50f);
			PdfPCell cell = new PdfPCell(img, true);
			cell.setFixedHeight(60);
			cell.setBorder(Rectangle.NO_BORDER);
			return cell;

		}
		 //end of checking the operating system
	}
    
	
	// Codes for creating the table and its contents

	public void createPdf() throws IOException, DocumentException {
		
		//if(selectedBoard!=0) {
			//setValid(true);
			
		Font font18 = new Font(Font.FontFamily.TIMES_ROMAN, 13, Font.BOLDITALIC, BaseColor.BLUE);
		Font font0 = new Font(Font.FontFamily.TIMES_ROMAN, 11, Font.BOLD);
		Font font8 = new Font(Font.FontFamily.TIMES_ROMAN, 11,Font.BOLDITALIC);
		Font font2 = new Font(Font.FontFamily.TIMES_ROMAN, 14, Font.BOLD,BaseColor.BLACK);
		Date date = new Date();
		
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
		
		//document.add(new Paragraph("\n"));
		
		Board t = boardImpl.getBoardById(selectedBoard, "boardId");
		String boardname=t.getBoardName();
		
		Paragraph header1 = new Paragraph("REPORT OF TASKS ASSIGNED ON  "+ " " +boardname+ "  " +"BOARD", font2);
		header1.setAlignment(Element.ALIGN_CENTER);
		Paragraph welcome = new Paragraph();
		
		
		// LOGO IMAGE FOR TRESS

		ServletContext ctx = (ServletContext) FacesContext.getCurrentInstance().getExternalContext().getContext();
		String realPath = ctx.getRealPath("/");
		LOGGER.info("Filse Reals Path::::" + realPath);
		 Path destination=null;
		String OS = null;
		if (OS == null) {
			OS = System.getProperty("os.name");
		}
     if (OS.startsWith("Windows")) {
		  destination = Paths.get(realPath + FILELOCATION + "logo.jpeg");
     }else {
	  destination = Paths.get(realPath + FILELOCATIONUNIX + "logo.jpeg");	
	 LOGGER.info("Path UNIX::" + destination);
     }
		
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

		document.add(new Paragraph("                                                                                                  Generated on "+ xdate));

		document.add(new Paragraph("............................................................................................................................................................"));
		
		document.add(new Paragraph("                                              "));
		
		PdfPTable table = new PdfPTable(6);
		table.setTotalWidth(PageSize.A4.getWidth());
		table.setWidths(new int[] {1,5,5,5,5,5});
	
		table.setLockedWidth(true);
		
		PdfPCell pc0 = new PdfPCell(new Paragraph("#", font18));
		pc0.setHorizontalAlignment(Element.ALIGN_CENTER);
		table.addCell(pc0);
		
		PdfPCell pc1 = new PdfPCell(new Paragraph("TASK NAME", font18));
		pc1.setHorizontalAlignment(Element.ALIGN_CENTER);
		table.addCell(pc1);

		PdfPCell pc2 = new PdfPCell(new Paragraph("WEIGHT", font18));
		pc2.setHorizontalAlignment(Element.ALIGN_CENTER);
		table.addCell(pc2);

		PdfPCell pc3 = new PdfPCell(new Paragraph("START DATE", font18));
		pc3.setHorizontalAlignment(Element.ALIGN_CENTER);
		table.addCell(pc3);
		
		PdfPCell pcS = new PdfPCell(new Paragraph("DUE DATE", font18));
		pcS.setHorizontalAlignment(Element.ALIGN_CENTER);
		table.addCell(pcS);
		
		PdfPCell pc5 = new PdfPCell(new Paragraph("END DATE", font18));
		pc5.setHorizontalAlignment(Element.ALIGN_CENTER);
		table.addCell(pc5);
		
		
		table.setHeaderRows(1);
		try {
			if(selectedBoard!=0) {
				setValid(true);
			int number=1;
			for (Object[] data : taskImpl.reportList(
					"select t.taskName,t.endDate,t.dueDate,t.startDate,t.taskWeight,t.genericStatus,b.boardName from Task t,Board b \r\n" + 
					"where b.boardId=t.board and b.boardId='"+selectedBoard+"' order by t.startDate" )) {
				
				PdfPCell p = new PdfPCell(new Paragraph(number + "",font8));
				p.setHorizontalAlignment(Element.ALIGN_CENTER);
				table.addCell(p);
				
				PdfPCell pcel1 = new PdfPCell(new Paragraph(data[0] + "",font8));
				pcel1.setHorizontalAlignment(Element.ALIGN_CENTER);
				table.addCell(pcel1);
				
				PdfPCell pcel2 = new PdfPCell(new Paragraph(data[4] + "",font8));
				pcel2.setHorizontalAlignment(Element.ALIGN_CENTER);
				table.addCell(pcel2);
				
				PdfPCell pcel3 = new PdfPCell(new Paragraph(dtf.format(data[3]),font8));
				pcel3.setHorizontalAlignment(Element.ALIGN_CENTER);
				table.addCell(pcel3);
				
				PdfPCell pcel4 = new PdfPCell(new Paragraph(dtf.format(data[2]),font8));
				pcel4.setHorizontalAlignment(Element.ALIGN_CENTER);
				table.addCell(pcel4);
				
				PdfPCell pcele = new PdfPCell(new Paragraph(dtf.format(data[1]),font8));
				pcele.setHorizontalAlignment(Element.ALIGN_CENTER);
				table.addCell(pcele);
				
				number++;
			}
			document.add(table);

			document.close();

			writePDFToResponse(context.getExternalContext(), baos, "MD_report"+xdate);

			context.responseComplete();
			}else {
				setValid(false);
				JSFMessagers.addErrorMessage(getProvider().getValue("com.server.side.internal.errorSelectedBoard"));
			}

		} catch (Exception e) {

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

//Method to print excel sheet
	public void printXLS() throws IOException {
		HSSFWorkbook book = new HSSFWorkbook();
		HSSFSheet sheet = book.createSheet("SupervisorExcelReport");
		// create a heading
		Row heading = sheet.createRow(0);
		heading.createCell(0).setCellValue("Task Name");
		heading.createCell(1).setCellValue("Execution Date");
		heading.createCell(2).setCellValue("Status");
		heading.createCell(3).setCellValue("Departement");
		heading.createCell(4).setCellValue("Marks");
		for (int i = 0; i < 5; i++) {
			CellStyle cellStyle = book.createCellStyle();
			HSSFFont font = book.createFont();
			font.setFontName(HSSFFont.FONT_ARIAL);
			font.setBoldweight((short) 100);
			font.setColor(IndexedColors.DARK_RED.getIndex());
			font.setFontHeightInPoints((short) 16);
			cellStyle.setFont(font);
			cellStyle.setVerticalAlignment(CellStyle.ALIGN_CENTER);
			heading.getCell(i).setCellStyle(cellStyle);
		}

		// From database

		try {
			int i = 1;
			for (Object[] data : taskImpl
					.reportList("select t.taskName,t.endDate,t.genericStatus,b.boardName from Task t,Board b,Users u,"
							+ "Activity a where t.taskId=a.task and u.userId=a.user and a.user=u.userId and b.boardId=u.board and b.boardId='"
							+ selectedBoard + "'")) {

				Row row = sheet.createRow(i);
				Cell cell1 = row.createCell(0);
				cell1.setCellValue(data[0] + "");

				Cell cell2 = row.createCell(1);
				cell2.setCellValue(data[1] + "");

				Cell cell3 = row.createCell(2);
				cell3.setCellValue(data[2] + "");

				Cell cell4 = row.createCell(3);
				cell4.setCellValue(data[3] + "");
				if (data[2].equals("active")) {
					Cell cell5 = row.createCell(4);
					cell5.setCellValue("5");

				} else {
					Cell cell5 = row.createCell(4);
					cell5.setCellValue("0");

				}

				i++;

			}

			sheet.autoSizeColumn(4);

		} catch (Exception e) {
			e.getMessage();
		}
		FacesContext facesContext = FacesContext.getCurrentInstance();
		ExternalContext externalContext = facesContext.getExternalContext();
		externalContext.setResponseContentType("application/vnd.ms-excel");
		externalContext.setResponseHeader("Content-Disposition", "attachment; filename=\"SupervisorReport.xls\"");

		book.write(externalContext.getResponseOutputStream());
		facesContext.responseComplete();
	}

	public void updateTable() throws Exception {
		if (myChoice.equalsIgnoreCase(pdfFormat)) {

			rendered = true;
			renderedx = false;
			renderedchart = false;
			renderTableByBoard = false;
			renderEditedTableByBoard = true;
			renderedclear = false;
			renderSelectedBoard=false;
		} else if (myChoice.equalsIgnoreCase(taskchart)) {
			renderedchart = true;
			ChartController chart= new ChartController();
			chart.myClearance();
			rendered = false;
			renderTableByBoard = false;
			renderEditedTableByBoard = false;
			renderedx = false;
			renderedclear = false;
			renderSelectedBoard=false;
			
		} else if (myChoice.equalsIgnoreCase(clear)) {

			rendered = false;
			renderedx = false;
			renderedchart = false;
			renderTableByBoard = false;
			renderEditedTableByBoard = false;
			renderedclear = true;
			renderSelectedBoard=false;
			myClearances();
		} else {
			rendered = false;
			renderedx = true;
			//renderTableByBoard = true;
			renderEditedTableByBoard = true;
			renderedchart = false;
			renderedclear = false;
			renderSelectedBoard=false;
		}
	}

	public List<ClearanceDto> myClearance() {
		try {
			LOGGER.info("mwenedata::::::::::::::::::::::::::::::::::::::::::::::junior");
			if (selectedBoard != 0) {
				renderEditedTableByBoard = true;
				ClearanceDtoDetails = new ArrayList<ClearanceDto>();
				for (Object[] data : institutionReportViewImpl
						.reportList("SELECT stategicplan,mytaskName, sum(case  when  board in ('" + selectedBoard
								+ "')  then 1 else 0 end),\r\n" + "sum(case when (board in ('" + selectedBoard
								+ "') and status='done' ) then 1 else 0 end),\r\n" + "(sum(case when (board in ('"
								+ selectedBoard
								+ "') and status='done' ) then 1 else 0 end) /sum(case  when board in ('"
								+ selectedBoard + "')\r\n"
								+ "then 1 else 0 end))*100 from InstitutionReportView group by stategicplan,mytask")) {

					LOGGER.info("tes1 1::::::::::::::::" + data[0] + " ::::::::::::'" + selectedBoard + "'::::::::::::"
							+ data[1] + "::::::::" + data[2] + "::::::" + data[3] + ":::::::::::::" + data[4]
							+ ":::::::::::::james and Rashid");

					ClearanceDto userDtos = new ClearanceDto();
					userDtos.setStrategicplan(data[0] + "");
					userDtos.setTaskName(data[1] + "");
					userDtos.setNumberOfActivities(Integer.parseInt(data[2] + ""));
					userDtos.setCompleted(Integer.parseInt(data[3] + ""));
					userDtos.setRate(Double.parseDouble(data[4] + ""));

					ClearanceDtoDetails.add(userDtos);
				}

				return (ClearanceDtoDetails);

			} else {
				setValid(false);
				JSFMessagers.addErrorMessage(getProvider().getValue("com.server.side.internal.invalidchoice"));
			}

		} catch (Exception e) {
			e.printStackTrace();
		}

		return null;
	}
	//The following method is for generating the clearance report on chart for the selected Board.
	
		public List<ClearanceDto> createdClearanceForSelecetedBoard() {
			try {		
				ClearanceDtoDetails = new ArrayList<ClearanceDto>();
		        ClearanceDtoDetails = new ArrayList<ClearanceDto>();
						for (Object[] data : institutionReportViewImpl.reportList("SELECT mytaskName,\r\n" + 
								"(count(*)-sum(case when (status='rejected' ) then 1 else 0 end)) ,\r\n" + 
								"sum(case when (status='rejected' ) then 1 else 0 end) ,\r\n" + 
								"sum(case when (status='Approved' ) then 1 else 0 end) , \r\n" + 
								"sum(case when (status='Completed' ) then 1 else 0 end) ,\r\n" + 
								"((sum(case when (status='Completed' ) then 1 else 0 end)*100)/(count(*)-sum(case when (status='rejected' ) then 1 else 0 end)))  \r\n" + 
								"from InstitutionReportView where board='"+selectedBoard+"'   group by mytaskName"
				)) {
					
					ClearanceDto userDtos = new ClearanceDto();
					//userDtos.setStrategicplan(data[0] + "");
					userDtos.setTaskName(data[0] + "");
					//userDtos.setNumberOfActivities(Integer.parseInt(data[1] + ""));
					userDtos.setApproved(Integer.parseInt(data[3] + ""));
					//userDtos.setPending(Integer.parseInt(data[4] + ""));*/
					userDtos.setCompleted(Integer.parseInt(data[4] + ""));
					//userDtos.setRate(Double.parseDouble(data[5]+""));
					ClearanceDtoDetails.add(userDtos);
					this.name=new Gson().toJson(ClearanceDtoDetails);
					LOGGER.info("tes1 1::::::::::::::::the selectesd board is" +selectedBoard);
					
				}	
				return(ClearanceDtoDetails);
				
				
			} catch (Exception e) {
				e.printStackTrace();
			}
			
			
			return null;
		}
		//The following method is for generating the clearance report on chart for MdReport.
		
		public List<ClearanceDto> CrearanceClearanceForTheOverAll() {
			try {		
				ClearanceDtoDetails = new ArrayList<ClearanceDto>();
		        ClearanceDtoDetails = new ArrayList<ClearanceDto>();
						for (Object[] data : institutionReportViewImpl.reportList("SELECT mytaskName,\r\n" + 
								"(count(*)-sum(case when (status='rejected' ) then 1 else 0 end)) ,\r\n" + 
								"sum(case when (status='rejected' ) then 1 else 0 end) ,\r\n" + 
								"sum(case when (status='Approved' ) then 1 else 0 end) , \r\n" + 
								"sum(case when (status='Completed' ) then 1 else 0 end) ,\r\n" + 
								"((sum(case when (status='Completed' ) then 1 else 0 end)*100)/(count(*)-sum(case when (status='rejected' ) then 1 else 0 end)))  \r\n" + 
								"from InstitutionReportView group by mytaskName"
				)) {
					
					ClearanceDto userDtos = new ClearanceDto();
					//userDtos.setStrategicplan(data[0] + "");
					userDtos.setTaskName(data[0] + "");
					userDtos.setNumberOfActivities(Integer.parseInt(data[1] + ""));
					userDtos.setApproved(Integer.parseInt(data[3] + ""));
					//userDtos.setPending(Integer.parseInt(data[4] + ""));*/
					userDtos.setCompleted(Integer.parseInt(data[4] + ""));
					//userDtos.setRate(Double.parseDouble(data[5]+""));
					
					ClearanceDtoDetails.add(userDtos);
					this.namess=new Gson().toJson(ClearanceDtoDetails);
					
				}	
				return(ClearanceDtoDetails);
				
				
			} catch (Exception e) {
				e.printStackTrace();
			}
			
			
			return null;
		}
	
	public List<ClearanceDto> repfoboard() {
		try {
			renderEditedTableByBoard=true;
			if (selectedBoard != 0) {

				activityMddtodetails = new ArrayList<ClearanceDto>();
				for (Object[] data : taskImpl.reportList("select t.taskName,t.endDate,t.dueDate,t.startDate,t.taskWeight,t.genericStatus,b.boardName from Task t,Board b \r\n" + 
						"where b.boardId=t.board and b.boardId='"+selectedBoard+"' order by t.startDate")) {
					LOGGER.info("ndamukunda::::::::::::::::::::::::::::::::::::::::::::::kamana"+data[0] + "");

					ClearanceDto userDtos = new ClearanceDto();
					userDtos.setTaskName(data[0] + "");
					userDtos.setEndDate(sdf.format(data[1]));
					userDtos.setDueDate(sdf.format(data[2]));
					userDtos.setStartDate(sdf.format(data[3]));
					userDtos.setTaskWeight(data[4]+"");
					userDtos.setGenericStatus(data[5] + "");
					userDtos.setBoarName(data[3] + "");
					

					activityMddtodetails.add(userDtos);
				}
				return (activityMddtodetails);

			} else {
				setValid(false);
				JSFMessagers.addErrorMessage(getProvider().getValue("com.server.side.internal.invalidchoice"));
			}

		} catch (Exception e) {
			e.printStackTrace();
		}

		return null;
	}
	
	
	//CODES TO GENERATE THE TASK ASSIGNED ON A BOARD.
	
	public void showStatistics() {

		rendered = false;
		renderedx = false;
		renderedchart = false;
		renderTableByBoard = false;
		renderEditedTableByBoard = false;
		renderedclear = false;
		renderSelectedBoard=true;
		createdClearanceForSelecetedBoard();
			
	}

	@SuppressWarnings("unlikely-arg-type")
	public void showGraph() {
		if (type.equals(ACTIVITYAPROVAL)) {

		} else if (type.equals(ACTIVITYCOMPLETED)) {

		} else if (type.equals(ACTIVITYPLANNED)) {

		}
	}
	
	
	public List<ClearanceDto> myClearances() {
		try {
			ClearanceDtoDetailsy = new ArrayList<ClearanceDto>();

			ClearanceDtoDetailsy = new ArrayList<ClearanceDto>();
			for (Object[] data : institutionReportViewImpl.reportList("SELECT board,mytaskName,\r\n"
					+ "(count(*)-sum(case when (status='rejected' ) then 1 else 0 end)),\r\n"
					+ "sum(case when (status='Not Started' ) then 1 else 0 end),\r\n"
					+ "sum(case when (status='pending' ) then 1 else 0 end),\r\n"
					+ "sum(case when (status='Completed' ) then 1 else 0 end),\r\n"
					+ "((sum(case when (status='Completed' ) then 1 else 0 end)*100)/(count(*)-sum(case when (status='rejected' ) then 1 else 0 end))) \r\n"
					+ "from InstitutionReportView group by mytaskName")) {
                 
				ClearanceDto userDtos = new ClearanceDto();

				//userDtos.setStrategicplan(data[0] + "");
				userDtos.setTaskName(data[1] + "");
				userDtos.setNumberOfActivities(Integer.parseInt(data[2] + ""));
				userDtos.setApproved(Integer.parseInt(data[3] + ""));
				userDtos.setRejected(Integer.parseInt(data[4] + ""));
				userDtos.setCompleted(Integer.parseInt(data[5] + ""));
				userDtos.setRate(Double.parseDouble(data[6] + ""));
				ClearanceDtoDetailsy.add(userDtos);

			}
			return (ClearanceDtoDetailsy);

		} catch (Exception e) {
			e.printStackTrace();
		}

		return null;
	}
	
	//END OF CODES TO GENRATE THE ASSIGNED BOARD CHART 
	public List<ClearanceDto> getActivityMddtodetails() {
		return activityMddtodetails;
	}

	public void setActivityMddtodetails(List<ClearanceDto> activityMddtodetails) {
		this.activityMddtodetails = activityMddtodetails;
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

	public boolean isRenderEditedTableByBoard() {
		return renderEditedTableByBoard;
	}

	public void setRenderEditedTableByBoard(boolean renderEditedTableByBoard) {
		this.renderEditedTableByBoard = renderEditedTableByBoard;
	}

	public int getSelectedBoard() {
		return selectedBoard;
	}

	public void setSelectedBoard(int selectedBoard) {
		this.selectedBoard = selectedBoard;
	}

	public Clearance getClearance() {
		return clearance;
	}

	public void setClearance(Clearance clearance) {
		this.clearance = clearance;
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

	public String getMyChoice() {
		return myChoice;
	}

	public void setMyChoice(String myChoice) {
		this.myChoice = myChoice;
	}

	/*
	 * public Board getT() { return t; }
	 * 
	 * public void setT(Board t) { this.t = t; }
	 */

	public boolean isRendered() {
		return rendered;
	}

	public void setRendered(boolean rendered) {
		this.rendered = rendered;
	}

	public boolean isRenderedx() {
		return renderedx;
	}

	public void setRenderedx(boolean renderedx) {
		this.renderedx = renderedx;
	}

	public boolean isRenderedchart() {
		return renderedchart;
	}

	public void setRenderedchart(boolean renderedchart) {
		this.renderedchart = renderedchart;
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

	public Task getTc() {
		return tc;
	}

	public void setTc(Task tc) {
		this.tc = tc;
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

	public boolean isRendertable() {
		return rendertable;
	}

	public void setRendertable(boolean rendertable) {
		this.rendertable = rendertable;
	}

	public boolean isRenderTableByBoard() {
		return renderTableByBoard;
	}

	public void setRenderTableByBoard(boolean renderTableByBoard) {
		this.renderTableByBoard = renderTableByBoard;
	}

	public boolean isRenderedclear() {
		return renderedclear;
	}

	public void setRenderedclear(boolean renderedclear) {
		this.renderedclear = renderedclear;
	}

	public TaskAssignment getTaskAssign() {
		return taskAssign;
	}

	public void setTaskAssign(TaskAssignment taskAssign) {
		this.taskAssign = taskAssign;
	}

	public String getPerformance() {
		return performance;
	}

	public void setPerformance(String performance) {
		this.performance = performance;
	}

	public List<ClearanceDto> getStaffPerformanceDtoDetails() {
		return staffPerformanceDtoDetails;
	}

	public void setStaffPerformanceDtoDetails(List<ClearanceDto> staffPerformanceDtoDetails) {
		this.staffPerformanceDtoDetails = staffPerformanceDtoDetails;
	}

	public String getOverallPerformance() {
		return overallPerformance;
	}

	public void setOverallPerformance(String overallPerformance) {
		this.overallPerformance = overallPerformance;
	}

	public StaffReportViewImpl getStaffReportViewImpl() {
		return staffReportViewImpl;
	}

	public void setStaffReportViewImpl(StaffReportViewImpl staffReportViewImpl) {
		this.staffReportViewImpl = staffReportViewImpl;
	}

	public String[] getTimespan() {
		return timespan;
	}

	public void setTimespan(String[] timespan) {
		this.timespan = timespan;
	}

	public String[] getGraphType() {
		return graphType;
	}

	public void setGraphType(String[] graphType) {
		this.graphType = graphType;
	}

	public String[] getType() {
		return type;
	}

	public void setType(String[] type) {
		this.type = type;
	}

	public boolean isRenderTask() {
		return renderTask;
	}

	public void setRenderTask(boolean renderTask) {
		this.renderTask = renderTask;
	}

	public boolean isRenderedd() {
		return renderedd;
	}

	public void setRenderedd(boolean renderedd) {
		this.renderedd = renderedd;
	}

	public boolean isBackBtn() {
		return backBtn;
	}

	public void setBackBtn(boolean backBtn) {
		this.backBtn = backBtn;
	}

	public boolean isRenderTaskForm() {
		return renderTaskForm;
	}

	public void setRenderTaskForm(boolean renderTaskForm) {
		this.renderTaskForm = renderTaskForm;
	}

	public boolean isRenderSelectedBoard() {
		return renderSelectedBoard;
	}

	public void setRenderSelectedBoard(boolean renderSelectedBoard) {
		this.renderSelectedBoard = renderSelectedBoard;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public String getConvertedData() {
		return convertedData;
	}

	public void setConvertedData(String convertedData) {
		this.convertedData = convertedData;
	}

	public String[] getNumber() {
		return number;
	}

	public void setNumber(String[] number) {
		this.number = number;
	}

	public String[] getValues() {
		return values;
	}

	public void setValues(String[] values) {
		this.values = values;
	}

	public List<ClearanceDto> getClearanceDtoDetailsy() {
		return ClearanceDtoDetailsy;
	}

	public void setClearanceDtoDetailsy(List<ClearanceDto> clearanceDtoDetailsy) {
		ClearanceDtoDetailsy = clearanceDtoDetailsy;
	}

	public String getNamess() {
		return namess;
	}

	public void setNamess(String namess) {
		this.namess = namess;
	}
	

}
