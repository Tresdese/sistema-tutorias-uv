/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/javafx/FXMLController.java to edit this template
 */
package com.sistematutoriascomp.sistematutorias.controller.reporte;

import java.net.URL;
import java.util.ResourceBundle;

import com.sistematutoriascomp.sistematutorias.model.dao.ReporteGeneralDAO;
import com.sistematutoriascomp.sistematutorias.model.pojo.ReporteGeneral;
import com.sistematutoriascomp.sistematutorias.utilidad.Utilidades;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.Node;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.control.ComboBox;
import javafx.scene.control.TextArea;
import javafx.stage.Stage;

/**
 * FXML Controller class
 *
 * @author super
 */
public class FXMLRespuestaReporteGeneralController implements Initializable {

    @FXML
    private Button btnVolver;
    @FXML
    private ComboBox<String> cbEstado;
    @FXML
    private TextArea txtObservacionesExtra;
    @FXML
    private Button btnCancelar;
    @FXML
    private Button btnGuardar;

    private ReporteGeneral reporteEdicion;

    /**
     * Initializes the controller class.
     */
    @Override
    public void initialize(URL url, ResourceBundle rb) {
        cargarEstadosReporte();
    }    

    @FXML
    private void onVolver(ActionEvent event) {
        cerrarVentana(event);
    }

    @FXML
    private void onCancelar(ActionEvent event) {
        cerrarVentana(event);
    }

    @FXML
    private void onGuardar(ActionEvent event) {
        if (validarCampos()) {
            clicOnGuardar(event);
        }
    }

    private void clicOnGuardar(ActionEvent event) {

        String estadoSeleccionado = cbEstado.getValue();
        String observacionesExtra = txtObservacionesExtra.getText();

        try {
            reporteEdicion.setEstado(estadoSeleccionado);
            reporteEdicion.setObservaciones(observacionesExtra);

            ReporteGeneralDAO reporteGeneralDAO = new ReporteGeneralDAO();
            boolean exito = reporteGeneralDAO.actualizarRespuestas(reporteEdicion);
            if (exito) {
                Utilidades.mostrarAlertaSimple("Éxito", "Respuestas guardadas correctamente.", Alert.AlertType.INFORMATION);
            } else {
                Utilidades.mostrarAlertaSimple("Error", "No se pudieron guardar las respuestas.", Alert.AlertType.ERROR);
            }
        } catch (Exception e) {
            Utilidades.mostrarAlertaSimple("Error", "Ocurrió un error al guardar las respuestas: " + e.getMessage(), Alert.AlertType.ERROR);
        }

        cerrarVentana(event);
    }

    private boolean validarCampos() {
        boolean respuesta = true;
        if (txtObservacionesExtra.getText().trim().isEmpty()) {
            Utilidades.mostrarAlertaSimple("Campos vacíos", "Las observaciones extra son obligatorias", Alert.AlertType.WARNING);
            respuesta = false;
        } else if (cbEstado.getSelectionModel().isEmpty()) {
            Utilidades.mostrarAlertaSimple("Campos vacíos", "El estado es obligatorio", Alert.AlertType.WARNING);
            respuesta = false;
        }
        return respuesta;
    }

    public void inicializarParaEdicion(ReporteGeneral reporte) {
        this.reporteEdicion = reporte;

        cbEstado.setValue(reporte.getEstado());
        txtObservacionesExtra.setText(reporte.getObservaciones());
    }
    
    private void cargarEstadosReporte() {
        cbEstado.getItems().addAll("Devuelto con observaciones", "Aprobado");
    }

    private void cerrarVentana(ActionEvent event) {
        Stage stage = (Stage) ((Node) event.getSource()).getScene().getWindow();
        stage.close();
    }
}
