package dao;

import model.Disciplina;
import utils.JPAUtil;
import jakarta.persistence.EntityManager;
import jakarta.persistence.TypedQuery;
import java.util.List;

/**
 * DAO específico para a entidade Disciplina.
 * Estende GenericDAO para herdar as operações CRUD básicas.
 */
public class DisciplinaDAO extends GenericDAO<Disciplina> {
    public DisciplinaDAO() {
        super(Disciplina.class);
    }

    /**
     * Sobrescreve o método listarTodos para carregar o Curso associado
     * de forma ansiosa (eagerly) usando JOIN FETCH, evitando LazyInitializationException
     * ao exibir as disciplinas na UI.
     */
    @Override
    public List<Disciplina> listarTodos() {
        EntityManager em = JPAUtil.getEntityManager();
        try {
            // JPQL com JOIN FETCH para carregar o 'curso' junto com a 'disciplina'
            String jpql = "SELECT d FROM Disciplina d JOIN FETCH d.curso";
            TypedQuery<Disciplina> query = em.createQuery(jpql, Disciplina.class);
            return query.getResultList();
        } finally {
            em.close();
        }
    }
}