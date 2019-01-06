package tres.domain;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;

@Entity
public class Statistics {
	@Id
	@GeneratedValue(strategy=GenerationType.AUTO)
	private int id;
	private double marks;
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
	
}
