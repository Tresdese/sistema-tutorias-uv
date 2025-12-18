package com.sistematutoriascomp.sistematutorias.dominio;

import java.sql.SQLException;
import java.util.ArrayList;
import java.util.HashMap;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import com.sistematutoriascomp.sistematutorias.model.dao.AsistenciaDAO;
import com.sistematutoriascomp.sistematutorias.model.pojo.AsistenciaRow;
import com.sistematutoriascomp.sistematutorias.model.pojo.Tutoria;
import com.sistematutoriascomp.sistematutorias.utilidad.Sesion;

public class AsistenciaImp {
    private final static Logger LOGGER = LogManager.getLogger(AsistenciaImp.class);

    public static HashMap<String, Object> obtenerSesionesTutor(int idTutor) {
        HashMap<String, Object> respuesta = new HashMap<>();
        try {
            int idPeriodo = Sesion.getIdPeriodoActual();
            ArrayList<Tutoria> lista = AsistenciaDAO.obtenerSesionesPorTutor(idTutor, idPeriodo);
            if (lista.isEmpty()) {
                LOGGER.info("No se encontraron sesiones para el tutor con ID: {}", idTutor);
                respuesta.put("error", true);
                respuesta.put("mensaje", "No tienes sesiones registradas para el periodo actual.");
            } else {
                LOGGER.info("Sesiones obtenidas para tutor ID " + idTutor + ": " + lista.size());
                respuesta.put("error", false);
                respuesta.put("sesiones", lista);
            }
        } catch (SQLException ex) {
            LOGGER.error("Error al obtener sesiones para tutor: " + ex.getMessage());
            respuesta.put("error", true);
            respuesta.put("mensaje", "Error BD: " + ex.getMessage());
        }
        return respuesta;
    }

    public static HashMap<String, Object> obtenerListaAsistencia(int idTutor, int idTutoria) {
        HashMap<String, Object> respuesta = new HashMap<>();
        try {
            int idPeriodo = Sesion.getIdPeriodoActual();
            ArrayList<AsistenciaRow> lista = AsistenciaDAO.obtenerTutoradosPorTutor(idTutor, idPeriodo, idTutoria);
            respuesta.put("error", false);
            respuesta.put("tutorados", lista);
        } catch (SQLException ex) {
            LOGGER.error("Error al obtener lista de asistencia: " + ex.getMessage());
            respuesta.put("error", true);
            respuesta.put("mensaje", "Error BD: " + ex.getMessage());
        }
        return respuesta;
    }

    public static HashMap<String, Object> guardarListaAsistencia(int idTutoria, ArrayList<AsistenciaRow> lista) {
        HashMap<String, Object> respuesta = new HashMap<>();
        respuesta.put("error", false);
        try {
            for (AsistenciaRow alumno : lista) {
                AsistenciaDAO.registrarAsistencia(idTutoria, alumno.getIdTutorado(), alumno.isAsistio());
            }
            respuesta.put("mensaje", "Asistencia registrada correctamente.");
        } catch (SQLException ex) {
            LOGGER.error("Error al guardar lista de asistencia: " + ex.getMessage());
            respuesta.put("error", true);
            respuesta.put("mensaje", "Error al guardar: " + ex.getMessage());
        }
        return respuesta;
    }

    public static boolean yaTieneAsistenciaRegistrada(int idTutoria) {
        try {
            return AsistenciaDAO.existeAsistenciaParaTutoria(idTutoria);
        } catch (SQLException ex) {
            LOGGER.error("Error al verificar asistencia: " + ex.getMessage());
            System.err.println("Error al verificar asistencia: " + ex.getMessage());
            return false;
        }
    }

}
