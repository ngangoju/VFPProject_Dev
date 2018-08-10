package tres.dao.impl;

import java.util.List;

import tres.dao.generic.AbstractDao;
import tres.dao.interfc.IActivity;
import tres.domain.Activity;
import tres.domain.Institution;

/**
 *
 * @author NGANGO
 */
public class ActivityImpl extends AbstractDao<Long, Activity> implements IActivity {

	public Activity saveActivity(Activity activity) {
		return saveIntable(activity);
	}

	public List<Activity> getListActivities() {
		return (List<Activity>) (Object) getModelList();
	}

	public Activity getActivityById(int activityId, String primaryKeyclomunName) {
		return (Activity) getModelById(activityId, primaryKeyclomunName);
	}

	public Activity UpdateActivity(Activity activity) {
		return updateIntable(activity);
	}

}
