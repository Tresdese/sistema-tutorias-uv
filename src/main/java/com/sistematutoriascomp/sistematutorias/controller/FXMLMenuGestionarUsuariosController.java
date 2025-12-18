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

public class FXMLMenuGestionarUsuariosController implements Initializable {

    private static final Logger LOGGER = Logger.getLogger(FXMLMenuGestionarUsuariosController.class.getName());

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
            LOGGER.severe("Error al cambiar a la ventana de registro de usuario: " + e.getMessage());
            e.printStackTrace();
        } catch (Exception ex) {
            LOGGER.severe("Error inesperado al cambiar a la ventana de registro de usuario: " + ex.getMessage());
            ex.printStackTrace();
        }
    }

    @FXML
    private void clicVolverMenuPrincipal(ActionEvent event) {
        try {
            Utilidades.volverMenuPrincipal(event);
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
