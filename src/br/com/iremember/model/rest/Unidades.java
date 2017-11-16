package br.com.iremember.model.rest;

import java.util.ArrayList;
import java.util.List;
import javax.ws.rs.core.Link;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlRootElement;
import javax.xml.bind.annotation.XmlTransient;

import br.com.iremember.model.Unidade;

@XmlRootElement
public class Unidades {
	private List<Unidade> unidades = new ArrayList<>();

	public Unidades() {
		
	}
	
	public Unidades(List<Unidade> unidades) {
		this.unidades = unidades;
	}
	
	@XmlTransient
	public List<Unidade> getUnidades() {
		return unidades;
	}

	public void setUnidades(List<Unidade> unidades) {
		this.unidades = unidades;
	}
	
	@XmlElement(name="link")
	public List<Link> getLinks() {
		List<Link> links = new ArrayList<>();
		for (Unidade unidade : getUnidades()) {
			
			Link link = Link.fromPath("unidades/{nome}")
					.rel("colecao")
					.title(unidade.getNome())
					.build(unidade.getNome());
			
			
			links.add(link);
		}
		return links;
	}
	
	@XmlElement(name="colecoes")
	public List<Unidade> getResumoColecao() {
		return getUnidades();
	}
	
	public void setLinks (List<Link> links) {
		
	}
}
