/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.sistematutoriascomp.sistematutorias.utilidad;

import java.io.IOException;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import javafx.event.ActionEvent;
import javafx.fxml.FXMLLoader;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.stage.Modality;
import javafx.stage.Stage;

/**
 *
 * @author HP
 */
public class Utilidades {

    private final static Logger LOGGER = LogManager.getLogger(Utilidades.class);

    private final static String RUTA_VISTAS = "/com/sistematutoriascomp/sistematutorias/views";
    
    public static void mostrarAlertaSimple(String titulo, String contenido, Alert.AlertType tipo) {
        Alert alerta = new Alert(tipo);
        alerta.setTitle(titulo);
        alerta.setHeaderText(null);
        alerta.setContentText(contenido);
        alerta.showAndWait();
    }

    public static Parent loadFXML(String fxmlPath) throws IOException, NullPointerException {
        return FXMLLoader.load(Utilidades.class.getResource(RUTA_VISTAS + fxmlPath));
    }

    // actualiza la ventana actual
    public static void goToWindow(String fxmlPath, ActionEvent event, String title) throws IOException, NullPointerException {
        Parent vista = loadFXML(fxmlPath);
        Scene escena = new Scene(vista);
        Node source = (Node) event.getSource();
        Stage escenario = (Stage) source.getScene().getWindow();
        escenario.setScene(escena);
        escenario.setTitle(title);
        escenario.show();
    }

    // abre una nueva ventana modal
    public static void openModal(String fxmlPath, String title) throws IOException, NullPointerException {
        Parent vista = loadFXML(fxmlPath);
        Scene escena = new Scene(vista);
        Stage escenario = new Stage();
        escenario.setScene(escena);
        escenario.setTitle(title);
        escenario.initModality(Modality.APPLICATION_MODAL);
        escenario.showAndWait();
    }

    public static void cerrarVentana(ActionEvent event) {
        Stage stage = (Stage) ((Node) event.getSource()).getScene().getWindow();
        stage.close();
    }

    public static void clicVolverMenuPrincipal(ActionEvent event) throws IOException {
        goToWindow("/FXMLMenuPrincipal.fxml", event, "Menú Principal");
    }

    public static void clicCerrarSesion(ActionEvent event) throws IOException {
        goToWindow("/FXMLInicioSesion.fxml", event, "Iniciar Sesión");
    }

}
