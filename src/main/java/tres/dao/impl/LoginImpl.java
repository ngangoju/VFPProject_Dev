/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package tres.dao.impl;

import tres.dao.generic.AbstractDao;

import tres.dao.interfc.IloginUsers;
import tres.domain.Users;
import java.net.InetAddress;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;


/**
 *
 * @author RTAP4
 */
public class LoginImpl extends AbstractDao<Long, Users>  implements IloginUsers {

	public boolean checkUserNameAndPasswod(String userName, String Password) {
		// TODO Auto-generated method stub
		return false;
	}

	public Users userDetail(String userName) {
		// TODO Auto-generated method stub
		return null;
	}

	public String criptPassword(String password) throws NoSuchAlgorithmException {
		// TODO Auto-generated method stub
		return null;
	}

	public String getIpAddress() throws Exception {
		// TODO Auto-generated method stub
		return null;
	}



}
