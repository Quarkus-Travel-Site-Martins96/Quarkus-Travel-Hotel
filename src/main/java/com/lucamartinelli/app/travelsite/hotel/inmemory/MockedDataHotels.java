package com.lucamartinelli.app.travelsite.hotel.inmemory;

import com.lucamartinelli.app.travelsite.hotel.vo.HotelVO;

public class MockedDataHotels {

	private static final HotelVO HOTEL1 = new HotelVO(
			"1", 
			"Hotel1", 
			"Long Description", 
			"Short desc", 
			"location1", 
			(short) 1, 
			"https://cdn.iconscout.com/icon/free/png-256/hotel-512-453740.png");
	private static final HotelVO HOTEL2 = new HotelVO(
			"2", 
			"Hotel2", 
			"Long Description", 
			"Short desc", 
			"location2", 
			(short) 2, 
			"https://cdn.iconscout.com/icon/free/png-256/hotel-512-453740.png");
	private static final HotelVO HOTEL3 = new HotelVO(
			"3", 
			"Hotel3", 
			"Long Description", 
			"Short desc", 
			"location3", 
			(short) 3, 
			"https://cdn.iconscout.com/icon/free/png-256/hotel-512-453740.png");
	private static final HotelVO HOTEL4 = new HotelVO(
			"4", 
			"Hotel4", 
			"Long Description", 
			"Short desc", 
			"location4", 
			(short) 4, 
			"https://cdn.iconscout.com/icon/free/png-256/hotel-512-453740.png");
	private static final HotelVO HOTEL5 = new HotelVO(
			"5", 
			"Hotel5", 
			"Long Description", 
			"Short desc", 
			"location5", 
			(short) 5, 
			"https://cdn.iconscout.com/icon/free/png-256/hotel-512-453740.png");
	
	public static HotelVO getHotel() {
		return getHotel(1);
	}
	
	public static HotelVO getHotel(int id) {
		switch (id) {
		case 1:
			return HOTEL1;
		case 2:
			return HOTEL2;
		case 3:
			return HOTEL3;
		case 4:
			return HOTEL4;
		case 5:
			return HOTEL5;

		default:
			return HOTEL1;
		}
	}
	

}
