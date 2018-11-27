package tres.dao.interfc;

import java.util.List;

import tres.domain.Activity;
import tres.domain.InstitutionReportView;

public interface IInstitutionReportView {
	
	public 	InstitutionReportView saveInstitutionReportView (InstitutionReportView insView);
	
	public List<InstitutionReportView>getInstitutionReportviews();
	
	public InstitutionReportView getInstitutionReportViewById(int number, String primaryKeyclomunName);
	

	public Activity UpdateInstitutionreportview(InstitutionReportView institutionrepView);
	
	

}
