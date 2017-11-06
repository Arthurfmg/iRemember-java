package br.com.iremember.dao;

import java.util.List;
import javax.persistence.Query;
import br.com.iremember.model.Colecao;
import br.com.iremember.model.rest.Colecoes;

public class ColecaoDao extends JpaDaoBase<Colecao> implements IDao<Colecao>{
	public Colecao buscaPorNome(String nome) {
		Query query = em.createNamedQuery("Colecao.buscaPorNome").setParameter("nome", nome);
		List<Colecao> colecoes = query.getResultList();
		if (!colecoes.isEmpty())
			return colecoes.get(0);
		return null;
	}
	
	public Colecoes buscaPorUsuario(Long usuario_id) {		
		Query query = em.createNamedQuery("Colecao.buscaPorUsuario").setParameter("usuario", new UsuarioDao().buscaPorld(usuario_id));
		List<Colecao> colecoes = query.getResultList();
		if (!colecoes.isEmpty())
			return new Colecoes(colecoes);
		return null;
	}
	
	public void remove(String nome) {
		em.getTransaction().begin();
		Query query = em.createQuery("DELETE FROM Colecao c WHERE c.nome = :nome ").setParameter("nome", nome);
		query.executeUpdate();
		em.getTransaction().commit();
	}
}