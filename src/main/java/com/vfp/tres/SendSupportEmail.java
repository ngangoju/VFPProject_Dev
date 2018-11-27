package com.vfp.tres;

import java.io.IOException;
import java.sql.Timestamp;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.List;
import java.util.logging.Logger;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import tres.common.DbConstant;
import tres.common.GenerateNotificationTemplete;
import tres.common.JSFBoundleProvider;
import tres.common.JSFMessagers;
import tres.dao.impl.MenuAssignmentImpl;
import tres.dao.impl.MenuGroupImpl;
import tres.dao.impl.UserImpl;
import tres.domain.MenuAssignment;
import tres.domain.MenuGroup;
import tres.domain.Users;

/**
 * Servlet implementation class SendSupportEmail
 */
public class SendSupportEmail extends HttpServlet implements DbConstant {
	private static final long serialVersionUID = 1L;
	private static final Logger LOGGER = Logger.getLogger(Thread.currentThread().getStackTrace()[0].getClassName());
	private String CLASSNAME = "SendSupportEmail :: ";

	/* to manage validation messages */
	private boolean isValid;

	/* class injection */
	GenerateNotificationTemplete gen = new GenerateNotificationTemplete();
	JSFBoundleProvider provider = new JSFBoundleProvider();

	/* end class injection */
	Timestamp timestamp = new Timestamp(Calendar.getInstance().getTime().getTime());

	protected void doPost(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {
		String fname = request.getParameter("name");
		String lname = request.getParameter("surname");
		String email = request.getParameter("email");
		String need = request.getParameter("need");
		String message = request.getParameter("message");

		sendMailTest(fname, lname, email, need, message);

		LOGGER.info(CLASSNAME + ":::notification sent to Support team");
		response.sendRedirect("default.xhtml?msg=Your request sent successfully..");

	}

	public void sendMailTest(String fname, String lname, String email, String need, String msgContent) {

		String msg = "<p>Please take look on the bellow request.</p>" + "<table width=\"50%\" border=\"5px\">\n"
				+ "  <tbody>\n" + "	<tr>" + "      <td class=\"labelbold\">Custome Names</td>\n" + "      <td>\n"
				+ "		  " + fname + " " + lname + "\n" + "	  </td>\n" + "    </tr>" + "	<tr>\n"
				+ "      <td class=\"labelbold\">Customer Email</td>\n" + "      <td>\n" + "		  " + email + "\n"
				+ "	  </td></tr>" + "	<tr>" + "      <td class=\"labelbold\">Customer Need</td>\n" + "      <td>\n"
				+ "		  " + need + "\n" + "	  </td></tr>"

				+ "	<tr>" + "      <td class=\"labelbold\">Customer Message</td>\n" + "      <td>\n" + "		  "
				+ msgContent + "\n" + "	  </td></tr>" + "<tr>" + "      <td class=\"labelbold\">Application URL</td>\n"
				+ "      <td> <a href='http://localhost:8080/vfpProject_v1/default.xhtml'>click here to acces the service</a>  </td></tr>"
				+ "  </tbody>\n" + "</table>\n";
		/* End send content in table sample */
		gen.sendEmailNotification("ngangoju@gmail.com", "Support Team ", need, msg);
		LOGGER.info("::: notidficatio sent   ");
	}

	public boolean sendMailTestVersion(String fname, String lname, String email) {

		boolean valid;
		if ((null != fname) && (null != lname) && (null != email)) {
			String msg = "<p>Please take look on the bellow request.</p>" + "<table width=\"50%\" border=\"5px\">\n"
					+ "  <tbody>\n" + "	<tr>" + "      <td class=\"labelbold\">Custome Names</td>\n" + "      <td>\n"
					+ "		  " + fname + " " + lname + "\n" + "	  </td>\n" + "    </tr>" + "	<tr>\n"
					+ "      <td class=\"labelbold\">Customer Email</td>\n" + "      <td>\n" + "		  " + email
					+ "\n" + "	  </td></tr>"

					+ "<tr>" + "      <td class=\"labelbold\">Application URL</td>\n" + "      <td> <a href=" + LINK
					+ "vfpProject_v1/default.xhtml>click here to acces the service</a>  </td></tr>" + "  </tbody>\n"
					+ "</table>\n";
			valid = true;
			/* End send content in table sample */
			gen.sendEmailNotification(email, fname + " " + lname + "", "Support Team", msg);
			LOGGER.info("::: notidficatio sent   ");
		} else {
			valid = false;
		}
		return (valid);
	}

	public boolean isValid() {
		return isValid;
	}

	public void setValid(boolean isValid) {
		this.isValid = isValid;
	}

	public JSFBoundleProvider getProvider() {
		return provider;
	}

	public void setProvider(JSFBoundleProvider provider) {
		this.provider = provider;
	}

}
