package com.endava.petclinic.model;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;
import lombok.NonNull;
import lombok.RequiredArgsConstructor;


@JsonIgnoreProperties(ignoreUnknown = true)
@Data
@NoArgsConstructor
@RequiredArgsConstructor
public class Pet {

    @EqualsAndHashCode.Exclude
    private Long id;
    @NonNull
    private String birthDate;
    @NonNull
    private String name;
    @NonNull
    private Owner owner;
    @NonNull
    private PetType type;


    @Override
    public String toString() {
        ObjectMapper objectMapper = new ObjectMapper();
        try {
            return objectMapper.writeValueAsString(this);
        } catch (JsonProcessingException e) {
            return super.toString();
        }
    }

}
