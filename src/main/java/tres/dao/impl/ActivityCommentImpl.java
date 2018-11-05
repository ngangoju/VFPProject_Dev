package tres.dao.impl;

import java.util.List;

import tres.dao.generic.AbstractDao;
import tres.dao.interfc.IActivityComment;
import tres.domain.ActivityComment;
import tres.domain.Institution;

/**
 *
 * @author NGANGO
 */
public class ActivityCommentImpl extends AbstractDao<Long, ActivityComment> implements IActivityComment {

	public ActivityComment saveActivityComment(ActivityComment activityComment) {
		return saveIntable(activityComment);
	}

	public ActivityComment getActivityCommentById(int activityCommentId, String primaryKeyclomunName) {
		return (ActivityComment) getModelById(activityCommentId, primaryKeyclomunName);
	}

	public ActivityComment UpdateActivityComment(ActivityComment activityComment) {
		return updateIntable(activityComment);
	}

	public List<ActivityComment> getListActivityComments() {
		return (List<ActivityComment>) (Object) getModelList();
	}

}
