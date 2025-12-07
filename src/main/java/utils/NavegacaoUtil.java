package utils;

import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Modality;
import javafx.stage.Stage;
import java.io.IOException;
import java.util.function.Consumer;

/**
 * Classe utilitária para gerenciar a navegação entre telas e a abertura de modais.
 * Centraliza a lógica de carregamento de FXML e a configuração de janelas.
 */
public class NavegacaoUtil {

    private static Stage palcoPrincipal;

    /**
     * Define o palco (janela) principal da aplicação.
     * Deve ser chamado no método start() da classe Application.
     */
    public static void setPalcoPrincipal(Stage stage) {
        palcoPrincipal = stage;
    }

    /**
     * Carrega e exibe uma nova tela no palco principal.
     * @param fxmlPath O caminho para o arquivo FXML (a partir da pasta resources).
     * @param titulo O título a ser exibido na janela.
     */
    public static void carregarTela(String fxmlPath, String titulo) {
        try {
            FXMLLoader loader = new FXMLLoader(NavegacaoUtil.class.getResource(fxmlPath));
            Parent root = loader.load();
            palcoPrincipal.setScene(new Scene(root));
            palcoPrincipal.setTitle(titulo);
            palcoPrincipal.show();
        } catch (IOException e) {
            e.printStackTrace();
            AlertaUtil.mostrarErro("Erro de Navegação", "Não foi possível carregar a tela: " + fxmlPath);
        }
    }

    /**
     * Abre uma janela de formulário modal e permite configurar seu controlador.
     * Útil para passar dados para a tela que está sendo aberta (ex: em modo de edição).
     * @param fxmlPath O caminho para o arquivo FXML.
     * @param titulo O título da janela.
     * @param aoFechar Ação a ser executada ao fechar o modal.
     * @param configurador Um Consumer que recebe o controlador do FXML para configuração.
     */
    public static <T> void abrirModal(String fxmlPath, String titulo, Runnable aoFechar, Consumer<T> configurador) {
        try {
            FXMLLoader loader = new FXMLLoader(NavegacaoUtil.class.getResource(fxmlPath));
            Parent root = loader.load();

            // Configura o controlador se um configurador for fornecido
            if (configurador != null) {
                configurador.accept(loader.getController());
            }

            Stage modalStage = new Stage();
            modalStage.setTitle(titulo);
            modalStage.initModality(Modality.APPLICATION_MODAL);
            modalStage.initOwner(palcoPrincipal);
            modalStage.setScene(new Scene(root));

            // Define a ação de callback para quando a janela for fechada
            if (aoFechar != null) {
                modalStage.setOnHidden(event -> aoFechar.run());
            }

            modalStage.showAndWait();

        } catch (IOException e) {
            e.printStackTrace();
            AlertaUtil.mostrarErro("Erro de Navegação", "Não foi possível abrir o formulário: " + fxmlPath);
        }
    }
}