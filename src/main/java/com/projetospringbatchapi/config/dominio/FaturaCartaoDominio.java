package com.projetospringbatchapi.config.dominio;

import java.util.ArrayList;
import java.util.List;

public class FaturaCartaoDominio {

	private ClienteDominio cliente;
	private CartaoCreditoDominio cartaoCredito;

	private List<TransacaoDominio> transacoes = new ArrayList<>();

	public ClienteDominio getCliente() {
		return cliente;
	}

	public void setCliente(ClienteDominio cliente) {
		this.cliente = cliente;
	}

	public CartaoCreditoDominio getCartaoCredito() {
		return cartaoCredito;
	}

	public void setCartaoCredito(CartaoCreditoDominio cartaoCredito) {
		this.cartaoCredito = cartaoCredito;
	}

	public List<TransacaoDominio> getTransacoes() {
		return transacoes;
	}

	public void setTransacoes(List<TransacaoDominio> transacoes) {
		this.transacoes = transacoes;
	}

}
