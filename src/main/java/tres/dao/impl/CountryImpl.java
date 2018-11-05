package tres.dao.impl;

import java.util.List;

import tres.dao.generic.AbstractDao;
import tres.dao.interfc.ICountry;
import tres.domain.Country;

public class CountryImpl extends AbstractDao<Long, Country> implements ICountry {

	public Country saveCountry(Country country) {
		return saveIntable(country);
	}

	public List<Country> getListCountrys() {
		return (List<Country>) (Object) getModelList();
	}

	public Country getCountryById(int countryId, String primaryKeyclomunName) {
		return (Country) getModelById(countryId, primaryKeyclomunName);
	}

	public Country UpdateCountry(Country country) {
		return updateIntable(country);
	}

}
