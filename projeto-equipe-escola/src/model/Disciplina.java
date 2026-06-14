package model;

import java.util.List;
import java.util.Set;

public class Disciplina extends ConteudoEducacional implements Gerenciavel {

    private String descricao;
    private boolean ativo;
    private List<Turma> turmas;
    private Set<Professor> professoresAptos;

    public Disciplina(int id,String nome,int cargaHoraria,String descricao,boolean ativo,List<Turma> turmas,Set<Professor> professoresAptos) {

        super(id, nome, cargaHoraria);

        this.descricao = descricao;
        this.ativo = ativo;
        this.turmas = turmas;
        this.professoresAptos = professoresAptos;
    }

    public String getDescricao() {
        return descricao;
    }

    public void setDescricao(String descricao) {
        this.descricao = descricao;
    }

    @Override
    public boolean isAtivo() {
        return ativo;
    }

    @Override
    public void ativar() {
        ativo = true;
    }

    @Override
    public void desativar() {
        ativo = false;
    }

    public void setAtivo(boolean ativo) {
        this.ativo = ativo;
    }

    public List<Turma> getTurmas() {
        return turmas;
    }

    public Set<Professor> getProfessoresAptos() {
        return professoresAptos;
    }

    public void adicionarTurma(Turma turma) {
        turmas.add(turma);
    }

    public void listarTurmas() {

        if (turmas.isEmpty()) {
            System.out.println("Nenhuma turma cadastrada.");
            return;
        }
    
        for (Turma turma : turmas) {
    
            System.out.println("ID: " + turma.getId());
            System.out.println("Código: " + turma.getCodigo());
            System.out.println("Turno: " + turma.getTurno());
            System.out.println("Sala: " + turma.getSala());
            System.out.println("Ativa: " + turma.isAtivo());
    
            if (turma.getProfessor() != null) {
                System.out.println("Professor: " + turma.getProfessor().getNome());
            } else {
                System.out.println("Professor: Não definido");
            }
    
            System.out.println("-------------------------");
        }
    }

    public boolean atualizarTurma(
            int id,
            String novoTurno,
            String novaSala) {

        for (Turma turma : turmas) {

            if (turma.getId() == id) {

                turma.atualizarDados(
                        novoTurno,
                        novaSala
                );

                return true;
            }
        }

        return false;
    }

    public boolean removerTurma(int id) {

        for (int i = 0; i < turmas.size(); i++) {

            if (turmas.get(i).getId() == id) {

                turmas.remove(i);

                System.out.println("Turma removida com sucesso.");
                return true;
            }
        }

        System.out.println("Turma não encontrada.");
        return false;
    }

    public void adicionarProfessorApto(Professor professor) {

        professoresAptos.add(professor);

        System.out.println("Professor adicionado com sucesso.");
    }

    public void listarProfessoresAptos() {

        if (professoresAptos.isEmpty()) {

            System.out.println("Nenhum professor apto cadastrado.");
            return;
        }

        for (Professor professor : professoresAptos) {
            professor.exibirDados();
        }
    }

    public boolean removerProfessorApto(Professor professor) {

        if (professoresAptos.remove(professor)) {

            System.out.println("Professor removido com sucesso.");
            return true;
        }

        System.out.println("Professor não encontrado.");
        return false;
    }

    public Turma buscarTurmaPorId(int idTurma) {
        for (Turma turma : turmas) {
            if (turma.getId() == idTurma) {
                return turma;
            }
        }
    
        return null;
    }

    public void exibirDados() {

        System.out.println("===== DISCIPLINA =====");

        System.out.println("ID: " + getId());
        System.out.println("Nome: " + getNome());
        System.out.println("Carga Horária: " + getCargaHoraria());
        System.out.println("Descrição: " + descricao);
        System.out.println("Ativa: " + ativo);

        System.out.println("Quantidade de Turmas: "
                + turmas.size());

        System.out.println("Quantidade de Professores Aptos: "
                + professoresAptos.size());
    }
}