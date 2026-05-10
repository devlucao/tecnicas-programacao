package biblioteca.modelo;

import simplodb.Persistivel;

import java.time.LocalDate;

public class Usuario implements Persistivel {

    private static final long serialVersionUID = 1L;

    private Long id;
    private String nome;
    private String email;
    private String cpf;
    private LocalDate dataCadastro;

    public Usuario() {}

    public Usuario(String nome, String email, String cpf, LocalDate dataCadastro) {
        this.nome = nome;
        this.email = email;
        this.cpf = cpf;
        this.dataCadastro = dataCadastro;
    }

    @Override public Long getId() { return id; }
    @Override public void setId(Long id) { this.id = id; }

    public String getNome() { return nome; }
    public void setNome(String nome) { this.nome = nome; }

    public String getEmail() { return email; }
    public void setEmail(String email) { this.email = email; }

    public String getCpf() { return cpf; }
    public void setCpf(String cpf) { this.cpf = cpf; }

    public LocalDate getDataCadastro() { return dataCadastro; }
    public void setDataCadastro(LocalDate dataCadastro) { this.dataCadastro = dataCadastro; }

    @Override
    public String toString() {
        return "Usuario{id=" + id + ", nome='" + nome + "', email='" + email + "'}";
    }
}
