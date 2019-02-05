package com.vfp.tres;

import java.util.ArrayList;
import java.util.List;
import java.util.logging.Logger;

import javax.annotation.PostConstruct;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.RequestScoped;
import com.google.gson.Gson;

import tres.common.JSFBoundleProvider;
import tres.common.JSFMessagers;
import tres.dao.impl.InstitutionReportViewImpl;
import tres.dao.impl.StatisticsImpl;
import tres.domain.InstitutionReportView;
import tres.domain.Statistics;
import tres.vfp.dto.ClearanceDto;
import tres.vfp.dto.GraphDto;

@ManagedBean(name="charController")
@RequestScoped
public class ChartController {
	private static final Logger LOGGER = Logger.getLogger(Thread.currentThread().getStackTrace()[0].getClassName());
	private int selectedBoard;
	private ClearanceDto statistics=new ClearanceDto();
	private String name;
	private String convertedData="test";
	private String[] number;
	private String[] values;
	JSFBoundleProvider provider = new JSFBoundleProvider();
	InstitutionReportViewImpl institutionReportViewImpl=new InstitutionReportViewImpl();
	private List<GraphDto>GraphDtoDetails=new ArrayList<GraphDto>();
	private List<ClearanceDto> ClearanceDtoDetails = new ArrayList<ClearanceDto>();
	private ClearanceDto clearanceDto;
	
	//private ClearanceDto clearanceDto;
	//list of elements to be graphed
	
	private List<InstitutionReportView> elementsList = new InstitutionReportViewImpl().getInstitutionReportviews();
	
	
	@PostConstruct
	public void init() {
		
		myClearance();
	}
	
	public List<ClearanceDto> myClearance() {
		try {		
			ClearanceDtoDetails = new ArrayList<ClearanceDto>();
	        ClearanceDtoDetails = new ArrayList<ClearanceDto>();
					for (Object[] data : institutionReportViewImpl.reportList("SELECT mytask,\r\n" + 
							"(count(*)-sum(case when (status='rejected' ) then 1 else 0 end)),\r\n" + 
							"sum(case when (status='Not Started' ) then 1 else 0 end),\r\n" + 
							"sum(case when (status='pending' ) then 1 else 0 end),\r\n" + 
							"sum(case when (status='Completed' ) then 1 else 0 end),\r\n" + 
							"((sum(case when (status='Completed' ) then 1 else 0 end)*100)/(count(*)-sum(case when (status='rejected' ) then 1 else 0 end))) \r\n" + 
							"from InstitutionReportView group by mytask"
			)) {
				
				ClearanceDto userDtos = new ClearanceDto();
				
				//userDtos.setStrategicplan(data[0] + "");
				userDtos.setTaskName(data[1] + "");
				/*userDtos.setNumberOfActivities(Integer.parseInt(data[2] + ""));
				userDtos.setNotStarted(Integer.parseInt(data[3] + ""));
				userDtos.setPending(Integer.parseInt(data[4] + ""));
				userDtos.setCompleted(Integer.parseInt(data[5] + ""));*/
				userDtos.setRate(Double.parseDouble(data[6]+""));
				
				ClearanceDtoDetails.add(userDtos);
				this.name=new Gson().toJson(ClearanceDtoDetails);
				
			}	
			return(ClearanceDtoDetails);
			
			
		} catch (Exception e) {
			e.printStackTrace();
		}
		
		
		return null;
	}

	
	public ClearanceDto getStatistics() {
		return statistics;
	}

	public void setStatistics(ClearanceDto statistics) {
		this.statistics = statistics;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public String getConvertedData() {
		return convertedData;
	}

	public void setConvertedData(String convertedData) {
		this.convertedData = convertedData;
	}

	public String[] getNumber() {
		return number;
	}

	public void setNumber(String[] number) {
		this.number = number;
	}

	public String[] getValues() {
		return values;
	}

	public void setValues(String[] values) {
		this.values = values;
	}

	public InstitutionReportViewImpl getInstitutionReportViewImpl() {
		return institutionReportViewImpl;
	}

	public void setInstitutionReportViewImpl(InstitutionReportViewImpl institutionReportViewImpl) {
		this.institutionReportViewImpl = institutionReportViewImpl;
	}

	public List<GraphDto> getGraphDtoDetails() {
		return GraphDtoDetails;
	}

	public void setGraphDtoDetails(List<GraphDto> graphDtoDetails) {
		GraphDtoDetails = graphDtoDetails;
	}

	public List<InstitutionReportView> getElementsList() {
		return elementsList;
	}

	public void setElementsList(List<InstitutionReportView> elementsList) {
		this.elementsList = elementsList;
	}

	public int getSelectedBoard() {
		return selectedBoard;
	}

	public void setSelectedBoard(int selectedBoard) {
		this.selectedBoard = selectedBoard;
	}

	public JSFBoundleProvider getProvider() {
		return provider;
	}

	public void setProvider(JSFBoundleProvider provider) {
		this.provider = provider;
	}

	public List<ClearanceDto> getClearanceDtoDetails() {
		return ClearanceDtoDetails;
	}

	public void setClearanceDtoDetails(List<ClearanceDto> clearanceDtoDetails) {
		ClearanceDtoDetails = clearanceDtoDetails;
	}

	public ClearanceDto getClearanceDto() {
		return clearanceDto;
	}

	public void setClearanceDto(ClearanceDto clearanceDto) {
		this.clearanceDto = clearanceDto;
	}

	public static Logger getLogger() {
		return LOGGER;
	}
}
