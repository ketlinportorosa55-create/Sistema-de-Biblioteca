package model;

import java.io.Serializable;

public class Livro implements Persistivel, Serializable {
    private static final long serialVersionUID = 1L;
    
    private int id;
    private String titulo;
    private Autor autor; 

    public Livro() {}

    public Livro(int id, String titulo, Autor autor) {
        this.id = id;
        this.titulo = titulo;
        this.autor = autor;
    }

    public void atualizarDados(String titulo) {
        this.titulo = titulo;
    }

    public void atualizarDados(String titulo, Autor autor) {
        this.titulo = titulo;
        this.autor = autor;
    }

    @Override
    public int getId() { return id; }
    @Override
    public void setId(int id) { this.id = id; }
    public String getTitulo() { return titulo; }
    public void setTitulo(String titulo) { this.titulo = titulo; }
    public Autor getAutor() { return autor; }
    public void setAutor(Autor autor) { this.autor = autor; }

    @Override
    public String toString() {
        return "ID: " + id + " | Título: " + titulo + " | Autor: [" + (autor != null ? autor.getNome() : "Sem Autor") + "]";
    }
}