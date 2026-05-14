package pagamentos.futebol.controller;

import java.time.LocalDate;
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
import pagamentos.futebol.repository.JogadorRepository;

/*
    Controlador dos jogadores
*/
@RestController
@RequestMapping("/jogadores")
public class JogadorController {

    @Autowired
    private JogadorRepository jogadorRepository;

    // Listar jogadores por nome, e-mail, data de nascimento ou todos eles
    @GetMapping("/")
    public ResponseEntity<List<Jogador>> listarTodos(@RequestParam(required = false) String nome, @RequestParam(required = false) String email,
                                                        @RequestParam(required = false) LocalDate dataNascimento){
        try{
            List<Jogador> jogadores = new ArrayList<>();
            if(nome == null && email == null && dataNascimento == null)
                    jogadorRepository.findAll().forEach(jogadores::add);
            else{
                if(nome != null)
                    jogadorRepository.findByNome(nome).forEach(jogadores::add);
                if(email != null)
                    jogadorRepository.findByEmail(email).forEach(jogadores::add);
                if(dataNascimento != null)
                    jogadorRepository.findByDataNasc(dataNascimento).forEach(jogadores::add);
            }

            if(jogadores.isEmpty())
                return new ResponseEntity<>(HttpStatus.NO_CONTENT);
            else return new ResponseEntity<>(jogadores, HttpStatus.OK);
        }
        catch(Exception e){
            return new ResponseEntity<>(HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    // Buscar por id
    @GetMapping("/{id}")
    public ResponseEntity<Jogador> buscarPorId(@PathVariable Long id){
        try{
            Optional<Jogador> data = jogadorRepository.findById(id);

            if(data.isPresent()){
                Jogador jogador = data.get();
                return new ResponseEntity<>(jogador, HttpStatus.OK);
            }
            else return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }catch(Exception e){
            return new ResponseEntity<>(HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    // Criar novo jogador
    @PostMapping("/")
    public ResponseEntity<Jogador> criarJogador(@Valid @RequestBody Jogador jogador){
        try{
            Jogador jogador_novo = jogadorRepository.save(new Jogador(jogador.getNome(), jogador.getEmail(), jogador.getDataNasc()));
            return new ResponseEntity<>(jogador_novo, HttpStatus.OK);
        }catch(Exception e){
            return new ResponseEntity<>(HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    // Atualizar jogador por id
    @PutMapping("/{id}")
    public ResponseEntity<Jogador> atualizarJogador(@PathVariable Long id, @Valid @RequestBody Jogador jogador){
        Optional<Jogador> data = jogadorRepository.findById(id);
        if(data.isPresent()){
            Jogador jogador_novo = data.get();
            jogador_novo.setNome(jogador.getNome());
            jogador_novo.setEmail(jogador.getEmail());
            jogador_novo.setDataNasc(jogador.getDataNasc());
            return new ResponseEntity<>(jogadorRepository.save(jogador_novo), HttpStatus.OK);
        }
        else return new ResponseEntity<>(HttpStatus.NOT_FOUND);
    }

    // Remover jogador por id
    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deletar(@PathVariable Long id){
        try{
            jogadorRepository.deleteById(id);
            return new ResponseEntity<>(HttpStatus.NO_CONTENT);
        }catch(Exception e){
            return new ResponseEntity<>(HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    // Remover todos os jogadores
    @DeleteMapping("/")
    public ResponseEntity<Void> deletar(){
        try{
            jogadorRepository.deleteAll();
            return new ResponseEntity<>(HttpStatus.NO_CONTENT);
        }catch(Exception e){
            return new ResponseEntity<>(HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }
}