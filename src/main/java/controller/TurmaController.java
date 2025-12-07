package controller;

import dao.TurmaDAO;
import model.Turma;
import utils.AlertaUtil;
import utils.NavegacaoUtil;
import javafx.collections.FXCollections;
import javafx.fxml.FXML;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.beans.property.SimpleStringProperty;
import java.util.List;

/**
 * Controlador para a tela de Gerenciamento de Turmas.
 */
public class TurmaController {

    @FXML private TableView<Turma> tabelaTurmas;
    @FXML private TableColumn<Turma, Long> colunaId;
    @FXML private TableColumn<Turma, String> colunaSemestre;
    @FXML private TableColumn<Turma, String> colunaDisciplina;
    @FXML private TableColumn<Turma, String> colunaProfessor;
    @FXML private TableColumn<Turma, String> colunaHorario;

    private final TurmaDAO turmaDAO = new TurmaDAO();

    @FXML
    public void initialize() {
        configurarTabela();
        carregarDados();
    }

    private void configurarTabela() {
        colunaId.setCellValueFactory(new PropertyValueFactory<>("id"));
        colunaSemestre.setCellValueFactory(new PropertyValueFactory<>("semestre"));
        colunaHorario.setCellValueFactory(new PropertyValueFactory<>("horario"));

        colunaDisciplina.setCellValueFactory(cellData -> {
            if (cellData.getValue().getDisciplina() != null) {
                return new SimpleStringProperty(cellData.getValue().getDisciplina().getNome());
            }
            return new SimpleStringProperty("N/A");
        });

        colunaProfessor.setCellValueFactory(cellData -> {
            if (cellData.getValue().getProfessor() != null) {
                return new SimpleStringProperty(cellData.getValue().getProfessor().getNome());
            }
            return new SimpleStringProperty("N/A");
        });
    }

    private void carregarDados() {
        try {
            List<Turma> turmas = turmaDAO.listarTodos();
            tabelaTurmas.setItems(FXCollections.observableArrayList(turmas));
        } catch (Exception e) {
            AlertaUtil.mostrarErro("Erro ao Carregar", "Não foi possível carregar as turmas.");
        }
    }

    @FXML
    private void novaTurma() {
        NavegacaoUtil.abrirModal("/view/TurmaForm.fxml", "Nova Turma", this::carregarDados, null);
    }

    @FXML
    private void editarTurma() {
        Turma selecionada = tabelaTurmas.getSelectionModel().getSelectedItem();
        if (selecionada != null) {
            NavegacaoUtil.abrirModal("/view/TurmaForm.fxml", "Editar Turma", this::carregarDados,
                (TurmaFormController controller) -> controller.setTurmaParaEdicao(selecionada)
            );
        } else {
            AlertaUtil.mostrarAviso("Nenhuma Seleção", "Selecione uma turma para editar.");
        }
    }

    @FXML
    private void excluirTurma() {
        Turma selecionada = tabelaTurmas.getSelectionModel().getSelectedItem();
        if (selecionada != null) {
            if (AlertaUtil.mostrarConfirmacao("Confirmar Exclusão", "Deseja excluir a turma: " + selecionada.getSemestre() + " - " + selecionada.getDisciplina().getNome() + "?")) {
                try {
                    turmaDAO.deletar(selecionada.getId());
                    carregarDados();
                } catch (Exception e) {
                    AlertaUtil.mostrarErro("Erro ao Excluir", "Ocorreu um erro ao excluir a turma.");
                }
            }
        } else {
            AlertaUtil.mostrarAviso("Nenhuma Seleção", "Selecione uma turma para excluir.");
        }
    }

    @FXML
    private void voltarAoMenu() {
        NavegacaoUtil.carregarTela("/view/MenuPrincipal.fxml", "Menu Principal");
    }
}