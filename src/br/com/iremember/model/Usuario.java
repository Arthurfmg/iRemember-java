package br.com.iremember.model;

import java.util.List;

import javax.persistence.CascadeType;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.NamedQueries;
import javax.persistence.NamedQuery;
import javax.persistence.OneToMany;
import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlElementWrapper;
import javax.xml.bind.annotation.XmlRootElement;

@XmlRootElement
@XmlAccessorType(XmlAccessType.FIELD)

@NamedQueries({
    @NamedQuery(name="Usuario.buscaPorNome",
    			query="SELECT u FROM Usuario u where u.nome = :nome "),
    @NamedQuery(name="Usuario.verificaLogin",
                query="SELECT u FROM Usuario u WHERE u.email = :email AND u.password = :password"),
}) 

@Entity
public class Usuario implements IBean{
	@Id
	@GeneratedValue
	private Long id;
	private String nome;
	private String email;
	private String password;
	
	@OneToMany(mappedBy="usuario", cascade = CascadeType.ALL)
	@XmlElementWrapper(name = "colecoes")
	@XmlElement(name = "colecao")
	private List<Colecao> colecoes;
	
	@OneToMany(mappedBy="usuario", cascade = CascadeType.ALL)
	@XmlElementWrapper(name = "series")
	@XmlElement(name = "serie")
	private List<Serie> series;
	
	public Usuario() {
		super();
	}
	
	public Usuario(String nome, String email, String password) {
		super();
		this.nome = nome;
		this.email = email;
		this.password = password;
	}

	public String toString() {
		return this.nome + " - " + this.email;
	}

	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + ((nome == null) ? 0 : nome.hashCode());
		return result;
	}

	@Override
	public boolean equals(Object obj) {
		if (this == obj)
			return true;
		if (obj == null)
			return false;
		if (getClass() != obj.getClass())
			return false;
		Usuario other = (Usuario) obj;
		if (nome == null) {
			if (other.nome != null)
				return false;
		} else if (!nome.equals(other.nome))
			return false;
		return true;
	}


	public Long getId() {
		return id;
	}
	public void setId(Long id) {
		this.id = id;
	}
	public String getNome() {
		return nome;
	}
	public void setNome(String nome) {
		this.nome = nome;
	}
	public String getEmail() {
		return email;
	}
	public void setEmail(String email) {
		this.email = email;
	}
	public String getPassword() {
		return password;
	}
	public void setPassword(String password) {
		this.password = password;
	}
}