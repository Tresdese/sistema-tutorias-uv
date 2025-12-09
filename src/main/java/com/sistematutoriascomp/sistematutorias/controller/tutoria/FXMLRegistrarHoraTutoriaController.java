/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/javafx/FXMLController.java to edit this template
 */
package com.sistematutoriascomp.sistematutorias.controller.tutoria;

import java.io.IOException;
import java.net.URL;
import java.time.LocalTime;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.ResourceBundle;

import com.sistematutoriascomp.sistematutorias.dominio.TutoriaImp;
import com.sistematutoriascomp.sistematutorias.model.pojo.FechaTutoria;
import com.sistematutoriascomp.sistematutorias.model.pojo.Tutoria;
import com.sistematutoriascomp.sistematutorias.utilidad.Sesion;
import com.sistematutoriascomp.sistematutorias.utilidad.Utilidades;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Alert;
import javafx.scene.control.ComboBox;
import javafx.scene.control.Label;
import javafx.scene.control.Spinner;
import javafx.scene.control.SpinnerValueFactory;

public class FXMLRegistrarHoraTutoriaController implements Initializable {

    @FXML
    private ComboBox<FechaTutoria> cbFechas;
    @FXML
    private Spinner<Integer> spHora;
    @FXML
    private Spinner<Integer> spMinuto;
    @FXML
    private Label lbErrorFecha;
    @FXML
    private Label lbErrorHora;

    @Override
    public void initialize(URL url, ResourceBundle rb) {
        configurarSpinners();
        cargarFechas();
    }

    private void configurarSpinners() {
        SpinnerValueFactory<Integer> horasFactory = new SpinnerValueFactory.IntegerSpinnerValueFactory(7, 20, 10);
        spHora.setValueFactory(horasFactory);
        SpinnerValueFactory<Integer> minutosFactory = new SpinnerValueFactory.IntegerSpinnerValueFactory(0, 59, 0);
        spMinuto.setValueFactory(minutosFactory);
    }

    private void cargarFechas() {
        HashMap<String, Object> respuesta = TutoriaImp.obtenerFechasPeriodoActual();
        if (!(boolean) respuesta.get("error")) {
            ArrayList<FechaTutoria> lista = (ArrayList<FechaTutoria>) respuesta.get("fechas");
            ObservableList<FechaTutoria> fechasObs = FXCollections.observableArrayList(lista);
            cbFechas.setItems(fechasObs);
        }
    }

    @FXML
    private void clicRegistrar(ActionEvent event) {
        lbErrorFecha.setText("");
        lbErrorHora.setText("");
        FechaTutoria fechaSeleccionada = cbFechas.getValue();
        Integer hora = spHora.getValue();
        Integer minuto = spMinuto.getValue();
        boolean valido = true;
        if (fechaSeleccionada == null) {
            lbErrorFecha.setText("Selecciona una fecha.");
            lbErrorFecha.setVisible(true); // Asegurar visibilidad
            valido = false;
        }
        if (hora == null || hora < 7 || hora > 20) {
            lbErrorHora.setText("Hora inválida (7-20).");
            lbErrorHora.setVisible(true);
            valido = false;
        }
        if (minuto == null || minuto < 0 || minuto > 59) {
             valido = false;
        }
        if (!valido) {
            return;
        }
        Tutoria nuevaTutoria = new Tutoria();
        nuevaTutoria.setIdTutor(Sesion.getTutorSesion().getIdTutor());
        nuevaTutoria.setIdPeriodo(Sesion.getIdPeriodoActual());
        nuevaTutoria.setFecha(fechaSeleccionada.getFecha());
        nuevaTutoria.setHoraInicio(LocalTime.of(hora, minuto));
        guardarTutoria(nuevaTutoria);
        irAtras(event);
    }

    private void guardarTutoria(Tutoria tutoria) {
        HashMap<String, Object> respuesta = TutoriaImp.registrarHorarioTutoria(tutoria);
        if (!(boolean) respuesta.get("error")) {
            Utilidades.mostrarAlertaSimple("Éxito", (String) respuesta.get("mensaje"), Alert.AlertType.INFORMATION);
        } else {
            Utilidades.mostrarAlertaSimple("Error", (String) respuesta.get("mensaje"), Alert.AlertType.ERROR);
        }
    }

    @FXML
    private void clicCancelar(ActionEvent event) {
        cbFechas.getSelectionModel().clearSelection();
        spHora.getValueFactory().setValue(10);
        spMinuto.getValueFactory().setValue(0);
        lbErrorFecha.setText("");
    }

    @FXML
    private void clicVolver(ActionEvent event) {
        irAtras(event);
    }

    private void irAtras(ActionEvent event) {
        try {
            Utilidades.goToWindow("/FXMLMenuTutoria.fxml", event, "Menú Tutoría");
        } catch (IOException ex) {
            ex.printStackTrace();
            Utilidades.mostrarAlertaSimple("Error", "No se pudo volver al menú.", Alert.AlertType.ERROR);
        } catch (NullPointerException ex) {
            ex.printStackTrace();
            Utilidades.mostrarAlertaSimple("Error", "Recurso no encontrado al volver al menú.", Alert.AlertType.ERROR);
        } catch (Exception ex) {
            ex.printStackTrace();
            Utilidades.mostrarAlertaSimple("Error", "Error inesperado al volver al menú.", Alert.AlertType.ERROR);
        }
    }
}
