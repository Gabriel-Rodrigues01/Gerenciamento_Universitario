package view;

import dao.CursoDAO;
import dao.ProfessorDAO;
import model.Curso;
import model.Professor;
import utils.JPAUtil;

import java.util.List;

public class TesteBanco {
    public static void main(String[] args) {
        System.out.println("--- INICIANDO TESTE DO BANCO DE DADOS ---");

        CursoDAO cursoDAO = new CursoDAO();
        ProfessorDAO professorDAO = new ProfessorDAO();

        try {
            // 1. Criar e Salvar um Curso
            System.out.println("\n--- Teste: Criar Curso ---");
            Curso novoCurso = new Curso(null, "Engenharia de Software", 3000, null);
            cursoDAO.criar(novoCurso);
            System.out.println("Curso criado: " + novoCurso.getNome() + " (ID: " + novoCurso.getId() + ")");

            // 2. Criar e Salvar um Professor
            System.out.println("\n--- Teste: Criar Professor ---");
            Professor novoProfessor = new Professor(null, "Dra. Ana Costa", "ana.costa@email.com", "Doutora em ES", null);
            professorDAO.criar(novoProfessor);
            System.out.println("Professor criado: " + novoProfessor.getNome() + " (ID: " + novoProfessor.getId() + ")");

            // 3. Listar todos os Cursos
            System.out.println("\n--- Teste: Listar Cursos ---");
            List<Curso> cursos = cursoDAO.listarTodos();
            if (cursos.isEmpty()) {
                System.out.println("Nenhum curso encontrado.");
            } else {
                cursos.forEach(curso -> System.out.println("Curso: " + curso.getNome() + ", Carga Horária: " + curso.getCargaHoraria()));
            }

            // 4. Listar todos os Professores
            System.out.println("\n--- Teste: Listar Professores ---");
            List<Professor> professores = professorDAO.listarTodos();
            if (professores.isEmpty()) {
                System.out.println("Nenhum professor encontrado.");
            } else {
                professores.forEach(professor -> System.out.println("Professor: " + professor.getNome() + ", Email: " + professor.getEmail()));
            }

            System.out.println("\n--- TESTE DO BANCO DE DADOS CONCLUÍDO COM SUCESSO ---");

        } catch (Exception e) {
            System.err.println("\n--- ERRO DURANTE O TESTE DO BANCO DE DADOS ---");
            e.printStackTrace();
        } finally {
            // Garante que o EntityManagerFactory seja fechado
            JPAUtil.close();
        }
    }
}
