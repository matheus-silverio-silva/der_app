package com.example.app.service;

import com.example.app.dao.GenericDAO;
import com.example.app.entity.Aluno;
import com.example.app.entity.Curso;
import com.example.app.entity.Matricula;

import java.time.LocalDate;
import java.util.List;

public class MatriculaService {

    private final GenericDAO<Aluno, Integer> alunoDao = new GenericDAO<>(Aluno.class);
    private final GenericDAO<Curso, Integer> cursoDao = new GenericDAO<>(Curso.class);
    private final GenericDAO<Matricula, Long> matriculaDao = new GenericDAO<>(Matricula.class);

    public void matricular(int idAluno, int idCurso) throws Exception {
        Aluno aluno = alunoDao.pesquisaId(idAluno);
        Curso curso = cursoDao.pesquisaId(idCurso);

        if (aluno == null || curso == null) {
            throw new Exception("Aluno ou curso não encontrado.");
        }

        boolean jaMatriculado = aluno.getMatriculas().stream()
                .anyMatch(m -> m.getCurso().getId().equals(idCurso));

        if (jaMatriculado) {
            throw new Exception("Aluno já está matriculado neste curso.");
        }

        Matricula matricula = new Matricula(aluno, curso, LocalDate.now());
        aluno.addMatricula(matricula);
        curso.addMatricula(matricula);

        matriculaDao.inserir(matricula);
    }

    public void cancelarMatricula(int idAluno, int idCurso) throws Exception {
        Aluno aluno = alunoDao.pesquisaId(idAluno);
        if (aluno == null) {
            throw new Exception("Aluno não encontrado.");
        }

        Matricula matricula = aluno.getMatriculas().stream()
                .filter(m -> m.getCurso().getId().equals(idCurso))
                .findFirst()
                .orElse(null);

        if (matricula == null) {
            throw new Exception("Matrícula não encontrada para o curso informado.");
        }

        Curso curso = matricula.getCurso();
        aluno.getMatriculas().remove(matricula);
        curso.getMatriculas().remove(matricula);

        alunoDao.update(aluno);
        cursoDao.update(curso);
    }

    public List<Matricula> listarTodas() throws Exception {
        return matriculaDao.retornaTodos();
    }
}