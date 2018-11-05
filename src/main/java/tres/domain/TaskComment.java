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
@Table(name = "TaskComment")
@NamedQuery(name = "TaskComment.findAll", query = "SELECT r FROM TaskComment r order by v desc")
public class TaskComment extends CommonDomain implements Serializable {

	private static final long serialVersionUID = 1L;
	@Id
	@GeneratedValue
	@Column(name = "commentTaskId")
	private long commentTaskId;
	@OneToOne
	@JoinColumn(name = "comment")
	private Comment comment;
	@ManyToOne
	@JoinColumn(name = "task")
	private Task task;

	public long getCommentTaskId() {
		return commentTaskId;
	}

	public void setCommentTaskId(long commentTaskId) {
		this.commentTaskId = commentTaskId;
	}

	public Comment getComment() {
		return comment;
	}

	public void setComment(Comment comment) {
		this.comment = comment;
	}

	public Task getTask() {
		return task;
	}

	public void setTask(Task task) {
		this.task = task;
	}

}
