package tres.dao.impl;

import java.util.List;

import tres.dao.generic.AbstractDao;
import tres.dao.interfc.ICell;
import tres.domain.Cell;



public class CellImpl extends AbstractDao<Long, Cell> implements ICell {

	public Cell saveCell(Cell cell) {
		  return saveIntable(cell);  
	}

	public List<Cell> getListCells() {
		  return (List<Cell>)(Object)getModelList(); 
	}

	public Cell getCellById(int cellId, String primaryKeyclomunName) {
		   return (Cell) getModelById(cellId,primaryKeyclomunName);	
	}

	public Cell UpdateCell(Cell cell) {
		 return updateIntable(cell);
	}

}
