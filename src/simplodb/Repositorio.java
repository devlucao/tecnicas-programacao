package simplodb;

import java.io.IOException;
import java.util.List;
import java.util.Optional;
import java.util.concurrent.atomic.AtomicLong;
import java.util.function.Predicate;
import java.util.stream.Collectors;

/**
 * Repositório genérico do SimploDB — inspirado no Spring Data JPA.
 *
 * Toda classe de repositório do domínio deve estender esta classe
 * informando o tipo da entidade e o nome da coleção (diretório).
 *
 * Exemplo de uso:
 *   public class LivroRepositorio extends Repositorio<Livro> {
 *       public LivroRepositorio(ArquivoMotor motor) {
 *           super(motor, "livros");
 *       }
 *   }
 */
public abstract class Repositorio<T extends Persistivel> {

    private final ArquivoMotor motor;
    private final String nomeEntidade;
    private final AtomicLong proximoId;

    protected Repositorio(ArquivoMotor motor, String nomeEntidade) {
        this.motor = motor;
        this.nomeEntidade = nomeEntidade;
        // Inicializa o contador com o maior ID já persistido
        long maxId = buscarTodos().stream()
                .map(Persistivel::getId)
                .filter(id -> id != null)
                .mapToLong(Long::longValue)
                .max()
                .orElse(0L);
        this.proximoId = new AtomicLong(maxId);
    }

    // -------------------------------------------------------------------------
    // Fornecido — salvar
    // -------------------------------------------------------------------------

    /**
     * Persiste a entidade. Se não tiver ID, atribui o próximo disponível.
     *
     * @param objeto entidade a salvar
     * @return a própria entidade com ID preenchido
     */
    public T salvar(T objeto) {
        try {
            if (objeto.getId() == null) {
                objeto.setId(proximoId.incrementAndGet());
            }
            motor.salvar(nomeEntidade, objeto.getId(), objeto);
            return objeto;
        } catch (IOException e) {
            throw new RuntimeException("Erro ao salvar " + nomeEntidade, e);
        }
    }

    // -------------------------------------------------------------------------
    // Fornecido — buscarPorId
    // -------------------------------------------------------------------------

    /**
     * Busca uma entidade pelo ID.
     *
     * @param id identificador
     * @return Optional com a entidade, ou Optional.empty() se não existir
     */
    public Optional<T> buscarPorId(Long id) {
        try {
            return motor.carregar(nomeEntidade, id);
        } catch (IOException | ClassNotFoundException e) {
            throw new RuntimeException("Erro ao carregar " + nomeEntidade + " id=" + id, e);
        }
    }

    // -------------------------------------------------------------------------
    // Fornecido — buscarTodos
    // -------------------------------------------------------------------------

    /**
     * Retorna todas as entidades persistidas desta coleção.
     *
     * @return lista com todos os registros (pode ser vazia)
     */
    public List<T> buscarTodos() {
        try {
            return motor.carregarTodos(nomeEntidade);
        } catch (IOException | ClassNotFoundException e) {
            throw new RuntimeException("Erro ao carregar todos de " + nomeEntidade, e);
        }
    }

    // -------------------------------------------------------------------------
    // Fornecido — deletar
    // -------------------------------------------------------------------------

    /**
     * Remove a entidade com o ID informado.
     *
     * @param id identificador
     * @return true se foi removida, false se não existia
     */
    public boolean deletar(Long id) {
        try {
            return motor.deletar(nomeEntidade, id);
        } catch (IOException e) {
            throw new RuntimeException("Erro ao deletar " + nomeEntidade + " id=" + id, e);
        }
    }

    // -------------------------------------------------------------------------
    // TODO Exercício 2 — Programação Funcional (Módulo 2)
    // -------------------------------------------------------------------------

    /**
     * Retorna todos os registros que satisfaçam o critério informado.
     *
     * Este é o método central de consulta do SimploDB: ao receber um Predicate<T>,
     * ele permite filtrar qualquer coleção com qualquer critério — sem precisar
     * criar um método novo para cada tipo de busca.
     *
     * Exemplo de uso:
     *   List<Livro> romances = livroRepo.buscarComFiltro(l -> l.getGenero().equals("Romance"));
     *   List<Usuario> anas   = usuarioRepo.buscarComFiltro(u -> u.getNome().startsWith("Ana"));
     *
     * Passos:
     *   1. Chame buscarTodos() para obter todos os registros
     *   2. Abra uma stream com .stream()
     *   3. Aplique .filter(filtro) usando o Predicate recebido
     *   4. Colete o resultado com .collect(Collectors.toList())
     *
     * @param filtro Predicate<T> — expressão lambda que retorna true para os elementos desejados
     * @return lista dos elementos que passam no filtro
     */
    public List<T> buscarComFiltro(Predicate<T> filtro) {
        // TODO Exercício 2
        throw new UnsupportedOperationException("Não implementado — veja TODO Exercício 2");
    }
}
