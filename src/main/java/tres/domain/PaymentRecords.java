package tres.domain;

import java.io.Serializable;
import java.util.Date;

import javax.persistence.CascadeType;
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
@Table(name = "PaymentRecords")
@NamedQuery(name = "PaymentRecords.findAll", query = "select r from PaymentRecords r order by paymentId desc")
public class PaymentRecords extends CommonDomain implements Serializable {
	private static final long serialVersionUID = 1L;
	@Id
	@GeneratedValue
	@Column(name = "paymentId")
	private int paymentId;

	@Column(name = "paymentCode", unique = true)
	private String paymentCode;
	
	@Column(name = "institution")
	private Institution institution;
	
	@Column(name = "paymentDate", columnDefinition = "DATETIME")
	@Temporal(TemporalType.TIMESTAMP)
	private Date paymentDate;
	
	
	@ManyToOne
	@JoinColumn(name = "amount")
	private UserCategory amount;

	@ManyToOne
	@JoinColumn(name = "paymentApproved")
	private UserCategory paymentApproved;

	public int getPaymentId() {
		return paymentId;
	}

	public void setPaymentId(int paymentId) {
		this.paymentId = paymentId;
	}

	public String getPaymentCode() {
		return paymentCode;
	}

	public void setPaymentCode(String paymentCode) {
		this.paymentCode = paymentCode;
	}

	public Institution getInstitution() {
		return institution;
	}

	public void setInstitution(Institution institution) {
		this.institution = institution;
	}

	public Date getPaymentDate() {
		return paymentDate;
	}

	public void setPaymentDate(Date paymentDate) {
		this.paymentDate = paymentDate;
	}

	public UserCategory getAmount() {
		return amount;
	}

	public void setAmount(UserCategory amount) {
		this.amount = amount;
	}

	public UserCategory getPaymentApproved() {
		return paymentApproved;
	}

	public void setPaymentApproved(UserCategory paymentApproved) {
		this.paymentApproved = paymentApproved;
	}
	
	
	
	
}
