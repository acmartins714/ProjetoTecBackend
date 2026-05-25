package br.uniesp.si.techback.dto;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Data;

@Data
public class RfbApiResponseDTO {

    private String cnpj;
    private String nome;
    private String fantasia;
    private String logradouro;
    private String numero;
    private String complemento;
    private String bairro;
    private String municipio;
    private String uf;
    private String cep;
    private String email;
    private String telefone;

    @JsonProperty("status")
    private String status;
    @JsonProperty("message")
    private String message;

}
