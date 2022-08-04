package br.org.shameupinc.shameup.repository;

import br.org.shameupinc.shameup.model.Postagem;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface PostagemRepository extends JpaRepository<Postagem, Long> {
    
}
