package controller;

import dao.DisciplinaDAO;
import dao.ProfessorDAO;
import dao.TurmaDAO;
import model.Disciplina;
import model.Professor;
import model.Turma;
import utils.AlertaUtil;
import javafx.collections.FXCollections;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.ComboBox;
import javafx.scene.control.TextField;
import javafx.stage.Stage;
import javafx.scene.control.ListCell;

/**
 * Controlador para o formulário de cadastro e edição de Turmas.
 */
public class TurmaFormController {

    @FXML private TextField txtSemestre;
    @FXML private ComboBox<Disciplina> comboDisciplina;
    @FXML private ComboBox<Professor> comboProfessor;
    @FXML private TextField txtHorario;
    @FXML private Button btnSalvar;

    private final TurmaDAO turmaDAO = new TurmaDAO();
    private final DisciplinaDAO disciplinaDAO = new DisciplinaDAO();
    private final ProfessorDAO professorDAO = new ProfessorDAO();
    private Turma turmaParaEdicao;

    @FXML
    public void initialize() {
        carregarDisciplinas();
        carregarProfessores();
    }

    private void carregarDisciplinas() {
        try {
            comboDisciplina.setItems(FXCollections.observableArrayList(disciplinaDAO.listarTodos()));
            comboDisciplina.setCellFactory(lv -> new ListCell<>() { // Usando operador diamante
                @Override
                protected void updateItem(Disciplina item, boolean empty) {
                    super.updateItem(item, empty);
                    setText(empty ? "" : item.getNome());
                }
            });
            comboDisciplina.setButtonCell(new ListCell<>() { // Usando operador diamante
                @Override
                protected void updateItem(Disciplina item, boolean empty) {
                    super.updateItem(item, empty);
                    setText(empty ? "" : item.getNome());
                }
            });
        } catch (Exception e) {
            AlertaUtil.mostrarErro("Erro ao Carregar", "Não foi possível carregar as disciplinas.");
        }
    }

    private void carregarProfessores() {
        try {
            comboProfessor.setItems(FXCollections.observableArrayList(professorDAO.listarTodos()));
            comboProfessor.setCellFactory(lv -> new ListCell<>() { // Usando operador diamante
                @Override
                protected void updateItem(Professor item, boolean empty) {
                    super.updateItem(item, empty);
                    setText(empty ? "" : item.getNome());
                }
            });
            comboProfessor.setButtonCell(new ListCell<>() { // Usando operador diamante
                @Override
                protected void updateItem(Professor item, boolean empty) {
                    super.updateItem(item, empty);
                    setText(empty ? "" : item.getNome());
                }
            });
        } catch (Exception e) {
            AlertaUtil.mostrarErro("Erro ao Carregar", "Não foi possível carregar os professores.");
        }
    }

    public void setTurmaParaEdicao(Turma turma) {
        this.turmaParaEdicao = turma;
        txtSemestre.setText(turma.getSemestre());
        comboDisciplina.setValue(turma.getDisciplina());
        comboProfessor.setValue(turma.getProfessor());
        txtHorario.setText(turma.getHorario());
    }

    @FXML
    private void salvar() {
        if (!validarCampos()) {
            return;
        }

        try {
            String semestre = txtSemestre.getText().trim();
            Disciplina disciplina = comboDisciplina.getValue();
            Professor professor = comboProfessor.getValue();
            String horario = txtHorario.getText().trim();

            if (turmaParaEdicao == null) { // Criando uma nova
                Turma novaTurma = new Turma();
                novaTurma.setSemestre(semestre);
                novaTurma.setDisciplina(disciplina);
                novaTurma.setProfessor(professor);
                novaTurma.setHorario(horario);
                turmaDAO.criar(novaTurma);
            } else { // Editando uma existente
                turmaParaEdicao.setSemestre(semestre);
                turmaParaEdicao.setDisciplina(disciplina);
                turmaParaEdicao.setProfessor(professor);
                turmaParaEdicao.setHorario(horario);
                turmaDAO.atualizar(turmaParaEdicao);
            }
            fecharJanela();
        } catch (Exception e) {
            AlertaUtil.mostrarErro("Erro ao Salvar", "Ocorreu um erro ao salvar a turma: " + e.getMessage());
        }
    }

    @FXML
    private void cancelar() {
        fecharJanela();
    }

    private boolean validarCampos() {
        if (txtSemestre.getText().trim().isEmpty() || comboDisciplina.getValue() == null ||
            comboProfessor.getValue() == null || txtHorario.getText().trim().isEmpty()) {
            AlertaUtil.mostrarAviso("Campos Inválidos", "Todos os campos são obrigatórios.");
            return false;
        }
        return true;
    }

    private void fecharJanela() {
        Stage stage = (Stage) btnSalvar.getScene().getWindow();
        stage.close();
    }
}