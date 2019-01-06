package tres.dao.interfc;

import java.util.List;
import tres.domain.Activity;
import tres.domain.StatGraph;

/**
 *
 * @author Gwiza
 */
public interface IStatGraph {
	public StatGraph saveStatGraph(StatGraph statGraph);

	public List<StatGraph> getListStatGraphs();

	public StatGraph getStatGraphById(int graphId, String primaryKeyclomunName);

	public StatGraph UpdateStatGraph(StatGraph statGraph);
}
