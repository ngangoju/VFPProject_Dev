package tres.domain;

import java.io.Serializable;
import java.sql.Timestamp;

import javax.persistence.Column;
import javax.persistence.MappedSuperclass;
import javax.xml.bind.annotation.adapters.XmlJavaTypeAdapter;

@MappedSuperclass
public abstract class Documents implements Serializable {
private static final long serialVersionUID =  -6665275582900585705L;
public Documents() {

  }
  @Column(name = "DocUrl")
  private String DocumentUrl;

  @Column(name = "DocName")
  private String DocName;
  @Column(name = "DocType")
  private String DocType;

  @Column(name = "DocSize")
  private int DocSize;

  @Column(name = "UploadDtTime")
  private Timestamp UploadDtTime;

  @Column(name = "UploadedBy")
  private String UploadedBy;
  
  @Column(name = "DocStatus")
  private String DocStatus;
public String getDocumentUrl() {
	return DocumentUrl;
}

public void setDocumentUrl(String documentUrl) {
	DocumentUrl = documentUrl;
}
public String getDocName() {
	return DocName;
}

public void setDocName(String docName) {
	DocName = docName;
}

public Timestamp getUploadDtTime() {
	return UploadDtTime;
}

public void setUploadDtTime(Timestamp uploadDtTime) {
	UploadDtTime = uploadDtTime;
}

public String getUploadedBy() {
	return UploadedBy;
}

public void setUploadedBy(String uploadedBy) {
	UploadedBy = uploadedBy;
}

public String getDocStatus() {
	return DocStatus;
}

public void setDocStatus(String docStatus) {
	DocStatus = docStatus;
}

public int getDocSize() {
	return DocSize;
}

public void setDocSize(int docSize) {
	DocSize = docSize;
}

public String getDocType() {
	return DocType;
}

public void setDocType(String docType) {
	DocType = docType;
} 

}
