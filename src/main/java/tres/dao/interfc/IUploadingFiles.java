/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package tres.dao.interfc;

import tres.domain.Documents;
import tres.domain.UploadingFiles;
import tres.domain.Users;
import java.util.List;

/**
 *
 * @author Emmanuel
 */
public interface IUploadingFiles {
	public UploadingFiles saveUploadedFile(UploadingFiles upload);

	public List<UploadingFiles> getListUploadedFiles();

	public UploadingFiles getUploadedFileById(int UploadId, String primaryKeyclomunName);

	public UploadingFiles UpdateUploadedFile(UploadingFiles upload);

	public String myName();

	public UploadingFiles getUploadedFilesWithQuery(final String[] propertyName, final Object[] value,
			final String hqlStatement);
}