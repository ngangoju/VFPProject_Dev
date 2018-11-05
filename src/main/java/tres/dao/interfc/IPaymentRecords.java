/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package tres.dao.interfc;

import tres.domain.PaymentRecords;
import java.util.List;

/**
 *
 * @author Emile
 */
public interface IPaymentRecords {
	public PaymentRecords savePaymentRecords(PaymentRecords paymentRecords);

	public List<PaymentRecords> getListPaymentRecords();

	public PaymentRecords gettPaymentRecordsById(int paymentId, String primaryKeyclomunName);

	public PaymentRecords UpdatePaymentRecords(PaymentRecords paymentRecords);

	public String myNane();

	public PaymentRecords getPaymentRecordsWithQuery(final String[] propertyName, final Object[] value,
			final String hqlStatement);
}
