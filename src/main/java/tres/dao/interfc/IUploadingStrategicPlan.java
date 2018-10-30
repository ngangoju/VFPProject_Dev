package tres.dao.interfc;

import java.util.List;

import tres.domain.UploadingStrategicPlan;

public interface IUploadingStrategicPlan {

    public UploadingStrategicPlan saveUploadedPlan(UploadingStrategicPlan upload);
       public List<UploadingStrategicPlan> getListUploadedPlans();
        public UploadingStrategicPlan getUploadedPlanById(int UploadId,String primaryKeyclomunName);
        public UploadingStrategicPlan UpdateUploadedPlan(UploadingStrategicPlan upload);
        public String myName();
public UploadingStrategicPlan getUploadedPlansWithQuery(final String[] propertyName, final Object[] value, final String hqlStatement);

}
