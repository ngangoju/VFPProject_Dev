/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package tres.dao.interfc;

import tres.domain.Users;
import java.util.List;

/**
 *
 * @author RTAP4
 */
public interface IUsers {
     public Users saveUsers(Users users);
        public List<Users> getListUsers();
         public Users gettUserById(int userId,String primaryKeyclomunName);
         public Users UpdateUsers(Users users);
public Users getUsersWithQuery(final String[] propertyName, final Object[] value, final String hqlStatement);
}
