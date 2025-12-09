/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.sistematutoriascomp.sistematutorias.utilidad;

import java.sql.SQLException;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import com.sistematutoriascomp.sistematutorias.model.dao.RolDAO;
import com.sistematutoriascomp.sistematutorias.model.pojo.Tutor;

public class Sesion {

    private static final Logger LOGGER = LogManager.getLogger(Sesion.class);

    private static RolDAO rolDAO = new RolDAO();

    private static Tutor tutorSesion;
    // Aqui podrias agregar: private static Coordinador coordinadorSesion; para el futuro
    private static String rolActual; // "ACADEMICO", "COORDINADOR", "ADMINISTRADOR"
    private static int idPeriodoActual;

    public static Tutor getTutorSesion() {
        return tutorSesion;
    }

    public static void setTutorSesion(Tutor tutor) {
        tutorSesion = tutor;

        // Si no hay tutor en sesión, limpiamos el rol y terminamos
        if (tutorSesion == null) {
            rolActual = null;
            return;
        }

        try {
            String rol = rolDAO.obtenerRolPorId(tutor.getIdRol());
            if (rol != null) {
                rolActual = rol.toUpperCase();
            } else {
                LOGGER.warn("No se encontró un rol asociado al id {}", tutor.getIdRol());
                rolActual = null;
            }
        } catch (SQLException e) {
            LOGGER.error("Error en la base de datos al obtener el rol: " + e.getMessage(), e);
            rolActual = null;
        } catch (Exception ex) {
            LOGGER.error("Error inesperado al obtener el rol: " + ex.getMessage(), ex);
            rolActual = null;
        }
    }

    public static String getRolActual() {
        return rolActual;
    }
    
    public static int getIdPeriodoActual() {
        return idPeriodoActual;
    }

    public static void setIdPeriodoActual(int idPeriodo) {
        idPeriodoActual = idPeriodo;
    }
    
    // Método para limpiar al cerrar sesión
    public static void cerrarSesion() {
        tutorSesion = null;
        rolActual = null;
        idPeriodoActual = 0;
    }
}
