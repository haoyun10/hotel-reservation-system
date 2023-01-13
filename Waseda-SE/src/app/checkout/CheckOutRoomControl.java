/*
 * Copyright(C) 2007-2013 National Institute of Informatics, All rights reserved.
 */
package app.checkout;

import java.util.Date;
import java.util.List;

import app.AppException;
import app.ManagerFactory;
import domain.payment.PaymentManager;
import domain.payment.PaymentException;
import domain.room.RoomManager;
import domain.room.RoomException;

/**
 * Control class for Check-out Customer
 * 
 */
public class CheckOutRoomControl {
	
	public void checkOut(String roomNumber) throws AppException {
		try {
			//Clear room
			/*
			 * Your code for clearing room by using domain.room.RoomManager
			 */
			RoomManager roomManager = getRoomManager();
			List<Date> list = roomManager.removeCustomer(roomNumber);
			Date stayingDate = list.get(0);
			Date checkoutDate = list.get(1);

			//Consume payment
			/*
			 * Your code for consuming payment by using domain.payment.PaymentManager
			 */
			PaymentManager paymentManager = getPaymentManager();
			// Integer amout=paymentManager.amount;
			paymentManager.consumePayment(stayingDate,checkoutDate, roomNumber);

		}
		catch (RoomException e) {
			AppException exception = new AppException("Failed to check-out", e);
			exception.getDetailMessages().add(e.getMessage());
			exception.getDetailMessages().addAll(e.getDetailMessages());
			throw exception;
		}
		catch (PaymentException e) {
			AppException exception = new AppException("Failed to check-out", e);
			exception.getDetailMessages().add(e.getMessage());
			exception.getDetailMessages().addAll(e.getDetailMessages());
			throw exception;
		}
	}

	private RoomManager getRoomManager() {
		return ManagerFactory.getInstance().getRoomManager();
	}

	private PaymentManager getPaymentManager() {
		return ManagerFactory.getInstance().getPaymentManager();
	}
}
