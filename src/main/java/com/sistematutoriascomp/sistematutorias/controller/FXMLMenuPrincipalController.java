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

public class FXMLMenuPrincipalController implements Initializable {

    @FXML
    private Label lbNombreUsuario;
    @FXML
    private Label lbRol;
    @FXML
    private Button btnGestionarTutorias;
    @FXML
    private Button btnGestionarReportes;
    @FXML
    private Button btnGestionarUsuarios;

    @Override
    public void initialize(URL url, ResourceBundle rb) {
        configurarPermisos();
        cargarDatosUsuario();
    }

    private void configurarPermisos() {
        String rol = Sesion.getRolActual();

        btnGestionarTutorias.setVisible(false);
        btnGestionarReportes.setVisible(false);
        btnGestionarUsuarios.setVisible(false);

        if ("ACADEMICO".equals(rol)) {
            btnGestionarTutorias.setVisible(true);
            btnGestionarReportes.setVisible(true);
            btnGestionarUsuarios.setVisible(false);
        } else if ("COORDINADOR".equals(rol)) {
            btnGestionarTutorias.setVisible(true);
            btnGestionarReportes.setVisible(true);
            btnGestionarUsuarios.setVisible(false);
        } else if ("ADMINISTRADOR".equals(rol)) {
            btnGestionarTutorias.setVisible(true);
            btnGestionarReportes.setVisible(true);
            btnGestionarUsuarios.setVisible(true);
        }
    }

    private void cargarDatosUsuario() {
        String rol = Sesion.getRolActual();
        String nombreCompleto = "";

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
        lbRol.setText(rol);
    }

    @FXML
    private void clicGestionarTutorias(ActionEvent event) {
        irPantalla("/FXMLMenuGestionarTutorias.fxml", "Menú Gestión de Tutorías", event);
    }

    @FXML
    private void clicGestionarReportes(ActionEvent event) {
        irPantalla("/FXMLMenuGestionarReportes.fxml", "Menú Gestión de Reportes", event);
    }

    @FXML
    private void clicGestionarUsuarios(ActionEvent event) {
        irPantalla("/FXMLMenuGestionarUsuarios.fxml", "Menú Gestión de Usuarios", event);
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
