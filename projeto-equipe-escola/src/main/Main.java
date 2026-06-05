package main;

import model.Curso;
import repository.CursoRepository;
import service.CursoService;

import java.util.List;
import java.util.Scanner;

public class Main {

    private static final Scanner scanner = new Scanner(System.in);
    private static final CursoService cursoService =
            new CursoService(new CursoRepository());

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
                    System.out.println("Função gerenciar turmas de uma disciplina em desenvolvimento.");
                    break;
                case 6:
                    System.out.println("Função gerenciar professores aptos em desenvolvimento.");
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

    // --- Demais menus (ainda em desenvolvimento) ---

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
                    System.out.println("Função cadastrar turma em uma disciplina em desenvolvimento.");
                    break;
                case 2:
                    System.out.println("Função listar turmas de uma disciplina em desenvolvimento.");
                    break;
                case 3:
                    System.out.println("Função atualizar turma em desenvolvimento.");
                    break;
                case 4:
                    System.out.println("Função remover turma em desenvolvimento.");
                    break;
                case 5:
                    System.out.println("Função definir professor da turma em desenvolvimento.");
                    break;
                case 6:
                    System.out.println("Função listar alunos da turma em desenvolvimento.");
                    break;
                case 7:
                    System.out.println("Função ativar turma em desenvolvimento.");
                    break;
                case 8:
                    System.out.println("Função desativar turma em desenvolvimento.");
                    break;
                case 0:
                    System.out.println("Voltando ao menu principal...");
                    break;
                default:
                    System.out.println("Opção inválida.");
            }

        } while (opcao != 0);
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
                    System.out.println("Função cadastrar aluno em desenvolvimento.");
                    break;
                case 2:
                    System.out.println("Função listar alunos em desenvolvimento.");
                    break;
                case 3:
                    System.out.println("Função consultar matrículas do aluno em desenvolvimento.");
                    break;
                case 0:
                    System.out.println("Voltando ao menu principal...");
                    break;
                default:
                    System.out.println("Opção inválida.");
            }

        } while (opcao != 0);
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
                    System.out.println("Função cadastrar professor em desenvolvimento.");
                    break;
                case 2:
                    System.out.println("Função listar professores em desenvolvimento.");
                    break;
                case 0:
                    System.out.println("Voltando ao menu principal...");
                    break;
                default:
                    System.out.println("Opção inválida.");
            }

        } while (opcao != 0);
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
                    System.out.println("Função realizar matrícula em desenvolvimento.");
                    break;
                case 2:
                    System.out.println("Função confirmar matrícula em desenvolvimento.");
                    break;
                case 3:
                    System.out.println("Função cancelar matrícula em desenvolvimento.");
                    break;
                case 4:
                    System.out.println("Função consultar matrícula em desenvolvimento.");
                    break;
                case 0:
                    System.out.println("Voltando ao menu principal...");
                    break;
                default:
                    System.out.println("Opção inválida.");
            }

        } while (opcao != 0);
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
                    System.out.println("Função lançar nota em desenvolvimento.");
                    break;
                case 2:
                    System.out.println("Função listar notas de uma turma em desenvolvimento.");
                    break;
                case 0:
                    System.out.println("Voltando ao menu principal...");
                    break;
                default:
                    System.out.println("Opção inválida.");
            }

        } while (opcao != 0);
    }
}
