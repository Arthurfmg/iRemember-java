package br.com.iremember.dao;

import java.util.ArrayList;
import java.util.List;

import org.junit.Assert;
import org.junit.Test;
import br.com.iremember.model.Colecao;
import br.com.iremember.model.Unidade;

public class ColecaoDaoTest {

	private ColecaoDao colecaoDao = ColecaoDaoFactory.getInstance().getColecaoDao();
	
	@Test
	public void testSave(){	
		Colecao primeiraColecao = new Colecao("Blade", "Anime de samurai");
		Colecao segundaColecao = new Colecao("Gintama", "Anime de comédia");
		
		Unidade unidade1 = new Unidade("Capítulo 1", "", 1, segundaColecao);
		Unidade unidade2 = new Unidade("Capítulo 2", "", 2, segundaColecao);
		
		List<Unidade> unidades = new ArrayList<>();
		unidades.add(unidade1);
		unidades.add(unidade2);
		
		segundaColecao.setUnidades(unidades);
		
		colecaoDao.salva(primeiraColecao);
		colecaoDao.salva(segundaColecao);
		
		Assert.assertNotNull(primeiraColecao.getId());
		Assert.assertNotNull(segundaColecao.getId());
	}
}
