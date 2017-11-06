package br.com.iremember.dao;

public class SerieDaoFactory {
	public static SerieDaoFactory instance = new SerieDaoFactory();
	private SerieDao serieDao;

	private SerieDaoFactory() {}
		
	public static SerieDaoFactory getInstance(){
		return instance;
	}
	
	public SerieDao getSerieDao(){
		if(this.serieDao == null)
			this.serieDao = new SerieDao();
		return this.serieDao;
	}
}