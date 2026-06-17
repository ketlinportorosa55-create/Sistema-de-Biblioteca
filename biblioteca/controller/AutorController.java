package controller;

import exception.EntidadeNaoEncontradaException;
import model.Autor;
import java.io.*;
import java.util.ArrayList;
import java.util.List;

public class AutorController {
    private List<Autor> lista = new ArrayList<>();
    private int proximoId = 1;
    private final String arquivoDados = "autores.dat";
    private final String arquivoLog = "sistema_biblioteca.log";

    public AutorController() {
        carregarDados();
    }

    public void cadastrar(String nome, String biografia, String nacionalidade) {
        Autor autor = new Autor(proximoId++, nome, biografia, nacionalidade);
        lista.add(autor);
        registrarLog("Autor " + nome + " cadastrado.");
    }

    public List<Autor> listarTodos() {
        return new ArrayList<>(lista);
    }

    public Autor buscarPorId(int id) throws EntidadeNaoEncontradaException {
        for (Autor a : lista) {
            if (a.getId() == id) return a;
        }
        throw new EntidadeNaoEncontradaException("Autor com ID " + id + " não encontrado.");
    }

    public void atualizar(int id, String nome, String biografia, String nacionalidade) throws EntidadeNaoEncontradaException {
        Autor a = buscarPorId(id);
        a.setNome(nome);
        a.setBiografia(biografia);
        a.setNacionalidade(nacionalidade);
        registrarLog("Autor ID " + id + " atualizado.");
    }

    public void deletar(int id) throws EntidadeNaoEncontradaException {
        Autor a = buscarPorId(id);
        lista.remove(a);
        registrarLog("Autor ID " + id + " removido.");
    }

    public void salvarDados() {
        try (ObjectOutputStream oos = new ObjectOutputStream(new FileOutputStream(arquivoDados))) {
            oos.writeObject(lista);
        } catch (IOException e) {
            registrarLog("Erro ao salvar autores: " + e.getMessage());
        }
    }

    @SuppressWarnings("unchecked")
    private void carregarDados() {
        File file = new File(arquivoDados);
        if (!file.exists()) return;
        try (ObjectInputStream ois = new ObjectInputStream(new FileInputStream(file))) {
            lista = (List<Autor>) ois.readObject();
            for (Autor a : lista) {
                if (a.getId() >= proximoId) proximoId = a.getId() + 1;
            }
        } catch (Exception e) {
            registrarLog("Erro ao carregar autores.");
        }
    }

    private void registrarLog(String mensagem) {
        try (FileWriter fw = new FileWriter(arquivoLog, true); PrintWriter pw = new PrintWriter(fw)) {
            pw.println("[LOG AUTOR] " + mensagem);
        } catch (IOException e) {
            System.err.println("Erro ao escrever log.");
        }
    }
}