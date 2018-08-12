package tres.dao.interfc;
/**
 * author James
 * */

import java.util.List;

import tres.domain.InstitutionRegistrationRequest;

public interface IInstitutionRegRequest {
	public InstitutionRegistrationRequest saveInstitutionRegRequest(InstitutionRegistrationRequest instRegReqst);

	public List<InstitutionRegistrationRequest> getListInstitRegReqsts();

	public InstitutionRegistrationRequest getInstitutionRegRequestById(int instRegReqstId, String primaryKeyclomunName);

	public InstitutionRegistrationRequest UpdateInstitRegReqsts(InstitutionRegistrationRequest instRegReqst);
}
