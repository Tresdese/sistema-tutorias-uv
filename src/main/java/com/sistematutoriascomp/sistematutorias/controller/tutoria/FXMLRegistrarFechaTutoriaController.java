package com.sistematutoriascomp.sistematutorias.controller.tutoria;

import com.sistematutoriascomp.sistematutorias.dominio.FechaTutoriaImp;
import com.sistematutoriascomp.sistematutorias.model.dao.FechaTutoriaDAO;
import com.sistematutoriascomp.sistematutorias.model.pojo.FechaTutoria;
import com.sistematutoriascomp.sistematutorias.utilidad.Sesion;
import com.sistematutoriascomp.sistematutorias.utilidad.Utilidades;

import java.io.IOException;
import java.net.URL;
import java.sql.SQLException;
import java.time.LocalDate;
import java.util.HashMap;
import java.util.ResourceBundle;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.control.DateCell;
import javafx.scene.control.DatePicker;
import javafx.scene.control.TextArea;
import javafx.scene.control.TextField;
import javafx.util.Callback;

public class FXMLRegistrarFechaTutoriaController implements Initializable {

    private static final Logger LOGGER = LogManager.getLogger(FXMLRegistrarFechaTutoriaController.class);

    @FXML
    private TextField tfNumeroSesion;
    @FXML
    private DatePicker dpFechaTutoria;
    @FXML
    private TextField tfTitulo;
    @FXML
    private TextArea taDescripcion;
    @FXML
    private Button btnCancelar;
    @FXML
    private Button btnRegistrar;

    private int numeroSesionAuto = 0;

    @Override
    public void initialize(URL url, ResourceBundle rb) {
        configurarVentana();
    }

    private void configurarVentana() {
        configurarFechasDisponibles();
        cargarSiguienteSesion();
    }

    private void configurarFechasDisponibles() {
        Callback<DatePicker, DateCell> dayCellFactory = dp -> new DateCell() {
            @Override
            public void updateItem(LocalDate item, boolean empty) {
                super.updateItem(item, empty);
                if (item.isBefore(LocalDate.now())) {
                    setDisable(true);
                    setStyle("-fx-background-color: #ffc0cb;");
                }
            }
        };
        dpFechaTutoria.setDayCellFactory(dayCellFactory);
    }

    private void cargarSiguienteSesion() {
        try {
            int idPeriodo = Sesion.getIdPeriodoActual();
            if (idPeriodo <= 0) {
                idPeriodo = FechaTutoriaDAO.obtenerIdPeriodoActual();
            }

            numeroSesionAuto = FechaTutoriaDAO.comprobarSiguienteSesion(idPeriodo);

            if (numeroSesionAuto > 3) {
                tfNumeroSesion.setText("COMPLETO");
                btnRegistrar.setDisable(true);
                Utilidades.mostrarAlertaSimple("Periodo Completo", "Ya se han registrado las 3 sesiones para este periodo.", Alert.AlertType.INFORMATION);
            } else {
                tfNumeroSesion.setText(String.valueOf(numeroSesionAuto));
                btnRegistrar.setDisable(false); // Reactivar por si acaso
            }

        } catch (SQLException ex) {
            LOGGER.error("Error al calcular la siguiente sesión", ex);
            tfNumeroSesion.setText("Error");
            Utilidades.mostrarAlertaSimple("Error de Conexión", "No se pudo calcular el número de sesión.", Alert.AlertType.ERROR);
        }
    }

    @FXML
    private void clicRegistrar(ActionEvent event) {
        try {
            if (!validarCampos()) {
                return;
            }

            FechaTutoria nuevaFecha = new FechaTutoria();
            nuevaFecha.setNumeroSesion(numeroSesionAuto);
            nuevaFecha.setFecha(dpFechaTutoria.getValue());
            nuevaFecha.setTitulo(tfTitulo.getText().trim());
            nuevaFecha.setDescripcion(taDescripcion.getText().trim());

            registrarInformacion(nuevaFecha);

        } catch (Exception ex) {
            LOGGER.error("Error inesperado al intentar registrar fecha de tutoría", ex);
            Utilidades.mostrarAlertaSimple("Error inesperado", "Ocurrió un error al procesar el registro.", Alert.AlertType.ERROR);
        }
    }

    private boolean validarCampos() {
        if (dpFechaTutoria.getValue() == null) {
            Utilidades.mostrarAlertaSimple("Campos requeridos", "Por favor seleccione la fecha de la tutoría.", Alert.AlertType.WARNING);
            return false;
        }

        if (dpFechaTutoria.getValue().isBefore(LocalDate.now())) {
            Utilidades.mostrarAlertaSimple("Fecha inválida", "No puede seleccionar una fecha pasada.", Alert.AlertType.WARNING);
            return false;
        }

        if (tfTitulo.getText().trim().isEmpty()) {
            Utilidades.mostrarAlertaSimple("Campos requeridos", "El título de la sesión es obligatorio.", Alert.AlertType.WARNING);
            return false;
        }

        if (taDescripcion.getText() == null) {
            taDescripcion.setText("");
        }

        return true;
    }

    private void registrarInformacion(FechaTutoria fecha) {
        HashMap<String, Object> respuesta = FechaTutoriaImp.registrarFechaTutoria(fecha);

        if (!(boolean) respuesta.get("error")) {
            Utilidades.mostrarAlertaSimple("Registro exitoso", (String) respuesta.get("mensaje"), Alert.AlertType.INFORMATION);
            limpiarCampos();
        } else {
            Utilidades.mostrarAlertaSimple("Error al registrar", (String) respuesta.get("mensaje"), Alert.AlertType.ERROR);
        }
    }

    @FXML
    private void clicCancelar(ActionEvent event) {
        limpiarCampos();
        irAtras(event);
    }

    private void limpiarCampos() {
        dpFechaTutoria.setValue(null);
        tfTitulo.clear();
        taDescripcion.clear();
        cargarSiguienteSesion();
    }

    private void irAtras(ActionEvent event) {
        try {
            Utilidades.volverMenuGestionarTutorias(event);
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
