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
import javax.ws.rs.core.Response.Status;
import javax.ws.rs.core.UriBuilder;

import br.com.iremember.dao.ColecaoDao;
import br.com.iremember.dao.SerieDao;
import br.com.iremember.dao.SerieDaoFactory;
import br.com.iremember.dao.UsuarioDao;
import br.com.iremember.model.Serie;
import br.com.iremember.model.rest.Colecoes;
import br.com.iremember.model.rest.ColecoesUnidades;
import br.com.iremember.model.rest.Series;
import br.com.iremember.model.rest.SeriesResumos;

@Path("/series")
@Consumes({ MediaType.TEXT_XML, MediaType.APPLICATION_XML,
		MediaType.APPLICATION_JSON })
@Produces({ MediaType.TEXT_XML, MediaType.APPLICATION_XML,
		MediaType.APPLICATION_JSON })
public class SerieService {

	private SerieDao serieDao = SerieDaoFactory.getInstance().getSerieDao();

	private static final int TAMANHO_PAGINA = 10;

	@GET
	@Path("{nome}")
	public Serie encontreSerie(@PathParam("nome") String nomeDaSerie) {
		Serie serie = serieDao.buscaPorNome(nomeDaSerie);
		if (serie != null)
			return serie;

		throw new WebApplicationException(Status.NOT_FOUND);
	}
	
	@GET
	@Path("/usuario/{usuario_id}")
	public Series listarSeriesDoUsuario(@QueryParam("usuario_id") Long usuario_id) {
		//@SuppressWarnings("unchecked")
		//List<Serie> series = (List<Serie>) new SerieDao().buscaPorUsuario(usuario_id);
		//return new Series(series);
		return serieDao.buscaPorUsuario(1l);
	}
	
	@GET
	@Path("/resumos/")
	public SeriesResumos listarSeriesResumos() {
		//List<Colecao> colecoes = (List<Colecao>) new ColecaoDao().buscaPorUsuario(1l);
		return serieDao.buscaSeriesResumos();
	}

	@GET
	public Series listeTodasAsSeries(@QueryParam("pagina") int pagina) {
		List<Serie> series = serieDao.listaPaginada(pagina, TAMANHO_PAGINA);
		return new Series(series);
	}

	@POST
	@Path("usuario/{usuario_id}")
	public Response criarSerie(@QueryParam("usuario_id") Long usuario_id, Serie serie) {
		
		try {
			serie.setUsuario(new UsuarioDao().buscaPorld(1l));
			serieDao.salva(serie);
		//} catch (SerieJaExisteException e) {
		} catch (Exception e) {
			throw new WebApplicationException(Status.CONFLICT);
		}

		URI uri = UriBuilder.fromPath("series/{nome}").build(
				serie.getNome());

		return Response.created(uri).entity(serie).build();
	}

	/*@PUT
	@Path("{nome}")
	public void atualizarSerie(@PathParam("nome") String nome, Serie serie) {
		//encontreSerie(nome);
		serie.setNome(nome);
		serieDao.atualiza(serie);
	}*/
	
	@PUT
	public void atualizarSerie(Serie serie) {
		serie.setUsuario(new UsuarioDao().buscaPorld(1l));
		
		serieDao.atualiza(serie);
	}

	@DELETE
	@Path("{nome}")
	public void apagarSerie(@PathParam("nome") String nome) {
		serieDao.remove(nome);
	}

	@GET
	@Path("{nome}")
	@Produces("image/*")
	public Response recuperaImagem(@PathParam("nome") String nomeDaSerie)
			throws IOException {
		InputStream is = SerieService.class.getResourceAsStream("/"
				+ nomeDaSerie + ".jpg");

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