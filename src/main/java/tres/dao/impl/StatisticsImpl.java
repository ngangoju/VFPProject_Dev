package tres.dao.impl;

import java.util.List;

import tres.dao.generic.AbstractDao;
import tres.dao.interfc.IActivity;
import tres.dao.interfc.IStatistics;
import tres.domain.Activity;
import tres.domain.StatGraph;
import tres.domain.Statistics;

public class StatisticsImpl extends AbstractDao<Long,Statistics> implements IStatistics {


	public Statistics saveStatistics(Statistics statistics) {
		return saveIntable(statistics);
	}

	public List<Statistics> getListStatistics() {
		return (List<Statistics>) (Object) getModelList();
	}
	public Statistics getStatisticsById(int id, String primaryKeyclomunName) {
		return (Statistics) getModelById(id, primaryKeyclomunName);
	}

	public Statistics UpdateStatistics(Statistics statistics) {
		return updateIntable(statistics);
	}

	

}
