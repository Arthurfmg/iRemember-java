package br.com.iremember.dao;

import java.util.List;

import javax.persistence.Query;

import br.com.iremember.model.Usuario;

public class UsuarioDao extends JpaDaoBase<Usuario> implements IDao<Usuario>{
	public Usuario buscaPorNome(String nome) {
		Query query = em.createNamedQuery("Usuario.buscaPorNome").setParameter("nome", nome);
		List<Usuario> usuarios = query.getResultList();
		if (!usuarios.isEmpty())
			return usuarios.get(0);
		return null;
	}
	
	public Usuario verificaLogin(String email, String password) {
		Query query = em.createNamedQuery("Usuario.verificaLogin")
				.setParameter("email", email)
				.setParameter("password", password);
		
		List<Usuario> usuarios = query.getResultList();
		
		if (!usuarios.isEmpty())
			return usuarios.get(0);
		return null;
	}
	
	public void remove(String nome) {
		em.getTransaction().begin();
		Query query = em.createQuery("DELETE FROM Usuario u WHERE u.nome = :nome ").setParameter("nome", nome);
		query.executeUpdate();
		em.getTransaction().commit();
	}
}
