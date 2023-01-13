/*
 * Copyright(C) 2007-2013 National Institute of Informatics, All rights reserved.
 */
package domain.reservation;

import java.util.Calendar;
import java.util.Date;
import java.util.*;

import util.DateUtil;
import domain.DaoFactory;

/**
 * Manager for reservations<br>
 * 
 */
public class ReservationManager {
	
	public String createReservation(Date stayingDate, Date checkoutDate, String roomType) throws ReservationException,
			NullPointerException {
		if (stayingDate == null) {
			throw new NullPointerException("stayingDate");
		}
		if (checkoutDate == null) {
			throw new NullPointerException("checkoutDate");
		}
		if (roomType == null) {
			throw new NullPointerException("roomType");
		}
		Reservation reservation = new Reservation();
		String reservationNumber = generateReservationNumber();
		reservation.setReservationNumber(reservationNumber);
		reservation.setStayingDate(stayingDate);
		reservation.setCheckoutDate(checkoutDate);

		reservation.setRoomType(roomType);

		reservation.setStatus(Reservation.RESERVATION_STATUS_CREATE);

		ReservationDao reservationDao = getReservationDao();
		reservationDao.createReservation(reservation);
		return reservationNumber;
	}

	private synchronized String generateReservationNumber() {
		Calendar calendar = Calendar.getInstance();
		try {
			Thread.sleep(10);
		}
		catch (Exception e) {
		}
		return String.valueOf(calendar.getTimeInMillis());
	}

	public List<String> consumeReservation(String reservationNumber) throws ReservationException,
			NullPointerException {
		if (reservationNumber == null) {
			throw new NullPointerException("reservationNumber");
		}

		ReservationDao reservationDao = getReservationDao();
		Reservation reservation = reservationDao.getReservation(reservationNumber);
		//If corresponding reservation does not exist
		if (reservation == null) {
			ReservationException exception = new ReservationException(
					ReservationException.CODE_RESERVATION_NOT_FOUND);
			exception.getDetailMessages().add("reservation_number[" + reservationNumber + "]");
			throw exception;
		}
		//If reservation has been consumed already
		if (reservation.getStatus().equals(Reservation.RESERVATION_STATUS_CONSUME)) {
			ReservationException exception = new ReservationException(
					ReservationException.CODE_RESERVATION_ALREADY_CONSUMED);
			exception.getDetailMessages().add("reservation_number[" + reservationNumber + "]");
			throw exception;
		}

		Date stayingDate = reservation.getStayingDate();
		Date checkoutDate = reservation.getCheckoutDate();

		String roomType=reservation.getRoomType();
		reservation.setStatus(Reservation.RESERVATION_STATUS_CONSUME);
		reservationDao.updateReservation(reservation);

		List<String> list=new ArrayList<>();
        list.add(DateUtil.convertToString(stayingDate));
        list.add(DateUtil.convertToString(checkoutDate));
        list.add(roomType);
        return list;
	}

	private ReservationDao getReservationDao() {
		return DaoFactory.getInstance().getReservationDao();
	}
}
