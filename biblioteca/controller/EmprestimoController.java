package controller;

import exception.EntidadeNaoEncontradaException;
import model.Emprestimo;
import model.Livro;
import model.Usuario;
import java.io.*;
import java.util.ArrayList;
import java.util.List;

public class EmprestimoController {
    private List<Emprestimo> lista = new ArrayList<>();
    private int proximoId = 1;
    private final String arquivoDados = "emprestimos.dat";
    private final String arquivoLog = "sistema_biblioteca.log";

    public EmprestimoController() {
        carregarDados();
    }

    public void registrarEmprestimo(Usuario usuario, Livro livro, String data) {
        Emprestimo emprestimo = new Emprestimo(proximoId++, usuario, livro, data);
        lista.add(emprestimo);
        registrarLog("Livro ID " + livro.getId() + " alugado para Usuário ID " + usuario.getId());
    }

    public List<Emprestimo> listarTodos() {
        return new ArrayList<>(lista);
    }

    public Emprestimo buscarPorId(int id) throws EntidadeNaoEncontradaException {
        for (Emprestimo e : lista) {
            if (e.getId() == id) return e;
        }
        throw new EntidadeNaoEncontradaException("Empréstimo com ID " + id + " não encontrado.");
    }

    public void devolverLivro(int id) throws EntidadeNaoEncontradaException {
        Emprestimo e = buscarPorId(id);
        if (e.isDevolvido()) {
            throw new EntidadeNaoEncontradaException("Este empréstimo já constava como devolvido!");
        }
        e.setDevolvido(true);
        registrarLog("Devolução registrada para Empréstimo ID " + id);
    }

    public void salvarDados() {
        try (ObjectOutputStream oos = new ObjectOutputStream(new FileOutputStream(arquivoDados))) {
            oos.writeObject(lista);
        } catch (IOException e) {
            registrarLog("Erro ao salvar empréstimos: " + e.getMessage());
        }
    }

    @SuppressWarnings("unchecked")
    private void carregarDados() {
        File file = new File(arquivoDados);
        if (!file.exists()) return;
        try (ObjectInputStream ois = new ObjectInputStream(new FileInputStream(file))) {
            lista = (List<Emprestimo>) ois.readObject();
            for (Emprestimo e : lista) {
                if (e.getId() >= proximoId) proximoId = e.getId() + 1;
            }
        } catch (Exception e) {
            registrarLog("Erro ao carregar empréstimos.");
        }
    }

    private void registrarLog(String mensagem) {
        try (FileWriter fw = new FileWriter(arquivoLog, true); PrintWriter pw = new PrintWriter(fw)) {
            pw.println("[LOG EMPRESTIMO] " + mensagem);
        } catch (IOException e) {
            System.err.println("Erro ao escrever log.");
        }
    }
}