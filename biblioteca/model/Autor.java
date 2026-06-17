package model;

public class Autor extends Pessoa {
    private static final long serialVersionUID = 1L;
    private String nacionalidade;
    private String biografia;

    public Autor() { super(); }

    public Autor(int id, String nome, String biografia, String nacionalidade) {
        super(id, nome, biografia);
        this.nacionalidade = nacionalidade;
        this.biografia = biografia;
    }

    @Override
    public String getDocumentoPrincipal() {
        return "Nacionalidade: " + this.nacionalidade;
    }

    public String getNacionalidade() { return nacionalidade; }
    public void setNacionalidade(String nacionalidade) { this.nacionalidade = nacionalidade; }
    public String getBiografia() { return biografia; }
    public void setBiografia(String biografia) { this.biografia = biografia; }

    @Override
    public String toString() {
        return "ID: " + getId() + " | Autor: " + getNome() + " | " + getDocumentoPrincipal() + " | Bio: " + biografia;
    }
}