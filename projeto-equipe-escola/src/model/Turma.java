package model;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class Turma {

    private int id;
    private String codigo;
    private String turno;
    private String sala;
    private Professor professor;
    private List<Matricula> matriculas;
    private Map<Integer, Aluno> alunos;
    private boolean ativo;

    public Turma(int id, String codigo, String turno, String sala) {
        this.id = id;
        this.codigo = codigo;
        this.turno = turno;
        this.sala = sala;
        this.matriculas = new ArrayList<>();
        this.alunos = new HashMap<>();
        this.ativo = true;
    }

    public void definirProfessor(Professor professor) {
        this.professor = professor;
    }

    public void adicionarAluno(Aluno aluno) {
        if (alunos.containsKey(aluno.getId())) {
            System.out.println("Aluno já está cadastrado nesta turma.");
            return;
        }

        alunos.put(aluno.getId(), aluno);
        System.out.println("Aluno adicionado com sucesso.");
    }

    public void listarAlunos() {
        if (alunos.isEmpty()) {
            System.out.println("Nenhum aluno cadastrado nesta turma.");
            return;
        }

        for (Aluno aluno : alunos.values()) {
            aluno.exibirDados();
        }
    }

    public void removerAluno(int idAluno) {
        if (alunos.containsKey(idAluno)) {
            alunos.remove(idAluno);
            System.out.println("Aluno removido com sucesso.");
        } else {
            System.out.println("Aluno não encontrado nesta turma.");
        }
    }

    public void adicionarMatricula(Matricula matricula) {
        matriculas.add(matricula);
    }

    public void atualizarDados(String novoTurno, String novaSala) {
        this.turno = novoTurno;
        this.sala = novaSala;
        System.out.println("Dados da turma atualizados com sucesso.");
    }

    public void ativar() {
        this.ativo = true;
        System.out.println("Turma ativada.");
    }

    public void desativar() {
        this.ativo = false;
        System.out.println("Turma desativada.");
    }

    public boolean isAtivo() {
        return ativo;
    }

    public int getId() {
        return id;
    }

    public String getCodigo() {
        return codigo;
    }

    public String getTurno() {
        return turno;
    }

    public String getSala() {
        return sala;
    }

    public Professor getProfessor() {
        return professor;
    }

    public List<Matricula> getMatriculas() {
        return matriculas;
    }

    public Map<Integer, Aluno> getAlunos() {
        return alunos;
    }
}
