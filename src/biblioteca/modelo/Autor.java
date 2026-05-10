package biblioteca.modelo;

import simplodb.Persistivel;

import java.time.LocalDate;

public class Autor implements Persistivel {

    private static final long serialVersionUID = 1L;

    private Long id;
    private String nome;
    private String nacionalidade;
    private LocalDate dataNascimento;

    public Autor() {}

    public Autor(String nome, String nacionalidade, LocalDate dataNascimento) {
        this.nome = nome;
        this.nacionalidade = nacionalidade;
        this.dataNascimento = dataNascimento;
    }

    @Override public Long getId() { return id; }
    @Override public void setId(Long id) { this.id = id; }

    public String getNome() { return nome; }
    public void setNome(String nome) { this.nome = nome; }

    public String getNacionalidade() { return nacionalidade; }
    public void setNacionalidade(String nacionalidade) { this.nacionalidade = nacionalidade; }

    public LocalDate getDataNascimento() { return dataNascimento; }
    public void setDataNascimento(LocalDate dataNascimento) { this.dataNascimento = dataNascimento; }

    @Override
    public String toString() {
        return "Autor{id=" + id + ", nome='" + nome + "', nacionalidade='" + nacionalidade + "'}";
    }
}
