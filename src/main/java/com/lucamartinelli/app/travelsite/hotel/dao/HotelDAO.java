package com.lucamartinelli.app.travelsite.hotel.dao;

import java.rmi.ConnectException;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import javax.annotation.PostConstruct;
import javax.enterprise.context.ApplicationScoped;
import javax.enterprise.inject.Default;
import javax.enterprise.inject.spi.CDI;

import org.eclipse.microprofile.metrics.MetricUnits;
import org.eclipse.microprofile.metrics.annotation.Counted;
import org.eclipse.microprofile.metrics.annotation.Timed;
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
	
	private AgroalDataSource datasource = null;
	
	private Logger log = Logger.getLogger(getClass().getCanonicalName());
	
	@PostConstruct
	void init() {
		datasource = CDI.current().select(AgroalDataSource.class).get();
	}
	
	@Counted(name="db-iteration-info", displayName = "DB Iterations Hotel Info",
			absolute = true, description = "Iterations with DB for Hotel Info")
	@Timed(name="db-timer-info", displayName = "DB Timer Hotel Info",
			absolute = true, description = "Hotel Info performance timer related to DB",
			unit = MetricUnits.MILLISECONDS)
	public HotelVO getInfo(String id) throws SQLException {
		log.debug("Connecting to DB with id " + id);
		HotelVO hotelInfo = null;
		
		if (datasource == null) {
			log.error("Error during connection to DB");
			throw new SQLException(new ConnectException("Connection refused"));
		}
		
		try (Connection conn = datasource.getConnection();
				final PreparedStatement ps = conn.prepareStatement(QUERY_HOTEL_INFO)) {
			ps.setString(1, id);
			try (final ResultSet rs = ps.executeQuery()) {
				if(rs.next()) {
					hotelInfo = new HotelVO();
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
	
	
	@Counted(name="db-iteration-images", displayName = "DB Iterations Hotel Images",
			absolute = true, description = "Iterations with DB for Hotel Images")
	@Timed(name="db-timer-images", displayName = "DB Timer Hotel Images",
			absolute = true, description = "Hotel Info performance timer related to DB",
			unit = MetricUnits.MILLISECONDS)
	public HotelImagesVO getImages(String id) throws SQLException {
		log.debug("Connecting to DB with id " + id);
		HotelImagesVO hotelImg = null;
		
		if (datasource == null) {
			log.error("Error during connection to DB");
			throw new SQLException(new ConnectException("Connection refused"));
		}
		
		try (Connection conn = datasource.getConnection();
				final PreparedStatement ps = conn.prepareStatement(QUERY_HOTEL_IMAGES)) {
			ps.setString(1, id);
			try (final ResultSet rs = ps.executeQuery()) {
				final List<String> imgs = new ArrayList<>();
				while(rs.next()) {
					imgs.add(rs.getString("IMAGE_SRC"));
				}
				if (imgs.size() > 0) {
					hotelImg = new HotelImagesVO();
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
	
}
