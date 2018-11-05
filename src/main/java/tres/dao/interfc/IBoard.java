/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package tres.dao.interfc;

import tres.domain.Board;
import java.util.List;

/**
 *
 * @author Gwiza
 */
public interface IBoard {
	public Board saveBoards(Board board);

	public List<Board> getListBoards();

	public Board getBoardById(int boardId, String primaryKeyclomunName);

	public Board UpdateBoard(Board board);

	public Board getBoardsWithQuery(final String[] propertyName, final Object[] value, final String hqlStatement);
}
