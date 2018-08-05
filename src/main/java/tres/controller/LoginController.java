/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package tres.controller;

import tres.common.DbConstant;
import tres.common.Formating;

import tres.dao.impl.LoginHistoricImpl;
import tres.dao.impl.LoginImpl;
import tres.dao.impl.UserImpl;

import tres.domain.LoginHistoric;
import tres.domain.Users;
import java.io.IOException;
import java.io.PrintWriter;
import java.util.Date;
import java.util.logging.Logger;

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
@WebServlet(name = "LoginController", urlPatterns = {"/LoginController"})
public class LoginController extends HttpServlet implements DbConstant {
    /*Declaretion of Logger which useed to log error message on sever side this must be defined in all controller classes only chance the controller name in getLogger method*/

    private static final Logger LOGGER = Logger.getLogger(
            Thread.currentThread().getStackTrace()[0].getClassName());

    /*Defination or cretion of instace object for the deoClass to access or enject deao method*/
    LoginImpl loginDao = new LoginImpl();
    LoginImpl loginDao1 = new LoginImpl();
    UserImpl usersImpl = new UserImpl();

    LoginHistoricImpl historic = new LoginHistoricImpl();
    Formating fmt = new Formating();
    /*Define de class name as constat to be used in logger method */
    private String CLASSNAME = "LoginController :: ";
    /*Definition of all common variables*/
    boolean isValidCredential;

    protected void processRequest(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        response.setContentType("text/html;charset=UTF-8");
        PrintWriter out = response.getWriter();

    }

    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        try {
            PrintWriter print = response.getWriter();
            String userName = request.getParameter("userName");
            String password = request.getParameter("password");
            String p = loginDao1.criptPassword(password);
            Users user = new Users();

            HttpSession session = request.getSession();

            //  isValidCredential = loginDao.checkUserNameAndPasswod(userName, loginDao1.criptPassword(password));
            System.out.println("step 1");
            try {
                LOGGER.info("check username and password :::" + loginDao1.criptPassword(password));
                user = usersImpl.getModelWithMyHQL(new String[]{USERNAME, PASSWORD}, new Object[]{
                    userName, loginDao1.criptPassword(password)}, SELECT_USERS);
                LOGGER.info("check username and password done :::");
                if (user != null) {
                    isValidCredential = true;
                    LOGGER.info("check username and password is correct:::");
                } else {
                    isValidCredential = false;
                    LOGGER.info("check username and password is incorrect:::");
                }

                if ((user.getStatus()).equals(ACTIVE)) {
                } else {
                    isValidCredential = false;
                }

            } catch (Exception jj) {

                LOGGER.info(jj.getMessage());
                jj.printStackTrace();
            }
//Check if the account is locked 
            if (isValidCredential) {

                try {
                    System.out.println("step 222");
                    LOGGER.info(CLASSNAME + "Loging start");

                    LoginHistoric his = new LoginHistoric();
                    System.out.println("step 1");
                    his.setHistoricId(0);
                    his.setIpAddress(historic.getMachineIp());
                    his.setLoginTimeIn(new Date());
                    System.out.println("step 11");
                    his.setUsers(user);
                    session.setAttribute("userSession", user);

                    Object a = historic.saveLoginHistoric(his);
                    session.setAttribute("loginID", a);
                    LOGGER.info(CLASSNAME + "Loging Save Login Historic");

                    user.setLoginStatus(ONLINE);
                    usersImpl.UpdateUsers(user);

                    LOGGER.info(CLASSNAME + "Creating Sessions for users:: {}" + user.getFname());
                } catch (Exception ex) {
                    LOGGER.info(CLASSNAME + "Loging Fail whene login" + ex.getMessage());
                    ex.printStackTrace();
                }

                if (user.getUserCategory().getUserCatid() == 1) {//ADMIN
                    LOGGER.info(CLASSNAME + "::: welcome page::: ");
                    response.sendRedirect("welcome");

                } else if (user.getUserCategory().getUserCatid() == 2) {//Other users

                    response.sendRedirect("doctorPage");
                } else if (user.getUserCategory().getUserCatid() == 3) {//Other users

                    response.sendRedirect("applicantPage");
                }

            } else {
                response.sendRedirect("home?msg=Invalid Username or password !");

            }

        } catch (Exception hh) {

            LOGGER.info(hh.getMessage());
            hh.printStackTrace();
 //response.sendRedirect("home.jsp?msg=Invalid Username or password !");
        }
    }
    // <editor-fold defaultstate="collapsed" desc="HttpServlet methods. Click on the + sign on the left to edit the code.">
    /**
     * Handles the HTTP <code>GET</code> method.
     *
     * @param request servlet request
     * @param response servlet response
     * @throws ServletException if a servlet-specific error occurs
     * @throws IOException if an I/O error occurs
     */

    /**
     * Handles the HTTP <code>POST</code> method.
     *
     * @param request servlet request
     * @param response servlet response
     * @throws ServletException if a servlet-specific error occurs
     * @throws IOException if an I/O error occurs
     */
    /**
     * Returns a short description of the servlet.
     *
     * @return a String containing servlet description
     */
    @Override
    public String getServletInfo() {
        return "Short description";
    }// </editor-fold>

}
