package com.lucamartinelli.app.travelsite.hotel.ejb;

import com.lucamartinelli.app.travelsite.hotel.vo.HotelImagesVO;
import com.lucamartinelli.app.travelsite.hotel.vo.HotelVO;

public interface HotelEJB {
	
	public HotelVO getHotelInfo(String id);
	
	public HotelImagesVO getImages(String id);
	
	
	
}
