package controller;

import java.io.*;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.List;

public abstract class BaseController<T> {
    protected List<T> lista = new ArrayList<>();
    private final String arquivoDados;
    private static final String ARQUIVO_LOG = "sistema_biblioteca.log";

    public BaseController(String arquivoDados) {
        this.arquivoDados = arquivoDados;
        carregarDados();
    }

    public void adicionar(T objeto) {
        lista.add(objeto);
        registrarLog("Item adicionado na lista do controlador " + this.getClass().getSimpleName());
    }

    public List<T> listarTodos() {
        return new ArrayList<>(lista);
    }

    public void remover(T objeto) {
        lista.remove(objeto);
        registrarLog("Item removido na lista do controlador " + this.getClass().getSimpleName());
    }

    public void salvarDados() {
        try (ObjectOutputStream oos = new ObjectOutputStream(new FileOutputStream(arquivoDados))) {
            oos.writeObject(lista);
            registrarLog("Dados salvos com sucesso em: " + arquivoDados);
        } catch (IOException e) {
            registrarLog("ERRO ao salvar dados em " + arquivoDados + ": " + e.getMessage());
        }
    }

    @SuppressWarnings("unchecked")
    private void carregarDados() {
        File file = new File(arquivoDados);
        if (!file.exists()) return;
        
        try (ObjectInputStream ois = new ObjectInputStream(new FileInputStream(file))) {
            lista = (List<T>) ois.readObject();
            registrarLog("Dados carregados com sucesso de: " + arquivoDados);
        } catch (IOException | ClassNotFoundException e) {
            registrarLog("ERRO ao carregar dados de " + arquivoDados + ": " + e.getMessage());
        }
    }

    protected void registrarLog(String mensagem) {
        try (FileWriter fw = new FileWriter(ARQUIVO_LOG, true);
             PrintWriter pw = new PrintWriter(fw)) {
            String dataHora = LocalDateTime.now().format(DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss"));
            pw.println("[" + dataHora + "] " + mensagem);
        } catch (IOException e) {
            System.err.println("Erro crítico ao escrever no Log do sistema: " + e.getMessage());
        }
    }
}