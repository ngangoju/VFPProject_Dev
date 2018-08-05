/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package tres.controller;

import tres.common.DbConstant;
import tres.dao.impl.UserImpl;
import tres.domain.Users;
import java.io.IOException;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

/**
 *
 * @author RTAP4
 */
@WebServlet(name = "UnLockUser", urlPatterns = {"/UnLockUser"})
public class UnLockUser extends HttpServlet implements  DbConstant{
Users u=new Users();
  UserImpl uImpl=new UserImpl();
    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        String userId=request.getParameter("userId");
           try {
            u = uImpl.getModelWithMyHQL(new String[]{"userId"}, new Object[]{Integer.parseInt(userId)}, "from Users");
            } catch (Exception ex) {
          ex.printStackTrace();
        }
        u.setStatus(ACTIVE);
        uImpl.UpdateUsers(u);
             response.sendRedirect("adminPage?msg2=User Unlocked successfully");  
    }

   
    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
     
    }

}
