/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.sistematutoriascomp.sistematutorias.dominio;

import java.sql.SQLException;
import java.util.ArrayList;
import java.util.HashMap;

import com.sistematutoriascomp.sistematutorias.model.dao.ReporteTutoriaDAO;
import com.sistematutoriascomp.sistematutorias.model.pojo.ReporteTutoria;
import com.sistematutoriascomp.sistematutorias.model.pojo.Tutoria;
import com.sistematutoriascomp.sistematutorias.utilidad.Sesion;


public class ReporteTutoriaImp {
    
    public static HashMap<String, Object> obtenerSesionesPendientes(int idTutor) {
        HashMap<String, Object> respuesta = new HashMap<>();
        try {
            int periodoActual = Sesion.getIdPeriodoActual();
            ArrayList<Tutoria> lista = ReporteTutoriaDAO.obtenerSesionesPendientes(idTutor, periodoActual);
            if (lista.isEmpty()) {
                respuesta.put("error", true);
                respuesta.put("mensaje", "No hay sesiones pendientes de reporte en este periodo.");
            } else {
                respuesta.put("error", false);
                respuesta.put("sesiones", lista);
            }
        } catch (SQLException ex) {
            respuesta.put("error", true);
            respuesta.put("mensaje", "Error BD: " + ex.getMessage());
        }
        return respuesta;
    }

    public static HashMap<String, Object> cargarTotales(int idTutoria) {
        HashMap<String, Object> respuesta = new HashMap<>();
        try {
            HashMap<String, Integer> totales = ReporteTutoriaDAO.obtenerTotales(idTutoria);
            respuesta.put("error", false);
            respuesta.put("totales", totales);
        } catch (SQLException ex) {
            respuesta.put("error", true);
            respuesta.put("mensaje", "Error al calcular totales.");
        }
        return respuesta;
    }

    public static HashMap<String, Object> guardarReporte(ReporteTutoria reporte) {
        HashMap<String, Object> respuesta = new HashMap<>();
        try {
            if (ReporteTutoriaDAO.registrarReporte(reporte)) {
                respuesta.put("error", false);
                respuesta.put("mensaje", "Reporte generado correctamente.");
            } else {
                respuesta.put("error", true);
                respuesta.put("mensaje", "No se pudo guardar el reporte.");
            }
        } catch (SQLException ex) {
            respuesta.put("error", true);
            respuesta.put("mensaje", "Error BD: " + ex.getMessage());
        }
        return respuesta;
    }
}