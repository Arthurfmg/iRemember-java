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
import br.com.iremember.dao.SerieDao;
import br.com.iremember.dao.UsuarioDao;
import br.com.iremember.dao.UsuarioDaoFactory;
import br.com.iremember.model.Colecao;
import br.com.iremember.model.Serie;
import br.com.iremember.model.Usuario;
import br.com.iremember.model.rest.Colecoes;
import br.com.iremember.model.rest.Series;
import br.com.iremember.model.rest.Usuarios;

@Path("/usuarios")
@Consumes({ MediaType.TEXT_XML, MediaType.APPLICATION_XML,
		MediaType.APPLICATION_JSON })
@Produces({ MediaType.TEXT_XML, MediaType.APPLICATION_XML,
		MediaType.APPLICATION_JSON })
public class UsuarioService {

	private UsuarioDao usuarioDao = UsuarioDaoFactory.getInstance().getUsuarioDao();

	private static final int TAMANHO_PAGINA = 10;

	@GET
	@Path("/login/{email}/{password}")
	public Usuario entrar(@PathParam("email") String email, @PathParam("password") String password) {
		Usuario usuario = usuarioDao.verificaLogin(email, password);
		if (usuario != null)
			return usuario;

		throw new WebApplicationException(Status.NOT_FOUND);
	}
		
	@GET
	@Path("{nome}")
	public Usuario encontreUsuario(@PathParam("nome") String nomeDoUsuario) {
		Usuario usuario = usuarioDao.buscaPorNome(nomeDoUsuario);
		if (usuario != null)
			return usuario;

		throw new WebApplicationException(Status.NOT_FOUND);

	}

	@GET
	public Usuarios listeTodasOsUsuarios(@QueryParam("pagina") int pagina) {
		List<Usuario> usuarios = usuarioDao.listaPaginada(pagina, TAMANHO_PAGINA);
		return new Usuarios(usuarios);
	}

	@POST
	public Response criarUsuario(Usuario usuario) {
		
		try {
			usuarioDao.salva(usuario);
		//} catch (UsuarioJaExisteException e) {
		} catch (Exception e) {
			throw new WebApplicationException(Status.CONFLICT);
		}

		URI uri = UriBuilder.fromPath("usuarios/{nome}").build(
				usuario.getNome());

		return Response.created(uri).entity(usuario).build();
	}

	@PUT
	@Path("{nome}")
	public void atualizarUsuario(@PathParam("nome") String nome, Usuario usuario) {
		//encontreUsuario(nome);
		usuario.setNome(nome);
		usuarioDao.atualiza(usuario);
	}

	@DELETE
	@Path("{nome}")
	public void apagarUsuario(@PathParam("nome") String nome) {
		usuarioDao.remove(nome);
	}

	@GET
	@Path("{nome}")
	@Produces("image/*")
	public Response recuperaImagem(@PathParam("nome") String nomeDoUsuario)
			throws IOException {
		InputStream is = UsuarioService.class.getResourceAsStream("/"
				+ nomeDoUsuario + ".jpg");

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