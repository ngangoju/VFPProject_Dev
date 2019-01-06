/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package tres.domain;

import java.io.Serializable;
import java.util.Date;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.NamedQuery;
import javax.persistence.Table;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;
import javax.persistence.Transient;

/**
 *
 * @author Gwiza
 */
@Entity
@Table(name = "StatGraph")
@NamedQuery(name = "StatGraph.findAll", query = "SELECT r FROM StatGraph r order by graphId desc")
public class StatGraph extends CommonDomain implements Serializable {

	private static final long serialVersionUID = 1L;
	@Id
	@GeneratedValue
	@Column(name = "graphId")
	private int graphId;
	@Column(name = "graphTitle", unique = true)
	private String graphTitle;

	@Column(name = "timeSpan")
	private String timeSpan;

	@Column(name = "creationDate", columnDefinition = "DATETIME")
	@Temporal(TemporalType.TIMESTAMP)
	private Date creationDate;

	@Column(name = "position")
	private String position;

	@Column(name = "fullNames")
	private String fullNames;

	@Transient
	private String action;

	public int getGraphId() {
		return graphId;
	}

	public void setGraphId(int graphId) {
		this.graphId = graphId;
	}

	public String getGraphTitle() {
		return graphTitle;
	}

	public void setGraphTitle(String graphTitle) {
		this.graphTitle = graphTitle;
	}

	public String getTimeSpan() {
		return timeSpan;
	}

	public void setTimeSpan(String timeSpan) {
		this.timeSpan = timeSpan;
	}

	public Date getCreationDate() {
		return creationDate;
	}

	public void setCreationDate(Date creationDate) {
		this.creationDate = creationDate;
	}

	public String getPosition() {
		return position;
	}

	public void setPosition(String position) {
		this.position = position;
	}

	public String getFullNames() {
		return fullNames;
	}

	public void setFullNames(String fullNames) {
		this.fullNames = fullNames;
	}

	public String getAction() {
		return action;
	}

	public void setAction(String action) {
		this.action = action;
	}

}
