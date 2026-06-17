package controller;

import exception.EntidadeNaoEncontradaException;
import model.Autor;
import model.Livro;
import java.io.*;
import java.util.ArrayList;
import java.util.List;

public class LivroController {
    private List<Livro> lista = new ArrayList<>();
    private int proximoId = 1;
    private final String arquivoDados = "livros.dat";
    private final String arquivoLog = "sistema_biblioteca.log";

    public LivroController() {
        carregarDados();
    }

    public void cadastrar(String titulo, Autor autor) {
        Livro livro = new Livro(proximoId++, titulo, autor);
        lista.add(livro);
        registrarLog("Livro " + titulo + " cadastrado.");
    }

    public List<Livro> listarTodos() {
        return new ArrayList<>(lista);
    }

    public Livro buscarPorId(int id) throws EntidadeNaoEncontradaException {
        for (Livro l : lista) {
            if (l.getId() == id) return l;
        }
        throw new EntidadeNaoEncontradaException("Livro com ID " + id + " não encontrado.");
    }

    public void atualizar(int id, String titulo, Autor autor) throws EntidadeNaoEncontradaException {
        Livro l = buscarPorId(id);
        l.atualizarDados(titulo, autor); // Uso da sobrecarga
        registrarLog("Livro ID " + id + " atualizado.");
    }

    public void deletar(int id) throws EntidadeNaoEncontradaException {
        Livro l = buscarPorId(id);
        lista.remove(l);
        registrarLog("Livro ID " + id + " deletado.");
    }

    public void salvarDados() {
        try (ObjectOutputStream oos = new ObjectOutputStream(new FileOutputStream(arquivoDados))) {
            oos.writeObject(lista);
        } catch (IOException e) {
            registrarLog("Erro ao salvar livros: " + e.getMessage());
        }
    }

    @SuppressWarnings("unchecked")
    private void carregarDados() {
        File file = new File(arquivoDados);
        if (!file.exists()) return;
        try (ObjectInputStream ois = new ObjectInputStream(new FileInputStream(file))) {
            lista = (List<Livro>) ois.readObject();
            for (Livro l : lista) {
                if (l.getId() >= proximoId) proximoId = l.getId() + 1;
            }
        } catch (Exception e) {
            registrarLog("Erro ao carregar livros.");
        }
    }

    private void registrarLog(String mensagem) {
        try (FileWriter fw = new FileWriter(arquivoLog, true); PrintWriter pw = new PrintWriter(fw)) {
            pw.println("[LOG LIVRO] " + mensagem);
        } catch (IOException e) {
            System.err.println("Erro ao escrever log.");
        }
    }
}