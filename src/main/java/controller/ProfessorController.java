package controller;

import dao.ProfessorDAO;
import model.Professor;
import utils.AlertaUtil;
import utils.NavegacaoUtil;
import javafx.collections.FXCollections;
import javafx.fxml.FXML;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.cell.PropertyValueFactory;
import java.util.List;

import controller.ProfessorFormController; // Re-adicionado a importação

/**
 * Controlador para a tela de Gerenciamento de Professores.
 */
public class ProfessorController {

    @FXML private TableView<Professor> tabelaProfessores;
    @FXML private TableColumn<Professor, Long> colunaId;
    @FXML private TableColumn<Professor, String> colunaNome;
    @FXML private TableColumn<Professor, String> colunaEmail;
    @FXML private TableColumn<Professor, String> colunaFormacao;

    private final ProfessorDAO professorDAO = new ProfessorDAO();

    @FXML
    public void initialize() {
        configurarTabela();
        carregarDados();
    }

    private void configurarTabela() {
        colunaId.setCellValueFactory(new PropertyValueFactory<>("id"));
        colunaNome.setCellValueFactory(new PropertyValueFactory<>("nome"));
        colunaEmail.setCellValueFactory(new PropertyValueFactory<>("email"));
        colunaFormacao.setCellValueFactory(new PropertyValueFactory<>("formacao"));
    }

    private void carregarDados() {
        try {
            List<Professor> professores = professorDAO.listarTodos();
            tabelaProfessores.setItems(FXCollections.observableArrayList(professores));
        } catch (Exception e) {
            AlertaUtil.mostrarErro("Erro ao Carregar", "Não foi possível carregar os professores.");
        }
    }

    @FXML
    private void novoProfessor() {
        NavegacaoUtil.abrirModal("/view/ProfessorForm.fxml", "Novo Professor", this::carregarDados, null);
    }

    @FXML
    private void editarProfessor() {
        Professor selecionado = tabelaProfessores.getSelectionModel().getSelectedItem();
        if (selecionado != null) {
            NavegacaoUtil.abrirModal("/view/ProfessorForm.fxml", "Editar Professor", this::carregarDados,
                (ProfessorFormController controller) -> controller.setProfessorParaEdicao(selecionado)
            );
        } else {
            AlertaUtil.mostrarAviso("Nenhuma Seleção", "Selecione um professor para editar.");
        }
    }

    @FXML
    private void excluirProfessor() {
        Professor selecionado = tabelaProfessores.getSelectionModel().getSelectedItem();
        if (selecionado != null) {
            if (AlertaUtil.mostrarConfirmacao("Confirmar Exclusão", "Deseja excluir o professor: " + selecionado.getNome() + "?")) {
                try {
                    professorDAO.deletar(selecionado.getId());
                    carregarDados();
                } catch (Exception e) {
                    AlertaUtil.mostrarErro("Erro ao Excluir", "Ocorreu um erro ao excluir o professor.");
                }
            }
        } else {
            AlertaUtil.mostrarAviso("Nenhuma Seleção", "Selecione um professor para excluir.");
        }
    }

    @FXML
    private void voltarAoMenu() {
        NavegacaoUtil.carregarTela("/view/MenuPrincipal.fxml", "Menu Principal");
    }
}