package tres.dao.interfc;

import java.util.List;

import tres.domain.ActivityEvaluation;

public interface IActivityEvaluation {
	public ActivityEvaluation saveActivityEvaluation(ActivityEvaluation activityEvaluation);

	public List<ActivityEvaluation> getListActivityEvaluation();

	public ActivityEvaluation getActivityEvaluationById(int activityEvaluationId, String primaryKeyclomunName);

	public ActivityEvaluation UpdateActivityEvaluation(ActivityEvaluation activityEvaluation);
}
