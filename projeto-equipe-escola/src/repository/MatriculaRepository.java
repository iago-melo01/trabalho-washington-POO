package repository;

import model.Matricula;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

public class MatriculaRepository {

    private final List<Matricula> matriculas = new ArrayList<>();

    public void salvar(Matricula matricula) {
        matriculas.add(matricula);
    }

    public Optional<Matricula> buscarPorId(int id) {
        return matriculas.stream()
                .filter(m -> m.getId() == id)
                .findFirst();
    }

    public List<Matricula> listarPorAluno(int idAluno) {
        List<Matricula> resultado = new ArrayList<>();
        for (Matricula m : matriculas) {
            if (m.getAluno().getId() == idAluno) {
                resultado.add(m);
            }
        }
        return resultado;
    }
}
