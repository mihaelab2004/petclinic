package com.endava.petclinic.client;

import com.endava.petclinic.model.Visit;
import io.restassured.http.ContentType;
import io.restassured.response.Response;

public class VisitClient extends BaseClient {

    public Response createVisitClient(Visit visit) {
        return getBasicRestConfig()
                .contentType(ContentType.JSON)
                .body(visit)
                // .when()
                .post("api/visits");
    }

    public Response deleteVisitById(Long visitId) {
        return getBasicRestConfig()
                .pathParam("visitId", visitId)
                .delete("api/visits/{visitId}");
    }
}
