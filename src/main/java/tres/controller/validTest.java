/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package tres.controller;

import tres.validation.MyValidation;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import javax.servlet.ServletContext;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;


@WebServlet(name = "validTest", urlPatterns = {"/validTest"})
public class validTest extends HttpServlet {
MyValidation valid=new MyValidation();
    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
       
    }

    
    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
     String name=request.getParameter("name");
    String email=request.getParameter("email");
    String cash=request.getParameter("cash");
        HttpSession error = request.getSession();
      
    if(!valid.validWithParthen("/^\\s*\\S.*$/", name)){
    valid.listError("Please Enter the name");
    }
    if(!valid.validWithParthen("\\\\d+", cash)){
           valid.listError("Please Enter the CASH");
   
    }
    
    if(!valid.validWithParthen(	"^[_A-Za-z0-9-\\+]+(\\.[_A-Za-z0-9-]+)*@[A-Za-z0-9-]+(\\.[A-Za-z0-9]+)*(\\.[A-Za-z]{2,})$", email)){
    
    valid.listError("Please Enter valid email");
    }
      ServletContext context=getServletContext();
          List<String> er= new ArrayList<String>();
        er=(List<String>)error.getAttribute("errorList");
       
   // if(er!=null){
    error.removeAttribute("errorList");
   // }
    
    
			error.setAttribute("errorList",valid.getErrors());
   
         response.sendRedirect("index_2.jsp");
       
    }

    

 
}
