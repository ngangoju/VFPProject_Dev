/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package tres.dao.impl;

import java.util.List;
import tres.dao.generic.AbstractDao;
import tres.dao.interfc.IDistrict;
import tres.domain.District;

/**
 *
 * @author Emmanuel
 */

public class DistrictImpl extends AbstractDao<Long, District> implements IDistrict {

	public District saveDistrict(District district) {

		return saveIntable(district);
	}

	public List<District> getListDistricts() {

		return (List<District>) (Object) getModelList();
	}

	public District getDistrictById(int districtId, String primaryKeyclomunName) {

		return (District) getModelById(districtId, primaryKeyclomunName);
	}

	public District UpdateDistrict(District district) {

		return updateIntable(district);
	}

}
