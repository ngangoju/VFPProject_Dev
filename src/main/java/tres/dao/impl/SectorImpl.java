/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package tres.dao.impl;

import java.util.List;
import tres.dao.generic.AbstractDao;
import tres.dao.interfc.IProvince;
import tres.dao.interfc.ISector;
import tres.domain.Province;
import tres.domain.Sector;

/**
 *
 * @author Emmanuel
 */
public class SectorImpl extends AbstractDao<Long, Sector> implements ISector {

	public Sector saveSector(Sector sector) {

		return saveIntable(sector);
	}

	public List<Sector> getListSectors() {

		return (List<Sector>) (Object) getModelList();
	}

	public Sector getSectorById(int sectorId, String primaryKeyclomunName) {

		return (Sector) getModelById(sectorId, primaryKeyclomunName);
	}

	public Sector UpdateSector(Sector sector) {

		return updateIntable(sector);
	}

}
