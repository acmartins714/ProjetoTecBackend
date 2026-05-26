package br.uniesp.si.techback.dto;

import com.fasterxml.jackson.annotation.JsonProperty;

public class brasilApiCepResponseDTO {

    private String cep;
    private String state;
    private String city;
    private String neighborhood;
    private String street;

    @JsonProperty("name")
    private String name;
    @JsonProperty("message")
    private String message;
    @JsonProperty("type")
    private String type;

}
