package repository;

import model.Professor;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

public class ProfessorRepository {

    private final List<Professor> professores = new ArrayList<>();
    private int proximoId = 1;

    public int gerarId() {
        return proximoId++;
    }

    public void salvar(Professor professor) {
        professores.add(professor);
    }

    public List<Professor> listar() {
        return new ArrayList<>(professores);
    }

    public Optional<Professor> buscarPorId(int id) {
        return professores.stream()
                .filter(p -> p.getId() == id)
                .findFirst();
    }

    public boolean remover(int id) {
        return professores.removeIf(p -> p.getId() == id);
    }
}
