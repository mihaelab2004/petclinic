package com.endava.petclinic.client;

import com.endava.petclinic.filters.AuthenticationFilter;
import com.endava.petclinic.filters.LogFilter;
import io.restassured.specification.RequestSpecification;

import static com.endava.petclinic.util.EnvReader.getBasePath;
import static com.endava.petclinic.util.EnvReader.getBaseUri;
import static com.endava.petclinic.util.EnvReader.getPort;
import static io.restassured.RestAssured.given;

public class BaseClient {

    protected RequestSpecification getBasicRestConfig() {
        return given().filters(new AuthenticationFilter(), new LogFilter())
                .baseUri(getBaseUri())
                .port(getPort())
                .basePath(getBasePath());
    }
}
