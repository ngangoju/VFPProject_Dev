package tres.dao.impl;

import java.util.List;
import java.util.logging.Logger;

import tres.dao.generic.AbstractDao;
import tres.dao.interfc.IVillage;
import tres.domain.Village;
/**
*
* @author Emmanuel
*/
public class VillageImpl extends AbstractDao<Long, Village> implements IVillage {
	private static final Logger LOGGER = Logger.getLogger(Thread.currentThread().getStackTrace()[0].getClassName());

	public Village saveVillage(Village village) {

		return saveIntable(village);
	}

	public List<Village> getListVillages() {

		return (List<Village>) (Object) getModelList();
	}

	public Village getVillageById(int villageId, String primaryKeyclomunName) {

		return (Village) getModelById(villageId, primaryKeyclomunName);
	}

	public Village UpdateVillage(Village village) {

		return updateIntable(village);
	}

}
