package tres.domain;

import java.io.Serializable;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.NamedQuery;
import javax.persistence.OneToOne;
import javax.persistence.Table;
@Entity
@Table(name = "UploadingActivity ")
@NamedQuery(name = "UploadingActivity .findAll", query = "SELECT r FROM UploadingActivity  r order by upLoadActId desc")
public class UploadingActivity extends CommonDomain implements Serializable {

	private static final long serialVersionUID = 1L;
	@Id
	@GeneratedValue
	@Column(name = "upLoadActId")
	private long upLoadActId;
	@OneToOne
	@JoinColumn(name = "documents")
	private Documents documents;
	@ManyToOne
	@JoinColumn(name="activity")
	private Activity activity;


	public long getUpLoadActId() {
		return upLoadActId;
	}

	public void setUpLoadActId(long upLoadActId) {
		this.upLoadActId = upLoadActId;
	}

	public Activity getActivity() {
		return activity;
	}

	public void setActivity(Activity activity) {
		this.activity = activity;
	}

	public Documents getDocuments() {
		return documents;
	}

	public void setDocuments(Documents documents) {
		this.documents = documents;
	}

}
