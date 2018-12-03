package tres.dao.interfc;

import java.util.List;

import tres.domain.InstitutionLogo;

public interface IInstitutionLogo {

	public InstitutionLogo saveInstitutionLogo(InstitutionLogo logo);

	public List<InstitutionLogo> getListInstitutionLogo();

	public InstitutionLogo getInstitutionById(int logoId, String primaryKeyclomunName);

	public InstitutionLogo UpdateInstitution(InstitutionLogo logo);
}
