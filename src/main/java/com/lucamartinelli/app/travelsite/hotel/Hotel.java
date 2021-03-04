package com.lucamartinelli.app.travelsite.hotel;

import java.io.IOException;
import java.sql.SQLException;
import java.util.HashMap;
import java.util.Map;

import javax.annotation.PostConstruct;
import javax.enterprise.context.ApplicationScoped;
import javax.inject.Inject;
import javax.inject.Provider;
import javax.servlet.http.HttpServletResponse;
import javax.ws.rs.GET;
import javax.ws.rs.Path;
import javax.ws.rs.PathParam;
import javax.ws.rs.Produces;
import javax.ws.rs.core.Context;
import javax.ws.rs.core.MediaType;

import org.eclipse.microprofile.config.inject.ConfigProperty;
import org.eclipse.microprofile.metrics.Counter;
import org.eclipse.microprofile.metrics.Metadata;
import org.eclipse.microprofile.metrics.MetricRegistry;
import org.eclipse.microprofile.metrics.MetricType;
import org.eclipse.microprofile.metrics.MetricUnits;
import org.eclipse.microprofile.metrics.annotation.Timed;
import org.jboss.logging.Logger;

import com.lucamartinelli.app.travelsite.hotel.ejb.HotelDBEJB;
import com.lucamartinelli.app.travelsite.hotel.ejb.HotelEJB;
import com.lucamartinelli.app.travelsite.hotel.ejb.HotelInMemoryEJB;
import com.lucamartinelli.app.travelsite.hotel.vo.HotelImagesVO;
import com.lucamartinelli.app.travelsite.hotel.vo.HotelVO;

import io.smallrye.metrics.MetricRegistries;


/**
 * REST APIs manager and exposer class
 * 
 * @author Luca Martinelli
 * @category REST APIs
 *
 */
@Path("/hotel")
@ApplicationScoped
public class Hotel {
	
	private HotelEJB ejb = null;
	
	@Inject Provider<HotelInMemoryEJB> inMemoryEJB;
	@Inject Provider<HotelDBEJB> DatabaseEJB;
	
	@ConfigProperty(name = "hotel.mode", defaultValue = "IN_MEMORY")
	String mode;
	
	@Inject
	Logger log;
	
	@Context
	HttpServletResponse response;
	
	private MetricRegistry registry = MetricRegistries.get(MetricRegistry.Type.APPLICATION);
	private static final Map<String, Counter> hotelsCounter = new HashMap<>();
	
	private void incCounterHotel(String hotelID) {
		Counter counter = hotelsCounter.getOrDefault(hotelID, null);
		if (counter == null) {
			Metadata metadata = Metadata.builder()
	                .withName("counter-hotel[" + hotelID + "]")
	                .withType(MetricType.COUNTER)
	                .withDescription("Views counter for hotel with id " + hotelID)
	                .withDisplayName("Counter Hotel " + hotelID)
	                .withUnit(MetricUnits.NONE)
	                .build();
			counter = registry.counter(metadata);
		}
		counter.inc();
		hotelsCounter.put(hotelID, counter);
	}
	
	@PostConstruct
	public void init() {
		log.debug("Hotel service is loaded with mode [" + mode + "]");
		if (ejb == null) {
			switch (mode) {
			case "IN_MEMORY":
				ejb = inMemoryEJB.get();
				break;

			case "DB":
				ejb = DatabaseEJB.get();
				break;
				
			default:
				log.error("Error configuration service Hotels, please check"
						+ " the value for key [hotel.mode]");
			}
		}
	}
	
	
	//-------- REST -- SERVICE ----------
	
	@GET
	@Produces({MediaType.APPLICATION_JSON, MediaType.APPLICATION_XML})
	@Path("/info/{hotelID}")
	@Timed(name = "info-service-timer", absolute = true, displayName = "Info service Timer",
			description = "Execution time for getInfo() service",
			unit = MetricUnits.MILLISECONDS)
	public HotelVO getInfo(@PathParam("hotelID") String id) {
		log.debug("Requested hotel information with id " + id);
		
		if (id == null || id.isEmpty()) {
			setError(403, "Wrong input");
			return null;
		}
		
		incCounterHotel(id);
		
		if (ejb == null) {
			setError(503, "Service Unavailable. Wrong Configurations");
			return null;
		}
		
		HotelVO result = null;
		try {
			result = ejb.getHotelInfo(id);
		} catch (SQLException | RuntimeException e) {
			log.error("Error in get Hotel Info: " + e.getMessage());
			setError(500, null);
			return null;
		}
		if (result == null)
			log.debug("Result for getting hotel data is null");
		else
			log.debug("Result for getting hotel data: " + result.toJSON());
		
		return result;
	}
	
	
	@GET
	@Produces({MediaType.APPLICATION_JSON, MediaType.APPLICATION_XML})
	@Path("/images/{hotelID}")
	@Timed(name = "images-service-timer", absolute = true, displayName = "Images service Timer",
	description = "Execution time for getImage() service",
	unit = MetricUnits.MILLISECONDS)
	public HotelImagesVO getImages(@PathParam("hotelID") String id) {
		log.debug("Requested hotel information with id " + id);
		if (ejb == null) {
			setError(503, "Service Unavailable. Wrong Configurations");
			return null;
		}
		
		HotelImagesVO result = null;
		try {
			result = ejb.getImages(id);
		} catch (SQLException e) {
			log.error("Error in getting Hotel images " + e.getMessage());
			setError(500, null);
			return null;
		} catch (RuntimeException e) {
        	log.error("Error during execution getImages() ", e);
        	setError(500, "Internal server error");
        	return null;
		}
		if (result == null)
			log.debug("Result for getting hotel image is null");
		else
			log.debug("Result for getting hotel image: " + result.toJSON());
		
		return result;
	}
	
	
	private void setError(int error, String msg) {
    	try {
    		response.sendError(error, msg);
		} catch (IOException e) {
			log.error(e);
			throw new RuntimeException(e);
		}
    }
}