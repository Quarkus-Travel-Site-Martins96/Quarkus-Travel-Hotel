package com.lucamartinelli.app.travelsite.hotel;

import io.quarkus.test.junit.QuarkusTest;

import static org.junit.jupiter.api.Assertions.assertEquals;

import org.junit.jupiter.api.Test;

import com.lucamartinelli.app.travelsite.hotel.vo.HotelVO;

import static io.restassured.RestAssured.given;

@QuarkusTest
public class HotelTest {

	@Test
	public void testHelloEndpoint() {
		final HotelVO resp = given()
			.when()
				.get("/hotel/info/1")
			.then()
				.statusCode(200)
				.extract().as(HotelVO.class);
		
		assertEquals("1", resp.getHotelID(), "Hotel ID is not equals with input");
		
	}

}