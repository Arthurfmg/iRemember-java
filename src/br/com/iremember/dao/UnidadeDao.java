package br.com.iremember.dao;

import java.util.List;

import javax.persistence.Query;

import br.com.iremember.model.Unidade;

public class UnidadeDao extends JpaDaoBase<Unidade> implements IDao<Unidade>{
	public Unidade buscaPorNome(String nome) {
		Query query = em.createNamedQuery("Unidade.buscaPorNome").setParameter("nome", nome);
		List<Unidade> unidades = query.getResultList();
		if (!unidades.isEmpty())
			return unidades.get(0);
		return null;
	}
	
	public void remove(String nome) {
		em.getTransaction().begin();
		Query query = em.createQuery("DELETE FROM Unidade u WHERE u.nome = :nome ").setParameter("nome", nome);
		query.executeUpdate();
		em.getTransaction().commit();
	}
}
