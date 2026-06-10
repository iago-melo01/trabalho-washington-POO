package main;

import model.Aluno;
import model.Curso;
import model.Matricula;
import model.Professor;
import repository.AlunoRepository;
import repository.CursoRepository;
import repository.MatriculaRepository;
import repository.ProfessorRepository;
import service.AlunoService;
import service.CursoService;
import service.MatriculaService;
import service.NotaService;
import service.ProfessorService;
import service.TurmaService;

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
    private static final NotaService notaService = new NotaService(matriculaService);

    public static void main(String[] args) {
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
                    menuNotas();
                    break;
                case 0:
                    System.out.println("Encerrando o sistema...");
                    break;
                default:
                    System.out.println("Opção inválida.");
            }

        } while (opcao != 0);

        scanner.close();
    }

    private static void exibirMenuPrincipal() {
        System.out.println("\n===== SISTEMA ESCOLA/CURSO =====");
        System.out.println("1 - Gerenciar cursos");
        System.out.println("2 - Gerenciar disciplinas");
        System.out.println("3 - Gerenciar turmas");
        System.out.println("4 - Gerenciar alunos");
        System.out.println("5 - Gerenciar professores");
        System.out.println("6 - Gerenciar matrículas");
        System.out.println("7 - Gerenciar notas");
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

    private static int lerIdCurso() {
        return lerInteiro("ID do curso: ");
    }

    private static int lerIdDisciplina() {
        return lerInteiro("ID da disciplina: ");
    }

    private static int lerIdTurma() {
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
            System.out.println("5 - Gerenciar disciplinas de um curso");
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
        int id = lerInteiro("ID do curso: ");
        String nome = lerLinha("Nome do curso: ");
        int carga = lerInteiro("Carga horária: ");
        double valor = lerDouble("Valor mensal (R$): ");
        cursoService.cadastrar(id, nome, carga, valor);
        System.out.println("Curso cadastrado com sucesso.");
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
        String nome = lerLinha("Novo nome: ");
        int carga = lerInteiro("Nova carga horária: ");
        double valor = lerDouble("Novo valor mensal (R$): ");
        cursoService.atualizar(id, nome, carga, valor);
        System.out.println("Curso atualizado com sucesso.");
    }

    private static void removerCurso() {
        int id = lerIdCurso();
        cursoService.remover(id);
        System.out.println("Curso removido com sucesso.");
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
            System.out.println("5 - Gerenciar turmas de uma disciplina");
            System.out.println("6 - Gerenciar professores aptos");
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
                    menuTurmasDisciplina();
                    break;
                case 6:
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
        int idDisc = lerInteiro("ID da disciplina: ");
        String nome = lerLinha("Nome da disciplina: ");
        int carga = lerInteiro("Carga horária: ");
        String descricao = lerLinha("Descrição: ");
        cursoService.adicionarDisciplina(idCurso, idDisc, nome, carga, descricao);
        System.out.println("Disciplina cadastrada com sucesso.");
    }

    private static void atualizarDisciplina(int idCurso) {
        int idDisc = lerInteiro("ID da disciplina: ");
        System.out.println("1 - Nome | 2 - Carga horária | 3 - Descrição");
        int campo = lerOpcao();
        switch (campo) {
            case 1:
                cursoService.atualizarNomeDisciplina(idCurso, idDisc, lerLinha("Novo nome: "));
                break;
            case 2:
                cursoService.atualizarCargaHorariaDisciplina(idCurso, idDisc, lerInteiro("Nova carga horária: "));
                break;
            case 3:
                cursoService.atualizarDescricaoDisciplina(idCurso, idDisc, lerLinha("Nova descrição: "));
                break;
            default:
                throw new IllegalArgumentException("Campo inválido para atualização.");
        }
        System.out.println("Disciplina atualizada com sucesso.");
    }

    private static void removerDisciplina(int idCurso) {
        int idDisc = lerInteiro("ID da disciplina: ");
        cursoService.removerDisciplina(idCurso, idDisc);
    }

    private static void menuTurmasDisciplina() {
        tratarErro(() -> {
            int idCurso = lerIdCurso();
            int idDisciplina = lerIdDisciplina();
            turmaService.listarTurmas(idCurso, idDisciplina);
            menuTurmasComIds(idCurso, idDisciplina);
        });
    }

    private static void menuProfessoresAptos() {
        tratarErro(() -> {
            int idCurso = lerIdCurso();
            int idDisciplina = lerIdDisciplina();
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
                                idCurso, idDisciplina, lerInteiro("ID do professor: ")));
                        break;
                    case 2:
                        tratarErro(() -> turmaService.listarProfessoresAptos(idCurso, idDisciplina));
                        break;
                    case 3:
                        tratarErro(() -> turmaService.removerProfessorApto(
                                idCurso, idDisciplina, lerInteiro("ID do professor: ")));
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

    private static void cadastrarTurma() {
        int idCurso = lerIdCurso();
        int idDisciplina = lerIdDisciplina();
        cadastrarTurma(idCurso, idDisciplina);
    }

    private static void cadastrarTurma(int idCurso, int idDisciplina) {
        int idTurma = lerIdTurma();
        String codigo = lerLinha("Código da turma: ");
        String turno = lerLinha("Turno: ");
        String sala = lerLinha("Sala: ");
        turmaService.cadastrar(idCurso, idDisciplina, idTurma, codigo, turno, sala);
        System.out.println("Turma cadastrada com sucesso.");
    }

    private static void listarTurmas() {
        turmaService.listarTurmas(lerIdCurso(), lerIdDisciplina());
    }

    private static void atualizarTurma() {
        int idCurso = lerIdCurso();
        int idDisciplina = lerIdDisciplina();
        atualizarTurma(idCurso, idDisciplina);
    }

    private static void atualizarTurma(int idCurso, int idDisciplina) {
        int idTurma = lerIdTurma();
        String turno = lerLinha("Novo turno: ");
        String sala = lerLinha("Nova sala: ");
        turmaService.atualizar(idCurso, idDisciplina, idTurma, turno, sala);
    }

    private static void removerTurma() {
        int idCurso = lerIdCurso();
        int idDisciplina = lerIdDisciplina();
        removerTurma(idCurso, idDisciplina);
    }

    private static void removerTurma(int idCurso, int idDisciplina) {
        turmaService.remover(idCurso, idDisciplina, lerIdTurma());
    }

    private static void definirProfessorTurma() {
        turmaService.definirProfessor(
                lerIdCurso(), lerIdDisciplina(), lerIdTurma(), lerInteiro("ID do professor: "));
    }

    private static void listarAlunosTurma() {
        turmaService.listarAlunos(lerIdCurso(), lerIdDisciplina(), lerIdTurma());
    }

    private static void ativarTurma() {
        turmaService.ativar(lerIdCurso(), lerIdDisciplina(), lerIdTurma());
    }

    private static void desativarTurma() {
        turmaService.desativar(lerIdCurso(), lerIdDisciplina(), lerIdTurma());
    }

    private static void menuAlunos() {
        int opcao;

        do {
            System.out.println("\n===== GERENCIAR ALUNOS =====");
            System.out.println("1 - Cadastrar aluno");
            System.out.println("2 - Listar alunos");
            System.out.println("3 - Consultar matrículas do aluno");
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
        int id = lerInteiro("ID do aluno: ");
        String nome = lerLinha("Nome: ");
        String email = lerLinha("E-mail: ");
        String cpf = lerLinha("CPF: ");
        String dataNasc = lerLinha("Data de nascimento: ");
        String matricula = lerLinha("Matrícula: ");
        alunoService.cadastrar(id, nome, email, cpf, dataNasc, matricula);
        System.out.println("Aluno cadastrado com sucesso.");
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

    private static void menuProfessores() {
        int opcao;

        do {
            System.out.println("\n===== GERENCIAR PROFESSORES =====");
            System.out.println("1 - Cadastrar professor");
            System.out.println("2 - Listar professores");
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
                case 0:
                    System.out.println("Voltando ao menu principal...");
                    break;
                default:
                    System.out.println("Opção inválida.");
            }

        } while (opcao != 0);
    }

    private static void cadastrarProfessor() {
        int id = lerInteiro("ID do professor: ");
        String nome = lerLinha("Nome: ");
        String email = lerLinha("E-mail: ");
        String cpf = lerLinha("CPF: ");
        String dataNasc = lerLinha("Data de nascimento: ");
        String especialidade = lerLinha("Especialidade: ");
        professorService.cadastrar(id, nome, email, cpf, dataNasc, especialidade);
        System.out.println("Professor cadastrado com sucesso.");
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
        Matricula matricula = matriculaService.realizar(
                lerInteiro("ID do aluno: "),
                lerIdCurso(),
                lerIdDisciplina(),
                lerIdTurma());
        System.out.println("Matrícula realizada com sucesso. ID: " + matricula.getId());
    }

    private static void confirmarMatricula() {
        matriculaService.confirmar(lerInteiro("ID da matrícula: "));
        System.out.println("Matrícula confirmada com sucesso.");
    }

    private static void cancelarMatricula() {
        matriculaService.cancelar(lerInteiro("ID da matrícula: "));
        System.out.println("Matrícula cancelada com sucesso.");
    }

    private static void consultarMatricula() {
        Matricula matricula = matriculaService.buscarPorId(lerInteiro("ID da matrícula: "));
        System.out.println(matricula);
    }

    private static void menuNotas() {
        int opcao;

        do {
            System.out.println("\n===== GERENCIAR NOTAS =====");
            System.out.println("1 - Lançar nota");
            System.out.println("2 - Listar notas de uma turma");
            System.out.println("0 - Voltar");
            System.out.print("Escolha uma opção: ");

            opcao = lerOpcao();

            switch (opcao) {
                case 1:
                    tratarErro(Main::lancarNota);
                    break;
                case 2:
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

    private static void lancarNota() {
        notaService.lancar(lerInteiro("ID da matrícula: "), lerDouble("Nota (0 a 10): "));
        System.out.println("Nota lançada com sucesso.");
    }

    private static void listarNotasTurma() {
        notaService.listarPorTurma(lerIdCurso(), lerIdDisciplina(), lerIdTurma());
    }
}
