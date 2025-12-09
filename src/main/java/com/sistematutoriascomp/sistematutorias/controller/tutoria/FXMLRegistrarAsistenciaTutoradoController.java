/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/javafx/FXMLController.java to edit this template
 */
package com.sistematutoriascomp.sistematutorias.controller.tutoria;

import java.io.File;
import java.io.IOException;
import java.net.URL;
import java.nio.file.Files;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.ResourceBundle;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.control.ComboBox;
import javafx.scene.control.Label;
import javafx.scene.control.TableCell;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.cell.CheckBoxTableCell;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.stage.FileChooser;
import javafx.stage.Stage;
import javafx.util.Callback;
import com.sistematutoriascomp.sistematutorias.dominio.AsistenciaImp;
import com.sistematutoriascomp.sistematutorias.dominio.TutoriaImp;
import com.sistematutoriascomp.sistematutorias.model.dao.TutoriaDAO;
import com.sistematutoriascomp.sistematutorias.model.pojo.AsistenciaRow;
import com.sistematutoriascomp.sistematutorias.model.pojo.Tutoria;
import com.sistematutoriascomp.sistematutorias.utilidad.Sesion;
import com.sistematutoriascomp.sistematutorias.utilidad.Utilidades;

public class FXMLRegistrarAsistenciaTutoradoController implements Initializable {

    @FXML
    private ComboBox<Tutoria> cbSesiones;
    @FXML
    private TableView<AsistenciaRow> tvAsistencia;
    @FXML
    private TableColumn<AsistenciaRow, String> colMatricula;
    @FXML
    private TableColumn<AsistenciaRow, String> colNombre;
    @FXML
    private TableColumn<AsistenciaRow, Integer> colSemestre;
    @FXML
    private TableColumn<AsistenciaRow, Boolean> colAsistio;
    @FXML
    private TableColumn<AsistenciaRow, Void> colAcciones;
    @FXML
    private Button btnRegistrar;
    @FXML
    private Button btnSubirEvidencia;
    @FXML
    private Label lbErrorSesion;
    @FXML
    private Label lbMensajeInfo;

    private ObservableList<AsistenciaRow> listaAlumnos;

    @Override
    public void initialize(URL url, ResourceBundle rb) {
        inicializarEstadoUI();
        configurarTabla();
        configurarEventos();
        cargarSesiones();
    }

    private void inicializarEstadoUI() {
        listaAlumnos = FXCollections.observableArrayList();
        tvAsistencia.setItems(listaAlumnos);
        limpiarErrorSesion();
        ocultarMensajeInfo();
        btnRegistrar.setDisable(false);
        btnSubirEvidencia.setDisable(true);
    }

    private void configurarEventos() {
        cbSesiones.valueProperty().addListener((obs, oldVal, newVal) -> onSesionSeleccionada(newVal));
    }

    private void configurarTabla() {
        colMatricula.setCellValueFactory(new PropertyValueFactory<>("matricula"));
        colNombre.setCellValueFactory(new PropertyValueFactory<>("nombreCompleto"));
        colSemestre.setCellValueFactory(new PropertyValueFactory<>("semestre"));
        colAsistio.setCellValueFactory(cellData -> cellData.getValue().asistioProperty());
        colAsistio.setCellFactory(CheckBoxTableCell.forTableColumn(colAsistio));
        tvAsistencia.setEditable(true);
        configurarColumnaAccionesProblematica();
    }

    private void configurarColumnaAccionesProblematica() {
        Callback<TableColumn<AsistenciaRow, Void>, TableCell<AsistenciaRow, Void>> cellFactory
                = new Callback<TableColumn<AsistenciaRow, Void>, TableCell<AsistenciaRow, Void>>() {
            @Override
            public TableCell<AsistenciaRow, Void> call(final TableColumn<AsistenciaRow, Void> param) {
                return new TableCell<AsistenciaRow, Void>() {
                    private final Button btn = new Button("Problemática");

                    {
                        btn.setOnAction((ActionEvent event) -> manejarClickProblematica());
                    }

                    private void manejarClickProblematica() {
                        AsistenciaRow data = getTableView().getItems().get(getIndex());
                        if (data == null) {
                            return;
                        }
                        if (data.isAsistio()) {
                            abrirVentanaProblematica(data.getIdTutorado(), data.getNombreCompleto());
                        } else {
                            Utilidades.mostrarAlertaSimple(
                                    "Aviso",
                                    "El alumno debe tener asistencia para registrar problemática.",
                                    Alert.AlertType.WARNING
                            );
                        }
                    }

                    @Override
                    public void updateItem(Void item, boolean empty) {
                        super.updateItem(item, empty);
                        if (empty) {
                            setGraphic(null);
                        } else {
                            setGraphic(btn);
                        }
                    }
                };
            }
        };
        colAcciones.setCellFactory(cellFactory);
    }

    private void cargarSesiones() {
        int idTutor = Sesion.getTutorSesion().getIdTutor();
        HashMap<String, Object> respuesta = AsistenciaImp.obtenerSesionesTutor(idTutor);

        if (!(boolean) respuesta.get("error")) {
            ArrayList<Tutoria> sesiones = (ArrayList<Tutoria>) respuesta.get("sesiones");
            cbSesiones.setItems(FXCollections.observableArrayList(sesiones));
        } else {
            Utilidades.mostrarAlertaSimple(
                    "Sin sesiones",
                    (String) respuesta.get("mensaje"),
                    Alert.AlertType.INFORMATION
            );
        }
    }

    private void onSesionSeleccionada(Tutoria nuevaSesion) {
        limpiarErrorSesion();
        if (nuevaSesion == null) {
            limpiarTablaAsistencia();
            ocultarMensajeInfo();
            btnSubirEvidencia.setDisable(true);
            btnRegistrar.setDisable(false); // por si acaso
            return;
        }

        cargarAlumnos();
        cargarEstadoEvidencia(nuevaSesion.getIdTutoria());
        configurarEstadoBotonRegistrar(nuevaSesion.getIdTutoria());
    }

    private void configurarEstadoBotonRegistrar(int idTutoria) {
        boolean yaTieneAsistencia = AsistenciaImp.yaTieneAsistenciaRegistrada(idTutoria);
        btnRegistrar.setDisable(yaTieneAsistencia);
    }

    private void cargarAlumnos() {
        Tutoria sesion = cbSesiones.getValue();
        if (sesion == null) {
            limpiarTablaAsistencia();
            return;
        }
        int idTutor = Sesion.getTutorSesion().getIdTutor();
        HashMap<String, Object> respuesta = AsistenciaImp.obtenerListaAsistencia(idTutor, sesion.getIdTutoria());
        if (!(boolean) respuesta.get("error")) {
            listaAlumnos = FXCollections.observableArrayList(
                    (ArrayList<AsistenciaRow>) respuesta.get("tutorados")
            );
            tvAsistencia.setItems(listaAlumnos);
        } else {
            Utilidades.mostrarAlertaSimple(
                    "Error",
                    (String) respuesta.get("mensaje"),
                    Alert.AlertType.ERROR
            );
            limpiarTablaAsistencia();
        }
    }

    private void limpiarTablaAsistencia() {
        if (listaAlumnos != null) {
            listaAlumnos.clear();
        }
    }

    @FXML
    private void clicRegistrar(ActionEvent event) {
        Tutoria sesion = cbSesiones.getValue();
        if (!esSesionSeleccionadaValida(sesion)) {
            return;
        }
        guardarAsistenciaSesion(sesion);
    }

    private boolean esSesionSeleccionadaValida(Tutoria sesion) {
        if (sesion == null) {
            lbErrorSesion.setText("Selecciona una sesión");
            lbErrorSesion.setVisible(true);
            lbErrorSesion.setManaged(true);
            return false;
        }
        return true;
    }

    private void guardarAsistenciaSesion(Tutoria sesion) {
        if (listaAlumnos == null || listaAlumnos.isEmpty()) {
            Utilidades.mostrarAlertaSimple(
                    "Sin alumnos",
                    "No hay tutorados para registrar asistencia.",
                    Alert.AlertType.WARNING
            );
            return;
        }

        HashMap<String, Object> res = AsistenciaImp.guardarListaAsistencia(
                sesion.getIdTutoria(),
                new ArrayList<>(listaAlumnos)
        );
        if (!(boolean) res.get("error")) {
            Utilidades.mostrarAlertaSimple(
                    "Éxito",
                    (String) res.get("mensaje"),
                    Alert.AlertType.INFORMATION
            );
            btnRegistrar.setDisable(true);
            habilitarSubirEvidenciaSiCorresponde(sesion.getIdTutoria());
        } else {
            Utilidades.mostrarAlertaSimple(
                    "Error",
                    (String) res.get("mensaje"),
                    Alert.AlertType.ERROR
            );
        }
    }

    private void habilitarSubirEvidenciaSiCorresponde(int idTutoria) {
        try {
            boolean tieneEvidencia = TutoriaDAO.comprobarExistenciaEvidencia(idTutoria);
            if (!tieneEvidencia) {
                btnSubirEvidencia.setDisable(false);
                ocultarMensajeInfo();
            } else {
                btnSubirEvidencia.setDisable(true);
                mostrarMensajeInfo("Ya se ha subido evidencia para esta sesión.", "#2e7d32");
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    private void cargarEstadoEvidencia(int idTutoria) {
        try {
            boolean tieneEvidencia = TutoriaDAO.comprobarExistenciaEvidencia(idTutoria);
            if (tieneEvidencia) {
                btnSubirEvidencia.setDisable(true);
                mostrarMensajeInfo("Ya se ha subido evidencia para esta sesión.", "#2e7d32");
            } else {
                btnSubirEvidencia.setDisable(true);
                ocultarMensajeInfo();
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    @FXML
    private void clicSubirEvidencia(ActionEvent event) {
        Tutoria sesion = cbSesiones.getValue();
        if (!validarSesionParaEvidencia(sesion)) {
            return;
        }
        if (yaTieneEvidenciaEnBD(sesion)) {
            return;
        }
        File archivo = mostrarSelectorArchivoPdf();
        if (archivo == null) {
            return;
        }
        if (!validarArchivoPdf(archivo)) {
            return;
        }
        subirEvidenciaAServicio(sesion, archivo);
    }

    private boolean validarSesionParaEvidencia(Tutoria sesion) {
        if (sesion == null) {
            Utilidades.mostrarAlertaSimple(
                    "Selección requerida",
                    "Por favor seleccione una sesión de tutoría.",
                    Alert.AlertType.WARNING
            );
            return false;
        }
        return true;
    }

    private boolean yaTieneEvidenciaEnBD(Tutoria sesion) {
        try {
            if (TutoriaDAO.comprobarExistenciaEvidencia(sesion.getIdTutoria())) {
                Utilidades.mostrarAlertaSimple(
                        "Aviso",
                        "Ya existe una evidencia para esta sesión.",
                        Alert.AlertType.INFORMATION
                );
                btnSubirEvidencia.setDisable(true);
                mostrarMensajeInfo("Ya se ha subido evidencia para esta sesión.", "#2e7d32");
                return true;
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return false;
    }

    private File mostrarSelectorArchivoPdf() {
        FileChooser dialogo = new FileChooser();
        dialogo.setTitle("Seleccionar evidencia (PDF)");
        dialogo.getExtensionFilters().add(
                new FileChooser.ExtensionFilter("Archivos PDF", "*.pdf")
        );
        return dialogo.showOpenDialog(btnSubirEvidencia.getScene().getWindow());
    }

    private boolean validarArchivoPdf(File archivo) {
        long maxBytes = 5L * 1024L * 1024L;
        if (archivo.length() > maxBytes) {
            Utilidades.mostrarAlertaSimple(
                    "Archivo muy pesado",
                    "El archivo debe pesar menos de 5MB.",
                    Alert.AlertType.WARNING
            );
            return false;
        }

        try {
            String mimeType = Files.probeContentType(archivo.toPath());

            if (mimeType != null && !mimeType.equals("application/pdf")) {
                Utilidades.mostrarAlertaSimple(
                        "Formato incorrecto",
                        "El archivo seleccionado no es un PDF válido.",
                        Alert.AlertType.WARNING
                );
                return false;
            }

            if (mimeType == null && !archivo.getName().toLowerCase().endsWith(".pdf")) {
                Utilidades.mostrarAlertaSimple(
                        "Formato incorrecto",
                        "El archivo debe tener extensión .pdf",
                        Alert.AlertType.WARNING
                );
                return false;
            }

        } catch (IOException e) {
            Utilidades.mostrarAlertaSimple(
                    "Error",
                    "Error al validar el archivo: " + e.getMessage(),
                    Alert.AlertType.ERROR
            );
            return false;
        }

        return true;
    }

    private void subirEvidenciaAServicio(Tutoria sesion, File archivo) {
        try {
            byte[] datosArchivo = Files.readAllBytes(archivo.toPath());
            HashMap<String, Object> respuesta
                    = TutoriaImp.subirEvidencia(sesion.getIdTutoria(), datosArchivo);

            if (!(boolean) respuesta.get("error")) {
                Utilidades.mostrarAlertaSimple(
                        "Éxito",
                        (String) respuesta.get("mensaje"),
                        Alert.AlertType.INFORMATION
                );
                btnSubirEvidencia.setDisable(true);
                mostrarMensajeInfo("Ya se ha subido evidencia para esta sesión.", "#2e7d32");
            } else {
                Utilidades.mostrarAlertaSimple(
                        "Error",
                        (String) respuesta.get("mensaje"),
                        Alert.AlertType.ERROR
                );
            }
        } catch (Exception ex) {
            Utilidades.mostrarAlertaSimple(
                    "Error",
                    "Error al leer el archivo: " + ex.getMessage(),
                    Alert.AlertType.ERROR
            );
        }
    }

    private void abrirVentanaProblematica(int idTutorado, String nombreAlumno) {
        try {
            Tutoria sesionActual = cbSesiones.getValue();
            if (sesionActual == null) {
                return;
            }

            FXMLLoader loader = new FXMLLoader(
                    getClass().getResource("/sistematutorias/vista/tutoria/FXMLRegistrarProblematica.fxml")
            );
            Parent root = loader.load();

            FXMLRegistrarProblematicaController controlador = loader.getController();
            controlador.inicializarValores(
                    sesionActual.getIdTutoria(),
                    idTutorado,
                    nombreAlumno
            );

            Stage escenario = new Stage();
            escenario.setScene(new Scene(root));
            escenario.setTitle("Registrar Problemática");
            escenario.initModality(javafx.stage.Modality.APPLICATION_MODAL);
            escenario.showAndWait();

        } catch (Exception ex) {
            ex.printStackTrace();
        }
    }

    @FXML
    private void clicVolver(ActionEvent event) {
        navegarAMenuTutoria(event);
    }

    private void navegarAMenuTutoria(ActionEvent event) {
        try {
            Utilidades.goToWindow("/sistematutorias/vista/FXMLMenuTutoria.fxml", event, "Menú Tutoría");
        } catch (IOException ex) {
            ex.printStackTrace();
            Utilidades.mostrarAlertaSimple(
                    "Error de navegación",
                    "No se pudo cargar el menú principal.",
                    Alert.AlertType.ERROR
            );
        }
    }

    private void limpiarErrorSesion() {
        lbErrorSesion.setText("");
        lbErrorSesion.setVisible(false);
        lbErrorSesion.setManaged(false);
    }

    private void mostrarMensajeInfo(String texto, String colorHex) {
        lbMensajeInfo.setText(texto);
        lbMensajeInfo.setStyle("-fx-text-fill: " + colorHex + ";");
        lbMensajeInfo.setVisible(true);
        lbMensajeInfo.setManaged(true);
    }

    private void ocultarMensajeInfo() {
        lbMensajeInfo.setText("");
        lbMensajeInfo.setVisible(false);
        lbMensajeInfo.setManaged(false);
    }
}
