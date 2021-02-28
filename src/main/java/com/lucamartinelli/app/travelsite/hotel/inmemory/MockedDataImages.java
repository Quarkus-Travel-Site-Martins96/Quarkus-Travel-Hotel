package com.lucamartinelli.app.travelsite.hotel.inmemory;

import java.util.Arrays;

import com.lucamartinelli.app.travelsite.hotel.vo.HotelImagesVO;

public class MockedDataImages {
	
	private final static HotelImagesVO imgs = new HotelImagesVO(Arrays
			.asList(new String[] {
					"https://cdn.iconscout.com/icon/free/png-256/hotel-512-453740.png",
					"https://cdn.iconscout.com/icon/free/png-256/hotel-512-453740.png",
					"https://cdn.iconscout.com/icon/free/png-256/hotel-512-453740.png",
					"https://cdn.iconscout.com/icon/free/png-256/hotel-512-453740.png"
			}), "1");
	
	public static HotelImagesVO getImages() {
		return imgs;
	}
	
}
