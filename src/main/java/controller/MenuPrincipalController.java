package controller;

import javafx.fxml.FXML;
import utils.NavegacaoUtil;

/**
 * Controlador para a tela do menu principal.
 */
public class MenuPrincipalController {

    @FXML
    private void abrirGerenciarCursos() {
        NavegacaoUtil.carregarTela("/view/CursoView.fxml", "Gerenciamento de Cursos");
    }

    @FXML
    private void abrirGerenciarProfessores() {
        NavegacaoUtil.carregarTela("/view/ProfessorView.fxml", "Gerenciamento de Professores");
    }

    @FXML
    private void abrirGerenciarDisciplinas() {
        NavegacaoUtil.carregarTela("/view/DisciplinaView.fxml", "Gerenciamento de Disciplinas");
    }

    @FXML
    private void abrirGerenciarTurmas() {
        NavegacaoUtil.carregarTela("/view/TurmaView.fxml", "Gerenciamento de Turmas");
    }

    @FXML
    private void sobre() {
        // Futuramente, pode abrir uma tela de "Sobre"
    }
}