package br.com.iremember.dao;

import java.util.List;

public interface IDao<T> {

	
	public void salva(T t);

	public void remove(T t);

	public List<T> listaTodos();

	public T buscaPorld(Long id);
}
