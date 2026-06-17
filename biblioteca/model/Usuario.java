package model;

public class Usuario extends Pessoa {
    private static final long serialVersionUID = 1L;
    private String cpf;

    public Usuario() { super(); }

    public Usuario(int id, String nome, String email, String cpf) {
        super(id, nome, email);
        this.cpf = cpf;
    }

    @Override
    public String getDocumentoPrincipal() {
        return "CPF: " + this.cpf;
    }

    public String getCpf() { return cpf; }
    public void setCpf(String cpf) { this.cpf = cpf; }

    @Override
    public String toString() {
        return "ID: " + getId() + " | Nome: " + getNome() + " | " + getDocumentoPrincipal() + " | Email: " + getEmail();
    }
}