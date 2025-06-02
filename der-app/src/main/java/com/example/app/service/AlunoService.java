package com.example.app.service;

import com.example.app.dao.GenericDAO;
import com.example.app.entity.Aluno;

import javax.persistence.NoResultException;
import javax.persistence.TypedQuery;
import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import javax.persistence.Persistence;
import java.util.List;
import org.hibernate.Session;
import com.example.app.util.HibernateUtil;
import com.example.app.entity.Aluno;

public class AlunoService {
    private GenericDAO<Aluno, Integer> dao = new GenericDAO<>(Aluno.class);

    public Aluno buscarPorId(int id) {
        return dao.pesquisaId(id);
    }

    public Integer cadastrar(String nome, String cpf) throws Exception {
        // Validação de nome
        if (nome == null) {
            throw new Exception("Nome do aluno inválido: nulo");
        }
        if (nome.trim().isEmpty()) {
            throw new Exception("Nome do aluno inválido: vazio");
        }
        if (nome.length() > 100) {
            throw new Exception("Nome do aluno inválido: tamanho excedido (máximo 100 caracteres)");
        }

        // Validação de CPF
        if (cpf == null) {
            throw new Exception("CPF do aluno inválido: nulo");
        }
        if (cpf.trim().isEmpty()) {
            throw new Exception("CPF do aluno inválido: vazio");
        }
        if (!cpf.matches("\\d{11}")) {
            throw new Exception("CPF do aluno inválido: deve conter exatamente 11 dígitos numéricos");
        }
        if (cpfExistente(cpf)) {
            throw new Exception("CPF do aluno já existe");
        }

        Aluno a = new Aluno();
        a.setNome(nome.trim());
        a.setCpf(cpf);
        return dao.inserir(a);
    }
    public void atualizar(int id, String nome, String cpf) throws Exception {
        Aluno aExistente = dao.pesquisaId(id);
        if (aExistente == null) {
            throw new Exception("Aluno não encontrado para id: " + id);
        }
        // Validação de nome
        if (nome == null) {
            throw new Exception("Nome do aluno inválido: nulo");
        }
        if (nome.trim().isEmpty()) {
            throw new Exception("Nome do aluno inválido: vazio");
        }
        if (nome.length() > 100) {
            throw new Exception("Nome do aluno inválido: tamanho excedido (máximo 100 caracteres)");
        }
        // Validação de CPF
        if (cpf == null) {
            throw new Exception("CPF do aluno inválido: nulo");
        }
        if (cpf.trim().isEmpty()) {
            throw new Exception("CPF do aluno inválido: vazio");
        }
        if (!cpf.matches("\\d{11}")) {
            throw new Exception("CPF do aluno inválido: deve conter exatamente 11 dígitos numéricos");
        }
        boolean cpfAlterado = !aExistente.getCpf().equals(cpf);
        if (cpfAlterado && cpfExistente(cpf)) {
            throw new Exception("CPF do aluno já existe em outro registro");
        }
        aExistente.setNome(nome.trim());
        aExistente.setCpf(cpf);
        dao.update(aExistente);
    }
    public void excluir(int id) {
        Aluno a = dao.pesquisaId(id);
        if (a != null) {
            dao.excluir(a);
        }
    }
    public List<Aluno> listarTodos() {
        return dao.retornaTodos();
    }

    private boolean cpfExistente(String cpf) {
        Session session = HibernateUtil.getSessionFactory().openSession();
        try {
            Aluno resultado = session.createQuery(
                            "FROM Aluno a WHERE a.cpf = :cpf", Aluno.class)
                    .setParameter("cpf", cpf)
                    .uniqueResult();
            return resultado != null;
        } catch (Exception e) {
            return false;
        } finally {
            session.close();
        }
    }
}