package dao;

import model.Curso;

/**
 * DAO específico para a entidade Curso.
 * Estende GenericDAO para herdar as operações CRUD básicas.
 */
public class CursoDAO extends GenericDAO<Curso> {
    public CursoDAO() {
        super(Curso.class);
    }
}