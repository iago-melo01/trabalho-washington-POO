package service;

import model.Aluno;
import repository.AlunoRepository;
import util.Validador;
import java.util.List;

public class AlunoService {

    private final AlunoRepository repository;

    public AlunoService(AlunoRepository repository) {
        this.repository = repository;
    }

    public Aluno cadastrar(String nome, String email, String cpf,
                          String dataNascimento, String matricula) {
        Validador.validarNome(nome);
        Validador.validarTextoObrigatorio(matricula, "Matrícula");
        if (repository.buscarPorMatricula(matricula).isPresent()) {
            throw new IllegalArgumentException("Já existe aluno com a matrícula " + matricula + ".");
        }
        int id = repository.gerarId();
        Aluno aluno = new Aluno(id, nome, email, cpf, dataNascimento, matricula);
        repository.salvar(aluno);
        return aluno;
    }

    public List<Aluno> listar() {
        return repository.listar();
    }

    public Aluno buscarPorId(int id) {
        return repository.buscarPorId(id)
                .orElseThrow(() -> new IllegalArgumentException("Aluno não encontrado: ID " + id));
    }

    public void atualizar(int id, String nome, String email, String cpf,
                          String dataNascimento, String matricula) {
        Validador.validarNome(nome);
        Validador.validarTextoObrigatorio(matricula, "Matrícula");
        Aluno aluno = buscarPorId(id);
        repository.buscarPorMatricula(matricula)
                .filter(a -> a.getId() != id)
                .ifPresent(a -> {
                    throw new IllegalArgumentException("Já existe aluno com a matrícula " + matricula + ".");
                });
        aluno.setNome(nome);
        aluno.setEmail(email);
        aluno.setCpf(cpf);
        aluno.setDataNascimento(dataNascimento);
        aluno.setMatricula(matricula);
    }

    public void remover(int id) {
        if (!repository.remover(id)) {
            throw new IllegalArgumentException("Aluno não encontrado: ID " + id);
        }
    }
}
