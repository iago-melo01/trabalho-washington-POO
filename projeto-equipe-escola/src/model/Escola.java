package model;

import java.util.ArrayList;
import java.util.List;

public class Escola {

    private int id;
    private String nome;
    private String endereco;
    private String telefone;
    private List<Curso> cursos;

    public Escola(int id, String nome, String endereco, String telefone) {
        this.id = id;
        this.nome = nome;
        this.endereco = endereco;
        this.telefone = telefone;
        this.cursos = new ArrayList<>();
    }

    public void adicionarCurso(Curso curso) {
        cursos.add(curso);
    }

    public void listarCursos() {
        if (cursos.isEmpty()) {
            System.out.println("Nenhum curso cadastrado.");
            return;
        }

        for (Curso curso : cursos) {
            System.out.println(curso);
        }
    }

    public void atualizarCurso(int idCurso, Curso novoCurso) {
        for (int i = 0; i < cursos.size(); i++) {
            if (cursos.get(i).getId() == idCurso) {
                cursos.set(i, novoCurso);
                System.out.println("Curso atualizado com sucesso.");
                return;
            }
        }

        System.out.println("Curso não encontrado.");
    }

    public void removerCurso(int idCurso) {
        for (int i = 0; i < cursos.size(); i++) {
            if (cursos.get(i).getId() == idCurso) {
                cursos.remove(i);
                System.out.println("Curso removido com sucesso.");
                return;
            }
        }

        System.out.println("Curso não encontrado.");
    }

    public int getId() {
        return id;
    }

    public String getNome() {
        return nome;
    }

    public String getEndereco() {
        return endereco;
    }

    public String getTelefone() {
        return telefone;
    }

    public List<Curso> getCursos() {
        return cursos;
    }
}
