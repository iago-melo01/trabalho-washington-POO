package repository;

import model.Curso;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

public class CursoRepository {

    private final List<Curso> cursos = new ArrayList<>();

    public void salvar(Curso curso) {
        cursos.add(curso);
    }

    public List<Curso> listar() {
        return new ArrayList<>(cursos);
    }

    public Optional<Curso> buscarPorId(int id) {
        return cursos.stream()
                .filter(c -> c.getId() == id)
                .findFirst();
    }

    public boolean remover(int id) {
        return cursos.removeIf(c -> c.getId() == id);
    }
}
