package service;

import model.Curso;
import repository.CursoRepository;
import java.util.List;

public class CursoService {

    private final CursoRepository repository;

    public CursoService(CursoRepository repository) {
        this.repository = repository;
    }

    public void cadastrar(int id, String nome, int cargaHoraria, double valorMensal) {
        validarNome(nome);
        if (cargaHoraria <= 0) {
            throw new IllegalArgumentException("Carga horária deve ser maior que zero.");
        }
        if (valorMensal < 0) {
            throw new IllegalArgumentException("Valor mensal não pode ser negativo.");
        }
        if (repository.buscarPorId(id).isPresent()) {
            throw new IllegalArgumentException("Já existe curso com o ID " + id + ".");
        }
        repository.salvar(new Curso(id, nome, cargaHoraria, valorMensal, true));
    }

    public List<Curso> listar() {
        return repository.listar();
    }

    public Curso buscarPorId(int id) {
        return repository.buscarPorId(id)
                .orElseThrow(() -> new IllegalArgumentException("Curso não encontrado: ID " + id));
    }

    public void atualizar(int id, String nome, int cargaHoraria, double valorMensal) {
        validarNome(nome);
        if (cargaHoraria <= 0) {
            throw new IllegalArgumentException("Carga horária deve ser maior que zero.");
        }
        if (valorMensal < 0) {
            throw new IllegalArgumentException("Valor mensal não pode ser negativo.");
        }
        Curso curso = buscarPorId(id);
        curso.setNome(nome);
        curso.setCargaHoraria(cargaHoraria);
        curso.setValorMensal(valorMensal);
    }

    public void remover(int id) {
        if (!repository.remover(id)) {
            throw new IllegalArgumentException("Curso não encontrado: ID " + id);
        }
    }

    public void adicionarDisciplina(int idCurso, int idDisciplina, String nome,
            int cargaHoraria, String descricao) {
        validarNome(nome);
        if (cargaHoraria <= 0) {
            throw new IllegalArgumentException("Carga horária da disciplina deve ser maior que zero.");
        }
        Curso curso = buscarPorId(idCurso);
        if (curso.possuiDisciplinaComId(idDisciplina)) {
            throw new IllegalArgumentException("Já existe disciplina com o ID " + idDisciplina + " neste curso.");
        }
        curso.adicionarDisciplina(idDisciplina, nome, cargaHoraria, descricao, true);
    }

    public void listarDisciplinas(int idCurso) {
        Curso curso = buscarPorId(idCurso);
        System.out.println("Disciplinas do curso \"" + curso.getNome() + "\":");
        curso.listarDisciplinas();
    }

    public void atualizarNomeDisciplina(int idCurso, int idDisciplina, String novoNome) {
        validarNome(novoNome);
        Curso curso = buscarPorId(idCurso);
        if (!curso.atualizarNomeDisciplina(idDisciplina, novoNome)) {
            throw new IllegalArgumentException("Disciplina não encontrada no curso: ID " + idDisciplina);
        }
    }

    public void atualizarCargaHorariaDisciplina(int idCurso, int idDisciplina, int novaCarga) {
        if (novaCarga <= 0) {
            throw new IllegalArgumentException("Carga horária deve ser maior que zero.");
        }
        Curso curso = buscarPorId(idCurso);
        if (!curso.atualizarCargaHorariaDisciplina(idDisciplina, novaCarga)) {
            throw new IllegalArgumentException("Disciplina não encontrada no curso: ID " + idDisciplina);
        }
    }

    public void atualizarDescricaoDisciplina(int idCurso, int idDisciplina, String descricao) {
        Curso curso = buscarPorId(idCurso);
        if (!curso.atualizarDescricaoDisciplina(idDisciplina, descricao)) {
            throw new IllegalArgumentException("Disciplina não encontrada no curso: ID " + idDisciplina);
        }
    }

    public void removerDisciplina(int idCurso, int idDisciplina) {
        Curso curso = buscarPorId(idCurso);
        if (!curso.removerDisciplina(idDisciplina)) {
            throw new IllegalArgumentException("Disciplina não encontrada no curso: ID " + idDisciplina);
        }
        System.out.println("Disciplina removida com sucesso.");
    }

    public void ativarCurso(int id) {
        buscarPorId(id).ativar();
    }

    public void desativarCurso(int id) {
        buscarPorId(id).desativar();
    }

    private void validarNome(String nome) {
        if (nome == null || nome.trim().isEmpty()) {
            throw new IllegalArgumentException("Nome não pode ser vazio.");
        }
    }
}
