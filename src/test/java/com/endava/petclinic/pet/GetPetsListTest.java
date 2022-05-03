package com.endava.petclinic.pet;

import com.endava.petclinic.TestBaseClass;
import com.endava.petclinic.model.Pet;
import io.restassured.response.Response;
import org.apache.http.HttpStatus;
import org.junit.jupiter.api.Test;

import java.util.List;

import static io.restassured.RestAssured.withArgs;
import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.hasItem;
import static org.hamcrest.Matchers.is;

public class GetPetsListTest extends TestBaseClass {

    @Test
    public void shouldReturnPetsList() {
        //GIVEN
        fixture.createOwner()
                .createPetType()
                .createPet();

        Pet pet = fixture.getPet();

        //WHEN
        Response response = petClient.getPetsList();

        //THEN
        response.then().statusCode(HttpStatus.SC_OK)
                .body("find{it -> it.id == %s}.name", withArgs(pet.getId()), is(pet.getName()));

        Pet actualPet = response.body().jsonPath().param("id", pet.getId()).getObject("find{ it -> it.id == id}", Pet.class);
        assertThat(actualPet, is(pet));

        List<Pet> petList = response.body().jsonPath().getList("", Pet.class);
        assertThat(petList, hasItem(pet));

    }


}
