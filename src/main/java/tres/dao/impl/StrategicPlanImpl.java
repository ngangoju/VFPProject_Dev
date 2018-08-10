package tres.dao.impl;

import java.util.List;

import tres.dao.generic.AbstractDao;
import tres.dao.interfc.IStrategicPlan;
import tres.domain.Institution;
import tres.domain.StrategicPlan;

/**
 *
 * @author NGANGO
 */
public class StrategicPlanImpl extends AbstractDao<Long, StrategicPlan> implements IStrategicPlan {

	public StrategicPlan saveStrategicPlan(StrategicPlan strategicPlan) {
		return saveIntable(strategicPlan);
	}

	public List<StrategicPlan> getListStrategicPlans() {
		return (List<StrategicPlan>) (Object) getModelList();
	}

	public StrategicPlan getStrategicPlanById(int strategicPlanId, String primaryKeyclomunName) {
		return (StrategicPlan) getModelById(strategicPlanId, primaryKeyclomunName);
	}

	public StrategicPlan UpdateStrategicPlan(StrategicPlan strategicPlan) {
		return updateIntable(strategicPlan);
	}

}
