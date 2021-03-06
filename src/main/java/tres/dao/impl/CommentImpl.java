package tres.dao.impl;

import java.util.List;
import tres.dao.generic.AbstractDao;
import tres.dao.interfc.IComment;
import tres.domain.Comment;
public class CommentImpl extends AbstractDao<Long, Comment> implements IComment {

	public Comment saveComment(Comment comment) {
		return  saveIntable(comment);
	}

	public List<Comment> getListComments() {
		 return(List<Comment>) (Object) getModelList();
	}

	public Comment getCommentById(int commentId, String primaryKeyclomunName) {
		return (Comment) getModelById(commentId, primaryKeyclomunName);
	}

	public Comment UpdateComment(Comment comment) {
		return updateIntable(comment);
	}

}
