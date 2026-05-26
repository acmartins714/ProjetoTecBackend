package br.uniesp.si.techback.client;

import br.uniesp.si.techback.dto.brasilApiCepResponseDTO;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;

public interface BrasiApiCep {
    @FeignClient(name = "brasilApiCep", url = "${brasilapi.url:https://brasilapi.com.br}")
    public interface BrasilApiCEP {

        @GetMapping("/cep/v1/{cep}")
        brasilApiCepResponseDTO buscarPorCep(@PathVariable("cep") String cep);
    }
}
