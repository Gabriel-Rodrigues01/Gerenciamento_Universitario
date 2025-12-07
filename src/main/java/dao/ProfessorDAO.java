package dao;

import model.Professor;

/**
 * DAO específico para a entidade Professor.
 * Estende GenericDAO para herdar as operações CRUD básicas.
 */
public class ProfessorDAO extends GenericDAO<Professor> {
    public ProfessorDAO() {
        super(Professor.class);
    }
}