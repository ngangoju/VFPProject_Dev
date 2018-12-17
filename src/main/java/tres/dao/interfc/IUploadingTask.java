package tres.dao.interfc;

import java.util.List;

import tres.domain.UploadingTask;

public interface IUploadingTask {
	public UploadingTask saveUploadedTask(UploadingTask upload);

	public List<UploadingTask> getListUploadedTask();

	public UploadingTask getUploadedFileById(int UploadId, String primaryKeyclomunName);

	public UploadingTask UpdateUploadedFile(UploadingTask upload);

	public String myName();

	public UploadingTask getUploadedTaskWithQuery(final String[] propertyName, final Object[] value,
			final String hqlStatement);

}
