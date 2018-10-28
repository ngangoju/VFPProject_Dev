package com.vfp.tres;

import java.io.Serializable;
import java.sql.Timestamp;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.List;
import java.util.logging.Logger;

import javax.annotation.PostConstruct;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.ViewScoped;
import javax.servlet.http.HttpSession;

import org.hibernate.HibernateException;

import tres.common.DbConstant;
import tres.common.JSFBoundleProvider;
import tres.common.JSFMessagers;
import tres.common.SessionUtils;
import tres.dao.impl.BoardImpl;
import tres.dao.impl.ContactImpl;
import tres.dao.impl.InstitutionImpl;
import tres.dao.impl.UserImpl;
import tres.domain.Board;
import tres.domain.Contact;
import tres.domain.Institution;
import tres.domain.Users;
import tres.vfp.dto.BoardDto;
import tres.vfp.dto.ContactDto;
import tres.vfp.dto.UserDto;

@ManagedBean
@ViewScoped
public class BoardController implements Serializable, DbConstant {
	private static final Logger LOGGER = Logger.getLogger(Thread.currentThread().getStackTrace()[0].getClassName());
	private String CLASSNAME = "UserContactController :: ";
	private static final long serialVersionUID = 1L;
	/* to manage validation messages */
	private boolean isValid;
	/* end manage validation messages */
	private Institution institution;
	private Board board;
	private Users users;
	private BoardDto boardDto;
	private UserDto userDto;
	private Users usersSession;
	private List<Board> boardList = new ArrayList<Board>();
	private List<BoardDto> boardDtoList = new ArrayList<BoardDto>();
	private List<Institution> institutionList = new ArrayList<Institution>();
	private int instituteNumber;
	private int boardNumber;
	private String boardName;
	/* class injection */
	JSFBoundleProvider provider = new JSFBoundleProvider();
	UserImpl usersImpl = new UserImpl();
	ContactImpl contactImpl = new ContactImpl();
	BoardImpl boardImpl = new BoardImpl();
	InstitutionImpl instituteImpl = new InstitutionImpl();
	Timestamp timestamp = new Timestamp(Calendar.getInstance().getTime().getTime());

	@SuppressWarnings("unchecked")
	@PostConstruct
	public void init() {
		HttpSession session = SessionUtils.getSession();
		usersSession = (Users) session.getAttribute("userSession");

		if (users == null) {
			users = new Users();
		}
		if (boardDto == null) {
			boardDto = new BoardDto();
		}
		if (board == null) {
			board = new Board();
		}

		try {
			LOGGER.info("initialise lists:: ");
			boardList = boardImpl.getGenericListWithHQLParameter(new String[] { "genericStatus" },
					new Object[] { ACTIVE }, "Board", "boardId desc");
			
			institutionList = instituteImpl.getGenericListWithHQLParameter(new String[] { "genericStatus" },
					new Object[] { ACTIVE }, "Institution", "institutionId desc");
			LOGGER.info("lis size :: "+institutionList.size());
			for (Board board : boardList) {
				BoardDto boardDto = new BoardDto();
				boardDto.setEditable(false);
				boardDto.setBoardName(board.getBoardName());
				boardDto.setInstitution(board.getInstitution());
				boardDto.setBoard(board.getBoard());
				boardDtoList.add(boardDto);

			}
		} catch (Exception e) {
			setValid(false);
			JSFMessagers.addErrorMessage(getProvider().getValue("com.server.side.internal.error"));
			LOGGER.info(e.getMessage());
			e.printStackTrace();
		}

	}

	public String saveBoard() throws Exception {
		try {

			try {

				Board bd = new Board();
				bd = boardImpl.getModelWithMyHQL(new String[] { "boardName" }, new Object[] { boardName },
						"from Board");
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
			board.setUpDtTime(timestamp);
			Institution inst = new Institution();
			inst = instituteImpl.getModelWithMyHQL(new String[] { "institutionId", "genericstatus" },
					new Object[] { instituteNumber, ACTIVE }, "from Institution ");

			Board bd1 = new Board();

			bd1 = boardImpl.getModelWithMyHQL(new String[] { "boardId", "genericstatus" },
					new Object[] { boardNumber, ACTIVE }, "from Board ");
			if ((null != inst) || (null != bd1)) {
				LOGGER.info(inst.getInstitutionId() + ":::------->>>>>>Institute founded");
				board.setInstitution(instituteImpl.getInstitutionById(inst.getInstitutionId(), "institutionId"));
				board.setUpdatedBy(usersSession.getViewId());
				board.setBoardName(boardName);
				board.setBoard(bd1);
				boardImpl.saveBoards(board);
				JSFMessagers.resetMessages();
				setValid(true);
				JSFMessagers.addErrorMessage(getProvider().getValue("com.save.form.board"));
				LOGGER.info(CLASSNAME + ":::Contact Details is saved");
				clearContactFuileds();
				return "/menu/boardOrganigram.xhtml?faces-redirect=true";
			}
			return "/menu/Organigram.xhtml?faces-redirect=true";
		} catch (HibernateException e) {
			LOGGER.info(CLASSNAME + ":::Contact Details is fail with HibernateException  error");
			JSFMessagers.resetMessages();
			setValid(false);
			JSFMessagers.addErrorMessage(getProvider().getValue("com.server.side.internal.error"));
			LOGGER.info(CLASSNAME + "" + e.getMessage());
			e.printStackTrace();
			return "";
		}

	}

	private void clearContactFuileds() {
		board = new Board();
		// usersDetails=null;
	}

	public String saveAction(UserDto user) {
		LOGGER.info("update  saveAction method");
		// get all existing value but set "editable" to false
		Users us = new Users();
		us = new Users();
		us = usersImpl.gettUserById(user.getUserId(), "userId");

		LOGGER.info("here update sart for " + us + " useriD " + us.getUserId());

		user.setEditable(false);
		us.setFname(user.getFname());
		us.setLname(user.getLname());

		usersImpl.UpdateUsers(us);

		// return to current page
		return "/menu/ListOfUsers.xhtml?faces-redirect=true";

	}

	public String cancel(UserDto user) {
		user.setEditable(false);
		// usersImpl.UpdateUsers(user);
		return null;

	}

	public String editAction(UserDto user) {

		user.setEditable(true);
		// usersImpl.UpdateUsers(user);
		return null;
	}

	public String saveContactAction(ContactDto contact) {
		LOGGER.info("update  saveAction method");
		// get all existing value but set "editable" to false
		Contact cont = new Contact();
		cont = new Contact();
		cont = contactImpl.getContactById(contact.getContactId(), "contactId");

		LOGGER.info("here update sart for " + cont + " useriD " + cont.getContactId());

		contact.setEditable(false);
		cont.setContactDetails(contact.getContactDetails());
		cont.setEmail(contact.getEmail());
		cont.setPhone(contact.getPhone());
		contactImpl.UpdateContact(cont);

		// return to current page
		return "/menu/ViewUsersContacts.xhtml?faces-redirect=true";

	}

	public String cancelContact(ContactDto contact) {
		contact.setEditable(false);
		// usersImpl.UpdateUsers(user);
		return null;

	}

	public String editContactAction(ContactDto contact) {

		contact.setEditable(true);
		// usersImpl.UpdateUsers(user);
		return null;
	}

	public String addNewContact() {

		return "/menu/UserContacts.xhtml?faces-redirect=true";
	}

	public String getCLASSNAME() {
		return CLASSNAME;
	}

	public void setCLASSNAME(String cLASSNAME) {
		CLASSNAME = cLASSNAME;
	}

	public boolean isValid() {
		return isValid;
	}

	public void setValid(boolean isValid) {
		this.isValid = isValid;
	}

	public Users getUsers() {
		return users;
	}

	public void setUsers(Users users) {
		this.users = users;
	}

	public JSFBoundleProvider getProvider() {
		return provider;
	}

	public void setProvider(JSFBoundleProvider provider) {
		this.provider = provider;
	}

	public UserImpl getUsersImpl() {
		return usersImpl;
	}

	public void setUsersImpl(UserImpl usersImpl) {
		this.usersImpl = usersImpl;
	}

	public ContactImpl getContactImpl() {
		return contactImpl;
	}

	public void setContactImpl(ContactImpl contactImpl) {
		this.contactImpl = contactImpl;
	}

	public Users getUsersSession() {
		return usersSession;
	}

	public void setUsersSession(Users usersSession) {
		this.usersSession = usersSession;
	}

	public UserDto getUserDto() {
		return userDto;
	}

	public void setUserDto(UserDto userDto) {
		this.userDto = userDto;
	}

	public Institution getInstitution() {
		return institution;
	}

	public void setInstitution(Institution institution) {
		this.institution = institution;
	}

	public Board getBoard() {
		return board;
	}

	public void setBoard(Board board) {
		this.board = board;
	}

	public BoardDto getBoardDto() {
		return boardDto;
	}

	public void setBoardDto(BoardDto boardDto) {
		this.boardDto = boardDto;
	}

	public List<Board> getBoardList() {
		return boardList;
	}

	public void setBoardList(List<Board> boardList) {
		this.boardList = boardList;
	}

	public List<Institution> getInstitutionList() {
		return institutionList;
	}

	public void setInstitutionList(List<Institution> institutionList) {
		this.institutionList = institutionList;
	}

	public BoardImpl getBoardImpl() {
		return boardImpl;
	}

	public void setBoardImpl(BoardImpl boardImpl) {
		this.boardImpl = boardImpl;
	}

	public InstitutionImpl getInstituteImpl() {
		return instituteImpl;
	}

	public void setInstituteImpl(InstitutionImpl instituteImpl) {
		this.instituteImpl = instituteImpl;
	}

	public List<BoardDto> getBoardDtoList() {
		return boardDtoList;
	}

	public void setBoardDtoList(List<BoardDto> boardDtoList) {
		this.boardDtoList = boardDtoList;
	}

	public int getInstituteNumber() {
		return instituteNumber;
	}

	public void setInstituteNumber(int instituteNumber) {
		this.instituteNumber = instituteNumber;
	}

	public int getBoardNumber() {
		return boardNumber;
	}

	public void setBoardNumber(int boardNumber) {
		this.boardNumber = boardNumber;
	}

	public String getBoardName() {
		return boardName;
	}

	public void setBoardName(String boardName) {
		this.boardName = boardName;
	}

}
