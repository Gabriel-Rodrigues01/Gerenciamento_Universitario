package view;

import controller.HelloApplication;
import javafx.application.Application;
import javafx.stage.Stage;

/**
 * Classe principal para iniciar a aplicação JavaFX.
 * Esta classe atua como um wrapper para HelloApplication, conforme solicitado.
 */
public class MainApp extends Application {

    @Override
    public void start(Stage primaryStage) throws Exception {
        // Delega a inicialização para a classe HelloApplication
        new HelloApplication().start(primaryStage);
    }

    @Override
    public void stop() throws Exception {
        // Delega o encerramento para a classe HelloApplication
        new HelloApplication().stop();
    }

    public static void main(String[] args) {
        launch(args);
    }
}