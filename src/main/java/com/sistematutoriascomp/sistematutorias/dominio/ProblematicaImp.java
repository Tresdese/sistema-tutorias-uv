package com.sistematutoriascomp.sistematutorias.dominio;

import java.sql.SQLException;
import java.util.HashMap;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import com.sistematutoriascomp.sistematutorias.model.dao.ProblematicaDAO;
import com.sistematutoriascomp.sistematutorias.model.pojo.Problematica;

public class ProblematicaImp {
    private static final Logger LOGGER = LogManager.getLogger(ProblematicaImp.class);
    
    public static HashMap<String, Object> registrarProblematica(Problematica problematica) {
        HashMap<String, Object> respuesta = new HashMap<>();
        respuesta.put("error", true);

        try {
            boolean exito = ProblematicaDAO.registrarProblematica(problematica);
            if (exito) {
                respuesta.put("error", false);
                respuesta.put("mensaje", "Problemática registrada correctamente.");
            } else {
                respuesta.put("mensaje", "No se pudo registrar la problemática.");
            }
        } catch (SQLException ex) {
            respuesta.put("mensaje", "Error de base de datos: " + ex.getMessage());
            LOGGER.error("Error al registrar problemática", ex);
            System.err.println("Error al registrar problemática: " + ex.getMessage());
        }

        return respuesta;
    }
}
