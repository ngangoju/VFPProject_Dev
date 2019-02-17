package tres.dao.impl;

import java.util.List;

import tres.dao.generic.AbstractDao;
import tres.dao.interfc.IStaffReportView;
import tres.domain.StaffReportView;

public class StaffReportViewImpl  extends AbstractDao<Long, StaffReportView> implements IStaffReportView {

	public StaffReportView saveStaffReportView(StaffReportView insView) {
		return saveIntable(insView);
	}

	public List<StaffReportView> getStaffReportView() {
		return (List<StaffReportView>) (Object) getModelList();
	}

	public StaffReportView getStaffReportViewById(int number, String primaryKeyclomunName) {
		return (StaffReportView) getModelById(number, primaryKeyclomunName);
	}

	public StaffReportView UpdateStaffReportView(StaffReportView staffReportView) {
		// TODO Auto-generated method stub
		return null;
	}
	

}
