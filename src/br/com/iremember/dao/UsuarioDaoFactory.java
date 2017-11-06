package br.com.iremember.dao;

public class UsuarioDaoFactory {
	public static UsuarioDaoFactory instance = new UsuarioDaoFactory();
	private UsuarioDao usuarioDao;

	private UsuarioDaoFactory() {}
		
	public static UsuarioDaoFactory getInstance(){
		return instance;
	}
	
	public UsuarioDao getUsuarioDao(){
		if(this.usuarioDao == null)
			this.usuarioDao = new UsuarioDao();
		return this.usuarioDao;
	}
}
