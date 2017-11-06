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
import br.com.iremember.dao.UnidadeDao;
import br.com.iremember.dao.UnidadeDaoFactory;
import br.com.iremember.model.Unidade;
import br.com.iremember.model.rest.Unidades;

@Path("/unidades")
@Consumes({ MediaType.TEXT_XML, MediaType.APPLICATION_XML,
		MediaType.APPLICATION_JSON })
@Produces({ MediaType.TEXT_XML, MediaType.APPLICATION_XML,
		MediaType.APPLICATION_JSON })
public class UnidadeService {

	private UnidadeDao unidadeDao = UnidadeDaoFactory.getInstance().getUnidadeDao();

	private static final int TAMANHO_PAGINA = 10;

	@GET
	@Path("{nome}")
	public Unidade encontreUnidade(@PathParam("nome") String nomeDaUnidade) {
		Unidade unidade = unidadeDao.buscaPorNome(nomeDaUnidade);
		if (unidade != null)
			return unidade;

		throw new WebApplicationException(Status.NOT_FOUND);

	}

	@GET
	public Unidades listeTodasAsUnidades(@QueryParam("pagina") int pagina) {
		List<Unidade> unidades = unidadeDao.listaPaginada(pagina, TAMANHO_PAGINA);
		return new Unidades(unidades);
	}

	@POST
	public Response criarUnidade(Unidade unidade) {
		
		try {
			unidadeDao.salva(unidade);
		//} catch (UnidadeJaExisteException e) {
		} catch (Exception e) {
			throw new WebApplicationException(Status.CONFLICT);
		}

		URI uri = UriBuilder.fromPath("unidades/{nome}").build(
				unidade.getNome());

		return Response.created(uri).entity(unidade).build();
	}

	@PUT
	@Path("{nome}")
	public void atualizarUnidade(@PathParam("nome") String nome, Unidade unidade) {
		//encontreUnidade(nome);
		unidade.setNome(nome);
		unidadeDao.atualiza(unidade);
	}

	@DELETE
	@Path("{nome}")
	public void apagarUnidade(@PathParam("nome") String nome) {
		unidadeDao.remove(nome);
	}

	@GET
	@Path("{nome}")
	@Produces("image/*")
	public Response recuperaImagem(@PathParam("nome") String nomeDaUnidade)
			throws IOException {
		InputStream is = UnidadeService.class.getResourceAsStream("/"
				+ nomeDaUnidade + ".jpg");

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