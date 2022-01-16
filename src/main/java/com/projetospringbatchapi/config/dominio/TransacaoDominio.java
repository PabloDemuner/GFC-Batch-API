package com.projetospringbatchapi.config.dominio;

import java.util.Date;

public class TransacaoDominio {

	private int id;
	private CartaoCreditoDominio cartaoCredito;
	private String descricao;
	private Double valor;
	private Date data;

	public int getId() {
		return id;
	}

	public void setId(int id) {
		this.id = id;
	}

	public CartaoCreditoDominio getCartaoCredito() {
		return cartaoCredito;
	}

	public void setCartaoCredito(CartaoCreditoDominio cartaoCredito) {
		this.cartaoCredito = cartaoCredito;
	}

	public String getDescricao() {
		return descricao;
	}

	public void setDescricao(String descricao) {
		this.descricao = descricao;
	}

	public Double getValor() {
		return valor;
	}

	public void setValor(Double valor) {
		this.valor = valor;
	}

	public Date getData() {
		return data;
	}

	public void setData(Date data) {
		this.data = data;
	}
}
