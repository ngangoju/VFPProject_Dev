package tres.dao.interfc;
/**
 * author James
 * */

import java.util.List;

import tres.domain.InstitutionRegistrationRequest;

public interface IInstitutionRegRequest {
	public InstitutionRegistrationRequest saveContact(InstitutionRegistrationRequest instRegReqst);

	public List<InstitutionRegistrationRequest> getListInstitRegReqsts();

	public InstitutionRegistrationRequest getContactById(int instRegReqstId, String primaryKeyclomunName);

	public InstitutionRegistrationRequest UpdateInstitRegReqsts(InstitutionRegistrationRequest instRegReqst);
}
