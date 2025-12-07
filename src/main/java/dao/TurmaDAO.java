package dao;

import model.Turma;
import utils.JPAUtil;
import jakarta.persistence.EntityManager;
import jakarta.persistence.TypedQuery;
import java.util.List;

/**
 * DAO específico para a entidade Turma.
 * Estende GenericDAO para herdar as operações CRUD básicas.
 */
public class TurmaDAO extends GenericDAO<Turma> {
    public TurmaDAO() {
        super(Turma.class);
    }

    /**
     * Sobrescreve o método listarTodos para carregar a Disciplina e o Professor associados
     * de forma ansiosa (eagerly) usando JOIN FETCH, evitando LazyInitializationException
     * ao exibir as turmas na UI.
     */
    @Override
    public List<Turma> listarTodos() {
        EntityManager em = JPAUtil.getEntityManager();
        try {
            // JPQL com JOIN FETCH para carregar 'disciplina' e 'professor' junto com a 'turma'
            String jpql = "SELECT t FROM Turma t JOIN FETCH t.disciplina d JOIN FETCH t.professor p";
            TypedQuery<Turma> query = em.createQuery(jpql, Turma.class);
            return query.getResultList();
        } finally {
            em.close();
        }
    }
}