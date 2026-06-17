package view;

import java.util.Scanner;

public class MainView {
    private final Scanner scanner = new Scanner(System.in);

    public int exibirMenuPrincipal() {
        System.out.println("\n=================================");
        System.out.println("   SISTEMA DE BIBLIOTECA (MVC)   ");
        System.out.println("=================================");
        System.out.println("1. Gerenciar Livros");
        System.out.println("2. Gerenciar Autores");
        System.out.println("3. Gerenciar Usuários");
        System.out.println("4. Gerenciar Empréstimos");
        System.out.println("0. Sair e Gravar Dados");
        System.out.print("Escolha uma opção: ");
        try {
            return Integer.parseInt(scanner.nextLine());
        } catch (NumberFormatException e) {
            return -1; 
        }
    }

    public int exibirSubMenu(String entidade) {
        System.out.println("\n--- Menu " + entidade + " ---");
        if (entidade.equals("Empréstimos")) {
            System.out.println("1. Registrar Novo Empréstimo");
            System.out.println("2. Listar Histórico de Empréstimos");
            System.out.println("3. Registrar Devolução de Livro");
            System.out.println("0. Voltar ao Menu Principal");
        } else {
            System.out.println("1. Cadastrar");
            System.out.println("2. Listar Todos");
            System.out.println("3. Atualizar");
            System.out.println("4. Deletar");
            System.out.println("0. Voltar ao Menu Principal");
        }
        System.out.print("Escolha uma opção: ");
        try {
            return Integer.parseInt(scanner.nextLine());
        } catch (NumberFormatException e) {
            return -1;
        }
    }

    public String lerEntrada(String campo) {
        System.out.print("Digite o(a) " + campo + ": ");
        return scanner.nextLine();
    }
}