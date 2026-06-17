package main;

import model.Aluno;
import model.Curso;
import model.Matricula;
import model.Professor;
import model.Turma;
import model.Escola;
import model.Disciplina;
import model.Pessoa;
import repository.AlunoRepository;
import repository.CursoRepository;
import repository.MatriculaRepository;
import repository.ProfessorRepository;
import service.AlunoService;
import service.CursoService;
import service.MatriculaService;
import service.ProfessorService;
import service.TurmaService;

import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;

public class Main {

    private static final Scanner scanner = new Scanner(System.in);
    private static final CursoRepository cursoRepository = new CursoRepository();
    private static final AlunoRepository alunoRepository = new AlunoRepository();
    private static final ProfessorRepository professorRepository = new ProfessorRepository();
    private static final MatriculaRepository matriculaRepository = new MatriculaRepository();
    private static final CursoService cursoService = new CursoService(cursoRepository);
    private static final AlunoService alunoService = new AlunoService(alunoRepository);
    private static final ProfessorService professorService = new ProfessorService(professorRepository);
    private static final TurmaService turmaService = new TurmaService(cursoRepository, professorRepository);
    private static final MatriculaService matriculaService =
            new MatriculaService(alunoRepository, matriculaRepository, turmaService);

    private static Escola escola;

    public static void main(String[] args) {
        escola = new Escola(
                1,
                lerLinha("Nome da escola: "),
                "",
                ""
        );
    
        int opcao;
    
        do {
            exibirMenuPrincipal();
            opcao = lerOpcao();
    
            switch (opcao) {
                case 1:
                    menuCursos();
                    break;
                case 2:
                    menuDisciplinas();
                    break;
                case 3:
                    menuTurmas();
                    break;
                case 4:
                    menuAlunos();
                    break;
                case 5:
                    menuProfessores();
                    break;
                case 6:
                    menuMatriculas();
                    break;
                case 7:
                    listarPessoasDoSistema();
                    break;
                case 0:
                    System.out.println("Encerrando o sistema da escola " + escola.getNome() + "...");
                    break;
                default:
                    System.out.println("Opção inválida.");
            }
    
        } while (opcao != 0);
    
        scanner.close();
    }

    private static void exibirMenuPrincipal() {
        System.out.println("\n===== " + escola.getNome().toUpperCase() + " =====");
        System.out.println("1 - Gerenciar cursos");
        System.out.println("2 - Gerenciar disciplinas");
        System.out.println("3 - Gerenciar turmas");
        System.out.println("4 - Gerenciar alunos");
        System.out.println("5 - Gerenciar professores");
        System.out.println("6 - Gerenciar matrículas");
        System.out.println("7 - Listar pessoas do sistema");
        System.out.println("0 - Sair");
        System.out.print("Escolha uma opção: ");
    }
    
    private static int lerOpcao() {
        try {
            return Integer.parseInt(scanner.nextLine().trim());
        } catch (NumberFormatException e) {
            System.out.println("Erro: digite apenas números.");
            return -1;
        }
    }

    private static String lerLinha(String mensagem) {
        System.out.print(mensagem);
        return scanner.nextLine().trim();
    }

    private static int lerInteiro(String mensagem) {
        String valor = lerLinha(mensagem);
        try {
            return Integer.parseInt(valor);
        } catch (NumberFormatException e) {
            throw new IllegalArgumentException("Valor numérico inválido: " + valor);
        }
    }

    private static double lerDouble(String mensagem) {
        String valor = lerLinha(mensagem).replace(',', '.');
        try {
            return Double.parseDouble(valor);
        } catch (NumberFormatException e) {
            throw new IllegalArgumentException("Valor decimal inválido: " + valor);
        }
    }

    private static void tratarErro(Runnable acao) {
        try {
            acao.run();
        } catch (IllegalArgumentException e) {
            System.out.println("Erro: " + e.getMessage());
        }
    }

    private static void listarPessoasDoSistema() {
        List<Pessoa> pessoas = new ArrayList<>();
        pessoas.addAll(alunoService.listar());
        pessoas.addAll(professorService.listar());

        if (pessoas.isEmpty()) {
            System.out.println("Nenhuma pessoa cadastrada no sistema.");
            return;
        }

        System.out.println("\n--- Pessoas cadastradas no sistema ---");
        for (Pessoa pessoa : pessoas) {
            pessoa.exibirDados();
        }
    }

    private static void exibirCursosResumo() {
        List<Curso> cursos = cursoService.listar();
        if (cursos.isEmpty()) {
            System.out.println("Nenhum curso cadastrado.");
            return;
        }
        System.out.println("\n--- Cursos cadastrados ---");
        for (Curso curso : cursos) {
            System.out.println("ID: " + curso.getId() + " | Nome: " + curso.getNome());
        }
    }

    private static void exibirDisciplinasResumo(int idCurso) {
        Curso curso = cursoService.buscarPorId(idCurso);
        List<Disciplina> disciplinas = curso.getDisciplinas();
        if (disciplinas.isEmpty()) {
            System.out.println("Nenhuma disciplina cadastrada neste curso.");
            return;
        }
        System.out.println("\n--- Disciplinas cadastradas ---");
        for (Disciplina disciplina : disciplinas) {
            System.out.println("ID: " + disciplina.getId() + " | Nome: " + disciplina.getNome());
        }
    }

    private static void exibirTurmasResumo(int idCurso, int idDisciplina) {
        Disciplina disciplina = cursoService.buscarDisciplina(idCurso, idDisciplina);
        List<Turma> turmas = disciplina.getTurmas();
        if (turmas.isEmpty()) {
            System.out.println("Nenhuma turma cadastrada nesta disciplina.");
            return;
        }
        System.out.println("\n--- Turmas cadastradas ---");
        for (Turma turma : turmas) {
            System.out.println("ID: " + turma.getId() + " | Código: " + turma.getCodigo()
                    + " | Turno: " + turma.getTurno() + " | Sala: " + turma.getSala());
        }
    }

    private static void exibirAlunosResumo() {
        List<Aluno> alunos = alunoService.listar();
        if (alunos.isEmpty()) {
            System.out.println("Nenhum aluno cadastrado.");
            return;
        }
        System.out.println("\n--- Alunos cadastrados ---");
        for (Aluno aluno : alunos) {
            System.out.println("ID: " + aluno.getId() + " | Nome: " + aluno.getNome());
        }
    }

    private static void exibirProfessoresResumo() {
        List<Professor> professores = professorService.listar();
        if (professores.isEmpty()) {
            System.out.println("Nenhum professor cadastrado.");
            return;
        }
        System.out.println("\n--- Professores cadastrados ---");
        for (Professor professor : professores) {
            System.out.println("ID: " + professor.getId() + " | Nome: " + professor.getNome());
        }
    }

    private static void exibirMatriculasResumo() {
        List<Matricula> matriculas = matriculaService.listar();
        if (matriculas.isEmpty()) {
            System.out.println("Nenhuma matrícula cadastrada.");
            return;
        }
        System.out.println("\n--- Matrículas cadastradas ---");
        for (Matricula matricula : matriculas) {
            System.out.println(matricula);
        }
    }

    private static int lerIdCurso() {
        exibirCursosResumo();
        return lerInteiro("ID do curso: ");
    }

    private static int lerIdDisciplina(int idCurso) {
        exibirDisciplinasResumo(idCurso);
        return lerInteiro("ID da disciplina: ");
    }

    private static int lerIdTurma(int idCurso, int idDisciplina) {
        exibirTurmasResumo(idCurso, idDisciplina);
        return lerInteiro("ID da turma: ");
    }

    // --- Cursos ---

    private static void menuCursos() {
        int opcao;

        do {
            System.out.println("\n===== GERENCIAR CURSOS =====");
            System.out.println("1 - Cadastrar curso");
            System.out.println("2 - Listar cursos");
            System.out.println("3 - Atualizar curso");
            System.out.println("4 - Remover curso");
            System.out.println("5 - Ativar curso");
            System.out.println("6 - Desativar curso");
            System.out.println("7 - Gerenciar disciplinas de um curso");
            System.out.println("0 - Voltar");
            System.out.print("Escolha uma opção: ");

            opcao = lerOpcao();

            switch (opcao) {
                case 1:
                    tratarErro(Main::cadastrarCurso);
                    break;
                case 2:
                    listarCursos();
                    break;
                case 3:
                    tratarErro(Main::atualizarCurso);
                    break;
                case 4:
                    tratarErro(Main::removerCurso);
                    break;
                case 5:
                    tratarErro(Main::ativarCurso);
                    break;
                case 6:
                    tratarErro(Main::desativarCurso);
                    break;
                case 7:
                    menuDisciplinasDoCurso();
                    break;
                case 0:
                    System.out.println("Voltando ao menu principal...");
                    break;
                default:
                    System.out.println("Opção inválida.");
            }

        } while (opcao != 0);
    }

    private static void cadastrarCurso() {
        String nome = lerLinha("Nome do curso: ");
        int carga = lerInteiro("Carga horária: ");
        double valor = lerDouble("Valor mensal (R$): ");
        Curso curso = cursoService.cadastrar(nome, carga, valor);
        System.out.println("Curso cadastrado com sucesso. ID gerado: " + curso.getId());
    }

    private static void listarCursos() {
        List<Curso> cursos = cursoService.listar();
        if (cursos.isEmpty()) {
            System.out.println("Nenhum curso cadastrado.");
            return;
        }
        System.out.println("\n--- Cursos cadastrados ---");
        for (Curso curso : cursos) {
            System.out.println();
            curso.exibirDados();
        }
    }

    private static void atualizarCurso() {
        int id = lerIdCurso();
    
        Curso curso = cursoService.buscarPorId(id);
    
        System.out.println("Pressione ENTER para manter o valor atual.");
    
        String nome = lerLinha("Novo nome (" + curso.getNome() + "): ");
        String cargaStr = lerLinha("Nova carga horária (" + curso.getCargaHoraria() + "): ");
        String valorStr = lerLinha("Novo valor mensal (R$ " + curso.getValorMensal() + "): ");
    
        if (nome.isBlank()) {
            nome = curso.getNome();
        }
    
        int carga = cargaStr.isBlank()
                ? curso.getCargaHoraria()
                : Integer.parseInt(cargaStr);
    
        double valor = valorStr.isBlank()
                ? curso.getValorMensal()
                : Double.parseDouble(valorStr);
    
        cursoService.atualizar(id, nome, carga, valor);
    
        System.out.println("Curso atualizado com sucesso.");
    }

    private static void removerCurso() {
        int id = lerIdCurso();
        cursoService.remover(id);
        System.out.println("Curso removido com sucesso.");
    }

    private static void ativarCurso() {
        int id = lerIdCurso();
        cursoService.ativarCurso(id);
        System.out.println("Curso ativado com sucesso.");
    }

    private static void desativarCurso() {
        int id = lerIdCurso();
        cursoService.desativarCurso(id);
        System.out.println("Curso desativado com sucesso.");
    }

    private static void menuDisciplinasDoCurso() {
        tratarErro(() -> {
            int idCurso = lerIdCurso();
            cursoService.buscarPorId(idCurso);

            int opcao;
            do {
                System.out.println("\n--- Disciplinas do curso ID " + idCurso + " ---");
                System.out.println("1 - Cadastrar disciplina");
                System.out.println("2 - Listar disciplinas");
                System.out.println("3 - Atualizar disciplina");
                System.out.println("4 - Remover disciplina");
                System.out.println("5 - Ativar disciplina");
                System.out.println("6 - Desativar disciplina");
                System.out.println("0 - Voltar");
                System.out.print("Escolha uma opção: ");

                opcao = lerOpcao();

                switch (opcao) {
                    case 1:
                        tratarErro(() -> cadastrarDisciplina(idCurso));
                        break;
                    case 2:
                        tratarErro(() -> cursoService.listarDisciplinas(idCurso));
                        break;
                    case 3:
                        tratarErro(() -> atualizarDisciplina(idCurso));
                        break;
                    case 4:
                        tratarErro(() -> removerDisciplina(idCurso));
                        break;
                    case 5:
                        tratarErro(() -> ativarDisciplina(idCurso));
                        break;
                    case 6:
                        tratarErro(() -> desativarDisciplina(idCurso));
                        break;
                    case 0:
                        break;
                    default:
                        System.out.println("Opção inválida.");
                }
            } while (opcao != 0);
        });
    }

    // --- Disciplinas (menu global) ---

    private static void menuDisciplinas() {
        int opcao;

        do {
            System.out.println("\n===== GERENCIAR DISCIPLINAS =====");
            System.out.println("1 - Cadastrar disciplina em um curso");
            System.out.println("2 - Listar disciplinas de um curso");
            System.out.println("3 - Atualizar disciplina");
            System.out.println("4 - Remover disciplina");
            System.out.println("5 - Ativar disciplina");
            System.out.println("6 - Desativar disciplina");
            System.out.println("7 - Gerenciar turmas de uma disciplina");
            System.out.println("8 - Gerenciar professores aptos");
            System.out.println("0 - Voltar");
            System.out.print("Escolha uma opção: ");

            opcao = lerOpcao();

            switch (opcao) {
                case 1:
                    tratarErro(() -> cadastrarDisciplina(lerIdCurso()));
                    break;
                case 2:
                    tratarErro(() -> cursoService.listarDisciplinas(lerIdCurso()));
                    break;
                case 3:
                    tratarErro(() -> atualizarDisciplina(lerIdCurso()));
                    break;
                case 4:
                    tratarErro(() -> removerDisciplina(lerIdCurso()));
                    break;
                case 5:
                    tratarErro(() -> ativarDisciplina(lerIdCurso()));
                    break;
                case 6:
                    tratarErro(() -> desativarDisciplina(lerIdCurso()));
                    break;
                case 7:
                    menuTurmasDisciplina();
                    break;
                case 8:
                    menuProfessoresAptos();
                    break;
                case 0:
                    System.out.println("Voltando ao menu principal...");
                    break;
                default:
                    System.out.println("Opção inválida.");
            }

        } while (opcao != 0);
    }

    private static void cadastrarDisciplina(int idCurso) {
        String nome = lerLinha("Nome da disciplina: ");
        int carga = lerInteiro("Carga horária: ");
        String descricao = lerLinha("Descrição: ");
        Disciplina disciplina = cursoService.adicionarDisciplina(idCurso, nome, carga, descricao);
        System.out.println("Disciplina cadastrada com sucesso. ID gerado: " + disciplina.getId());
    }

    private static void atualizarDisciplina(int idCurso) {
        int idDisc = lerIdDisciplina(idCurso);
    
        Disciplina disciplina = cursoService.buscarDisciplina(idCurso, idDisc);
    
        System.out.println("Pressione ENTER para manter o valor atual.");
    
        String nome = lerLinha(
                "Novo nome (" + disciplina.getNome() + "): "
        );
    
        String cargaStr = lerLinha(
                "Nova carga horária (" + disciplina.getCargaHoraria() + "): "
        );
    
        String descricao = lerLinha(
                "Nova descrição (" + disciplina.getDescricao() + "): "
        );
    
        if (nome.isBlank()) {
            nome = disciplina.getNome();
        }
    
        int carga = cargaStr.isBlank()
                ? disciplina.getCargaHoraria()
                : Integer.parseInt(cargaStr);
    
        if (descricao.isBlank()) {
            descricao = disciplina.getDescricao();
        }
    
        cursoService.atualizarDisciplina(
                idCurso,
                idDisc,
                nome,
                carga,
                descricao
        );
    
        System.out.println("Disciplina atualizada com sucesso.");
    }

    private static void removerDisciplina(int idCurso) {
        int idDisc = lerIdDisciplina(idCurso);
        cursoService.removerDisciplina(idCurso, idDisc);
    }

    private static void ativarDisciplina(int idCurso) {
        int idDisc = lerIdDisciplina(idCurso);
        cursoService.ativarDisciplina(idCurso, idDisc);
        System.out.println("Disciplina ativada com sucesso.");
    }

    private static void desativarDisciplina(int idCurso) {
        int idDisc = lerIdDisciplina(idCurso);
        cursoService.desativarDisciplina(idCurso, idDisc);
        System.out.println("Disciplina desativada com sucesso.");
    }

    private static void menuTurmasDisciplina() {
        tratarErro(() -> {
            int idCurso = lerIdCurso();
            int idDisciplina = lerIdDisciplina(idCurso);
            turmaService.listarTurmas(idCurso, idDisciplina);
            menuTurmasComIds(idCurso, idDisciplina);
        });
    }

    private static void menuProfessoresAptos() {
        tratarErro(() -> {
            int idCurso = lerIdCurso();
            int idDisciplina = lerIdDisciplina(idCurso);
            int opcao;
            do {
                System.out.println("\n--- Professores aptos (curso " + idCurso + ", disciplina " + idDisciplina + ") ---");
                System.out.println("1 - Adicionar professor apto");
                System.out.println("2 - Listar professores aptos");
                System.out.println("3 - Remover professor apto");
                System.out.println("0 - Voltar");
                System.out.print("Escolha uma opção: ");
                opcao = lerOpcao();
                switch (opcao) {
                    case 1:
                        tratarErro(() -> turmaService.adicionarProfessorApto(
                                idCurso, idDisciplina, lerIdProfessor()));
                        break;
                    case 2:
                        tratarErro(() -> turmaService.listarProfessoresAptos(idCurso, idDisciplina));
                        break;
                    case 3:
                        tratarErro(() -> turmaService.removerProfessorApto(
                                idCurso, idDisciplina, lerIdProfessor()));
                        break;
                    case 0:
                        break;
                    default:
                        System.out.println("Opção inválida.");
                }
            } while (opcao != 0);
        });
    }

    private static void menuTurmas() {
        int opcao;

        do {
            System.out.println("\n===== GERENCIAR TURMAS =====");
            System.out.println("1 - Cadastrar turma em uma disciplina");
            System.out.println("2 - Listar turmas de uma disciplina");
            System.out.println("3 - Atualizar turma");
            System.out.println("4 - Remover turma");
            System.out.println("5 - Definir professor da turma");
            System.out.println("6 - Listar alunos da turma");
            System.out.println("7 - Ativar turma");
            System.out.println("8 - Desativar turma");
            System.out.println("0 - Voltar");
            System.out.print("Escolha uma opção: ");

            opcao = lerOpcao();

            switch (opcao) {
                case 1:
                    tratarErro(Main::cadastrarTurma);
                    break;
                case 2:
                    tratarErro(Main::listarTurmas);
                    break;
                case 3:
                    tratarErro(Main::atualizarTurma);
                    break;
                case 4:
                    tratarErro(Main::removerTurma);
                    break;
                case 5:
                    tratarErro(Main::definirProfessorTurma);
                    break;
                case 6:
                    tratarErro(Main::listarAlunosTurma);
                    break;
                case 7:
                    tratarErro(Main::ativarTurma);
                    break;
                case 8:
                    tratarErro(Main::desativarTurma);
                    break;
                case 0:
                    System.out.println("Voltando ao menu principal...");
                    break;
                default:
                    System.out.println("Opção inválida.");
            }

        } while (opcao != 0);
    }

    private static void menuTurmasComIds(int idCurso, int idDisciplina) {
        int opcao;
        do {
            System.out.println("\n--- Turmas da disciplina " + idDisciplina + " ---");
            System.out.println("1 - Cadastrar turma");
            System.out.println("2 - Listar turmas");
            System.out.println("3 - Atualizar turma");
            System.out.println("4 - Remover turma");
            System.out.println("0 - Voltar");
            System.out.print("Escolha uma opção: ");
            opcao = lerOpcao();
            switch (opcao) {
                case 1:
                    tratarErro(() -> cadastrarTurma(idCurso, idDisciplina));
                    break;
                case 2:
                    tratarErro(() -> turmaService.listarTurmas(idCurso, idDisciplina));
                    break;
                case 3:
                    tratarErro(() -> atualizarTurma(idCurso, idDisciplina));
                    break;
                case 4:
                    tratarErro(() -> removerTurma(idCurso, idDisciplina));
                    break;
                case 0:
                    break;
                default:
                    System.out.println("Opção inválida.");
            }
        } while (opcao != 0);
    }

    private static int lerIdProfessor() {
        exibirProfessoresResumo();
        return lerInteiro("ID do professor: ");
    }

    private static void cadastrarTurma() {
        int idCurso = lerIdCurso();
        int idDisciplina = lerIdDisciplina(idCurso);
        cadastrarTurma(idCurso, idDisciplina);
    }

    private static void cadastrarTurma(int idCurso, int idDisciplina) {
        String codigo = lerLinha("Código da turma: ");
        String turno = lerLinha("Turno: ");
        String sala = lerLinha("Sala: ");
        Turma turma = turmaService.cadastrar(idCurso, idDisciplina, codigo, turno, sala);
        System.out.println("Turma cadastrada com sucesso. ID gerado: " + turma.getId());
    }

    private static void listarTurmas() {
        int idCurso = lerIdCurso();
        int idDisciplina = lerIdDisciplina(idCurso);
        turmaService.listarTurmas(idCurso, idDisciplina);
    }

    private static void atualizarTurma() {
        int idCurso = lerIdCurso();
        int idDisciplina = lerIdDisciplina(idCurso);
        atualizarTurma(idCurso, idDisciplina);
    }

    private static void atualizarTurma(int idCurso, int idDisciplina) {
        int idTurma = lerIdTurma(idCurso, idDisciplina);
    
        Turma turma = turmaService.buscarPorId(
                idCurso,
                idDisciplina,
                idTurma
        );
    
        System.out.println("Pressione ENTER para manter o valor atual.");
    
        String turno = lerLinha(
                "Novo turno (" + turma.getTurno() + "): "
        );
    
        String sala = lerLinha(
                "Nova sala (" + turma.getSala() + "): "
        );
    
        if (turno.isBlank()) {
            turno = turma.getTurno();
        }
    
        if (sala.isBlank()) {
            sala = turma.getSala();
        }
    
        turmaService.atualizar(
                idCurso,
                idDisciplina,
                idTurma,
                turno,
                sala
        );
    
        System.out.println("Turma atualizada com sucesso.");
    }

    private static void removerTurma() {
        int idCurso = lerIdCurso();
        int idDisciplina = lerIdDisciplina(idCurso);
        removerTurma(idCurso, idDisciplina);
    }

    private static void removerTurma(int idCurso, int idDisciplina) {
        int idTurma = lerIdTurma(idCurso, idDisciplina);
        turmaService.remover(idCurso, idDisciplina, idTurma);
    }

    private static void definirProfessorTurma() {
        int idCurso = lerIdCurso();
        int idDisciplina = lerIdDisciplina(idCurso);
        int idTurma = lerIdTurma(idCurso, idDisciplina);
        turmaService.definirProfessor(idCurso, idDisciplina, idTurma, lerIdProfessor());
    }

    private static void listarAlunosTurma() {
        int idCurso = lerIdCurso();
        int idDisciplina = lerIdDisciplina(idCurso);
        int idTurma = lerIdTurma(idCurso, idDisciplina);
        turmaService.listarAlunos(idCurso, idDisciplina, idTurma);
    }

    private static void ativarTurma() {
        int idCurso = lerIdCurso();
        int idDisciplina = lerIdDisciplina(idCurso);
        int idTurma = lerIdTurma(idCurso, idDisciplina);
        turmaService.ativar(idCurso, idDisciplina, idTurma);
    }

    private static void desativarTurma() {
        int idCurso = lerIdCurso();
        int idDisciplina = lerIdDisciplina(idCurso);
        int idTurma = lerIdTurma(idCurso, idDisciplina);
        turmaService.desativar(idCurso, idDisciplina, idTurma);
    }

    private static void menuAlunos() {
        int opcao;
    
        do {
            System.out.println("\n===== GERENCIAR ALUNOS =====");
            System.out.println("1 - Cadastrar aluno");
            System.out.println("2 - Listar alunos");
            System.out.println("3 - Atualizar aluno");
            System.out.println("4 - Remover aluno");
            System.out.println("5 - Consultar matrículas do aluno");
            System.out.println("0 - Voltar");
            System.out.print("Escolha uma opção: ");
    
            opcao = lerOpcao();
    
            switch (opcao) {
                case 1:
                    tratarErro(Main::cadastrarAluno);
                    break;
                case 2:
                    listarAlunos();
                    break;
                case 3:
                    tratarErro(Main::atualizarAluno);
                    break;
                case 4:
                    tratarErro(Main::removerAluno);
                    break;
                case 5:
                    tratarErro(Main::consultarMatriculasAluno);
                    break;
                case 0:
                    System.out.println("Voltando ao menu principal...");
                    break;
                default:
                    System.out.println("Opção inválida.");
            }
    
        } while (opcao != 0);
    }

    private static void cadastrarAluno() {
        String nome = lerLinha("Nome: ");
        String email = lerLinha("E-mail: ");
        String cpf = lerLinha("CPF: ");
        String dataNasc = lerLinha("Data de nascimento: ");
        String matricula = lerLinha("Matrícula: ");
        Aluno aluno = alunoService.cadastrar(nome, email, cpf, dataNasc, matricula);
        System.out.println("Aluno cadastrado com sucesso. ID gerado: " + aluno.getId());
    }

    private static void listarAlunos() {
        List<Aluno> alunos = alunoService.listar();
        if (alunos.isEmpty()) {
            System.out.println("Nenhum aluno cadastrado.");
            return;
        }
        for (Aluno aluno : alunos) {
            aluno.exibirDados();
        }
    }

    private static void consultarMatriculasAluno() {
        int idAluno = lerInteiro("ID do aluno: ");
        List<Matricula> matriculas = matriculaService.listarPorAluno(idAluno);
        if (matriculas.isEmpty()) {
            System.out.println("Nenhuma matrícula encontrada para este aluno.");
            return;
        }
        for (Matricula matricula : matriculas) {
            System.out.println(matricula);
        }
    }

    private static void atualizarAluno() {
        int id = lerInteiro("ID do aluno que deseja atualizar: ");
    
        Aluno aluno = alunoService.buscarPorId(id);
    
        System.out.println("Pressione ENTER para manter o valor atual.");
    
        String nome = lerLinha("Novo nome (" + aluno.getNome() + "): ");
        String email = lerLinha("Novo e-mail (" + aluno.getEmail() + "): ");
        String cpf = lerLinha("Novo CPF (" + aluno.getCpf() + "): ");
        String dataNasc = lerLinha("Nova data de nascimento (" + aluno.getDataNascimento() + "): ");
        String matricula = lerLinha("Nova matrícula (" + aluno.getMatricula() + "): ");
    
        if (nome.isBlank()) {
            nome = aluno.getNome();
        }
    
        if (email.isBlank()) {
            email = aluno.getEmail();
        }
    
        if (cpf.isBlank()) {
            cpf = aluno.getCpf();
        }
    
        if (dataNasc.isBlank()) {
            dataNasc = aluno.getDataNascimento();
        }
    
        if (matricula.isBlank()) {
            matricula = aluno.getMatricula();
        }
    
        alunoService.atualizar(id, nome, email, cpf, dataNasc, matricula);
    
        System.out.println("Aluno atualizado com sucesso.");
    }
    
    private static void removerAluno() {
        int id = lerInteiro("ID do aluno que deseja remover: ");
    
        alunoService.remover(id);
    
        System.out.println("Aluno removido com sucesso.");
    }

    private static void menuProfessores() {
        int opcao;
    
        do {
            System.out.println("\n===== GERENCIAR PROFESSORES =====");
            System.out.println("1 - Cadastrar professor");
            System.out.println("2 - Listar professores");
            System.out.println("3 - Atualizar professor");
            System.out.println("4 - Remover professor");
            System.out.println("5 - Lançar nota");
            System.out.println("6 - Listar notas de uma turma");
            System.out.println("0 - Voltar");
            System.out.print("Escolha uma opção: ");
    
            opcao = lerOpcao();
    
            switch (opcao) {
                case 1:
                    tratarErro(Main::cadastrarProfessor);
                    break;
                case 2:
                    listarProfessores();
                    break;
                case 3:
                    tratarErro(Main::atualizarProfessor);
                    break;
                case 4:
                    tratarErro(Main::removerProfessor);
                    break;
                case 5:
                    tratarErro(Main::lancarNota);
                    break;
                case 6:
                    tratarErro(Main::listarNotasTurma);
                    break;
                case 0:
                    System.out.println("Voltando ao menu principal...");
                    break;
                default:
                    System.out.println("Opção inválida.");
            }
    
        } while (opcao != 0);
    }

    private static void cadastrarProfessor() {
        String nome = lerLinha("Nome: ");
        String email = lerLinha("E-mail: ");
        String cpf = lerLinha("CPF: ");
        String dataNasc = lerLinha("Data de nascimento: ");
        String especialidade = lerLinha("Especialidade: ");
        Professor professor = professorService.cadastrar(nome, email, cpf, dataNasc, especialidade);
        System.out.println("Professor cadastrado com sucesso. ID gerado: " + professor.getId());
    }

    private static void listarProfessores() {
        List<Professor> professores = professorService.listar();
        if (professores.isEmpty()) {
            System.out.println("Nenhum professor cadastrado.");
            return;
        }
        for (Professor professor : professores) {
            professor.exibirDados();
        }
    }

    private static void atualizarProfessor() {
        int id = lerInteiro("ID do professor que deseja atualizar: ");
    
        Professor professor = professorService.buscarPorId(id);
    
        System.out.println("Pressione ENTER para manter o valor atual.");
    
        String nome = lerLinha("Novo nome (" + professor.getNome() + "): ");
        String email = lerLinha("Novo e-mail (" + professor.getEmail() + "): ");
        String cpf = lerLinha("Novo CPF (" + professor.getCpf() + "): ");
        String dataNasc = lerLinha("Nova data de nascimento (" + professor.getDataNascimento() + "): ");
        String especialidade = lerLinha("Nova especialidade (" + professor.getEspecialidade() + "): ");
    
        if (nome.isBlank()) {
            nome = professor.getNome();
        }
    
        if (email.isBlank()) {
            email = professor.getEmail();
        }
    
        if (cpf.isBlank()) {
            cpf = professor.getCpf();
        }
    
        if (dataNasc.isBlank()) {
            dataNasc = professor.getDataNascimento();
        }
    
        if (especialidade.isBlank()) {
            especialidade = professor.getEspecialidade();
        }
    
        professorService.atualizar(
                id,
                nome,
                email,
                cpf,
                dataNasc,
                especialidade
        );
    
        System.out.println("Professor atualizado com sucesso.");
    }

    private static void removerProfessor() {
        int id = lerInteiro("ID do professor que deseja remover: ");
    
        professorService.remover(id);
    
        System.out.println("Professor removido com sucesso.");
    }

    private static void menuMatriculas() {
        int opcao;

        do {
            System.out.println("\n===== GERENCIAR MATRÍCULAS =====");
            System.out.println("1 - Realizar matrícula");
            System.out.println("2 - Confirmar matrícula");
            System.out.println("3 - Cancelar matrícula");
            System.out.println("4 - Consultar matrícula");
            System.out.println("0 - Voltar");
            System.out.print("Escolha uma opção: ");

            opcao = lerOpcao();

            switch (opcao) {
                case 1:
                    tratarErro(Main::realizarMatricula);
                    break;
                case 2:
                    tratarErro(Main::confirmarMatricula);
                    break;
                case 3:
                    tratarErro(Main::cancelarMatricula);
                    break;
                case 4:
                    tratarErro(Main::consultarMatricula);
                    break;
                case 0:
                    System.out.println("Voltando ao menu principal...");
                    break;
                default:
                    System.out.println("Opção inválida.");
            }

        } while (opcao != 0);
    }

    private static void realizarMatricula() {
        exibirAlunosResumo();
        int idAluno = lerInteiro("ID do aluno: ");
        int idCurso = lerIdCurso();
        int idDisciplina = lerIdDisciplina(idCurso);
        int idTurma = lerIdTurma(idCurso, idDisciplina);
        Matricula matricula = matriculaService.realizar(idAluno, idCurso, idDisciplina, idTurma);
        System.out.println("Matrícula realizada com sucesso. ID gerado: " + matricula.getId());
    }

    private static void confirmarMatricula() {
        exibirMatriculasResumo();
        matriculaService.confirmar(lerInteiro("ID da matrícula: "));
        System.out.println("Matrícula confirmada com sucesso.");
    }

    private static void cancelarMatricula() {
        exibirMatriculasResumo();
        matriculaService.cancelar(lerInteiro("ID da matrícula: "));
        System.out.println("Matrícula cancelada com sucesso.");
    }

    private static void consultarMatricula() {
        exibirMatriculasResumo();
        Matricula matricula = matriculaService.buscarPorId(lerInteiro("ID da matrícula: "));
        System.out.println(matricula);
    }

    private static void lancarNota() {
        int idProfessor = lerIdProfessor();
        exibirMatriculasResumo();
        int idMatricula = lerInteiro("ID da matrícula: ");
        double nota = lerDouble("Nota (0 a 10): ");

        Professor professor = professorService.buscarPorId(idProfessor);
        Matricula matricula = matriculaService.buscarPorId(idMatricula);
        Turma turma = matricula.getTurma();

        validarProfessorResponsavel(professor, turma);
        validarMatriculaPodeReceberNota(matricula);

        try {
            professor.lancarNota(matricula, nota);
        } catch (Exception e) {
            throw new IllegalArgumentException(e.getMessage());
        }

        System.out.println("Nota lançada com sucesso.");
    }

    private static void listarNotasTurma() {
        int idProfessor = lerIdProfessor();
        int idCurso = lerIdCurso();
        int idDisciplina = lerIdDisciplina(idCurso);
        int idTurma = lerIdTurma(idCurso, idDisciplina);

        Professor professor = professorService.buscarPorId(idProfessor);
        Turma turma = turmaService.buscarPorId(idCurso, idDisciplina, idTurma);

        validarProfessorResponsavel(professor, turma);

        List<Matricula> matriculas = professor.listarNotas(turma);
        if (matriculas.isEmpty()) {
            System.out.println("Nenhuma matrícula nesta turma.");
            return;
        }

        for (Matricula matricula : matriculas) {
            System.out.println(matricula);
        }
    }

    private static void validarProfessorResponsavel(Professor professor, Turma turma) {
        if (turma.getProfessor() == null) {
            throw new IllegalArgumentException("A turma ainda não possui professor definido.");
        }

        if (turma.getProfessor().getId() != professor.getId()) {
            throw new IllegalArgumentException("Este professor não é responsável por esta turma.");
        }
    }

    private static void validarMatriculaPodeReceberNota(Matricula matricula) {
        if (Matricula.STATUS_CANCELADA.equals(matricula.getStatus())) {
            throw new IllegalArgumentException("Não é possível lançar nota em matrícula cancelada.");
        }

        if (!Matricula.STATUS_CONFIRMADA.equals(matricula.getStatus())) {
            throw new IllegalArgumentException("Matrícula precisa estar confirmada para lançar nota.");
        }
    }
}
