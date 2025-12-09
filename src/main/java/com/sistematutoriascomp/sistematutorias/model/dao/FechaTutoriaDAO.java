/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.sistematutoriascomp.sistematutorias.model.dao;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;

import com.sistematutoriascomp.sistematutorias.model.ConexionBaseDatos;
import com.sistematutoriascomp.sistematutorias.model.pojo.FechaTutoria;

public class FechaTutoriaDAO {

    public static ArrayList<FechaTutoria> obtenerFechasPorPeriodo(int idPeriodo) throws SQLException {
        ArrayList<FechaTutoria> fechas = new ArrayList<>();
        Connection conexion = ConexionBaseDatos.abrirConexionBD();
        
        if (conexion != null) {
            try {
                String consulta = "SELECT idFechaTutoria, idPeriodo, numeroSesion, fecha " +
                                  "FROM fechatutoria " +
                                  "WHERE idPeriodo = ? " +
                                  "ORDER BY numeroSesion ASC";
                
                PreparedStatement sentencia = conexion.prepareStatement(consulta);
                sentencia.setInt(1, idPeriodo);
                ResultSet resultado = sentencia.executeQuery();
                
                while (resultado.next()) {
                    FechaTutoria fechaT = new FechaTutoria();
                    fechaT.setIdFechaTutoria(resultado.getInt("idFechaTutoria"));
                    fechaT.setIdPeriodo(resultado.getInt("idPeriodo"));
                    fechaT.setNumeroSesion(resultado.getInt("numeroSesion"));
                    // Convertimos java.sql.Date a java.time.LocalDate
                    fechaT.setFecha(resultado.getDate("fecha").toLocalDate());
                    
                    fechas.add(fechaT);
                }
            } finally {
                ConexionBaseDatos.cerrarConexionBD();
            }
        }
        return fechas;
    }
}