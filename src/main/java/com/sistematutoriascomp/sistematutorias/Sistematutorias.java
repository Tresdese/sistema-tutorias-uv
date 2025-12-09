package com.sistematutoriascomp.sistematutorias;

import java.io.IOException;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import com.sistematutoriascomp.sistematutorias.utilidad.Utilidades;

import javafx.application.Application;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Stage;

public class Sistematutorias extends Application {

    private static final Logger LOGGER = LogManager.getLogger(Sistematutorias.class);

    @Override
    public void start(Stage primaryStage) {
        try {
            Parent vistaRaiz = Utilidades.loadFXML("/FXMLInicioSesion.fxml");
            Scene escena = new Scene(vistaRaiz);
            primaryStage.setTitle("Iniciar Sesión");
            primaryStage.setScene(escena);
            primaryStage.show();
        } catch (IOException e) {
            LOGGER.error("Error al iniciar la aplicación", e);
            e.printStackTrace();
        } catch (NullPointerException ex) {
            LOGGER.error("Recurso no encontrado al iniciar la aplicación", ex);
            ex.printStackTrace();
        } catch (Exception ex) {
            LOGGER.error("Error inesperado al iniciar la aplicación", ex);
            ex.printStackTrace();
        }
    }

    public static void main(String[] args) {
        launch(args);
    }
}
