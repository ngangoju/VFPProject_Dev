package com.vfp.tres;

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
import java.util.logging.Logger;

import javax.annotation.PostConstruct;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.ViewScoped;
import javax.faces.context.ExternalContext;
import javax.faces.context.FacesContext;
import javax.servlet.ServletContext;
import javax.servlet.http.HttpSession;
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
import tres.dao.impl.TaskImpl;
import tres.dao.impl.UserCategoryImpl;
import tres.dao.impl.UserImpl;
import tres.domain.Activity;
import tres.domain.Board;
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
	private Board board;
	private int myTask;
	private boolean rendered;
	private boolean renderedx;
	private boolean renderForexcel;
	private String myChoice;
	private String pdfchoice;
	private String excelchoice;
	private boolean renderedspdf;
	private boolean renderedtpdf;
	private boolean renderedsxl;
	private boolean renderedtxl;
	private int myName;
	private Date first = null;
	private Activity activity;
	private List<Activity> activityDetails = new ArrayList<Activity>();
	private List<Activity> activityDetailz = new ArrayList<Activity>();
	private List<Activity> activityDetailss = new ArrayList<Activity>();
	private List<ActivityDto> activityDtoDetails = new ArrayList<ActivityDto>();
	private List<Users> usersDetails = new ArrayList<Users>();
	private List<Task> taskDetail = new ArrayList<Task>();
	private String[] status = { NOTSTARTED, APPROVED, REJECT, INPROGRESS, COMPLETED };
	private String[] weight = { SHORT, MEDIUM, LONG };
	private LineChartModel animatedModel1;
	private BarChartModel animatedModel2;
	private PieChartModel pieModel1;
	private PieChartModel pieModel2;
	Task tc = new Task();
	TaskImpl taskImpl = new TaskImpl();
	UserCategoryImpl usercatgoryImpl = new UserCategoryImpl();
	BoardImpl boardImpl = new BoardImpl();
	/* class injection */
	JSFBoundleProvider provider = new JSFBoundleProvider();
	UserImpl usersImpl = new UserImpl();
	ActivityImpl activityImpl = new ActivityImpl();
	SimpleDateFormat sdf = new SimpleDateFormat("dd/MM/yyyy");
	SimpleDateFormat dt = new SimpleDateFormat("dd/MM/yyyy  hh:mm:ss");
	Date date =new Date();
	String xdate = dt.format(date);
	Font ffont2 = new Font(Font.FontFamily.UNDEFINED, 16, Font.BOLD);
	Font ffont3 = new Font(Font.FontFamily.UNDEFINED, 12, Font.BOLDITALIC,BaseColor.BLUE);
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
		if (board == null) {
			board = new Board();
		}

		try {

			taskDetail = taskImpl.getGenericListWithHQLParameter(new String[] { "genericStatus" },
					new Object[] { ACTIVE }, "Task", "taskId asc");

			usersDetails = usersImpl.getGenericListWithHQLParameter(new String[] { "genericStatus" },
					new Object[] { ACTIVE},
					"Users", "userId asc");
			
			/*usersDetails = usersImpl.getGenericListWithHQLParameter(new String[] { "genericStatus", "board" },
					new Object[] { ACTIVE, boardImpl.getBoardById(usersSession.getBoard().getBoardId(), "boardId") },
					"Users", "userId asc");*/

		} catch (Exception e) {
			e.getMessage();
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
		animatedModel2.setTitle("Bar Charts");
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
		for (Object[] data : taskImpl
				.reportList("select count(*),tas.taskName from Activity a  join  a.task tas  group by tas.taskId")) {

			// select t.taskName,u.fname,b.boardName from Task t,Board b,Users u,Activity a
			// where t.taskId=a.task and u.userId=a.user and a.user=u.userId and
			// b.boardId=u.board;
			tasks.set(data[1] + "", Integer.parseInt(data[0] + ""));
		}
		model.addSeries(tasks);
		tasks.setLabel(tc.getTaskName());
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
	// CREATING FOOTER AND HEADER

	class MyFooter extends PdfPageEventHelper {
		Font ffont1 = new Font(Font.FontFamily.UNDEFINED, 12, Font.ITALIC);

		Font ffont2 = new Font(Font.FontFamily.UNDEFINED, 16, Font.ITALIC);

		public void onEndPage(PdfWriter writer, Document document) {
			PdfContentByte cb = writer.getDirectContent();
			Date date = new Date();
			SimpleDateFormat dt = new SimpleDateFormat("dd/MM/yyyy");
		    String xdate = dt.format(date);
			//Phrase header = new Phrase("Printed On: " + xdate, ffont1);
			Phrase footer = new Phrase("                         @Copyright ITEME...!\n", ffont2);
			//ColumnText.showTextAligned(cb, Element.ALIGN_CENTER, header,
					//(document.right() - document.left()) / 2 + document.leftMargin(), document.top() + 10, 0);
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
	// Codes for creating the table and its contents
	@SuppressWarnings("unchecked")
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

		try {
			activityDetails = activityImpl.getGenericListWithHQLParameter(new String[] { "genericStatus", "task" },
					new Object[] { ACTIVE, taskImpl.getTaskById(Integer.parseInt(myTask + ""), "taskId") }, "Activity",
					"activityId asc");
			if (activityDetails.size() > 0) {
				setValid(true);
				document.add(new Paragraph("\n"));
				Font font0 = new Font(Font.FontFamily.TIMES_ROMAN, 11, Font.BOLD);
				
				Task t = taskImpl.getTaskById(myTask, "taskId");
				String mytaskNane = t.getTaskName();
				//PdfPCell pc = new PdfPCell(new Paragraph("Report of all activities for:\n" + mytaskNane));
				Paragraph header1 = new Paragraph("Report of all activities for:\n" + mytaskNane, ffont2);
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

				document.add(new Paragraph("                                                                                                  Generated on "+ xdate));

				document.add(new Paragraph("............................................................................................................................................................"));
				
				document.add(new Paragraph("                                              "));
				
				PdfPTable table = new PdfPTable(5);
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
				
				PdfPCell pc5 = new PdfPCell(new Paragraph(" Staff", ffont3));
				pc5.setHorizontalAlignment(Element.ALIGN_CENTER);
				table.addCell(pc5);
				
				table.setHeaderRows(1);
				HttpSession session = SessionUtils.getSession();
				usersSession = (Users) session.getAttribute("userSession");

				if (users == null) {
					users = new Users();
				}

				if (activity == null) {
					activity = new Activity();
				}

				for (Activity activity : activityDetails) {
					PdfPCell pcel1 = new PdfPCell(new Paragraph(activity.getFormatedDate2() + ""));
					pcel1.setHorizontalAlignment(Element.ALIGN_CENTER);
					table.addCell(pcel1);
					
					PdfPCell pcel2 = new PdfPCell(new Paragraph(activity.getDescription()));
					pcel2.setHorizontalAlignment(Element.ALIGN_CENTER);
					table.addCell(pcel2);
					
					PdfPCell pcel3 = new PdfPCell(new Paragraph("     "+activity.getFormatedDate1()+"\n "+"to"+" "+activity.getFormatedDate2()));
					pcel3.setHorizontalAlignment(Element.ALIGN_CENTER);
					table.addCell(pcel3);
					
					PdfPCell pcel4 = new PdfPCell(new Paragraph(activity.getStatus()));
					pcel4.setHorizontalAlignment(Element.ALIGN_CENTER);
					table.addCell(pcel4);
					
					PdfPCell pcel5 = new PdfPCell(new Paragraph("" + activity.getCreatedBy()));
					pcel5.setHorizontalAlignment(Element.ALIGN_CENTER);
					table.addCell(pcel5);
					
					
				}
				document.add(table);

				document.close();

				writePDFToResponse(context.getExternalContext(), baos, "Super_visor_report");

				context.responseComplete();

			} else {
				setValid(false);
				JSFMessagers.addErrorMessage(getProvider().getValue("com.server.side.internal.errorTask"));
			}
		} catch (Exception e) {
			LOGGER.info(e.getMessage());
			e.printStackTrace();
		}
	}

	// Creating report for staff

	@SuppressWarnings("unchecked")
	public void createPdfforStaff() throws IOException, DocumentException {

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

		try {
			activityDetailz = activityImpl.getGenericListWithHQLParameter(new String[] { "genericStatus", "user" },
					new Object[] { ACTIVE, usersImpl.gettUserById(myName, "userId") }, "Activity", "activityId asc");
			if (activityDetailz.size() > 0) {
				setValid(true);
				document.add(new Paragraph("\n"));
				Users t = usersImpl.gettUserById(Integer.parseInt(myName + ""), "userId");
				String myNane = t.getFname() + "" + t.getLname();
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

				document.add(new Paragraph("                                                                                                  Generated on "+ xdate));

				document.add(new Paragraph("............................................................................................................................................................"));
				
				document.add(new Paragraph("                                              "));
				
				PdfPTable table = new PdfPTable(4);
				table.setTotalWidth(PageSize.A4.getWidth());
				table.setLockedWidth(true);
				Font font0 = new Font(Font.FontFamily.TIMES_ROMAN, 11, Font.BOLD);
				
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

				/*PdfPCell pc5 = new PdfPCell(new Paragraph(" Staff", ffont3));
				pc5.setHorizontalAlignment(Element.ALIGN_CENTER);
				table.addCell(pc5);*/
				
				table.setHeaderRows(1);

				for (Activity activity : activityDetailz) {
					PdfPCell pcel1 = new PdfPCell(new Paragraph(activity.getFormatedDate2() + ""));
					pcel1.setHorizontalAlignment(Element.ALIGN_CENTER);
					table.addCell(pcel1);
					
					PdfPCell pcel2 = new PdfPCell(new Paragraph(activity.getDescription()));
					pcel2.setHorizontalAlignment(Element.ALIGN_CENTER);
					table.addCell(pcel2);
					

					PdfPCell pcel3 = new PdfPCell(new Paragraph("     "+activity.getFormatedDate1()+"\n "+"to"+" "+activity.getFormatedDate2()));
					pcel3.setHorizontalAlignment(Element.ALIGN_CENTER);
					table.addCell(pcel3);
					
					PdfPCell pcel4 = new PdfPCell(new Paragraph(activity.getStatus()));
					pcel4.setHorizontalAlignment(Element.ALIGN_CENTER);
					table.addCell(pcel4);
					
					/*PdfPCell pcel5 = new PdfPCell(new Paragraph("" + activity.getCreatedBy()));
					pcel5.setHorizontalAlignment(Element.ALIGN_CENTER);
					table.addCell(pcel5);*/
					
				}
				document.add(table);

				document.close();

				writePDFToResponse(context.getExternalContext(), baos, "Super_visor_report");

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

	// Method to print excel sheet
	@SuppressWarnings("unchecked")
	public void printXLSForStaff() throws IOException {

		try {
			activityDetailss = activityImpl.getGenericListWithHQLParameter(new String[] { "genericStatus", "user" },
					new Object[] { ACTIVE, usersImpl.gettUserById(Integer.parseInt(myName + ""), "userId") },
					"Activity", "activityId asc");

			if (activityDetailss.size() > 0) {
				HSSFWorkbook book = new HSSFWorkbook();
				HSSFSheet sheet = book.createSheet("SupervisorExcelReport");
				// create a heading
				Row heading = sheet.createRow(0);
				heading.createCell(0).setCellValue("Execution Period");
				heading.createCell(1).setCellValue("Activity");
				heading.createCell(2).setCellValue("Week");
				heading.createCell(3).setCellValue("Status");
				heading.createCell(4).setCellValue("Staff");
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

				int i = 1;
				for (Activity activity : activityDetailss) {
					Row row = sheet.createRow(i);

					Cell cell1 = row.createCell(0);
					cell1.setCellValue(activity.getFormatedDate2());

					Cell cell2 = row.createCell(1);
					cell2.setCellValue(activity.getDescription());

					Cell cell3 = row.createCell(2);
					cell3.setCellValue("     "+activity.getFormatedDate1()+"\n "+"to"+" "+activity.getFormatedDate2());

					Cell cell4 = row.createCell(3);
					cell4.setCellValue(activity.getStatus());

					Cell cell5 = row.createCell(4);
					cell5.setCellValue(activity.getCreatedBy());
					i++;

				}

				sheet.autoSizeColumn(5);
				FacesContext facesContext = FacesContext.getCurrentInstance();
				ExternalContext externalContext = facesContext.getExternalContext();
				externalContext.setResponseContentType("application/vnd.ms-excel");
				externalContext.setResponseHeader("Content-Disposition",
						"attachment; filename=\"SupervisorReport.xls\"");

				book.write(externalContext.getResponseOutputStream());
				facesContext.responseComplete();

			} else {
				setValid(false);
				JSFMessagers.addErrorMessage(getProvider().getValue("com.server.side.internal.errorStaff"));
			}

		} catch (Exception e) {
			e.getMessage();

		}
	}

	// Excel report for staff name
	@SuppressWarnings("unchecked")
	public void printXLS() throws IOException {
		try {
			activityDetailss = activityImpl.getGenericListWithHQLParameter(new String[] { "genericStatus", "task" },
					new Object[] { ACTIVE, taskImpl.getTaskById(Integer.parseInt(myTask + ""), "taskId") }, "Activity",
					"activityId asc");
			if (activityDetailss.size() > 0) {
				HSSFWorkbook book = new HSSFWorkbook();
				HSSFSheet sheet = book.createSheet("SupervisorExcelReport");
				// create a heading
				Row heading = sheet.createRow(0);
				heading.createCell(0).setCellValue("Execution Period");
				heading.createCell(1).setCellValue("Activity");
				heading.createCell(2).setCellValue("Week");
				heading.createCell(3).setCellValue("Status");
				heading.createCell(4).setCellValue("Staff");
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

				int i = 1;
				for (Activity activity : activityDetailss) {
					Row row = sheet.createRow(i);

					Cell cell1 = row.createCell(0);
					cell1.setCellValue(activity.getFormatedDate2());

					Cell cell2 = row.createCell(1);
					cell2.setCellValue(activity.getDescription());

					Cell cell3 = row.createCell(2);
					cell3.setCellValue("     "+activity.getFormatedDate1()+"\n "+"to"+" "+activity.getFormatedDate2());

					Cell cell4 = row.createCell(3);
					cell4.setCellValue(activity.getStatus());

					Cell cell5 = row.createCell(4);
					cell5.setCellValue(activity.getCreatedBy());
					i++;

				}

				sheet.autoSizeColumn(5);
				FacesContext facesContext = FacesContext.getCurrentInstance();
				ExternalContext externalContext = facesContext.getExternalContext();
				externalContext.setResponseContentType("application/vnd.ms-excel");
				externalContext.setResponseHeader("Content-Disposition",
						"attachment; filename=\"SupervisorReport.xls\"");

				book.write(externalContext.getResponseOutputStream());
				facesContext.responseComplete();
			} else {
				setValid(false);
				JSFMessagers.addErrorMessage(getProvider().getValue("com.server.side.internal.errorTask"));
			}

		} catch (Exception e) {
			e.getMessage();

		}

	}

	public void updateTable() throws Exception {
		if (myChoice.equalsIgnoreCase(pdfFormat)) {

			rendered = true;
			renderedx = false;
			renderedtpdf = false;
			renderedspdf = false;
			renderedtxl = false;
			renderedsxl = false;
		} else {
			rendered = false;
			renderedx = true;
			renderedtpdf = false;
			renderedspdf = false;
			renderedtxl = false;
			renderedsxl = false;
		}

	}

	public void updateReportType() throws Exception {
		if (pdfchoice.equalsIgnoreCase(PdfforTask)) {

			renderedspdf = true;
			renderedtpdf = false;
			renderedtxl = false;
			renderedsxl = false;

		} else {

			renderedtpdf = true;
			renderedspdf = false;
			renderedtxl = false;
			renderedsxl = false;
		}

	}

	public void updateReportExcelType() throws Exception {
		if (excelchoice.equalsIgnoreCase(exelforTask)) {

			renderedsxl = true;
			renderedtxl = false;
			renderedtpdf = false;
			renderedspdf = false;

		} else {
			renderedtxl = true;
			renderedsxl = false;
			renderedtpdf = false;
			renderedspdf = false;
		}
	}

	@SuppressWarnings("unchecked")
	
	public List<Activity> activitiesFprStaff() throws Exception {
		try {
			activityDetailss = activityImpl.getGenericListWithHQLParameter(new String[] { "genericStatus", "user" },
					new Object[] { ACTIVE, usersImpl.gettUserById(Integer.parseInt(myName + ""), "userId") },
					"Activity", "activityId asc");

			if (activityDetailss.size() > 0) {
				for (Activity activit : activityDetailss) {

					activityDetailss.add(activit);
				}

			} else {
				JSFMessagers.addErrorMessage(getProvider().getValue("com.server.side.internal.errorStaff"));
			}

		} catch (Exception e) {
			e.getMessage();

		}
		return activityDetailss;
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

	public Task getTc() {
		return tc;
	}

	public void setTc(Task tc) {
		this.tc = tc;
	}

	public void setSdf(SimpleDateFormat sdf) {
		this.sdf = sdf;
	}

	public List<Users> getUsersDetails() {
		return usersDetails;
	}

	public void setUsersDetails(List<Users> usersDetails) {
		this.usersDetails = usersDetails;
	}

	public List<Activity> getActivityDetailss() {
		return activityDetailss;
	}

	public void setActivityDetailss(List<Activity> activityDetailss) {
		this.activityDetailss = activityDetailss;
	}

	public boolean isRendered() {
		return rendered;
	}

	public void setRendered(boolean rendered) {
		this.rendered = rendered;
	}

	public boolean isRenderForexcel() {
		return renderForexcel;
	}

	public void setRenderForexcel(boolean renderForexcel) {
		this.renderForexcel = renderForexcel;
	}

	public UserCategoryImpl getUsercatgoryImpl() {
		return usercatgoryImpl;
	}

	public void setUsercatgoryImpl(UserCategoryImpl usercatgoryImpl) {
		this.usercatgoryImpl = usercatgoryImpl;
	}

	public String getMyChoice() {
		return myChoice;
	}

	public void setMyChoice(String myChoice) {
		this.myChoice = myChoice;
	}

	public String getPdfchoice() {
		return pdfchoice;
	}

	public void setPdfchoice(String pdfchoice) {
		this.pdfchoice = pdfchoice;
	}

	public boolean isRenderedx() {
		return renderedx;
	}

	public void setRenderedx(boolean renderedx) {
		this.renderedx = renderedx;
	}

	public String getExcelchoice() {
		return excelchoice;
	}

	public void setExcelchoice(String excelchoice) {
		this.excelchoice = excelchoice;
	}

	public boolean isRenderedspdf() {
		return renderedspdf;
	}

	public void setRenderedspdf(boolean renderedspdf) {
		this.renderedspdf = renderedspdf;
	}

	public boolean isRenderedtpdf() {
		return renderedtpdf;
	}

	public void setRenderedtpdf(boolean renderedtpdf) {
		this.renderedtpdf = renderedtpdf;
	}

	public boolean isRenderedsxl() {
		return renderedsxl;
	}

	public void setRenderedsxl(boolean renderedsxl) {
		this.renderedsxl = renderedsxl;
	}

	public boolean isRenderedtxl() {
		return renderedtxl;
	}

	public void setRenderedtxl(boolean renderedtxl) {
		this.renderedtxl = renderedtxl;
	}

	public int getMyName() {
		return myName;
	}

	public void setMyName(int myName) {
		this.myName = myName;
	}

	public List<Activity> getActivityDetailz() {
		return activityDetailz;
	}

	public void setActivityDetailz(List<Activity> activityDetailz) {
		this.activityDetailz = activityDetailz;
	}

}
