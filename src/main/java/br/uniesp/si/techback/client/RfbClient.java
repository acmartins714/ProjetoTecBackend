package br.uniesp.si.techback.client;

import br.uniesp.si.techback.dto.RfbApiResponseDTO;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;

@FeignClient(name = "rfbClient", url = "${receitaapi.url:https://receitaws.com.br}")
public interface RfbClient {

    @GetMapping("/v1/cnpj/{cnpj}")
    RfbApiResponseDTO buscarPorCnpj(@PathVariable("cnpj") String cnpj);
}