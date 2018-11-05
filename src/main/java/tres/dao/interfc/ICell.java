package tres.dao.interfc;

import java.util.List;

import tres.domain.Cell;

/**
 *
 * @author Emmanuel
 */

public interface ICell {
	public Cell saveCell(Cell cell);

	public List<Cell> getListCells();

	public Cell getCellById(int cellId, String primaryKeyclomunName);

	public Cell UpdateCell(Cell cell);
}
