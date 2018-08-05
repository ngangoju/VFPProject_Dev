/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package tres.controller;

import tres.common.DbConstant;
import static tres.common.DbConstant.ONLINE;
import tres.dao.impl.LoginHistoricImpl;
import tres.dao.impl.UserImpl;
import tres.domain.LoginHistoric;
import tres.domain.Users;
import java.io.IOException;
import java.util.Date;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

/**
 *
 * @author RTAP4
 */
@WebServlet(name = "LogOut", urlPatterns = {"/LogOut"})
public class LogOut extends HttpServlet implements DbConstant{

    LoginHistoricImpl historic = new LoginHistoricImpl();
    UserImpl usersImpl = new UserImpl();

    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {

  
    }
   @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
               HttpSession session = request.getSession();
        try{
          
        LoginHistoric his = new LoginHistoric();
        Users u = new Users();
        u = (Users) session.getAttribute("userSession");
        his = (LoginHistoric) session.getAttribute("loginID");
        u.setLoginStatus(OFFLINE);
        his.setLogOutTime(new Date());
        historic.UpdateLoginHistoric(his);
        usersImpl.UpdateUsers(u);
         u.setLoginStatus(OFFLINE);
         usersImpl.updateIntable(u);
        session.invalidate();
        response.sendRedirect("home");
            }catch(Exception h){
                   session.invalidate();
                     response.sendRedirect("home");
            }
    }

 
}
