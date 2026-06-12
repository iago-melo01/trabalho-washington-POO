package service;

import model.Curso;
import model.Disciplina;
import model.Professor;
import model.Turma;
import repository.CursoRepository;
import repository.ProfessorRepository;
import util.Validador;

public class TurmaService {

    private final CursoRepository cursoRepository;
    private final ProfessorRepository professorRepository;

    public TurmaService(CursoRepository cursoRepository, ProfessorRepository professorRepository) {
        this.cursoRepository = cursoRepository;
        this.professorRepository = professorRepository;
    }

    public void cadastrar(int idCurso, int idDisciplina, int idTurma,
                          String codigo, String turno, String sala) {
        Validador.validarTextoObrigatorio(codigo, "Código");
        Disciplina disciplina = buscarDisciplina(idCurso, idDisciplina);
        if (!disciplina.isAtivo()) {
            throw new IllegalArgumentException("Não é possível cadastrar turma em disciplina inativa.");
        }
        for (Turma turma : disciplina.getTurmas()) {
            if (turma.getId() == idTurma) {
                throw new IllegalArgumentException("Já existe turma com o ID " + idTurma + " nesta disciplina.");
            }
        }
        disciplina.adicionarTurma(new Turma(idTurma, codigo, turno, sala));
    }

    public void listarTurmas(int idCurso, int idDisciplina) {
        Disciplina disciplina = buscarDisciplina(idCurso, idDisciplina);
        disciplina.listarTurmas();
    }

    public Turma buscarTurma(int idCurso, int idDisciplina, int idTurma) {
        Disciplina disciplina = buscarDisciplina(idCurso, idDisciplina);
        for (Turma turma : disciplina.getTurmas()) {
            if (turma.getId() == idTurma) {
                return turma;
            }
        }
        throw new IllegalArgumentException("Turma não encontrada: ID " + idTurma);
    }

    public void atualizar(int idCurso, int idDisciplina, int idTurma, String turno, String sala) {
        Turma turma = buscarTurma(idCurso, idDisciplina, idTurma);
        turma.atualizarDados(turno, sala);
    }

    public void remover(int idCurso, int idDisciplina, int idTurma) {
        Disciplina disciplina = buscarDisciplina(idCurso, idDisciplina);
        if (!disciplina.removerTurma(idTurma)) {
            throw new IllegalArgumentException("Turma não encontrada: ID " + idTurma);
        }
    }

    public void definirProfessor(int idCurso, int idDisciplina, int idTurma, int idProfessor) {
        Turma turma = buscarTurma(idCurso, idDisciplina, idTurma);
        Professor professor = professorRepository.buscarPorId(idProfessor)
                .orElseThrow(() -> new IllegalArgumentException("Professor não encontrado: ID " + idProfessor));
        turma.definirProfessor(professor);
        System.out.println("Professor definido com sucesso.");
    }

    public void listarAlunos(int idCurso, int idDisciplina, int idTurma) {
        buscarTurma(idCurso, idDisciplina, idTurma).listarAlunos();
    }

    public void ativar(int idCurso, int idDisciplina, int idTurma) {
        buscarTurma(idCurso, idDisciplina, idTurma).ativar();
    }

    public void desativar(int idCurso, int idDisciplina, int idTurma) {
        buscarTurma(idCurso, idDisciplina, idTurma).desativar();
    }

    public void adicionarProfessorApto(int idCurso, int idDisciplina, int idProfessor) {
        Disciplina disciplina = buscarDisciplina(idCurso, idDisciplina);
        Professor professor = professorRepository.buscarPorId(idProfessor)
                .orElseThrow(() -> new IllegalArgumentException("Professor não encontrado: ID " + idProfessor));
        disciplina.adicionarProfessorApto(professor);
    }

    public void listarProfessoresAptos(int idCurso, int idDisciplina) {
        buscarDisciplina(idCurso, idDisciplina).listarProfessoresAptos();
    }

    public void removerProfessorApto(int idCurso, int idDisciplina, int idProfessor) {
        Disciplina disciplina = buscarDisciplina(idCurso, idDisciplina);
        Professor professor = professorRepository.buscarPorId(idProfessor)
                .orElseThrow(() -> new IllegalArgumentException("Professor não encontrado: ID " + idProfessor));
        disciplina.removerProfessorApto(professor);
    }

    private Disciplina buscarDisciplina(int idCurso, int idDisciplina) {
        Curso curso = cursoRepository.buscarPorId(idCurso)
                .orElseThrow(() -> new IllegalArgumentException("Curso não encontrado: ID " + idCurso));
        Disciplina disciplina = curso.buscarDisciplinaPorId(idDisciplina);
        if (disciplina == null) {
            throw new IllegalArgumentException("Disciplina não encontrada no curso: ID " + idDisciplina);
        }
        return disciplina;
    }

    public Turma buscarPorId(int idCurso, int idDisciplina, int idTurma) {
        Disciplina disciplina = buscarDisciplina(idCurso, idDisciplina);

        Turma turma = disciplina.buscarTurmaPorId(idTurma);

        if (turma == null) {
            throw new IllegalArgumentException(
                    "Turma não encontrada: ID " + idTurma
            );
        }

        return turma;
    }
}
