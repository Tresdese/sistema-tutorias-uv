/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/javafx/FXMLController.java to edit this template
 */
package com.sistematutoriascomp.sistematutorias.controller;

import java.io.IOException;
import java.net.URL;
import java.util.HashMap;
import java.util.ResourceBundle;

import com.sistematutoriascomp.sistematutorias.dominio.AsistenciaImp;
import com.sistematutoriascomp.sistematutorias.dominio.TutoriaImp;
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
public class FXMLMenuTutoriaController implements Initializable {

    @FXML
    private Button btnRegistrarHorario;
    @FXML
    private Button btnRegistrarAsistencia;
    @FXML
    private Button btnRegistrarFechaTutoria;
    @FXML
    private Button btnAsignarTutorado;

    /**
     * Initializes the controller class.
     */
    @Override
    public void initialize(URL url, ResourceBundle rb) {
        configurarVistaPorRol();
    }

    private void configurarVistaPorRol() {
        String rol = Sesion.getRolActual();

        btnRegistrarHorario.setVisible(false);
        btnRegistrarAsistencia.setVisible(false);
        if (btnRegistrarFechaTutoria != null && btnAsignarTutorado != null) {
            btnRegistrarFechaTutoria.setVisible(false);
            btnAsignarTutorado.setVisible(false);

        }

        if ("TUTOR".equals(rol)) {
            btnRegistrarHorario.setVisible(true);
            btnRegistrarAsistencia.setVisible(true);
        }
    }

    @FXML
    private void clicRegistrarHoraTutoria(ActionEvent event) {
        HashMap<String, Object> respuesta = TutoriaImp.obtenerFechasPeriodoActual();
        if (!(boolean) respuesta.get("error")) {
            irPantalla("/tutoria/FXMLRegistrarHoraTutoria.fxml", "Registrar Hora", event);
        } else {
            Utilidades.mostrarAlertaSimple("No se puede continuar",
                    (String) respuesta.get("mensaje"),
                    Alert.AlertType.WARNING);
        }
    }

    @FXML
    private void clicRegistrarAsistenciaTutorado(ActionEvent event) {
        int idTutor = Sesion.getTutorSesion().getIdTutor();

        HashMap<String, Object> respuesta = AsistenciaImp.obtenerSesionesTutor(idTutor);

        if (!(boolean) respuesta.get("error")) {
            irPantalla("/tutoria/FXMLRegistrarAsistenciaTutorado.fxml", "Registrar Asistencia", event);
        } else {
            Utilidades.mostrarAlertaSimple("No se puede continuar",
                    (String) respuesta.get("mensaje"),
                    Alert.AlertType.WARNING);
        }
    }

    @FXML
    private void clicRegistrarFechaTutoria(ActionEvent event) {
    }

    @FXML
    private void clicAsignarTutorado(ActionEvent event) {
    }

    @FXML
    private void clicVolverMenuPrincipal(ActionEvent event) {
        try {
            Utilidades.clicVolverMenuPrincipal(event);
        } catch (IOException ex) {
            ex.printStackTrace();
        }
    }

    private void irPantalla(String ruta, String titulo, ActionEvent event) {
        try {
            Utilidades.goToWindow(ruta, event, titulo);
        } catch (IOException ex) {
            ex.printStackTrace();
        }
    }
}