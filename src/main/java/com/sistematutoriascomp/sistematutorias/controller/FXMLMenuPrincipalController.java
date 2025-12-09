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

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.stage.Stage;

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
        if ("TUTOR".equals(rol)) {
            Tutor tutor = Sesion.getTutorSesion();
            if (tutor != null) {
                nombreCompleto = tutor.getNombre() + " "
                        + tutor.getApellidoPaterno() + " "
                        + tutor.getApellidoMaterno();
            }
        }
        /* // A FUTURO: Cuando agregues otros roles, solo añades el else if:
        else if ("COORDINADOR".equals(rol)) {
            Coordinador coord = Sesion.getCoordinadorSesion();
            nombreCompleto = coord.getNombre() + ...;
        }
         */

        // Mostramos el nombre en la etiqueta
        lbNombreUsuario.setText(nombreCompleto);
    }

    private void configurarPermisos() {
        String rol = Sesion.getRolActual();

        // Primero ocultamos todo por seguridad o lo dejamos visible y desactivamos
        btnTutoria.setVisible(false);
        btnReporte.setVisible(false);
        btnPersonal.setVisible(false);

        if ("TUTOR".equals(rol)) {
            // El tutor solo ve Tutoría y Reporte
            btnTutoria.setVisible(true);
            btnReporte.setVisible(true);
        }
        // else if ("COORDINADOR".equals(rol)) { ... logica futuro }
    }

    @FXML
    private void btnClicTutoria(ActionEvent event) {
        irPantalla("/sistematutorias/vista/FXMLMenuTutoria.fxml", "Menú Tutoría");
    }

    @FXML
    private void btnClicReporte(ActionEvent event) {
        irPantalla("/sistematutorias/vista/FXMLMenuReporte.fxml", "Menú Reporte");
    }

    @FXML
    private void btnClicPersonal(ActionEvent event) {
        irPantalla("/sistematutorias/vista/FXMLMenuPersonal.fxml", "Menú Personal");
    }

    private void irPantalla(String ruta, String titulo) {
        try {
            Stage escenario = (Stage) btnTutoria.getScene().getWindow();
            FXMLLoader loader = new FXMLLoader(getClass().getResource(ruta));
            Parent root = loader.load();
            Scene escena = new Scene(root);
            escenario.setScene(escena);
            escenario.setTitle(titulo);
            escenario.show();
        } catch (IOException ex) {
            ex.printStackTrace();
        }
    }

    @FXML
    private void btnClicSalir(ActionEvent event) {
        Sesion.cerrarSesion(); 
        try {
            Parent vista = FXMLLoader.load(getClass().getResource("vista/FXMLInicioSesion.fxml"));
            Scene escena = new Scene(vista);
            Stage stPrincipal = (Stage) btnTutoria.getScene().getWindow();
            stPrincipal.setScene(escena);
            stPrincipal.setTitle("Iniciar sesión");
            stPrincipal.show();
        } catch (IOException ex) {
            ex.printStackTrace();
        }
    }
}
