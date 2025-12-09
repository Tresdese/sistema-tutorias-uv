/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.sistematutoriascomp.sistematutorias.utilidad;

/**
 *
 * @author HP
 */
import com.sistematutoriascomp.sistematutorias.model.pojo.Tutor;

public class Sesion {
    private static Tutor tutorSesion;
    // Aqui podrias agregar: private static Coordinador coordinadorSesion; para el futuro
    private static String rolActual; // "TUTOR", "COORDINADOR", "ADMINISTRADOR"
    private static int idPeriodoActual;

    public static Tutor getTutorSesion() {
        return tutorSesion;
    }

    public static void setTutorSesion(Tutor tutor) {
        tutorSesion = tutor;
        rolActual = "TUTOR"; // Definimos el rol automáticamente
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
