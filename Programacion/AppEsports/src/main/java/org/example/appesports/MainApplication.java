package org.example.appesports;

import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Stage;
import org.example.appesports.Vista.MenuInicioSesionController;

import java.io.IOException;

public class MainApplication extends Application {
    @Override
    public void start(Stage stage) throws IOException {
        FXMLLoader fxmlLoader = new FXMLLoader(MainApplication.class.getResource("menu-inicio-sesion.fxml"));
        Parent root = fxmlLoader.load();

        MenuInicioSesionController controller = fxmlLoader.getController();
        controller.setStage(stage);
        Scene scene = new Scene(root, 1200, 800);
        stage.setTitle("AppEsports");
        stage.setScene(scene);
        stage.show();

    }
}
