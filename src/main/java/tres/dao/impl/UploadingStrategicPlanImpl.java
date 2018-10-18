package tres.dao.impl;

import java.util.List;
import java.util.logging.Logger;

import tres.dao.generic.AbstractDao;
import tres.dao.interfc.IUploadingStrategicPlan;
import tres.domain.UploadingStrategicPlan;

public class UploadingStrategicPlanImpl extends AbstractDao<Long, UploadingStrategicPlan> implements IUploadingStrategicPlan{
	private static final Logger LOGGER = Logger.getLogger(Thread.currentThread().getStackTrace()[0].getClassName());

	public String myName() {
		// TODO Auto-generated method stub
		return "Emma";
	}

	public UploadingStrategicPlan saveUploadedPlan(UploadingStrategicPlan upload) {
		return saveIntable(upload);
	}

	public List<UploadingStrategicPlan> getListUploadedPlans() {
		return (List<UploadingStrategicPlan>) (Object) getModelList();
	}

	public UploadingStrategicPlan getUploadedPlanById(int UploadId, String primaryKeyclomunName) {
		return (UploadingStrategicPlan) getModelById(UploadId, primaryKeyclomunName);
	}

	public UploadingStrategicPlan UpdateUploadedPlan(UploadingStrategicPlan upload) {
		return updateIntable(upload);
	}

	public UploadingStrategicPlan getUploadedPlansWithQuery(String[] propertyName, Object[] value,
			String hqlStatement) {
		try {
			return (UploadingStrategicPlan) getModelWithMyHQL(propertyName, value, hqlStatement);
		} catch (Exception ex) {
			LOGGER.info("getUsersWithQuery  Query error ::::" + ex.getMessage());
		}
		return null;
	}


}
