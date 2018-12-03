package tres.dao.impl;

import java.util.List;

import tres.dao.generic.AbstractDao;
import tres.dao.interfc.IInstitutionLogo;
import tres.domain.InstitutionLogo;

public class InstitutionLogoImpl extends AbstractDao<Long, InstitutionLogo> implements IInstitutionLogo {

	public InstitutionLogo saveInstitutionLogo(InstitutionLogo logo) {
		return saveIntable(logo);
	}

	public List<InstitutionLogo> getListInstitutionLogo() {
		return (List<InstitutionLogo>) (Object) getModelList();
	}

	public InstitutionLogo getInstitutionById(int logoId, String primaryKeyclomunName) {
		return (InstitutionLogo) getModelById(logoId, primaryKeyclomunName);
	}

	public InstitutionLogo UpdateInstitution(InstitutionLogo logo) {
		return updateIntable(logo);
	}

}
