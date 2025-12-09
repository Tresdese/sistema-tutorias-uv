/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/javafx/FXMLController.java to edit this template
 */
package com.sistematutoriascomp.sistematutorias.controller;

import java.io.IOException;
import java.net.URL;
import java.util.ResourceBundle;

import com.sistematutoriascomp.sistematutorias.model.pojo.Tutor;
import com.sistematutoriascomp.sistematutorias.utilidad.Sesion;
import com.sistematutoriascomp.sistematutorias.utilidad.Utilidades;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Button;
import javafx.scene.control.Label;

/**
 * FXML Controller class
 *
 * @author HP
 */
public class FXMLMenuPrincipalController implements Initializable {

    @FXML
    private Button btnTutoria;
    @FXML
    private Button btnReporte;
    @FXML
    private Button btnPersonal;
    @FXML
    private Label lbInfoExtra;
    @FXML
    private Label lbNombreUsuario;
    @FXML
    private Button btnMenuEspecial;

    /**
     * Initializes the controller class.
     */
    @Override
    public void initialize(URL url, ResourceBundle rb) {
        configurarPermisos();
        cargarDatosUsuario();
    }

    private void cargarDatosUsuario() {
        String rol = Sesion.getRolActual();
        String nombreCompleto = "";

        // Verificamos el rol para saber de qué objeto sacar el nombre
        if ("ACADEMICO".equals(rol)) {
            Tutor tutor = Sesion.getTutorSesion();
            if (tutor != null) {
                nombreCompleto = tutor.getNombre() + " "
                        + tutor.getApellidoPaterno() + " "
                        + tutor.getApellidoMaterno();
            }
        } else if ("COORDINADOR".equals(rol)) {
            Tutor tutor = Sesion.getTutorSesion();
            if (tutor != null) {
                nombreCompleto = tutor.getNombre() + " "
                        + tutor.getApellidoPaterno() + " "
                        + tutor.getApellidoMaterno();
            }
        } else if ("ADMINISTRADOR".equals(rol)) {
            Tutor tutor = Sesion.getTutorSesion();
            if (tutor != null) {
                nombreCompleto = tutor.getNombre() + " "
                + tutor.getApellidoPaterno() + " "
                + tutor.getApellidoMaterno();
            }
        }
        
        lbNombreUsuario.setText(nombreCompleto);
    }

    private void configurarPermisos() {
        String rol = Sesion.getRolActual();

        // Primero ocultamos todo por seguridad o lo dejamos visible y desactivamos
        btnTutoria.setVisible(false);
        btnReporte.setVisible(false);
        btnPersonal.setVisible(false);

        if ("ACADEMICO".equals(rol)) {
            btnTutoria.setVisible(true);
            btnReporte.setVisible(true);
            btnPersonal.setVisible(false);
            btnMenuEspecial.setVisible(false);
        } else if ("COORDINADOR".equals(rol)) {
            btnTutoria.setVisible(true);
            btnReporte.setVisible(true);
            btnPersonal.setVisible(false);
            btnMenuEspecial.setVisible(true);
        } else if ("ADMINISTRADOR".equals(rol)) {
            btnTutoria.setVisible(false);
            btnReporte.setVisible(false);
            btnPersonal.setVisible(true);
            btnMenuEspecial.setVisible(true);
        }
    }

    @FXML
    private void btnClicTutoria(ActionEvent event) {
        irPantalla("/FXMLMenuTutoria.fxml", "Menú Tutoría", event);
    }

    @FXML
    private void btnClicReporte(ActionEvent event) {
        irPantalla("/FXMLMenuReporte.fxml", "Menú Reporte", event);
    }

    @FXML
    private void btnClicPersonal(ActionEvent event) {
        
    }

    @FXML
    private void btnClicMenuEspecial(ActionEvent event) {
        irPantalla("/FXMLMenuPersonal.fxml", "Menú Especial", event);
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
    private void btnClicSalir(ActionEvent event) {
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
