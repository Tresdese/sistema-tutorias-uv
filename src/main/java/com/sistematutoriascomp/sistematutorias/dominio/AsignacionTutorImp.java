package com.sistematutoriascomp.sistematutorias.dominio;

import com.sistematutoriascomp.sistematutorias.model.dao.AsignacionTutorDAO;
import com.sistematutoriascomp.sistematutorias.model.dao.FechaTutoriaDAO;
import com.sistematutoriascomp.sistematutorias.model.dao.TutorDAO;
import com.sistematutoriascomp.sistematutorias.model.dao.TutoradoDAO;
import com.sistematutoriascomp.sistematutorias.model.pojo.Tutor;
import com.sistematutoriascomp.sistematutorias.model.pojo.Tutorado;
import com.sistematutoriascomp.sistematutorias.utilidad.Sesion;

import java.sql.SQLException;

import java.util.HashMap;
import java.util.List;

public class AsignacionTutorImp {

    public static HashMap<String, Object> obtenerListasParaAsignacion() {
        HashMap<String, Object> respuesta = new HashMap<>();
        respuesta.put("error", true);

        try {
            TutoradoDAO tutoradoDAO = new TutoradoDAO();
            TutorDAO tutorDAO = new TutorDAO();

            List<Tutorado> listaTutorados = tutoradoDAO.obtenerTutoradosSinTutor();
            List<Tutor> listaTutores = tutorDAO.obtenerTutoresDisponibles();

            respuesta.put("error", false);
            respuesta.put("tutorados", listaTutorados);
            respuesta.put("tutores", listaTutores);

        } catch (SQLException e) {
            respuesta.put("mensaje", "Error de conexión al cargar las listas: " + e.getMessage());
            e.printStackTrace();
        } catch (Exception e) {
            respuesta.put("mensaje", "Error inesperado: " + e.getMessage());
            e.printStackTrace();
        }

        return respuesta;
    }

    public static HashMap<String, Object> asignarTutor(Tutorado tutorado, Tutor tutor) {
        HashMap<String, Object> respuesta = new HashMap<>();
        respuesta.put("error", true);

        try {
            int idPeriodo = Sesion.getIdPeriodoActual();

            if (idPeriodo <= 0) {
                idPeriodo = FechaTutoriaDAO.obtenerIdPeriodoActual();
            }

            if (idPeriodo <= 0) {
                respuesta.put("mensaje", "No se encontró un periodo escolar activo.");
                return respuesta;
            }

            TutoradoDAO tutoradoDAO = new TutoradoDAO();

            boolean actualizacionExitosa = tutoradoDAO.asignarTutor(tutorado.getIdTutorado(), tutor.getIdTutor());

            if (actualizacionExitosa) {
                boolean historialExitoso = AsignacionTutorDAO.registrarAsignacion(tutor.getIdTutor(), tutorado.getIdTutorado(), idPeriodo);

                if (historialExitoso) {
                    respuesta.put("error", false);
                    respuesta.put("mensaje", "La asignación se realizó correctamente.");
                } else {
                    respuesta.put("error", false);
                    respuesta.put("mensaje", "Se asignó el tutor, pero hubo una advertencia al guardar el historial.");
                }
            } else {
                respuesta.put("mensaje", "No se pudo asignar el tutor al estudiante en la base de datos.");
            }

        } catch (SQLException e) {
            respuesta.put("mensaje", "Error de base de datos al asignar: " + e.getMessage());
            e.printStackTrace();
        }

        return respuesta;
    }
}
