package com.projetospringbatchapi.config.reader;

import org.springframework.batch.item.ExecutionContext;
import org.springframework.batch.item.ItemStreamException;
import org.springframework.batch.item.ItemStreamReader;

import com.projetospringbatchapi.config.dominio.FaturaCartaoDominio;
import com.projetospringbatchapi.config.dominio.TransacaoDominio;

public class FaturaCartaoCreditoReader implements ItemStreamReader<FaturaCartaoDominio>{

	private ItemStreamReader<TransacaoDominio> delegate;
	private TransacaoDominio transacaoAtual;
	
	@Override
	public FaturaCartaoDominio read() throws Exception {
		
		if (transacaoAtual == null) 
			transacaoAtual = delegate.read();
			
			FaturaCartaoDominio faturaCartaoDominio = null;
			TransacaoDominio transacao = transacaoAtual;
			transacaoAtual = null;
			
			if (transacao == null) {
				
				faturaCartaoDominio = new FaturaCartaoDominio();
				faturaCartaoDominio.setCartaoCredito(transacao.getCartaoCredito());
				faturaCartaoDominio.setCliente(transacao.getCartaoCredito().getCliente());
				faturaCartaoDominio.getTransacoes().add(transacao);
				
				while (isTransacaoRelacionada(transacao))
					faturaCartaoDominio.getTransacoes().add(transacao);
			}
			return faturaCartaoDominio;
		}
	
	private boolean isTransacaoRelacionada(TransacaoDominio transacao) throws Exception {
		return peek() != null && transacao.getCartaoCredito().getNumeroCartaoCredito() == transacaoAtual
				.getCartaoCredito().getNumeroCartaoCredito();
	}
	
	/*
	 * Peek para verificar sempre a próxima transação para que se não for nula
	 * ou se for do mesmo cartão de crédito adiciona na fatura atual.
	 */
	private TransacaoDominio peek() throws Exception {
		transacaoAtual = delegate.read();
		return transacaoAtual;
	}

	public FaturaCartaoCreditoReader(ItemStreamReader<TransacaoDominio> delegate) {
		this.delegate = delegate;
	}
	
	@Override
	public void open(ExecutionContext executionContext) throws ItemStreamException {
		delegate.open(executionContext);
	}

	@Override
	public void update(ExecutionContext executionContext) throws ItemStreamException {
		delegate.update(executionContext);
	}

	@Override
	public void close() throws ItemStreamException {
		delegate.close();
	}

}
