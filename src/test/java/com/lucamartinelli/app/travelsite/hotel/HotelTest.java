package com.lucamartinelli.app.travelsite.hotel;

import io.quarkus.test.junit.QuarkusTest;
import org.junit.jupiter.api.Test;

import static io.restassured.RestAssured.given;

@QuarkusTest
public class HotelTest {

    @Test
    public void testHelloEndpoint() {
        given()
          .when().get("/hotel");
    }

}