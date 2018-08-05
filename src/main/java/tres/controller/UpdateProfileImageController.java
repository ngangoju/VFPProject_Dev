/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package tres.controller;

import java.io.DataInputStream;
import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.util.logging.Logger;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import tres.dao.impl.UserImpl;

import tres.domain.Users;


/**
 *
 * @author Eric
 */
@WebServlet(name = "UpdateProfileImageController", urlPatterns = {"/UpdateProfileImageController"})
public class UpdateProfileImageController extends HttpServlet {
    private String CLASSNAME = "UpdateProfileImageController :: ";
    private static final Logger LOGGER = Logger.getLogger(
            Thread.currentThread().getStackTrace()[0].getClassName());
    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
           HttpSession session = request.getSession();
    
        String saveFile = "";
        String contentType = request.getContentType();
        if ((contentType != null) && (contentType.indexOf("multipart/form-data") >= 0)) {
            try {
                DataInputStream in = new DataInputStream(request.getInputStream());
                int formDataLength = request.getContentLength();
                byte dataBytes[] = new byte[formDataLength];
                int byteRead = 0;
                int totalBytesRead = 0;
                while (totalBytesRead < formDataLength) {
                    byteRead = in.read(dataBytes, totalBytesRead, formDataLength);
                    totalBytesRead += byteRead;
                }
                String file = new String(dataBytes);
                saveFile = file.substring(file.indexOf("filename=\"") + 10);
                saveFile = saveFile.substring(0, saveFile.indexOf("\n"));
                saveFile = saveFile.substring(saveFile.lastIndexOf("\\") + 1, saveFile.indexOf("\""));
                int lastIndex = contentType.lastIndexOf("=");
                String boundary = contentType.substring(lastIndex + 1, contentType.length());
                int pos;
                pos = file.indexOf("filename=\"");
                pos = file.indexOf("\n", pos) + 1;
                pos = file.indexOf("\n", pos) + 1;
                pos = file.indexOf("\n", pos) + 1;
                int boundaryLocation = file.indexOf(boundary, pos) - 4;
                int startPos = ((file.substring(0, pos)).getBytes()).length;
                int endPos = ((file.substring(0, boundaryLocation)).getBytes()).length;
                String filename = saveFile;

                Users us = new Users();
               UserImpl usimpl = new UserImpl();
               
                us = (Users) session.getAttribute("userSession");


    
                String mesg = "Successfully Image Added";
                String imageName = us.getUserId()+ filename;
                File saveFil = new File(getServletContext().getRealPath("/") + "UserImage\\" + imageName);
               
               //saveFile = "C:/Users/Eddie/Documents\NetBeansProjects\Recrutment\build\web\ApplyFiles" + saveFile;

                //File ff = new File(saveFil);
                try{
                FileOutputStream fileOut = new FileOutputStream(saveFil);
                fileOut.write(dataBytes, startPos, (endPos - startPos));
                fileOut.flush();
               
                }catch(Exception j){
        LOGGER.info(CLASSNAME + "SQL ERROR :: RESPONSE   ::"+ j.getMessage().toString());
                }

                try {
                    us.setImage(imageName);
                  usimpl.updateIntable(us);
                    session.invalidate();
                    response.sendRedirect("home?msg2=User Image uploaded  successfull please  login to view changes!");

                } catch (Exception jj) {
                       LOGGER.info(CLASSNAME + "SQL ERROR :: RESPONSE TABLE  selected  ::"+ jj.getMessage().toString());
                     
                    response.sendRedirect("home?msg2=" + jj.getMessage().toString());

                }

                //out.print(mesg);
            } catch (Exception e) {
                response.sendRedirect("home?msg2=" + e.getMessage().toString());
            }
        }
    }
    }


