package service;

import exception.MatriculaInvalidaException;
import model.Aluno;
import model.Matricula;
import model.Turma;
import repository.AlunoRepository;
import repository.MatriculaRepository;
import java.util.List;

public class MatriculaService {

    private final AlunoRepository alunoRepository;
    private final MatriculaRepository matriculaRepository;
    private final TurmaService turmaService;

    public MatriculaService(AlunoRepository alunoRepository,
                            MatriculaRepository matriculaRepository,
                            TurmaService turmaService) {
        this.alunoRepository = alunoRepository;
        this.matriculaRepository = matriculaRepository;
        this.turmaService = turmaService;
    }

    public Matricula realizar(int idAluno, int idCurso, int idDisciplina, int idTurma) {
        Aluno aluno = alunoRepository.buscarPorId(idAluno)
                .orElseThrow(() -> new IllegalArgumentException("Aluno não encontrado: ID " + idAluno));
        Turma turma = turmaService.buscarTurma(idCurso, idDisciplina, idTurma);

        if (turma.getAlunos().containsKey(idAluno)) {
            throw new IllegalArgumentException("Aluno já está matriculado nesta turma.");
        }

        try {
            Matricula matricula = new Matricula(aluno, turma);
            matriculaRepository.salvar(matricula);
            turma.adicionarMatricula(matricula);
            turma.adicionarAluno(aluno);
            return matricula;
        } catch (MatriculaInvalidaException e) {
            throw new IllegalArgumentException(e.getMessage());
        }
    }

    public void confirmar(int idMatricula) {
        Matricula matricula = buscarPorId(idMatricula);
        try {
            matricula.confirmar();
        } catch (MatriculaInvalidaException e) {
            throw new IllegalArgumentException(e.getMessage());
        }
    }

    public void cancelar(int idMatricula) {
        Matricula matricula = buscarPorId(idMatricula);
        try {
            matricula.cancelar();
        } catch (MatriculaInvalidaException e) {
            throw new IllegalArgumentException(e.getMessage());
        }
    }

    public Matricula buscarPorId(int id) {
        return matriculaRepository.buscarPorId(id)
                .orElseThrow(() -> new IllegalArgumentException("Matrícula não encontrada: ID " + id));
    }

    public List<Matricula> listarPorAluno(int idAluno) {
        alunoRepository.buscarPorId(idAluno)
                .orElseThrow(() -> new IllegalArgumentException("Aluno não encontrado: ID " + idAluno));
        return matriculaRepository.listarPorAluno(idAluno);
    }

    public List<Matricula> listarPorTurma(int idCurso, int idDisciplina, int idTurma) {
        Turma turma = turmaService.buscarTurma(idCurso, idDisciplina, idTurma);
        return turma.getMatriculas();
    }
}
