package br.org.shameupinc.shameup.model;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.Size;

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
	@Size(min = 10, max = 1000, message = "A descrição deve conter no mínimo 10 e no máximo 1000 caracteres")
	private String descrição;

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

	public String getDescrição() {
		return descrição;
	}

	public void setDescrição(String descrição) {
		this.descrição = descrição;
	}
	
	
}
