package br.com.iremember.model.rest;

import java.util.ArrayList;
import java.util.List;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlRootElement;
import javax.xml.bind.annotation.XmlTransient;
import br.com.iremember.model.SerieResumo;

@XmlRootElement
public class SeriesResumos {
	private List<SerieResumo> seriesResumos = new ArrayList<>();

	public SeriesResumos() {
		
	}
	
	public SeriesResumos(List<SerieResumo> seriesResumos) {
		super();
		this.seriesResumos = seriesResumos;
	}
	
	@XmlTransient
	public List<SerieResumo> getSeriesResumos() {
		return seriesResumos;
	}

	public void setSeriesResumos(List<SerieResumo> seriesResumos) {
		this.seriesResumos = seriesResumos;
	}
	
	@XmlElement(name="seriesresumos")
	public List<SerieResumo> getListaSeriesResumos() {
		return getSeriesResumos();
	}
}
