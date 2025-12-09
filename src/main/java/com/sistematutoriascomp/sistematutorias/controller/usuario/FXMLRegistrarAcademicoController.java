package com.sistematutoriascomp.sistematutorias.controller.usuario;

import java.net.URL;
import java.sql.SQLException;
import java.util.ResourceBundle;

import com.sistematutoriascomp.sistematutorias.model.dao.TutorDAO;
import com.sistematutoriascomp.sistematutorias.model.pojo.Tutor;
import com.sistematutoriascomp.sistematutorias.utilidad.Utilidades;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Alert;
import javafx.scene.control.ComboBox;
import javafx.scene.control.PasswordField;
import javafx.scene.control.TextField;

public class FXMLRegistrarAcademicoController implements Initializable {

    @FXML private TextField txtNumeroPersonal;
    @FXML private TextField txtNombres;
    @FXML private TextField txtApellidoPaterno;
    @FXML private TextField txtApellidoMaterno;
    @FXML private TextField txtUsuario;
    @FXML private TextField txtCorreoInstitucional;
    @FXML private PasswordField txtPassword;
    @FXML private PasswordField txtConfirmPassword;

    @FXML private ComboBox<String> cbRol;
    @FXML private ComboBox<String> cbCarrera;

    TutorDAO tutorDAO = new TutorDAO();

    @Override
    public void initialize(URL url, ResourceBundle rb) {
        
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

    private void limpiarCampos() {
        txtNumeroPersonal.clear();
        txtNombres.clear();
        txtApellidoPaterno.clear();
        txtApellidoMaterno.clear();
        txtUsuario.clear();
        txtCorreoInstitucional.clear();
        if (txtPassword != null) {
            txtPassword.clear();
        }
        if (txtConfirmPassword != null) {
            txtConfirmPassword.clear();
        }
        cbRol.getSelectionModel().clearSelection();
        cbCarrera.getSelectionModel().clearSelection();
    }
    
    private boolean validarCampos() {
        if (txtNumeroPersonal.getText().trim().isEmpty()) {
            Utilidades.mostrarAlertaSimple("Campos vacíos", "El número de personal es obligatorio", Alert.AlertType.WARNING);
            return false;
        }
        if (txtNombres.getText().trim().isEmpty()) {
            Utilidades.mostrarAlertaSimple("Campos vacíos", "El nombre es obligatorio", Alert.AlertType.WARNING);
            return false;
        }
        if (txtApellidoPaterno.getText().trim().isEmpty()) {
            Utilidades.mostrarAlertaSimple("Campos vacíos", "El apellido paterno es obligatorio", Alert.AlertType.WARNING);
            return false;
        }
        if (txtApellidoMaterno.getText().trim().isEmpty()) {
            Utilidades.mostrarAlertaSimple("Campos vacíos", "El apellido materno es obligatorio", Alert.AlertType.WARNING);
            return false;
        }
        if (txtCorreoInstitucional.getText().trim().isEmpty()) {
            Utilidades.mostrarAlertaSimple("Campos vacíos", "El correo institucional es obligatorio", Alert.AlertType.WARNING);
            return false;
        }
        if (txtPassword != null && txtPassword.getText().trim().isEmpty()) {
            Utilidades.mostrarAlertaSimple("Campos vacíos", "La contraseña es obligatoria", Alert.AlertType.WARNING);
            return false;
        }
        if (txtConfirmPassword != null && txtConfirmPassword.getText().trim().isEmpty()) {
            Utilidades.mostrarAlertaSimple("Campos vacíos", "Debe confirmar la contraseña", Alert.AlertType.WARNING);
            return false;
        }
        if (txtPassword != null && txtConfirmPassword != null && 
            !txtPassword.getText().equals(txtConfirmPassword.getText())) {
            Utilidades.mostrarAlertaSimple("Error de validación", "Las contraseñas no coinciden", Alert.AlertType.WARNING);
            return false;
        }
        return true;
    }
    
    private void registrarTutor() {
        try {
            Tutor tutor = new Tutor();
            tutor.setNumeroDePersonal(txtNumeroPersonal.getText().trim());
            tutor.setNombre(txtNombres.getText().trim());
            tutor.setApellidoPaterno(txtApellidoPaterno.getText().trim());
            tutor.setApellidoMaterno(txtApellidoMaterno.getText().trim());
            tutor.setCorreo(txtCorreoInstitucional.getText().trim());
            if (txtPassword != null) {
                tutor.setPassword(txtPassword.getText());
            }
            
            boolean registrado = tutorDAO.insertarTutor(tutor);
            
            if (registrado) {
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
            Utilidades.mostrarAlertaSimple("Error de base de datos", 
                "Error al registrar tutor: " + ex.getMessage(), 
                Alert.AlertType.ERROR);
            ex.printStackTrace();
        }
    }
}
