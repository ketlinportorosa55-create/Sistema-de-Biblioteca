package model;

import java.io.Serializable;

public class Emprestimo implements Persistivel, Serializable {
    private static final long serialVersionUID = 1L;

    private int id;
    private Usuario usuario; 
    private Livro livro;     
    private String dataEmprestimo;
    private boolean devolvido;

    public Emprestimo() {}

    public Emprestimo(int id, Usuario usuario, Livro livro, String dataEmprestimo) {
        this.id = id;
        this.usuario = usuario;
        this.livro = livro;
        this.dataEmprestimo = dataEmprestimo;
        this.devolvido = false;
    }

    @Override
    public int getId() { return id; }
    @Override
    public void setId(int id) { this.id = id; }
    public Usuario getUsuario() { return usuario; }
    public void setUsuario(Usuario usuario) { this.usuario = usuario; }
    public Livro getLivro() { return livro; }
    public void setLivro(Livro livro) { this.livro = livro; }
    public String getDataEmprestimo() { return dataEmprestimo; }
    public void setDataEmprestimo(String dataEmprestimo) { this.dataEmprestimo = dataEmprestimo; }
    public boolean isDevolvido() { return devolvido; }
    public void setDevolvido(boolean devolvido) { this.devolvido = devolvido; }

    @Override
    public String toString() {
        String status = devolvido ? "DEVOLVIDO" : "ATIVO";
        return "ID Empréstimo: " + id + " | Livro: " + livro.getTitulo() + " | Usuário: " + usuario.getNome() + " | Data: " + dataEmprestimo + " | Status: " + status;
    }
}