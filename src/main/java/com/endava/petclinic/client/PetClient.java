package com.endava.petclinic.client;

import com.endava.petclinic.model.Pet;
import io.restassured.http.ContentType;
import io.restassured.response.Response;

import static com.endava.petclinic.util.EnvReader.getBasePath;

public class PetClient extends BaseClient {

    public Response createPet(Pet pet) {

        return getBasicRestConfig()
                .basePath(getBasePath())
                .contentType(ContentType.JSON)
                .body(pet)
                .when()
                .post("api/pets");
    }

    public Response getPetById(Long petId) {
        return getBasicRestConfig()
                .basePath(getBasePath())
                .pathParam("petId", petId)
                .get("api/pets/{petId}");
    }

    public Response getPetsList() {
        return getBasicRestConfig()
                .basePath(getBasePath())
                .get("api/pets");
    }

    public Response deletePetById(Long petId) {
        return getBasicRestConfig()
                .basePath(getBasePath())
                .pathParam("petId", petId)
                .delete("api/pets/{petId}");
    }

    public Response updatePetById(Long petId, Pet pet) {
        return getBasicRestConfig()
                .basePath(getBasePath())
                .pathParam("petId", petId)
                .body(pet)
                .contentType(ContentType.JSON)
                .put("api/pets/{petId}");
    }

    public Response getPetByType(String type, Pet pet) {
        return getBasicRestConfig()
                .basePath(getBasePath())
                .pathParam("type", type)
                .body(pet)
                .contentType(ContentType.JSON)
                .put("api/pets/{pettypes}");
    }
}

