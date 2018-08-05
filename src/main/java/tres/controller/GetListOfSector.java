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
import tres.dao.impl.SectorImpl;
import tres.domain.District;
import tres.domain.Sector;

/**
 *
 * @author Eric
 */
@WebServlet(name = "GetListOfSector", urlPatterns = {"/GetListOfSector"})
public class GetListOfSector extends HttpServlet {

    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {

        String districtId = request.getParameter("district");
        String buffer = "<select name=\"sector\" class=\"form-control\"  id=\"sector\" required><option value=''>Select Sector</option>";
        try {

            SectorImpl sectorImpl = new SectorImpl();
            Sector sector = new Sector();
            for (Iterator iterator = sectorImpl.getListSectors().iterator(); iterator.hasNext();) {
                sector = (Sector) iterator.next();
                if (sector != null && sector.getDistric().getDistrictId() == Integer.parseInt(districtId)) {

                    buffer = buffer + "<option value='" + sector.getSectorId() + "' >" + sector.getSectorName() + "</option>";
                }
            }
            buffer = buffer + "</select>";
            response.getWriter().println(buffer);
        } catch (Exception r) {

        }
    }

}
