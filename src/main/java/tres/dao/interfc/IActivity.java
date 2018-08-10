package tres.dao.interfc;

import java.util.List;
import tres.domain.Activity;

/**
 *
 * @author NGANGO
 */
public interface IActivity {
	public Activity saveActivity(Activity activity);

	public List<Activity> getListActivities();

	public Activity getActivityById(int activityId, String primaryKeyclomunName);

	public Activity UpdateActivity(Activity activity);
}
