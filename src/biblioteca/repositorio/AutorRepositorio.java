package biblioteca.repositorio;

import biblioteca.modelo.Autor;
import simplodb.ArquivoMotor;
import simplodb.Repositorio;

import java.util.Optional;

/**
 * Repositório de Autores — exemplo completo de como estender Repositorio<T>.
 *
 * Observe como buscarComFiltro() é usado com um lambda para criar
 * consultas customizadas sem precisar de SQL ou métodos adicionais no framework.
 */
public class AutorRepositorio extends Repositorio<Autor> {

    public AutorRepositorio(ArquivoMotor motor) {
        super(motor, "autores");
    }

    /**
     * Busca um autor pelo nome (ignora maiúsculas/minúsculas).
     */
    public Optional<Autor> buscarPorNome(String nome) {
        return buscarComFiltro(a -> a.getNome().equalsIgnoreCase(nome))
                .stream()
                .findFirst();
    }
}
