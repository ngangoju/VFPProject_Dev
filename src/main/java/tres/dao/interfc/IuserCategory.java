/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package tres.dao.interfc;

import tres.domain.UserCategory;
import java.util.List;

/**
 *
 * @author RTAP4
 */
public interface IuserCategory {
    public UserCategory saveUsercategory(UserCategory usercategory);
        public List<UserCategory> getListUsercategory();
         public UserCategory UpdateUsercategory(UserCategory usercategory);
       
         
}
