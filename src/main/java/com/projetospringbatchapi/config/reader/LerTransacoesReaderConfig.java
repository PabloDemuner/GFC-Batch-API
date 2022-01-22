package com.projetospringbatchapi.config.reader;

import java.sql.ResultSet;
import java.sql.SQLException;

import javax.sql.DataSource;

import org.springframework.batch.item.database.JdbcCursorItemReader;
import org.springframework.batch.item.database.builder.JdbcCursorItemReaderBuilder;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.jdbc.core.RowMapper;

import com.projetospringbatchapi.config.dominio.CartaoCreditoDominio;
import com.projetospringbatchapi.config.dominio.ClienteDominio;
import com.projetospringbatchapi.config.dominio.TransacaoDominio;

@Configuration
public class LerTransacoesReaderConfig {

	@Bean
	public JdbcCursorItemReader<TransacaoDominio> lerTransacoesReader(
			@Qualifier("appDataSource") DataSource dataSource) {
		
		return new JdbcCursorItemReaderBuilder<TransacaoDominio>()
				.name("lerTransacoesReader")
				.dataSource(dataSource)
				.sql("SELECT * FROM transacao join cartao_credito using (numero_cartao_credito) ORDER BY numero_cartao_credito")
				.rowMapper(rowMapperTransacao())
				.build();
	}

	private RowMapper<TransacaoDominio> rowMapperTransacao() {
		
		return new RowMapper<TransacaoDominio>() {
			
			@Override
			public TransacaoDominio mapRow(ResultSet rs, int rowNum) throws SQLException {
				
				CartaoCreditoDominio cartaoCredito = new CartaoCreditoDominio();
				cartaoCredito.setNumeroCartaoCredito(rs.getInt("numero_cartao_credito"));
				
				ClienteDominio cliente = new ClienteDominio();
				cliente.setId(rs.getInt("cliente"));
				cartaoCredito.setCliente(cliente);
				
				TransacaoDominio transacao = new TransacaoDominio();
				transacao.setId(rs.getInt("id"));
				transacao.setCartaoCredito(cartaoCredito);
				transacao.setDescricao(rs.getString("descricao"));
				transacao.setValor(rs.getDouble("valor"));
				transacao.setData(rs.getDate("data"));
				
				return transacao;
			}
		};
	}
	
}
