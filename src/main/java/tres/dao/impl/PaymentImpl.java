/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package tres.dao.impl;

import java.io.Serializable;
import java.util.List;
import java.util.logging.Logger;
import tres.dao.generic.AbstractDao;
import tres.dao.interfc.IPaymentRecords;
import tres.domain.PaymentRecords;

/**
 *
 * @author Emile
 */

public class PaymentImpl extends AbstractDao<Long, PaymentRecords> implements IPaymentRecords{
	private static final Logger LOGGER = Logger.getLogger(Thread.currentThread().getStackTrace()[0].getClassName());
	
	public PaymentRecords savePaymentRecords(PaymentRecords paymentRecords) {
		return saveIntable(paymentRecords);
	}

	public List<PaymentRecords> getListPaymentRecords() {
		return (List<PaymentRecords>) (Object) getModelList();
	}

	public PaymentRecords gettPaymentRecordsById(int paymentId, String primaryKeyclomunName) {
		return (PaymentRecords) getModelById(paymentId, primaryKeyclomunName);
	}
	
	public PaymentRecords UpdatePaymentRecords(PaymentRecords paymentRecords) {
		return updateIntable(paymentRecords);
	}

	public PaymentRecords getPaymentRecordsWithQuery(String[] propertyName, Object[] value, String hqlStatement) {
		try {
			return (PaymentRecords) getModelWithMyHQL(propertyName, value, hqlStatement);
		} catch (Exception ex) {
			LOGGER.info("getUsersWithQuery  Query error ::::" + ex.getMessage());
		}
		return null;
	}

	public String myNane() {
		return"nBAGO ERIC";
	}
	
	public PaymentImpl() {
		
	}

	
}
