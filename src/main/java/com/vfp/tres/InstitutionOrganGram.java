package com.vfp.tres;

import java.util.ArrayList;
import java.util.List;

import javax.annotation.ManagedBean;
import javax.annotation.PostConstruct;
import javax.faces.application.FacesMessage;
import javax.faces.bean.ViewScoped;
import javax.faces.context.FacesContext;

import org.primefaces.event.organigram.OrganigramNodeCollapseEvent;
import org.primefaces.event.organigram.OrganigramNodeDragDropEvent;
import org.primefaces.event.organigram.OrganigramNodeExpandEvent;
import org.primefaces.event.organigram.OrganigramNodeSelectEvent;
import org.primefaces.model.DefaultOrganigramNode;
import org.primefaces.model.OrganigramNode;

import tres.dao.impl.BoardImpl;
import tres.domain.Board;

@ManagedBean
@ViewScoped
public class InstitutionOrganGram {
	private OrganigramNode rootNode;
	private OrganigramNode selection;

	private boolean zoom = false;
	private String style = "widht:100%";
	private int leafNodeConnectionHeight = 0;
	private boolean autoScollToSelection = false;

	private String employeeName;

	private List<Board> boardList = new ArrayList<Board>();
	private Board board;
	BoardImpl boardImpl = new BoardImpl();

	@PostConstruct
	public void init() {
		if (board == null) {
			board = new Board();
		}
		try {
			
			selection = new DefaultOrganigramNode();
			selection = new DefaultOrganigramNode(null, "Ridvan Agar", null);

			rootNode = new DefaultOrganigramNode("root", "CommerceBay GmbH", null);
			rootNode.setCollapsible(false);
			rootNode.setDroppable(true);
			
			boardList = boardImpl.getListWithHQL("select b from Board b");
			for(Board board:boardList) {
				OrganigramNode softwareDevelopment = addDivision(rootNode, "Software Development", "Ridvan Agar"); 
			}

			OrganigramNode softwareDevelopment = addDivision(rootNode, "Software Development", "Ridvan Agar");

			OrganigramNode teamJavaEE = addDivision(softwareDevelopment, "Team JavaEE");
			addDivision(teamJavaEE, "JSF", "Thomas Andraschko");
			addDivision(teamJavaEE, "Backend", "Marie Louise");

			OrganigramNode teamMobile = addDivision(softwareDevelopment, "Team Mobile");
			addDivision(teamMobile, "Android", "Andy Ruby");
			addDivision(teamMobile, "iOS", "Stevan Jobs");

			addDivision(rootNode, "Managed Services", "Thorsten Schultze", "Sandra Becker");

			OrganigramNode marketing = addDivision(rootNode, "Marketing");
			addDivision(marketing, "Social Media", "Ali Mente", "Lisa Boehm");
			addDivision(marketing, "Press", "Michael Gmeiner", "Hans Peter");

			addDivision(rootNode, "Management", "Hassan El Manfalouty");
		} catch (Exception e) {
			// TODO: handle exception
		}

	}

	public Board getRoot() {
		try {
			board = boardImpl.getModelWithMyHQL(new String[] { "board" }, new Object[] { null }, "from Board");
			return board;
		} catch (Exception e) {
			return null;
		}
	}

	protected OrganigramNode addDivision(OrganigramNode parent, String name, String... employees) {
		OrganigramNode divisionNode = new DefaultOrganigramNode("division", name, parent);
		divisionNode.setDraggable(true);
		divisionNode.setSelectable(true);
		if (employees != null) {
			for (String employee : employees) {
				OrganigramNode employeeNode = new DefaultOrganigramNode("empoyee", employee, divisionNode);
				employeeNode.setDraggable(true);
				employeeNode.setSelectable(true);
			}
		}
		return divisionNode;
	}

	public void nodeDragDropListener(OrganigramNodeDragDropEvent event) {
		FacesMessage message = new FacesMessage();
		message.setSummary("Node'" + event.getOrganigramNode().getData() + "'Moved from."
				+ event.getSourceOrganigramNode() + "to" + event.getTargetOrganigramNode().getData() + "");
		message.setSeverity(FacesMessage.SEVERITY_INFO);

		FacesContext.getCurrentInstance().addMessage(null, message);
	}

	public void nodeSelecteListener(OrganigramNodeSelectEvent event) {
		FacesMessage message = new FacesMessage();
		message.setSummary("Node'" + event.getOrganigramNode().getData() + "'selected.");
		message.setSeverity(FacesMessage.SEVERITY_INFO);

		FacesContext.getCurrentInstance().addMessage(null, message);
	}

	public void nodeCollopseListener(OrganigramNodeCollapseEvent event) {
		FacesMessage message = new FacesMessage();
		message.setSummary("Node'" + event.getOrganigramNode().getData() + "'collopsed.");
		message.setSeverity(FacesMessage.SEVERITY_INFO);

		FacesContext.getCurrentInstance().addMessage(null, message);
	}

	public void nodeExpandListener(OrganigramNodeExpandEvent event) {
		FacesMessage message = new FacesMessage();
		message.setSummary("Node'" + event.getOrganigramNode().getData() + "'collopsed.");
		message.setSeverity(FacesMessage.SEVERITY_INFO);

		FacesContext.getCurrentInstance().addMessage(null, message);
	}

	public OrganigramNode getRootNode() {
		return rootNode;
	}

	public void setRootNode(OrganigramNode rootNode) {
		this.rootNode = rootNode;
	}

	public OrganigramNode getSelection() {
		return selection;
	}

	public void setSelection(OrganigramNode selection) {
		this.selection = selection;
	}

	public boolean isZoom() {
		return zoom;
	}

	public void setZoom(boolean zoom) {
		this.zoom = zoom;
	}

	public String getStyle() {
		return style;
	}

	public void setStyle(String style) {
		this.style = style;
	}

	public int getLeafNodeConnectionHeight() {
		return leafNodeConnectionHeight;
	}

	public void setLeafNodeConnectionHeight(int leafNodeConnectionHeight) {
		this.leafNodeConnectionHeight = leafNodeConnectionHeight;
	}

	public boolean isAutoScollToSelection() {
		return autoScollToSelection;
	}

	public void setAutoScollToSelection(boolean autoScollToSelection) {
		this.autoScollToSelection = autoScollToSelection;
	}

	public String getEmployeeName() {
		return employeeName;
	}

	public void setEmployeeName(String employeeName) {
		this.employeeName = employeeName;
	}

}
