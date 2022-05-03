package com.endava.petclinic.visit;

import com.endava.petclinic.TestBaseClass;
import com.endava.petclinic.model.Owner;
import com.endava.petclinic.model.Pet;
import com.endava.petclinic.model.PetType;
import com.endava.petclinic.model.Visit;
import io.restassured.response.Response;
import org.apache.http.HttpStatus;
import org.junit.jupiter.api.Test;

import static org.hamcrest.Matchers.allOf;
import static org.hamcrest.Matchers.containsString;
import static org.hamcrest.Matchers.notNullValue;

public class CreateVisitTest extends TestBaseClass {

    @Test
    public void shouldCreateVisit() {
        //GIVEN
        fixture.createOwner()
                .createPetType()
                .createPet();

        Pet pet = fixture.getPet();

        Visit visit = testDataProvider.getVisit(pet);

        //WHEN
        Response response = visitClient.createVisitClient(visit);

        //THEN
        response.then().statusCode(HttpStatus.SC_CREATED)
                .body("id", notNullValue());

    }

    @Test
    public void shouldFailToCreateVisitGivenNoDescription() {
        //GIVEN
        fixture.createOwner()
                .createPetType()
                .createPet();

        Pet pet = fixture.getPet();

        Visit visit = testDataProvider.getVisit(pet);
        visit.setDescription("");

        //WHEN
        Response response = visitClient.createVisitClient(visit);

        //THEN
        response.then().statusCode(HttpStatus.SC_BAD_REQUEST)
                .header("errors", allOf(containsString("description"), containsString("may not be empty")));

    }

    @Test
    public void shouldFailToCreateVisitGivenEmptyDate() {
        //GIVEN
        fixture.createOwner()
                .createPetType()
                .createPet();

        Pet pet = fixture.getPet();

        Visit visit = testDataProvider.getVisit(pet);
        visit.setDate("");

        //WHEN
        Response response = visitClient.createVisitClient(visit);

        //THEN
        response.then().statusCode(HttpStatus.SC_BAD_REQUEST)
                .body("className", containsString("HttpMessageNotReadableException"));

    }

    @Test
    public void shouldFailToCreateVisitGivenNoOwner() {
        //GIVEN
        fixture.createOwner()
                .createPetType()
                .createPet();

        Pet pet = fixture.getPet();
        Owner owner = fixture.getOwner();

        Response deleteOwnerResponse = ownerClient.deleteOwnerById(owner.getId());
        deleteOwnerResponse.then().statusCode(HttpStatus.SC_NO_CONTENT);

        Visit visit = testDataProvider.getVisit(pet);

        //WHEN
        Response response = visitClient.createVisitClient(visit);

        //THEN
        response.then().statusCode(HttpStatus.SC_BAD_REQUEST)
                .body("className", containsString("DataIntegrityViolationException"));

    }

    @Test
    public void shouldFailToCreateVisitGivenNoPet() {
        //GIVEN
        fixture.createOwner()
                .createPetType()
                .createPet();

        Pet pet = fixture.getPet();

        Response deletePetResponse = petClient.deletePetById(pet.getId());
        deletePetResponse.then().statusCode(HttpStatus.SC_NO_CONTENT);

        Visit visit = testDataProvider.getVisit(pet);

        //WHEN
        Response response = visitClient.createVisitClient(visit);

        //THEN
        response.then().statusCode(HttpStatus.SC_BAD_REQUEST)
                .body("className", containsString("DataIntegrityViolationException"));

    }

    @Test
    public void shouldFailToCreateVisitGivenNoPetType() {
        //GIVEN
        fixture.createOwner()
                .createPetType()
                .createPet();

        Pet pet = fixture.getPet();
        PetType petType = fixture.getPetType();

        Response deletePetTypeResponse = petTypeClient.deletePetTypeById(petType.getId());
        deletePetTypeResponse.then().statusCode(HttpStatus.SC_NO_CONTENT);

        Visit visit = testDataProvider.getVisit(pet);

        //WHEN
        Response response = visitClient.createVisitClient(visit);

        //THEN
        response.then().statusCode(HttpStatus.SC_BAD_REQUEST)
                .body("className", containsString("DataIntegrityViolationException"));

    }

}
