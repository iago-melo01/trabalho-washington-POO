package model;

public abstract class Pessoa {

    protected int id;
    protected String nome;
    protected String email;
    protected String cpf;
    protected String dataNascimento;

    public Pessoa(int id, String nome, String email, String cpf, String dataNascimento) {
        this.id = id;
        this.nome = nome;
        this.email = email;
        this.cpf = cpf;
        this.dataNascimento = dataNascimento;
    }

    public abstract void exibirDados();
}