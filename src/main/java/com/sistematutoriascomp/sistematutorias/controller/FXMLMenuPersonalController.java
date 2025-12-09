/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/javafx/FXMLController.java to edit this template
 */
package com.sistematutoriascomp.sistematutorias.controller;

import java.io.IOException;
import java.net.URL;
import java.util.ResourceBundle;
import java.util.logging.Logger;

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
public class FXMLMenuPersonalController implements Initializable {

    private static final Logger LOGGER = Logger.getLogger(FXMLMenuPersonalController.class.getName());

    @FXML
    private Label lbNombreUsuario;
    @FXML
    private Label lbInfoExtra;
    @FXML
    private Button btnRegistrarUsuario;
    @FXML
    private Button btnRegistrarTutorado;
    @FXML
    private Button btnVerReportesGenerales;
    @FXML
    private Button btnCrearReporteGeneral;
    @FXML
    private Button btnClicCerrarSesion;
    @FXML
    private Button btnVolver;

    /**
     * Initializes the controller class.
     */
    @Override
    public void initialize(URL url, ResourceBundle rb) {
        cargarDatosUsuario();
    }

    @FXML
    private void onClicCerrarSesion(ActionEvent event) {
        irInicioSesion(event);
    }

    @FXML
    private void onVolver(ActionEvent event) {
        irVentanaAtras(event);
    }

    @FXML
    private void btnRegistrarUsuario(ActionEvent event) {
        irRegistroUsuario();
    }

    @FXML
    private void btnRegistrarTutorado(ActionEvent event) {
        irRegistroTutorado();
    }

    @FXML
    private void btnVerReportesGenerales(ActionEvent event) {
        irVerReportesGenerales();
    }

    @FXML
    private void btnCrearReporteGeneral(ActionEvent event) {
        irCrearReporteGeneral();
    }

    private void cargarDatosUsuario() {
        Tutor tutor = Sesion.getTutorSesion();
        String rol = Sesion.getRolActual();

        if (tutor != null) {
            if (rol.equals("COORDINADOR")) {
                btnRegistrarUsuario.setVisible(false);
                btnRegistrarTutorado.setVisible(true);
                btnVerReportesGenerales.setVisible(true);
                btnCrearReporteGeneral.setVisible(true);
            } else if (rol.equals("ADMINISTRADOR")) {
                btnRegistrarUsuario.setVisible(true);
                btnRegistrarTutorado.setVisible(true);
                btnVerReportesGenerales.setVisible(true);
                btnCrearReporteGeneral.setVisible(true);
            }
        }
    }

    private void irRegistroUsuario() {
        try {
            Utilidades.openModal("/usuario/FXMLRegistrarAcademico.fxml", "Registrar Académico");
        } catch (IOException e) {
            LOGGER.severe("Error al cambiar a la ventana de registro de usuario: " + e.getMessage());
            e.printStackTrace();
        } catch (Exception ex) {
            LOGGER.severe("Error inesperado al cambiar a la ventana de registro de usuario: " + ex.getMessage());
            ex.printStackTrace();
        }
    }

    private void irRegistroTutorado() {
        try {
            Utilidades.openModal("/usuario/FXMLRegistrarTutorado.fxml", "Registrar Tutorado");
        } catch (IOException e) {
            LOGGER.severe("Error al cambiar a la ventana de registro de tutorado: " + e.getMessage());
            e.printStackTrace();
        } catch (Exception ex) {
            LOGGER.severe("Error inesperado al cambiar a la ventana de registro de tutorado: " + ex.getMessage());
            ex.printStackTrace();
        }
    }

    private void irVerReportesGenerales() {
        try {
            Utilidades.openModal("/reporte/FXMLAdministrarReporteGeneral.fxml", "Ver Reportes Generales");
        } catch (Exception ex) {
            LOGGER.severe("Error inesperado al cambiar a la ventana de ver reportes generales: " + ex.getMessage());
            ex.printStackTrace();
        }
    }

    private void irCrearReporteGeneral() {
        try {
            Utilidades.openModal("/reporte/FXMLFormularioReporteGeneral.fxml", "Crear Reporte General");
        } catch (IOException e) {
            LOGGER.severe("Error al cambiar a la ventana de crear reporte general: " + e.getMessage());
            e.printStackTrace();
        } catch (Exception ex) {
            LOGGER.severe("Error inesperado al cambiar a la ventana de crear reporte general: " + ex.getMessage());
            ex.printStackTrace();
        }
    }

    private void irVentanaAtras(ActionEvent event) {
        try {
            Utilidades.goToWindow("/FXMLMenuPrincipal.fxml", event, "Menú Principal");
        } catch (IOException e) {
            LOGGER.severe("Error al cambiar a la ventana del menú principal: " + e.getMessage());
            e.printStackTrace();
        } catch (Exception ex) {
            LOGGER.severe("Error inesperado al cambiar a la ventana del menú principal: " + ex.getMessage());
            ex.printStackTrace();
        }
    }

    private void irInicioSesion(ActionEvent event) {
        try {
            Utilidades.goToWindow("/FXMLInicioSesion.fxml", event, "Iniciar Sesión");
        } catch (IOException e) {
            LOGGER.severe("Error al volver a la pantalla de inicio de sesión: " + e.getMessage());
            e.printStackTrace();
        } catch (Exception ex) {
            LOGGER.severe("Error inesperado al volver a la pantalla de inicio de sesión: " + ex.getMessage());
            ex.printStackTrace();
        }
    }
}
