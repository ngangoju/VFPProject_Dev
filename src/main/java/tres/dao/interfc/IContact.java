package tres.dao.interfc;
/**
 * author James
 * */

import java.util.List;

import tres.domain.Contact;

public interface IContact {
	public Contact saveContact(Contact contact);

	public List<Contact> getListContacts();

	public Contact getContactById(int contactId, String primaryKeyclomunName);

	public Contact UpdateInstitution(Contact contact);
}
