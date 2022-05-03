package com.endava.petclinic;

import com.endava.petclinic.client.OwnerClient;
import com.endava.petclinic.client.PetClient;
import com.endava.petclinic.client.PetTypeClient;
import com.endava.petclinic.client.VisitClient;
import com.endava.petclinic.extensions.TestReporterExtension;
import com.endava.petclinic.fixture.PetclinicFixture;
import com.endava.petclinic.services.DBService;
import com.endava.petclinic.testData.TestDataProvider;
import org.junit.jupiter.api.extension.ExtendWith;

@ExtendWith(TestReporterExtension.class)
public class TestBaseClass {

    protected OwnerClient ownerClient = new OwnerClient();
    protected PetClient petClient = new PetClient();
    protected PetTypeClient petTypeClient = new PetTypeClient();
    protected VisitClient visitClient = new VisitClient();
    protected TestDataProvider testDataProvider = new TestDataProvider();

    protected PetclinicFixture fixture = new PetclinicFixture();

    protected DBService db = new DBService();
}
