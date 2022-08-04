package br.org.shameupinc.shameup.repository;

import br.org.shameupinc.shameup.model.Usuario;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

@Repository
public interface UsuarioRepository extends JpaRepository<Usuario, Long> {

	public List<Usuario> findAllByNomeContainingIgnoreCase(@Param("nome") String nome);	

	     
	}


