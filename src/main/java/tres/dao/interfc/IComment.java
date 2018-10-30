package tres.dao.interfc;

import java.util.List;
import tres.domain.Comment;

/**
 *
 * @author NGANGO
 */
public interface IComment {
	public Comment saveComment(Comment comment);

	public List<Comment> getListComments();

	public Comment getCommentById(int commentId, String primaryKeyclomunName);

	public Comment UpdateComment(Comment comment);
}
