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
@Table(name = "ActivityComment")
@NamedQuery(name = "ActivityComment.findAll", query = "select r from ActivityComment r order by v desc")
public class ActivityComment extends CommonDomain implements Serializable {

	private static final long serialVersionUID = 1L;
	@Id
	@GeneratedValue
	@Column(name = "commentActId")
	private long commentActId;
	@OneToOne
	@JoinColumn(name = "comment")
	private Comment comment;
	@ManyToOne
	@JoinColumn(name = "activity")
	private Activity activity;

	public long getCommentActId() {
		return commentActId;
	}

	public void setCommentActId(long commentActId) {
		this.commentActId = commentActId;
	}

	public Activity getActivity() {
		return activity;
	}

	public void setActivity(Activity activity) {
		this.activity = activity;
	}

	public Comment getComment() {
		return comment;
	}

	public void setComment(Comment comment) {
		this.comment = comment;
	}

}
