package com.endava.petclinic.filters;

import com.endava.petclinic.util.EnvReader;
import io.restassured.filter.Filter;
import io.restassured.filter.FilterContext;
import io.restassured.response.Response;
import io.restassured.specification.FilterableRequestSpecification;
import io.restassured.specification.FilterableResponseSpecification;

public class AuthenticationFilter implements Filter {


    @Override
    public Response filter(FilterableRequestSpecification requestSpec, FilterableResponseSpecification responseSpec, FilterContext context) {
        if (EnvReader.getAdminUsername() != null && EnvReader.getAdminPassword() != null) {
            requestSpec.auth().preemptive().basic(EnvReader.getAdminUsername(), EnvReader.getAdminPassword());
        }
        return context.next(requestSpec, responseSpec);
    }
}
