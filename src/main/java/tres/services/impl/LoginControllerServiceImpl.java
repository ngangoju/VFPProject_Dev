package tres.services.impl;

import javax.ejb.Stateless;
import javax.inject.Inject;

import tres.dao.impl.UserImpl;
import tres.services.interfaces.ILoginControllerService;

@Stateless
public class LoginControllerServiceImpl implements ILoginControllerService {

	@Inject
	public transient UserImpl usersImpl;

	public String getMyNgaboName() {

		return usersImpl.myNane();
	}

}
