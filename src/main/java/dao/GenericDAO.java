package dao;

import utils.JPAUtil;
import jakarta.persistence.EntityManager;
import jakarta.persistence.EntityTransaction;
import jakarta.persistence.TypedQuery;
import java.util.List;
import java.util.function.Consumer;

/**
 * DAO genérico com operações CRUD básicas.
 * @param <T> O tipo da entidade.
 */
public abstract class GenericDAO<T> {

    private final Class<T> classe;

    public GenericDAO(Class<T> classe) {
        this.classe = classe;
    }

    /**
     * Salva uma nova entidade no banco de dados.
     */
    public void criar(T entidade) {
        executarEmTransacao(em -> em.persist(entidade));
    }

    /**
     * Atualiza uma entidade existente no banco de dados.
     */
    public T atualizar(T entidade) {
        EntityManager em = JPAUtil.getEntityManager();
        EntityTransaction tx = em.getTransaction();
        try {
            tx.begin();
            T mergedEntity = em.merge(entidade);
            tx.commit();
            return mergedEntity;
        } catch (Exception e) {
            if (tx.isActive()) tx.rollback();
            throw new RuntimeException("Erro ao atualizar entidade: " + e.getMessage(), e);
        } finally {
            em.close();
        }
    }

    /**
     * Remove uma entidade do banco de dados pelo seu ID.
     */
    public void deletar(Object id) {
        executarEmTransacao(em -> {
            T entidade = em.find(classe, id);
            if (entidade != null) {
                em.remove(entidade);
            }
        });
    }

    /**
     * Busca uma entidade pelo seu ID.
     */
    public T encontrarPorId(Object id) {
        EntityManager em = JPAUtil.getEntityManager();
        try {
            return em.find(classe, id);
        } finally {
            em.close();
        }
    }

    /**
     * Retorna uma lista com todas as entidades do tipo T.
     */
    public List<T> listarTodos() {
        EntityManager em = JPAUtil.getEntityManager();
        try {
            String jpql = "SELECT e FROM " + classe.getSimpleName() + " e";
            TypedQuery<T> query = em.createQuery(jpql, classe);
            return query.getResultList();
        } finally {
            em.close();
        }
    }

    /**
     * Executa uma ação dentro de uma transação.
     * Garante que a transação seja iniciada, commitada ou revertida em caso de erro.
     */
    private void executarEmTransacao(Consumer<EntityManager> acao) {
        EntityManager em = JPAUtil.getEntityManager();
        EntityTransaction tx = em.getTransaction();
        try {
            tx.begin();
            acao.accept(em);
            tx.commit();
        } catch (Exception e) {
            if (tx.isActive()) tx.rollback();
            throw new RuntimeException("Erro na transação: " + e.getMessage(), e);
        } finally {
            em.close();
        }
    }
}