/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.sistematutoriascomp.sistematutorias.model.dao;

import java.sql.Connection;
import java.sql.Date;
import java.sql.PreparedStatement;
import java.sql.SQLException;

import com.sistematutoriascomp.sistematutorias.model.ConexionBaseDatos;
import com.sistematutoriascomp.sistematutorias.model.pojo.Problematica;

public class ProblematicaDAO {
    
    public static boolean registrarProblematica(Problematica problematica) throws SQLException {
        boolean resultado = false;
        Connection conexion = ConexionBaseDatos.abrirConexionBD();
        if (conexion != null) {
            try {
                String consulta = "INSERT INTO problematica (idTutorado, idTutoria, titulo, descripcion, fecha, estatus) " +
                                  "VALUES (?, ?, ?, ?, ?, ?)";
                PreparedStatement sentencia = conexion.prepareStatement(consulta);
                sentencia.setInt(1, problematica.getIdTutorado());
                sentencia.setInt(2, problematica.getIdTutoria());
                sentencia.setString(3, problematica.getTitulo());
                sentencia.setString(4, problematica.getDescripcion());
                sentencia.setDate(5, Date.valueOf(problematica.getFecha())); 
                sentencia.setString(6, "ABIERTA"); 
                int filasAfectadas = sentencia.executeUpdate();
                resultado = (filasAfectadas > 0);
            } finally {
                ConexionBaseDatos.cerrarConexionBD();
            }
        }
        return resultado;
    }
}