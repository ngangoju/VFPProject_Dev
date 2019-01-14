/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package tres.dao.impl;

import java.util.List;
import java.util.logging.Logger;
import tres.dao.generic.AbstractDao;
import tres.dao.interfc.IStatGraph;
import tres.domain.StatGraph;

/**
 *
 * @author Gwiza
 */

public class StatGraphsImpl extends AbstractDao<Long, StatGraph> implements IStatGraph {
	private static final Logger LOGGER = Logger.getLogger(Thread.currentThread().getStackTrace()[0].getClassName());
	
	public StatGraph saveStatGraph(StatGraph statGraph) {
		return saveIntable(statGraph);
	}

	public List<StatGraph> getListStatGraphs() {
		return (List<StatGraph>) (Object) getModelList();
	}

	public StatGraph getStatGraphById(int graphId, String primaryKeyclomunName) {
		return (StatGraph) getModelById(graphId, primaryKeyclomunName);
	}

	public StatGraph UpdateStatGraph(StatGraph statGraph) {
		return updateIntable(statGraph);
	}
}
