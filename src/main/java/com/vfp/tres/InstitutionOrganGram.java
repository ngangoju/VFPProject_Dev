package com.vfp.tres;

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

@ManagedBean
@ViewScoped
public class InstitutionOrganGram {
	private OrganigramNode rootNode;
	private OrganigramNode selection;

	private boolean zoom = false;
	private String style = "widht:800px";
	private int leafNodeConnectionHeight = 0;
	private boolean autoScollToSelection = false;

	private String employeeName;

	@PostConstruct
	public void init() {
		selection = new DefaultOrganigramNode();
		rootNode = new DefaultOrganigramNode("root", "CommerceBay", null);
		rootNode.setCollapsible(false);
		rootNode.setDroppable(true);

		OrganigramNode hd = addDivision(rootNode, "Software", "Rittt");

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
