package tres.dao.impl;

import java.util.List;

import tres.dao.generic.AbstractDao;
import tres.dao.interfc.IInstitutionEscaletPolicy;
import tres.domain.InstitutionEscaletePolicy;

public class InstitutionEscaletPolicyImpl extends AbstractDao<Long, InstitutionEscaletePolicy>
		implements IInstitutionEscaletPolicy {

	public InstitutionEscaletePolicy saveInstEscalPolicy(InstitutionEscaletePolicy policy) {
		return saveIntable(policy);
	}

	public List<InstitutionEscaletePolicy> getListInstEscalPolicy() {
		return (List<InstitutionEscaletePolicy>) (Object) getModelList();
	}

	public InstitutionEscaletePolicy getInstEscalPolicyById(int policyId, String primaryKeyclomunName) {
		return (InstitutionEscaletePolicy) getModelById(policyId, primaryKeyclomunName);
	}

	public InstitutionEscaletePolicy UpdateInstEscalPolicy(InstitutionEscaletePolicy policy) {
		return updateIntable(policy);
	}

}
