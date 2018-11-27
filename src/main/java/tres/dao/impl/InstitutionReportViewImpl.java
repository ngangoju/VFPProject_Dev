package tres.dao.impl;

import java.util.List;

import tres.dao.generic.AbstractDao;
import tres.dao.interfc.IActivity;
import tres.dao.interfc.IInstitutionReportView;
import tres.domain.Activity;
import tres.domain.InstitutionReportView;

public class InstitutionReportViewImpl extends AbstractDao<Long, InstitutionReportView> implements IInstitutionReportView {

	public InstitutionReportView saveInstitutionReportView(InstitutionReportView insView) {
		return saveIntable(insView);
	}

	public List<InstitutionReportView> getInstitutionReportviews() {
		return (List<InstitutionReportView>) (Object) getModelList();
	}

	public InstitutionReportView getInstitutionReportViewById(int number, String primaryKeyclomunName) {
		return (InstitutionReportView) getModelById(number, primaryKeyclomunName);
	}

	public Activity UpdateInstitutionreportview(InstitutionReportView institutionrepView) {
		// TODO Auto-generated method stub
		return null;
	}




}
