package com.endava.petclinic.pet;

import com.endava.petclinic.TestBaseClass;
import com.endava.petclinic.model.Owner;
import com.endava.petclinic.model.Pet;
import io.restassured.response.Response;
import org.apache.http.HttpStatus;
import org.junit.jupiter.api.Test;

public class GetPetById extends TestBaseClass {

    @Test
    public void shouldGetPetById() {

        //GIVEN
        fixture.createOwner()
                .createPetType()
                .createPet();

        Pet pet = fixture.getPet();

        //WHEN
        Response response = petClient.getPetById(pet.getId());

        //THEN
        response.then().statusCode(HttpStatus.SC_OK);
    }

    @Test
    public void shouldDeletePetGivenDeletionOfOwner() {
        //GIVEN
        fixture.createOwner()
                .createPetType()
                .createPet();

        Pet pet = fixture.getPet();
        Owner owner = fixture.getOwner();

        Response deleteOwnerResponse = ownerClient.deleteOwnerById(owner.getId());
        deleteOwnerResponse.then().statusCode(HttpStatus.SC_NO_CONTENT);

        //WHEN
        Response response = petClient.getPetById(pet.getId());

        //THEN
        response.then().statusCode(HttpStatus.SC_NOT_FOUND);
    }
}
