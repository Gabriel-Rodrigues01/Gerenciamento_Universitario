package controller;

import dao.CursoDAO;
import model.Curso;
import utils.AlertaUtil;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.TextField;
import javafx.stage.Stage;

/**
 * Controlador para o formulário de cadastro e edição de Cursos.
 */
public class CursoFormController {

    @FXML private TextField txtNome;
    @FXML private TextField txtCargaHoraria;
    @FXML private Button btnSalvar;

    private final CursoDAO cursoDAO = new CursoDAO();
    private Curso cursoParaEdicao;

    public void setCursoParaEdicao(Curso curso) {
        this.cursoParaEdicao = curso;
        txtNome.setText(curso.getNome());
        txtCargaHoraria.setText(String.valueOf(curso.getCargaHoraria()));
    }

    @FXML
    private void salvar() {
        if (!validarCampos()) {
            return;
        }

        try {
            String nome = txtNome.getText().trim();
            int cargaHoraria = Integer.parseInt(txtCargaHoraria.getText().trim());

            if (cursoParaEdicao == null) { // Criando um novo
                Curso novoCurso = new Curso();
                novoCurso.setNome(nome);
                novoCurso.setCargaHoraria(cargaHoraria);
                cursoDAO.criar(novoCurso);
            } else { // Editando um existente
                cursoParaEdicao.setNome(nome);
                cursoParaEdicao.setCargaHoraria(cargaHoraria);
                cursoDAO.atualizar(cursoParaEdicao);
            }
            fecharJanela();
        } catch (NumberFormatException e) {
            AlertaUtil.mostrarErro("Erro de Formato", "A carga horária deve ser um número inteiro válido.");
        } catch (Exception e) {
            AlertaUtil.mostrarErro("Erro ao Salvar", "Ocorreu um erro ao salvar o curso: " + e.getMessage());
        }
    }

    @FXML
    private void cancelar() {
        fecharJanela();
    }

    private boolean validarCampos() {
        if (txtNome.getText().trim().isEmpty() || txtCargaHoraria.getText().trim().isEmpty()) {
            AlertaUtil.mostrarAviso("Campos Inválidos", "Nome e Carga Horária são obrigatórios.");
            return false;
        }
        return true;
    }

    private void fecharJanela() {
        Stage stage = (Stage) btnSalvar.getScene().getWindow();
        stage.close();
    }
}