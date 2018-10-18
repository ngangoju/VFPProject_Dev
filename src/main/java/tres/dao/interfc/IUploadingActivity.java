package tres.dao.interfc;

import java.util.List;

import tres.domain.UploadingActivity;

public interface IUploadingActivity {
	public UploadingActivity saveUploadedActivity(UploadingActivity upload);
    public List<UploadingActivity> getListUploadedActivity();
     public UploadingActivity getUploadedFileById(int UploadId,String primaryKeyclomunName);
     public UploadingActivity UpdateUploadedFile(UploadingActivity upload);
     public String myName();
public UploadingActivity getUploadedActivtyWithQuery(final String[] propertyName, final Object[] value, final String hqlStatement);

}
