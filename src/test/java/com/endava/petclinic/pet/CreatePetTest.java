package com.endava.petclinic.pet;

import com.endava.petclinic.TestBaseClass;
import com.endava.petclinic.model.Owner;
import com.endava.petclinic.model.Pet;
import com.endava.petclinic.model.PetType;
import io.restassured.response.Response;
import org.apache.http.HttpStatus;
import org.junit.jupiter.api.Test;

import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.allOf;
import static org.hamcrest.Matchers.containsString;
import static org.hamcrest.Matchers.is;
import static org.hamcrest.Matchers.notNullValue;

public class CreatePetTest extends TestBaseClass {

    @Test
    public void shouldCreatePet() {
        //GIVEN
        Owner owner = fixture.createOwner()
                .getOwner();

        PetType petType = fixture.createPetType()
                .getPetType();

        Pet pet = testDataProvider.getPet(owner, petType);

        //WHEN
        Response response = petClient.createPet(pet);

        //THEN
        response.then().statusCode(HttpStatus.SC_CREATED)
                .body("id", notNullValue());
        long id = response.body().jsonPath().getLong("id");

        Pet actualPetInDB = db.getPetById(id);
        assertThat(actualPetInDB.getId(), is(id));
        assertThat(actualPetInDB.getName(), is(pet.getName()));
        assertThat(actualPetInDB.getBirthDate(), is(pet.getBirthDate()));
    }

    @Test
    public void shouldFailToCreatePetGivenEmptyName() {
        //GIVEN
        Owner owner = fixture.createOwner()
                .getOwner();

        PetType petType = fixture.createPetType()
                .getPetType();

        Pet pet = testDataProvider.getPet(owner, petType);
        pet.setName("");

        //WHEN
        Response response = petClient.createPet(pet);

        //THEN
        response.then().statusCode(HttpStatus.SC_BAD_REQUEST)
                .header("errors", allOf(containsString("name"), containsString("may not be empty")));
    }

    @Test
    public void shouldFailToCreatePetGivenEmptyBirthDate() {
        //GIVEN
        Owner owner = fixture.createOwner()
                .getOwner();

        PetType petType = fixture.createPetType()
                .getPetType();

        Pet pet = testDataProvider.getPet(owner, petType);
        pet.setBirthDate("");

        //WHEN
        Response response = petClient.createPet(pet);

        //THEN
        response.then().statusCode(HttpStatus.SC_BAD_REQUEST)
                .body("className", containsString("HttpMessageNotReadableException"));
    }

    @Test
    public void shouldFailToCreatePetGivenNoExistingOwner() {
        //GIVEN
        Owner owner = fixture.createOwner()
                .getOwner();

        Response deleteOwnerResponse = ownerClient.deleteOwnerById(owner.getId());
        deleteOwnerResponse.then().statusCode(HttpStatus.SC_NO_CONTENT);

        PetType petType = fixture.createPetType()
                .getPetType();

        Pet pet = testDataProvider.getPet(owner, petType);

        //WHEN
        Response response = petClient.createPet(pet);

        //THEN
        response.then().statusCode(HttpStatus.SC_BAD_REQUEST)
                .body("className", containsString("DataIntegrityViolationException"));
    }

    @Test
    public void shouldFailToCreatePetGivenNoExistingPetType() {
        //GIVEN
        Owner owner = fixture.createOwner()
                .getOwner();

        PetType petType = new PetType();
        petType.setId(3000000L);

        Pet pet = testDataProvider.getPet(owner, petType);

        //WHEN
        Response response = petClient.createPet(pet);

        //THEN
        response.then().statusCode(HttpStatus.SC_BAD_REQUEST)
                .body("className", containsString("DataIntegrityViolationException"));

    }

    @Test
    public void shouldCreatePetGivenBirthdayInTheFuture() {
        //GIVEN
        Owner owner = fixture.createOwner()
                .getOwner();

        PetType petType = fixture.createPetType()
                .getPetType();

        Pet pet = testDataProvider.getPet(owner, petType);
        pet.setBirthDate("2025/03/03");

        //WHEN
        Response response = petClient.createPet(pet);

        //THEN
        response.then().statusCode(HttpStatus.SC_CREATED)
                .body("id", notNullValue());

    }

}
