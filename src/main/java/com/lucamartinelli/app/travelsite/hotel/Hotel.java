package com.lucamartinelli.app.travelsite.hotel;

import java.io.IOException;

import javax.annotation.PostConstruct;
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
import org.jboss.logging.Logger;

import com.lucamartinelli.app.travelsite.hotel.ejb.HotelDBEJB;
import com.lucamartinelli.app.travelsite.hotel.ejb.HotelEJB;
import com.lucamartinelli.app.travelsite.hotel.ejb.HotelInMemoryEJB;
import com.lucamartinelli.app.travelsite.hotel.vo.HotelImagesVO;
import com.lucamartinelli.app.travelsite.hotel.vo.HotelVO;

@Path("/hotel")
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
	
	@GET
	@Produces({MediaType.APPLICATION_JSON, MediaType.APPLICATION_XML})
	@Path("/info/{hotelID}")
	public HotelVO getInfo(@PathParam("hotelID") String id) {
		log.debug("Requested hotel information with id " + id);
		if (ejb == null) {
			try {
				response.sendError(503, "Service Unavailable. Wrong Configurations");
			} catch (IOException e) {
				log.error("Error during setting status in Servlet response");
				throw new RuntimeException(e);
			}
			return null;
		}
		
		final HotelVO result =  ejb.getHotelInfo(id);
		if (result == null) {
			try {
				response.sendError(500);
				return null;
			} catch (IOException e) {
				log.error("Error during setting status in Servlet response");
				throw new RuntimeException(e);
			}
		}
		
		return result;
	}
	
	
	@GET
	@Produces({MediaType.APPLICATION_JSON, MediaType.APPLICATION_XML})
	@Path("/images/{hotelID}")
	public HotelImagesVO getImages(@PathParam("hotelID") String id) {
		log.debug("Requested hotel information with id " + id);
		if (ejb == null) {
			try {
				response.sendError(503, "Service Unavailable. Wrong Configurations");
			} catch (IOException e) {
				log.error("Error during setting status in Servlet response");
				throw new RuntimeException(e);
			}
			return null;
		}
		
		final HotelImagesVO result =  ejb.getImages(id);
		if (result == null) {
			try {
				response.sendError(500);
				return null;
			} catch (IOException e) {
				log.error("Error during setting status in Servlet response");
				throw new RuntimeException(e);
			}
		}
		
		return result;
	}
}