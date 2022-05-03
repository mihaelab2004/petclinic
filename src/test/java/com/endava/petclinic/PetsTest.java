package com.endava.petclinic;

import org.apache.http.HttpStatus;
import org.junit.jupiter.api.Test;

import static io.restassured.RestAssured.given;

public class PetsTest {

    @Test
    public void getPetList() {
        given().baseUri("http://bhdtest.endava.com")
                .port(8080)
                .basePath("petclinic")

                .when()
                .get("api/pets")

                .then()
                .statusCode(HttpStatus.SC_OK);
    }

}

