package tres.dao.interfc;

import java.util.List;

import tres.domain.Activity;
import tres.domain.StatGraph;
import tres.domain.Statistics;

public interface IStatistics {
	public Statistics saveStatistics(Statistics statistics);

	public List<Statistics> getListStatistics();

	public Statistics getStatisticsById(int id, String primaryKeyclomunName);

	public Statistics UpdateStatistics(Statistics statistics);
}
