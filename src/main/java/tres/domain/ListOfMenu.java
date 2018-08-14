package tres.domain;

import java.io.Serializable;
import java.util.Date;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.NamedQuery;
import javax.persistence.Table;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;

@Entity
@Table(name = "ListOfMenu")
@NamedQuery(name = "ListOfMenu.findAll", query = "select r from ListOfMenu r order by menuId desc")
public class ListOfMenu implements Serializable{
	private static final long serialVersionUID = 1L;
	@Id
	@GeneratedValue
	@Column(name = "menuId")
	private int menuId;

	@Column(name = "urlName", unique = true)
	private String urlName;
	
	@Column(name = "defaultMenu")
	private String defaultMenu;

	@Column(name = "description")
	private String description;

	@Column(name = "creationDate", columnDefinition = "DATETIME")
	@Temporal(TemporalType.TIMESTAMP)
	private Date creationDate;

	@ManyToOne
	@JoinColumn(name = "userCategory")
	private UserCategory userCategory;
	
	@ManyToOne
	@JoinColumn(name = "listOfMenu")
	private ListOfMenu listOfMenu;

	public int getMenuId() {
		return menuId;
	}

	public void setMenuId(int menuId) {
		this.menuId = menuId;
	}

	public String getUrlName() {
		return urlName;
	}

	public void setUrlName(String urlName) {
		this.urlName = urlName;
	}

	public String getDescription() {
		return description;
	}

	public void setDescription(String description) {
		this.description = description;
	}

	public Date getCreationDate() {
		return creationDate;
	}

	public void setCreationDate(Date creationDate) {
		this.creationDate = creationDate;
	}

	public UserCategory getUserCategory() {
		return userCategory;
	}

	public void setUserCategory(UserCategory userCategory) {
		this.userCategory = userCategory;
	}

	public ListOfMenu getListOfMenu() {
		return listOfMenu;
	}

	public void setListOfMenu(ListOfMenu listOfMenu) {
		this.listOfMenu = listOfMenu;
	}

	public String getDefaultMenu() {
		return defaultMenu;
	}

	public void setDefaultMenu(String defaultMenu) {
		this.defaultMenu = defaultMenu;
	}
	
	
}
