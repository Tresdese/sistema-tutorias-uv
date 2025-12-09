/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/javafx/FXMLController.java to edit this template
 */
package com.sistematutoriascomp.sistematutorias.controller.tutoria;

import java.net.URL;
import java.time.LocalDate;
import java.util.HashMap;
import java.util.ResourceBundle;

import com.sistematutoriascomp.sistematutorias.dominio.ProblematicaImp;
import com.sistematutoriascomp.sistematutorias.model.pojo.Problematica;
import com.sistematutoriascomp.sistematutorias.utilidad.Utilidades;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Alert;
import javafx.scene.control.Label;
import javafx.scene.control.TextArea;
import javafx.scene.control.TextField;
import javafx.stage.Stage;

public class FXMLRegistrarProblematicaController implements Initializable {
    @FXML
    private TextField tfTitulo;
    @FXML
    private TextArea taDescripcion;
    @FXML
    private Label lbNombreAlumno;

    private int idTutoria;
    private int idTutorado;

    @Override
    public void initialize(URL url, ResourceBundle rb) {
        tfTitulo.textProperty().addListener((observable, oldValue, newValue) -> {
            if (newValue.length() > 120) {
                tfTitulo.setText(oldValue);
            }
        });
    }

    public void inicializarValores(int idTutoria, int idTutorado, String nombreAlumno) {
        this.idTutoria = idTutoria;
        this.idTutorado = idTutorado;
        this.lbNombreAlumno.setText(nombreAlumno);
    }

    @FXML
    private void clicGuardar(ActionEvent event) {
        String titulo = tfTitulo.getText() != null ? tfTitulo.getText().trim() : "";
        String descripcion = taDescripcion.getText() != null ? taDescripcion.getText().trim() : "";
        if (titulo.isEmpty() || descripcion.isEmpty()) {
            Utilidades.mostrarAlertaSimple("Campos vacíos", "Por favor llena todos los campos.", Alert.AlertType.WARNING);
            return;
        } else if (titulo.length() > 120) {
            Utilidades.mostrarAlertaSimple("Título demasiado largo", 
                "El título no puede exceder los 120 caracteres. Actualmente tiene " + titulo.length() + ".", 
                Alert.AlertType.WARNING);
            return;
        } else if (descripcion.length() > 500) {
            Utilidades.mostrarAlertaSimple("Descripción demasiado larga", 
                "La descripción no puede exceder los 500 caracteres. Actualmente tiene " + descripcion.length() + ".", 
                Alert.AlertType.WARNING);
            return;
        }
        Problematica nuevaProblematica = prepararProblematica(titulo, descripcion);
        enviarAlDominio(nuevaProblematica);
    }
    
    private Problematica prepararProblematica(String titulo, String descripcion) {
        Problematica p = new Problematica();
        p.setIdTutoria(this.idTutoria);
        p.setIdTutorado(this.idTutorado);
        p.setTitulo(titulo);
        p.setDescripcion(descripcion);
        p.setFecha(LocalDate.now());
        return p;
    }

    private void enviarAlDominio(Problematica problematica) {
        HashMap<String, Object> respuesta = ProblematicaImp.registrarProblematica(problematica);
        
        if (!(boolean) respuesta.get("error")) {
            Utilidades.mostrarAlertaSimple("Éxito", (String) respuesta.get("mensaje"), Alert.AlertType.INFORMATION);
            cerrarVentana();
        } else {
            Utilidades.mostrarAlertaSimple("Error", (String) respuesta.get("mensaje"), Alert.AlertType.ERROR);
        }
    }

    @FXML
    private void clicCancelar(ActionEvent event) {
        cerrarVentana();
    }

    private void cerrarVentana() {
        Stage stage = (Stage) tfTitulo.getScene().getWindow();
        stage.close();
    }
}