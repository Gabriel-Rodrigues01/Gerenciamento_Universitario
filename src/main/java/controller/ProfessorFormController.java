package controller;

import dao.ProfessorDAO;
import model.Professor;
import utils.AlertaUtil;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.TextField;
import javafx.stage.Stage;

/**
 * Controlador para o formulário de cadastro e edição de Professores.
 */
public class ProfessorFormController {

    @FXML private TextField txtNome;
    @FXML private TextField txtEmail;
    @FXML private TextField txtFormacao;
    @FXML private Button btnSalvar;

    private final ProfessorDAO professorDAO = new ProfessorDAO();
    private Professor professorParaEdicao;

    public void setProfessorParaEdicao(Professor professor) {
        this.professorParaEdicao = professor;
        txtNome.setText(professor.getNome());
        txtEmail.setText(professor.getEmail());
        txtFormacao.setText(professor.getFormacao());
    }

    @FXML
    private void salvar() {
        if (!validarCampos()) {
            return;
        }

        try {
            String nome = txtNome.getText().trim();
            String email = txtEmail.getText().trim();
            String formacao = txtFormacao.getText().trim();

            if (professorParaEdicao == null) { // Criando um novo
                Professor novoProfessor = new Professor();
                novoProfessor.setNome(nome);
                novoProfessor.setEmail(email);
                novoProfessor.setFormacao(formacao);
                professorDAO.criar(novoProfessor);
            } else { // Editando um existente
                professorParaEdicao.setNome(nome);
                professorParaEdicao.setEmail(email);
                professorParaEdicao.setFormacao(formacao);
                professorDAO.atualizar(professorParaEdicao);
            }
            fecharJanela();
        } catch (Exception e) {
            AlertaUtil.mostrarErro("Erro ao Salvar", "Ocorreu um erro ao salvar o professor: " + e.getMessage());
        }
    }

    @FXML
    private void cancelar() {
        fecharJanela();
    }

    private boolean validarCampos() {
        if (txtNome.getText().trim().isEmpty() || txtEmail.getText().trim().isEmpty()) {
            AlertaUtil.mostrarAviso("Campos Inválidos", "Nome e Email são obrigatórios.");
            return false;
        }
        // Validação de formato de email simples
        if (!txtEmail.getText().trim().matches("^[\\w-\\.]+@([\\w-]+\\.)+[\\w-]{2,4}$")) {
            AlertaUtil.mostrarAviso("Email Inválido", "Por favor, insira um endereço de email válido.");
            return false;
        }
        return true;
    }

    private void fecharJanela() {
        Stage stage = (Stage) btnSalvar.getScene().getWindow();
        stage.close();
    }
}