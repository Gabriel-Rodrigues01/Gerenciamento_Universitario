package controller;

import dao.DisciplinaDAO;
import dao.ProfessorDAO;
import model.Disciplina;
import model.Professor;
import utils.AlertaUtil;
import javafx.collections.FXCollections;
import javafx.fxml.FXML;
import javafx.scene.control.Label;
import javafx.scene.control.ListView;
import javafx.stage.Stage;
import javafx.scene.control.ListCell; // Adicionado esta importação

import java.util.List;
import java.util.stream.Collectors;

/**
 * Controlador para o formulário de associação de Professores a Disciplinas.
 */
public class DisciplinaProfessorFormController {

    @FXML private Label lblDisciplinaNome;
    @FXML private ListView<Professor> listViewProfessoresDisponiveis;
    @FXML private ListView<Professor> listViewProfessoresAssociados;

    private Disciplina disciplina;
    private final DisciplinaDAO disciplinaDAO = new DisciplinaDAO();
    private final ProfessorDAO professorDAO = new ProfessorDAO();

    @FXML
    public void initialize() {
        // Configura as ListViews para exibir o nome do professor
        listViewProfessoresDisponiveis.setCellFactory(lv -> new ListCell<Professor>() {
            @Override
            protected void updateItem(Professor professor, boolean empty) {
                super.updateItem(professor, empty);
                setText(empty ? null : professor.getNome());
            }
        });
        listViewProfessoresAssociados.setCellFactory(lv -> new ListCell<Professor>() {
            @Override
            protected void updateItem(Professor professor, boolean empty) {
                super.updateItem(professor, empty);
                setText(empty ? null : professor.getNome());
            }
        });
    }

    public void setDisciplina(Disciplina disciplina) {
        this.disciplina = disciplina;
        lblDisciplinaNome.setText("Disciplina: " + disciplina.getNome());
        carregarProfessores();
    }

    private void carregarProfessores() {
        try {
            List<Professor> todosProfessores = professorDAO.listarTodos();
            List<Professor> professoresAssociados = disciplina.getProfessores();

            // Filtra os professores disponíveis (aqueles que ainda não estão associados)
            List<Professor> professoresDisponiveis = todosProfessores.stream()
                    .filter(p -> !professoresAssociados.contains(p))
                    .collect(Collectors.toList());

            listViewProfessoresDisponiveis.setItems(FXCollections.observableArrayList(professoresDisponiveis));
            listViewProfessoresAssociados.setItems(FXCollections.observableArrayList(professoresAssociados));

        } catch (Exception e) {
            AlertaUtil.mostrarErro("Erro ao Carregar", "Não foi possível carregar a lista de professores.");
        }
    }

    @FXML
    private void associarProfessor() {
        Professor selecionado = listViewProfessoresDisponiveis.getSelectionModel().getSelectedItem();
        if (selecionado != null) {
            listViewProfessoresAssociados.getItems().add(selecionado);
            listViewProfessoresDisponiveis.getItems().remove(selecionado);
        } else {
            AlertaUtil.mostrarAviso("Nenhuma Seleção", "Selecione um professor para associar.");
        }
    }

    @FXML
    private void desassociarProfessor() {
        Professor selecionado = listViewProfessoresAssociados.getSelectionModel().getSelectedItem();
        if (selecionado != null) {
            listViewProfessoresDisponiveis.getItems().add(selecionado);
            listViewProfessoresAssociados.getItems().remove(selecionado);
        } else {
            AlertaUtil.mostrarAviso("Nenhuma Seleção", "Selecione um professor para desassociar.");
        }
    }

    @FXML
    private void salvar() {
        try {
            // Atualiza a lista de professores da disciplina com os professores associados na ListView
            disciplina.setProfessores(listViewProfessoresAssociados.getItems());
            disciplinaDAO.atualizar(disciplina);
            fecharJanela();
        } catch (Exception e) {
            AlertaUtil.mostrarErro("Erro ao Salvar", "Ocorreu um erro ao salvar as associações.");
        }
    }

    @FXML
    private void cancelar() {
        fecharJanela();
    }

    private void fecharJanela() {
        // Obtém o palco (Stage) da janela atual e o fecha
        Stage stage = (Stage) lblDisciplinaNome.getScene().getWindow();
        stage.close();
    }
}