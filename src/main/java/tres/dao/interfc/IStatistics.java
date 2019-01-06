package tres.dao.interfc;

import java.util.List;

import tres.domain.Activity;
import tres.domain.Statistics;

public interface IStatistics {
	public Statistics saveActivity(Statistics activity);

	public List<Statistics> getListActivities();
}
