package br.com.iremember.model;

import javax.xml.bind.annotation.XmlRootElement;

@XmlRootElement
public class SerieResumo {
	private String serieNome;
	private Integer numeroTemporadaParou;
	private Integer numeroCapituloParou;
	
	public SerieResumo(String serieNome, Integer numeroTemporadaParou, Integer numeroCapituloParou) {
		super();
		this.serieNome = serieNome;
		this.numeroTemporadaParou = numeroTemporadaParou;
		this.numeroCapituloParou = numeroCapituloParou;
	}
	
	public SerieResumo() {
		
	}

	public String getSerieNome() {
		return serieNome;
	}

	public void setSerieNome(String serieNome) {
		this.serieNome = serieNome;
	}

	public Integer getNumeroTemporadaParou() {
		return numeroTemporadaParou;
	}

	public void setNumeroTemporadaParou(Integer numeroTemporadaParou) {
		this.numeroTemporadaParou = numeroTemporadaParou;
	}

	public Integer getNumeroCapituloParou() {
		return numeroCapituloParou;
	}

	public void setNumeroCapituloParou(Integer numeroCapituloParou) {
		this.numeroCapituloParou = numeroCapituloParou;
	}
}
