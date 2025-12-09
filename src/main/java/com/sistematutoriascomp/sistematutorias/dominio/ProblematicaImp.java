/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.sistematutoriascomp.sistematutorias.dominio;

import java.sql.SQLException;
import java.util.HashMap;

import com.sistematutoriascomp.sistematutorias.model.dao.ProblematicaDAO;
import com.sistematutoriascomp.sistematutorias.model.pojo.Problematica;

public class ProblematicaImp {
    
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
            ex.printStackTrace();
        }
        
        return respuesta;
    }
}