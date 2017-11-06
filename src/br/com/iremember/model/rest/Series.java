package br.com.iremember.model.rest;

import java.util.ArrayList;
import java.util.List;

import javax.ws.rs.core.Link;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlRootElement;
import javax.xml.bind.annotation.XmlTransient;

import br.com.iremember.model.Serie;

@XmlRootElement
public class Series {
	private List<Serie> series = new ArrayList<>();

	public Series() {
		
	}
	
	public Series(List<Serie> series) {
		this.series = series;
	}
	
	@XmlTransient
	public List<Serie> getSeries() {
		return series;
	}

	public void setSeries(List<Serie> series) {
		this.series = series;
	}
	
	@XmlElement(name="link")
	public List<Link> getLinks() {
		List<Link> links = new ArrayList<>();
		for (Serie serie : getSeries()) {
			
			Link link = Link.fromPath("series/{nome}")
					.rel("serie")
					.title(serie.getNome())
					.build(serie.getNome());
			
			
			links.add(link);
		}
		return links;
	}
	
	

	public void setLinks (List<Link> links) {
		
	}
}
