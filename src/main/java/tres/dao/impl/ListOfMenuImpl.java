package tres.dao.impl;

import java.io.Serializable;
import java.util.List;

import tres.dao.generic.AbstractDao;
import tres.dao.interfc.IListOfMenu;
import tres.domain.ListOfMenu;

public class ListOfMenuImpl extends AbstractDao<Long, ListOfMenu> implements IListOfMenu, Serializable {
	private static final long serialVersionUID = 1L;

	public ListOfMenu saveListOfMenu(ListOfMenu listOfMenu) {
		return saveIntable(listOfMenu);
	}

	public ListOfMenu getListOfMenuById(int listOfMenuId, String primaryKeyclomunName) {
		return (ListOfMenu) getModelById(listOfMenuId, primaryKeyclomunName);
	}

	public List<ListOfMenu> getListListOfMenus() {
		return (List<ListOfMenu>) (Object) getModelList();
	}

	public ListOfMenu updateListOfMenu(ListOfMenu listOfMenu) {
		return updateIntable(listOfMenu);
	}

}
