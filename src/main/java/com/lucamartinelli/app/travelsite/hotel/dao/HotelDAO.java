package com.lucamartinelli.app.travelsite.hotel.dao;

import java.rmi.ConnectException;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import javax.annotation.PostConstruct;
import javax.annotation.PreDestroy;
import javax.enterprise.context.ApplicationScoped;
import javax.enterprise.inject.Default;
import javax.enterprise.inject.spi.CDI;

import org.jboss.logging.Logger;

import com.lucamartinelli.app.travelsite.hotel.vo.HotelImagesVO;
import com.lucamartinelli.app.travelsite.hotel.vo.HotelVO;

import io.agroal.api.AgroalDataSource;
import io.quarkus.arc.Unremovable;

@ApplicationScoped
@Unremovable
@Default
public class HotelDAO {
	
	private static final String QUERY_HOTEL_INFO = "SELECT * FROM hotels WHERE HOTEL_ID = ?";
	private static final String QUERY_HOTEL_IMAGES = "SELECT IMAGE_SRC FROM hotels_images WHERE HOTEL_ID = ?";
	
	private Connection connection = null;
	
	private Logger log = Logger.getLogger(getClass().getCanonicalName());
	
	@PostConstruct
	void init() {
		try {
			connection = CDI.current().select(AgroalDataSource.class).get().getConnection();
		} catch (SQLException e) {
			log.error("Error during connection to DB: ", e);
		}
	}
	
	public HotelVO getInfo(String id) throws SQLException {
		log.debug("Connecting to DB with id " + id);
		final HotelVO hotelInfo = new HotelVO();
		
		if (connection == null) {
			log.error("Error during connection to DB");
			throw new SQLException(new ConnectException("Connection refused"));
		}
		
		try (final PreparedStatement ps = connection.prepareStatement(QUERY_HOTEL_INFO)) {
			ps.setString(1, id);
			try (final ResultSet rs = ps.executeQuery()) {
				if(rs.next()) {
					hotelInfo.setHotelID(rs.getString("HOTEL_ID"));
					hotelInfo.setTitle(rs.getString("TITLE"));
					hotelInfo.setLongDescription(rs.getString("LONG_DESCRIPTION"));
					hotelInfo.setShortDescription(rs.getString("SHORT_DESCRIPTION"));
					hotelInfo.setLocation(rs.getString("LOCATION"));
					hotelInfo.setStars(rs.getShort("STARS"));
					hotelInfo.setPreviewImage(rs.getString("PREVIEW_IMAGE"));
				}
			}
		} catch (SQLException e) {
			log.error("Error during query on DB for Hotel Info");
			throw e;
		}
		
		log.debug("Result from DB is: " + hotelInfo);
		
		return hotelInfo;
	}
	
	
	public HotelImagesVO getImages(String id) throws SQLException {
		log.debug("Connecting to DB with id " + id);
		final HotelImagesVO hotelImg = new HotelImagesVO();
		
		if (connection == null) {
			log.error("Error during connection to DB");
			throw new SQLException(new ConnectException("Connection refused"));
		}
		
		try (final PreparedStatement ps = connection.prepareStatement(QUERY_HOTEL_IMAGES)) {
			ps.setString(1, id);
			try (final ResultSet rs = ps.executeQuery()) {
				final List<String> imgs = new ArrayList<>();
				while(rs.next()) {
					imgs.add(rs.getString("IMAGE_SRC"));
				}
				if (imgs.size() > 0) {
					hotelImg.setHotelId(id);
					hotelImg.setImages(imgs);
				}
			}
		} catch (SQLException e) {
			log.error("Error during query on DB for Hotel Images");
			throw e;
		}
		
		log.debug("Result from DB is: " + hotelImg);
		
		return hotelImg;
	}
	
	
	@PreDestroy
	void onDestroy() {
		try {
			if (connection != null)
				connection.close();
		} catch(SQLException e) {
			log.debug("Error during connection closure: ", e);
		}
	}
	
	
}
