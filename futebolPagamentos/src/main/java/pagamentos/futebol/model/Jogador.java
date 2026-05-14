package pagamentos.futebol.model;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

import com.fasterxml.jackson.annotation.JsonManagedReference;

import jakarta.persistence.CascadeType;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.OneToMany;
import jakarta.persistence.Table;
import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Past;
import jakarta.validation.constraints.Size;

/*
    Entidade de jogadores
*/
@Entity
@Table(name = "jogador")
public class Jogador {

    // Atributos
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "codjogador")
    private Long codjogador;

    @NotBlank(message = "Nome é obrigatório")
    @Size(max = 60, message = "Nome deve ter no máximo 60 caracteres")
    @Column(name = "nome", length = 60, nullable = false)
    private String nome;

    @NotBlank(message = "Email é obrigatório")
    @Email(message = "Email deve ser válido")
    @Size(max = 60, message = "Email deve ter no máximo 60 caracteres")
    @Column(name = "email", length = 60, nullable = false)
    private String email;

    @Past(message = "Data de nascimento deve ser no passado")
    @Column(name = "datanasc")
    private LocalDate dataNasc;

    @OneToMany(mappedBy = "jogador", cascade = CascadeType.ALL, orphanRemoval = true)
    @JsonManagedReference
    private List<Pagamento> pagamentos = new ArrayList<>();

    // Construtores
    public Jogador() {
    }

    public Jogador(String nome, String email, LocalDate dataNasc) {
        this.nome = nome;
        this.email = email;
        this.dataNasc = dataNasc;
    }

    // Getters e Setters
    public Long getCodJogador() {
        return codjogador;
    }

    public void setCodJogador(Long codjogador) {
        this.codjogador = codjogador;
    }

    public String getNome() {
        return nome;
    }

    public void setNome(String nome) {
        this.nome = nome;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public LocalDate getDataNasc() {
        return dataNasc;
    }

    public void setDataNasc(LocalDate dataNasc) {
        this.dataNasc = dataNasc;
    }

    public List<Pagamento> getPagamentos() {
        return pagamentos;
    }

    public void setPagamentos(List<Pagamento> pagamentos) {
        this.pagamentos = pagamentos;
    }

    // Métodos auxiliares para gerenciar relacionamento bidirecional
    public void adicionarPagamento(Pagamento pagamento) {
        pagamentos.add(pagamento);
        pagamento.setJogador(this);
    }

    public void removerPagamento(Pagamento pagamento) {
        pagamentos.remove(pagamento);
        pagamento.setJogador(null);
    }
}