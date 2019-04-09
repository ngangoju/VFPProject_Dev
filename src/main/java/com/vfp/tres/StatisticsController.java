package com.vfp.tres;

import java.util.logging.Logger;

import javax.annotation.PostConstruct;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.RequestScoped;
import javax.faces.view.ViewScoped;
import javax.servlet.http.HttpSession;

import org.hibernate.HibernateException;

import com.google.gson.Gson;

import tres.common.DbConstant;
import tres.common.Formating;
import tres.common.JSFBoundleProvider;
import tres.common.JSFMessagers;
import tres.common.SessionUtils;
import tres.dao.impl.ActivityImpl;
import tres.dao.impl.BoardImpl;
import tres.dao.impl.InstitutionReportViewImpl;
import tres.dao.impl.StaffReportViewImpl;
import tres.dao.impl.StatGraphsImpl;
import tres.dao.impl.StatisticsImpl;
import tres.dao.impl.TaskAssignmentImpl;
import tres.dao.impl.TaskImpl;
import tres.dao.impl.UserImpl;

import tres.domain.InstitutionReportView;
import tres.domain.StatGraph;
import tres.domain.Statistics;
import tres.domain.Task;
import tres.domain.TaskAssignment;
import tres.domain.Users;
import tres.vfp.dto.ActivityDto;
import tres.vfp.dto.ClearanceDto;

import java.io.Serializable;
import java.sql.Timestamp;
import java.text.SimpleDateFormat;
import java.util.*;

@ManagedBean(name = "statController")
@ViewScoped
public class StatisticsController implements Serializable, DbConstant {
	private static final Logger LOGGER = Logger.getLogger(Thread.currentThread().getStackTrace()[0].getClassName());
	private String CLASSNAME = "StatisticsController :: ";
	private static final long serialVersionUID = 1L;
	private Statistics statistics = new Statistics();
	private String name;
	private String performance;
	private String overallPerformance;
	private boolean isValid;
	private String convertedData = "test";
	private String[] number;
	private String[] values;
	private String[] timespan = { DAILY, WEEKLY, MONTHLY, YEARLY };
	private String[] graphType = { ACTIVITYAPROVAL, ACTIVITYPLANNED, ACTIVITYCOMPLETED };
	private String[] type;
	private boolean renderformgraph;
	private boolean rendergraphvalue;
	private String graphtitle;
	private StatGraph graph;
	private StatGraphsImpl grapImpl = new StatGraphsImpl();
	private Statistics statistic;
	private StatisticsImpl statImpl = new StatisticsImpl();
	private Users usersSession;
	TaskAssignment taskAssign;
	private StatGraph graphSession;
	private String marks;
	// list of elements to be graphed
	JSFBoundleProvider provider = new JSFBoundleProvider();
	Timestamp timestamp = new Timestamp(Calendar.getInstance().getTime().getTime());
	private List<Statistics> elementsList = new ArrayList<Statistics>();
	private List<InstitutionReportView> viewList = new ArrayList<InstitutionReportView>();
	private List<ClearanceDto> staffPerformanceDtoDetails = new ArrayList<ClearanceDto>();
	InstitutionReportViewImpl institutionReportViewImpl = new InstitutionReportViewImpl();
	StaffReportViewImpl staffReportViewImpl = new StaffReportViewImpl();
	private List<ActivityDto> activityDetails = new ArrayList<ActivityDto>();
	ActivityImpl activityImpl = new ActivityImpl();
	ArrayList<ActivityDto> newList = new ArrayList<ActivityDto>();
	private Users userassigned;
	TaskImpl taskImpl = new TaskImpl();
	UserImpl usersImpl = new UserImpl();
	private List<TaskAssignment> taskAssignDetails = new ArrayList<TaskAssignment>();
	
	private List<TaskAssignment> supervisorStaffAssignTask = new ArrayList<TaskAssignment>();
	private List<TaskAssignment> listAssignTask = new ArrayList<TaskAssignment>();
	TaskAssignmentImpl taskAssignImpl = new TaskAssignmentImpl();
	private boolean renderTaskForm;
	private boolean renderTask;
	private boolean rendered;
	private boolean backBtn;
	private boolean rendersuperchart;
	private int selectedStaff;

	@SuppressWarnings("unchecked")
	public String create() {

		HttpSession session = SessionUtils.getSession();

		try {
			graphSession = (StatGraph) session.getAttribute("graphInfo");

			/* Statistics stat = new StatisticsImpl().saveActivity(statistics); */
			if (null != marks) {
				statistic.setStatGraph(graphSession);
				statistic.setCrtdDtTime(timestamp);
				statistic.setCreatedBy(usersSession.getViewId());
				statistic.setGenericStatus(ACTIVE);
				statistic.setMarks(Integer.parseInt(marks));
				statImpl.saveStatistics(statistic);
				setValid(true);
				JSFMessagers.addErrorMessage(getProvider().getValue("com.server.side.mark.details"));
				LOGGER.info(CLASSNAME + ":::Marks Details is saved");
				this.name = null;
				convertdata();
				elementsList = statImpl.getGenericListWithHQLParameter(
						new String[] { "genericStatus", "createdBy", "statGraph" },
						new Object[] { ACTIVE, usersSession.getViewId(), graphSession }, "Statistics", "id asc");
				this.rendergraphvalue = true;
			}

			return null;
		} catch (Exception e) {
			JSFMessagers.resetMessages();
			setValid(false);
			JSFMessagers.addErrorMessage(getProvider().getValue("com.server.side.internal.error"));
			LOGGER.info(CLASSNAME + "" + e.getMessage());
			e.printStackTrace();
		}
		return null;
	}

	@SuppressWarnings({ "unchecked", "static-access", "unlikely-arg-type" })
	@PostConstruct
	public void init() {

		HttpSession session = SessionUtils.getSession();
		usersSession = (Users) session.getAttribute("userSession");
		graphSession = (StatGraph) session.getAttribute("graphInfo");
		if (graph == null) {
			graph = new StatGraph();
		}

		if (statistic == null) {
			statistic = new Statistics();
		}
		if (null != usersSession) {
			userassigned = usersImpl.gettUserById(usersSession.getUserId(), "userId");
		}

		try {
			convertdata();
			//Clearance();
			// testCompletedAcitivity();
			
			institutionOverallPerformance();
			this.renderformgraph = true;

			elementsList = statImpl.getGenericListWithHQLParameter(
					new String[] { "genericStatus", "createdBy", "statGraph" },
					new Object[] { ACTIVE, usersSession.getViewId(), graphSession }, "Statistics", "id asc");
			 listAssignTask= taskAssignImpl.getGenericListWithHQLParameter(new String[] { "genericStatus", "user" },
					new Object[] { ACTIVE, userassigned }, "TaskAssignment", "upDtTime desc");
			 taskAssignDetails=staffReport(listAssignTask);
			this.renderTask = true;
			this.rendered = true;
			/*
			 * Formating fmt = new Formating(); // Listing DueDate for plan period
			 * ArrayList<String> al = new ArrayList<String>(); for (Object[] data :
			 * activityImpl
			 * .reportList("select activityId,dueDate from Activity where dueDate is not null"
			 * )) { fmt.getMysqlFormatV3((Date) data[1]); //
			 * LOGGER.info("Due Date::::::::::"+data[1]+""); ActivityDto actDto = new
			 * ActivityDto(); actDto.setActivityId(Integer.parseInt(data[0] + ""));
			 * actDto.setWeeklyPlan(fmt.getMysqlFormatV3((Date) data[1]));
			 * LOGGER.info("FORMATED DATE::::" + fmt.getMysqlFormatV3((Date) data[1]));
			 * 
			 * al.add(fmt.getMysqlFormatV3((Date) data[1]));
			 * 
			 * activityDetails.add(actDto); }
			 * 
			 * LOGGER.info("List with duplicates:" + al); Set<String> set = new
			 * LinkedHashSet<String>(al); al.clear(); al.addAll(set);
			 * LOGGER.info("List without duplicates:" + al);
			 */

			supervisorStaffAssignTask=superVisorStaffTask();
		} catch (Exception e) {
			setValid(false);
			JSFMessagers.addErrorMessage(getProvider().getValue("com.server.side.internal.error"));
			LOGGER.info(e.getMessage());
			e.printStackTrace();
		}

	}
	
	public List<TaskAssignment>staffReport(List<TaskAssignment>list){
		List<TaskAssignment>listdetails= new ArrayList<TaskAssignment>();
		for (TaskAssignment ass:list) {
			TaskAssignment taskAssign= new TaskAssignment();
			taskAssign.setTaskAssignmentId(ass.getTaskAssignmentId());
			taskAssign.setTask(ass.getTask());
			taskAssign.setUser(ass.getUser());
			taskAssign.setCrtdDtTime(ass.getCrtdDtTime());
			taskAssign.setTaskWeight(ass.getTask().getTaskWeight());
			if (ass.getTask().getTaskWeight().equals(LONG)) {
				taskAssign.setRedIcon(false);	
			}else {
				taskAssign.setRedIcon(true);	
			}
			if (ass.getTask().getTaskWeight().equals(SHORT)) {
				taskAssign.setGreenIcon(false);	
			}else {
				taskAssign.setGreenIcon(true);	
			}
			
			if (ass.getTask().getTaskWeight().equals(MEDIUM)) {
				taskAssign.setYellowIcon(false);	
			}else {
				taskAssign.setYellowIcon(true);	
			}
			listdetails.add(taskAssign);
		}
		return(listdetails);
		
	}
	public List<TaskAssignment>superVisorStaffTask(){
		List<TaskAssignment> staffAssignTask = new ArrayList<TaskAssignment>();
		for (Object[] data :taskAssignImpl.reportList("Select ass.taskAssignmentId ,ass.task, ass.user,ass.crtdDtTime,tsk.taskWeight from TaskAssignment ass,Task tsk,Users us,Board b,StrategicPlan s "
				+ "where tsk.taskId=ass.task and us.userId=ass.user and b.boardId=us.board and tsk.strategicPlan=s.planId and s.genericStatus='"+ACTIVE+"' and "
				+ "us.board="+usersSession.getBoard().getBoardId()+" and ass.genericStatus='"+ACTIVE+"'")) {
			TaskAssignment taskAssign= new TaskAssignment();
			taskAssign.setTaskAssignmentId(Integer.parseInt(data[0]+""));
			taskAssign.setTask((Task)data[1]);
			taskAssign.setUser((Users)data[2]);
			taskAssign.setCrtdDtTime((Timestamp)data[3]);
			taskAssign.setTaskWeight(data[4]+"");
			if (data[4].equals(LONG)) {
				taskAssign.setRedIcon(false);	
			}else {
				taskAssign.setRedIcon(true);	
			}
			if (data[4].equals(SHORT)) {
				taskAssign.setGreenIcon(false);	
			}else {
				taskAssign.setGreenIcon(true);	
			}
			
			if (data[4].equals(MEDIUM)) {
				taskAssign.setYellowIcon(false);	
			}else {
				taskAssign.setYellowIcon(true);	
			}
			staffAssignTask.add(taskAssign);
		}
		return(staffAssignTask);
		
	}

	public String getMyFormattedDate(Statistics statDate) {
		return new SimpleDateFormat("dd-MM-yyyy").format(statDate.getCrtdDtTime());
	}

	public String saveGraphDetails() {
		try {
			HttpSession session = SessionUtils.getSession();
			try {

				StatGraph graph = new StatGraph();
				if (null != graphtitle)
					graph = grapImpl.getModelWithMyHQL(new String[] { "graphTitle" }, new Object[] { graphtitle },
							"from StatGraph");
				if (null != graph) {

					JSFMessagers.resetMessages();
					setValid(false);
					JSFMessagers.addErrorMessage(getProvider().getValue("error.server.side.dupicate.Graphtitle"));
					LOGGER.info(CLASSNAME + "sivaserside validation :: Graph title already  recorded in the system! ");
					return null;
				}

			} catch (Exception e) {
				JSFMessagers.resetMessages();
				setValid(false);
				JSFMessagers.addErrorMessage(getProvider().getValue("com.server.side.internal.error"));
				LOGGER.info(CLASSNAME + "" + e.getMessage());
				e.printStackTrace();
				return null;
			}
			graph.setCreatedBy(usersSession.getViewId());
			graph.setCreationDate(timestamp);
			graph.setGenericStatus(ACTIVE);
			graph.setFullNames(usersSession.getFname() + " " + usersSession.getLname());
			graph.setPosition(usersSession.getUserCategory().getUsercategoryName());
			graph.setCrtdDtTime(timestamp);
			graph.setGraphTitle(graphtitle);
			grapImpl.saveStatGraph(graph);
			session.setAttribute("graphInfo", graph);
			setValid(true);
			JSFMessagers.addErrorMessage(getProvider().getValue("com.server.side.graph.details"));
			LOGGER.info(CLASSNAME + ":::Graph Details is saved");

			return "/menu/StatisticGraph.xhtml?faces-redirect=true";
		} catch (HibernateException e) {
			LOGGER.info(CLASSNAME + ":::Graph Details is fail with HibernateException  error");
			JSFMessagers.resetMessages();
			setValid(false);
			JSFMessagers.addErrorMessage(getProvider().getValue("com.server.side.internal.errorsession"));
			LOGGER.info(CLASSNAME + "" + e.getMessage());
			e.printStackTrace();
			return "";
		}

	}

	@SuppressWarnings("unchecked")
	public void convertdata() throws Exception {
		List<Statistics> list2 = new ArrayList<Statistics>();
		System.out.println("here --------------------------------");
		// list2 = new StatisticsImpl().getListStatistics();
		list2 = statImpl.getGenericListWithHQLParameter(new String[] { "genericStatus", "createdBy", "statGraph" },
				new Object[] { ACTIVE, usersSession.getViewId(), graphSession }, "Statistics", "id asc");
		this.name = new Gson().toJson(list2);

	}

	public void testCompletedAcitivity() {
		HttpSession session = SessionUtils.getSession();
		// Get the values from the session
		taskAssign = (TaskAssignment) session.getAttribute("taskstatistics");
		staffPerformanceDtoDetails = new ArrayList<ClearanceDto>();
		for (Object[] data : staffReportViewImpl.reportList("SELECT DATE_FORMAT(dueDate,'%d/%m/%Y'),mytask,\r\n"
				+ "((sum(case when (status='Completed') then 1 else 0 end)*100)/(sum(case when(status='Completed') then 1 else 0 end)+sum(case when (status='Approved') then 1 else 0 end)))\r\n"
				+ "from StaffReportView where dueDate is not null and mytask='" + taskAssign.getTask().getTaskName()
				+ "' group by DATE_FORMAT(dueDate,'%d/%m/%Y') \r\n" + "")) {
			ClearanceDto userDtos = new ClearanceDto();
			userDtos.setDueDate(data[0] + "");
			userDtos.setTaskName(data[1] + "");
			userDtos.setRate(Double.parseDouble(data[2] + ""));
			LOGGER.info("Due Date Founded:::::::" + data[0] + "::::::Task Performed::::" + data[1]
					+ ":::::Rate Founded::" + data[2] + "");

		}
	}

	public List<ClearanceDto> Clearance() {
		try {
			HttpSession session = SessionUtils.getSession();
			taskAssign = (TaskAssignment) session.getAttribute("taskstatistics");
			staffPerformanceDtoDetails = new ArrayList<ClearanceDto>();
			int i = 1;
			for (Object[] data : staffReportViewImpl.reportList(
					"SELECT DATE_FORMAT(dueDate,'%d/%m/%Y') as dueDate, mytask as MyTask,((sum(ActivityPlanned)*100)/(sum(ActivityPlanned))) as TotalPlannedActivity,sum(case when (ActivityApproved>0) then 1 else 0 end) as TotalApprovedActivity,((sum(ActivityApproved)*100)/(sum(ActivityPlanned))) as ApprovedActivity,\r\n"
							+ "((sum(case when (status='Rejected') then 1 else 0 end)*100)/(sum(case when (ActivityPlanned>0) then 1 else 0 end))) as RejectedActivity,((sum(ActivityReported)*100)/(sum(ActivityApproved))) as ReportedActivity,((sum(case when (status='Completed') then 1 else 0 end)*100)/(sum(ActivityReported))) as CompletedActivity,\r\n"
							+ "((sum(case when (status='Failed') then 1 else 0 end)*100)/(sum(ActivityReported))) as FailedActivity\r\n"
							+ "from StaffReportView  where dueDate is not null and  mytask='" + taskAssign.getTask().getTaskName()
							 + "' group by DATE_FORMAT(dueDate,'%d/%m/%Y') order by Month(dueDate),Day(dueDate)")) {

				LOGGER.info("::::::::Planned:::::::" + data[2] + ":::Approved:::::" + data[8] + "::::Rejected::::"
						+ data[4] + ":::Reported::::::" + data[5] + "::::::::Completed::::::" + data[6]);
				ClearanceDto userDtos = new ClearanceDto();
				// userDtos.setPlanned(Integer.parseInt(data[2] + ""));
				if (null == data[4]) {
					userDtos.setApproved(defaultCount);

				} else {
					userDtos.setApproved(Integer.parseInt(data[4] + ""));
				}
				if (null == data[5]) {
					userDtos.setRejected(defaultCount);

				} else {
					userDtos.setRejected(Integer.parseInt(data[5] + ""));
				}
				if (null == data[6]) {
					userDtos.setReported(defaultCount);

				} else {
					userDtos.setReported(Integer.parseInt(data[6] + ""));
				}
				userDtos.setDueDate(data[0] + "" +"[Week "+i+"]");
				LOGGER.info("::::::::Due Date:::::::" +userDtos.getDueDate());
				if (null == data[7]) {
					userDtos.setCompleted(defaultCount);

				} else {
					userDtos.setCompleted(Integer.parseInt(data[7] + ""));
				}
				if (null == data[8]) {
					userDtos.setFailed(defaultCount);
					LOGGER.info("::::::::FAILED:::::::" + data[8]);
				} else {
					userDtos.setFailed(Integer.parseInt(data[8] + ""));
					LOGGER.info("::::::::FAILED:::::::" + data[8]);
				}
				i++;
				staffPerformanceDtoDetails.add(userDtos);
			}

			this.performance = new Gson().toJson(staffPerformanceDtoDetails);

		} catch (Exception e) {
			e.printStackTrace();
		}
		return (staffPerformanceDtoDetails);
	}

	public void institutionOverallPerformance() {
		try {
			staffPerformanceDtoDetails = new ArrayList<ClearanceDto>();
			for (Object[] data : staffReportViewImpl.reportList("SELECT((sum(ac.countPlanned)*100)/(sum(ac.countPlanned))),((sum(ac.countApproved)*100)/(sum(ac.countPlanned))),((sum(case when (ac.status='Rejected') then 1 else 0 end)*100)/(sum(ac.countPlanned))), ((sum(ac.countReported)*100)/(sum(ac.countPlanned))),((sum(case when (ac.status='Completed') then 1 else 0 end)*100)/(sum(ac.countPlanned))),((sum(case when (ac.status='Failed') then 1 else 0 end)*100)/(sum(ac.countPlanned))),tsk.taskName from Activity ac,Task tsk,StrategicPlan plan where tsk.taskId=ac.task and tsk.strategicPlan=plan.planId and plan.genericStatus ='active' group by ac.task")) {
				ClearanceDto userDtos = new ClearanceDto();
				if(null==data[0]) {
					userDtos.setPlanned(defaultCount);	
				}else {
					userDtos.setPlanned(Integer.parseInt(data[0]+""));		
				}
				if(null==data[1]) {
					userDtos.setApproved(defaultCount);
				}else {
					userDtos.setApproved(Integer.parseInt(data[1]+""));		
				}
				if(null==data[2]) {
					userDtos.setRejected(defaultCount);
				}else {
					userDtos.setRejected(Integer.parseInt(data[2]+""));	
				}
				if(null==data[3]) {
					userDtos.setReported(defaultCount);
				}else {
					userDtos.setReported(Integer.parseInt(data[3]+""));
				}
				if(null==data[4]) {
					userDtos.setCompleted(defaultCount);
				}else {
					userDtos.setCompleted(Integer.parseInt(data[4]+""));
				}
				if(null==data[5]) {
					userDtos.setFailed(defaultCount);	
				}else {
					userDtos.setFailed(Integer.parseInt(data[5]+""));	
				}
				userDtos.setTaskName(data[6]+"");
				staffPerformanceDtoDetails.add(userDtos);

			}
			this.overallPerformance = new Gson().toJson(staffPerformanceDtoDetails);

		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	@SuppressWarnings("unlikely-arg-type")
	public void showGraph() {
		if (type.equals(ACTIVITYAPROVAL)) {

		} else if (type.equals(ACTIVITYCOMPLETED)) {

		} else if (type.equals(ACTIVITYPLANNED)) {

		}
	}

	public void showStatistics(TaskAssignment info) {
		HttpSession sessionuser = SessionUtils.getSession();
		if (null != info) {
			sessionuser.setAttribute("taskstatistics", info);
			LOGGER.info("Info Founded are TaskAssId:>>>>>>>>>>>>>>>>>>>>>>>:" + info.getTaskAssignmentId()
					+ "taskname:::" + info.getTask().getTaskName() + "Task desc::" + info.getTask().getDescription()
					+ "USERINFO::" + info.getUser().getUserId());
			taskAssign = showAssignedTask();
			staffPerformanceDtoDetails = new ArrayList<ClearanceDto>();
			staffPerformanceDtoDetails=Clearance();
			if(staffPerformanceDtoDetails.size()>0) {
				this.renderTask = false;
				this.renderTaskForm = true;
				//this.rendered = false;
				this.backBtn = true;
				Clearance();
			}else {
				JSFMessagers.resetMessages();
				setValid(false);
				JSFMessagers.addErrorMessage(getProvider().getValue("com.staff.statistics"));
				LOGGER.info(CLASSNAME + ":::There is no statistics to display on this target ,it does not have any activity planned");
			}
			
			
		}
	}

	public TaskAssignment showAssignedTask() {
		HttpSession session = SessionUtils.getSession();
		// Get the values from the session
		taskAssign = (TaskAssignment) session.getAttribute("taskstatistics");
		return taskAssign;
	}

	public String getMyFormattedDate(TaskAssignment statDate) {
		return new SimpleDateFormat("dd-MM-yyyy").format(statDate.getCrtdDtTime());
	}

	public String getMyFormattedDueDate(TaskAssignment dueDate) {
		return new SimpleDateFormat("dd-MM-yyyy").format(dueDate.getTask().getDueDate());
	}

	public String getMyFormattedEndDate(TaskAssignment endDate) {
		return new SimpleDateFormat("dd-MM-yyyy").format(endDate.getTask().getEndDate());
	}

	public Statistics getStatistics() {
		return statistics;
	}

	public void setStatistics(Statistics statistics) {
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

	public List<Statistics> getElementsList() {
		return elementsList;
	}

	public void setElementsList(List<Statistics> elementsList) {
		this.elementsList = elementsList;
	}

	public String[] getTimespan() {
		return timespan;
	}

	public void setTimespan(String[] timespan) {
		this.timespan = timespan;
	}

	public boolean isRenderformgraph() {
		return renderformgraph;
	}

	public void setRenderformgraph(boolean renderformgraph) {
		this.renderformgraph = renderformgraph;
	}

	public boolean isRendergraphvalue() {
		return rendergraphvalue;
	}

	public void setRendergraphvalue(boolean rendergraphvalue) {
		this.rendergraphvalue = rendergraphvalue;
	}

	public String getGraphtitle() {
		return graphtitle;
	}

	public void setGraphtitle(String graphtitle) {
		this.graphtitle = graphtitle;
	}

	public StatGraph getGraph() {
		return graph;
	}

	public void setGraph(StatGraph graph) {
		this.graph = graph;
	}

	public StatGraphsImpl getGrapImpl() {
		return grapImpl;
	}

	public void setGrapImpl(StatGraphsImpl grapImpl) {
		this.grapImpl = grapImpl;
	}

	public Statistics getStatistic() {
		return statistic;
	}

	public void setStatistic(Statistics statistic) {
		this.statistic = statistic;
	}

	public StatisticsImpl getStatImpl() {
		return statImpl;
	}

	public void setStatImpl(StatisticsImpl statImpl) {
		this.statImpl = statImpl;
	}

	public boolean isValid() {
		return isValid;
	}

	public void setValid(boolean isValid) {
		this.isValid = isValid;
	}

	public JSFBoundleProvider getProvider() {
		return provider;
	}

	public void setProvider(JSFBoundleProvider provider) {
		this.provider = provider;
	}

	public Timestamp getTimestamp() {
		return timestamp;
	}

	public void setTimestamp(Timestamp timestamp) {
		this.timestamp = timestamp;
	}

	public Users getUsersSession() {
		return usersSession;
	}

	public void setUsersSession(Users usersSession) {
		this.usersSession = usersSession;
	}

	public StatGraph getGraphSession() {
		return graphSession;
	}

	public void setGraphSession(StatGraph graphSession) {
		this.graphSession = graphSession;
	}

	public String getMarks() {
		return marks;
	}

	public void setMarks(String marks) {
		this.marks = marks;
	}

	public String getPerformance() {
		return performance;
	}

	public void setPerformance(String performance) {
		this.performance = performance;
	}

	public List<InstitutionReportView> getViewList() {
		return viewList;
	}

	public void setViewList(List<InstitutionReportView> viewList) {
		this.viewList = viewList;
	}

	public List<ClearanceDto> getStaffPerformanceDtoDetails() {
		return staffPerformanceDtoDetails;
	}

	public void setStaffPerformanceDtoDetails(List<ClearanceDto> staffPerformanceDtoDetails) {
		this.staffPerformanceDtoDetails = staffPerformanceDtoDetails;
	}

	public InstitutionReportViewImpl getInstitutionReportViewImpl() {
		return institutionReportViewImpl;
	}

	public void setInstitutionReportViewImpl(InstitutionReportViewImpl institutionReportViewImpl) {
		this.institutionReportViewImpl = institutionReportViewImpl;
	}

	public List<ActivityDto> getActivityDetails() {
		return activityDetails;
	}

	public void setActivityDetails(List<ActivityDto> activityDetails) {
		this.activityDetails = activityDetails;
	}

	public ActivityImpl getActivityImpl() {
		return activityImpl;
	}

	public void setActivityImpl(ActivityImpl activityImpl) {
		this.activityImpl = activityImpl;
	}

	public StaffReportViewImpl getStaffReportViewImpl() {
		return staffReportViewImpl;
	}

	public void setStaffReportViewImpl(StaffReportViewImpl staffReportViewImpl) {
		this.staffReportViewImpl = staffReportViewImpl;
	}

	public Users getUserassigned() {
		return userassigned;
	}

	public void setUserassigned(Users userassigned) {
		this.userassigned = userassigned;
	}

	public UserImpl getUsersImpl() {
		return usersImpl;
	}

	public void setUsersImpl(UserImpl usersImpl) {
		this.usersImpl = usersImpl;
	}

	public List<TaskAssignment> getTaskAssignDetails() {
		return taskAssignDetails;
	}

	public void setTaskAssignDetails(List<TaskAssignment> taskAssignDetails) {
		this.taskAssignDetails = taskAssignDetails;
	}

	public TaskAssignmentImpl getTaskAssignImpl() {
		return taskAssignImpl;
	}

	public void setTaskAssignImpl(TaskAssignmentImpl taskAssignImpl) {
		this.taskAssignImpl = taskAssignImpl;
	}

	public TaskImpl getTaskImpl() {
		return taskImpl;
	}

	public void setTaskImpl(TaskImpl taskImpl) {
		this.taskImpl = taskImpl;
	}

	public boolean isRenderTaskForm() {
		return renderTaskForm;
	}

	public void setRenderTaskForm(boolean renderTaskForm) {
		this.renderTaskForm = renderTaskForm;
	}

	public boolean isRenderTask() {
		return renderTask;
	}

	public void setRenderTask(boolean renderTask) {
		this.renderTask = renderTask;
	}

	public boolean isRendered() {
		return rendered;
	}

	public void setRendered(boolean rendered) {
		this.rendered = rendered;
	}

	public boolean isBackBtn() {
		return backBtn;
	}

	public void setBackBtn(boolean backBtn) {
		this.backBtn = backBtn;
	}

	public String[] getGraphType() {
		return graphType;
	}

	public void setGraphType(String[] graphType) {
		this.graphType = graphType;
	}

	public String[] getType() {
		return type;
	}

	public void setType(String[] type) {
		this.type = type;
	}

	public TaskAssignment getTaskAssign() {
		return taskAssign;
	}

	public void setTaskAssign(TaskAssignment taskAssign) {
		this.taskAssign = taskAssign;
	}

	public String getOverallPerformance() {
		return overallPerformance;
	}

	public void setOverallPerformance(String overallPerformance) {
		this.overallPerformance = overallPerformance;
	}

	public List<TaskAssignment> getSupervisorStaffAssignTask() {
		return supervisorStaffAssignTask;
	}

	public void setSupervisorStaffAssignTask(List<TaskAssignment> supervisorStaffAssignTask) {
		this.supervisorStaffAssignTask = supervisorStaffAssignTask;
	}

	public ArrayList<ActivityDto> getNewList() {
		return newList;
	}

	public void setNewList(ArrayList<ActivityDto> newList) {
		this.newList = newList;
	}

	public List<TaskAssignment> getListAssignTask() {
		return listAssignTask;
	}

	public void setListAssignTask(List<TaskAssignment> listAssignTask) {
		this.listAssignTask = listAssignTask;
	}

	public boolean isRendersuperchart() {
		return rendersuperchart;
	}

	public void setRendersuperchart(boolean rendersuperchart) {
		this.rendersuperchart = rendersuperchart;
	}

	public int getSelectedStaff() {
		return selectedStaff;
	}

	public void setSelectedStaff(int selectedStaff) {
		this.selectedStaff = selectedStaff;
	}

}
