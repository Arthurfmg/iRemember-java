package br.com.iremember.dao;

public class UnidadeDaoFactory {
	public static UnidadeDaoFactory instance = new UnidadeDaoFactory();
	private UnidadeDao unidadeDao;

	private UnidadeDaoFactory() {}
		
	public static UnidadeDaoFactory getInstance(){
		return instance;
	}
	
	public UnidadeDao getUnidadeDao(){
		if(this.unidadeDao == null)
			this.unidadeDao = new UnidadeDao();
		return this.unidadeDao;
	}
}
