package tres.dao.impl;

import java.util.List;
import java.util.logging.Logger;

import tres.dao.generic.AbstractDao;
import tres.dao.interfc.IBoard;
import tres.domain.Board;

public class BoardImpl extends AbstractDao<Long, Board> implements IBoard{
	private static final Logger LOGGER = Logger.getLogger(Thread.currentThread().getStackTrace()[0].getClassName());

	public Board saveBoards(Board board) {
		return saveIntable(board);
	}

	public List<Board> getListBoards() {
		return (List<Board>) (Object) getModelList();
	}

	public Board getBoardById(int boardId, String primaryKeyclomunName) {
		return (Board) getModelById(boardId, primaryKeyclomunName);
	}

	public Board UpdateBoard(Board board) {
		return updateIntable(board);
	}
	

	public Board getBoardsWithQuery(String[] propertyName, Object[] value, String hqlStatement) {
		try {
			return (Board) getModelWithMyHQL(propertyName, value, hqlStatement);
		} catch (Exception ex) {
			LOGGER.info("getUsersWithQuery  Query error ::::" + ex.getMessage());
		}
		return null;
	}
}
