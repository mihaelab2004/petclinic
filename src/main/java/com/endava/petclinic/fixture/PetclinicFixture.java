package com.endava.petclinic.fixture;

import com.endava.petclinic.client.OwnerClient;
import com.endava.petclinic.client.PetClient;
import com.endava.petclinic.client.PetTypeClient;
import com.endava.petclinic.client.VisitClient;
import com.endava.petclinic.model.Owner;
import com.endava.petclinic.model.Pet;
import com.endava.petclinic.model.PetType;
import com.endava.petclinic.testData.TestDataProvider;
import io.restassured.response.Response;
import lombok.Getter;
import org.apache.http.HttpStatus;

public class PetclinicFixture {

    private OwnerClient ownerClient = new OwnerClient();
    private PetClient petClient = new PetClient();
    private PetTypeClient petTypeClient = new PetTypeClient();
    private VisitClient visitClient = new VisitClient();

    private TestDataProvider dataProvider = new TestDataProvider();

    @Getter
    private Owner owner;
    @Getter
    private PetType petType;
    @Getter
    private Pet pet;

    public PetclinicFixture createOwner() {
        owner = dataProvider.getOwner();
        Response createOwnerResponse = ownerClient.createOwner(owner);
        createOwnerResponse.then().statusCode(HttpStatus.SC_CREATED);

        long id = createOwnerResponse.body().jsonPath().getLong("id");
        owner.setId(id);

        return this;
    }

    public PetclinicFixture createPetType() {
        petType = dataProvider.getPetType();
        Response createPetTypeResponse = petTypeClient.createPetType(petType);
        createPetTypeResponse.then().statusCode(HttpStatus.SC_CREATED);

        long petTypeId = createPetTypeResponse.body().jsonPath().getLong("id");
        petType.setId(petTypeId);

        return this;
    }

    public PetclinicFixture createPet() {
        pet = dataProvider.getPet(owner, petType);
        Response createPetResponse = petClient.createPet(pet);
        createPetResponse.then().statusCode(HttpStatus.SC_CREATED);

        long petId = createPetResponse.body().jsonPath().getLong("id");
        pet.setId(petId);

        return this;
    }
}

