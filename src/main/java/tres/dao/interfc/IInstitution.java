package tres.dao.interfc;
/**
 * author James
 * */

import java.util.List;

import tres.domain.Institution;

public interface IInstitution {
	public Institution saveInstitution(Institution institution);

	public List<Institution> getListInstitutions();

	public Institution getInstitutionById(int institutionId, String primaryKeyclomunName);

	public Institution UpdateInstitution(Institution district);
}
