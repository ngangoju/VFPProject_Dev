package tres.dao.impl;

import java.util.List;
import java.util.logging.Logger;
import tres.dao.generic.AbstractDao;
import tres.dao.interfc.IUploadingTask;
import tres.domain.UploadingTask;

public class UploadingTaskImpl extends AbstractDao<Long, UploadingTask> implements IUploadingTask {
	private static final Logger LOGGER = Logger.getLogger(Thread.currentThread().getStackTrace()[0].getClassName());

	public UploadingTask saveUploadedTask(UploadingTask upload) {
		return saveIntable(upload);
	}

	@SuppressWarnings("unchecked")
	public List<UploadingTask> getListUploadedTask() {
		return (List<UploadingTask>) (Object) getModelList();
	}

	public UploadingTask getUploadedTaskById(int UploadId, String primaryKeyclomunName) {
		return (UploadingTask) getModelById(UploadId, primaryKeyclomunName);
	}

	public UploadingTask UpdateUploadedTask(UploadingTask upload) {
		return updateIntable(upload);
	}

	public UploadingTask getUploadedTaskWithQuery(String[] propertyName, Object[] value, String hqlStatement) {
		try {
			return (UploadingTask) getModelWithMyHQL(propertyName, value, hqlStatement);
		} catch (Exception ex) {
			LOGGER.info("getUsersWithQuery  Query error ::::" + ex.getMessage());
		}
		return null;
	}

	public UploadingTask getUploadedFileById(int UploadId, String primaryKeyclomunName) {
		// TODO Auto-generated method stub
		return null;
	}

	public UploadingTask UpdateUploadedFile(UploadingTask upload) {
		// TODO Auto-generated method stub
		return null;
	}

	public String myName() {
		// TODO Auto-generated method stub
		return null;
	}

}
