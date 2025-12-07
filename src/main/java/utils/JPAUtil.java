package utils;

import jakarta.persistence.EntityManager;
import jakarta.persistence.EntityManagerFactory;
import jakarta.persistence.Persistence;

/**
 * Classe utilitária para gerenciar o EntityManagerFactory e o EntityManager do JPA.
 * Garante que o EntityManagerFactory seja criado apenas uma vez (Singleton).
 */
public class JPAUtil {

    // O nome da unidade de persistência deve ser o mesmo definido no arquivo persistence.xml
    private static final String PERSISTENCE_UNIT_NAME = "universidade";

    private static EntityManagerFactory emf;

    /**
     * Retorna o EntityManagerFactory. Se não existir, cria um.
     * Este método garante que a aplicação use uma única instância de EMF.
     */
    private static EntityManagerFactory getEntityManagerFactory() {
        if (emf == null) {
            try {
                emf = Persistence.createEntityManagerFactory(PERSISTENCE_UNIT_NAME);
            } catch (Exception e) {
                System.err.println("Erro ao criar o EntityManagerFactory: " + e.getMessage());
                throw new RuntimeException(e);
            }
        }
        return emf;
    }

    /**
     * Fornece uma instância do EntityManager para realizar operações no banco de dados.
     */
    public static EntityManager getEntityManager() {
        return getEntityManagerFactory().createEntityManager();
    }

    /**
     * Fecha o EntityManagerFactory quando a aplicação é encerrada.
     * Deve ser chamado no final do ciclo de vida da aplicação.
     */
    public static void close() {
        if (emf != null && emf.isOpen()) {
            emf.close();
        }
    }
}