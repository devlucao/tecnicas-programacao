package biblioteca.repositorio;

import biblioteca.modelo.Usuario;
import simplodb.ArquivoMotor;
import simplodb.Repositorio;

import java.util.Optional;

public class UsuarioRepositorio extends Repositorio<Usuario> {

    public UsuarioRepositorio(ArquivoMotor motor) {
        super(motor, "usuarios");
    }

    public Optional<Usuario> buscarPorEmail(String email) {
        return buscarComFiltro(u -> u.getEmail().equalsIgnoreCase(email))
                .stream()
                .findFirst();
    }

    public Optional<Usuario> buscarPorCpf(String cpf) {
        return buscarComFiltro(u -> u.getCpf().equals(cpf))
                .stream()
                .findFirst();
    }
}
