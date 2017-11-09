package br.com.iremember.model;

import java.util.List;

import javax.persistence.CascadeType;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.ManyToOne;
import javax.persistence.NamedQueries;
import javax.persistence.NamedQuery;
import javax.persistence.OneToMany;
import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlElementWrapper;
import javax.xml.bind.annotation.XmlRootElement;
import javax.xml.bind.annotation.XmlTransient;

import br.com.iremember.model.IBean;

@XmlRootElement
@XmlAccessorType(XmlAccessType.FIELD)

@NamedQueries({
    @NamedQuery(name="Colecao.buscaPorNome",
    			query="SELECT c FROM Colecao c where c.nome = :nome "),
    @NamedQuery(name="Colecao.buscaUltimasColecoes",
				query="SELECT c FROM Colecao c "),
    @NamedQuery(name="Colecao.buscaPorUsuario",
                query="SELECT c FROM Colecao c WHERE c.usuario = :usuario Order by c.nome"),
}) 

@Entity
public class Colecao implements IBean {

	@Id
	@GeneratedValue
	private Long id;
	private String nome;
	private String descricao;
	
	@OneToMany(mappedBy="colecao", cascade = CascadeType.ALL)
	@XmlElementWrapper(name = "unidades")
	@XmlElement(name = "unidade")
	private List<Unidade> unidades;
	
	@ManyToOne
	@XmlTransient
	private Usuario usuario;

	public Colecao() {
		super();
	}

	public Colecao(String nome, String descricao, List<Unidade> unidades) {
		super();
		this.nome = nome;
		this.descricao = descricao;
		this.unidades = unidades;
	}
	public Colecao(String nome, String descricao) {
		super();
		this.nome = nome;
		this.descricao = descricao;
	}
	public Colecao(String nome, String descricao, Usuario usuario) {
		super();
		this.nome = nome;
		this.descricao = descricao;
		this.usuario = usuario;
	}

	public String toString() {
		return this.nome + " - " + this.descricao;
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
		Colecao other = (Colecao) obj;
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

	public String getDescricao() {
		return descricao;
	}

	public void setDescricao(String descricao) {
		this.descricao = descricao;
	}

	public List<Unidade> getUnidades() {
		return unidades;
	}

	public void setUnidades(List<Unidade> unidades) {
		this.unidades = unidades;
	}

	public Usuario getUsuario() {
		return usuario;
	}

	public void setUsuario(Usuario usuario) {
		this.usuario = usuario;
	}
}