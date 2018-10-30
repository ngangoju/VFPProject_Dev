package tres.dao.interfc;

import java.util.List;
import tres.domain.ActivityComment;

/**
 *
 * @author NGANGO
 */
public interface IActivityComment {
	public ActivityComment saveActivityComment(ActivityComment activityComment);

	public List<ActivityComment> getListActivityComments();

	public ActivityComment getActivityCommentById(int activityCommentId, String primaryKeyclomunName);

	public ActivityComment UpdateActivityComment(ActivityComment activityComment);
}
