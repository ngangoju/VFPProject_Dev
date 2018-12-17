package tres.dao.impl;

import java.util.List;
import java.util.logging.Logger;

import tres.dao.generic.AbstractDao;
import tres.dao.interfc.IUploadingActivity;
import tres.domain.UploadingActivity;

public class UploadingActivityImpl extends AbstractDao<Long, UploadingActivity> implements IUploadingActivity {
	private static final Logger LOGGER = Logger.getLogger(Thread.currentThread().getStackTrace()[0].getClassName());
	public String myName() {
		// TODO Auto-generated method stub
		return "Junior";
	}

	public UploadingActivity saveUploadedActivity(UploadingActivity upload) {
		return saveIntable(upload);
	}

	public List<UploadingActivity> getListUploadedActivity() {
		return (List<UploadingActivity>) (Object) getModelList();
	}

	public UploadingActivity getUploadedFileById(int UploadId, String primaryKeyclomunName) {
		return (UploadingActivity) getModelById(UploadId, primaryKeyclomunName);
	}

	public UploadingActivity UpdateUploadedFile(UploadingActivity upload) {
		return updateIntable(upload);
	}

	public UploadingActivity getUploadedActivtyWithQuery(String[] propertyName, Object[] value, String hqlStatement) {
		try {
			return (UploadingActivity) getModelWithMyHQL(propertyName, value, hqlStatement);
		} catch (Exception ex) {
			LOGGER.info("getUsersWithQuery  Query error ::::" + ex.getMessage());
		}
		return null;
	}

}
