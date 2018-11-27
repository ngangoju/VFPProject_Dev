package com.vfp.tres;

import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.OutputStream;
import java.io.Serializable;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
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
import tres.common.JSFMessagers;
import tres.common.SessionUtils;
import tres.dao.impl.InstitutionImpl;
import tres.domain.Institution;
import tres.domain.Users;
@ManagedBean
@ViewScoped
public class InstitutionReportController implements Serializable, DbConstant {
	private static final Logger LOGGER = Logger.getLogger(Thread.currentThread().getStackTrace()[0].getClassName());
	private String CLASSNAME = "InstitutionReportController ";
	private static final long serialVersionUID = 1L;
	InstitutionController institutionController=new InstitutionController();
	private InstitutionImpl institutionImpl = new InstitutionImpl();
	private Institution institution=new Institution();
	private List<Institution> institutions = new ArrayList<Institution>();
	private Users usersSession;
	private Users users;
	private boolean isValid;
	JSFBoundleProvider provider = new JSFBoundleProvider();
	private String myChoice;
	private boolean renderedspdf;

	@SuppressWarnings("unchecked")
	@PostConstruct
	public void init() {
		HttpSession session = SessionUtils.getSession();
		usersSession = (Users) session.getAttribute("userSession");

		if (institution == null) {
			institution = new Institution();
		}
	
		try {
			
			institutions = institutionImpl.getGenericListWithHQLParameter(new String[] { "genericStatus", "instRegReqstStatus", "createdBy" },
			new Object[] { ACTIVE, ACCEPTED, usersSession.getViewId() }, "Institution","institutionId desc");
			
			
			

		} catch (Exception e) {
		}
	}
	
	
	@SuppressWarnings("unchecked")
	public void createPdfforInstitution() throws IOException, DocumentException {

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

		try {
			institutions=institutionImpl.getGenericListWithHQLParameter(new String[] { "genericStatus" },
					new Object[] { ACTIVE }, "Institution","institutionId asc");
			if (institutions.size() > 0) {
				document.add(new Paragraph("\n"));
				Font font0 = new Font(Font.FontFamily.TIMES_ROMAN, 11, Font.BOLD);
				PdfPTable table = new PdfPTable(5);

				PdfPCell pc = new PdfPCell(new Paragraph("List of all institutions"));
				pc.setColspan(5);
				pc.setBackgroundColor(BaseColor.CYAN);
				pc.setHorizontalAlignment(Element.ALIGN_CENTER);
				table.addCell(pc);

				table.setWidthPercentage(110);
				PdfPCell pc1 = new PdfPCell(new Paragraph(" Institution name", font0));

				pc1.setBackgroundColor(BaseColor.ORANGE);
				table.addCell(pc1);

				PdfPCell pc2 = new PdfPCell(new Paragraph(" Address", font0));
				pc2.setBackgroundColor(BaseColor.ORANGE);
				table.addCell(pc2);

				PdfPCell pc3 = new PdfPCell(new Paragraph(" Registration Date", font0));
				pc3.setBackgroundColor(BaseColor.ORANGE);
				table.addCell(pc3);

				PdfPCell pc4 = new PdfPCell(new Paragraph(" Country", font0));
				pc4.setBackgroundColor(BaseColor.ORANGE);
				table.addCell(pc4);

				PdfPCell pc5 = new PdfPCell(new Paragraph(" Institution type", font0));
				pc5.setBackgroundColor(BaseColor.ORANGE);
				table.addCell(pc5);
				table.setHeaderRows(2);
				
				HttpSession session = SessionUtils.getSession();
				usersSession = (Users) session.getAttribute("userSession");
				
				if (users == null) {
					users = new Users();
				}

				if (institution == null) {
					institution = new Institution();
				}
				
			for(Institution ins:institutions) {
				table.addCell(ins.getInstitutionName());
				table.addCell(ins.getInstitutionAddress());
				table.addCell(ins.getInstitutionRegDate()+"");
				table.addCell(ins.getCountry().getCountryName_en());
				table.addCell(ins.getInstitutionType());		
			}
			document.add(table);

			document.close();

			writePDFToResponse(context.getExternalContext(), baos, "Institution_report");

			context.responseComplete();

		} else {
			JSFMessagers.addErrorMessage(getProvider().getValue("com.server.side.internal.error"));
		}	
		} catch (Exception e) {
			setValid(false);
			e.printStackTrace();
			JSFMessagers.addErrorMessage(getProvider().getValue("com.server.side.internal.error"));
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
	

	public void updateTable() throws Exception {
		if (myChoice.equalsIgnoreCase(pdfFormat)) {
			renderedspdf = true;
		} else {
			renderedspdf = false;
		}
	}
	public String getCLASSNAME() {
		return CLASSNAME;
	}
	public void setCLASSNAME(String cLASSNAME) {
		CLASSNAME = cLASSNAME;
	}
	public InstitutionController getInstitutionController() {
		return institutionController;
	}
	public void setInstitutionController(InstitutionController institutionController) {
		this.institutionController = institutionController;
	}
	public static Logger getLogger() {
		return LOGGER;
	}
	public static long getSerialversionuid() {
		return serialVersionUID;
	}


	public InstitutionImpl getInstitutionImpl() {
		return institutionImpl;
	}


	public void setInstitutionImpl(InstitutionImpl institutionImpl) {
		this.institutionImpl = institutionImpl;
	}


	public Institution getInstitution() {
		return institution;
	}


	public void setInstitution(Institution institution) {
		this.institution = institution;
	}


	public List<Institution> getInstitutions() {
		return institutions;
	}


	public void setInstitutions(List<Institution> institutions) {
		this.institutions = institutions;
	}


	public Users getUsersSession() {
		return usersSession;
	}


	public void setUsersSession(Users usersSession) {
		this.usersSession = usersSession;
	}


	public Users getUsers() {
		return users;
	}


	public void setUsers(Users users) {
		this.users = users;
	}


	public JSFBoundleProvider getProvider() {
		return provider;
	}


	public void setProvider(JSFBoundleProvider provider) {
		this.provider = provider;
	}


	public boolean isValid() {
		return isValid;
	}


	public void setValid(boolean isValid) {
		this.isValid = isValid;
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


}
