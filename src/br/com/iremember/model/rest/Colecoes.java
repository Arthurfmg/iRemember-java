package br.com.iremember.model.rest;

import java.util.ArrayList;
import java.util.List;
import javax.ws.rs.core.Link;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlRootElement;
import javax.xml.bind.annotation.XmlTransient;

import br.com.iremember.model.Colecao;

@XmlRootElement
public class Colecoes {
	private List<Colecao> colecoes = new ArrayList<>();

	public Colecoes() {
		
	}

	public Colecoes(List<Colecao> colecoes) {
		this.colecoes = colecoes;
	}

	@XmlTransient
	public List<Colecao> getColecoes() {
		return colecoes;
	}

	public void setColecoes(List<Colecao> colecoes) {
		this.colecoes = colecoes;
	}
	
	
	@XmlElement(name="link")
	public List<Link> getLinks() {
		List<Link> links = new ArrayList<>();
		for (Colecao colecao : getColecoes()) {
			
			Link link = Link.fromPath("colecoes/{nome}")
					.rel("colecao")
					.title(colecao.getNome())
					.build(colecao.getNome());
			
			
			links.add(link);
		}
		return links;
	}
	
	public void setLinks (List<Link> links) {
		
	}
}
