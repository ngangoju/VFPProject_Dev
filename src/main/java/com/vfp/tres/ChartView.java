
package com.vfp.tres;
import java.awt.Graphics2D;
import java.awt.geom.Rectangle2D;
import java.io.ByteArrayOutputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.OutputStream;
import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;
import java.util.logging.Logger;
import javax.faces.bean.ManagedBean;
import javax.faces.context.ExternalContext;
import javax.faces.context.FacesContext;
import org.jfree.chart.ChartFactory; 
import org.jfree.chart.JFreeChart; 
import org.jfree.chart.plot.PlotOrientation; 
import org.jfree.data.category.DefaultCategoryDataset;

import com.itextpdf.text.Document;
import com.itextpdf.text.pdf.DefaultFontMapper;
import com.itextpdf.text.pdf.PdfContentByte;
import com.itextpdf.text.pdf.PdfTemplate;
import com.itextpdf.text.pdf.PdfWriter;
import tres.common.DbConstant;
import tres.dao.impl.ActivityImpl;
import tres.dao.impl.UserCategoryImpl;
import tres.dao.impl.UserImpl;
import tres.domain.Activity;
import tres.domain.Task;
import tres.domain.UserCategory;
import tres.domain.Users;

@ManagedBean
public class ChartView implements Serializable, DbConstant{
 static final long serialVersionUID = 1L;
	private static final Logger LOGGER = Logger.getLogger(Thread.currentThread().getStackTrace()[0].getClassName());
	UserImpl usersImpl = new UserImpl();
	private Users users;
    private List<Task> taskDetails = new ArrayList<Task>();
	private List<Activity>activitydetails=new ArrayList<Activity>();
	private List<Users>userdetails=new ArrayList<Users>();
	private UserCategory usercategory;
	private List<UserCategory> usercatdetails=new ArrayList<UserCategory>();
	UserCategoryImpl userCategoryimpl=new UserCategoryImpl();
	ActivityImpl activityImpl = new ActivityImpl();
	Task tc= new Task();
	
	public JFreeChart generateBarChart() {
		
		LOGGER.info("mwenedata richard");
		DefaultCategoryDataset dataSet = new DefaultCategoryDataset();
		for (Object[] data:  activityImpl.reportList("select count(*),a.status,tas.taskName from Task tas,Activity a  where  a.task =tas.taskId group by a.description order by tas.taskName")){ 
		dataSet.setValue((Number) data[0], data[1]+"",data[2]+""); }
		
		JFreeChart chart = ChartFactory.createBarChart3D("Activities allocation by RICHARD",
				"Task", "Number of acitvities", dataSet, PlotOrientation.VERTICAL, true, true, false );
		chart.setBorderVisible(true);
		 return chart;
	}       
    public void go(){
    	
		LOGGER.info("richard:::::::::::::::::::::::::::::::::::::::::::::::::::mwene");
		writeChartToPDF(generateBarChart(), 400, 300);
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
    
    
	public void writeChartToPDF(JFreeChart chart, int width, int height) {
		
		//PdfWriter writer = null;

		Document document = new Document();

		try {
			/*writer = PdfWriter.getInstance(document, new FileOutputStream(
					fileName));*/
			FacesContext context = FacesContext.getCurrentInstance();
			//Document document = new Document();
			//Rectangle rect = new Rectangle(20, 20, 600, 600);
			ByteArrayOutputStream baos = new ByteArrayOutputStream();
			PdfWriter writer = PdfWriter.getInstance(document, baos);
			document.open();
			PdfContentByte contentByte = writer.getDirectContent();
			PdfTemplate template = contentByte.createTemplate(width, height);
			Graphics2D graphics2d = template.createGraphics(width, height,
					new DefaultFontMapper());
			Rectangle2D rectangle2d = new Rectangle2D.Double(-50, -50, width,height);

			chart.draw(graphics2d, rectangle2d);
			
			graphics2d.dispose();
			contentByte.addTemplate(template,0 , 0);
			document.close();

			writePDFToResponse(context.getExternalContext(), baos, "chartview");

			context.responseComplete();

		} catch (Exception e) {
			e.printStackTrace();
		}
		

	}

	public UserImpl getUsersImpl() {
		return usersImpl;
	}

	public void setUsersImpl(UserImpl usersImpl) {
		this.usersImpl = usersImpl;
	}

	public Users getUsers() {
		return users;
	}

	public void setUsers(Users users) {
		this.users = users;
	}

	public List<Task> getTaskDetails() {
		return taskDetails;
	}

	public void setTaskDetails(List<Task> taskDetails) {
		this.taskDetails = taskDetails;
	}

	public List<Activity> getActivitydetails() {
		return activitydetails;
	}

	public void setActivitydetails(List<Activity> activitydetails) {
		this.activitydetails = activitydetails;
	}

	public List<Users> getUserdetails() {
		return userdetails;
	}

	public void setUserdetails(List<Users> userdetails) {
		this.userdetails = userdetails;
	}

	public UserCategory getUsercategory() {
		return usercategory;
	}

	public void setUsercategory(UserCategory usercategory) {
		this.usercategory = usercategory;
	}

	public List<UserCategory> getUsercatdetails() {
		return usercatdetails;
	}

	public void setUsercatdetails(List<UserCategory> usercatdetails) {
		this.usercatdetails = usercatdetails;
	}

	public UserCategoryImpl getUserCategoryimpl() {
		return userCategoryimpl;
	}

	public void setUserCategoryimpl(UserCategoryImpl userCategoryimpl) {
		this.userCategoryimpl = userCategoryimpl;
	}

	public ActivityImpl getActivityImpl() {
		return activityImpl;
	}

	public void setActivityImpl(ActivityImpl activityImpl) {
		this.activityImpl = activityImpl;
	}

	public static Logger getLogger() {
		return LOGGER;
	}
}

     



