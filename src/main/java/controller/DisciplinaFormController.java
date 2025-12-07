package controller;

import dao.CursoDAO;
import dao.DisciplinaDAO;
import model.Curso;
import model.Disciplina;
import utils.AlertaUtil;
import javafx.collections.FXCollections;
import javafx.fxml.FXML;
import javafx.scene.control.*;
import javafx.stage.Stage;
import java.util.ArrayList;

/**
 * Controlador para o formulário de Disciplinas.
 */
public class DisciplinaFormController {

    @FXML private TextField txtNome;
    @FXML private TextArea txtDescricao;
    @FXML private ComboBox<Curso> comboCurso;
    @FXML private Button btnSalvar;

    private final DisciplinaDAO disciplinaDAO = new DisciplinaDAO();
    private final CursoDAO cursoDAO = new CursoDAO();
    private Disciplina disciplinaParaEdicao;

    @FXML
    public void initialize() {
        carregarCursos();
    }

    private void carregarCursos() {
        try {
            comboCurso.setItems(FXCollections.observableArrayList(cursoDAO.listarTodos()));
        } catch (Exception e) {
            AlertaUtil.mostrarErro("Erro ao Carregar", "Não foi possível carregar os cursos.");
        }
    }

    public void setDisciplinaParaEdicao(Disciplina disciplina) {
        this.disciplinaParaEdicao = disciplina;
        txtNome.setText(disciplina.getNome());
        txtDescricao.setText(disciplina.getDescricao());
        comboCurso.setValue(disciplina.getCurso());
    }

    @FXML
    private void salvar() {
        if (!validarCampos()) {
            return;
        }

        try {
            String nome = txtNome.getText().trim();
            String descricao = txtDescricao.getText().trim();
            Curso curso = comboCurso.getValue();

            if (disciplinaParaEdicao == null) {
                Disciplina nova = new Disciplina();
                nova.setNome(nome);
                nova.setDescricao(descricao);
                nova.setCurso(curso);
                nova.setProfessores(new ArrayList<>());
                nova.setTurmas(new ArrayList<>());
                disciplinaDAO.criar(nova);
            } else {
                disciplinaParaEdicao.setNome(nome);
                disciplinaParaEdicao.setDescricao(descricao);
                disciplinaParaEdicao.setCurso(curso);
                disciplinaDAO.atualizar(disciplinaParaEdicao);
            }
            fecharJanela();
        } catch (Exception e) {
            AlertaUtil.mostrarErro("Erro ao Salvar", "Ocorreu um erro ao salvar a disciplina.");
        }
    }

    @FXML
    private void cancelar() {
        fecharJanela();
    }

    private boolean validarCampos() {
        if (txtNome.getText().trim().isEmpty() || comboCurso.getValue() == null) {
            AlertaUtil.mostrarAviso("Campos Inválidos", "Nome e Curso são obrigatórios.");
            return false;
        }
        return true;
    }

    private void fecharJanela() {
        Stage stage = (Stage) btnSalvar.getScene().getWindow();
        stage.close();
    }
}