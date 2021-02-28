package com.lucamartinelli.app.travelsite.hotel.vo;

import java.io.Serializable;

import javax.json.Json;
import javax.json.JsonObject;
import javax.json.JsonObjectBuilder;
import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlRootElement;

@XmlRootElement
@XmlAccessorType(XmlAccessType.FIELD)
public class HotelVO implements Serializable {
	
	private static final long serialVersionUID = 2064238635969654502L;
	
	private String hotelID;
	private String title;
	private String longDescription;
	private String shortDescription;
	private String location;
	private short stars;
	private String previewImage;
	
	public HotelVO() {
	}

	public HotelVO(String hotelID, String title, String longDescription, String shortDescription, String location,
			short stars, String previewImage) {
		super();
		this.hotelID = hotelID;
		this.title = title;
		this.longDescription = longDescription;
		this.shortDescription = shortDescription;
		this.location = location;
		this.stars = stars;
		this.previewImage = previewImage;
	}

	public String getHotelID() {
		return hotelID;
	}

	public void setHotelID(String hotelID) {
		this.hotelID = hotelID;
	}

	public String getTitle() {
		return title;
	}

	public void setTitle(String title) {
		this.title = title;
	}

	public String getLongDescription() {
		return longDescription;
	}

	public void setLongDescription(String longDescription) {
		this.longDescription = longDescription;
	}

	public String getShortDescription() {
		return shortDescription;
	}

	public void setShortDescription(String shortDescription) {
		this.shortDescription = shortDescription;
	}

	public String getLocation() {
		return location;
	}

	public void setLocation(String location) {
		this.location = location;
	}

	public short getStars() {
		return stars;
	}

	public void setStars(short stars) {
		this.stars = stars;
	}

	public String getPreviewImage() {
		return previewImage;
	}

	public void setPreviewImage(String previewImage) {
		this.previewImage = previewImage;
	}

	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + ((hotelID == null) ? 0 : hotelID.hashCode());
		result = prime * result + ((location == null) ? 0 : location.hashCode());
		result = prime * result + ((longDescription == null) ? 0 : longDescription.hashCode());
		result = prime * result + ((previewImage == null) ? 0 : previewImage.hashCode());
		result = prime * result + ((shortDescription == null) ? 0 : shortDescription.hashCode());
		result = prime * result + stars;
		result = prime * result + ((title == null) ? 0 : title.hashCode());
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
		HotelVO other = (HotelVO) obj;
		if (hotelID == null) {
			if (other.hotelID != null)
				return false;
		} else if (!hotelID.equals(other.hotelID))
			return false;
		if (location == null) {
			if (other.location != null)
				return false;
		} else if (!location.equals(other.location))
			return false;
		if (longDescription == null) {
			if (other.longDescription != null)
				return false;
		} else if (!longDescription.equals(other.longDescription))
			return false;
		if (previewImage == null) {
			if (other.previewImage != null)
				return false;
		} else if (!previewImage.equals(other.previewImage))
			return false;
		if (shortDescription == null) {
			if (other.shortDescription != null)
				return false;
		} else if (!shortDescription.equals(other.shortDescription))
			return false;
		if (stars != other.stars)
			return false;
		if (title == null) {
			if (other.title != null)
				return false;
		} else if (!title.equals(other.title))
			return false;
		return true;
	}

	@Override
	public String toString() {
		return "HotelVO [hotelID=" + hotelID + ", title=" + title + ", longDescription=" + longDescription
				+ ", shortDescription=" + shortDescription + ", location=" + location + ", stars=" + stars
				+ ", previewImage=" + previewImage + "]";
	}

	public JsonObject toJSON() {
		final JsonObjectBuilder job = Json.createObjectBuilder();
		job.add("hotelID", hotelID);
		job.add("title", title);
		job.add("longDescription", longDescription);
		job.add("shortDescription", shortDescription);
		job.add("location", location);
		job.add("stars", stars);
		job.add("previewImage", previewImage);
		return job.build();
		
	}
	
	
	
}
