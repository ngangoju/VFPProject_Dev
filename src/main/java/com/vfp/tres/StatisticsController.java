package com.vfp.tres;


import java.util.ArrayList;
import java.util.List;

import javax.annotation.PostConstruct;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.RequestScoped;
import javax.faces.view.ViewScoped;

import com.google.gson.Gson;

import tres.dao.impl.StatisticsImpl;
import tres.domain.Board;
import tres.domain.BoardTemplate;
import tres.domain.Statistics;
import java.util.*;

@ManagedBean(name="statController")
@RequestScoped
public class StatisticsController {
	private Statistics statistics=new Statistics();
	private String name;
	private String convertedData="test";
	private String[] number;
	private String[] values;
	//list of elements to be graphed
	private List<Statistics> elementsList = new StatisticsImpl().getListActivities();
	
	public String create() {
		Statistics stat=new StatisticsImpl().saveActivity(statistics);
		statistics=new Statistics();
		this.name=null;
		convertdata();
		return "FormExample.xhtml?faces-redirect=true";
	} 
	@PostConstruct
	public void init() {
		convertdata();
	}
	
	public void convertdata() {
		List<Statistics> list2 = new ArrayList<Statistics>();
		System.out.println("here --------------------------------");
		list2=new StatisticsImpl().getListActivities();
		
		this.name=new Gson().toJson(list2);
		
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
	
	
	
	
}
