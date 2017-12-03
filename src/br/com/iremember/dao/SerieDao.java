package br.com.iremember.dao;

import java.util.List;
import javax.persistence.Query;
import javax.persistence.TypedQuery;
import br.com.iremember.model.Serie;
import br.com.iremember.model.SerieResumo;
import br.com.iremember.model.rest.Series;
import br.com.iremember.model.rest.SeriesResumos;

public class SerieDao extends JpaDaoBase<Serie> implements IDao<Serie>{
	public Serie buscaPorNome(String nome) {
		Query query = em.createNamedQuery("Serie.buscaPorNome").setParameter("nome", nome);
		List<Serie> series = query.getResultList();
		if (!series.isEmpty())
			return series.get(0);
		return null;
	}
	
	public Series buscaPorUsuario(Long usuario_id) {
		Query query = em.createNamedQuery("Serie.buscaPorUsuario").setParameter("usuario", new UsuarioDao().buscaPorld(usuario_id));
		List<Serie> series = query.getResultList();
		if (!series.isEmpty())
			return new Series(series);
		return null;
	}
	
	public void remove(String nome) {
		em.getTransaction().begin();
		Query query = em.createQuery("DELETE FROM Serie s WHERE s.nome = :nome ").setParameter("nome", nome);
		query.executeUpdate();
		em.getTransaction().commit();
	}
	
	public SeriesResumos buscaSeriesResumos(Long usuario_id) {		
		TypedQuery<SerieResumo> consulta = em.createQuery(
				"SELECT NEW br.com.iremember.model.SerieResumo(s.nome, s.numeroTemporadaParou, s.numeroCapituloParou) FROM Serie s Where s.usuario = :usuario", 
				SerieResumo.class
				).setParameter("usuario", new UsuarioDao().buscaPorld(usuario_id));
			
		List<SerieResumo> seriesResumos = consulta.getResultList();
		
		if (!seriesResumos.isEmpty())
			return new SeriesResumos(seriesResumos);
		return null;
	}
}
