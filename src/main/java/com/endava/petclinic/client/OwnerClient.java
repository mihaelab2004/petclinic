package com.endava.petclinic.client;

import com.endava.petclinic.model.Owner;
import io.restassured.http.ContentType;
import io.restassured.response.Response;

public class OwnerClient extends BaseClient {

    public Response createOwner(Owner owner) {

        return getBasicRestConfig()
                .contentType(ContentType.JSON)
                .body(owner)
                // .when()
                .post("api/owners");
    }

    public Response getOwnerById(Long ownerId) {

        return getBasicRestConfig()
                .pathParam("ownerId", ownerId)
                .get("api/owners/{ownerId}");
    }

    public Response getOwnerList() {
        return getBasicRestConfig()
                .get("api/owners/");
    }

    public Response deleteOwnerById(Long ownerId) {
        return getBasicRestConfig()
                .pathParam("ownerId", ownerId)
                .delete("api/owners/{ownerId}");
    }

    public Response updateOwnerById(Long ownerId, Owner owner) {
        return getBasicRestConfig()
                .pathParam("ownerId", ownerId)
                .body(owner)
                .contentType(ContentType.JSON)
                .put("api/owners/{ownerId}");
    }

    public Response getOwnerByLastName(String lastName, Owner owner) {
        return getBasicRestConfig()
                .pathParam("lastName", lastName)
                .body(owner)
                .contentType(ContentType.JSON)
                .put("api/owners/{lastName}");
    }
}
