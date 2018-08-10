package tres.dao.interfc;

/**
 * author Junior
 **/

import java.util.List;
import tres.domain.Activity;

public interface IActivity {
	public Activity saveActivity(Activity activity);
    public List<Activity> getListActivities();
    public Activity getActivityById(int activityId,String primaryKeyclomunName);
    public Activity UpdateActivity(Activity activity);  
}
