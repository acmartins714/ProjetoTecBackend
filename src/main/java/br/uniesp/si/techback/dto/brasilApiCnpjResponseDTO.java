package br.uniesp.si.techback.dto;

import com.fasterxml.jackson.annotation.JsonProperty;

public class brasilApiCnpjResponseDTO {

    private String cnpj;
    private String razao_social;
    private String nome_fantasia;
    private String logradouro;
    private String numero;
    private String complemento;
    private String bairro;
    private String municipio;
    private String uf;
    private String email;
    private String ddd_telefone_1;
    private String ddd_telefone_2;
    private int codigo_municipio_ibge;

    @JsonProperty("name")
    private String name;
    @JsonProperty("message")
    private String message;
    @JsonProperty("type")
    private String type;

}
