package controller;

import javafx.application.Application;
import javafx.stage.Stage;
import utils.JPAUtil;
import utils.NavegacaoUtil;

/**
 * Classe principal que inicia a aplicação JavaFX.
 */
public class HelloApplication extends Application {

    @Override
    public void start(Stage primaryStage) {
        NavegacaoUtil.setPalcoPrincipal(primaryStage);
        primaryStage.setTitle("Sistema de Gerenciamento Universitário");
        NavegacaoUtil.carregarTela("/view/MenuPrincipal.fxml", "Menu Principal");
    }

    @Override
    public void stop() {
        JPAUtil.close();
    }

    public static void main(String[] args) {
        launch(args);
    }
}