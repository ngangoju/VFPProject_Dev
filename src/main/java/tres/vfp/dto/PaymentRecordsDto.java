package tres.vfp.dto;

import java.io.Serializable;
import java.sql.Timestamp;
import java.util.Date;

import tres.domain.Users;
import tres.domain.Institution;

public class PaymentRecordsDto implements Serializable {
	private static final long serialVersionUID = 1L;

	private int paymentId;

	private String paymentCode;

	private Date paymentDate;

	private Date paymentExpiretionDate;

	private String amount;

	private String currency;

	private String paymentChanel;

	private String paymentStatus;

	private String bankRefernceNo;

	private String comment;

	private Users paymentApprovedBy;

	private Institution institution;

	private boolean editables;

	private String action;

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

	public String getAmount() {
		return amount;
	}

	public void setAmount(String amount) {
		this.amount = amount;
	}

	public String getCurrency() {
		return currency;
	}

	public void setCurrency(String currency) {
		this.currency = currency;
	}

	public String getPaymentChanel() {
		return paymentChanel;
	}

	public void setPaymentChanel(String paymentChanel) {
		this.paymentChanel = paymentChanel;
	}

	public String getPaymentStatus() {
		return paymentStatus;
	}

	public void setPaymentStatus(String paymentStatus) {
		this.paymentStatus = paymentStatus;
	}

	public String getBankRefernceNo() {
		return bankRefernceNo;
	}

	public void setBankRefernceNo(String bankRefernceNo) {
		this.bankRefernceNo = bankRefernceNo;
	}

	public String getComment() {
		return comment;
	}

	public void setComment(String comment) {
		this.comment = comment;
	}

	public Users getPaymentApprovedBy() {
		return paymentApprovedBy;
	}

	public void setPaymentApprovedBy(Users paymentApprovedBy) {
		this.paymentApprovedBy = paymentApprovedBy;
	}

	public Institution getInstitution() {
		return institution;
	}

	public void setInstitution(Institution institution) {
		this.institution = institution;
	}

	public boolean iseditables() {
		return editables;
	}

	public void seteditables(boolean editables) {
		this.editables = editables;
	}

	public String getAction() {
		return action;
	}

	public void setAction(String action) {
		this.action = action;
	}

	public Date getPaymentDate() {
		return paymentDate;
	}

	public void setPaymentDate(Date paymentDate) {
		this.paymentDate = paymentDate;
	}

	public Date getPaymentExpiretionDate() {
		return paymentExpiretionDate;
	}

	public void setPaymentExpiretionDate(Date paymentExpiretionDate) {
		this.paymentExpiretionDate = paymentExpiretionDate;
	}

}
