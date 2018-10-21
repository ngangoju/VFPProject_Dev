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
@Table(name = "UploadingStrategicPlan")
@NamedQuery(name = "UploadingStrategicPlan.findAll", query = "select r from UploadingStrategicPlan r order by v desc")
public class UploadingStrategicPlan extends CommonDomain implements Serializable {

	private static final long serialVersionUID = 1L;
	@Id
	@GeneratedValue
	@Column(name = "upLoadPlanId")
	private long upLoadPlanId;
	@OneToOne
	@JoinColumn(name = "documents")
	private Documents documents;
	@ManyToOne
	@JoinColumn(name="strategicPlan")
	private StrategicPlan strategicPlan;


	public long getUpLoadPlanId() {
		return upLoadPlanId;
	}

	public void setUpLoadPlanId(long upLoadPlanId) {
		this.upLoadPlanId = upLoadPlanId;
	}

	public Documents getDocuments() {
		return documents;
	}

	public void setDocuments(Documents documents) {
		this.documents = documents;
	}

	public StrategicPlan getStrategicPlan() {
		return strategicPlan;
	}

	public void setStrategicPlan(StrategicPlan strategicPlan) {
		this.strategicPlan = strategicPlan;
	}

}
