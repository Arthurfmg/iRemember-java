package br.com.iremember.services;

import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.net.URI;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;
import javax.ws.rs.Consumes;
import javax.ws.rs.DELETE;
import javax.ws.rs.GET;
import javax.ws.rs.POST;
import javax.ws.rs.PUT;
import javax.ws.rs.Path;
import javax.ws.rs.PathParam;
import javax.ws.rs.Produces;
import javax.ws.rs.QueryParam;
import javax.ws.rs.WebApplicationException;
import javax.ws.rs.core.Context;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;
import javax.ws.rs.core.UriBuilder;
import javax.ws.rs.core.Response.Status;

import br.com.iremember.dao.ColecaoDao;
import br.com.iremember.dao.ColecaoDaoFactory;
import br.com.iremember.dao.UsuarioDao;
import br.com.iremember.model.Colecao;
import br.com.iremember.model.rest.Colecoes;
import br.com.iremember.model.rest.ColecoesUnidades;
import br.com.iremember.model.rest.Unidades;

@Path("/colecoes")
@Consumes({ MediaType.TEXT_XML, MediaType.APPLICATION_XML,
		MediaType.APPLICATION_JSON })
@Produces({ MediaType.TEXT_XML, MediaType.APPLICATION_XML,
		MediaType.APPLICATION_JSON })
public class ColecaoService {

	private ColecaoDao colecaoDao = ColecaoDaoFactory.getInstance().getColecaoDao();

	private static final int TAMANHO_PAGINA = 10;

	@GET
	@Path("{nome}")
	public Colecao encontreColecao(@PathParam("nome") String nomeDaColecao) {
		Colecao colecao = colecaoDao.buscaPorNome(nomeDaColecao);
		if (colecao != null)
			return colecao;

		throw new WebApplicationException(Status.NOT_FOUND);
	}
	
	/*@GET
	@Path("/ultimas/")
	public Unidades listarUltimasColecoes() {
		//List<Colecao> colecoes = (List<Colecao>) new ColecaoDao().buscaPorUsuario(1l);
		return colecaoDao.buscaUltimasColecoes();
	}*/
	@GET
	@Path("/ultimas/{usuario_id}")
	public ColecoesUnidades listarUltimasColecoes(@PathParam("usuario_id") Long usuario_id) {
		//List<Colecao> colecoes = (List<Colecao>) new ColecaoDao().buscaPorUsuario(1l);
		return colecaoDao.buscaUltimasColecoes2(usuario_id);
	}
	
	@GET
	@Path("/usuario/{id}")
	public Colecoes listarColecoesDoUsuario(@PathParam("id") Long id) {
		return colecaoDao.buscaPorUsuario(id);
	}

	@GET
	public Colecoes listeTodasAsColecoes(@QueryParam("pagina") int pagina) {
		List<Colecao> Colecoes = colecaoDao.listaPaginada(pagina, TAMANHO_PAGINA);
		return new Colecoes(Colecoes);
	}

	@POST
	@Path("/usuario/{usuario_id}")
	public Response criarColecao(@PathParam("usuario_id") Long usuario_id, Colecao colecao) {
		
		try {
			colecao.setUsuario(new UsuarioDao().buscaPorld(usuario_id));
			colecaoDao.salva(colecao);
		} catch (Exception e) {
			throw new WebApplicationException(Status.CONFLICT);
		}

		URI uri = UriBuilder.fromPath("colecoes/{nome}").build(
				colecao.getNome());

		return Response.created(uri).entity(colecao).build();
	}

	/*@PUT
	@Path("{nome}")
	public void atualizarColecao(@PathParam("nome") String nome, Colecao colecao) {
		//encontreColecao(nome);
		//colecao.setNome(nome);
		colecaoDao.atualiza(colecao);
	}*/
	
	/*
	@PUT
	//@Path("/id/{nome}")
	public void atualizarColecao(Colecao colecao) {
		//encontreColecao(nome);
		//colecao.setNome(nome);
		
		colecao.setUsuario(new UsuarioDao().buscaPorld(1l));
		
		colecaoDao.atualiza(colecao);
	}
	*/
	
	@PUT
	@Path("/usuarios/{usuario_id}")
	public void atualizarColecao(@PathParam("usuario_id") Long usuario_id, Colecao colecao) {
		colecao.setUsuario(new UsuarioDao().buscaPorld(usuario_id));
		colecaoDao.atualiza(colecao);
	}

	@DELETE
	@Path("{nome}")
	public void apagarColecao(@PathParam("nome") String nome) {
		colecaoDao.remove(nome);
	}

	@GET
	@Path("{nome}")
	@Produces("image/*")
	public Response recuperaImagem(@PathParam("nome") String nomeDaColecao)
			throws IOException {
		InputStream is = ColecaoService.class.getResourceAsStream("/"
				+ nomeDaColecao + ".jpg");

		if (is == null)
			throw new WebApplicationException(Status.NOT_FOUND);

		byte[] dados = new byte[is.available()];
		is.read(dados);
		is.close();

		return Response.ok(dados).type("image/jpg").build();
	}

	private static Map<String, String> EXTENSOES;

	static {
		EXTENSOES = new HashMap<>();
		EXTENSOES.put("image/jpg", ".jpg");
	}

	@POST
	@Path("{nome}")
	@Consumes("image/*")
	public Response criaImagem(@PathParam("nome") String nomeDaImagem,
			@Context HttpServletRequest req, byte[] dados) throws IOException,
			InterruptedException {

		String userHome = System.getProperty("user.home");
		String mimeType = req.getContentType();
		FileOutputStream fos = new FileOutputStream(userHome
				+ java.io.File.separator + nomeDaImagem
				+ EXTENSOES.get(mimeType));

		fos.write(dados);
		fos.flush();
		fos.close();

		return Response.ok().build();
	}
}