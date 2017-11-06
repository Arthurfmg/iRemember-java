package br.com.iremember.dao;

import org.junit.Assert;
import org.junit.Test;
import br.com.iremember.model.Colecao;
import br.com.iremember.model.Serie;

public class SerieDaoTest {

	private SerieDao serieDao = SerieDaoFactory.getInstance().getSerieDao();
	
	@Test
	public void testSave(){
		//Serie novaSerie = new Serie("Sobrenatural", "Eu assistia muito no SBT", 100, 10, 7, 6);
		Serie novaSerie = new Serie("Dragon ball Z", "Muito foda", 300, 10, 10, 3);

		serieDao.salva(novaSerie);
		
		Assert.assertNotNull(novaSerie.getId());
	}
}
