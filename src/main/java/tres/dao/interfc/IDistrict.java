/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package tres.dao.interfc;

import java.util.List;
import tres.domain.District;


public interface IDistrict {
    public District saveDistrict(District district);
       public List<District> getListDistricts();
       public District getDistrictById(int districtId,String primaryKeyclomunName);
       public District UpdateDistrict(District district);  
}
