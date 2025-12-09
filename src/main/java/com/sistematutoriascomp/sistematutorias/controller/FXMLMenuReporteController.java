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

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import com.sistematutoriascomp.sistematutorias.dominio.ReporteTutoriaImp;
import com.sistematutoriascomp.sistematutorias.utilidad.Sesion;
import com.sistematutoriascomp.sistematutorias.utilidad.Utilidades;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;

/**
 * FXML Controller class
 *
 * @author HP
 */
public class FXMLMenuReporteController implements Initializable {

    private final Logger LOGGER = LogManager.getLogger(FXMLMenuReporteController.class);

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
            LOGGER.info("No hay sesiones de tutoría pendientes para generar reportes para el tutor con ID: {}", idTutor);
            Utilidades.mostrarAlertaSimple("Sin pendientes",
                    (String) respuesta.get("mensaje"),
                    Alert.AlertType.INFORMATION);
        }
    }

    private void abrirVentanaGenerarReporte() {
        try {
            Utilidades.openModal("/reporte/FXMLGenerarReporteTutoria.fxml", "Generar Reporte de Tutoría");
        } catch (IOException ex) {
            LOGGER.error("Error al abrir la ventana para generar el reporte de tutoría", ex);
            ex.printStackTrace();
            Utilidades.mostrarAlertaSimple(
                    "Error",
                    "No se pudo abrir la ventana para generar el reporte de tutoría.",
                    Alert.AlertType.ERROR
            );
        } catch (Exception e) {
            LOGGER.error("Error inesperado al abrir la ventana para generar el reporte de tutoría", e);
            e.printStackTrace();
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
            Utilidades.clicVolverMenuPrincipal(event);
        } catch (IOException ex) {
            LOGGER.error("Error al volver al menú principal desde el menú de reportes", ex);
            ex.printStackTrace();
        } catch (Exception e) {
            LOGGER.error("Error inesperado al volver al menú principal desde el menú de reportes", e);
            e.printStackTrace();
        }
    }
}
