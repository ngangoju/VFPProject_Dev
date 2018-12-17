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
@Table(name = "UploadingTask")
@NamedQuery(name = "UploadingTask.findAll", query = "select r from UploadingTask r order by v desc")
public class UploadingTask extends CommonDomain implements Serializable {

	private static final long serialVersionUID = 1L;
	@Id
	@GeneratedValue
	@Column(name = "upLoadTaskId")
	private long upLoadTaskId;
	@OneToOne
	@JoinColumn(name = "documents")
	private Documents documents;
	@ManyToOne
	@JoinColumn(name = "Task")
	private Task Task;

	public long getUpLoadTaskId() {
		return upLoadTaskId;
	}

	public void setUpLoadTaskId(long upLoadTaskId) {
		this.upLoadTaskId = upLoadTaskId;
	}

	public Task getTask() {
		return Task;
	}

	public void setTask(Task Task) {
		this.Task = Task;
	}

	public Documents getDocuments() {
		return documents;
	}

	public void setDocuments(Documents documents) {
		this.documents = documents;
	}

}
