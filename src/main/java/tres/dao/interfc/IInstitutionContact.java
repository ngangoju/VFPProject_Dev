package tres.dao.interfc;

import java.util.List;

import org.hibernate.HibernateException;

import tres.domain.InstitutionContact;

public interface IInstitutionContact {
	public InstitutionContact saveContact(InstitutionContact contact) throws HibernateException;

	public List<InstitutionContact> getListContacts();

	public InstitutionContact getContactById(int contactId, String primaryKeyclomunName);

	public InstitutionContact UpdateContact(InstitutionContact contact);
}
