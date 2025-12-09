/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.sistematutoriascomp.sistematutorias.model.dao;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

import com.sistematutoriascomp.sistematutorias.model.ConexionBaseDatos;

public class PeriodoDAO {

    public static int obtenerIdPeriodoActual() throws SQLException {
        int idPeriodo = -1; 
        Connection conexion = ConexionBaseDatos.abrirConexionBD();

        if (conexion != null) {
            try {
                String consulta = "SELECT idPeriodo FROM periodo WHERE esActual = 1";
                PreparedStatement sentencia = conexion.prepareStatement(consulta);
                ResultSet resultado = sentencia.executeQuery();

                if (resultado.next()) {
                    idPeriodo = resultado.getInt("idPeriodo");
                }
            } finally {
                ConexionBaseDatos.cerrarConexionBD();
            }
        }
        return idPeriodo;
    }
}
