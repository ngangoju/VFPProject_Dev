package tres.vfp.dto;

import java.io.Serializable;

public class GraphDto implements Serializable {
	private static final long serialVersionUID = 1L;
	 private int number;
	 private String Taskname;
	 private Double rate;
	public int getNumber() {
		return number;
	}
	public void setNumber(int number) {
		this.number = number;
	}
	public String getTaskname() {
		return Taskname;
	}
	public void setTaskname(String taskname) {
		Taskname = taskname;
	}
	public Double getRate() {
		return rate;
	}
	public void setRate(Double rate) {
		this.rate = rate;
	}
	 

}
