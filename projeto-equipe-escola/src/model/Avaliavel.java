package model;

import exception.NotaInvalidaException;
import java.util.List;

public interface Avaliavel {
    void lancarNota(Matricula matricula, double nota) throws NotaInvalidaException;
    List<Matricula> listarNotas(Turma turma);
}
