package com.sistematutoriascomp.sistematutorias.controller;

import java.io.IOException;
import java.net.URL;
import java.util.HashMap;
import java.util.ResourceBundle;

import com.sistematutoriascomp.sistematutorias.dominio.AsistenciaImp;
import com.sistematutoriascomp.sistematutorias.dominio.TutoriaImp;
import com.sistematutoriascomp.sistematutorias.model.pojo.Tutor;
import com.sistematutoriascomp.sistematutorias.utilidad.Sesion;
import com.sistematutoriascomp.sistematutorias.utilidad.Utilidades;
import java.util.logging.Logger;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;

public class FXMLMenuGestionarTutoriasController implements Initializable {
    private static final Logger LOGGER = Logger.getLogger(FXMLMenuGestionarTutoriasController.class.getName());
    
    @FXML private Button btnRegistrarHorario;
    @FXML private Button btnRegistrarAsistencia;
    @FXML private Button btnRegistrarFecha;
    @FXML private Button btnAsignarTutorado;
    @FXML private Button btnRegistrarTutorado;

    @Override
    public void initialize(URL url, ResourceBundle rb) {
        cargarDatosUsuario();
    }

    private void cargarDatosUsuario() {
        Tutor tutor = Sesion.getTutorSesion();
        String rol = Sesion.getRolActual();

        if (tutor != null) {
            if (rol.equals("ACADEMICO")) {
                btnRegistrarHorario.setVisible(true);
                btnRegistrarAsistencia.setVisible(true);
                btnRegistrarFecha.setVisible(false);
                btnAsignarTutorado.setVisible(false);
                btnRegistrarTutorado.setVisible(false);
            } else if (rol.equals("COORDINADOR")) {
                btnRegistrarHorario.setVisible(true);
                btnRegistrarAsistencia.setVisible(true);
                btnRegistrarFecha.setVisible(true);
                btnAsignarTutorado.setVisible(true);
                btnRegistrarTutorado.setVisible(true);
            } else if (rol.equals("ADMINISTRADOR")) {
                btnRegistrarHorario.setVisible(true);
                btnRegistrarAsistencia.setVisible(true);
                btnRegistrarFecha.setVisible(true);
                btnAsignarTutorado.setVisible(true);
                btnRegistrarTutorado.setVisible(true);
            }
        }
    }

    @FXML
    private void clicRegistrarHorario(ActionEvent event) {
        HashMap<String, Object> respuesta = TutoriaImp.obtenerFechasPeriodoActual();
        if (!(boolean) respuesta.get("error")) {
            irPantalla("/tutoria/FXMLRegistrarHoraTutoria.fxml", "Registrar Hora de Tutoría", event);
        } else {
            Utilidades.mostrarAlertaSimple("No se puede continuar",
                    (String) respuesta.get("mensaje"),
                    Alert.AlertType.WARNING);
        }
    }

    @FXML
    private void clicRegistrarAsistencia(ActionEvent event) {
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
    private void clicRegistrarFecha(ActionEvent event) {
        irPantalla("/tutoria/FXMLRegistrarFechaTutoria.fxml", "Registrar Fecha de Tutoría", event);
    }

    @FXML
    private void clicAsignarTutorado(ActionEvent event) {
        irPantalla("/tutoria/FXMLAsignarTutorado.fxml", "Asignar Tutorado", event);
    }
    
    @FXML
    private void clicRegistrarTutorado() {
        try {
            Utilidades.openModal("/tutoria/FXMLRegistrarTutorado.fxml", "Registrar Tutorado");
        } catch (IOException e) {
            LOGGER.severe("Error al cambiar a la ventana de registro de tutorado: " + e.getMessage());
            e.printStackTrace();
        } catch (Exception ex) {
            LOGGER.severe("Error inesperado al cambiar a la ventana de registro de tutorado: " + ex.getMessage());
            ex.printStackTrace();
        }
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