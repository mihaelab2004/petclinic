package com.endava.petclinic.owner;

import com.endava.petclinic.TestBaseClass;
import com.endava.petclinic.model.Owner;
import io.restassured.response.Response;
import org.apache.http.HttpStatus;
import org.junit.jupiter.api.Test;

public class GetOwnerByIdTest extends TestBaseClass {

    @Test
    public void shouldGetOwnerById() {
        //GIVEN
        Owner owner = fixture.createOwner()
                .getOwner();

        //WHEN
        Response response = ownerClient.getOwnerById(owner.getId());

        //THEN
        response.then().statusCode(HttpStatus.SC_OK);

    }

    @Test
    public void shouldFailToGetOwnerByNonExistingId() {
        //GIVEN
        Owner owner = fixture.createOwner()
                .getOwner();

        Response deleteOwnerResponse = ownerClient.deleteOwnerById(owner.getId());
        deleteOwnerResponse.then().statusCode(HttpStatus.SC_NO_CONTENT);

        //WHEN
        Response response = ownerClient.getOwnerById(owner.getId());

        //THEN
        response.then().statusCode(HttpStatus.SC_NOT_FOUND);

    }
}
