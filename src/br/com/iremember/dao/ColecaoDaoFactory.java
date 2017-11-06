package br.com.iremember.dao;

public class ColecaoDaoFactory {
	public static ColecaoDaoFactory instance = new ColecaoDaoFactory();
	private ColecaoDao colecaoDao;

	private ColecaoDaoFactory() {}
		
	public static ColecaoDaoFactory getInstance(){
		return instance;
	}
	
	public ColecaoDao getColecaoDao(){
		if(this.colecaoDao == null)
			this.colecaoDao = new ColecaoDao();
		return this.colecaoDao;
	}
}