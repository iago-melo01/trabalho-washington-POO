package service;

import model.Matricula;
import util.Validador;
import java.util.List;

public class NotaService {

    private final MatriculaService matriculaService;

    public NotaService(MatriculaService matriculaService) {
        this.matriculaService = matriculaService;
    }

    public void lancar(int idMatricula, double nota) {
        Validador.validarNota(nota);
        Matricula matricula = matriculaService.buscarPorId(idMatricula);

        if (Matricula.STATUS_CANCELADA.equals(matricula.getStatus())) {
            throw new IllegalArgumentException("Não é possível lançar nota em matrícula cancelada.");
        }
        if (!Matricula.STATUS_CONFIRMADA.equals(matricula.getStatus())) {
            throw new IllegalArgumentException("Matrícula precisa estar confirmada para lançar nota.");
        }

        matricula.setNotaFinal(nota);
    }

    public void listarPorTurma(int idCurso, int idDisciplina, int idTurma) {
        List<Matricula> matriculas = matriculaService.listarPorTurma(idCurso, idDisciplina, idTurma);
        if (matriculas.isEmpty()) {
            System.out.println("Nenhuma matrícula nesta turma.");
            return;
        }
        for (Matricula matricula : matriculas) {
            System.out.println(matricula);
        }
    }
}
