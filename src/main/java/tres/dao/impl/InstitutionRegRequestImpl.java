package tres.dao.impl;
/**
 * author James
 * */

import java.util.List;

import tres.dao.generic.AbstractDao;
import tres.dao.interfc.IInstitutionRegRequest;
import tres.domain.InstitutionRegistrationRequest;

public class InstitutionRegRequestImpl extends AbstractDao<Long, InstitutionRegistrationRequest>
		implements IInstitutionRegRequest {

	public InstitutionRegistrationRequest saveContact(InstitutionRegistrationRequest instRegReqst) {
		return saveIntable(instRegReqst);
	}

	public List<InstitutionRegistrationRequest> getListInstitRegReqsts() {
		return (List<InstitutionRegistrationRequest>) (Object) getModelList();
	}

	public InstitutionRegistrationRequest getContactById(int instRegReqstId, String primaryKeyclomunName) {
		return (InstitutionRegistrationRequest) getModelById(instRegReqstId, primaryKeyclomunName);
	}

	public InstitutionRegistrationRequest UpdateInstitRegReqsts(InstitutionRegistrationRequest instRegReqst) {
		return updateIntable(instRegReqst);
	}

}
