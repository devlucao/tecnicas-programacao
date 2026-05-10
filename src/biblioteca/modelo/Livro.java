package biblioteca.modelo;

import simplodb.Persistivel;

public class Livro implements Persistivel {

    private static final long serialVersionUID = 1L;

    private Long id;
    private String isbn;
    private String titulo;
    private String genero;
    private int anoPublicacao;
    private Long autorId;

    public Livro() {}

    public Livro(String isbn, String titulo, String genero, int anoPublicacao, Long autorId) {
        this.isbn = isbn;
        this.titulo = titulo;
        this.genero = genero;
        this.anoPublicacao = anoPublicacao;
        this.autorId = autorId;
    }

    @Override public Long getId() { return id; }
    @Override public void setId(Long id) { this.id = id; }

    public String getIsbn() { return isbn; }
    public void setIsbn(String isbn) { this.isbn = isbn; }

    public String getTitulo() { return titulo; }
    public void setTitulo(String titulo) { this.titulo = titulo; }

    public String getGenero() { return genero; }
    public void setGenero(String genero) { this.genero = genero; }

    public int getAnoPublicacao() { return anoPublicacao; }
    public void setAnoPublicacao(int anoPublicacao) { this.anoPublicacao = anoPublicacao; }

    public Long getAutorId() { return autorId; }
    public void setAutorId(Long autorId) { this.autorId = autorId; }

    @Override
    public String toString() {
        return "Livro{id=" + id + ", isbn='" + isbn + "', titulo='" + titulo +
               "', genero='" + genero + "', ano=" + anoPublicacao + "}";
    }
}
