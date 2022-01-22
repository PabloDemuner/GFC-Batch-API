package com.projetospringbatchapi.config.writer;

import java.io.IOException;
import java.io.Writer;
import java.text.NumberFormat;
import java.text.SimpleDateFormat;

import org.springframework.batch.item.file.FlatFileFooterCallback;
import org.springframework.batch.item.file.FlatFileHeaderCallback;
import org.springframework.batch.item.file.FlatFileItemWriter;
import org.springframework.batch.item.file.MultiResourceItemWriter;
import org.springframework.batch.item.file.ResourceSuffixCreator;
import org.springframework.batch.item.file.builder.FlatFileItemWriterBuilder;
import org.springframework.batch.item.file.builder.MultiResourceItemWriterBuilder;
import org.springframework.batch.item.file.transform.LineAggregator;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.core.io.FileSystemResource;

import com.projetospringbatchapi.config.dominio.FaturaCartaoDominio;
import com.projetospringbatchapi.config.dominio.TransacaoDominio;

@Configuration
public class ArquivoFaturaCartaoWriterConfig {

	@Bean
	public MultiResourceItemWriter<FaturaCartaoDominio> arquivosFaturaCartao() {

		return new MultiResourceItemWriterBuilder<FaturaCartaoDominio>()
				.name("arquivosFaturaCartao")
				.resource(new FileSystemResource("files/fatura"))
				.itemCountLimitPerResource(1)
				.resourceSuffixCreator(criadorExtencao())
				.delegate(arquivoFaturaCartao())
				.build();
	}

	private FlatFileItemWriter<FaturaCartaoDominio> arquivoFaturaCartao() {

		return new FlatFileItemWriterBuilder<FaturaCartaoDominio>()
				.name("arquivoFaturaCartao")
				.resource(new FileSystemResource("files/fatura.txt"))
				.lineAggregator(linhaAgregada())
				.headerCallback(cabecalhoArquivo())
				.footerCallback(rodapeArquivo())
				.build();
	}

	@Bean
	public FlatFileFooterCallback rodapeArquivo() {
		return new TotalTransacoesRodapeArquivo();
	}

	private FlatFileHeaderCallback cabecalhoArquivo() {
		return new FlatFileHeaderCallback() {
			
			@Override
			public void writeHeader(Writer writer) throws IOException {
				writer.append(String.format("%121s\n", "Cartão XPTO"));
				writer.append(String.format("%121s\n\n", "Rua Vergueiro, 131"));
			}
		};
	}

	// Agregação de todas as linhas do cabeçalho
	private LineAggregator<FaturaCartaoDominio> linhaAgregada() {
		return new LineAggregator<FaturaCartaoDominio>() {

			@Override
			public String aggregate(FaturaCartaoDominio faturaCartaoCredito) {
				StringBuilder writer = new StringBuilder();
				writer.append(String.format("Nome: %s\n", faturaCartaoCredito.getCliente().getNome()));
				writer.append(String.format("Endereço: %s\n\n\n", faturaCartaoCredito.getCliente().getEndereco()));
				writer.append(String.format("Fatura completa do cartão %d\n",
						faturaCartaoCredito.getCartaoCredito().getNumeroCartaoCredito()));
				writer.append(
						"-------------------------------------------------------------------------------------------------------------------------\n");
				writer.append("DATA DESCRICAO VALOR\n");
				writer.append(
						"-------------------------------------------------------------------------------------------------------------------------\n");

				// Iteração das transsações para imprimir ja formatado.
				for (TransacaoDominio transacao : faturaCartaoCredito.getTransacoes()) {
					writer.append(String.format("\n[%10s] %-80s - %s",
							new SimpleDateFormat("dd/MM/yyyy").format(transacao.getData()), transacao.getDescricao(),
							NumberFormat.getCurrencyInstance().format(transacao.getValor())));
				}
				return writer.toString();
			}

		};
	}

	private ResourceSuffixCreator criadorExtencao() {
		return new ResourceSuffixCreator() {

			@Override
			public String getSuffix(int index) {
				return index + ".txt";
			}
		};
	}
}
