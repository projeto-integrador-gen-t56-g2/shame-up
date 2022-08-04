package br.org.shameupinc.shameup.model;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;

import javax.persistence.*;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.Size;
import java.util.List;

@Entity
@Table(name = "tb_categorias")
public class Categoria {
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY) 
	private Long id;
	
	@NotBlank(message = "O nome é Obrigatório!") 
	@Size(min = 3, max = 255, message = "O nome deve conter no mínimo 03 e no maximo 255 caracteres")
	private String nome;
	
	@NotBlank(message = "A descrição é Obrigatória!")
	@Size(min = 3, max = 1000, message = "A descrição deve conter no mínimo 10 e no máximo 1000 caracteres")
	private String descricao;

	@OneToMany(mappedBy = "categoria", cascade = CascadeType.REMOVE)
	@JsonIgnoreProperties("categoria")
	private List<Postagem> postagens;

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

	public void setDescrição(String descricao) {
		this.descricao = descricao;
	}

	public List<Postagem> getPostagens() {
		return postagens;
	}

	public void setPostagens(List<Postagem> postagens) {
		this.postagens = postagens;
	}
}
