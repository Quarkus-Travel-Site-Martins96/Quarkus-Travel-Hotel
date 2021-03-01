package com.lucamartinelli.app.travelsite.hotel.ejb;

import java.sql.SQLException;

import javax.enterprise.context.RequestScoped;
import javax.enterprise.inject.Default;
import javax.enterprise.inject.spi.CDI;
import javax.inject.Inject;

import org.jboss.logging.Logger;

import com.lucamartinelli.app.travelsite.hotel.dao.HotelDAO;
import com.lucamartinelli.app.travelsite.hotel.vo.HotelImagesVO;
import com.lucamartinelli.app.travelsite.hotel.vo.HotelVO;

@RequestScoped
public class HotelDBEJB implements HotelEJB {
	
	@Inject
	Logger log;
	
	
	
	@Override
	public HotelVO getHotelInfo(String id) throws SQLException{
		log.debug("Get Hotel Info Service START");
		final HotelDAO dao = CDI.current().select(HotelDAO.class, new Default.Literal()).get();
		try {
			return dao.getInfo(id);
		} catch (SQLException e) {
			log.error("Error in SQL query: ", e);
			throw e;
		} finally {
			log.debug("Get Hotel Info Service END");
		}
	}
	
	@Override
	public HotelImagesVO getImages(String id) throws SQLException{
		log.debug("Get Hotel Images Service START");
		final HotelDAO dao = CDI.current().select(HotelDAO.class).get();
		try {
			return dao.getImages(id);
		} catch (SQLException e) {
			log.error("Error in SQL query: ", e);
			throw e;
		} finally {
			log.debug("Get Hotel Images Service END");
		}
	}
	
	
	
}
