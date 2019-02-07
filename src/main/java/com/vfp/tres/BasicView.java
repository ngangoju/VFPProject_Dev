package com.vfp.tres;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.List;

import javax.annotation.PostConstruct;
import javax.faces.application.FacesMessage;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.ViewScoped;
import javax.faces.context.FacesContext;
import javax.servlet.http.HttpSession;

import org.primefaces.event.timeline.TimelineSelectEvent;
import org.primefaces.model.timeline.TimelineEvent;
import org.primefaces.model.timeline.TimelineModel;

import tres.common.DbConstant;
import tres.common.SessionUtils;
import tres.dao.impl.InstitutionImpl;
import tres.dao.impl.InstitutionRegRequestImpl;
import tres.dao.impl.StrategicPlanImpl;
import tres.dao.impl.UserImpl;
import tres.domain.Institution;
import tres.domain.InstitutionRegistrationRequest;
import tres.domain.StrategicPlan;
import tres.domain.Users;

@ManagedBean(name = "basicView")
@ViewScoped
public class BasicView implements Serializable, DbConstant {

	private static final long serialVersionUID = 1L;
	private TimelineModel model;

	private boolean selectable = true;
	private boolean zoomable = true;
	private boolean moveable = true;
	private boolean stackEvents = true;
	private String eventStyle = "box";
	private boolean axisOnTop;
	private boolean showCurrentTime = true;
	private boolean showNavigation = false;
	private Institution institution;
	private InstitutionRegistrationRequest request;
	private Users usersSession;

	private List<StrategicPlan> strategicPlanDetails = new ArrayList<StrategicPlan>();
	StrategicPlanImpl strategicPlanImpl = new StrategicPlanImpl();
	UserImpl usersImpl = new UserImpl();
	InstitutionImpl institutionImpl = new InstitutionImpl();
	InstitutionRegRequestImpl requestImpl = new InstitutionRegRequestImpl();

	@SuppressWarnings("unchecked")
	@PostConstruct
	protected void initialize() {
		HttpSession session = SessionUtils.getSession();
		usersSession = (Users) session.getAttribute("userSession");

		if (request == null) {
			request = new InstitutionRegistrationRequest();
		}
		if (institution == null) {
			institution = new Institution();
		}
		try {

			strategicPlanDetails = strategicPlanImpl.getGenericListWithHQLParameter(new String[] { "createdBy" },
					new Object[] { usersSession.getFname() + " " + usersSession.getLname() }, "StrategicPlan",
					"planId asc");
			model = new TimelineModel();
			for (StrategicPlan stpl : strategicPlanDetails) {
				Calendar cal = Calendar.getInstance();
				cal.setTime(stpl.getStartDate());
				// cal.set(2019, Calendar.JUNE, 12, 0, 0, 0);
				model.add(new TimelineEvent(stpl.getDetails(), cal.getTime()));
			}

		} catch (Exception e) {
			// TODO: handle exception
		}
	}

	public void onSelect(TimelineSelectEvent e) {
		TimelineEvent timelineEvent = e.getTimelineEvent();

		FacesMessage msg = new FacesMessage(FacesMessage.SEVERITY_INFO, "Selected event:",
				timelineEvent.getData().toString());
		FacesContext.getCurrentInstance().addMessage(null, msg);
	}

	public TimelineModel getModel() {
		return model;
	}

	public boolean isSelectable() {
		return selectable;
	}

	public void setSelectable(boolean selectable) {
		this.selectable = selectable;
	}

	public boolean isZoomable() {
		return zoomable;
	}

	public void setZoomable(boolean zoomable) {
		this.zoomable = zoomable;
	}

	public boolean isMoveable() {
		return moveable;
	}

	public void setMoveable(boolean moveable) {
		this.moveable = moveable;
	}

	public boolean isStackEvents() {
		return stackEvents;
	}

	public void setStackEvents(boolean stackEvents) {
		this.stackEvents = stackEvents;
	}

	public String getEventStyle() {
		return eventStyle;
	}

	public void setEventStyle(String eventStyle) {
		this.eventStyle = eventStyle;
	}

	public boolean isAxisOnTop() {
		return axisOnTop;
	}

	public void setAxisOnTop(boolean axisOnTop) {
		this.axisOnTop = axisOnTop;
	}

	public boolean isShowCurrentTime() {
		return showCurrentTime;
	}

	public void setShowCurrentTime(boolean showCurrentTime) {
		this.showCurrentTime = showCurrentTime;
	}

	public boolean isShowNavigation() {
		return showNavigation;
	}

	public void setShowNavigation(boolean showNavigation) {
		this.showNavigation = showNavigation;
	}
}
