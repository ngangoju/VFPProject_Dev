package tres.dao.impl;

import java.util.List;

import org.hibernate.HibernateException;

import tres.dao.generic.AbstractDao; 
import tres.dao.interfc.IInstitutionContact; 
import tres.domain.InstitutionContact;

public class InstitutionContactImpl extends AbstractDao<Long, InstitutionContact> implements IInstitutionContact {

	public InstitutionContact saveContact(InstitutionContact contact) throws HibernateException {
		return saveIntable(contact);
	}

	public List<InstitutionContact> getListContacts() {
		return (List<InstitutionContact>) (Object) getModelList();
	}

	public InstitutionContact getContactById(int contactId, String primaryKeyclomunName) {
		return (InstitutionContact) getModelById(contactId, primaryKeyclomunName);
	}

	public InstitutionContact UpdateContact(InstitutionContact contact) {
		return updateIntable(contact);
	}

}
