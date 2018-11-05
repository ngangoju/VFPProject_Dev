package tres.dao.interfc;

import java.util.List;

import tres.domain.MenuGroup;

public interface IMenuGroup {
	public MenuGroup saveMenuGroupt(MenuGroup menuGroup);

	public List<MenuGroup> getListMenuGroups();

	public MenuGroup getMenuGroupById(int menuGroupId, String primaryKeyclomunName);

	public MenuGroup updateMenuGroup(MenuGroup menuGroup);
}
