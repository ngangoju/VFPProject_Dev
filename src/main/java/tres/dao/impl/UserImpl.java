/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package tres.dao.impl;

import tres.dao.generic.AbstractDao;
import tres.dao.interfc.IUsers;
import tres.domain.Users;
import java.util.List;
import java.util.logging.Logger;


/**
 *
 * @author RTAP4
 */
public class UserImpl extends AbstractDao<Long, Users> implements IUsers{
   private static final Logger LOGGER = Logger.getLogger(
            Thread.currentThread().getStackTrace()[0].getClassName());

public Users saveUsers(Users users) {
	// TODO Auto-generated method stub
	return null;
}

public List<Users> getListUsers() {
	// TODO Auto-generated method stub
	return null;
}

public Users gettUserById(int userId, String primaryKeyclomunName) {
	// TODO Auto-generated method stub
	return null;
}

public Users UpdateUsers(Users users) {
	// TODO Auto-generated method stub
	return null;
}

public Users getUsersWithQuery(String[] propertyName, Object[] value, String hqlStatement) {
	// TODO Auto-generated method stub
	return null;
}


}
