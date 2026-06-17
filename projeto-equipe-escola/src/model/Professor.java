package model;

import exception.NotaInvalidaException;
import java.util.List;

public class Professor extends Pessoa implements Avaliavel {

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

    @Override
    public void lancarNota(Matricula matricula, double nota) throws NotaInvalidaException {
        if (matricula == null) {
            throw new NotaInvalidaException("Matrícula não pode ser nula.");
        }
        if (nota < 0 || nota > 10) {
            throw new NotaInvalidaException("Nota deve estar entre 0 e 10.");
        }

        matricula.setNotaFinal(nota);
    }

    @Override
    public List<Matricula> listarNotas(Turma turma) {
        if (turma == null) {
            throw new IllegalArgumentException("Turma não pode ser nula.");
        }

        return turma.getMatriculas();
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
