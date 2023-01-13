/*
 * Copyright(C) 2007-2013 National Institute of Informatics, All rights reserved.
 */
package app.checkin;

import java.util.Date;
import java.util.*;
import util.DateUtil;

import app.AppException;
import app.ManagerFactory;
import domain.payment.PaymentManager;
import domain.payment.PaymentException;
import domain.reservation.ReservationManager;
import domain.reservation.ReservationException;
import domain.room.RoomManager;
import domain.room.RoomException;

/**
 * Control class for Check-in Customer
 * 
 */
public class CheckInRoomControl {

	public List<String> checkIn(String reservationNumber) throws AppException {
		try {
			//Consume reservation
			ReservationManager reservationManager = getReservationManager();
			List<String> list1=reservationManager.consumeReservation(reservationNumber);
		
			Date stayingDate = DateUtil.convertToDate(list1.get(0));
			Date checkoutDate = DateUtil.convertToDate(list1.get(1));

			String roomType = list1.get(2);
			Integer numOfroom = Integer.parseInt(list1.get(3));

			// System.out.println("stayingDate "+list1.get(1));
			List<String> roomNumberList=new ArrayList();
			for(int i=0; i<numOfroom; i++){
				//Assign room
				RoomManager roomManager = getRoomManager();
				String roomNumber = roomManager.assignCustomer(stayingDate,checkoutDate,roomType);

				//Create payment
				PaymentManager paymentManager = getPaymentManager();
				paymentManager.createPayment(stayingDate,checkoutDate, roomNumber,roomType);
				roomNumberList.add(roomNumber);
			}
			return roomNumberList;
		}
		catch (ReservationException e) {
			AppException exception = new AppException("Failed to check-in", e);
			exception.getDetailMessages().add(e.getMessage());
			exception.getDetailMessages().addAll(e.getDetailMessages());
			throw exception;

		}
		catch (RoomException e) {
			AppException exception = new AppException("Failed to check-in", e);
			exception.getDetailMessages().add(e.getMessage());
			exception.getDetailMessages().addAll(e.getDetailMessages());
			throw exception;
		}
		catch (PaymentException e) {
			AppException exception = new AppException("Failed to check-in", e);
			exception.getDetailMessages().add(e.getMessage());
			exception.getDetailMessages().addAll(e.getDetailMessages());
			throw exception;
		}
	}

	private ReservationManager getReservationManager() {
		return ManagerFactory.getInstance().getReservationManager();
	}

	private RoomManager getRoomManager() {
		return ManagerFactory.getInstance().getRoomManager();
	}

	private PaymentManager getPaymentManager() {
		return ManagerFactory.getInstance().getPaymentManager();
	}
}
