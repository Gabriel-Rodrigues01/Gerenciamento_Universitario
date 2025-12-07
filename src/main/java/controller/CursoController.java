package controller;

import dao.CursoDAO;
import model.Curso;
import utils.AlertaUtil;
import utils.NavegacaoUtil;
import javafx.collections.FXCollections;
import javafx.fxml.FXML;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.cell.PropertyValueFactory;
import java.util.List;

/**
 * Controlador para a tela de Gerenciamento de Cursos.
 */
public class CursoController {

    @FXML private TableView<Curso> tabelaCursos;
    @FXML private TableColumn<Curso, Long> colunaId;
    @FXML private TableColumn<Curso, String> colunaNome;
    @FXML private TableColumn<Curso, Integer> colunaCargaHoraria;

    private final CursoDAO cursoDAO = new CursoDAO();

    @FXML
    public void initialize() {
        configurarTabela();
        carregarDados();
    }

    private void configurarTabela() {
        colunaId.setCellValueFactory(new PropertyValueFactory<>("id"));
        colunaNome.setCellValueFactory(new PropertyValueFactory<>("nome"));
        colunaCargaHoraria.setCellValueFactory(new PropertyValueFactory<>("cargaHoraria"));
    }

    private void carregarDados() {
        try {
            List<Curso> cursos = cursoDAO.listarTodos();
            tabelaCursos.setItems(FXCollections.observableArrayList(cursos));
        } catch (Exception e) {
            AlertaUtil.mostrarErro("Erro ao Carregar", "Não foi possível carregar os cursos.");
        }
    }

    @FXML
    private void novoCurso() {
        NavegacaoUtil.abrirModal("/view/CursoForm.fxml", "Novo Curso", this::carregarDados, null);
    }

    @FXML
    private void editarCurso() {
        Curso selecionado = tabelaCursos.getSelectionModel().getSelectedItem();
        if (selecionado != null) {
            NavegacaoUtil.abrirModal("/view/CursoForm.fxml", "Editar Curso", this::carregarDados,
                (CursoFormController controller) -> controller.setCursoParaEdicao(selecionado)
            );
        } else {
            AlertaUtil.mostrarAviso("Nenhuma Seleção", "Selecione um curso para editar.");
        }
    }

    @FXML
    private void excluirCurso() {
        Curso selecionado = tabelaCursos.getSelectionModel().getSelectedItem();
        if (selecionado != null) {
            if (AlertaUtil.mostrarConfirmacao("Confirmar Exclusão", "Deseja excluir o curso: " + selecionado.getNome() + "?")) {
                try {
                    cursoDAO.deletar(selecionado.getId());
                    carregarDados();
                } catch (Exception e) {
                    AlertaUtil.mostrarErro("Erro ao Excluir", "Ocorreu um erro ao excluir o curso.");
                }
            }
        } else {
            AlertaUtil.mostrarAviso("Nenhuma Seleção", "Selecione um curso para excluir.");
        }
    }

    @FXML
    private void voltarAoMenu() {
        NavegacaoUtil.carregarTela("/view/MenuPrincipal.fxml", "Menu Principal");
    }
}