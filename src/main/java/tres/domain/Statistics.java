package tres.domain;

import java.io.Serializable;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.NamedQuery;
import javax.persistence.OneToOne;
import javax.persistence.Table;
@Entity
@Table(name = "Statistics")
@NamedQuery(name = "Statistics.findAll", query = "SELECT r FROM Statistics r order by id desc")
public class Statistics extends CommonDomain implements Serializable {
	private static final long serialVersionUID = 1L;
	@Id
	@GeneratedValue(strategy=GenerationType.AUTO)
	private int id;
	private double marks;
	@OneToOne
	@JoinColumn(name = "statGraph")
	private StatGraph statGraph;
	public int getId() {
		return id;
	}
	public void setId(int id) {
		this.id = id;
	}
	public double getMarks() {
		return marks;
	}
	public void setMarks(double marks) {
		this.marks = marks;
	}
	public StatGraph getStatGraph() {
		return statGraph;
	}
	public void setStatGraph(StatGraph statGraph) {
		this.statGraph = statGraph;
	}
	
	
}
