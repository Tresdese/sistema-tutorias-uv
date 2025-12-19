/*
 * Autor: Hernandez Romero Jarly
 * Ultima modificación hecha por: Delgado Santiago Darlington Diego
 * Versión: 2.0
 */
package com.sistematutoriascomp.sistematutorias.controller;

import java.io.IOException;
import java.net.URL;
import java.util.ResourceBundle;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import com.sistematutoriascomp.sistematutorias.model.pojo.Tutor;
import com.sistematutoriascomp.sistematutorias.utilidad.Sesion;
import com.sistematutoriascomp.sistematutorias.utilidad.Utilidades;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Button;

public class FXMLMenuGestionarUsuariosController implements Initializable {
    private static final Logger LOGGER = LogManager.getLogger(FXMLMenuGestionarUsuariosController.class);

    @FXML
    private Button btnRegistrarUsuario;

    @Override
    public void initialize(URL url, ResourceBundle rb) {
        cargarDatosUsuario();
    }

    private void cargarDatosUsuario() {
        Tutor tutor = Sesion.getTutorSesion();
        String rol = Sesion.getRolActual();

        if (tutor != null) {
            if (rol.equals("ADMINISTRADOR")) {
                btnRegistrarUsuario.setVisible(true);
            }
        }
    }

    @FXML
    private void clicRegistrarUsuario() {
        try {
            Utilidades.openModal("/usuario/FXMLRegistrarUsuario.fxml", "Registrar Académico");
        } catch (IOException e) {
            LOGGER.error("Error al cambiar a la ventana de registro de usuario", e);
            System.err.println("Error al cambiar a la ventana de registro de usuario: " + e.getMessage());
        } catch (Exception ex) {
            LOGGER.error("Error inesperado al cambiar a la ventana de registro de usuario", ex);
            System.err.println("Error inesperado al cambiar a la ventana de registro de usuario: " + ex.getMessage());
        }
    }

    @FXML
    private void clicVolverMenuPrincipal(ActionEvent event) {
        try {
            Utilidades.volverMenuPrincipal(event);
        } catch (IOException ex) {
            LOGGER.error("Error al volver al menú principal", ex);
            System.err.println("Error al volver al menú principal: " + ex.getMessage());
        } catch (Exception e) {
            LOGGER.error("Error inesperado al volver al menú principal", e);
            System.err.println("Error inesperado al volver al menú principal: " + e.getMessage());
        }
    }

    @FXML
    private void clicCerrarSesion(ActionEvent event) {
        Sesion.cerrarSesion();
        try {
            Utilidades.clicCerrarSesion(event);
        } catch (IOException ex) {
            LOGGER.error("Error al cerrar sesión", ex);
            System.err.println("Error al cerrar sesión: " + ex.getMessage());
        } catch (Exception e) {
            LOGGER.error("Error inesperado al cerrar sesión", e);
            System.err.println("Error inesperado al cerrar sesión: " + e.getMessage());
        }
    }
}
