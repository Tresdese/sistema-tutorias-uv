package com.sistematutoriascomp.sistematutorias.controller.usuario;

import java.io.IOException;
import java.net.URL;
import java.sql.SQLException;
import java.util.List;
import java.util.ResourceBundle;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import com.sistematutoriascomp.sistematutorias.model.dao.CarreraDAO;
import com.sistematutoriascomp.sistematutorias.model.dao.RolDAO;
import com.sistematutoriascomp.sistematutorias.model.dao.TutorDAO;
import com.sistematutoriascomp.sistematutorias.model.pojo.Carrera;
import com.sistematutoriascomp.sistematutorias.model.pojo.Rol;
import com.sistematutoriascomp.sistematutorias.model.pojo.Tutor;
import com.sistematutoriascomp.sistematutorias.utilidad.Utilidades;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.control.ComboBox;
import javafx.scene.control.PasswordField;
import javafx.scene.control.TextField;

public class FXMLRegistrarAcademicoController implements Initializable {

    @FXML
    private Button btnVolver;
    
    @FXML
    private Button btnCancelar;

    @FXML
    private Button btnGuardar;


    private final Logger LOGGER = LogManager.getLogger(FXMLRegistrarAcademicoController.class);

    @FXML private TextField txtNumeroPersonal;
    @FXML private TextField txtNombres;
    @FXML private TextField txtApellidoPaterno;
    @FXML private TextField txtApellidoMaterno;
    @FXML private TextField txtCorreoInstitucional;
    @FXML private PasswordField txtPassword;

    @FXML private ComboBox<String> cbRol;
    @FXML private ComboBox<String> cbCarrera;

    TutorDAO tutorDAO = new TutorDAO();
    RolDAO rolDAO = new RolDAO();
    CarreraDAO carreraDAO = new CarreraDAO();

    @Override
    public void initialize(URL url, ResourceBundle rb) {
        llenarCombos();
    }

    @FXML
    private void onGuardar(ActionEvent event) {
        if (validarCampos()) {
            registrarTutor();
        }
    }

    @FXML
    private void onCancelar(ActionEvent event) {
        limpiarCampos();
    }

    @FXML
    private void onVolver(ActionEvent event) {
        try {
            Utilidades.clicVolverMenuPrincipal(event);
        } catch (IOException ex) {
            LOGGER.error("Error al volver al menú principal", ex);
            ex.printStackTrace();
        } catch (Exception e) {
            LOGGER.error("Error inesperado al volver al menú principal", e);
            e.printStackTrace();
        }
    }

    private void limpiarCampos() {
        txtNumeroPersonal.clear();
        txtNombres.clear();
        txtApellidoPaterno.clear();
        txtApellidoMaterno.clear();
        txtCorreoInstitucional.clear();
        if (txtPassword != null) {
            txtPassword.clear();
        }
        cbRol.getSelectionModel().clearSelection();
        cbCarrera.getSelectionModel().clearSelection();
    }

    private void llenarCombos() {
        llenarComboBoxRoles();
        llenarComboBoxCarreras();
    }

    private void llenarComboBoxRoles() {
        List<Rol> roles;
        try {
            roles = rolDAO.obtenerTodosRoles();
                for (Rol rol : roles) {
                    cbRol.getItems().add(rol.getNombreRol());
                }
        } catch (SQLException ex) {
            LOGGER.error("Error al obtener roles de la base de datos", ex);
            Utilidades.mostrarAlertaSimple("Error de base de datos", 
                "Error al cargar roles: " + ex.getMessage(), 
                Alert.AlertType.ERROR);
        } catch (Exception e) {
            LOGGER.error("Error inesperado al obtener roles de la base de datos", e);
            Utilidades.mostrarAlertaSimple("Error inesperado", 
                "Ocurrió un error inesperado: " + e.getMessage(), 
                Alert.AlertType.ERROR);
        }
    }

    private void llenarComboBoxCarreras() {
        List<Carrera> carreras;
        try {
            carreras = carreraDAO.obtenerTodasCarreras();
                for (Carrera carrera : carreras) {
                    cbCarrera.getItems().add(carrera.getNombre());
                }
        } catch (SQLException ex) {
            LOGGER.error("Error al obtener carreras de la base de datos", ex);
            Utilidades.mostrarAlertaSimple("Error de base de datos", 
                "Error al cargar carreras: " + ex.getMessage(), 
                Alert.AlertType.ERROR);
        } catch (Exception e) {
            LOGGER.error("Error inesperado al obtener carreras de la base de datos", e);
            Utilidades.mostrarAlertaSimple("Error inesperado", 
                "Ocurrió un error inesperado: " + e.getMessage(), 
                Alert.AlertType.ERROR);
        }
    }
    
    private boolean validarCampos() {
        boolean respuesta = true;
        if (txtNumeroPersonal.getText().trim().isEmpty()) {
            Utilidades.mostrarAlertaSimple("Campos vacíos", "El número de personal es obligatorio", Alert.AlertType.WARNING);
            respuesta = false;
        }
        if (txtNombres.getText().trim().isEmpty()) {
            Utilidades.mostrarAlertaSimple("Campos vacíos", "El nombre es obligatorio", Alert.AlertType.WARNING);
            respuesta = false;
        }
        if (txtApellidoPaterno.getText().trim().isEmpty()) {
            Utilidades.mostrarAlertaSimple("Campos vacíos", "El apellido paterno es obligatorio", Alert.AlertType.WARNING);
            respuesta = false;
        }
        if (txtApellidoMaterno.getText().trim().isEmpty()) {
            Utilidades.mostrarAlertaSimple("Campos vacíos", "El apellido materno es obligatorio", Alert.AlertType.WARNING);
            respuesta = false;
        }
        if (txtCorreoInstitucional.getText().trim().isEmpty()) {
            Utilidades.mostrarAlertaSimple("Campos vacíos", "El correo institucional es obligatorio", Alert.AlertType.WARNING);
            respuesta = false;
        }
        if (txtPassword != null && txtPassword.getText().trim().isEmpty()) {
            Utilidades.mostrarAlertaSimple("Campos vacíos", "La contraseña es obligatoria", Alert.AlertType.WARNING);
            respuesta = false;
        }
        return respuesta;
    }
    
    private void registrarTutor() {
        try {
            Tutor tutor = new Tutor();
            tutor.setNumeroDePersonal(txtNumeroPersonal.getText().trim());
            tutor.setNombre(txtNombres.getText().trim());
            tutor.setApellidoPaterno(txtApellidoPaterno.getText().trim());
            tutor.setApellidoMaterno(txtApellidoMaterno.getText().trim());
            tutor.setIdRol(cbRol.getSelectionModel().getSelectedIndex() + 1);
            tutor.setCorreo(txtCorreoInstitucional.getText().trim());
            tutor.setIdCarrera(cbCarrera.getSelectionModel().getSelectedIndex() + 1);
            if (txtPassword != null) {
                tutor.setPassword(txtPassword.getText());
            }
            
            boolean registrado = tutorDAO.insertarTutor(tutor);
            
            if (registrado) {
                LOGGER.info("Tutor registrado exitosamente: {}", tutor.getNumeroDePersonal());
                Utilidades.mostrarAlertaSimple("Registro exitoso", 
                    "El tutor ha sido registrado correctamente", 
                    Alert.AlertType.INFORMATION);
                limpiarCampos();
            } else {
                Utilidades.mostrarAlertaSimple("Error de registro", 
                    "No se pudo registrar al tutor", 
                    Alert.AlertType.ERROR);
            }
        } catch (SQLException ex) {
            LOGGER.error("Error al registrar tutor en la base de datos", ex);
            Utilidades.mostrarAlertaSimple("Error de base de datos", 
                "Error al registrar tutor: " + ex.getMessage(), 
                Alert.AlertType.ERROR);
            ex.printStackTrace();
        } catch (Exception e) {
            LOGGER.error("Error inesperado al registrar tutor", e);
            Utilidades.mostrarAlertaSimple("Error inesperado", 
                "Ocurrió un error inesperado: " + e.getMessage(), 
                Alert.AlertType.ERROR);
            e.printStackTrace();
        }
    }
}
