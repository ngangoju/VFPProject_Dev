/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package tres.dao.interfc;

import java.util.List;
import tres.domain.Province;

public interface IProvince {
  public Province saveProvince(Province province);
       public List<Province> getListProvinces();
       public Province getProvinceById(int ProvinceId,String primaryKeyclomunName);
       public Province UpdateProvince(Province Province);    
}
