package tres.dao.impl;

import java.util.List;

import tres.dao.generic.AbstractDao;
import tres.dao.interfc.IActivity;
import tres.dao.interfc.IStatistics;
import tres.domain.Activity;
import tres.domain.Statistics;

public class StatisticsImpl extends AbstractDao<Long,Statistics> implements IStatistics {

	public Statistics saveActivity(Statistics activity) {
		return saveIntable(activity);
	}

	public List<Statistics> getListActivities() {
		 return (List<Statistics>) (Object) getModelList();
	}

	

}
