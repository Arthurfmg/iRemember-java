package br.com.iremember.model;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.ManyToOne;
import javax.persistence.NamedQueries;
import javax.persistence.NamedQuery;
import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlRootElement;
import javax.xml.bind.annotation.XmlTransient;

@XmlRootElement
@XmlAccessorType(XmlAccessType.FIELD)

@NamedQueries({
    @NamedQuery(name="Unidade.buscaPorNome",
    			query="SELECT u FROM Unidade u where u.nome = :nome "),
    @NamedQuery(name="Unidade.buscaPorColecao",
                query="SELECT u FROM Unidade u WHERE u.colecao = :colecao"),
}) 

@Entity
public class Unidade implements IBean {
	@Id
	@GeneratedValue
	private Long id;
	private String nome;
	private String descricao;
	private Integer numero;
	
	@ManyToOne
	@XmlTransient
	private Colecao colecao;
	
	public Unidade() {
		super();
	}

	public Unidade(Long id, String nome, String descricao, Integer numero, Colecao colecao) {
		super();
		this.id = id;
		this.nome = nome;
		this.descricao = descricao;
		this.numero = numero;
		this.colecao = colecao;
	}
	public Unidade(String nome, String descricao, Integer numero) {
		super();
		this.nome = nome;
		this.descricao = descricao;
		this.numero = numero;
	}
	public Unidade(String nome, String descricao, Integer numero, Colecao colecao) {
		super();
		this.nome = nome;
		this.descricao = descricao;
		this.numero = numero;
		this.colecao = colecao;
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
		Unidade other = (Unidade) obj;
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

	public Integer getNumero() {
		return numero;
	}

	public void setNumero(Integer numero) {
		this.numero = numero;
	}

	public Colecao getColecao() {
		return colecao;
	}

	public void setColecao(Colecao colecao) {
		this.colecao = colecao;
	}
}
