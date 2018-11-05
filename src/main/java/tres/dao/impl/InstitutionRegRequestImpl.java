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

	public List<InstitutionRegistrationRequest> getListInstitRegReqsts() {
		return (List<InstitutionRegistrationRequest>) (Object) getModelList();
	}

	public InstitutionRegistrationRequest UpdateInstitRegReqsts(InstitutionRegistrationRequest instRegReqst) {
		return updateIntable(instRegReqst);
	}

	public InstitutionRegistrationRequest saveInstitutionRegRequest(InstitutionRegistrationRequest instRegReqst) {
		return saveIntable(instRegReqst);
	}

	public InstitutionRegistrationRequest getInstitutionRegRequestById(int instRegReqstId,
			String primaryKeyclomunName) {
		return (InstitutionRegistrationRequest) getModelById(instRegReqstId, primaryKeyclomunName);
	}

}
