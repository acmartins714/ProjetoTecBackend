package br.uniesp.si.techback.client;

import br.uniesp.si.techback.dto.brasilApiCnpjResponseDTO;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;

public interface BrasiApiCnpj {
    @FeignClient(name = "brasilApiCnpj", url = "${brasilapi.url:https://brasilapi.com.br}")
    public interface BrasilApiCNPJ {

        @GetMapping("/cnpj/v1/{cnpj}")
        brasilApiCnpjResponseDTO buscarPorCnpj(@PathVariable("cnpj") String cnpj);
    }
}
