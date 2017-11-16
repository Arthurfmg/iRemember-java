package br.com.iremember.model.rest;

import java.util.ArrayList;
import java.util.List;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlRootElement;
import javax.xml.bind.annotation.XmlTransient;
import br.com.iremember.model.ColecaoUnidade;

@XmlRootElement
public class ColecoesUnidades {
	private List<ColecaoUnidade> colecoesUnidades = new ArrayList<>();

	public ColecoesUnidades() {
		
	}
	
	public ColecoesUnidades(List<ColecaoUnidade> colecoesUnidades) {
		super();
		this.colecoesUnidades = colecoesUnidades;
	}
	
	@XmlTransient
	public List<ColecaoUnidade> getColecoesUnidades() {
		return colecoesUnidades;
	}

	public void setColecoesUnidades(List<ColecaoUnidade> colecoesUnidades) {
		this.colecoesUnidades = colecoesUnidades;
	}
	
	@XmlElement(name="colecoesunidades")
	public List<ColecaoUnidade> getListaColecoesUnidades() {
		return getColecoesUnidades();
	}
}
