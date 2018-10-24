package tres.dao.interfc;

import java.util.List;

import tres.domain.InstitutionEscaletePolicy;

public interface IInstitutionEscaletPolicy {
	public InstitutionEscaletePolicy saveInstEscalPolicy(InstitutionEscaletePolicy policy);

	public List<InstitutionEscaletePolicy> getListInstEscalPolicy();

	public InstitutionEscaletePolicy getInstEscalPolicyById(int policyId, String primaryKeyclomunName);

	public InstitutionEscaletePolicy UpdateInstEscalPolicy(InstitutionEscaletePolicy policy);
}
