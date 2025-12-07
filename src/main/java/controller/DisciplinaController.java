package controller;

import dao.DisciplinaDAO;
import model.Disciplina;
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
 * Controlador para a tela de Gerenciamento de Disciplinas.
 */
public class DisciplinaController {

    @FXML private TableView<Disciplina> tabelaDisciplinas;
    @FXML private TableColumn<Disciplina, Long> colunaId;
    @FXML private TableColumn<Disciplina, String> colunaNome;
    @FXML private TableColumn<Disciplina, String> colunaDescricao;
    @FXML private TableColumn<Disciplina, String> colunaCurso;

    private final DisciplinaDAO disciplinaDAO = new DisciplinaDAO();

    @FXML
    public void initialize() {
        configurarTabela();
        carregarDados();
    }

    private void configurarTabela() {
        colunaId.setCellValueFactory(new PropertyValueFactory<>("id"));
        colunaNome.setCellValueFactory(new PropertyValueFactory<>("nome"));
        colunaDescricao.setCellValueFactory(new PropertyValueFactory<>("descricao"));
        colunaCurso.setCellValueFactory(cellData -> {
            if (cellData.getValue().getCurso() != null) {
                return new SimpleStringProperty(cellData.getValue().getCurso().getNome());
            }
            return new SimpleStringProperty("N/A");
        });
    }

    private void carregarDados() {
        try {
            List<Disciplina> disciplinas = disciplinaDAO.listarTodos();
            tabelaDisciplinas.setItems(FXCollections.observableArrayList(disciplinas));
        } catch (Exception e) {
            AlertaUtil.mostrarErro("Erro ao Carregar", "Não foi possível carregar as disciplinas.");
        }
    }

    @FXML
    private void novaDisciplina() {
        NavegacaoUtil.abrirModal("/view/DisciplinaForm.fxml", "Nova Disciplina", this::carregarDados, null);
    }

    @FXML
    private void editarDisciplina() {
        Disciplina selecionada = tabelaDisciplinas.getSelectionModel().getSelectedItem();
        if (selecionada != null) {
            NavegacaoUtil.abrirModal("/view/DisciplinaForm.fxml", "Editar Disciplina", this::carregarDados,
                (DisciplinaFormController controller) -> controller.setDisciplinaParaEdicao(selecionada)
            );
        } else {
            AlertaUtil.mostrarAviso("Nenhuma Seleção", "Selecione uma disciplina para editar.");
        }
    }

    @FXML
    private void excluirDisciplina() {
        Disciplina selecionada = tabelaDisciplinas.getSelectionModel().getSelectedItem();
        if (selecionada != null) {
            if (AlertaUtil.mostrarConfirmacao("Confirmar Exclusão", "Deseja excluir a disciplina: " + selecionada.getNome() + "?")) {
                try {
                    disciplinaDAO.deletar(selecionada.getId());
                    carregarDados();
                } catch (Exception e) {
                    AlertaUtil.mostrarErro("Erro ao Excluir", "Ocorreu um erro ao excluir a disciplina.");
                }
            }
        } else {
            AlertaUtil.mostrarAviso("Nenhuma Seleção", "Selecione uma disciplina para excluir.");
        }
    }

    // Método associarProfessor() removido
    // @FXML
    // private void associarProfessor() {
    //     Disciplina selecionada = tabelaDisciplinas.getSelectionModel().getSelectedItem();
    //     if (selecionada != null) {
    //         NavegacaoUtil.abrirModal("/view/DisciplinaProfessorForm.fxml", "Associar Professor", this::carregarDados,
    //             (DisciplinaProfessorFormController controller) -> controller.setDisciplina(selecionada)
    //         );
    //     } else {
    //         AlertaUtil.mostrarAviso("Nenhuma Seleção", "Selecione uma disciplina para associar professores.");
    //     }
    // }

    @FXML
    private void voltarAoMenu() {
        NavegacaoUtil.carregarTela("/view/MenuPrincipal.fxml", "Menu Principal");
    }
}