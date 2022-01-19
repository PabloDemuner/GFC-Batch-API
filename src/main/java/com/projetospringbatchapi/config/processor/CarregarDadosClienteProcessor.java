package com.projetospringbatchapi.config.processor;

import org.springframework.batch.item.ItemProcessor;
import org.springframework.batch.item.validator.ValidationException;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Component;
import org.springframework.web.client.RestTemplate;

import com.projetospringbatchapi.config.dominio.ClienteDominio;
import com.projetospringbatchapi.config.dominio.FaturaCartaoDominio;


@Component
public class CarregarDadosClienteProcessor  implements ItemProcessor<FaturaCartaoDominio, FaturaCartaoDominio> {
	
	private RestTemplate restTemplate = new RestTemplate();
	
	@Override
	public FaturaCartaoDominio process(FaturaCartaoDominio faturaCartaoCredito) throws Exception {
		String uri = String.format("http://my-json-server.typicode.com/giuliana-bezerra/demo/profile/%d", 
				faturaCartaoCredito.getCliente().getId());
		ResponseEntity<ClienteDominio> response = restTemplate.getForEntity(uri, ClienteDominio.class);
		
		if (response.getStatusCode() != HttpStatus.OK)
			throw new ValidationException("Cliente não encontrado!");
		
		faturaCartaoCredito.setCliente(response.getBody());
		return faturaCartaoCredito;
	}

}
