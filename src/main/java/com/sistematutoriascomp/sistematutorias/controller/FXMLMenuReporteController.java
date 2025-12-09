/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/javafx/FXMLController.java to edit this template
 */
 /*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/javafx/FXMLController.java to edit this template
 */
package com.sistematutoriascomp.sistematutorias.controller;

import java.io.IOException;
import java.net.URL;
import java.util.HashMap;
import java.util.ResourceBundle;

import com.sistematutoriascomp.sistematutorias.dominio.ReporteTutoriaImp;
import com.sistematutoriascomp.sistematutorias.utilidad.Sesion;
import com.sistematutoriascomp.sistematutorias.utilidad.Utilidades;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.stage.Modality;
import javafx.stage.Stage;

/**
 * FXML Controller class
 *
 * @author HP
 */
public class FXMLMenuReporteController implements Initializable {

    @FXML
    private Button btnGenerarReporte;
    @FXML
    private Button btnEnviarReporte;
    @FXML
    private Button btnResponderReporteTutoria;
    @FXML
    private Button btnGenerarReporteGeneral;
    @FXML
    private Button btnRevisarReporteGeneral;
    @FXML
    private Button btnResponderReporteGeneral;

    /**
     * Initializes the controller class.
     */
    @Override
    public void initialize(URL url, ResourceBundle rb) {
        configurarVistaPorRol();
    }
    private void configurarVistaPorRol() {
        String rol = Sesion.getRolActual();
        btnGenerarReporte.setVisible(false);
        btnEnviarReporte.setVisible(false);

        if (btnResponderReporteTutoria != null) {
            btnResponderReporteTutoria.setVisible(false);
        }
        if (btnGenerarReporteGeneral != null) {
            btnGenerarReporteGeneral.setVisible(false);
        }
        if (btnRevisarReporteGeneral != null) {
            btnRevisarReporteGeneral.setVisible(false);
        }
        if (btnResponderReporteGeneral != null) {
            btnResponderReporteGeneral.setVisible(false);
        }
        if ("TUTOR".equals(rol)) {
            btnGenerarReporte.setVisible(true);
            btnEnviarReporte.setVisible(true);
        }
    }

    @FXML
    private void clicGenerarReporteTutoria(ActionEvent event) {
        int idTutor = Sesion.getTutorSesion().getIdTutor();
        HashMap<String, Object> respuesta = ReporteTutoriaImp.obtenerSesionesPendientes(idTutor);
        if (!(boolean) respuesta.get("error")) {
            abrirVentanaGenerarReporte();
        } else {
            Utilidades.mostrarAlertaSimple("Sin pendientes",
                    (String) respuesta.get("mensaje"),
                    Alert.AlertType.INFORMATION);
        }
    }

    private void abrirVentanaGenerarReporte() {
        try {
            FXMLLoader cargador = new FXMLLoader(
                    getClass().getResource("vista/reporte/FXMLGenerarReporteTutoria.fxml")
            );
            Parent vista = cargador.load();
            Scene escena = new Scene(vista);
            Stage escenario = new Stage();
            escenario.setScene(escena);
            escenario.setTitle("Generar reporte de tutoría");
            escenario.initOwner(btnGenerarReporte.getScene().getWindow());
            escenario.initModality(Modality.APPLICATION_MODAL);
            escenario.showAndWait();
        } catch (IOException ex) {
            ex.printStackTrace();
            Utilidades.mostrarAlertaSimple(
                    "Error",
                    "No se pudo abrir la ventana para generar el reporte de tutoría.",
                    Alert.AlertType.ERROR
            );
        }
    }

    @FXML
    private void clicEnviarReporteTutoria(ActionEvent event) {
        // Pendiente para siguiente CU
    }

    @FXML
    private void clicResponderReporteTutoria(ActionEvent event) {
    }

    @FXML
    private void clicGenerarReporteGeneral(ActionEvent event) {
    }

    @FXML
    private void clicRevisarReporteGeneral(ActionEvent event) {
    }

    @FXML
    private void clicResponderReporteGeneral(ActionEvent event) {
    }

    @FXML
    private void clicVolverMenuPrincipal(ActionEvent event) {
        try {
            Parent vista = FXMLLoader.load(getClass().getResource("vista/FXMLMenuPrincipal.fxml"));
            Scene escena = new Scene(vista);
            Stage stPrincipal = (Stage) btnGenerarReporte.getScene().getWindow();
            stPrincipal.setScene(escena);
            stPrincipal.setTitle("Menú Principal");
            stPrincipal.show();
        } catch (IOException ex) {
            ex.printStackTrace();
        }
    }
}
