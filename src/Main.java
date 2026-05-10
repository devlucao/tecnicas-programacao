import biblioteca.modelo.Autor;
import biblioteca.modelo.Emprestimo;
import biblioteca.modelo.Livro;
import biblioteca.modelo.Usuario;
import biblioteca.repositorio.AutorRepositorio;
import biblioteca.repositorio.EmprestimoRepositorio;
import biblioteca.repositorio.LivroRepositorio;
import biblioteca.repositorio.UsuarioRepositorio;
import biblioteca.servico.BibliotecaServico;
import biblioteca.servico.RelatorioServico;
import simplodb.ArquivoMotor;

import java.nio.file.Paths;
import java.time.LocalDate;

/**
 * Ponto de entrada da Biblioteca Digital.
 *
 * Execute esta classe para verificar se todos os TODOs foram implementados corretamente.
 * A saída esperada está descrita no README.md.
 *
 * Para limpar o banco de dados: apague a pasta db/ e reexecute.
 */
public class Main {

    public static void main(String[] args) {
        ArquivoMotor motor = new ArquivoMotor(Paths.get("db"));

        AutorRepositorio autorRepo         = new AutorRepositorio(motor);
        LivroRepositorio livroRepo         = new LivroRepositorio(motor);
        UsuarioRepositorio usuarioRepo     = new UsuarioRepositorio(motor);
        EmprestimoRepositorio empRepo      = new EmprestimoRepositorio(motor);

        BibliotecaServico biblioteca       = new BibliotecaServico(livroRepo, usuarioRepo, empRepo);
        RelatorioServico relatorio         = new RelatorioServico(livroRepo, autorRepo, empRepo);

        // ---- Cadastro inicial ----
        System.out.println("=== Cadastrando autores ===");
        Autor machado  = autorRepo.salvar(new Autor("Machado de Assis", "Brasileiro", LocalDate.of(1839, 6, 21)));
        Autor clarice  = autorRepo.salvar(new Autor("Clarice Lispector", "Brasileira", LocalDate.of(1920, 12, 10)));
        Autor drummond = autorRepo.salvar(new Autor("Carlos Drummond de Andrade", "Brasileiro", LocalDate.of(1902, 10, 31)));
        System.out.println(machado);
        System.out.println(clarice);
        System.out.println(drummond);

        System.out.println("\n=== Cadastrando livros ===");
        Livro domCasmurro  = livroRepo.salvar(new Livro("978-85-01-00001-1", "Dom Casmurro", "Romance", 1899, machado.getId()));
        Livro horaEstrela  = livroRepo.salvar(new Livro("978-85-01-00002-2", "A Hora da Estrela", "Romance", 1977, clarice.getId()));
        Livro memorias     = livroRepo.salvar(new Livro("978-85-01-00003-3", "Memórias Póstumas de Brás Cubas", "Romance", 1881, machado.getId()));
        Livro sentimento   = livroRepo.salvar(new Livro("978-85-01-00004-4", "Sentimento do Mundo", "Poesia", 1940, drummond.getId()));
        Livro perto        = livroRepo.salvar(new Livro("978-85-01-00005-5", "Perto do Coração Selvagem", "Romance", 1943, clarice.getId()));
        System.out.println(domCasmurro);
        System.out.println(horaEstrela);
        System.out.println(memorias);

        System.out.println("\n=== Cadastrando usuários ===");
        Usuario ana    = usuarioRepo.salvar(new Usuario("Ana Silva", "ana@email.com", "111.222.333-44", LocalDate.now()));
        Usuario bruno  = usuarioRepo.salvar(new Usuario("Bruno Costa", "bruno@email.com", "555.666.777-88", LocalDate.now()));
        System.out.println(ana);
        System.out.println(bruno);

        // ---- Empréstimos ----
        System.out.println("\n=== Registrando empréstimos ===");
        Emprestimo e1 = biblioteca.registrarEmprestimo(ana.getId(), domCasmurro.getId());
        Emprestimo e2 = biblioteca.registrarEmprestimo(ana.getId(), horaEstrela.getId());
        Emprestimo e3 = biblioteca.registrarEmprestimo(bruno.getId(), memorias.getId());
        System.out.println(e1);
        System.out.println(e2);
        System.out.println(e3);

        // ---- Regra: livro já emprestado ----
        System.out.println("\n=== Tentando emprestar livro já emprestado ===");
        try {
            biblioteca.registrarEmprestimo(bruno.getId(), domCasmurro.getId());
            System.out.println("ERRO: deveria ter lançado exceção!");
        } catch (IllegalStateException ex) {
            System.out.println("OK — exceção esperada: " + ex.getMessage());
        }

        // ---- Streams ----
        System.out.println("\n=== Livros de Romance (ordenados por ano, mais recente primeiro) ===");
        livroRepo.buscarPorGeneroCoordenado("Romance").forEach(System.out::println);

        System.out.println("\n=== Livros agrupados por gênero ===");
        livroRepo.agruparPorGenero().forEach((genero, livros) ->
                System.out.println(genero + ": " + livros.stream().map(Livro::getTitulo).toList()));

        // ---- Devolução ----
        System.out.println("\n=== Devolvendo empréstimo #" + e1.getId() + " ===");
        Emprestimo devolvido = biblioteca.devolverLivro(e1.getId());
        System.out.println(devolvido);

        // ---- Relatório completo (bônus) ----
        System.out.println("\n=== Relatório completo (bônus — Exercício 7) ===");
        try {
            RelatorioServico.RelatorioCompleto rel = relatorio.gerarRelatorioCompleto();
            System.out.println("Top 5 mais emprestados: " +
                    rel.top5MaisEmprestados().stream().map(Livro::getTitulo).toList());
            System.out.println("Multas pendentes por usuário: " + rel.multasPendentes());
            System.out.println("Gêneros disponíveis: " + rel.livrosPorGenero().keySet());
        } catch (UnsupportedOperationException e) {
            System.out.println("(bônus ainda não implementado)");
        }

        System.out.println("\n=== Fim. Dados persistidos em db/ ===");
    }
}
