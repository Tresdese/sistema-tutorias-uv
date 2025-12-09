/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/javafx/FXMLController.java to edit this template
 */
package com.sistematutoriascomp.sistematutorias.controller.reporte;

import java.io.IOException;
import java.net.URL;
import java.sql.SQLException;
import java.util.ResourceBundle;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import com.sistematutoriascomp.sistematutorias.model.dao.ReporteGeneralDAO;
import com.sistematutoriascomp.sistematutorias.model.pojo.ReporteGeneral;
import com.sistematutoriascomp.sistematutorias.utilidad.Utilidades;

import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.TextField;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.stage.Modality;
import javafx.stage.Stage;

/**
 * FXML Controller class
 *
 * @author super
 */
public class FXMLAdministrarReporteGeneralController implements Initializable {

    private static final Logger LOGGER = LogManager.getLogger(FXMLAdministrarReporteGeneralController.class);

    @FXML
    private Button btnVolver;
    @FXML
    private TextField txtBuscar;
    @FXML
    private Button btnRegistrar;
    @FXML
    private Button btnVerReporte;
    @FXML
    private TableView<ReporteGeneral> tblReportesGenerales;
    @FXML
    private TableColumn<?, ?> colIdReporteGeneral;
    @FXML
    private TableColumn<?, ?> colPeriodo;
    @FXML
    private TableColumn<?, ?> colFechaGeneracion;
    @FXML
    private TableColumn<?, ?> colEstado;
    
    private ReporteGeneralDAO reporteGeneralDAO;
    private ObservableList<ReporteGeneral> observableListReportesGenerales;
    
    /**
     * Initializes the controller class.
     */
    @Override
    public void initialize(URL url, ResourceBundle rb) {
        reporteGeneralDAO = new ReporteGeneralDAO();
        llenarTabla();
        cargarInformacion();
    }    

    @FXML
    private void onVolver(ActionEvent event) {
        irVolver(event);
    }

    @FXML
    private void onRegistrar(ActionEvent event) {
        abrirVentanaRegistrarReporteGeneral();
    }

    @FXML
    private void onVerReporte(ActionEvent event) {
        editarReporteSeleccionado();
    }

    private void cargarInformacion() {
        try {
            observableListReportesGenerales = reporteGeneralDAO.obtenerTodos();
            
            if(observableListReportesGenerales != null) {
                tblReportesGenerales.setItems(observableListReportesGenerales);
                configurarBusqueda();
            } else {
                Utilidades.mostrarAlertaSimple("Error", "No se pudieron cargar los reportes generales", Alert.AlertType.ERROR);
            }
        } catch (SQLException e) {
            LOGGER.error("Error al cargar los reportes generales desde la base de datos", e);
            Utilidades.mostrarAlertaSimple("Error de base de datos", "No se pudieron cargar los reportes generales: " + e.getMessage(), Alert.AlertType.ERROR);
            e.printStackTrace();
        } catch (Exception e) {
            LOGGER.error("Error inesperado al cargar los reportes generales", e);
            Utilidades.mostrarAlertaSimple("Error inesperado", "Ocurrió un error inesperado: " + e.getMessage(), Alert.AlertType.ERROR);
            e.printStackTrace();
        }
    }
    
    private void configurarBusqueda() {
    }

    private void llenarTabla() {
        colIdReporteGeneral.setCellValueFactory(new PropertyValueFactory<>("idReporteGeneral"));
        colPeriodo.setCellValueFactory(new PropertyValueFactory<>("nombrePeriodo"));
        colFechaGeneracion.setCellValueFactory(new PropertyValueFactory<>("fechaGeneracion"));
        colEstado.setCellValueFactory(new PropertyValueFactory<>("estado"));
    }

    private void irVolver(ActionEvent event) {
        Utilidades.cerrarVentana(event);
    }

    private void abrirVentanaRegistrarReporteGeneral() {
        try {
            Utilidades.openModal("/reporte/FXMLFormularioReporteGeneral.fxml", "Generar Reporte General");
            cargarInformacion();
        } catch (IOException e) {
            LOGGER.error("Error al abrir la ventana para registrar un reporte general", e);
            e.printStackTrace();
        } catch (NullPointerException e) {
            LOGGER.error("Recurso no encontrado al abrir la ventana para registrar un reporte general", e);
            e.printStackTrace();

        }catch (Exception e) {
            LOGGER.error("Error inesperado al abrir la ventana para registrar un reporte general", e);
            e.printStackTrace();
        }
    }

    private void editarReporteSeleccionado() {
        ReporteGeneral seleccionado = tblReportesGenerales.getSelectionModel().getSelectedItem();

        if (seleccionado == null) {
            Utilidades.mostrarAlertaSimple("Sin selección",
                    "Debe seleccionar un reporte general en la tabla para poder editarlo.",
                    Alert.AlertType.WARNING);
            return;
        }

        try {
            FXMLLoader loader = new FXMLLoader(Utilidades.class.getResource(
                    "/com/sistematutoriascomp/sistematutorias/views/reporte/FXMLFormularioReporteGeneral.fxml"));
            Parent vista = loader.load();

            FXMLFormularioReporteGeneralController controller = loader.getController();
            controller.inicializarParaEdicion(seleccionado);

            Stage escenario = new Stage();
            escenario.setScene(new Scene(vista));
            escenario.setTitle("Editar Reporte General");
            escenario.initModality(Modality.APPLICATION_MODAL);
            escenario.showAndWait();

            // Al cerrar el formulario de edición, recargar la tabla
            cargarInformacion();
        } catch (IOException e) {
            LOGGER.error("Error al abrir la ventana para editar un reporte general", e);
            Utilidades.mostrarAlertaSimple("Error",
                    "No se pudo abrir la ventana de edición del reporte general: " + e.getMessage(),
                    Alert.AlertType.ERROR);
        } catch (Exception e) {
            LOGGER.error("Error inesperado al editar un reporte general", e);
            Utilidades.mostrarAlertaSimple("Error inesperado",
                    "Ocurrió un error inesperado: " + e.getMessage(),
                    Alert.AlertType.ERROR);
        }
    }
}