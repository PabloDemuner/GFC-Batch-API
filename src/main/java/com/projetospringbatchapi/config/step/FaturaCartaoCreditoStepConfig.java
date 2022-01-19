package com.projetospringbatchapi.config.step;


import org.springframework.batch.core.Step;
import org.springframework.batch.core.configuration.annotation.StepBuilderFactory;
import org.springframework.batch.item.ItemProcessor;
import org.springframework.batch.item.ItemStreamReader;
import org.springframework.batch.item.ItemWriter;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import com.projetospringbatchapi.config.dominio.FaturaCartaoDominio;
import com.projetospringbatchapi.config.dominio.TransacaoDominio;
import com.projetospringbatchapi.config.reader.FaturaCartaoCreditoReader;

@Configuration
public class FaturaCartaoCreditoStepConfig {

	@Autowired
	private StepBuilderFactory stepBuilderFactory;
	
	@Bean
	public Step faturaCartaoCreditoStep(
			ItemStreamReader<TransacaoDominio> lerTransacoesReader,
			ItemProcessor<FaturaCartaoDominio, FaturaCartaoDominio> carregarDadosClienteProcessor,
			ItemWriter<FaturaCartaoDominio> escreverFaturaCartaoCredito) {
		return stepBuilderFactory
				.get("faturaCartaoCreditoStep")
				/*Efetua a leitura(reader) de FaturaCartaoDominio e
				 *  retorna para a escrita(writer) FaturaCartaoDominio
				*/
				.<FaturaCartaoDominio, FaturaCartaoDominio>chunk(1)
				//Delegate para o BD
				.reader(new FaturaCartaoCreditoReader(lerTransacoesReader))
				.processor(carregarDadosClienteProcessor)
				.writer(escreverFaturaCartaoCredito)
				.build();
	}
}
