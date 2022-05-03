package com.endava.petclinic.client;

import com.endava.petclinic.model.PetType;
import io.restassured.http.ContentType;
import io.restassured.response.Response;

public class PetTypeClient extends BaseClient {

    public Response createPetType(PetType petType) {
        return getBasicRestConfig()
                .contentType(ContentType.JSON)
                .body(petType)
                // .when()
                .post("api/pettypes");
    }

    public Response deletePetTypeById(Long petTypeId) {
        return getBasicRestConfig()
                .pathParam("petTypeId", petTypeId)
                .delete("api/pettypes/{petTypeId}");
    }

}
