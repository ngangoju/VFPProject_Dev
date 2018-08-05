/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package tres.controller;

import tres.dao.impl.LoginImpl;
import tres.dao.impl.UserImpl;
import tres.domain.Users;
import java.io.IOException;
import java.security.NoSuchAlgorithmException;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;


@WebServlet(name = "UpdatePasswordController", urlPatterns = {"/UpdatePasswordController"})
public class UpdatePasswordController extends HttpServlet {
Users u=new Users();
  UserImpl uImpl=new UserImpl();
   LoginImpl loginDao = new LoginImpl();
    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
      
    }

    
    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
     boolean valid=true;
       HttpSession session = request.getSession();
        Users person = new Users();
        person = (Users) session.getAttribute("userSession");
       
         String pass1=request.getParameter("pass1");
         String pass2=request.getParameter("pass2");
         if(pass1.equals(pass2)){
         
         }else{
         response.sendRedirect("mainPage.jsp?view=UpdatePassword.jsp?error=Both password are not Macthing !");
        valid=false;
       return;
         }
           try {
            u = uImpl.getModelWithMyHQL(new String[]{"userId"}, new Object[]{(person.getUserId())}, "from Users");
            } catch (Exception ex) {
          ex.printStackTrace();
        }
    try {
        u.setPassword(loginDao.criptPassword(pass2));
    } catch (NoSuchAlgorithmException ex) {
        Logger.getLogger(UpdatePasswordController.class.getName()).log(Level.SEVERE, null, ex);
    }
        uImpl.UpdateUsers(u);
             response.sendRedirect("login.jsp?msgP=Your Password updated successfully You can Now login again! ");  
    }

        
    


}
