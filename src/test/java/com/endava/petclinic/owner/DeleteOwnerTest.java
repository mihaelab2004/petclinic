package com.endava.petclinic.owner;

import com.endava.petclinic.TestBaseClass;
import com.endava.petclinic.filters.LogFilter;
import com.endava.petclinic.model.Owner;
import io.restassured.response.Response;
import org.apache.http.HttpStatus;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.junit.jupiter.api.Test;

public class DeleteOwnerTest extends TestBaseClass {

    private static final Logger LOGGER = LogManager.getLogger(LogFilter.class);

    @Test
    public void shouldDeleteOwner() {
        //GIVEN
        Owner owner = fixture.createOwner()
                .getOwner();

        //WHEN
        Response response = ownerClient.deleteOwnerById(owner.getId());

        //THEN
        response.then().statusCode(HttpStatus.SC_NO_CONTENT);
    }

}
