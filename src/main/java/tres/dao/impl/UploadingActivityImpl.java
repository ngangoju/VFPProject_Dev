package tres.dao.impl;

import java.util.List;
import java.util.logging.Logger;

import tres.dao.generic.AbstractDao;
import tres.dao.interfc.IUploadingActivity;
import tres.domain.UploadingActivity;

public class UploadingActivityImpl extends AbstractDao<Long, UploadingActivity> implements IUploadingActivity {
	private static final Logger LOGGER = Logger.getLogger(Thread.currentThread().getStackTrace()[0].getClassName());

	public UploadingActivity saveUploadedFile(UploadingActivity upload) {
		return saveIntable(upload);
	}

	@SuppressWarnings("unchecked")
	public List<UploadingActivity> getListUploadedFiles() {
		return (List<UploadingActivity>) (Object) getModelList();
	}

	public UploadingActivity getUploadedActivityById(int UploadId, String primaryKeyclomunName) {
		return (UploadingActivity) getModelById(UploadId, primaryKeyclomunName);
	}

	public UploadingActivity UpdateUploadedActivity(UploadingActivity upload) {
		return updateIntable(upload);
	}

	public String myName() {
		// TODO Auto-generated method stub
		return "Junior";
	}

	public UploadingActivity saveUploadedActivity(UploadingActivity upload) {
		// TODO Auto-generated method stub
		return null;
	}

	public List<UploadingActivity> getListUploadedActivity() {
		// TODO Auto-generated method stub
		return null;
	}

	public UploadingActivity getUploadedFileById(int UploadId, String primaryKeyclomunName) {
		// TODO Auto-generated method stub
		return null;
	}

	public UploadingActivity UpdateUploadedFile(UploadingActivity upload) {
		// TODO Auto-generated method stub
		return null;
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
