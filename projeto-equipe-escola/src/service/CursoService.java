package service;

import model.Curso;
import model.Disciplina;
import repository.CursoRepository;
import java.util.List;

public class CursoService {

    private final CursoRepository repository;
    private int proximoIdDisciplina = 1;

    public CursoService(CursoRepository repository) {
        this.repository = repository;
    }

    public int gerarIdDisciplina() {
        return proximoIdDisciplina++;
    }

    public Curso cadastrar(String nome, int cargaHoraria, double valorMensal) {
        validarNome(nome);
        if (cargaHoraria <= 0) {
            throw new IllegalArgumentException("Carga horária deve ser maior que zero.");
        }
        if (valorMensal < 0) {
            throw new IllegalArgumentException("Valor mensal não pode ser negativo.");
        }
        int id = repository.gerarId();
        Curso curso = new Curso(id, nome, cargaHoraria, valorMensal, true);
        repository.salvar(curso);
        return curso;
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

    public Disciplina adicionarDisciplina(int idCurso, String nome,
            int cargaHoraria, String descricao) {
        validarNome(nome);
        if (cargaHoraria <= 0) {
            throw new IllegalArgumentException("Carga horária da disciplina deve ser maior que zero.");
        }
        Curso curso = buscarPorId(idCurso);
        if (!curso.isAtivo()) {
            throw new IllegalArgumentException("Não é possível adicionar disciplina em curso inativo.");
        }
        int idDisciplina = gerarIdDisciplina();
        curso.adicionarDisciplina(idDisciplina, nome, cargaHoraria, descricao, true);
        return curso.buscarDisciplinaPorId(idDisciplina);
    }

    public void listarDisciplinas(int idCurso) {
        Curso curso = buscarPorId(idCurso);
        System.out.println("Disciplinas do curso \"" + curso.getNome() + "\":");
        curso.listarDisciplinas();
    }

    public void atualizarDisciplina(
            int idCurso,
            int idDisciplina,
            String nome,
            int cargaHoraria,
            String descricao) {

        validarNome(nome);

        if (cargaHoraria <= 0) {
            throw new IllegalArgumentException(
                    "Carga horária deve ser maior que zero."
            );
        }

        Disciplina disciplina = buscarDisciplina(idCurso, idDisciplina);

        disciplina.setNome(nome);
        disciplina.setCargaHoraria(cargaHoraria);
        disciplina.setDescricao(descricao);
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

    public void ativarDisciplina(int idCurso, int idDisciplina) {
        buscarDisciplina(idCurso, idDisciplina).ativar();
    }

    public void desativarDisciplina(int idCurso, int idDisciplina) {
        buscarDisciplina(idCurso, idDisciplina).desativar();
    }

    public Disciplina buscarDisciplina(int idCurso, int idDisciplina) {
        Curso curso = buscarPorId(idCurso);
        Disciplina disciplina = curso.buscarDisciplinaPorId(idDisciplina);
        if (disciplina == null) {
            throw new IllegalArgumentException("Disciplina não encontrada no curso: ID " + idDisciplina);
        }
        return disciplina;
    }

    private void validarNome(String nome) {
        if (nome == null || nome.trim().isEmpty()) {
            throw new IllegalArgumentException("Nome não pode ser vazio.");
        }
    }
}
