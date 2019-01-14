package com.vfp.tres;

import java.io.Serializable;
import java.sql.Timestamp;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.List;
import java.util.logging.Logger;

import javax.annotation.PostConstruct;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.SessionScoped;
import javax.faces.bean.ViewScoped;
import javax.servlet.http.HttpSession;

import org.hibernate.HibernateException;

import com.google.gson.Gson;
import com.google.gson.JsonObject;

import tres.common.DbConstant;
import tres.common.JSFBoundleProvider;
import tres.common.JSFMessagers;
import tres.common.SessionUtils;
import tres.dao.impl.BoardImpl;
import tres.dao.impl.InstitutionImpl;
import tres.domain.Board;
import tres.domain.BoardTemplate;
import tres.domain.Institution;
import tres.domain.Users;

@ManagedBean
@ViewScoped
public class OrganigramBean implements Serializable, DbConstant{
	private List<Board> boardList = new ArrayList<Board>();
	private static final Logger LOGGER = Logger.getLogger(Thread.currentThread().getStackTrace()[0].getClassName());
	private String CLASSNAME = "OrganigramBean :: ";
	private static final long serialVersionUID = 1L;
	private String jsonData = "hello wolrd";
	private int childId=5;
	BoardImpl boardImpl = new BoardImpl();
	private String boardName;
	private Users usersSession;
	InstitutionImpl instituteImpl = new InstitutionImpl();
	private boolean isValid;
	private Board board;
	JSFBoundleProvider provider = new JSFBoundleProvider();
	Timestamp timestamp = new Timestamp(Calendar.getInstance().getTime().getTime());
	@PostConstruct
	public void addElement() {
		boardList = new BoardImpl().getListBoards();
		System.out.println("--------------------------------" + boardList);
		this.convertToJson(boardList);
		HttpSession session = SessionUtils.getSession();
		usersSession = (Users) session.getAttribute("userSession");
		if (board == null) {
			board = new Board();
		}
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

	
	public String saveBoard() {
		try {
			Institution inst = new Institution();
			Board bd1 = new Board();
			try {

				Board bd = new Board();
				bd = boardImpl.getModelWithMyHQL(new String[] { "boardName" }, new Object[] { boardName },
						"from Board");

				inst = instituteImpl.getModelWithMyHQL(new String[] { "genericstatus", "institutionType", "createdBy" },
						new Object[] { ACTIVE, "HeadQuoter", usersSession.getViewId() }, "from Institution");
				LOGGER.info(inst.getInstitutionId() + ":::------->>>>>>Institute founded");
				LOGGER.info("Child Id listed:::"+childId);
				bd1 = boardImpl.getModelWithMyHQL(new String[] { "boardId", "genericstatus" },
						new Object[] { childId, ACTIVE }, "from Board ");
				LOGGER.info("Board listed:::"+bd1.getBoardId());
				if (null != bd) {

					JSFMessagers.resetMessages();
					setValid(false);
					JSFMessagers.addErrorMessage(getProvider().getValue("error.server.side.dupicate.board"));
					LOGGER.info(CLASSNAME + "sivaserside validation :: Board already  recorded in the system! ");
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

			board.setCreatedBy(usersSession.getViewId());
			board.setCrtdDtTime(timestamp);
			board.setGenericStatus(ACTIVE);
			board.setStatus(ACTIVE);
			board.setUpDtTime(timestamp);
			
				if (null != inst&&null != bd1) {
					board.setInstitution(instituteImpl.getInstitutionById(inst.getInstitutionId(), "institutionId"));
					board.setUpdatedBy(usersSession.getViewId());
					board.setBoardName(boardName);
					board.setBoard(bd1);
					boardImpl.saveBoards(board);
					JSFMessagers.resetMessages();
					setValid(true);
					JSFMessagers.addErrorMessage(getProvider().getValue("com.save.form.board"));
					LOGGER.info(CLASSNAME + ":::board Details is saved");
					clearContactFuileds();
				}
			return "/menu/boardOrganigram.xhtml?faces-redirect=true";
		} catch (HibernateException e) {
			LOGGER.info(CLASSNAME + ":::Contact Details is fail with HibernateException  error");
			JSFMessagers.resetMessages();
			setValid(false);
			JSFMessagers.addErrorMessage(getProvider().getValue("com.server.side.internal.error"));
			LOGGER.info(CLASSNAME + "" + e.getMessage());
			e.printStackTrace();
			return null;
		}
	}
	public List<Board> getBoardList() {
		return boardList;
	}

	private void clearContactFuileds() {
		board = new Board();
		// usersDetails=null;
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

	public String getCLASSNAME() {
		return CLASSNAME;
	}

	public void setCLASSNAME(String cLASSNAME) {
		CLASSNAME = cLASSNAME;
	}

	public BoardImpl getBoardImpl() {
		return boardImpl;
	}

	public void setBoardImpl(BoardImpl boardImpl) {
		this.boardImpl = boardImpl;
	}

	public String getBoardName() {
		return boardName;
	}

	public void setBoardName(String boardName) {
		this.boardName = boardName;
	}

	public Users getUsersSession() {
		return usersSession;
	}

	public void setUsersSession(Users usersSession) {
		this.usersSession = usersSession;
	}

	public InstitutionImpl getInstituteImpl() {
		return instituteImpl;
	}

	public void setInstituteImpl(InstitutionImpl instituteImpl) {
		this.instituteImpl = instituteImpl;
	}

	public boolean isValid() {
		return isValid;
	}

	public void setValid(boolean isValid) {
		this.isValid = isValid;
	}

	public static Logger getLogger() {
		return LOGGER;
	}

	public JSFBoundleProvider getProvider() {
		return provider;
	}

	public void setProvider(JSFBoundleProvider provider) {
		this.provider = provider;
	}

	public Board getBoard() {
		return board;
	}

	public void setBoard(Board board) {
		this.board = board;
	}

}
