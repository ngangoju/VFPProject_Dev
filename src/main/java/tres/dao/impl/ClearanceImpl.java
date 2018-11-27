package tres.dao.impl;

import java.util.List;

import tres.dao.generic.AbstractDao;
import tres.dao.interfc.IClearance;
import tres.domain.Clearance;
import tres.domain.InstitutionReportView;

public class ClearanceImpl extends AbstractDao<Long, Clearance> implements IClearance {

	public Clearance saveClearance(Clearance clearance) {
		
		 return saveIntable(clearance);
	}

	public List<Clearance> getListClearances() {
		return (List<Clearance>) (Object) getModelList();
	}

	public Clearance getClearanceById(int activities, String primaryKeyclomunName) {
		return (Clearance) getModelById(activities, primaryKeyclomunName);
	}

	public Clearance UpdateClearance(Clearance clearance) {
		// TODO Auto-generated method stub
		return null;
	}
	

}
