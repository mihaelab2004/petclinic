package com.endava.petclinic.owner;

import com.endava.petclinic.TestBaseClass;
import com.endava.petclinic.model.Owner;
import io.restassured.response.Response;
import org.apache.http.HttpStatus;
import org.junit.jupiter.api.Test;

import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.allOf;
import static org.hamcrest.Matchers.containsString;
import static org.hamcrest.Matchers.is;
import static org.hamcrest.Matchers.notNullValue;

public class CreateOwnerTest extends TestBaseClass {

    @Test
    public void shouldCreateOwner() {
        //GIVEN
        Owner owner = testDataProvider.getOwner();

        //WHEN
        Response response = ownerClient.createOwner(owner);

        //THEN
        response.then().statusCode(HttpStatus.SC_CREATED)
                .body("id", notNullValue());
        long id = response.body().jsonPath().getLong("id");

        Owner actualOwnerInDB = db.getOwnerById(id);
        assertThat(actualOwnerInDB, is(owner));

    }

    @Test
    public void shouldFailToCreateOwnerGivenEmptyFirstName() {
        //GIVEN
        Owner owner = testDataProvider.getOwner();
        owner.setFirstName("");


        //WHEN
        Response response = ownerClient.createOwner(owner);

        //THEN
        response.then().statusCode(HttpStatus.SC_BAD_REQUEST)
                .header("errors", allOf(containsString("firstName"), containsString("may not be empty")));
    }

    @Test
    public void shouldFailToCreateOwnerGivenFewDigitsTelephone() {
        //GIVEN
        Owner owner = testDataProvider.getOwner();
        owner.setTelephone(testDataProvider.getNumberWithDigits(0, 0));


        //WHEN
        Response response = ownerClient.createOwner(owner);

        //THEN
        response.then().statusCode(HttpStatus.SC_BAD_REQUEST)
                .header("errors", allOf(containsString("telephone"), containsString("numeric value out of bounds")));
    }

    @Test
    public void shouldFailToCreateOwnerGivenManyDigitsTelephone() {
        //GIVEN
        Owner owner = testDataProvider.getOwner();
        owner.setTelephone(testDataProvider.getNumberWithDigits(11, 100));


        //WHEN
        Response response = ownerClient.createOwner(owner);

        //THEN
        response.then().statusCode(HttpStatus.SC_BAD_REQUEST)
                .header("errors", allOf(containsString("telephone"), containsString("numeric value out of bounds")));
    }


}
