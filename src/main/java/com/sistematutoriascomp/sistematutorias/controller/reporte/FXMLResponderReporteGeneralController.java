/*
 * Autor: Delgado Santiago Darlington Diego
 * Ultima modificación hecha por: Delgado Santiago Darlington Diego
 * Versión: 5.0
 */
package com.sistematutoriascomp.sistematutorias.controller.reporte;

import java.net.URL;
import java.sql.SQLException;
import java.util.ResourceBundle;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import com.sistematutoriascomp.sistematutorias.model.dao.ReporteGeneralDAO;
import com.sistematutoriascomp.sistematutorias.model.pojo.ReporteGeneral;
import com.sistematutoriascomp.sistematutorias.utilidad.Utilidades;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Alert;
import javafx.scene.control.TextArea;
import javafx.stage.Stage;

public class FXMLResponderReporteGeneralController implements Initializable {

    private static final Logger LOGGER = LogManager.getLogger(FXMLResponderReporteGeneralController.class);

    @FXML
    private TextArea taRespuesta;

    private final ReporteGeneralDAO reporteGeneralDAO = new ReporteGeneralDAO();
    private ReporteGeneral reporte;
    private boolean respuestaGuardada = false;

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
    }

    public void inicializarReporte(ReporteGeneral reporte) {
        this.reporte = reporte;
        if (reporte != null && reporte.getObservaciones() != null) {
            taRespuesta.setText(reporte.getObservaciones());
        }
    }

    @FXML
    private void onGuardar(ActionEvent event) {
        if (reporte == null) {
            Utilidades.mostrarAlertaSimple("Sin reporte",
                    "No se encontró un reporte general para responder.",
                    Alert.AlertType.WARNING);
            return;
        }

        String respuesta = taRespuesta.getText() != null ? taRespuesta.getText().trim() : "";
        if (respuesta.isEmpty()) {
            Utilidades.mostrarAlertaSimple("Campo vacío",
                    "Escribe una respuesta antes de guardar.",
                    Alert.AlertType.WARNING);
            return;
        }

        reporte.setObservaciones(respuesta);
        if (reporte.getEstado() == null || reporte.getEstado().isBlank()) {
            reporte.setEstado("Revisado");
        } else {
            reporte.setEstado("Revisado");
        }

        try {
            boolean actualizado = reporteGeneralDAO.actualizarRespuestas(reporte);
            if (actualizado) {
                respuestaGuardada = true;
                Utilidades.mostrarAlertaSimple("Éxito",
                        "Respuesta guardada correctamente.",
                        Alert.AlertType.INFORMATION);
                cerrarVentana();
            } else {
                Utilidades.mostrarAlertaSimple("Sin cambios",
                        "No se pudo guardar la respuesta del reporte.",
                        Alert.AlertType.WARNING);
            }
        } catch (SQLException e) {
            LOGGER.error("Error de base de datos al responder reporte general", e);
            Utilidades.mostrarAlertaSimple("Error de base de datos",
                    "No se pudo guardar la respuesta: " + e.getMessage(),
                    Alert.AlertType.ERROR);
        } catch (Exception e) {
            LOGGER.error("Error inesperado al responder reporte general", e);
            Utilidades.mostrarAlertaSimple("Error inesperado",
                    "Ocurrió un error inesperado: " + e.getMessage(),
                    Alert.AlertType.ERROR);
        }
    }

    @FXML
    private void onCancelar(ActionEvent event) {
        cerrarVentana();
    }

    private void cerrarVentana() {
        Stage stage = (Stage) taRespuesta.getScene().getWindow();
        stage.close();
    }

    public boolean isRespuestaGuardada() {
        return respuestaGuardada;
    }
}
