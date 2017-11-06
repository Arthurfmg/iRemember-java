package br.com.iremember.model.rest;

import java.util.ArrayList;
import java.util.List;
import javax.ws.rs.core.Link;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlRootElement;
import javax.xml.bind.annotation.XmlTransient;
import br.com.iremember.model.Usuario;

@XmlRootElement
public class Usuarios {
	private List<Usuario> usuarios = new ArrayList<>();

	public Usuarios() {
		
	}
	
	public Usuarios(List<Usuario> usuarios) {
		this.usuarios = usuarios;
	}
	
	@XmlTransient
	public List<Usuario> getUsuarios() {
		return usuarios;
	}

	public void setUsuarios(List<Usuario> usuarios) {
		this.usuarios = usuarios;
	}
	
	@XmlElement(name="link")
	public List<Link> getLinks() {
		List<Link> links = new ArrayList<>();
		for (Usuario usuario : getUsuarios()) {
			
			Link link = Link.fromPath("usuarios/{nome}")
					.rel("colecao")
					.title(usuario.getNome())
					.build(usuario.getNome());
			
			
			links.add(link);
		}
		return links;
	}
	
	public void setLinks (List<Link> links) {
		
	}
}
