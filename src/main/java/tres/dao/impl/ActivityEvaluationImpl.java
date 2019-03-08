package tres.dao.impl;

import java.util.List;

import tres.dao.generic.AbstractDao;
import tres.dao.interfc.IActivityEvaluation;
import tres.domain.ActivityEvaluation;

public class ActivityEvaluationImpl extends AbstractDao<Long, ActivityEvaluation> implements IActivityEvaluation{

	public ActivityEvaluation saveActivityEvaluation(ActivityEvaluation activityEvaluation) {
		return saveIntable(activityEvaluation);
	}

	public List<ActivityEvaluation> getListActivityEvaluation() {
		return (List<ActivityEvaluation>) (Object) getModelList();
	}
	public ActivityEvaluation getActivityEvaluationById(int activityEvaluationId, String primaryKeyclomunName) {
		return (ActivityEvaluation) getModelById(activityEvaluationId, primaryKeyclomunName);
	}

	public ActivityEvaluation UpdateActivityEvaluation(ActivityEvaluation activityEvaluation) {
		return updateIntable(activityEvaluation);
	}

}
