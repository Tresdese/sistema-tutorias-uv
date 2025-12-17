package com.sistematutoriascomp.sistematutorias.dominio;

import java.sql.SQLException;

import com.sistematutoriascomp.sistematutorias.model.dao.AutenticacionDAO;
import com.sistematutoriascomp.sistematutorias.model.dao.PeriodoDAO;
import com.sistematutoriascomp.sistematutorias.model.pojo.Tutor;
import com.sistematutoriascomp.sistematutorias.utilidad.Sesion;

public class AutenticacionImp {
    
    public static boolean iniciarSesionTutor(String numeroPersonal, String password) {
        boolean respuesta = false;
        try {
            Tutor tutor = AutenticacionDAO.verificarSesionTutor(numeroPersonal, password);
            if (tutor != null) {
                Sesion.setTutorSesion(tutor);
                try {
                    int idPeriodo = PeriodoDAO.obtenerIdPeriodoActual();
                    if (idPeriodo > 0) {
                        Sesion.setIdPeriodoActual(idPeriodo);
                    } else {
                        System.out.println("ADVERTENCIA: No se encontró un periodo activo en la BD.");
                    }
                } catch (SQLException exPeriodo) {
                    System.err.println("Error al obtener periodo: " + exPeriodo.getMessage());
                }
                respuesta = true;
            }
        } catch (SQLException ex) {
            ex.printStackTrace();
        }
        return respuesta;
    }
}
