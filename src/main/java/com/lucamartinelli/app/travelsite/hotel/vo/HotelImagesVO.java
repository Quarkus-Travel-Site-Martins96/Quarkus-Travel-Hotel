package com.lucamartinelli.app.travelsite.hotel.vo;

import java.io.Serializable;
import java.util.List;

import javax.json.Json;
import javax.json.JsonArrayBuilder;
import javax.json.JsonObject;
import javax.json.JsonObjectBuilder;
import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlRootElement;

@XmlRootElement
@XmlAccessorType(XmlAccessType.FIELD)
public class HotelImagesVO implements Serializable {
	
	private static final long serialVersionUID = -1497635564512281806L;
	
	private List<String> images;
	private String hotelId;
	
	public HotelImagesVO() {
	}

	public HotelImagesVO(List<String> images, String hotelId) {
		super();
		this.images = images;
		this.hotelId = hotelId;
	}

	public List<String> getImages() {
		return images;
	}

	public void setImages(List<String> images) {
		this.images = images;
	}

	public String getHotelId() {
		return hotelId;
	}

	public void setHotelId(String hotelId) {
		this.hotelId = hotelId;
	}

	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + ((hotelId == null) ? 0 : hotelId.hashCode());
		result = prime * result + ((images == null) ? 0 : images.hashCode());
		return result;
	}

	@Override
	public boolean equals(Object obj) {
		if (this == obj)
			return true;
		if (obj == null)
			return false;
		if (getClass() != obj.getClass())
			return false;
		HotelImagesVO other = (HotelImagesVO) obj;
		if (hotelId == null) {
			if (other.hotelId != null)
				return false;
		} else if (!hotelId.equals(other.hotelId))
			return false;
		if (images == null) {
			if (other.images != null)
				return false;
		} else if (!images.equals(other.images))
			return false;
		return true;
	}

	@Override
	public String toString() {
		return "HotelImagesVO [images=" + images + ", hotelId=" + hotelId + "]";
	}
	
	public JsonObject toJSON() {
		final JsonObjectBuilder job = Json.createObjectBuilder();
		final JsonArrayBuilder jab = Json.createArrayBuilder();
		job.add("hotelId", hotelId);
		images.forEach(jab::add);
		job.add("images", jab.build());
		return job.build();
	}
	
}
