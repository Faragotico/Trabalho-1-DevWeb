package pagamentos.futebol.controller;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import jakarta.validation.Valid;
import pagamentos.futebol.model.Jogador;
import pagamentos.futebol.model.Pagamento;
import pagamentos.futebol.repository.JogadorRepository;
import pagamentos.futebol.repository.PagamentoRepository;

/*
    Controlador dos pagamentos
*/
@RestController
@RequestMapping("/pagamentos")
public class PagamentoController {

    @Autowired
    private PagamentoRepository pagamentoRepository;
    @Autowired
    private JogadorRepository jogadorRepository;

    // Listar pagamentos por ano, mes, valor ou todos eles
    @GetMapping
    public ResponseEntity<List<Pagamento>> listarTodos(@RequestParam(required = false) Short ano, @RequestParam(required = false) Byte mes,
                                                        @RequestParam(required = false) BigDecimal valor){

         try{
            List<Pagamento> pagamentos = new ArrayList<>();
            if(ano == null && mes == null && valor == null)
                    pagamentoRepository.findAll().forEach(pagamentos::add);
            else{
                if(ano != null)
                    pagamentoRepository.findByAno(ano).forEach(pagamentos::add);
                if(mes != null)
                    pagamentoRepository.findByMes(mes).forEach(pagamentos::add);
                if(valor != null)
                    pagamentoRepository.findByValor(valor).forEach(pagamentos::add);
            }

            if(pagamentos.isEmpty())
                return new ResponseEntity<>(HttpStatus.NO_CONTENT);
            else return new ResponseEntity<>(pagamentos, HttpStatus.OK);
        }
        catch(Exception e){
            return new ResponseEntity<>(HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    // Buscar pagamento por ID
    @GetMapping("/{id}")
    public ResponseEntity<Pagamento> buscarPorId(@PathVariable long id){
         try{
            Optional<Pagamento> data = pagamentoRepository.findById(id);

            if(data.isPresent()){
                Pagamento pagamento = data.get();
                return new ResponseEntity<>(pagamento, HttpStatus.OK);
            }
            else return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }catch(Exception e){
            return new ResponseEntity<>(HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    // Listar pagamentos de um jogador pelo seu id
    @GetMapping("/jogador/{id}")
    public ResponseEntity<List<Pagamento>> listarPorJogador(@PathVariable Long id){
        try{
            Optional<Jogador> data = jogadorRepository.findById(id);
            if(data.isPresent()){
                Jogador jogador = data.get();
                List<Pagamento> pagamentos = new ArrayList<>();
                pagamentoRepository.findByJogador(jogador).forEach(pagamentos::add);
                
                if(pagamentos.isEmpty())
                    return new ResponseEntity<>(HttpStatus.NO_CONTENT);
                else return new ResponseEntity<>(pagamentos, HttpStatus.OK);
            }
            else return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }catch(Exception e){
            return new ResponseEntity<>(HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    @GetMapping("/TotalPagoJogador/{id}")
    public ResponseEntity<BigDecimal> valorTotalPagoJogador(@PathVariable Long id){
        try{
            Optional<Jogador> data = jogadorRepository.findById(id);
            if(data.isPresent()){
                Jogador jogador = data.get();
                List<Pagamento> pagamentos = new ArrayList<>();
                pagamentoRepository.findByJogador(jogador).forEach(pagamentos::add);
                
                if(pagamentos.isEmpty())
                    return new ResponseEntity<>(HttpStatus.NO_CONTENT);
                else{
                    BigDecimal total = BigDecimal.ZERO;
                    for(Pagamento p : pagamentos)
                        total = total.add(p.getValor());
                    return new ResponseEntity<>(total, HttpStatus.OK);
                }
            }
            else return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }catch(Exception e){
            return new ResponseEntity<>(HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }
    // Criar novo pagamento
    @PostMapping
    public ResponseEntity<Pagamento> criarPagamento(@Valid @RequestBody Pagamento pagamento, @RequestParam Long codJogador){
        try{
            Optional<Jogador> data = jogadorRepository.findById(codJogador);
            if(data.isPresent()){
                Jogador jogador = data.get();
                Pagamento pagamento_novo = pagamentoRepository.save(new Pagamento(pagamento.getAno(), pagamento.getMes(), pagamento.getValor(), jogador));
                return new ResponseEntity<>(pagamento_novo, HttpStatus.OK);
            }
            else return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }catch(Exception e){
            return new ResponseEntity<>(HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    // Atualizar pagamento por id
    @PutMapping("/{id}")
    public ResponseEntity<Pagamento> atualizarPagamento(@PathVariable Long id, @Valid @RequestBody Pagamento pagamento){
        Optional<Pagamento> data = pagamentoRepository.findById(id);
        if(data.isPresent()){
            Pagamento pagamento_novo = data.get();
            pagamento_novo.setAno(pagamento.getAno());
            pagamento_novo.setMes(pagamento.getMes());
            pagamento_novo.setValor(pagamento.getValor());
            pagamento_novo.setJogador(pagamento.getJogador());
            return new ResponseEntity<>(pagamentoRepository.save(pagamento_novo), HttpStatus.OK);
        }
        else return new ResponseEntity<>(HttpStatus.NOT_FOUND);
    }

    // Remover pagamento por id
    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deletar(@PathVariable Long id){
        try{
            pagamentoRepository.deleteById(id);
            return new ResponseEntity<>(HttpStatus.NO_CONTENT);
        }catch(Exception e){
            return new ResponseEntity<>(HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    // Remover todos os pagamentos
    @DeleteMapping("/")
    public ResponseEntity<Void> deletar(){
        try{
            pagamentoRepository.deleteAll();
            return new ResponseEntity<>(HttpStatus.NO_CONTENT);
        }catch(Exception e){
            return new ResponseEntity<>(HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }
}