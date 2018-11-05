package com.vfp.tres;

import java.util.ArrayList;
import java.util.List;

import javax.annotation.PostConstruct;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.SessionScoped;
import javax.faces.bean.ViewScoped;

import com.google.gson.Gson;
import com.google.gson.JsonObject;

import tres.dao.impl.BoardImpl;
import tres.domain.Board;
import tres.domain.BoardTemplate;

@ManagedBean(name = "organigram")
@ViewScoped
public class OrganigramBean {
	private List<Board> boardList = new ArrayList<Board>();
	private String jsonData = "hello wolrd";
	private int childId;

	@PostConstruct
	public void addElement() {
		boardList = new BoardImpl().getListBoards();
		System.out.println("--------------------------------" + boardList);
		this.convertToJson(boardList);
	}

	public void convertToJson(List<Board> list) {
		List<BoardTemplate> list2 = new ArrayList<BoardTemplate>();

		for (Board x : list) {
			BoardTemplate t = new BoardTemplate();
			t.setId(x.getBoardId());
			t.setName(x.getBoardName());
			if (x.getBoard() == null) {
				t.setParent(0);
			} else {
				t.setParent(x.getBoard().getBoardId());
			}

			list2.add(t);
		}
		this.jsonData = new Gson().toJson(list2);
	}

	public List<Board> getBoardList() {
		return boardList;
	}

	public void setBoardList(List<Board> boardList) {
		this.boardList = boardList;
	}

	public String getJsonData() {
		return jsonData;
	}

	public void setJsonData(String jsonData) {
		this.jsonData = jsonData;
	}

	public int getChildId() {
		return childId;
	}

	public void setChildId(int childId) {
		this.childId = childId;
	}

}
