package main;

import java.util.Scanner;

public class Main {

    private static final Scanner scanner = new Scanner(System.in);

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
            return Integer.parseInt(scanner.nextLine());
        } catch (NumberFormatException e) {
            System.out.println("Erro: digite apenas números.");
            return -1;
        }
    }

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
                    System.out.println("Função cadastrar curso em desenvolvimento.");
                    break;
                case 2:
                    System.out.println("Função listar cursos em desenvolvimento.");
                    break;
                case 3:
                    System.out.println("Função atualizar curso em desenvolvimento.");
                    break;
                case 4:
                    System.out.println("Função remover curso em desenvolvimento.");
                    break;
                case 5:
                    System.out.println("Função gerenciar disciplinas de um curso em desenvolvimento.");
                    break;
                case 0:
                    System.out.println("Voltando ao menu principal...");
                    break;
                default:
                    System.out.println("Opção inválida.");
            }

        } while (opcao != 0);
    }

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
                    System.out.println("Função cadastrar disciplina em um curso em desenvolvimento.");
                    break;
                case 2:
                    System.out.println("Função listar disciplinas de um curso em desenvolvimento.");
                    break;
                case 3:
                    System.out.println("Função atualizar disciplina em desenvolvimento.");
                    break;
                case 4:
                    System.out.println("Função remover disciplina em desenvolvimento.");
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