package tres.dao.impl;
/**
 * author James
 * */

import java.util.List;

import tres.dao.generic.AbstractDao;
import tres.dao.interfc.IContact;
import tres.domain.Contact;

public class ContactImpl extends AbstractDao<Long, Contact> implements IContact {

	public Contact saveContact(Contact contact) {
		return saveIntable(contact);
	}

	public List<Contact> getListContacts() {
		return (List<Contact>) (Object) getModelList();
	}

	public Contact getContactById(int contactId, String primaryKeyclomunName) {
		return (Contact) getModelById(contactId, primaryKeyclomunName);
	}

	public Contact UpdateContact(Contact contact) {
		return updateIntable(contact);
	}

}
