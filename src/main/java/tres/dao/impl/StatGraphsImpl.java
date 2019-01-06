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

	public StatGraph saveStatGraphs(StatGraph statGraphs) {
		return saveIntable(statGraphs);
	}

	@SuppressWarnings("unchecked")
	public List<StatGraph> getListStatisticGraphs() {
		return (List<StatGraph>) (Object) getModelList();
	}

	public StatGraph gettStatGraphById(int graphId, String primaryKeyclomunName) {
		return (StatGraph) getModelById(graphId, primaryKeyclomunName);
	}

	public StatGraph UpdateStatGraphs(StatGraph statGraphs) {
		return updateIntable(statGraphs);
	}

	public StatGraph getStatGraphWithQuery(String[] propertyName, Object[] value, String hqlStatement) {
		try {
			return (StatGraph) getModelWithMyHQL(propertyName, value, hqlStatement);
		} catch (Exception ex) {
			LOGGER.info("getUsersWithQuery  Query error ::::" + ex.getMessage());
		}
		return null;
	}

	public String myNane() {
		return "GWIZA ERIC";
	}

	public StatGraphsImpl() {

	}

	public StatGraph saveStatGraph(StatGraph statGraph) {
		// TODO Auto-generated method stub
		return null;
	}

	public List<StatGraph> getListStatGraphs() {
		// TODO Auto-generated method stub
		return null;
	}

	public StatGraph getStatGraphById(int graphId, String primaryKeyclomunName) {
		// TODO Auto-generated method stub
		return null;
	}

	public StatGraph UpdateStatGraph(StatGraph statGraph) {
		// TODO Auto-generated method stub
		return null;
	}
}
