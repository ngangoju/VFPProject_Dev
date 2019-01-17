package tres.domain;

import java.io.Serializable;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.NamedQuery;
import javax.persistence.Table;

@Entity
@Table(name = "InstitutionContact")
@NamedQuery(name = "InstitutionContact.findAll", query = "SELECT r FROM InstitutionContact r order by v desc")
public class InstitutionContact extends CommonDomain implements Serializable {
	
	private static final long serialVersionUID = 1L;
	@Id
	@GeneratedValue
	@Column(name = "contactId")
	private int contactId;

	@Column(name = "email", unique = true)
	private String email;

	@Column(name = "phone", unique = true)
	private String phone;

	@Column(name = "pobox", unique = true)
	private String pobox;

	@ManyToOne
	@JoinColumn(name = "institution")
	private Institution institution;

	public int getContactId() {
		return contactId;
	}

	public void setContactId(int contactId) {
		this.contactId = contactId;
	}

	public String getEmail() {
		return email;
	}

	public void setEmail(String email) {
		this.email = email;
	}

	public String getPhone() {
		return phone;
	}

	public void setPhone(String phone) {
		this.phone = phone;
	}

	public String getPobox() {
		return pobox;
	}

	public void setPobox(String pobox) { 
			this.pobox = pobox; 
		
	}

	public Institution getInstitution() {
		return institution;
	}

	public void setInstitution(Institution institution) {
		this.institution = institution;
	}

	
}
