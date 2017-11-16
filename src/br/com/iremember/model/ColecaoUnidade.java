package br.com.iremember.model;

import javax.xml.bind.annotation.XmlRootElement;

@XmlRootElement
public class ColecaoUnidade {
	private String colecaoNome;
	private Integer numero;
	
	public ColecaoUnidade(String colecaoNome, Integer numero) {
		super();
		this.colecaoNome = colecaoNome;
		this.numero = numero;
	}
	
	public ColecaoUnidade() {
		
	}



	public String getColecaoNome() {
		return colecaoNome;
	}
	public void setColecaoNome(String colecaoNome) {
		this.colecaoNome = colecaoNome;
	}
	public Integer getNumero() {
		return numero;
	}
	public void setNumero(Integer numero) {
		this.numero = numero;
	}
}
