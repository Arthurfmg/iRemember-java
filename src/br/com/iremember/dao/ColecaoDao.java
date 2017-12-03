package br.com.iremember.dao;

import java.util.List;
import javax.persistence.Query;
import javax.persistence.TypedQuery;

import br.com.iremember.model.Colecao;
import br.com.iremember.model.ColecaoUnidade;
import br.com.iremember.model.Unidade;
import br.com.iremember.model.rest.Colecoes;
import br.com.iremember.model.rest.ColecoesUnidades;
import br.com.iremember.model.rest.Unidades;

public class ColecaoDao extends JpaDaoBase<Colecao> implements IDao<Colecao>{
	public Colecao buscaPorNome(String nome) {
		Query query = em.createNamedQuery("Colecao.buscaPorNome").setParameter("nome", nome);
		List<Colecao> colecoes = query.getResultList();
		if (!colecoes.isEmpty())
			return colecoes.get(0);
		return null;
	}
	
	/*public Unidades buscaUltimasColecoes() {		
		Query query = em.createNamedQuery("Colecao.buscaUltimasColecoes");
		List<Unidade> unidades = query.getResultList();
		if (!unidades.isEmpty())
			return new Unidades(unidades);
		return null;
	}*/
	public ColecoesUnidades buscaUltimasColecoes2(Long usuario_id) {		
		TypedQuery<ColecaoUnidade> consulta = em.createQuery(
				"SELECT NEW br.com.iremember.model.ColecaoUnidade(c.nome, u.numero) FROM Unidade u INNER JOIN u.colecao c Where c.usuario = :usuario", 
				ColecaoUnidade.class
				).setParameter("usuario", new UsuarioDao().buscaPorld(usuario_id));
		List<ColecaoUnidade> colecoesUnidades = consulta.getResultList();
		
		if (!colecoesUnidades.isEmpty())
			return new ColecoesUnidades(colecoesUnidades);
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