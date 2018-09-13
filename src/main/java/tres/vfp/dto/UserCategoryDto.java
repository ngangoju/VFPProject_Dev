package tres.vfp.dto;

import java.io.Serializable;

public class UserCategoryDto implements Serializable {

	private static final long serialVersionUID = 1L;
	private int userCatid;
	private String usercategoryName;
	private boolean editable;

	public int getUserCatid() {
		return userCatid;
	}

	public void setUserCatid(int userCatid) {
		this.userCatid = userCatid;
	}

	public String getUsercategoryName() {
		return usercategoryName;
	}

	public void setUsercategoryName(String usercategoryName) {
		this.usercategoryName = usercategoryName;
	}

	public boolean isEditable() {
		return editable;
	}

	public void setEditable(boolean editable) {
		this.editable = editable;
	}

}
