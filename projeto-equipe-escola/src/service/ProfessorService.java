package service;

import model.Professor;
import repository.ProfessorRepository;
import util.Validador;
import java.util.List;

public class ProfessorService {

    private final ProfessorRepository repository;

    public ProfessorService(ProfessorRepository repository) {
        this.repository = repository;
    }

    public Professor cadastrar(String nome, String email, String cpf,
                          String dataNascimento, String especialidade) {
        Validador.validarNome(nome);
        Validador.validarTextoObrigatorio(especialidade, "Especialidade");
        int id = repository.gerarId();
        Professor professor = new Professor(id, nome, email, cpf, dataNascimento, especialidade);
        repository.salvar(professor);
        return professor;
    }

    public List<Professor> listar() {
        return repository.listar();
    }

    public Professor buscarPorId(int id) {
        return repository.buscarPorId(id)
                .orElseThrow(() -> new IllegalArgumentException("Professor não encontrado: ID " + id));
    }

    public void atualizar(int id, String nome, String email, String cpf,
                          String dataNascimento, String especialidade) {
        Validador.validarNome(nome);
        Validador.validarTextoObrigatorio(especialidade, "Especialidade");
        Professor professor = buscarPorId(id);
        professor.setNome(nome);
        professor.setEmail(email);
        professor.setCpf(cpf);
        professor.setDataNascimento(dataNascimento);
        professor.setEspecialidade(especialidade);
    }

    public void remover(int id) {
        if (!repository.remover(id)) {
            throw new IllegalArgumentException("Professor não encontrado: ID " + id);
        }
    }
}
