/*
 * Copyright(C) 2007-2013 National Institute of Informatics, All rights reserved.
 */
package app.reservation;

import java.util.Date;

import app.AppException;

/**
 * Form class for Reserve Room
 * 
 */
public class ReserveRoomForm {

	private ReserveRoomControl reserveRoomHandler = new ReserveRoomControl();

	private Date stayingDate;

	private Date checkoutDate;

	private String roomType;

	private Integer numOfRoom;

	private ReserveRoomControl getReserveRoomHandler() {
		return reserveRoomHandler;
	}

	public String submitReservation() throws AppException {
		ReserveRoomControl reserveRoomHandler = getReserveRoomHandler();
		return reserveRoomHandler.makeReservation(stayingDate,checkoutDate,numOfRoom,roomType);
	}

	public Date getStayingDate() {
		return stayingDate;
	}

	public void setStayingDate(Date stayingDate) {
		this.stayingDate = stayingDate;
	}
	public Date getCheckoutDate() {
		return stayingDate;
	}

	public void setCheckoutDate(Date checkoutDate) {
		this.checkoutDate = checkoutDate;
	}
	public String getRoomType() {
		return roomType;
	}

	public void setRoomType(String roomType) {
		this.roomType = roomType;
	}

	public Integer getNumOfRoom() {
		return numOfRoom;
	}

	public void setNumOfRoom(Integer numOfRoom) {
		this.numOfRoom = numOfRoom;
	}

}
