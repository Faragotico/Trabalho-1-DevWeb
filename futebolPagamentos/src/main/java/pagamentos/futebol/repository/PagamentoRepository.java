package pagamentos.futebol.repository;

import java.math.BigDecimal;
import java.util.List;
import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import pagamentos.futebol.model.Jogador;
import pagamentos.futebol.model.Pagamento;
/*
    Interface Repository para Pagamento
*/
@Repository
public interface PagamentoRepository extends JpaRepository<Pagamento, Long>{
    
    @Override
    Optional<Pagamento> findById(Long id);
    List<Pagamento> findByAno(Short ano);
    List<Pagamento> findByMes(Byte mes);
    List<Pagamento> findByValor(BigDecimal valor);
    List<Pagamento> findByJogador(Jogador jogador);
}