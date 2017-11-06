package br.com.iremember.dao;

import org.junit.Assert;
import org.junit.Test;
import br.com.iremember.model.Unidade;

public class UnidadeDaoTest {

	private UnidadeDao unidadeDao = UnidadeDaoFactory.getInstance().getUnidadeDao();
	
	@Test
	public void testSave(){
		Unidade novaUnidade = new Unidade("Capítulo 3", "", 3, ColecaoDaoFactory.getInstance().getColecaoDao().buscaPorld(2l));

		unidadeDao.salva(novaUnidade);

		Assert.assertNotNull(novaUnidade.getId());
	}
}
