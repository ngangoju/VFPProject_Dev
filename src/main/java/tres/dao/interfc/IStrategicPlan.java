package tres.dao.interfc;

/**
 * author Junior
 **/

import java.util.List;
import tres.domain.StrategicPlan;

public interface IStrategicPlan {
	public StrategicPlan saveStrategicPlan(StrategicPlan strategicPlan);
    public List<StrategicPlan> getListStrategicPlans();
    public StrategicPlan getStrategicPlanById(int strategicPlanId,String primaryKeyclomunName);
    public StrategicPlan UpdateStrategicPlan(StrategicPlan strategicPlan);  
}
