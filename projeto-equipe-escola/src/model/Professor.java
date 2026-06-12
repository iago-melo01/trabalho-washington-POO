package model;

public class Professor extends Pessoa {

    private String especialidade;

    public Professor(int id, String nome, String email, String cpf,
                     String dataNascimento, String especialidade) {

        super(id, nome, email, cpf, dataNascimento);
        this.especialidade = especialidade;
    }

    public String getEspecialidade() {
        return especialidade;
    }

    public void setEspecialidade(String especialidade) {
        this.especialidade = especialidade;
    }

    public void lancarNota() {
        System.out.println("Nota lançada.");
    }

    public void listarNotas() {
        System.out.println("Listando notas...");
    }

    @Override
    public void exibirDados() {
        System.out.println("ID: " + getId()
                + " | Nome: " + getNome()
                + " | Email: " + getEmail()
                + " | CPF: " + getCpf()
                + " | Data de nascimento: " + getDataNascimento()
                + " | Especialidade: " + especialidade);
    }
}
