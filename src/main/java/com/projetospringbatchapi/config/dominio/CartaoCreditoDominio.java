package com.projetospringbatchapi.config.dominio;

public class CartaoCreditoDominio {

	private int numeroCartaoCredito;
	private ClienteDominio cliente;

	public int getNumeroCartaoCredito() {
		return numeroCartaoCredito;
	}

	public void setNumeroCartaoCredito(int numeroCartaoCredito) {
		this.numeroCartaoCredito = numeroCartaoCredito;
	}

	public ClienteDominio getCliente() {
		return cliente;
	}

	public void setCliente(ClienteDominio cliente) {
		this.cliente = cliente;
	}
}
