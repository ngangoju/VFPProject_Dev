package tres.dao.interfc;

import java.util.List;
import tres.domain.StrategicPlan;

/**
 *
 * @author NGANGO
 */
public interface IStrategicPlan {
	public StrategicPlan saveStrategicPlan(StrategicPlan strategicPlan);

	public List<StrategicPlan> getListStrategicPlans();

	public StrategicPlan getStrategicPlanById(int strategicPlanId, String primaryKeyclomunName);

	public StrategicPlan UpdateStrategicPlan(StrategicPlan strategicPlan);
}
