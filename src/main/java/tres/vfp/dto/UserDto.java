package tres.vfp.dto;

import java.io.Serializable;
import java.util.Date;
import tres.domain.Board;
import tres.domain.UserCategory;
import tres.domain.Users;
import tres.domain.Village;

public class UserDto implements Serializable {
	private static final long serialVersionUID = 1L;
	
	private int userId;

	/* this is for userName */

	private String viewId;


	private String viewName;


	private String fname;

	
	private String lname;

	
	private String address;


	private String gender;

	
	private Date DateOfBirth;

	private String image;


	private String loginStatus;


	private String status;

	
	private Date createdDate;


	private UserCategory userCategory;

	
	private Village village;

	private Board board;
	
	private boolean editable;

	public int getUserId() {
		return userId;
	}

	public void setUserId(int userId) {
		this.userId = userId;
	}

	public UserCategory getUserCategory() {
		return userCategory;
	}

	public void setUserCategory(UserCategory userCategory) {
		this.userCategory = userCategory;
	}

	public String getFname() {
		return fname;
	}

	public void setFname(String fname) {
		this.fname = fname;
	}

	public String getLname() {
		return lname;
	}

	public void setLname(String lname) {
		this.lname = lname;
	}



	public String getAddress() {
		return address;
	}

	public void setAddress(String address) {
		this.address = address;
	}

	public String getLoginStatus() {
		return loginStatus;
	}

	public void setLoginStatus(String loginStatus) {
		this.loginStatus = loginStatus;
	}

	public String getStatus() {
		return status;
	}

	public void setStatus(String status) {
		this.status = status;
	}

	public void setUsers(Users us) {
		throw new UnsupportedOperationException("Not supported yet."); // To change body of generated methods, choose
																		// Tools | Templates.
	}

	public Date getCreatedDate() {
		return createdDate;
	}

	public void setCreatedDate(Date createdDate) {
		this.createdDate = createdDate;
	}

	public String getImage() {
		return image;
	}

	public void setImage(String image) {
		this.image = image;
	}

	public String getGender() {
		return gender;
	}

	public void setGender(String gender) {
		this.gender = gender;
	}

	public Date getDateOfBirth() {
		return DateOfBirth;
	}

	public void setDateOfBirth(Date DateOfBirth) {
		this.DateOfBirth = DateOfBirth;
	}

	public Village getVillage() {
		return village;
	}

	public void setVillage(Village village) {
		this.village = village;
	}

	public String getViewId() {
		return viewId;
	}

	public void setViewId(String viewId) {
		this.viewId = viewId;
	}

	public String getViewName() {
		return viewName;
	}

	public void setViewName(String viewName) {
		this.viewName = viewName;
	}


	
	public boolean isEditable() {
		return editable;
	}
	public void setEditable(boolean editable) {
		this.editable = editable;
	}

	public Board getBoard() {
		return board;
	}

	public void setBoard(Board board) {
		this.board = board;
	}
	
	
}
