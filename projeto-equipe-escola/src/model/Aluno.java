package model;

public class Aluno extends Pessoa {

    private String matricula;

    public Aluno(int id, String nome, String email, String cpf,
                 String dataNascimento, String matricula) {

        super(id, nome, email, cpf, dataNascimento);
        this.matricula = matricula;
    }

    public String getMatricula() {
        return matricula;
    }

    public void setMatricula(String matricula) {
        this.matricula = matricula;
    }

    public void realizarMatricula() {
        System.out.println("Matrícula realizada.");
    }

    public void consultarMatriculas() {
        System.out.println("Consultando matrículas...");
    }

    @Override
    public void exibirDados() {
        System.out.println("ID: " + getId()
                + " | Nome: " + getNome()
                + " | Email: " + getEmail()
                + " | CPF: " + getCpf()
                + " | Data de nascimento: " + getDataNascimento()
                + " | Matrícula: " + matricula);
    }
}
