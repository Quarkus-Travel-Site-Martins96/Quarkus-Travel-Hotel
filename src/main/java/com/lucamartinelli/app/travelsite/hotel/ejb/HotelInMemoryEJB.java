package com.lucamartinelli.app.travelsite.hotel.ejb;

import javax.enterprise.context.RequestScoped;
import javax.inject.Inject;

import org.apache.commons.lang3.math.NumberUtils;
import org.jboss.logging.Logger;

import com.lucamartinelli.app.travelsite.hotel.inmemory.MockedDataHotels;
import com.lucamartinelli.app.travelsite.hotel.inmemory.MockedDataImages;
import com.lucamartinelli.app.travelsite.hotel.vo.HotelImagesVO;
import com.lucamartinelli.app.travelsite.hotel.vo.HotelVO;

@RequestScoped
public class HotelInMemoryEJB implements HotelEJB {
	
	@Inject
	Logger log;
	
	@Override
	public HotelVO getHotelInfo(String id) {
		return MockedDataHotels.getHotel(NumberUtils.toInt(id, 1));
	}
	
	@Override
	public HotelImagesVO getImages(String id) {
		return MockedDataImages.getImages();
	}
	
	
}
