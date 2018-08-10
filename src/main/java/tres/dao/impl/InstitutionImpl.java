package tres.dao.impl;
/**
 * author James
 * */

import java.util.List;

import tres.dao.generic.AbstractDao;
import tres.dao.interfc.IInstitution;
import tres.domain.Institution;

public class InstitutionImpl extends AbstractDao<Long, Institution> implements IInstitution {

	public Institution saveInstitution(Institution institution) {
		return saveIntable(institution);
	}

	public List<Institution> getListInstitutions() {
		return (List<Institution>) (Object) getModelList();
	}

	public Institution getInstitutionById(int institutionId, String primaryKeyclomunName) {
		return (Institution) getModelById(institutionId, primaryKeyclomunName);
	}

	public Institution UpdateInstitution(Institution district) {
		return updateIntable(district);
	}

}
