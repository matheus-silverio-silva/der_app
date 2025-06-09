package com.example.app;

import com.example.app.entity.Aluno;
import com.example.app.entity.Curso;
import com.example.app.entity.Matricula;
import com.example.app.service.AlunoService;
import com.example.app.service.CursoService;
import com.example.app.service.MatriculaService;

import java.util.List;
import java.util.Scanner;

public class MainApp {
    private static final Scanner scanner = new Scanner(System.in);
    private static final AlunoService alunoService = new AlunoService();
    private static final CursoService cursoService = new CursoService();
    private static final MatriculaService matriculaService = new MatriculaService();

    public static void main(String[] args) {
        int opc;
        do {
            mostrarMenu();
            try {
                opc = Integer.parseInt(scanner.nextLine());
            } catch (Exception e) {
                opc = -1;
            }
            try {
                switch (opc) {
                    case 1 -> cadastrarAluno();
                    case 2 -> cadastrarCurso();
                    case 3 -> matricular();
                    case 4 -> cancelarMatricula();
                    case 5 -> excluirAluno();
                    case 6 -> excluirCurso();
                    case 7 -> listarAlunos();
                    case 8 -> listarCursosComAlunos();
                    case 0 -> System.out.println("Saindo...");
                    default -> System.out.println("Opção inválida.");
                }
            } catch (Exception e) {
                System.err.println("Erro: " + e.getMessage());
            }
        } while (opc != 0);
        System.exit(0);
    }

    private static void mostrarMenu() {
        System.out.println("\n=== Menu ===");
        System.out.println("1- Cadastrar aluno");
        System.out.println("2- Cadastrar curso");
        System.out.println("3- Matricular aluno no curso");
        System.out.println("4- Cancelar matrícula do aluno no curso");
        System.out.println("5- Excluir aluno");
        System.out.println("6- Excluir curso");
        System.out.println("7- Listar todos os alunos");
        System.out.println("8- Listar todos os cursos e seus respectivos alunos");
        System.out.println("0- Sair");
        System.out.print("Escolha uma opção: ");
    }

    private static void cadastrarAluno() {
        System.out.print("Nome: ");
        String nome = scanner.nextLine();
        System.out.print("CPF: ");
        String cpf = scanner.nextLine();
        try {
            int id = alunoService.cadastrar(nome, cpf);
            System.out.println("Aluno cadastrado com ID: " + id);
        } catch (Exception e) {
            System.out.println("Erro ao cadastrar aluno: " + e.getMessage());
        }
    }

    private static void cadastrarCurso() {
        System.out.print("Nome do curso: ");
        String nome = scanner.nextLine();
        try {
            int id = cursoService.cadastrar(nome);
            System.out.println("Curso cadastrado com ID: " + id);
        } catch (Exception e) {
            System.out.println("Erro ao cadastrar curso: " + e.getMessage());
        }
    }

    private static void matricular() {
        System.out.print("ID do aluno: ");
        int idA = Integer.parseInt(scanner.nextLine());
        System.out.print("ID do curso: ");
        int idC = Integer.parseInt(scanner.nextLine());
        try {
            matriculaService.matricular(idA, idC);
            System.out.println("Matrícula realizada com sucesso.");
        } catch (Exception e) {
            System.out.println("Erro ao matricular aluno: " + e.getMessage());
        }
    }

    private static void cancelarMatricula() {
        System.out.print("ID do aluno: ");
        int idA = Integer.parseInt(scanner.nextLine());
        System.out.print("ID do curso: ");
        int idC = Integer.parseInt(scanner.nextLine());
        try {
            matriculaService.cancelarMatricula(idA, idC);
            System.out.println("Matrícula cancelada com sucesso.");
        } catch (Exception e) {
            System.out.println("Erro ao cancelar matricula: " + e.getMessage());
        }
    }

    private static void excluirAluno() {
        System.out.print("ID do aluno: ");
        int id = Integer.parseInt(scanner.nextLine());

        Aluno aluno = alunoService.buscarPorId(id);
        if (aluno == null) {
            System.out.println("Aluno não encontrado.");
            return;
        }

        if (!aluno.getMatriculas().isEmpty()) {
            System.out.println("Este aluno está matriculado em cursos.");
            System.out.print("Deseja realmente excluir? Isso removerá todas as matrículas. (s/n): ");
            String confirmacao = scanner.nextLine().trim().toLowerCase();
            if (!confirmacao.equals("s")) {
                System.out.println("Operação cancelada.");
                return;
            }
        }

        alunoService.excluir(id);
        System.out.println("Aluno excluído.");
    }

    private static void excluirCurso() {
        System.out.print("ID do curso: ");
        int id = Integer.parseInt(scanner.nextLine());

        Curso curso = cursoService.buscarPorId(id);
        if (curso == null) {
            System.out.println("Curso não encontrado.");
            return;
        }

        if (!curso.getMatriculas().isEmpty()) {
            System.out.println("Há alunos matriculados neste curso.");
            System.out.print("Deseja realmente excluir? Isso removerá todas as matrículas. (s/n): ");
            String confirmacao = scanner.nextLine().trim().toLowerCase();
            if (!confirmacao.equals("s")) {
                System.out.println("Operação cancelada.");
                return;
            }
        }

        cursoService.excluir(id);
        System.out.println("Curso excluído.");
    }

    private static void listarAlunos() {
        List<Aluno> lista = alunoService.listarTodos();
        if (lista.isEmpty()) {
            System.out.println("Não possuem alunos cadastrados.");
        } else {
            System.out.println("\n--- Alunos ---");
            for (Aluno a : lista) {
                System.out.println(a);
            }
        }
    }

    private static void listarCursosComAlunos() {
        List<Curso> listaCursos = cursoService.listarTodos();
        if (listaCursos.isEmpty()) {
            System.out.println("Não possuem cursos cadastrados cadastrados.");
        } else {
            System.out.println("\n--- Cursos e Alunos Matriculados ---");
            for (Curso c : listaCursos) {
                System.out.println(c);
                if (c.getMatriculas().isEmpty()) {
                    System.out.println("\tNenhum aluno matriculado nesse curso.");
                } else {
                    for (Matricula m : c.getMatriculas()) {
                        Aluno a = m.getAluno();
                        System.out.println("\t" + a);
                    }
                }
            }
        }
    }
}