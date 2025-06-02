package com.example.app.service;

import com.example.app.dao.GenericDAO;
import com.example.app.entity.Curso;
import com.example.app.util.HibernateUtil;

import javax.persistence.NoResultException;
import javax.persistence.TypedQuery;
import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import javax.persistence.Persistence;
import java.util.List;

import org.hibernate.Session;
import com.example.app.util.HibernateUtil;
import com.example.app.entity.Curso;

public class CursoService {
    private GenericDAO<Curso, Integer> dao = new GenericDAO<>(Curso.class);

    public Curso buscarPorId(int id) {
        return dao.pesquisaId(id);
    }

    public Integer cadastrar(String nome) throws Exception {
        // Validação de nome
        if (nome == null) {
            throw new Exception("Nome do curso inválido: nulo");
        }
        if (nome.trim().isEmpty()) {
            throw new Exception("Nome do curso inválido: vazio");
        }
        if (nome.length() > 50) {
            throw new Exception("Nome do curso inválido: tamanho excedido (máximo 50 caracteres)");
        }
        // Verifica duplicidade de nome
        if (nomeExistente(nome)) {
            throw new Exception("Já existe um curso com este nome");
        }
        Curso c = new Curso();
        c.setNome(nome.trim());
        return dao.inserir(c);
    }
    public void atualizar(int id, String nome) throws Exception {
        Curso cExistente = dao.pesquisaId(id);
        if (cExistente == null) {
            throw new Exception("Curso não encontrado para id: " + id);
        }

        // Validação de nome
        if (nome == null) {
            throw new Exception("Nome do curso inválido: nulo");
        }
        if (nome.trim().isEmpty()) {
            throw new Exception("Nome do curso inválido: vazio");
        }
        if (nome.length() > 50) {
            throw new Exception("Nome do curso inválido: tamanho excedido (máximo 50 caracteres)");
        }
        boolean nomeAlterado = !cExistente.getNome().equalsIgnoreCase(nome.trim());
        if (nomeAlterado && nomeExistente(nome)) {
            throw new Exception("Já existe outro curso com este nome");
        }
        cExistente.setNome(nome.trim());
        dao.update(cExistente);
    }
    public void excluir(int id) {
        Curso c = dao.pesquisaId(id);
        if (c != null) {
            dao.excluir(c);
        }
    }
    public List<Curso> listarTodos() {
        return dao.retornaTodos();
    }

    private boolean nomeExistente(String nome) {
        Session session = HibernateUtil.getSessionFactory().openSession();
        try {
            Curso resultado = session.createQuery(
                            "FROM Curso c WHERE LOWER(c.nome) = :nome", Curso.class)
                    .setParameter("nome", nome.trim().toLowerCase())
                    .uniqueResult();
            return resultado != null;
        } catch (Exception e) {
            return false;
        } finally {
            session.close();
        }
    }
}