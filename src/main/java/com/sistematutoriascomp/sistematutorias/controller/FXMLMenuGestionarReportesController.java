package com.sistematutoriascomp.sistematutorias.controller;

import java.io.IOException;
import java.net.URL;
import java.util.HashMap;
import java.util.ResourceBundle;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import com.sistematutoriascomp.sistematutorias.dominio.ReporteTutoriaImp;
import com.sistematutoriascomp.sistematutorias.model.pojo.Tutor;
import com.sistematutoriascomp.sistematutorias.utilidad.Sesion;
import com.sistematutoriascomp.sistematutorias.utilidad.Utilidades;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;

public class FXMLMenuGestionarReportesController implements Initializable {
    private final Logger LOGGER = LogManager.getLogger(FXMLMenuGestionarReportesController.class);
    
    @FXML private Button btnGenerarReporteTutoria;
    @FXML private Button btnConsultarReportesTutoria;
    @FXML private Button btnGenerarReporteGeneral;
    @FXML private Button btnConsultarReportesGenerales;

    @Override
    public void initialize(URL url, ResourceBundle rb) {
        cargarDatosUsuario();
    }
    
    private void cargarDatosUsuario() {
        Tutor tutor = Sesion.getTutorSesion();
        String rol = Sesion.getRolActual();

        if (rol.equals("ACADEMICO")) {
            btnGenerarReporteTutoria.setVisible(true);
            btnConsultarReportesTutoria.setVisible(true);
            btnGenerarReporteGeneral.setVisible(false);
            btnConsultarReportesGenerales.setVisible(false);
        } else if (rol.equals("COORDINADOR") || rol.equals("ADMINISTRADOR")) {
            btnGenerarReporteTutoria.setVisible(true);
            btnConsultarReportesTutoria.setVisible(true);
            btnGenerarReporteGeneral.setVisible(true);
            btnConsultarReportesGenerales.setVisible(true);
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
    private void clicConsultarReportesTutoria(ActionEvent event) {
        String rol = Sesion.getRolActual();
        
        if (rol.equals("ACADEMICO")) {
            irPantalla("/reporte/FXMLListadoReportesTutoria.fxml", "Lista de Reportes de Tutoría", event);
        } else if (rol.equals("COORDINADOR") || rol.equals("ADMINISTRADOR")) {
            irPantalla("/reporte/FXMLListadoReportesCoordinador.fxml", "Lista de Reportes de Tutoría", event);
        }
    }
    
    @FXML
    private void clicGenerarReporteGeneral(ActionEvent event) {
        irPantalla("/reporte/FXMLGenerarReporteGeneral.fxml", "Generar Reporte General de Tutoría", event);
    }
    
    @FXML
    private void clicConsultarReportesGenerales(ActionEvent event) {
        irPantalla("/reporte/FXMLAdministrarReporteGeneral.fxml", "Consultar lista de Reportes Generales de Tutoria", event);
    }
    
    private void irPantalla(String ruta, String titulo, ActionEvent event) {
        try {
            Utilidades.goToWindow(ruta, event, titulo);
        } catch (IOException ex) {
            ex.printStackTrace();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
    
    @FXML
    private void clicVolverMenuPrincipal(ActionEvent event) {
        try {
            Utilidades.volverMenuPrincipal(event);
        } catch (IOException ex) {
            ex.printStackTrace();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
    
    @FXML
    private void clicCerrarSesion(ActionEvent event) {
        Sesion.cerrarSesion(); 
        try {
            Utilidades.clicCerrarSesion(event);
        } catch (IOException ex) {
            ex.printStackTrace();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}
