package biblioteca.repositorio;

import biblioteca.modelo.Emprestimo;
import simplodb.ArquivoMotor;
import simplodb.Repositorio;

import java.util.List;

public class EmprestimoRepositorio extends Repositorio<Emprestimo> {

    public EmprestimoRepositorio(ArquivoMotor motor) {
        super(motor, "emprestimos");
    }

    /**
     * Retorna todos os empréstimos de um usuário.
     */
    public List<Emprestimo> buscarPorUsuario(Long usuarioId) {
        return buscarComFiltro(e -> e.getUsuarioId().equals(usuarioId));
    }

    /**
     * Retorna todos os empréstimos ainda não devolvidos.
     */
    public List<Emprestimo> buscarAbertos() {
        return buscarComFiltro(e -> !e.isDevolvido());
    }

    /**
     * Retorna todos os empréstimos ativos de um usuário (não devolvidos).
     */
    public List<Emprestimo> buscarAbertosDoUsuario(Long usuarioId) {
        return buscarComFiltro(e -> e.getUsuarioId().equals(usuarioId) && !e.isDevolvido());
    }
}
