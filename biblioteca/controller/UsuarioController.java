package controller;

import exception.EntidadeNaoEncontradaException;
import model.Usuario;
import java.io.*;
import java.util.ArrayList;
import java.util.List;

public class UsuarioController {
    private List<Usuario> lista = new ArrayList<>();
    private int proximoId = 1;
    private final String arquivoDados = "usuarios.dat";
    private final String arquivoLog = "sistema_biblioteca.log";

    public UsuarioController() {
        carregarDados();
    }

    public void cadastrar(String nome, String email, String cpf) {
        Usuario usuario = new Usuario(proximoId++, nome, email, cpf);
        lista.add(usuario);
        registrarLog("Usuário " + nome + " cadastrado.");
    }

    public List<Usuario> listarTodos() {
        return new ArrayList<>(lista);
    }

    public Usuario buscarPorId(int id) throws EntidadeNaoEncontradaException {
        for (Usuario u : lista) {
            if (u.getId() == id) return u;
        }
        throw new EntidadeNaoEncontradaException("Usuário com ID " + id + " não encontrado.");
    }

    public void atualizar(int id, String nome, String email, String cpf) throws EntidadeNaoEncontradaException {
        Usuario u = buscarPorId(id);
        u.setNome(nome);
        u.setEmail(email);
        u.setCpf(cpf);
        registrarLog("Usuário ID " + id + " atualizado.");
    }

    public void deletar(int id) throws EntidadeNaoEncontradaException {
        Usuario u = buscarPorId(id);
        lista.remove(u);
        registrarLog("Usuário ID " + id + " deletado.");
    }

    public void salvarDados() {
        try (ObjectOutputStream oos = new ObjectOutputStream(new FileOutputStream(arquivoDados))) {
            oos.writeObject(lista);
        } catch (IOException e) {
            registrarLog("Erro ao salvar usuários: " + e.getMessage());
        }
    }

    @SuppressWarnings("unchecked")
    private void carregarDados() {
        File file = new File(arquivoDados);
        if (!file.exists()) return;
        try (ObjectInputStream ois = new ObjectInputStream(new FileInputStream(file))) {
            lista = (List<Usuario>) ois.readObject();
            for (Usuario u : lista) {
                if (u.getId() >= proximoId) proximoId = u.getId() + 1;
            }
        } catch (Exception e) {
            registrarLog("Erro ao carregar usuários.");
        }
    }

    private void registrarLog(String mensagem) {
        try (FileWriter fw = new FileWriter(arquivoLog, true); PrintWriter pw = new PrintWriter(fw)) {
            pw.println("[LOG USUARIO] " + mensagem);
        } catch (IOException e) {
            System.err.println("Erro ao escrever log.");
        }
    }
}