import controller.AutorController;
import controller.LivroController;
import controller.UsuarioController;
import controller.EmprestimoController;
import exception.EntidadeNaoEncontradaException;
import model.Autor;
import model.Livro;
import model.Usuario;
import model.Emprestimo;
import view.MainView;

public class Main {

    public static void main(String[] args) {
        MainView view = new MainView();
        LivroController livroController = new LivroController();
        AutorController autorController = new AutorController();
        UsuarioController usuarioController = new UsuarioController();
        EmprestimoController emprestimoController = new EmprestimoController();

        int opcaoPrincipal;

        do {
            opcaoPrincipal = view.exibirMenuPrincipal();

            switch (opcaoPrincipal) {
                case 1:
                    processarMenuLivros(view, livroController, autorController);
                    break;
                case 2:
                    processarMenuAutores(view, autorController);
                    break;
                case 3:
                    processarMenuUsuarios(view, usuarioController);
                    break;
                case 4:
                    processarMenuEmprestimos(view, emprestimoController, usuarioController, livroController);
                    break;
                case 0:
                    System.out.println("\nSalvando dados nos arquivos (.dat)...");
                    livroController.salvarDados();
                    autorController.salvarDados();
                    usuarioController.salvarDados();
                    emprestimoController.salvarDados();
                    System.out.println("Logs de atividade salvos em 'sistema_biblioteca.log'.");
                    System.out.println("Sistema encerrado com sucesso.");
                    break;
                default:
                    System.out.println("\nOpção inválida! Digite um número do menu.");
            }
        } while (opcaoPrincipal != 0);
    }

    private static void processarMenuEmprestimos(MainView view, EmprestimoController ec, UsuarioController uc, LivroController lc) {
        int opcao = view.exibirSubMenu("Empréstimos");
        try {
            switch (opcao) {
                case 1:
                    if (uc.listarTodos().isEmpty() || lc.listarTodos().isEmpty()) {
                        System.out.println("\n[AVISO] É necessário ter pelo menos um Usuário e um Livro cadastrados para efetuar um empréstimo.");
                        break;
                    }
                    System.out.println("\n--- Usuários Cadastrados ---");
                    for (Usuario u : uc.listarTodos()) System.out.println("ID: " + u.getId() + " | Nome: " + u.getNome());
                    int usuarioId = Integer.parseInt(view.lerEntrada("ID do Usuário"));
                    Usuario usuario = uc.buscarPorId(usuarioId);

                    System.out.println("\n--- Livros Cadastrados ---");
                    for (Livro l : lc.listarTodos()) System.out.println("ID: " + l.getId() + " | Título: " + l.getTitulo());
                    int livroId = Integer.parseInt(view.lerEntrada("ID do Livro"));
                    Livro livro = lc.buscarPorId(livroId);

                    String data = view.lerEntrada("Data do Empréstimo (DD/MM/AAAA)");

                    ec.registrarEmprestimo(usuario, livro, data);
                    System.out.println("Empréstimo registrado!");
                    break;
                case 2:
                    System.out.println("\n--- Histórico de Empréstimos ---");
                    if (ec.listarTodos().isEmpty()) System.out.println("Nenhum empréstimo registrado.");
                    for (Emprestimo emp : ec.listarTodos()) System.out.println(emp);
                    break;
                case 3:
                    if (ec.listarTodos().isEmpty()) {
                        System.out.println("Não há empréstimos registrados.");
                        break;
                    }
                    System.out.println("\n--- Empréstimos Ativos ---");
                    for (Emprestimo emp : ec.listarTodos()) {
                        if (!emp.isDevolvido()) System.out.println(emp);
                    }
                    int empId = Integer.parseInt(view.lerEntrada("ID do Empréstimo para devolução"));
                    ec.devolverLivro(empId);
                    System.out.println("Devolução registrada!");
                    break;
            }
        } catch (EntidadeNaoEncontradaException | NumberFormatException e) {
            System.out.println("\n[ERRO]: " + e.getMessage());
        }
    }

    private static void processarMenuLivros(MainView view, LivroController lc, AutorController ac) {
        int opcao = view.exibirSubMenu("Livros");
        try {
            switch (opcao) {
                case 1:
                    if (ac.listarTodos().isEmpty()) {
                        System.out.println("\n[AVISO] Não é possível cadastrar um livro porque não há nenhum autor.");
                        break;
                    }
                    String titulo = view.lerEntrada("Título do Livro");
                    System.out.println("\n--- Autores Disponíveis ---");
                    for (Autor a : ac.listarTodos()) System.out.println("ID: " + a.getId() + " | Nome: " + a.getNome());
                    int autorId = Integer.parseInt(view.lerEntrada("ID do Autor"));
                    Autor autor = ac.buscarPorId(autorId); 
                    lc.cadastrar(titulo, autor);
                    System.out.println("Livro cadastrado!");
                    break;
                case 2:
                    System.out.println("\n--- Lista de Livros ---");
                    if (lc.listarTodos().isEmpty()) System.out.println("Nenhum livro cadastrado.");
                    for (Livro l : lc.listarTodos()) System.out.println(l);
                    break;
                case 3:
                    int idAlt = Integer.parseInt(view.lerEntrada("ID do Livro a alterar"));
                    lc.buscarPorId(idAlt);
                    String novoTitulo = view.lerEntrada("Novo Título");
                    System.out.println("\n--- Autores Disponíveis ---");
                    for (Autor a : ac.listarTodos()) System.out.println("ID: " + a.getId() + " | Nome: " + a.getNome());
                    int novoAutorId = Integer.parseInt(view.lerEntrada("Novo ID do Autor"));
                    Autor novoAutor = ac.buscarPorId(novoAutorId);
                    lc.atualizar(idAlt, novoTitulo, novoAutor);
                    System.out.println("Livro atualizado!");
                    break;
                case 4:
                    int idDel = Integer.parseInt(view.lerEntrada("ID do Livro a deletar"));
                    lc.deletar(idDel);
                    System.out.println("Livro removido!");
                    break;
            }
        } catch (EntidadeNaoEncontradaException | NumberFormatException e) {
            System.out.println("\n[ERRO]: " + e.getMessage());
        }
    }

    private static void processarMenuAutores(MainView view, AutorController ac) {
        int opcao = view.exibirSubMenu("Autores");
        try {
            switch (opcao) {
                case 1:
                    String nome = view.lerEntrada("Nome do Autor");
                    String biografia = view.lerEntrada("Biografia");
                    String nacionalidade = view.lerEntrada("Nacionalidade");
                    ac.cadastrar(nome, biografia, nacionalidade);
                    System.out.println("Autor cadastrado!");
                    break;
                case 2:
                    System.out.println("\n--- Lista de Autores ---");
                    if (ac.listarTodos().isEmpty()) System.out.println("Nenhum autor cadastrado.");
                    for (Autor a : ac.listarTodos()) System.out.println(a);
                    break;
                case 3:
                    int idAlt = Integer.parseInt(view.lerEntrada("ID do Autor a alterar"));
                    String nNome = view.lerEntrada("Novo Nome");
                    String nBiografia = view.lerEntrada("Nova Biografia");
                    String nNac = view.lerEntrada("Nova Nacionalidade");
                    ac.atualizar(idAlt, nNome, nBiografia, nNac);
                    System.out.println("Autor atualizado!");
                    break;
                case 4:
                    int idDel = Integer.parseInt(view.lerEntrada("ID do Autor a deletar"));
                    ac.deletar(idDel);
                    System.out.println("Autor removido!");
                    break;
            }
        } catch (EntidadeNaoEncontradaException | NumberFormatException e) {
            System.out.println("\n[ERRO]: " + e.getMessage());
        }
    }

    private static void processarMenuUsuarios(MainView view, UsuarioController uc) {
        int opcao = view.exibirSubMenu("Usuários");
        try {
            switch (opcao) {
                case 1:
                    String nome = view.lerEntrada("Nome do Usuário");
                    String email = view.lerEntrada("Email");
                    String cpf = view.lerEntrada("CPF");
                    uc.cadastrar(nome, email, cpf);
                    System.out.println("Usuário cadastrado!");
                    break;
                case 2:
                    System.out.println("\n--- Lista de Usuários ---");
                    if (uc.listarTodos().isEmpty()) System.out.println("Nenhum usuário cadastrado.");
                    for (Usuario u : uc.listarTodos()) System.out.println(u);
                    break;
                case 3:
                    int idAlt = Integer.parseInt(view.lerEntrada("ID do Usuário a alterar"));
                    String nNome = view.lerEntrada("Novo Nome");
                    String nEmail = view.lerEntrada("Novo Email");
                    String nCpf = view.lerEntrada("Novo CPF");
                    uc.atualizar(idAlt, nNome, nEmail, nCpf);
                    System.out.println("Usuário updated!");
                    break;
                case 4:
                    int idDel = Integer.parseInt(view.lerEntrada("ID do Usuário a deletar"));
                    uc.deletar(idDel);
                    System.out.println("Usuário deletado!");
                    break;
            }
        } catch (EntidadeNaoEncontradaException | NumberFormatException e) {
            System.out.println("\n[ERRO]: " + e.getMessage());
        }
    }
}