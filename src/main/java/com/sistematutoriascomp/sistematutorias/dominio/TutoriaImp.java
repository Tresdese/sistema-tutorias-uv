/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/java to edit this template
 */
package com.sistematutoriascomp.sistematutorias.dominio;

import java.sql.SQLException;
import java.util.ArrayList;
import java.util.HashMap;

import com.sistematutoriascomp.sistematutorias.model.dao.FechaTutoriaDAO;
import com.sistematutoriascomp.sistematutorias.model.dao.TutoriaDAO;
import com.sistematutoriascomp.sistematutorias.model.pojo.FechaTutoria;
import com.sistematutoriascomp.sistematutorias.model.pojo.Tutoria;
import com.sistematutoriascomp.sistematutorias.utilidad.Sesion;

public class TutoriaImp {

    public static HashMap<String, Object> obtenerFechasPeriodoActual() {
        HashMap<String, Object> respuesta = new HashMap<>();
        try {
            int idPeriodo = Sesion.getIdPeriodoActual();
            ArrayList<FechaTutoria> fechas = FechaTutoriaDAO.obtenerFechasPorPeriodo(idPeriodo);
            if (!fechas.isEmpty()) {
                respuesta.put("error", false);
                respuesta.put("fechas", fechas);
            } else {
                respuesta.put("error", true);
                respuesta.put("mensaje", "No hay fechas de tutoría definidas por el coordinador para este periodo.");
            }
        } catch (SQLException ex) {
            respuesta.put("error", true);
            respuesta.put("mensaje", "Error al consultar fechas: " + ex.getMessage());
            ex.printStackTrace();
        }

        return respuesta;
    }

    public static HashMap<String, Object> registrarHorarioTutoria(Tutoria tutoria) {
        HashMap<String, Object> respuesta = new HashMap<>();
        respuesta.put("error", true);

        try {
            // 1. Validar si ya registró esta fecha (Llamada limpia al DAO)
            if (TutoriaDAO.comprobarTutoriaRegistrada(tutoria.getIdTutor(), tutoria.getFecha())) {
                respuesta.put("mensaje", "Ya has registrado un horario para esta fecha de tutoría.");
                return respuesta;
            }

            // 2. Insertar (Llamada limpia al DAO)
            int filas = TutoriaDAO.registrarTutoria(tutoria);
            if (filas > 0) {
                respuesta.put("error", false);
                respuesta.put("mensaje", "Horario registrado correctamente.");
            } else {
                respuesta.put("mensaje", "No se pudo registrar el horario.");
            }
        } catch (SQLException ex) {
            respuesta.put("mensaje", "Error de base de datos: " + ex.getMessage());
        }

        return respuesta;
    }

    public static HashMap<String, Object> subirEvidencia(int idTutoria, byte[] archivo) {
        HashMap<String, Object> respuesta = new HashMap<>();
        respuesta.put("error", true);
        try {
            if (TutoriaDAO.subirEvidencia(idTutoria, archivo)) {
                respuesta.put("error", false);
                respuesta.put("mensaje", "Evidencia subida correctamente.");
            } else {
                respuesta.put("mensaje", "No se pudo guardar la evidencia.");
            }
        } catch (SQLException ex) {
            respuesta.put("mensaje", "Error BD: " + ex.getMessage());
            ex.printStackTrace();
        }
        return respuesta;
    }
}
