/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package tres.dao.impl;

import java.util.List;
import tres.dao.generic.AbstractDao;
import tres.dao.interfc.IProvince;
import tres.domain.Province;

/**
 *
 * @author Emmanuel
 */
public class ProvinceImpl extends AbstractDao<Long, Province> implements IProvince {

	public Province saveProvince(Province province) {

		return saveIntable(province);
	}

	public List<Province> getListProvinces() {

		return (List<Province>) (Object) getModelList();
	}

	public Province getProvinceById(int provinceId, String primaryKeyclomunName) {

		return (Province) getModelById(provinceId, primaryKeyclomunName);
	}

	public Province UpdateProvince(Province province) {

		return updateIntable(province);
	}

}
