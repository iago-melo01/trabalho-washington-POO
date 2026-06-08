package model;

import java.util.ArrayList;
import java.util.List;
import java.util.HashSet;

public class Curso extends ConteudoEducacional {
    private double valorMensal;
    private boolean ativo;
    private final List<Disciplina> disciplinas;

    public Curso(int id, String nome, int cargaHoraria, double valorMensal, boolean ativo) {
        super(id, nome, cargaHoraria);
        this.valorMensal = valorMensal;
        this.ativo = ativo;
        this.disciplinas = new ArrayList<>();
    }

    public double getValorMensal() {
        return valorMensal;
    }

    public void setValorMensal(double valorMensal) {
        this.valorMensal = valorMensal;
    }

    public boolean isAtivo() {
        return ativo;
    }

    public void ativar() {
        ativo = true;
    }

    public void desativar() {
        ativo = false;
    }

    public List<Disciplina> getDisciplinas() {
        return disciplinas;
    }

    public void adicionarDisciplina(int id,String nome,int cargaHoraria,String descricao,boolean ativo) {
        disciplinas.add(
                new Disciplina(
                        id,
                        nome,
                        cargaHoraria,
                        descricao,
                        ativo,
                        new ArrayList<>(),
                        new HashSet<>()
                )
        );
    }

    public void listarDisciplinas() {
        if (disciplinas.isEmpty()) {
            System.out.println("Nenhuma disciplina cadastrada neste curso.");
            return;
        }
        for (Disciplina disciplina : disciplinas) {
            System.out.println("  ID: " + disciplina.getId()
                    + " | Nome: " + disciplina.getNome()
                    + " | Carga horária: " + disciplina.getCargaHoraria()
                    + " | Descrição: " + disciplina.getDescricao()
                    + " | Ativo: " + disciplina.isAtivo());
        }
    }

    public boolean atualizarNomeDisciplina(int id, String novoNome) {
        Disciplina disciplina = buscarDisciplinaPorId(id);
        if (disciplina == null) {
            return false;
        }
        disciplina.setNome(novoNome);
        return true;
    }

    public boolean atualizarCargaHorariaDisciplina(int id, int novaCargaHoraria) {
        Disciplina disciplina = buscarDisciplinaPorId(id);
        if (disciplina == null) {
            return false;
        }
        disciplina.setCargaHoraria(novaCargaHoraria);
        return true;
    }

    public boolean atualizarDescricaoDisciplina(int id, String novaDescricao) {
        Disciplina disciplina = buscarDisciplinaPorId(id);
        if (disciplina == null) {
            return false;
        }
        disciplina.setDescricao(novaDescricao);
        return true;
    }

    public boolean removerDisciplina(int id) {
        for (int i = 0; i < disciplinas.size(); i++) {
            if (disciplinas.get(i).getId() == id) {
                disciplinas.remove(i);
                return true;
            }
        }
        return false;
    }

    public Disciplina buscarDisciplinaPorId(int id) {
        for (Disciplina disciplina : disciplinas) {
            if (disciplina.getId() == id) {
                return disciplina;
            }
        }
        return null;
    }

    public boolean possuiDisciplinaComId(int id) {
        return buscarDisciplinaPorId(id) != null;
    }

    public void exibirDados() {
        System.out.println("ID: " + getId()
                + " | Nome: " + getNome()
                + " | Carga horária: " + getCargaHoraria()
                + " | Valor mensal: R$ " + valorMensal
                + " | Ativo: " + ativo);
        System.out.println("Disciplinas:");
        listarDisciplinas();
    }
}
