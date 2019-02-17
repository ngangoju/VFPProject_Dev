package tres.dao.interfc;

import java.util.List;

import tres.domain.Activity;
import tres.domain.StaffReportView;

public interface IStaffReportView {

	public 	StaffReportView  saveStaffReportView  (StaffReportView  insView);
	
	public List<StaffReportView>getStaffReportView ();
	
	public StaffReportView  getStaffReportViewById(int number, String primaryKeyclomunName);
	

	public StaffReportView UpdateStaffReportView (StaffReportView  staffReportView );
	
	

}
