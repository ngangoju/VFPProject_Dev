package tres.dao.impl;

import java.util.List;
import tres.dao.generic.AbstractDao;
import tres.dao.interfc.IMenuAssignment;
import tres.domain.MenuAssignment;

public class MenuAssignmentImpl extends AbstractDao<Long, MenuAssignment> implements IMenuAssignment{

	public MenuAssignment saveMenuAssignment(MenuAssignment menuAssignment) {
		return saveIntable(menuAssignment); 
	}

	public List<MenuAssignment> getListMenuAssignments() {
		return (List<MenuAssignment>) (Object) getModelList();
	}

	public MenuAssignment getMenuAssignmentById(int menuAssignmentId, String primaryKeyclomunName) {
		return (MenuAssignment) getModelById(menuAssignmentId, primaryKeyclomunName);
	}

	public MenuAssignment updateMenuAssignment(MenuAssignment menuAssignment) {
		return updateIntable(menuAssignment);
	}

}
