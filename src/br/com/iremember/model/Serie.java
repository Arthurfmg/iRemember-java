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
    @NamedQuery(name="Serie.buscaPorNome",
    			query="SELECT s FROM Serie s where s.nome = :nome "),
    @NamedQuery(name="Serie.buscaPorUsuario",
                query="SELECT s FROM Serie s WHERE s.usuario = :usuario_id"),
})

@Entity
public class Serie implements IBean {
	@Id
	@GeneratedValue
	private Long id;
	private String nome;
	private String descricao;
	private Integer numeroCapitulos;
	private Integer numeroTemporadas;
	private Integer numeroCapituloParou;
	private Integer numeroTemporadaParou;
	
	@ManyToOne
	@XmlTransient
	private Usuario usuario;
	
	public Serie() {
		super();
	}
	
	public Serie(Long id, String nome, String descricao, Integer numeroCapitulos, Integer numeroTemporadas,
			Integer numeroCapituloParou, Integer numeroTemporadaParou) {
		super();
		this.id = id;
		this.nome = nome;
		this.descricao = descricao;
		this.numeroCapitulos = numeroCapitulos;
		this.numeroTemporadas = numeroTemporadas;
		this.numeroCapituloParou = numeroCapituloParou;
		this.numeroTemporadaParou = numeroTemporadaParou;
	}
	public Serie(String nome, String descricao, Integer numeroCapitulos, Integer numeroTemporadas,
			Integer numeroCapituloParou, Integer numeroTemporadaParou) {
		super();
		this.nome = nome;
		this.descricao = descricao;
		this.numeroCapitulos = numeroCapitulos;
		this.numeroTemporadas = numeroTemporadas;
		this.numeroCapituloParou = numeroCapituloParou;
		this.numeroTemporadaParou = numeroTemporadaParou;
	}
	public Serie(String nome, String descricao, Integer numeroCapitulos, Integer numeroTemporadas,
			Integer numeroCapituloParou, Integer numeroTemporadaParou, Usuario usuario) {
		super();
		this.nome = nome;
		this.descricao = descricao;
		this.numeroCapitulos = numeroCapitulos;
		this.numeroTemporadas = numeroTemporadas;
		this.numeroCapituloParou = numeroCapituloParou;
		this.numeroTemporadaParou = numeroTemporadaParou;
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
		Serie other = (Serie) obj;
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

	public Integer getNumeroCapitulos() {
		return numeroCapitulos;
	}

	public void setNumeroCapitulos(Integer numeroCapitulos) {
		this.numeroCapitulos = numeroCapitulos;
	}

	public Integer getNumeroTemporadas() {
		return numeroTemporadas;
	}

	public void setNumeroTemporadas(Integer numeroTemporadas) {
		this.numeroTemporadas = numeroTemporadas;
	}

	public Integer getNumeroCapituloParou() {
		return numeroCapituloParou;
	}

	public void setNumeroCapituloParou(Integer numeroCapituloParou) {
		this.numeroCapituloParou = numeroCapituloParou;
	}

	public Integer getNumeroTemporadaParou() {
		return numeroTemporadaParou;
	}

	public void setNumeroTemporadaParou(Integer numeroTemporadaParou) {
		this.numeroTemporadaParou = numeroTemporadaParou;
	}
	
	public Usuario getUsuario() {
		return usuario;
	}

	public void setUsuario(Usuario usuario) {
		this.usuario = usuario;
	}
}
