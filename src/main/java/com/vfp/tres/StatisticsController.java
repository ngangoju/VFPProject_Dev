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
import tres.common.JSFBoundleProvider;
import tres.common.JSFMessagers;
import tres.common.SessionUtils;
import tres.dao.impl.StatGraphsImpl;
import tres.dao.impl.StatisticsImpl;
import tres.domain.Board;
import tres.domain.BoardTemplate;
import tres.domain.Contact;
import tres.domain.StatGraph;
import tres.domain.Statistics;
import tres.domain.Users;

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
	private boolean isValid;
	private String convertedData = "test";
	private String[] number;
	private String[] values;
	private String[] timespan = { DAILY, WEEKLY, MONTHLY, YEARLY };
	private boolean renderformgraph;
	private boolean rendergraphvalue;
	private String graphtitle;
	private StatGraph graph;
	private StatGraphsImpl grapImpl = new StatGraphsImpl();
	private Statistics statistic;
	private StatisticsImpl statImpl = new StatisticsImpl();
	private Users usersSession;
	private StatGraph graphSession;
	private String marks;
	// list of elements to be graphed
	JSFBoundleProvider provider = new JSFBoundleProvider();
	Timestamp timestamp = new Timestamp(Calendar.getInstance().getTime().getTime());

	private List<Statistics> elementsList = new ArrayList<Statistics>();

	@SuppressWarnings("unchecked")
	public String create() {
		
		HttpSession session = SessionUtils.getSession();
		
		try {
			graphSession=(StatGraph)session.getAttribute("graphInfo");
			
			/*Statistics stat = new StatisticsImpl().saveActivity(statistics);*/
			if(null!=marks) {
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
				elementsList=statImpl.getGenericListWithHQLParameter(new String[] { "genericStatus","createdBy","statGraph"},
						new Object[] { ACTIVE,usersSession.getViewId(),graphSession }, "Statistics", "id asc");
				this.rendergraphvalue=true;
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

	@SuppressWarnings("unchecked")
	@PostConstruct
	public void init() {
		
		HttpSession session = SessionUtils.getSession();
		usersSession = (Users) session.getAttribute("userSession");
		graphSession=(StatGraph)session.getAttribute("graphInfo");
		if (graph == null) {
			graph = new StatGraph();
		}

		if (statistic == null) {
			statistic = new Statistics();
		}
		
		try {
			convertdata();
			this.renderformgraph = true;
			elementsList=statImpl.getGenericListWithHQLParameter(new String[] { "genericStatus","createdBy","statGraph"},
					new Object[] { ACTIVE,usersSession.getViewId(),graphSession }, "Statistics", "id asc");
		} catch (Exception e) {
			setValid(false);
			JSFMessagers.addErrorMessage(getProvider().getValue("com.server.side.internal.error"));
			LOGGER.info(e.getMessage());
			e.printStackTrace();
		}
		
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
			graph.setFullNames(usersSession.getFname()+" "+usersSession.getLname());
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
		//list2 = new StatisticsImpl().getListStatistics();
		list2=statImpl.getGenericListWithHQLParameter(new String[] { "genericStatus","createdBy","statGraph"},
				new Object[] { ACTIVE,usersSession.getViewId(),graphSession }, "Statistics", "id asc");
		this.name = new Gson().toJson(list2);

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

}
