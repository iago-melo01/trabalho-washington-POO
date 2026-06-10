package repository;

import model.Aluno;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

public class AlunoRepository {

    private final List<Aluno> alunos = new ArrayList<>();

    public void salvar(Aluno aluno) {
        alunos.add(aluno);
    }

    public List<Aluno> listar() {
        return new ArrayList<>(alunos);
    }

    public Optional<Aluno> buscarPorId(int id) {
        return alunos.stream()
                .filter(a -> a.getId() == id)
                .findFirst();
    }

    public Optional<Aluno> buscarPorMatricula(String matricula) {
        return alunos.stream()
                .filter(a -> a.getMatricula().equals(matricula))
                .findFirst();
    }

    public boolean remover(int id) {
        return alunos.removeIf(a -> a.getId() == id);
    }
}
