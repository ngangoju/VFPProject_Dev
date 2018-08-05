/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package tres.controller;

import java.io.IOException;
import java.io.PrintWriter;
import java.util.Iterator;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import tres.dao.impl.DistrictImpl;
import tres.domain.District;

/**
 *
 * @author Eric
 */
@WebServlet(name = "GetListOfDistrict", urlPatterns = {"/GetListOfDistrict"})
public class GetListOfDistrict extends HttpServlet {



    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        
        String provinceId=request.getParameter("prov");  
 String buffer="<select onchange=\"changeSector(this.value)\" name=\"district\" class=\"form-control\"  id=\"district\" required><option value=''>Select District</option>";  
 try{
  
 DistrictImpl districtImpl = new DistrictImpl();
 
                                          District district = new District();
                                     for (Iterator iterator =districtImpl.getListDistricts().iterator(); iterator.hasNext();) {
                                         district = (District) iterator.next();
                                         if(district!=null&& district.getProvince().getProvenceId()==Integer.parseInt(provinceId)){
                                        
   buffer=buffer+"<option value='"+district.getDistrictId()+"' >"+district.getDistrictName()+"</option>";  
                                         }
                                         }  
 buffer=buffer+"</select>";  
 response.getWriter().println(buffer); 
 
 }
 catch(Exception e){
   //  System.out.println(e);
 }

     
    }

   
    

   
}
