/*
 * Copyright(C) 2007-2013 National Institute of Informatics, All rights reserved.
 */
package app.reservation;

import java.util.Date;
import java.util.Calendar;


import app.AppException;
import app.ManagerFactory;
import domain.reservation.ReservationManager;
import domain.reservation.ReservationException;
import domain.room.RoomManager;
import domain.room.RoomException;

/**
 * Control class for Reserve Room
 * 
 */
public class ReserveRoomControl {

	public String makeReservation(Date stayingDate ,Date checkoutDate, String roomType) throws AppException {
		//Permitting only one night so that change amount of availableQty is always -1
		int availableQtyOfChange = -1;
		try {
			//Update number of available rooms
			RoomManager roomManager = getRoomManager();

			final long ONE_DAY_MILLIS = 1000L * 60 * 60 * 24; 
			long gapDays = Math.abs(checkoutDate.getTime()/ONE_DAY_MILLIS - stayingDate.getTime()/ONE_DAY_MILLIS); 
			for(int i=0; i<gapDays;i++){ 
				Date updatedate=new Date();
				Calendar c = Calendar.getInstance();
				c.setTime(stayingDate);
				c.add(Calendar.DATE,i);
				updatedate = c.getTime();
				roomManager.updateRoomAvailableQty(updatedate,roomType, availableQtyOfChange);
			}
			//Create reservation
			ReservationManager reservationManager = getReservationManager();
			String reservationNumber = reservationManager.createReservation(stayingDate,checkoutDate,roomType);
			return reservationNumber;
		}
		catch (RoomException e) {
			AppException exception = new AppException("Failed to reserve", e);
			exception.getDetailMessages().add(e.getMessage());
			exception.getDetailMessages().addAll(e.getDetailMessages());
			throw exception;
		}
		catch (ReservationException e) {
			AppException exception = new AppException("Failed to reserve", e);
			exception.getDetailMessages().add(e.getMessage());
			exception.getDetailMessages().addAll(e.getDetailMessages());
			throw exception;
		}
	}

	private RoomManager getRoomManager() {
		return ManagerFactory.getInstance().getRoomManager();
	}

	private ReservationManager getReservationManager() {
		return ManagerFactory.getInstance().getReservationManager();
	}
}
