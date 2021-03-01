package com.lucamartinelli.app.travelsite.hotel.ejb;

import java.sql.SQLException;

import com.lucamartinelli.app.travelsite.hotel.vo.HotelImagesVO;
import com.lucamartinelli.app.travelsite.hotel.vo.HotelVO;

public interface HotelEJB {
	
	public HotelVO getHotelInfo(String id) throws SQLException;
	
	public HotelImagesVO getImages(String id) throws SQLException;
	
	
	
}
