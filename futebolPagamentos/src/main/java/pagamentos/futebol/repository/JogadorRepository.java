package pagamentos.futebol.repository;

import java.time.LocalDate;
import java.util.List;
import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import pagamentos.futebol.model.Jogador;

/*
    Interface Repository para Jogador
*/
@Repository
public interface JogadorRepository extends JpaRepository<Jogador, Long>{
    @Override
    Optional<Jogador> findById(Long id);
    List<Jogador> findByNome(String nome);
    List<Jogador> findByEmail(String email);
    List<Jogador> findByDataNasc(LocalDate dataNasc);
}