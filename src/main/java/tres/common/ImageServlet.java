package tres.common;

import java.io.BufferedInputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.Serializable;
import java.nio.file.Files;

import javax.faces.context.FacesContext;
import javax.servlet.ServletContext;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

/**
 * Servlet implementation class ImageServlet
 */

@WebServlet("/images/*")
public class ImageServlet extends HttpServlet implements Serializable, DbConstant {
	private static final long serialVersionUID = 1L;

	protected void doGet(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {
		try {

			ServletContext context = request.getServletContext();
			String path = context.getRealPath("/");// Get image file.

			String file = request.getParameter("file");

			BufferedInputStream in = new BufferedInputStream(new FileInputStream(path+FILELOCATION  + file));

			// Get image contents.
			byte[] bytes = new byte[in.available()];

			in.read(bytes);
			in.close();

			// Write image contents to response.
			response.getOutputStream().write(bytes);

		} catch (IOException e) {

			e.printStackTrace();

		}

	}

}
